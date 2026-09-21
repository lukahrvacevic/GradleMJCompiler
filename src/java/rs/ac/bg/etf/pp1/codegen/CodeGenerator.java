package rs.ac.bg.etf.pp1.codegen;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.codegen.Items.*;
import rs.ac.bg.etf.pp1.symbols.Symbol;
import rs.ac.bg.etf.pp1.symbols.Symbol.*;
import rs.ac.bg.etf.pp1.symbols.SymbolTable;
import rs.ac.bg.etf.pp1.symbols.Type;
import rs.ac.bg.etf.pp1.symbols.Type.*;
import rs.ac.bg.etf.pp1.util.Context;
import rs.ac.bg.etf.pp1.util.TreeVisitor;

import java.util.List;

import static rs.ac.bg.etf.pp1.codegen.Bytecodes.*;
import static rs.ac.bg.etf.pp1.codegen.BytecodeEmitter.*;

public final class CodeGenerator extends TreeVisitor {
    private final BytecodeEmitter code;
    private final Items items;
    private List<ClassSymbol> classes;
    private ClassSymbol currentClass;

    private Item result;

    public CodeGenerator(Context context) {
        this.code = BytecodeEmitter.getInstance(context);
        this.items = new Items(context);
    }

    public void generateProgram(MJProgram node) {
        ProgramSymbol program = node.programsymbol;
        classes = program.getClasses();
        List<MethodSymbol> methods = program.getMethods();
        int staticFieldCount = program.getStaticFieldCount();

        code.setStaticFieldCount(staticFieldCount);

        for (ClassSymbol clazz : classes) {
            currentClass = clazz;
            if (!clazz.getSymbolType().isClass()) continue;

            List<MethodSymbol> classMethods = clazz.getThisType().getMethods();

            for (MethodSymbol method : classMethods) {
                generateMethod(method);
            }
        }

        currentClass = null;

        for (MethodSymbol method : methods) {
            generateMethod(method);
        }

        code.setDataSize();
    }

    private void generateMethod(MethodSymbol method) {
        if (method.getOwner() != currentClass && currentClass != null)
            return;

        Method_body node = (Method_body) method.getBody();

        if (node instanceof MJAbstractMethodBody) {
            return;
        }

        int entry = code.entryPoint();
        method.setAdr(entry);
        code.emitop2(enter, (method.getLevel() << 8) | (method.getLocalSymbols().size() & 0xFF));

        if (method.isMain()) {
            generateVFT(classes);
            code.setMain(entry);
        }

        generateStatement(node);

        if (!code.isAlive()) {
            // everything returned normally
            return;
        }

        if (method.getSymbolType().isVoid()) {
            code.emitop0(exit);
            code.emitop0(return_);
        } else {
            // live code at the end of a method that expects return value.
            // we do not have control flow checks, so this might happen.
            code.emitop2(trap, 1);
        }
    }

    private void generateVFT(List<ClassSymbol> classes) {
        for (ClassSymbol clazz : classes) {
            clazz.setAdr(code.getNextStatic());
            List<MethodSymbol> methods = clazz.getThisType().getMethods();
            if (methods.isEmpty()) continue;

            for (MethodSymbol method : methods) {
                code.emitVirtualFunction(method);
            }
            code.emitVFTEntryEnd();
        }
    }

    @Override
    public void visit(MJNullLiteral node) {
        result = items.makeStackItem(SymbolTable.NULL);
    }

    @Override
    public void visit(MJIntegerLiteral node) {
        result = items.makeImmediateItem(intCode, node.getI1());
    }

    @Override
    public void visit(MJBooleanLiteral node) {
        result = items.makeImmediateItem(byteCode, node.getB1());
    }

    @Override
    public void visit(MJCharacterLiteral node) {
        result = items.makeImmediateItem(charCode, node.getC1());
    }

    @Override
    public void visit(MJSimpleName node) {
        Symbol symbol = node.expressionvalue.symbol;
        if (symbol.isConstant()) {
            result = items.makeImmediateItem(BytecodeEmitter.getTypecode(symbol.getSymbolType()), symbol.getAdr());
        } else if (symbol.isStatic()) {
            result = items.makeStaticItem(symbol);
        } else if (symbol.isMember()) {
            items.makeThisItem().load();
            result = items.makeMemberItem(symbol);
        } else {
            result = items.makeLocalItem(symbol);
        }
    }

    @Override
    public void visit(MJQualifiedName node) {
        Symbol qualifier = node.getName().expressionvalue.symbol;
        Symbol symbol = node.expressionvalue.symbol;
        if (qualifier.isType()) {
            result = items.makeImmediateItem(BytecodeEmitter.getTypecode(symbol.getSymbolType()), symbol.getAdr());
        } else {
            generateExpression(node.getName()).load();
            result = items.makeMemberItem(symbol);
        }
    }

    @Override
    public void visit(MJLength node) {
        generateExpression(node.getName()).load();
        code.emitop0(arraylength);
        result = items.makeStackItem(SymbolTable.INT);
    }

    @Override
    public void visit(MJAssignmentStatementExpression node) {
        generateExpression(node.getAssignment());
        result.drop();
    }

    @Override
    public void visit(MJPostincrementStatementExpression node) {
        generateStatement(node.getPostincrement_expression());
        result.drop();
    }

    @Override
    public void visit(MJPostdecrementStatementExpression node) {
        generateStatement(node.getPostdecrement_expression());
        result.drop();
    }

    @Override
    public void visit(MJMethodInvocationStatementExpression node) {
        generateStatement(node.getMethod_invocation());
        if (result.typecode != voidCode) {
            result.drop();
        }
    }

    @Override
    public void visit(MJIfThen node) {
        generateIf(node.getIf_condition(), node.getStatement(), null);
    }

    @Override
    public void visit(MJIfThenElse node) {
        generateIf(node.getIf_condition(), node.getStatement_no_short_if(), node.getStatement());
    }

    @Override
    public void visit(MJIfThenElseNoShortIf node) {
        generateIf(node.getIf_condition(), node.getStatement_no_short_if(), node.getStatement_no_short_if1());
    }

    private void generateIf(SyntaxNode condition, SyntaxNode thenBranch, SyntaxNode elseBranch) {
        ConditionalItem conditional = generateConditional(condition);

        if (conditional.isTrue()) {
            generateStatement(thenBranch);
            return;
        }

        if (conditional.isFalse()) {
            if (elseBranch != null) {
                generateStatement(elseBranch);
            }
            return;
        }

        Chain elseChain = conditional.jumpFalse();

        code.resolve(conditional.trueJumps);
        generateStatement(thenBranch);
        Chain thenExit = code.branch(jmp);

        if (elseChain != null) {
            code.resolve(elseChain);
            if (elseBranch != null) {
                generateStatement(elseBranch);
            }
        }

        code.resolve(thenExit);
    }

    private static final class GeneratorContext {
        Chain exit = null;
        Chain continue_ = null;

        void addExit(Chain chain) {
            exit = mergeChains(chain, exit);
        }

        void addContinue(Chain chain) {
            continue_ = mergeChains(chain, continue_);
        }
    }

    private GeneratorContext info;

    @Override
    public void visit(MJFor node) {
        generateStatement(node.getFor_init_opt());
        SyntaxNode condition = null;
        if (node.getExpression_opt() instanceof MJExpressionOption) {
            condition = ((MJExpressionOption) node.getExpression_opt()).getExpression();
        }
        generateLoop(node.getStatement(), condition, node.getFor_update_opt());
    }

    private void generateLoop(SyntaxNode body, SyntaxNode condition, SyntaxNode step) {
        GeneratorContext old = info;
        info = new GeneratorContext();

        int start = code.entryPoint();
        ConditionalItem conditional;
        if (condition != null) {
            conditional = generateConditional(condition);
        } else {
            conditional = items.makeConditionalItem(jmp);
        }
        Chain loopDone = conditional.jumpFalse();
        code.resolve(conditional.trueJumps);
        generateStatement(body);
        code.resolve(info.continue_);
        generateStatement(step);
        code.resolve(code.branch(jmp), start);
        code.resolve(loopDone);
        code.resolve(info.exit);

        info = old;
    }

    @Override
    public void visit(MJBreak node) {
        info.addExit(code.branch(jmp));
    }

    @Override
    public void visit(MJContinue node) {
        info.addContinue(code.branch(jmp));
    }

    @Override
    public void visit(MJReturn node) {
        if (node.getExpression_opt() instanceof MJExpressionOption) {
            generateExpression(node.getExpression_opt()).load();
        }
        code.emitop0(exit);
        code.emitop0(return_);
    }

    @Override
    public void visit(MJThis node) {
        result = items.makeThisItem();
    }

    @Override
    public void visit(MJParenthesisedExpression node) {
        result = generateExpression(node.getExpression()).load();
    }

    @Override
    public void visit(MJClassInstanceCreation node) {
        Symbol clazz = node.expressionvalue.symbol;
        ClassType type = (ClassType) node.expressionvalue.type;
        code.emitop2(new_, type.getSize());
        code.emitop0(dup);
        items.makeImmediateItem(intCode, clazz.getAdr()).load();
        code.emitop2(putfield, 0);
        result = items.makeStackItem(type);
    }

    @Override
    public void visit(MJArrayCreation node) {
        Type arrayType = ((ArrayType) node.expressionvalue.type).getElementType();
        generateExpression(node.getExpression()).load();
        code.emitop1(newarray, arrayType == SymbolTable.CHAR ? 0 : 1);
        result = items.makeStackItem(arrayType);
    }

    @Override
    public void visit(MJFieldAccess node) {
        generateExpression(node.getPrimary()).load();
        result = items.makeMemberItem(node.expressionvalue.symbol);
    }

    @Override
    public void visit(MJLengthFieldAccess node) {
        generateExpression(node.getPrimary()).load();
        code.emitop0(arraylength);
        result = items.makeStackItem(SymbolTable.INT);
    }

    @Override
    public void visit(MJMethodInvocation node) {
        Symbol method = node.expressionvalue.symbol;
        if (method.isMember()) {
            generateExpression(node.getName());
        }
        generateArguments(node.getArgument_list_opt());
        Item item = generateExpression(node.getName());
        result = item.invoke();
    }

    @Override
    public void visit(MJFindAny node) {
        Symbol arraySymbol = node.getName().expressionvalue.symbol;
        Type elementType = ((ArrayType) arraySymbol.getSymbolType()).getElementType();

        generateExpression(node.getName()).load();
        generateExpression(node.getExpression()).load();

        generateFindAny(elementType);
    }

    @Override
    public void visit(MJQualifiedFindAny node) {
        Symbol arraySymbol = node.getPrimary().expressionvalue.symbol;
        Type elementType = ((ArrayType) arraySymbol.getSymbolType()).getElementType();

        generateExpression(node.getPrimary()).load();
        generateExpression(node.getExpression()).load();

        generateFindAny(elementType);
    }

    private void generateFindAny(Type elementType) {
        int typecode = getTypecode(elementType);
        MethodSymbol builtin = (typecode == charCode || typecode == byteCode)
                ? SymbolTable.findAnyChar
                : SymbolTable.findAnyInt;
        result = items.makeStaticItem(builtin).invoke();
    }

    @Override
    public void visit(MJQualifiedMethodInvocation node) {
        Symbol method = node.expressionvalue.symbol;
        generateExpression(node.getPrimary()).load();
        generateArguments(node.getArgument_list_opt());
        if (method.isMember()) {
            generateExpression(node.getPrimary()).load();
        }
        Item item = items.makeMemberItem(node.expressionvalue.symbol);
        result = item.invoke();
    }

    private void generateArguments(Argument_list_opt node) {
        generateExpression(node);
    }

    @Override
    public void visit(MJRead node) {
        Item item = generateExpression(node.getExpression()); // generate argument
        code.emitop0(getInstruction(item.typecode, read));
        item.store();
        result = items.makeVoidItem();
    }

    @Override
    public void visit(MJPrint node) {
        Item item = generateExpression(node.getExpression()).load();
        items.makeImmediateItem(intCode, 1).load();
        code.emitop0(getInstruction(item.typecode, print));
        result = items.makeVoidItem();
    }

    @Override
    public void visit(MJPrintConst node) {
        Item item = generateExpression(node.getExpression()).load();
        items.makeImmediateItem(intCode, node.getI2()).load();
        code.emitop0(getInstruction(item.typecode, print));
        result = items.makeVoidItem();
    }

    private static int getInstruction(int typecode, int base) {
        switch (typecode) {
            case intCode: return base;
            case charCode:
            case byteCode:
                return base + 2;
            default:
                throw new AssertionError("Unexpected typecode: " + typecode);
        }
    }

    @Override
    public void visit(MJArrayAccess node) {
        generateExpression(node.getName()).load();
        generateExpression(node.getExpression()).load();
        result = items.makeIndexedItem(node.expressionvalue.type);
    }

    @Override
    public void visit(MJQualifiedArrayAccess node) {
        generateExpression(node.getPrimary_no_new_array()).load();
        generateExpression(node.getExpression()).load();
        result = items.makeIndexedItem(node.expressionvalue.type);
    }

    @Override
    public void visit(MJArgument node) {
        generateExpression(node.getExpression()).load();
    }

    @Override
    public void visit(MJNextArgument node) {
        node.getArgument_list().accept(this);
        generateExpression(node.getExpression()).load();
    }

    @Override
    public void visit(MJPostincrement node) {
        generateUnary(node.getPostfix_expression(), add);
    }

    @Override
    public void visit(MJPostdecrement node) {
        generateUnary(node.getPostfix_expression(), sub);
    }

    private void generateUnary(SyntaxNode node, int opcode) {
        Item item = generateExpression(node);

        item.duplicate();

        Item res = item.load();
        if (item instanceof LocalItem) {
            ((LocalItem) item).increment(opcode == add ? 1 : -1);
        } else {
            item.stash(item.typecode);
            code.emitop0(const_1);
            code.emitop0(opcode);
            item.store();
        }
        result = res;
    }

    @Override
    public void visit(MJNegation node) {
        result = generateExpression(node.getUnary_expression()).load();
        code.emitop0(neg);
    }

    @Override
    public void visit(MJMultiplication node) {
        result = finishBinary(node.getMultiplicative_expression(), node.getUnary_expression(), mul);
    }

    @Override
    public void visit(MJDivision node) {
        result = finishBinary(node.getMultiplicative_expression(), node.getUnary_expression(), div);
    }

    @Override
    public void visit(MJModulo node) {
        result = finishBinary(node.getMultiplicative_expression(), node.getUnary_expression(), rem);
    }

    @Override
    public void visit(MJAddition node) {
        result = finishBinary(node.getAdditive_expression(), node.getMultiplicative_expression(), add);
    }

    @Override
    public void visit(MJSubtraction node) {
        result = finishBinary(node.getAdditive_expression(), node.getMultiplicative_expression(), sub);
    }

    @Override
    public void visit(MJLessThan node) {
        result = finishBinary(node.getRelational_expression(), node.getAdditive_expression(), iflt);
    }

    @Override
    public void visit(MJGreaterThan node) {
        result = finishBinary(node.getRelational_expression(), node.getAdditive_expression(), ifgt);
    }

    @Override
    public void visit(MJLessThanOrEqualTo node) {
        result = finishBinary(node.getRelational_expression(), node.getAdditive_expression(), ifle);
    }

    @Override
    public void visit(MJGreaterThanOrEqualTo node) {
        result = finishBinary(node.getRelational_expression(), node.getAdditive_expression(), ifge);
    }

    @Override
    public void visit(MJEqual node) {
        result = finishBinary(node.getEquality_expression(), node.getRelational_expression(), ifeq);
    }

    @Override
    public void visit(MJNotEqual node) {
        result = finishBinary(node.getEquality_expression(), node.getRelational_expression(), ifne);
    }

    private Item finishBinary(
            SyntaxNode left,
            SyntaxNode right,
            int opcode
    ) {
        if (opcode >= ifeq && opcode <= ifge) {
            generateExpression(left).load();
            generateExpression(right).load();
            return items.makeConditionalItem(opcode, true);
        } else if (opcode >= add && opcode <= rem) {
            if (isConstant(left) && isConstant(right)) {
                Item leftItem = generateExpression(left);
                Item rightItem = generateExpression(right);
                int leftValue = ((ImmediateItem) leftItem).value;
                int rightValue = ((ImmediateItem) rightItem).value;
                return items.makeImmediateItem(intCode, calculate(leftValue, rightValue, opcode));
            }
            generateExpression(left).load();
            generateExpression(right).load();
            code.emitop0(opcode);
            return items.makeStackItem(SymbolTable.INT);
        } else {
            throw new AssertionError("Unexpected opcode: " + opcode);
        }
    }

    private static boolean isConstant(SyntaxNode node) {
        if (node instanceof Multiplicative_expression) {
            return ((Multiplicative_expression) node).expressionvalue.isConstant;
        } else if (node instanceof Additive_expression) {
            return ((Additive_expression) node).expressionvalue.isConstant;
        } else if (node instanceof Relational_expression) {
            return ((Relational_expression) node).expressionvalue.isConstant;
        } else if (node instanceof Equality_expression) {
            return ((Equality_expression) node).expressionvalue.isConstant;
        } else {
            return false;
        }
    }

    private static int calculate(int left, int right, int opcode) {
        switch (opcode) {
            case add: return left + right;
            case sub: return left - right;
            case mul: return left * right;
            case div: return left / right;
            case rem: return left % right;
            default: throw new AssertionError("Unexpected opcode: " + opcode);
        }
    }

    @Override
    public void visit(MJConjunction node) {
        ConditionalItem left = generateConditional(node.getConditional_and_expression());
        if (!left.isFalse()) {
            Chain falseJumps = left.jumpFalse();
            code.resolve(left.trueJumps);
            ConditionalItem right = generateConditional(node.getEquality_expression());
            result = items.makeConditionalItem(
                    right.opcode,
                    right.trueJumps,
                    mergeChains(falseJumps, right.falseJumps)
            );
        } else {
            result = left;
        }
    }

    @Override
    public void visit(MJDisjunction node) {
        ConditionalItem left = generateConditional(node.getConditional_or_expression());
        if (!left.isTrue()) {
            Chain trueJumps = left.jumpTrue();
            code.resolve(left.falseJumps);
            ConditionalItem right = generateConditional(node.getConditional_and_expression());
            result = items.makeConditionalItem(
                    right.opcode,
                    mergeChains(trueJumps, right.trueJumps),
                    right.falseJumps
            );
        } else {
            result = left;
        }
    }

    @Override
    public void visit(MJTernaryOperation node) {
        SyntaxNode conditional = node.getConditional_or_expression();
        SyntaxNode trueBranch = node.getExpression();
        SyntaxNode falseBranch = node.getConditional_expression();

        ConditionalItem condition = generateConditional(conditional);

        if (condition.isTrue()) {
            generateExpression(trueBranch).load();
            return;
        }

        if (condition.isFalse()) {
            generateExpression(falseBranch).load();
            return;
        }

        Chain elseChain = condition.jumpFalse();

        code.resolve(condition.trueJumps);
        generateExpression(trueBranch).load();
        Chain thenExit = code.branch(jmp);

        if (elseChain != null) {
            code.resolve(elseChain);
            generateExpression(falseBranch).load();
        }

        code.resolve(thenExit);
        result = items.makeStackItem(node.expressionvalue.type);
    }

    @Override
    public void visit(MJAssignment node) {
        Item lhs = generateExpression(node.getLeft_hand_side());
        generateExpression(node.getAssignment_expression()).load();
        result = items.makeAssignItem(lhs);
    }

    @Override
    public void visit(MJNoExpression node) {
        result = null;
    }

    private void generateStatement(SyntaxNode node) {
        node.accept(this);
    }

    private Item generateExpression(SyntaxNode node) {
        node.accept(this);
        return result;
    }

    private ConditionalItem generateConditional(SyntaxNode node) {
        return generateExpression(node).asConditional();
    }
}
