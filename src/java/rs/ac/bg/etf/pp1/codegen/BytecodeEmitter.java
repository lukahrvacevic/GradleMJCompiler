package rs.ac.bg.etf.pp1.codegen;

import rs.ac.bg.etf.pp1.logger.CompilerDiagnostics;
import rs.ac.bg.etf.pp1.symbols.SymbolTable;
import rs.ac.bg.etf.pp1.symbols.Type;
import rs.ac.bg.etf.pp1.util.Context;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.io.OutputStream;

import static rs.etf.pp1.mj.runtime.Code.*;
import static rs.ac.bg.etf.pp1.symbols.Symbol.*;

@SuppressWarnings("SpellCheckingInspection")
public final class BytecodeEmitter {
    private static final Context.Key<BytecodeEmitter> emitterKey = new Context.Key<>();
    private final CompilerDiagnostics diagnostics;
    private boolean alive = true;
    private Chain pendingJumps = null;
    private int nextStatic = 0;

    private BytecodeEmitter(Context context) {
        context.put(emitterKey, this);
        this.diagnostics = CompilerDiagnostics.getInstance(context);
    }

    public static BytecodeEmitter getInstance(Context context) {
        BytecodeEmitter emitter = context.get(emitterKey);
        if (emitter == null) {
            emitter = new BytecodeEmitter(context);
        }
        return emitter;
    }

    private void emit1(int od) {
        if (!alive) return;
        if (pc >= buf.length) {
            diagnostics.report(new CodeGenErrors.CodeTooBig());
            alive = false;
            return;
        }
        put(od);
    }

    private void emit2(int od) {
        if (!alive) return;
        if (pc + 2 > buf.length) {
            emit1(od >> 8);
            emit1(od);
        } else {
            put2(od);
        }
    }

    private void emit4(int od) {
        if (!alive) return;
        if (pc + 4 > buf.length) {
            emit1(od >> 24);
            emit1(od >> 16);
            emit1(od >> 8);
            emit1(od);
        } else {
            put4(od);
        }
    }

    private int get1(int pc) {
        return buf[pc] & 0xFF;
    }

    private void emitop(int op) {
        if (pendingJumps != null) resolvePending();
        if (!alive) return;
        emit1(op);
    }

    public void emitop0(int op) {
        emitop(op);
        if (!alive) return;
        switch (op) {
            case return_:
            case jmp:
            case trap:
                markDead();
        }
    }

    public void emitop1(int op, int od) {
        emitop(op);
        if (!alive) return;
        emit1(od);
    }

    public void emitop2(int op, int od) {
        emitop(op);
        if (!alive) return;
        emit2(od);
        if (op == jmp) markDead();
    }

    public void emitop4(int op, int od) {
        emitop(op);
        if (!alive) return;
        emit4(od);
    }

    public void emitVirtualFunction(MethodSymbol method) {
        for (char c : method.getName().toCharArray()) {
            emitop4(const_, c);
            emitop2(putstatic, nextStatic++);
        }
        emitop0(const_m1);
        emitop2(putstatic, nextStatic++);
        emitop4(const_, method.getAdr());
        emitop2(putstatic, nextStatic++);
    }

    public void emitVFTEntryEnd() {
        emitop4(const_, -2);
        emitop2(putstatic, nextStatic++);
    }

    public void emitInvokevirtual(String name) {
        emit1(invokevirtual);
        for (char c : name.toCharArray())
            emit4(c);
        emit4(-1);
    }

    public boolean isAlive() {
        return alive || pendingJumps != null;
    }

    public void markDead() {
        alive = false;
    }

    public int entryPoint() {
        int currentPc = pc;
        alive = true;
        return currentPc;
    }

    public int currentPC() {
        return pc;
    }

    public void setMain(int entry) {
        mainPc = entry;
    }

    public void emitCall(int address) {
        emitop2(call, address - pc);
    }

    public Chain emitCall(Chain chain) {
        emitop2(call, 0);
        return mergeChains(chain, new Chain(pc - 3, null));
    }

    public static class Chain {
        public final int pc;
        public final Chain next;

        public Chain(int pc, Chain next) {
            this.pc = pc;
            this.next = next;
        }
    }

    public int negate(int opcode) {
        switch (opcode) {
            case Bytecodes.ifeq: return Bytecodes.ifne;
            case Bytecodes.ifne: return Bytecodes.ifeq;
            case Bytecodes.iflt: return Bytecodes.ifge;
            case Bytecodes.ifge: return Bytecodes.iflt;
            case Bytecodes.ifgt: return Bytecodes.ifle;
            case Bytecodes.ifle: return Bytecodes.ifgt;
            default: {
                diagnostics.report(new CodeGenErrors.UnexpectedOpcode(opcode));
                return opcode;
            }
        }
    }

    public int emitJump(int opcode) {
        emitop2(opcode, 0);
        return pc - 3;
    }

    public Chain branch(int opcode) {
        Chain result = null;
        if (opcode == jmp) {
            result = pendingJumps;
            pendingJumps = null;
        }
        if (opcode != Bytecodes.dontjmp && isAlive()) {
            result = new Chain(emitJump(opcode), result);
            if (opcode == jmp) alive = false;
        }
        return result;
    }

    public void resolve(Chain chain, int target) {
        while (chain != null) {
            if (get1(chain.pc) == jmp && chain.pc + 3 == target && target == pc) {
                pc = pc - 3;
                target = target - 3;
                if (chain.next == null) {
                    alive = true;
                    break;
                }
            } else {
                put2(chain.pc + 1, target - chain.pc);
                chain = chain.next;
            }
            if (pc == target) {
                alive = true;
            }
        }
    }

    public void resolve(Chain chain) {
        pendingJumps = mergeChains(chain, pendingJumps);
    }

    public void resolvePending() {
        Chain x = pendingJumps;
        pendingJumps = null;
        resolve(x, pc);
    }

    public static Chain mergeChains(Chain chain1, Chain chain2) {
        if (chain1 == null) return chain2;
        if (chain2 == null) return chain1;

        if (chain1.pc < chain2.pc) return new Chain(chain2.pc, mergeChains(chain1, chain2.next));
        else return new Chain(chain1.pc, mergeChains(chain1.next, chain2));
    }

    public void setStaticFieldCount(int size) {
        nextStatic = size;
    }

    public int getNextStatic() {
        return nextStatic;
    }

    public void setDataSize() {
        dataSize = nextStatic;
    }

    public int getDataSize() {
        return dataSize;
    }

    static int getTypecode(Type type) {
        if (type == SymbolTable.VOID) return Bytecodes.voidCode;

        switch (type.getKind()) {
            case Struct.Int: return Bytecodes.intCode;
            case Struct.Char: return Bytecodes.charCode;
            case Struct.Bool: return Bytecodes.byteCode;
            case Struct.Enum: return Bytecodes.voidCode;
            case Struct.Array:
            case Struct.Class:
            case Struct.Interface:
                return Bytecodes.objectCode;
            default: throw new AssertionError("Unexpected type: " + type);
        }
    }

    public static void write(OutputStream os) {
        Code.write(os);
    }
}
