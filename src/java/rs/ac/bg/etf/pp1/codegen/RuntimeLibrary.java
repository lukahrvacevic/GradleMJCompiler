package rs.ac.bg.etf.pp1.codegen;

import rs.ac.bg.etf.pp1.symbols.Symbol;
import rs.ac.bg.etf.pp1.symbols.Symbol.*;
import rs.ac.bg.etf.pp1.symbols.SymbolTable;
import rs.ac.bg.etf.pp1.util.Context;

import java.util.Arrays;
import java.util.List;

import static rs.ac.bg.etf.pp1.codegen.Bytecodes.*;
import static rs.ac.bg.etf.pp1.codegen.BytecodeEmitter.Chain;

@SuppressWarnings("DuplicatedCode")
public final class RuntimeLibrary {
    private RuntimeLibrary() {}

    private interface BuiltinEmitter {
        void emit(Context context);
    }

    private static final List<BuiltinEmitter> BUILTINS = Arrays.asList(
            RuntimeLibrary::emitChr,
            RuntimeLibrary::emitOrd,
            RuntimeLibrary::emitFindAnyChar,
            RuntimeLibrary::emitFindAnyInt
    );

    public static void emit(Context context) {
        for (BuiltinEmitter emitter : BUILTINS) {
            emitter.emit(context);
        }
    }

    private static void emitChr(Context context) {
        BytecodeEmitter code = BytecodeEmitter.getInstance(context);
        SymbolTable table = SymbolTable.getInstance(context);

        Symbol method = table.find("chr");
        if (method == SymbolTable.NO_SYMBOL) {
            throw new AssertionError("Expected method symbol");
        }

        int pc = code.entryPoint();
        method.setAdr(pc);
        code.emitop2(enter, (1 << 8) | 1);

        code.emitop0(load_0);
        code.emitop0(exit);
        code.emitop0(return_);
    }

    private static void emitOrd(Context context) {
        BytecodeEmitter code = BytecodeEmitter.getInstance(context);
        SymbolTable table = SymbolTable.getInstance(context);

        Symbol method = table.find("ord");
        if (method == SymbolTable.NO_SYMBOL) {
            throw new AssertionError("Expected method symbol");
        }

        int pc = code.entryPoint();
        method.setAdr(pc);
        code.emitop2(enter, (1 << 8) | 1);

        code.emitop0(load_0);
        code.emitop0(exit);
        code.emitop0(return_);
    }

    private static void emitFindAnyChar(Context context) {
        emitFindAny(context, true);
    }

    private static void emitFindAnyInt(Context context) {
        emitFindAny(context, false);
    }

    private static void emitFindAny(Context context, boolean isChar) {
        BytecodeEmitter code = BytecodeEmitter.getInstance(context);
        SymbolTable table = SymbolTable.getInstance(context);

        String name = isChar ? "findAny<char>" : "findAny<int>";
        Symbol method = table.find(name);
        if (method == SymbolTable.NO_SYMBOL) {
            throw new AssertionError("Expected method symbol");
        }

        int elementLoadOp = isChar ? baload : aload;

        int pc = code.entryPoint();
        method.setAdr(pc);
        code.emitop2(enter, (3 << 8) | 2);

        // i = 0
        code.emitop0(const_0);
        code.emitop0(store_2);

        int loopStart = code.currentPC();

        // if (i - arr.length >= 0) goto notFound
        code.emitop0(load_2);       // i
        code.emitop0(load_0);       // arr
        code.emitop0(arraylength);  // arr.length
        code.emitop0(sub);
        Chain notFoundChain = code.branch(ifge);

        // if (arr[i] - value == 0) goto found
        code.emitop0(load_0);       // arr
        code.emitop0(load_2);       // i
        code.emitop0(elementLoadOp);
        code.emitop0(load_1);       // value
        code.emitop0(sub);
        Chain foundChain = code.branch(ifeq);

        // i++
        code.emitop0(load_2);       // i
        code.emitop0(const_1);
        code.emitop0(add);
        code.emitop0(store_2);
        code.emitop2(jmp, loopStart - code.currentPC());

        // notFound: push false
        code.resolve(notFoundChain);
        code.emitop0(const_0);
        Chain endChain = code.branch(jmp);

        // found: push true
        code.resolve(foundChain);
        code.emitop0(const_1);

        code.resolve(endChain);
        code.emitop0(exit);
        code.emitop0(return_);
    }
}
