package rs.ac.bg.etf.pp1.semantic;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.logger.CompilerDiagnostics;
import rs.ac.bg.etf.pp1.symbols.Flags;
import rs.ac.bg.etf.pp1.symbols.Symbol;
import rs.ac.bg.etf.pp1.symbols.Symbol.*;
import rs.ac.bg.etf.pp1.symbols.SymbolTable;
import rs.ac.bg.etf.pp1.symbols.Type;
import rs.ac.bg.etf.pp1.symbols.Type.*;
import rs.ac.bg.etf.pp1.util.Context;
import rs.ac.bg.etf.pp1.util.TreeVisitor;

import java.util.ArrayList;
import java.util.List;

public final class SemanticAnalyzer extends TreeVisitor {
    private final CompilerDiagnostics diagnostics;

    private MethodSymbol currentMethod;

    public SemanticAnalyzer(Context context) {
        diagnostics = CompilerDiagnostics.getInstance(context);
    }

    @Override
    public void visit(MJMethodDeclaration tree) {
        currentMethod = tree.methodsymbol;
        tree.getMethod_body().accept(this);
    }

    // Method body

    @Override
    public void visit(MJMethodBody tree) {
        if (currentMethod.getModifiers().has(Flags.Modifier.ABSTRACT)) {
            diagnostics.report(new SemanticErrors.AbstractMethodWithBody(tree.getLine()));
        }

        tree.getBlock().accept(this);
    }

    @Override
    public void visit(MJAbstractMethodBody tree) {
        if (!currentMethod.getModifiers().has(Flags.Modifier.ABSTRACT)) {
            diagnostics.report(new SemanticErrors.MethodWithoutBody(tree.getLine()));
        }
    }

    // Statements

    @Override
    public void visit(MJIfThen tree) {
        checkIf((MJIfCondition) tree.getIf_condition(), tree.getStatement(), null);
    }

    @Override
    public void visit(MJIfThenElse tree) {
        checkIf((MJIfCondition) tree.getIf_condition(), tree.getStatement_no_short_if(), tree.getStatement());
    }

    @Override
    public void visit(MJIfThenElseNoShortIf tree) {
        checkIf((MJIfCondition) tree.getIf_condition(), tree.getStatement_no_short_if(), tree.getStatement_no_short_if1());
    }

    private void checkIf(
            MJIfCondition condition,
            SyntaxNode thenBranch,
            SyntaxNode elseBranch
    ) {
        condition.getExpression().accept(this);

        Type conditionType = condition.getExpression().expressionvalue.type;
        if (conditionType != SymbolTable.BOOL) {
            diagnostics.report(new SemanticErrors.ArgumentsNotMatching(SymbolTable.BOOL, conditionType, condition.getLine()));
        }

        thenBranch.accept(this);
        if (elseBranch != null) elseBranch.accept(this);
    }

    private int loopDepth = 0;

    private boolean isContinueAllowed() { return loopDepth > 0; }
    private boolean isBreakAllowed() { return loopDepth > 0; }

    @Override
    public void visit(MJFor tree) {
        checkFor(tree.getFor_init_opt(), tree.getExpression_opt(), tree.getFor_update_opt(), tree.getStatement());
    }

    @Override
    public void visit(MJForNoShortIf tree) {
        checkFor(tree.getFor_init_opt(), tree.getExpression_opt(), tree.getFor_update_opt(), tree.getStatement_no_short_if());
    }

    private void checkFor(
            For_init_opt init,
            Expression_opt condition,
            For_update_opt update,
            SyntaxNode body
    ) {
        init.accept(this);

        condition.accept(this);
        Type conditionType = condition.expressionvalue.type;
        if (conditionType != SymbolTable.BOOL) {
            diagnostics.report(new SemanticErrors.ArgumentsNotMatching(SymbolTable.BOOL, conditionType, condition.getLine()));
        }

        update.accept(this);

        loopDepth++;
        body.accept(this);
        loopDepth--;
    }

    @Override
    public void visit(MJBreak tree) {
        if (!isBreakAllowed()) {
            diagnostics.report(new SemanticErrors.NotAllowedHere("break", tree.getLine()));
        }
    }

    @Override
    public void visit(MJContinue tree) {
        if (!isContinueAllowed()) {
            diagnostics.report(new SemanticErrors.NotAllowedHere("continue", tree.getLine()));
        }
    }

    @Override
    public void visit(MJReturn tree) {
        tree.getExpression_opt().accept(this);
        Type returnType = tree.getExpression_opt().expressionvalue.type;
        if (returnType == SymbolTable.VOID && currentMethod.getReturnType() != SymbolTable.VOID) {
            diagnostics.report(new SemanticErrors.MissingReturnValue(tree.getLine()));
            return;
        }

        if (returnType != SymbolTable.VOID && currentMethod.getReturnType() == SymbolTable.VOID) {
            diagnostics.report(new SemanticErrors.VoidMethodReturns(tree.getLine()));
            return;
        }

        if (returnType != currentMethod.getReturnType()) {
            diagnostics.report(new SemanticErrors.ArgumentsNotMatching(currentMethod.getReturnType(), returnType, tree.getExpression_opt().getLine()));
        }
    }

    @Override
    public void visit(MJMap tree) {
        tree.getName().accept(this);
        visitMapStatement(
                tree.getLeft_hand_side(),
                tree.getName().expressionvalue.symbol,
                tree.getI3(),
                tree.getExpression(),
                tree
        );
    }

    @Override
    public void visit(MJQualifiedMap tree) {
        tree.getPrimary().accept(this);
        visitMapStatement(
                tree.getLeft_hand_side(),
                tree.getPrimary().expressionvalue.symbol,
                tree.getI3(),
                tree.getExpression(),
                tree
        );
    }

    private void visitMapStatement(
            Left_hand_side lhs,
            Symbol arraySymbol,
            String identifier,
            Expression expression,
            SyntaxNode tree
    ) {
        if (arraySymbol == SymbolTable.NO_SYMBOL) {
            return;
        }

        if (!arraySymbol.getSymbolType().isArray()) {
            diagnostics.report(new SemanticErrors.NotAnArray(arraySymbol.getName(), tree.getLine()));
            return;
        }

        Symbol it = currentMethod.find(identifier);
        if (it == SymbolTable.NO_SYMBOL) {
            diagnostics.report(new SemanticErrors.NameNotFound(identifier, tree.getLine()));
            return;
        }
        if (!it.isVariable()) {
            diagnostics.report(new SemanticErrors.LocalOrGlobalVariableExpected(tree.getLine()));
            return;
        }

        Type itType = it.getSymbolType();
        ArrayType arrayType = (ArrayType) arraySymbol.getSymbolType();
        Type elementType = arrayType.getElementType();
        expression.accept(this);
        Type expressionType = expression.expressionvalue.type;
        if (!(itType.assignable(elementType) && expressionType.assignable(elementType))) {
            diagnostics.report(new SemanticErrors.ArgumentsNotMatching(
                    "(" + elementType + ") => " + elementType,
                    "(" + itType + ") => " + expressionType,
                    tree.getLine()
            ));
            return;
        }

        lhs.accept(this);
        Type lhsType = lhs.expressionvalue.type;
        if (!arrayType.assignable(lhsType)) {
            diagnostics.report(new SemanticErrors.NotAssignable(lhsType, arrayType, tree.getLine()));
        }
    }

    // Expressions

    @Override
    public void visit(MJNullLiteral tree) {
        tree.expressionvalue = new ExpressionValue(SymbolTable.NULL);
    }

    @Override
    public void visit(MJIntegerLiteral tree) {
        int value = tree.getI1();
        tree.expressionvalue = new ExpressionValue(SymbolTable.INT, value);
    }

    @Override
    public void visit(MJBooleanLiteral tree) {
        int value = tree.getB1();
        tree.expressionvalue = new ExpressionValue(SymbolTable.BOOL, value);
    }

    @Override
    public void visit(MJCharacterLiteral tree) {
        int value = tree.getC1();
        tree.expressionvalue = new ExpressionValue(SymbolTable.CHAR, value);
    }

    @Override
    public void visit(MJNameSimple tree) {
        tree.getSimple_name().accept(this);
        tree.expressionvalue = tree.getSimple_name().expressionvalue;
    }

    @Override
    public void visit(MJNameQualified tree) {
        tree.getQualified_name().accept(this);
        tree.expressionvalue = tree.getQualified_name().expressionvalue;
    }

    @Override
    public void visit(MJSimpleName tree) {
        String name = tree.getI1();
        Symbol symbol = currentMethod.find(name);

        if (symbol == SymbolTable.NO_SYMBOL) {
            diagnostics.report(new SemanticErrors.NameNotFound(name, tree.getLine()));
        }

        tree.expressionvalue = new ExpressionValue(symbol);
    }

    @Override
    public void visit(MJThis tree) {
        if (currentMethod.isStatic()) {
            diagnostics.report(new SemanticErrors.NotAllowedHere("this", tree.getLine()));
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }

        Symbol symbol = currentMethod.find("this");
        tree.expressionvalue = new ExpressionValue(symbol);
    }

    @Override
    public void visit(MJClassInstanceCreation tree) {
        visitType(tree.getMJType());
        Type type = tree.getMJType().expressionvalue.type;
        if (type == SymbolTable.VOID) {
            tree.expressionvalue = tree.getMJType().expressionvalue;
            return;
        }
        if (!type.isClass()) {
            diagnostics.report(new SemanticErrors.ClassExpected(tree.getLine()));
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }

        tree.expressionvalue = new ExpressionValue(type.getOwner());
    }

    @Override
    public void visit(MJArrayCreation tree) {
        tree.getExpression().accept(this);
        Type expressionType = tree.getExpression().expressionvalue.type;
        if (!expressionType.assignable(SymbolTable.INT)) {
            diagnostics.report(new SemanticErrors.ArgumentsNotMatching(SymbolTable.INT, expressionType, tree.getExpression().getLine()));
        }

        visitType(tree.getMJType());
        Type type = tree.getMJType().expressionvalue.type;
        if (type == SymbolTable.VOID) {
            tree.expressionvalue = tree.getMJType().expressionvalue;
            return;
        }
        tree.expressionvalue = new ExpressionValue(Type.arrayOf(type));
    }

    @Override
    public void visit(MJQualifiedName tree) {
        String name = tree.getI2();

        tree.getName().accept(this);
        Type type = tree.getName().expressionvalue.type;

        tree.expressionvalue = accessField(type, name, tree);
    }

    @Override
    public void visit(MJFieldAccess tree) {
        String name = tree.getI2();

        tree.getPrimary().accept(this);
        Type type = tree.getPrimary().expressionvalue.type;

        tree.expressionvalue = accessField(type, name, tree);
    }

    private ExpressionValue accessField(
            Type qualifierType,
            String name,
            SyntaxNode tree
    ) {
        if (qualifierType == SymbolTable.VOID) {
            return new ExpressionValue(SymbolTable.VOID);
        }

        if (!qualifierType.isClass() && !qualifierType.isEnum()) {
            diagnostics.report(new SemanticErrors.CannotAccessFieldOnType(qualifierType, tree.getLine()));
            return new ExpressionValue(SymbolTable.VOID);
        }

        Accessible classType = (Accessible) qualifierType;
        Symbol symbol = classType.find(name);

        if (symbol == SymbolTable.NO_SYMBOL)
            diagnostics.report(new SemanticErrors.FieldNotFound(name, qualifierType, tree.getLine()));

        return new ExpressionValue(symbol);
    }

    @Override
    public void visit(MJLength tree) {
        tree.getName().accept(this);
        Type type = tree.getName().expressionvalue.type;
        tree.expressionvalue = accessArrayLength(type, tree);
    }

    @Override
    public void visit(MJLengthFieldAccess tree) {
        tree.getPrimary().accept(this);
        Type type = tree.getPrimary().expressionvalue.type;
        tree.expressionvalue = accessArrayLength(type, tree);
    }

    private ExpressionValue accessArrayLength(Type type, SyntaxNode tree) {
        if (type == SymbolTable.VOID) {
            return new ExpressionValue(SymbolTable.VOID);
        }

        if (!type.isArray()) {
            diagnostics.report(new SemanticErrors.CanOnlyBeUsedOnArrays("length", tree.getLine()));
            return new ExpressionValue(SymbolTable.VOID);
        }

        return new ExpressionValue(SymbolTable.INT);
    }

    @Override
    public void visit(MJMethodInvocation tree) {
        tree.getName().accept(this);
        Symbol symbol = tree.getName().expressionvalue.symbol;
        if (symbol == SymbolTable.NO_SYMBOL) {
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }

        if (!symbol.isCallable()) {
            diagnostics.report(new SemanticErrors.NotCallable(symbol.getName(), tree.getLine()));
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }
        MethodSymbol method = (MethodSymbol) symbol;

        List<Type> arguments = getArguments(tree.getArgument_list_opt());
        if (!method.matchesArguments(arguments)) {
            diagnostics.report(new SemanticErrors.ArgumentsNotMatching(method.formatParameters(), formatArguments(arguments), tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(method);
    }

    @Override
    public void visit(MJFindAny tree) {
        tree.getName().accept(this);
        Symbol symbol = tree.getName().expressionvalue.symbol;
        tree.expressionvalue = visitFindAny(symbol, tree.getExpression(), tree);
    }

    @Override
    public void visit(MJQualifiedFindAny tree) {
        tree.getPrimary().accept(this);
        Symbol symbol = tree.getPrimary().expressionvalue.symbol;
        tree.expressionvalue = visitFindAny(symbol, tree.getExpression(), tree);
    }

    private ExpressionValue visitFindAny(Symbol symbol, Expression expression, SyntaxNode tree) {
        if (symbol == SymbolTable.NO_SYMBOL) {
            return new ExpressionValue(SymbolTable.VOID);
        }

        if (!symbol.getSymbolType().isArray()) {
            diagnostics.report(new SemanticErrors.NotAnArray(symbol.getName(), tree.getLine()));
            return new ExpressionValue(SymbolTable.VOID);
        }

        expression.accept(this);
        Type expressionType = expression.expressionvalue.type;
        Type elementType = ((ArrayType) symbol.getSymbolType()).getElementType();
        if (!expressionType.assignable(elementType)) {
            diagnostics.report(new SemanticErrors.ArgumentsNotMatching(elementType.toString(), expressionType.toString(), tree.getLine()));
            return new ExpressionValue(SymbolTable.VOID);
        }

        return new ExpressionValue(SymbolTable.BOOL);
    }

    @Override
    public void visit(MJQualifiedMethodInvocation tree) {
        tree.getPrimary().accept(this);
        Type type = tree.getPrimary().expressionvalue.type;
        if (type == SymbolTable.VOID) {
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }

        if (!type.isClass()) {
            diagnostics.report(new SemanticErrors.CannotAccessFieldOnType(type, tree.getLine()));
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }
        String name = tree.getI2();
        ClassType classType = (ClassType) type;
        Symbol symbol = classType.find(name);

        if (symbol == SymbolTable.NO_SYMBOL) {
            diagnostics.report(new SemanticErrors.NameNotFound(name, tree.getLine()));
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }

        if (!symbol.isCallable()) {
            diagnostics.report(new SemanticErrors.NotCallable(symbol.getName(), tree.getLine()));
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }
        MethodSymbol method = (MethodSymbol) symbol;

        List<Type> arguments = getArguments(tree.getArgument_list_opt());
        if (!method.matchesArguments(arguments)) {
            diagnostics.report(new SemanticErrors.ArgumentsNotMatching(method.formatParameters(), formatArguments(arguments), tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(method);
    }

    private List<Type> arguments;

    private static String formatArguments(List<Type> arguments) {
        return arguments.stream()
                .map(Type::toString)
                .collect(java.util.stream.Collectors.joining(", ", "(", ")"));
    }

    private List<Type> getArguments(Argument_list_opt argumentList) {
        arguments = new ArrayList<>();

        argumentList.accept(this);

        return arguments;
    }

    @Override
    public void visit(MJArgument tree) {
        List<Type> arguments = this.arguments;
        tree.getExpression().accept(this);
        arguments.add(tree.getExpression().expressionvalue.type);
        this.arguments = arguments;
    }

    @Override
    public void visit(MJNextArgument tree) {
        List<Type> arguments = this.arguments;
        tree.getArgument_list().accept(this);
        tree.getExpression().accept(this);
        arguments.add(tree.getExpression().expressionvalue.type);
        this.arguments = arguments;
    }

    @Override
    public void visit(MJRead tree) {
        tree.getExpression().accept(this);
        Symbol symbol = tree.getExpression().expressionvalue.symbol;
        if (!symbol.isDataHolder()) {
            diagnostics.report(new SemanticErrors.VariableExpected(tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
    }

    @Override
    public void visit(MJPrint tree) {
        tree.getExpression().accept(this);
        Type type = tree.getExpression().expressionvalue.type;
        if (!type.isPrimitive()) {
            diagnostics.report(new SemanticErrors.CannotPrintNonPrimitiveType(tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
    }

    @Override
    public void visit(MJPrintConst tree) {
        tree.getExpression().accept(this);
        Type type = tree.getExpression().expressionvalue.type;
        if (!type.isPrimitive()) {
            diagnostics.report(new SemanticErrors.CannotPrintNonPrimitiveType(tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
    }

    @Override
    public void visit(MJArrayAccess tree) {
        tree.getExpression().accept(this);
        Type expressionType = tree.getExpression().expressionvalue.type;
        if (!expressionType.assignable(SymbolTable.INT)) {
            diagnostics.report(new SemanticErrors.ArgumentsNotMatching(SymbolTable.INT, expressionType, tree.getExpression().getLine()));
        }

        tree.getName().accept(this);
        Type type = tree.getName().expressionvalue.type;
        if (type == SymbolTable.VOID) {
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }

        if (!type.isArray()) {
            String name = tree.getName().expressionvalue.symbol.getName();
            diagnostics.report(new SemanticErrors.NotAnArray(name, tree.getLine()));
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }

        tree.expressionvalue = new ExpressionValue(Symbol.makeDummy(((ArrayType) type).getElementType()));
    }

    @Override
    public void visit(MJQualifiedArrayAccess tree) {
        tree.getExpression().accept(this);
        Type expressionType = tree.getExpression().expressionvalue.type;
        if (!expressionType.assignable(SymbolTable.INT)) {
            diagnostics.report(new SemanticErrors.ArgumentsNotMatching(SymbolTable.INT, expressionType, tree.getExpression().getLine()));
        }

        tree.getPrimary_no_new_array().accept(this);
        Type type = tree.getPrimary_no_new_array().expressionvalue.type;
        if (type == SymbolTable.VOID) {
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }

        if (!type.isArray()) {
            String name = tree.getPrimary_no_new_array().expressionvalue.symbol.getName();
            diagnostics.report(new SemanticErrors.NotAnArray(name, tree.getLine()));
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
        }

        tree.expressionvalue = new ExpressionValue(Symbol.makeDummy(((ArrayType) type).getElementType()));
    }

    @Override
    public void visit(MJPostincrement tree) {
        tree.expressionvalue = checkPostfixExpression(tree.getPostfix_expression());
    }

    @Override
    public void visit(MJPostdecrement tree) {
        tree.expressionvalue = checkPostfixExpression(tree.getPostfix_expression());
    }

    private ExpressionValue checkPostfixExpression(Postfix_expression tree) {
        tree.accept(this);
        Symbol variable = tree.expressionvalue.symbol;

        if (!variable.isDataHolder()) {
            diagnostics.report(new SemanticErrors.VariableExpected(tree.getLine()));
            return new ExpressionValue(SymbolTable.VOID);
        }

        if (!variable.getSymbolType().assignable(SymbolTable.INT)) {
            diagnostics.report(new SemanticErrors.ArgumentsNotMatching(SymbolTable.INT, variable.getSymbolType(), tree.getLine()));
            return new ExpressionValue(SymbolTable.VOID);
        }

        return new ExpressionValue(SymbolTable.INT);
    }

    @Override
    public void visit(MJNegation tree) {
        tree.getUnary_expression().accept(this);
        Type type = tree.getUnary_expression().expressionvalue.type;
        if (!type.assignable(SymbolTable.INT)) {
            diagnostics.report(new SemanticErrors.ArgumentsNotMatching(SymbolTable.INT, type, tree.getUnary_expression().getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.INT);
    }

    @Override
    public void visit(MJMultiplication tree) {
        tree.getMultiplicative_expression().accept(this);
        tree.getUnary_expression().accept(this);
        Type type1 = tree.getMultiplicative_expression().expressionvalue.type;
        Type type2 = tree.getUnary_expression().expressionvalue.type;
        if (!type1.assignable(SymbolTable.INT) || !type2.assignable(SymbolTable.INT)) {
            diagnostics.report(new SemanticErrors.OperatorCannotBeApplied("*", type1, type2, tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.INT);
    }

    @Override
    public void visit(MJDivision tree) {
        tree.getMultiplicative_expression().accept(this);
        tree.getUnary_expression().accept(this);
        Type type1 = tree.getMultiplicative_expression().expressionvalue.type;
        Type type2 = tree.getUnary_expression().expressionvalue.type;
        if (!type1.assignable(SymbolTable.INT) || !type2.assignable(SymbolTable.INT)) {
            diagnostics.report(new SemanticErrors.OperatorCannotBeApplied("/", type1, type2, tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.INT);
    }

    @Override
    public void visit(MJModulo tree) {
        tree.getMultiplicative_expression().accept(this);
        tree.getUnary_expression().accept(this);
        Type type1 = tree.getMultiplicative_expression().expressionvalue.type;
        Type type2 = tree.getUnary_expression().expressionvalue.type;
        if (!type1.assignable(SymbolTable.INT) || !type2.assignable(SymbolTable.INT)) {
            diagnostics.report(new SemanticErrors.OperatorCannotBeApplied("%", type1, type2, tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.INT);
    }

    @Override
    public void visit(MJAddition tree) {
        tree.getAdditive_expression().accept(this);
        tree.getMultiplicative_expression().accept(this);
        Type type1 = tree.getAdditive_expression().expressionvalue.type;
        Type type2 = tree.getMultiplicative_expression().expressionvalue.type;
        if (!type1.assignable(SymbolTable.INT) || !type2.assignable(SymbolTable.INT)) {
            diagnostics.report(new SemanticErrors.OperatorCannotBeApplied("+", type1, type2, tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.INT);
    }

    @Override
    public void visit(MJSubtraction tree) {
        tree.getAdditive_expression().accept(this);
        tree.getMultiplicative_expression().accept(this);
        Type type1 = tree.getAdditive_expression().expressionvalue.type;
        Type type2 = tree.getMultiplicative_expression().expressionvalue.type;
        if (!type1.assignable(SymbolTable.INT) || !type2.assignable(SymbolTable.INT)) {
            diagnostics.report(new SemanticErrors.OperatorCannotBeApplied("-", type1, type2, tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.INT);
    }

    @Override
    public void visit(MJLessThan tree) {
        tree.getRelational_expression().accept(this);
        tree.getAdditive_expression().accept(this);
        Type type1 = tree.getRelational_expression().expressionvalue.type;
        Type type2 = tree.getAdditive_expression().expressionvalue.type;
        if (!type1.assignable(SymbolTable.INT) || !type2.assignable(SymbolTable.INT)) {
            diagnostics.report(new SemanticErrors.OperatorCannotBeApplied("<", type1, type2, tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
    }

    @Override
    public void visit(MJGreaterThan tree) {
        tree.getRelational_expression().accept(this);
        tree.getAdditive_expression().accept(this);
        Type type1 = tree.getRelational_expression().expressionvalue.type;
        Type type2 = tree.getAdditive_expression().expressionvalue.type;
        if (!type1.assignable(SymbolTable.INT) || !type2.assignable(SymbolTable.INT)) {
            diagnostics.report(new SemanticErrors.OperatorCannotBeApplied(">", type1, type2, tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
    }

    @Override
    public void visit(MJLessThanOrEqualTo tree) {
        tree.getRelational_expression().accept(this);
        tree.getAdditive_expression().accept(this);
        Type type1 = tree.getRelational_expression().expressionvalue.type;
        Type type2 = tree.getAdditive_expression().expressionvalue.type;
        if (!type1.assignable(SymbolTable.INT) || !type2.assignable(SymbolTable.INT)) {
            diagnostics.report(new SemanticErrors.OperatorCannotBeApplied("<=", type1, type2, tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
    }

    @Override
    public void visit(MJGreaterThanOrEqualTo tree) {
        tree.getRelational_expression().accept(this);
        tree.getAdditive_expression().accept(this);
        Type type1 = tree.getRelational_expression().expressionvalue.type;
        Type type2 = tree.getAdditive_expression().expressionvalue.type;
        if (!type1.assignable(SymbolTable.INT) || !type2.assignable(SymbolTable.INT)) {
            diagnostics.report(new SemanticErrors.OperatorCannotBeApplied(">=", type1, type2, tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
    }

    @Override
    public void visit(MJEqual tree) {
        tree.getEquality_expression().accept(this);
        tree.getRelational_expression().accept(this);
        Type type1 = tree.getEquality_expression().expressionvalue.type;
        Type type2 = tree.getEquality_expression().expressionvalue.type;
        if (!type1.assignable(type2) || !type2.assignable(type1)) {
            diagnostics.report(new SemanticErrors.OperatorCannotBeApplied("==", type1, type2, tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
    }

    @Override
    public void visit(MJNotEqual tree) {
        tree.getEquality_expression().accept(this);
        tree.getRelational_expression().accept(this);
        Type type1 = tree.getEquality_expression().expressionvalue.type;
        Type type2 = tree.getEquality_expression().expressionvalue.type;
        if (!type1.assignable(type2) || !type2.assignable(type1)) {
            diagnostics.report(new SemanticErrors.OperatorCannotBeApplied("!=", type1, type2, tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
    }

    @Override
    public void visit(MJConjunction tree) {
        tree.getConditional_and_expression().accept(this);
        tree.getEquality_expression().accept(this);
        Type type1 = tree.getConditional_and_expression().expressionvalue.type;
        Type type2 = tree.getEquality_expression().expressionvalue.type;
        if (type1 != SymbolTable.BOOL || type2 != SymbolTable.BOOL) {
            diagnostics.report(new SemanticErrors.OperatorCannotBeApplied("&&", type1, type2, tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
    }

    @Override
    public void visit(MJDisjunction tree) {
        tree.getConditional_or_expression().accept(this);
        tree.getConditional_and_expression().accept(this);
        Type type1 = tree.getConditional_and_expression().expressionvalue.type;
        Type type2 = tree.getConditional_or_expression().expressionvalue.type;
        if (type1 != SymbolTable.BOOL || type2 != SymbolTable.BOOL) {
            diagnostics.report(new SemanticErrors.OperatorCannotBeApplied("||", type1, type2, tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
    }

    @Override
    public void visit(MJTernaryOperation tree) {
        tree.getConditional_or_expression().accept(this);
        Type conditionType = tree.getConditional_or_expression().expressionvalue.type;
        if (conditionType != SymbolTable.BOOL) {
            diagnostics.report(new SemanticErrors.ArgumentsNotMatching(SymbolTable.BOOL, conditionType, tree.getConditional_or_expression().getLine()));
        }

        tree.getExpression().accept(this);
        tree.getConditional_expression().accept(this);
        Type trueType = tree.getExpression().expressionvalue.type;
        Type falseType = tree.getConditional_expression().expressionvalue.type;
        if (!trueType.assignable(falseType) || !falseType.assignable(trueType)) {
            diagnostics.report(new SemanticErrors.TernaryOperatorTypesMismatch(trueType, falseType, tree.getLine()));
        }
        tree.expressionvalue = new ExpressionValue(trueType);
    }

    @Override
    public void visit(MJAssignment tree) {
        tree.getLeft_hand_side().accept(this);
        Type left = tree.getLeft_hand_side().expressionvalue.type;
        if (left == SymbolTable.VOID) {
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }

        tree.getAssignment_expression().accept(this);
        Type right = tree.getAssignment_expression().expressionvalue.type;
        if (right == SymbolTable.VOID) {
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }

        if (!right.assignable(left)) {
            diagnostics.report(new SemanticErrors.NotAssignable(left, right, tree.getLine()));
        }
    }

    private void visitType(MJType tree) {
        String name = tree.getI1();
        Symbol found = currentMethod.find(name);

        if (found == SymbolTable.NO_SYMBOL) {
            diagnostics.report(new SemanticErrors.TypeNotFound(name, tree.getLine()));
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }

        if (!found.isType()) {
            diagnostics.report(new SemanticErrors.NotAType(name, tree.getLine()));
            tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
            return;
        }

        tree.expressionvalue = new ExpressionValue(found.getSymbolType());
    }

    @Override
    public void visit(MJNoExpression tree) {
        tree.expressionvalue = new ExpressionValue(SymbolTable.VOID);
    }

    // <editor-fold defaultstate="collapsed" desc="Simple traversal methods">

    @Override
    public void visit(MJConstLiteral tree) {
        tree.getConst_literal().accept(this);
        tree.expressionvalue = tree.getConst_literal().expressionvalue;
    }

    @Override
    public void visit(MJPrimaryNoNewArray tree) {
        tree.getPrimary_no_new_array().accept(this);
        tree.expressionvalue = tree.getPrimary_no_new_array().expressionvalue;
    }

    @Override
    public void visit(MJPrimaryClassInstanceCreationExpression tree) {
        tree.getClass_instance_creation_expression().accept(this);
        tree.expressionvalue = tree.getClass_instance_creation_expression().expressionvalue;
    }

    @Override
    public void visit(MJPrimaryArrayCreationExpression tree) {
        tree.getArray_creation_expression().accept(this);
        tree.expressionvalue = tree.getArray_creation_expression().expressionvalue;
    }

    @Override
    public void visit(MJPrimaryLiteral tree) {
        tree.getLiteral().accept(this);
        tree.expressionvalue = tree.getLiteral().expressionvalue;
    }

    @Override
    public void visit(MJParenthesisedExpression tree) {
        tree.getExpression().accept(this);
        tree.expressionvalue = tree.getExpression().expressionvalue;
    }

    @Override
    public void visit(MJPrimaryFieldAccess tree) {
        tree.getField_access().accept(this);
        tree.expressionvalue = tree.getField_access().expressionvalue;
    }

    @Override
    public void visit(MJPrimaryMethodInvocation tree) {
        tree.getMethod_invocation().accept(this);
        tree.expressionvalue = tree.getMethod_invocation().expressionvalue;
    }

    @Override
    public void visit(MJPrimaryArrayAccess tree) {
        tree.getArray_access().accept(this);
        tree.expressionvalue = tree.getArray_access().expressionvalue;
    }

    @Override
    public void visit(MJArgumentList tree) {
        tree.getArgument_list().accept(this);
        tree.expressionvalue = tree.getArgument_list().expressionvalue;
    }

    @Override
    public void visit(MJPrimaryExpression tree) {
        tree.getPrimary().accept(this);
        tree.expressionvalue = tree.getPrimary().expressionvalue;
    }

    @Override
    public void visit(MJNameExpression tree) {
        tree.getName().accept(this);
        tree.expressionvalue = tree.getName().expressionvalue;
    }

    @Override
    public void visit(MJPostincrementExpression tree) {
        tree.getPostincrement_expression().accept(this);
        tree.expressionvalue = tree.getPostincrement_expression().expressionvalue;
    }

    @Override
    public void visit(MJPostdecrementExpression tree) {
        tree.getPostdecrement_expression().accept(this);
        tree.expressionvalue = tree.getPostdecrement_expression().expressionvalue;
    }

    @Override
    public void visit(MJPostfixExpression tree) {
        tree.getPostfix_expression().accept(this);
        tree.expressionvalue = tree.getPostfix_expression().expressionvalue;
    }

    @Override
    public void visit(MJUnaryExpression tree) {
        tree.getUnary_expression().accept(this);
        tree.expressionvalue = tree.getUnary_expression().expressionvalue;
    }

    @Override
    public void visit(MJMultiplicativeExpression tree) {
        tree.getMultiplicative_expression().accept(this);
        tree.expressionvalue = tree.getMultiplicative_expression().expressionvalue;
    }

    @Override
    public void visit(MJAdditiveExpression tree) {
        tree.getAdditive_expression().accept(this);
        tree.expressionvalue = tree.getAdditive_expression().expressionvalue;
    }

    @Override
    public void visit(MJRelationalExpression tree) {
        tree.getRelational_expression().accept(this);
        tree.expressionvalue = tree.getRelational_expression().expressionvalue;
    }

    @Override
    public void visit(MJEqualityExpression tree) {
        tree.getEquality_expression().accept(this);
        tree.expressionvalue = tree.getEquality_expression().expressionvalue;
    }

    @Override
    public void visit(MJConditionalAndExpression tree) {
        tree.getConditional_and_expression().accept(this);
        tree.expressionvalue = tree.getConditional_and_expression().expressionvalue;
    }

    @Override
    public void visit(MJConditionalOrExpression tree) {
        tree.getConditional_or_expression().accept(this);
        tree.expressionvalue = tree.getConditional_or_expression().expressionvalue;
    }

    @Override
    public void visit(MJConditionalExpression tree) {
        tree.getConditional_expression().accept(this);
        tree.expressionvalue = tree.getConditional_expression().expressionvalue;
    }

    @Override
    public void visit(MJAssignmentExpression tree) {
        tree.getAssignment().accept(this);
        tree.expressionvalue = tree.getAssignment().expressionvalue;
    }

    @Override
    public void visit(MJLeftHandSideName tree) {
        tree.getName().accept(this);
        tree.expressionvalue = tree.getName().expressionvalue;
    }

    @Override
    public void visit(MJLeftHandSideFieldAccess tree) {
        tree.getField_access().accept(this);
        tree.expressionvalue = tree.getField_access().expressionvalue;
    }

    @Override
    public void visit(MJLeftHandSideArrayAccess tree) {
        tree.getArray_access().accept(this);
        tree.expressionvalue = tree.getArray_access().expressionvalue;
    }

    @Override
    public void visit(MJExpressionOption tree) {
        tree.getExpression().accept(this);
        tree.expressionvalue = tree.getExpression().expressionvalue;
    }

    @Override
    public void visit(MJExpression tree) {
        tree.getAssignment_expression().accept(this);
        tree.expressionvalue = tree.getAssignment_expression().expressionvalue;
    }

    // </editor-fold>
}
