package rs.ac.bg.etf.pp1.codegen;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.semantic.ExpressionValue;
import rs.ac.bg.etf.pp1.symbols.Symbol;
import rs.ac.bg.etf.pp1.symbols.Symbol.*;
import rs.ac.bg.etf.pp1.symbols.SymbolTable;
import rs.ac.bg.etf.pp1.symbols.Type;
import rs.ac.bg.etf.pp1.symbols.Type.*;
import rs.ac.bg.etf.pp1.util.TreeVisitor;


/**
 * This lowering phase lowers language sugar to constructs.
 * At the same time, it adds new variables to the symbol table if they are introduced.
 */
public class Lower extends TreeVisitor {

    static int nextTmp = 0;

    private String getNextName() {
        return "$tmp" + nextTmp++;
    }

    @Override
    public void visit(MJMap node) {
        SyntaxNode arrayDest = node.getLeft_hand_side();

        if (arrayDest instanceof MJLeftHandSideName) {
            arrayDest = ((MJLeftHandSideName) arrayDest).getName();
        } else if (arrayDest instanceof MJLeftHandSideFieldAccess) {
            arrayDest = ((MJLeftHandSideFieldAccess) arrayDest).getField_access();
        } else {
            throw new RuntimeException("Unexpected left hand side type");
        }

        transformMap(
                node,
                arrayDest,
                node.getName(),
                node.getI3(),
                (MJExpression) node.getExpression()
        );
    }

    @Override
    public void visit(MJQualifiedMap node) {
        SyntaxNode arrayDest = node.getLeft_hand_side();

        if (arrayDest instanceof MJLeftHandSideName) {
            arrayDest = ((MJLeftHandSideName) arrayDest).getName();
        } else if (arrayDest instanceof MJLeftHandSideFieldAccess) {
            arrayDest = ((MJLeftHandSideFieldAccess) arrayDest).getField_access();
        } else {
            throw new RuntimeException("Unexpected left hand side type");
        }

        transformMap(
                node,
                arrayDest,
                node.getPrimary(),
                node.getI3(),
                (MJExpression) node.getExpression()
        );
    }

    /**
     * Lowers
     * <pre>{@code
     *     intArrayDest = intArraySrc.map(it => expression(it))
     * }</pre>
     * to
     * <pre>{@code
     *     {
     *         tmp0 = intArraySrc.length;
     *         intArrayDest = new int[tmp0];
     *         for (tmp1 = 0; tmp1 < tmp0; tmp1++) {
     *             it = intArraySrc[tmp1];
     *             it = expression(it);
     *             intArrayDest[tmp1] = it;
     *         }
     *     }
     * }</pre>
     */
    private void transformMap(
            Map_statement original,
            SyntaxNode arrayDest,
            SyntaxNode arraySrc,
            String identifier,
            MJExpression expression
    ) {
        MethodSymbol method = getParentMethod(original);

        Symbol size = new VariableSymbol(getNextName(), SymbolTable.INT);
        method.addSymbol(size);
        Symbol index = new VariableSymbol(getNextName(), SymbolTable.INT);
        method.addSymbol(index);

        ArrayType arrayType = getArrayType(arraySrc);
        Type elementType = arrayType.getElementType();
        Symbol it = method.find(identifier);

        // size = src.length;
        Name sizeLHS = makeName(size);
        MJConditionalExpression srcLength = makeLengthExpression(arraySrc);
        MJStatementExpression statement0Expr = makeExpressionStatement(makeAssignment(sizeLHS, srcLength));
        MJBlockStatement statement0 = makeStatement(statement0Expr);

        MJBlock block = makeBlock(statement0);

        // dest = new int[tmp0];
        Name sizeArg = makeName(size);
        MJConditionalExpression newArray = makeNewArrayExpression(makeType(elementType), makeExpression(sizeArg));
        MJStatementExpression statement1Expr = makeExpressionStatement(makeAssignment(arrayDest, newArray));
        MJBlockStatement statement1 = makeStatement(statement1Expr);
        addStatementAfter(statement0, statement1);

        // index = 0;
        Name indexInitLHS = makeName(index);
        MJConditionalExpression zero = makeIntegerLiteral(0);
        Statement_expression init = makeStatementExpression(makeAssignment(indexInitLHS, zero));

        // index < size
        Name indexLeftCmp = makeName(index);
        Name sizeRightComp = makeName(size);
        MJLessThan condition = makeLessThan(indexLeftCmp, sizeRightComp);

        // index++
        Name indexInc = makeName(index);
        Statement_expression increment = makeStatementExpression(makePostincrement(indexInc));

        // it = intArraySrc[index];
        Name itLHS0 = makeName(it);
        Name indexSrcAccess = makeName(index);
        MJConditionalExpression srcAccess = makeArrayAccess(arraySrc, indexSrcAccess);
        MJStatementExpression forStatement0Expr = makeExpressionStatement(makeAssignment(itLHS0, srcAccess));
        MJBlockStatement forStatement0 = makeStatement(forStatement0Expr);

        MJBlock body = makeBlock(forStatement0);

        // it = expression(it);
        Name itLHS1 = makeName(it);
        MJStatementExpression forStatement1Expr = makeExpressionStatement(makeAssignment(itLHS1, expression.getAssignment_expression()));
        MJBlockStatement forStatement1 = makeStatement(forStatement1Expr);
        addStatementAfter(forStatement0, forStatement1);

        // intArrayDest[tmp1] = it;
        Name indexDestAccess = makeName(index);
        Array_access destAccess = accessArray(arrayDest, indexDestAccess);
        Name itRHS = makeName(it);
        MJStatementExpression forStatement2Expr = makeExpressionStatement(makeAssignment(destAccess, itRHS));
        MJBlockStatement forStatement2 = makeStatement(forStatement2Expr);
        addStatementAfter(forStatement1, forStatement2);

        MJFor forLoop = makeFor(init, condition, increment, body);
        MJBlockStatement statement2 = makeStatement(forLoop);
        addStatementAfter(statement1, statement2);

        replace(original, block);
    }

    // Utils

    private void addStatementAfter(MJBlockStatement after, MJBlockStatement statement) {
        Block_statements afterParent = (Block_statements) after.getParent();
        SyntaxNode afterParentParent = afterParent.getParent();

        MJNextBlockStatement new_ = new MJNextBlockStatement(afterParent, statement);

        if (afterParentParent instanceof MJBlockStatements) {
            ((MJBlockStatements) afterParentParent).setBlock_statements(new_);
        } else {
            ((MJNextBlockStatement) afterParentParent).setBlock_statements(new_);
        }

        new_.setParent(afterParentParent);
    }

    private void replace(Map_statement original, MJBlock block) {
        SyntaxNode parent = original.getParent().getParent();
        if (parent instanceof MJStatementWithoutTrailingSubstatement) {
            ((MJStatementWithoutTrailingSubstatement) parent)
                    .setStatement_without_trailing_substatement(new MJStatementBlock(block));
        } else if (parent instanceof MJStatementWithoutTrailingSubstatementNoShortIf) {
            ((MJStatementWithoutTrailingSubstatementNoShortIf) parent)
                    .setStatement_without_trailing_substatement(new MJStatementBlock(block));
        }
    }

    private MethodSymbol getParentMethod(SyntaxNode node) {
        while (node != null) {
            if (node instanceof MJMethodDeclaration)
                return ((MJMethodDeclaration) node).methodsymbol;
            node = node.getParent();
        }
        throw new IllegalStateException("No parent method found");
    }

    // Shortcuts

    private ArrayType getArrayType(SyntaxNode array) {
        if (array instanceof Primary)
            return (ArrayType) ((Primary) array).expressionvalue.type;
        if (array instanceof Name)
            return (ArrayType) ((Name) array).expressionvalue.type;
        throw new IllegalArgumentException("Invalid array type");
    }

    private MJConditionalExpression makeLengthExpression(SyntaxNode array) {
        Postfix_expression lengthExpression;
        if (array instanceof Primary) {
            lengthExpression = new MJPrimaryExpression(accessLength(array));
        } else if (array instanceof Name) {
            lengthExpression = new MJNameExpression(accessLength((Name) array));
        } else {
            throw new IllegalArgumentException("Invalid array type");
        }

        return (MJConditionalExpression) makeAssignmentExpression(lengthExpression);
    }

    private Primary accessLength(SyntaxNode node) {
        return makePrimary(makePrimaryNoNewArray(makeLengthFieldAccess(node)));
    }

    private MJConditionalExpression makeNewArrayExpression(MJType type, MJExpression expression) {
        return (MJConditionalExpression) makeAssignmentExpression(newArray(type, expression));
    }

    private Primary newArray(MJType type, MJExpression expression) {
        return makePrimary(makeArrayCreation(type, expression));
    }

    private MJConditionalExpression makeArrayAccess(SyntaxNode array, Name index) {
        if (array instanceof MJPrimaryNoNewArray) {
            array = ((MJPrimaryNoNewArray) array).getPrimary_no_new_array();
        }
        return (MJConditionalExpression) makeAssignmentExpression(makeAccessArrayPrimary(array, makeExpression(index)));
    }

    private Primary makeAccessArrayPrimary(SyntaxNode array, Expression index) {
        return makePrimary(makePrimaryNoNewArray(accessArray(array, index)));
    }

    // Types

    private MJType makeType(Type type) {
        MJType mjType = new MJType(type.toString());
        mjType.expressionvalue = new ExpressionValue(type);
        return mjType;
    }

    // Literals

    private Literal makeLiteral(SyntaxNode node) {
        if (node instanceof Literal) return (Literal) node;
        Const_literal constLiteral = makeConstLiteral(node);
        MJConstLiteral literal = new MJConstLiteral(constLiteral);
        literal.expressionvalue = constLiteral.expressionvalue;
        return literal;
    }

    private Const_literal makeConstLiteral(SyntaxNode node) {
        if (node instanceof Const_literal) return (Const_literal) node;
        throw new IllegalArgumentException("Unsupported literal type: " + node.getClass().getName());
    }

    private MJConditionalExpression makeIntegerLiteral(int value) {
        MJIntegerLiteral literal = new MJIntegerLiteral(value);
        literal.expressionvalue = new ExpressionValue(SymbolTable.INT, value);
        return (MJConditionalExpression) makeAssignmentExpression(
                makePrimary(makePrimaryNoNewArray(makeLiteral(literal)))
        );
    }

    private MJConditionalExpression makeBooleanLiteral(boolean value) {
        MJBooleanLiteral literal = new MJBooleanLiteral(value ? 1 : 0);
        literal.expressionvalue = new ExpressionValue(SymbolTable.BOOL, value ? 1 : 0);
        return (MJConditionalExpression) makeAssignmentExpression(
                makePrimary(makePrimaryNoNewArray(makeLiteral(literal)))
        );
    }

    private MJConditionalExpression makeCharacterLiteral(char value) {
        MJCharacterLiteral literal = new MJCharacterLiteral(value);
        literal.expressionvalue = new ExpressionValue(SymbolTable.CHAR, (int) value);
        return (MJConditionalExpression) makeAssignmentExpression(
                makePrimary(makePrimaryNoNewArray(makeLiteral(literal)))
        );
    }

    private MJConditionalExpression makeNullLiteral() {
        MJNullLiteral literal = new MJNullLiteral();
        literal.expressionvalue = new ExpressionValue(SymbolTable.NULL);
        return (MJConditionalExpression) makeAssignmentExpression(
                makePrimary(makePrimaryNoNewArray(literal))
        );
    }

    // Names

    private Name makeName(Symbol symbol) {
        MJSimpleName simpleName = new MJSimpleName(symbol.getName());
        simpleName.expressionvalue = new ExpressionValue(symbol);
        MJNameSimple name = new MJNameSimple(simpleName);
        name.expressionvalue = simpleName.expressionvalue;
        return name;
    }

    private Name accessLength(Name name) {
        MJLength length = new MJLength(name);
        length.expressionvalue = new ExpressionValue(SymbolTable.INT);
        MJNameQualified qualified = new MJNameQualified(length);
        qualified.expressionvalue = length.expressionvalue;
        return qualified;
    }

    /**
     * Builds a qualified {@code name.field} usable as a {@link Name}.
     */
    private Name makeQualifiedName(Name qualifier, String field, Symbol resolved) {
        MJQualifiedName qualified = new MJQualifiedName(qualifier, field);
        qualified.expressionvalue = new ExpressionValue(resolved);
        MJNameQualified name = new MJNameQualified(qualified);
        name.expressionvalue = qualified.expressionvalue;
        return name;
    }

    // Statements

    private MJBlock makeBlock(SyntaxNode node) {
        if (node instanceof MJBlock) return (MJBlock) node;
        return new MJBlock(new MJBlockStatements(makeBlockStatements(node)));
    }

    private Block_statements makeBlockStatements(SyntaxNode node) {
        if (node instanceof Block_statements) return (Block_statements) node;
        return new MJFirstBlockStatement(makeStatement(node));
    }

    private MJBlockStatement makeStatement(SyntaxNode node) {
        if (node instanceof MJBlockStatement) return (MJBlockStatement) node;
        if (node instanceof Statement) return new MJBlockStatement((Statement) node);
        if (node instanceof For_statement) return new MJBlockStatement(new MJForStatement((For_statement) node));
        if (node instanceof If_then_statement) return new MJBlockStatement(new MJIfThenStatement((If_then_statement) node));
        if (node instanceof If_then_else_statement) return new MJBlockStatement(new MJIfThenElseStatement((If_then_else_statement) node));
        return new MJBlockStatement(new MJStatementWithoutTrailingSubstatement(makeStatementWithoutTrailingSubstatement(node)));
    }

    private Statement_no_short_if makeStatementNoShortIf(SyntaxNode node) {
        if (node instanceof Statement_no_short_if) return (Statement_no_short_if) node;
        if (node instanceof For_statement_no_short_if) return new MJForStatementNoShortIf((For_statement_no_short_if) node);
        if (node instanceof If_then_else_statement_no_short_if) return new MJIfThenElseStatementNoShortIf((If_then_else_statement_no_short_if) node);
        return new MJStatementWithoutTrailingSubstatementNoShortIf(makeStatementWithoutTrailingSubstatement(node));
    }

    private Statement_without_trailing_substatement makeStatementWithoutTrailingSubstatement(SyntaxNode node) {
        if (node instanceof Statement_without_trailing_substatement) return (Statement_without_trailing_substatement) node;
        if (node instanceof Block) return new MJStatementBlock(makeBlock(node));
        if (node instanceof Expression_statement) return new MJExpressionStatement(makeExpressionStatement(node));
        if (node instanceof Empty_statement) return new MJEmptyStatement((Empty_statement) node);
        if (node instanceof Return_statement) return new MJReturnStatement((Return_statement) node);
        if (node instanceof Break_statement) return new MJBreakStatement((Break_statement) node);
        if (node instanceof Continue_statement) return new MJContinueStatement((Continue_statement) node);
        throw new IllegalArgumentException("Unexpected or unsupported node type: " + node.getClass().getName());
    }

    // If

    /**
     * Builds {@code if (condition) thenStatement}.
     */
    private MJIfThen makeIf(SyntaxNode condition, SyntaxNode thenStatement) {
        MJBlockStatement thenBlock = makeStatement(thenStatement);
        return new MJIfThen(makeIfCondition(condition), thenBlock.getStatement());
    }

    /**
     * Builds {@code if (condition) thenStatement else elseStatement}.
     */
    private MJIfThenElse makeIfElse(SyntaxNode condition, SyntaxNode thenStatement, SyntaxNode elseStatement) {
        MJBlockStatement elseBlock = makeStatement(elseStatement);
        return new MJIfThenElse(
                makeIfCondition(condition),
                makeStatementNoShortIf(thenStatement),
                elseBlock.getStatement()
        );
    }

    /**
     * Builds the no-short-if form for nesting inside another {@code if ... else ...}.
     */
    private MJIfThenElseNoShortIf makeIfElseNoShortIf(SyntaxNode condition, SyntaxNode thenStatement, SyntaxNode elseStatement) {
        return new MJIfThenElseNoShortIf(
                makeIfCondition(condition),
                makeStatementNoShortIf(thenStatement),
                makeStatementNoShortIf(elseStatement)
        );
    }

    private If_condition makeIfCondition(SyntaxNode node) {
        if (node instanceof If_condition) return (If_condition) node;
        return new MJIfCondition(makeExpression(node));
    }

    // Jumps / returns / empty

    private MJReturn makeReturn(SyntaxNode expression) {
        return new MJReturn(makeExpressionOpt(expression));
    }

    private MJReturn makeReturnVoid() {
        return new MJReturn(makeNoExpression());
    }

    private MJBreak makeBreak() {
        return new MJBreak();
    }

    private MJContinue makeContinue() {
        return new MJContinue();
    }

    private MJEmpty makeEmpty() {
        return new MJEmpty();
    }

    private MJStatementExpression makeExpressionStatement(SyntaxNode node) {
        if (node instanceof MJStatementExpression) return (MJStatementExpression) node;
        return new MJStatementExpression(makeStatementExpression(node));
    }

    private Statement_expression makeStatementExpression(SyntaxNode node) {
        if (node instanceof Statement_expression) return (Statement_expression) node;
        if (node instanceof MJAssignment) return new MJAssignmentStatementExpression((MJAssignment) node);
        if (node instanceof Postincrement_expression) return new MJPostincrementStatementExpression((Postincrement_expression) node);
        if (node instanceof Postdecrement_expression) return new MJPostdecrementStatementExpression((Postdecrement_expression) node);
        if (node instanceof Method_invocation) return new MJMethodInvocationStatementExpression((Method_invocation) node);
        if (node instanceof Class_instance_creation_expression) return new MJClassInstanceCreationStatementExpression((Class_instance_creation_expression) node);
        throw new IllegalArgumentException("Unexpected node type: " + node.getClass().getName());
    }

    private MJFor makeFor(
            SyntaxNode init,
            SyntaxNode condition,
            SyntaxNode update,
            MJBlock body
    ) {
        MJForInitOpt forInit = makeForInitOpt(init);
        Expression_opt forCondition = makeExpressionOpt(condition);
        MJForUpdateOpt forUpdate = makeForUpdateOpt(update);
        MJBlockStatement bodyStatement = makeStatement(makeStatementWithoutTrailingSubstatement(body));
        return new MJFor(forInit, forCondition, forUpdate, bodyStatement.getStatement());
    }

    private MJForInitOpt makeForInitOpt(SyntaxNode node) {
        if (node instanceof MJForInitOpt) return (MJForInitOpt) node;
        return new MJForInitOpt(makeForInit(node));
    }

    private MJForInit makeForInit(SyntaxNode node) {
        if (node instanceof MJForInit) return (MJForInit) node;
        return new MJForInit(new MJFirstStatementExpression(makeStatementExpression(node)));
    }

    private MJForUpdateOpt makeForUpdateOpt(SyntaxNode node) {
        if (node instanceof MJForUpdateOpt) return (MJForUpdateOpt) node;
        return new MJForUpdateOpt(makeForUpdate(node));
    }

    private MJForUpdate makeForUpdate(SyntaxNode node) {
        if (node instanceof MJForUpdate) return (MJForUpdate) node;
        return new MJForUpdate(new MJFirstStatementExpression(makeStatementExpression(node)));
    }

    // Expressions

    private Primary makePrimary(SyntaxNode node) {
        if (node instanceof Primary) return (Primary) node;
        if (node instanceof Primary_no_new_array) {
            MJPrimaryNoNewArray primary = new MJPrimaryNoNewArray((Primary_no_new_array) node);
            primary.expressionvalue = ((Primary_no_new_array) node).expressionvalue;
            return primary;
        }
        if (node instanceof Array_creation_expression) {
            MJPrimaryArrayCreationExpression primary = new MJPrimaryArrayCreationExpression((Array_creation_expression) node);
            primary.expressionvalue = ((Array_creation_expression) node).expressionvalue;
            return primary;
        }
        throw new IllegalArgumentException("Unexpected or unsupported node type: " + node.getClass().getName());
    }

    private Primary_no_new_array makePrimaryNoNewArray(SyntaxNode node) {
        if (node instanceof Primary_no_new_array) return (Primary_no_new_array) node;
        if (node instanceof Literal) {
            MJPrimaryLiteral primary = new MJPrimaryLiteral((Literal) node);
            primary.expressionvalue = ((Literal) node).expressionvalue;
            return primary;
        }
        if (node instanceof Field_access) {
            MJPrimaryFieldAccess primary = new MJPrimaryFieldAccess((Field_access) node);
            primary.expressionvalue = ((Field_access) node).expressionvalue;
            return primary;
        }
        if (node instanceof Array_access) {
            MJPrimaryArrayAccess primary = new MJPrimaryArrayAccess((Array_access) node);
            primary.expressionvalue = ((Array_access) node).expressionvalue;
            return primary;
        }
        throw new IllegalArgumentException("Unexpected or unsupported node type: " + node.getClass().getName());
    }

    private MJArrayCreation makeArrayCreation(MJType type, SyntaxNode expression) {
        MJArrayCreation arrayCreation = new MJArrayCreation(type, makeExpression(expression));
        arrayCreation.expressionvalue = new ExpressionValue(Type.arrayOf(type.expressionvalue.type));
        return arrayCreation;
    }

    private MJLengthFieldAccess makeLengthFieldAccess(SyntaxNode node) {
        MJLengthFieldAccess length = new MJLengthFieldAccess(makePrimary(node));
        length.expressionvalue = new ExpressionValue(SymbolTable.INT);
        return length;
    }

    private Array_access accessArray(SyntaxNode array, SyntaxNode index) {
        Type elementType;
        Array_access access;
        if (array instanceof Name) {
            elementType = ((ArrayType) ((Name) array).expressionvalue.type).getElementType();
            access = new MJArrayAccess((Name) array, makeExpression(index));
        } else if (array instanceof Primary_no_new_array) {
            elementType = ((ArrayType) ((Primary_no_new_array) array).expressionvalue.type).getElementType();
            access = new MJQualifiedArrayAccess((Primary_no_new_array) array, makeExpression(index));
        } else {
            throw new IllegalArgumentException("Unexpected node type: " + array.getClass().getName());
        }
        access.expressionvalue = new ExpressionValue(Symbol.makeDummy(elementType));
        return access;
    }

    private Postfix_expression makePostfixExpression(SyntaxNode node) {
        if (node instanceof Postfix_expression) return (Postfix_expression) node;
        if (node instanceof Primary) {
            MJPrimaryExpression postfix = new MJPrimaryExpression((Primary) node);
            postfix.expressionvalue = ((Primary) node).expressionvalue;
            return postfix;
        }
        if (node instanceof Name) {
            MJNameExpression postfix = new MJNameExpression((Name) node);
            postfix.expressionvalue = ((Name) node).expressionvalue;
            return postfix;
        }
        if (node instanceof Postincrement_expression) {
            MJPostincrementExpression postfix = new MJPostincrementExpression((Postincrement_expression) node);
            postfix.expressionvalue = ((Postincrement_expression) node).expressionvalue;
            return postfix;
        }
        if (node instanceof Postdecrement_expression) {
            MJPostdecrementExpression postfix = new MJPostdecrementExpression((Postdecrement_expression) node);
            postfix.expressionvalue = ((Postdecrement_expression) node).expressionvalue;
            return postfix;
        }
        throw new IllegalArgumentException("Unexpected node type: " + node.getClass().getName());
    }

    private MJPostincrement makePostincrement(SyntaxNode node) {
        if (node instanceof MJPostincrement) return (MJPostincrement) node;
        MJPostincrement increment = new MJPostincrement(makePostfixExpression(node));
        increment.expressionvalue = new ExpressionValue(SymbolTable.INT);
        return increment;
    }

    private MJPostdecrement makePostdecrement(SyntaxNode node) {
        if (node instanceof MJPostdecrement) return (MJPostdecrement) node;
        MJPostdecrement decrement = new MJPostdecrement(makePostfixExpression(node));
        decrement.expressionvalue = new ExpressionValue(SymbolTable.INT);
        return decrement;
    }

    // this / parenthesized / field access / method invocation

    private Primary_no_new_array makeThis(Type type) {
        MJThis thisNode = new MJThis();
        thisNode.expressionvalue = new ExpressionValue(type);
        return thisNode;
    }

    private Primary_no_new_array makeParenthesised(SyntaxNode expression) {
        MJExpression expr = makeExpression(expression);
        MJParenthesisedExpression paren = new MJParenthesisedExpression(expr);
        paren.expressionvalue = expr.expressionvalue;
        return paren;
    }

    private Field_access makeFieldAccess(SyntaxNode qualifier, String field, Symbol resolved) {
        MJFieldAccess access = new MJFieldAccess(makePrimary(qualifier), field);
        access.expressionvalue = new ExpressionValue(resolved);
        return access;
    }

    private Method_invocation makeMethodInvocation(Name name, Argument_list_opt arguments, Type returnType) {
        MJMethodInvocation invocation = new MJMethodInvocation(name, arguments);
        invocation.expressionvalue = new ExpressionValue(returnType);
        return invocation;
    }

    private Method_invocation makeQualifiedMethodInvocation(SyntaxNode qualifier, String method, Argument_list_opt arguments, Type returnType) {
        MJQualifiedMethodInvocation invocation = new MJQualifiedMethodInvocation(makePrimary(qualifier), method, arguments);
        invocation.expressionvalue = new ExpressionValue(returnType);
        return invocation;
    }

    // Argument lists

    private Argument_list_opt makeArgumentList(SyntaxNode... expressions) {
        if (expressions.length == 0) return new MJNoArgumentList();
        Argument_list list = new MJArgument(makeExpression(expressions[0]));
        for (int i = 1; i < expressions.length; i++) {
            list = new MJNextArgument(list, makeExpression(expressions[i]));
        }
        return new MJArgumentList(list);
    }

    private Unary_expression makeUnaryExpression(SyntaxNode node) {
        if (node instanceof Unary_expression) return (Unary_expression) node;
        Postfix_expression postfix = makePostfixExpression(node);
        MJPostfixExpression unary = new MJPostfixExpression(postfix);
        unary.expressionvalue = postfix.expressionvalue;
        return unary;
    }

    private Multiplicative_expression makeMultiplicativeExpression(SyntaxNode node) {
        if (node instanceof Multiplicative_expression) return (Multiplicative_expression) node;
        Unary_expression unary = makeUnaryExpression(node);
        MJUnaryExpression multiplicative = new MJUnaryExpression(unary);
        multiplicative.expressionvalue = unary.expressionvalue;
        return multiplicative;
    }

    private Additive_expression makeAdditiveExpression(SyntaxNode node) {
        if (node instanceof Additive_expression) return (Additive_expression) node;
        Multiplicative_expression multiplicative = makeMultiplicativeExpression(node);
        MJMultiplicativeExpression additive = new MJMultiplicativeExpression(multiplicative);
        additive.expressionvalue = multiplicative.expressionvalue;
        return additive;
    }

    private Relational_expression makeRelationalExpression(SyntaxNode node) {
        if (node instanceof Relational_expression) return (Relational_expression) node;
        Additive_expression additive = makeAdditiveExpression(node);
        MJAdditiveExpression relational = new MJAdditiveExpression(additive);
        relational.expressionvalue = additive.expressionvalue;
        return relational;
    }

    private MJLessThan makeLessThan(SyntaxNode left, SyntaxNode right) {
        MJLessThan lessThan = new MJLessThan(makeRelationalExpression(left), makeAdditiveExpression(right));
        lessThan.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
        return lessThan;
    }

    private MJLessThanOrEqualTo makeLessThanOrEqualTo(SyntaxNode left, SyntaxNode right) {
        MJLessThanOrEqualTo op = new MJLessThanOrEqualTo(makeRelationalExpression(left), makeAdditiveExpression(right));
        op.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
        return op;
    }

    private MJGreaterThan makeGreaterThan(SyntaxNode left, SyntaxNode right) {
        MJGreaterThan op = new MJGreaterThan(makeRelationalExpression(left), makeAdditiveExpression(right));
        op.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
        return op;
    }

    private MJGreaterThanOrEqualTo makeGreaterThanOrEqualTo(SyntaxNode left, SyntaxNode right) {
        MJGreaterThanOrEqualTo op = new MJGreaterThanOrEqualTo(makeRelationalExpression(left), makeAdditiveExpression(right));
        op.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
        return op;
    }

    // Arithmetic

    private MJAddition makeAddition(SyntaxNode left, SyntaxNode right) {
        MJAddition op = new MJAddition(makeAdditiveExpression(left), makeMultiplicativeExpression(right));
        op.expressionvalue = new ExpressionValue(SymbolTable.INT);
        return op;
    }

    private MJSubtraction makeSubtraction(SyntaxNode left, SyntaxNode right) {
        MJSubtraction op = new MJSubtraction(makeAdditiveExpression(left), makeMultiplicativeExpression(right));
        op.expressionvalue = new ExpressionValue(SymbolTable.INT);
        return op;
    }

    private MJMultiplication makeMultiplication(SyntaxNode left, SyntaxNode right) {
        MJMultiplication op = new MJMultiplication(makeMultiplicativeExpression(left), makeUnaryExpression(right));
        op.expressionvalue = new ExpressionValue(SymbolTable.INT);
        return op;
    }

    private MJDivision makeDivision(SyntaxNode left, SyntaxNode right) {
        MJDivision op = new MJDivision(makeMultiplicativeExpression(left), makeUnaryExpression(right));
        op.expressionvalue = new ExpressionValue(SymbolTable.INT);
        return op;
    }

    private MJModulo makeModulo(SyntaxNode left, SyntaxNode right) {
        MJModulo op = new MJModulo(makeMultiplicativeExpression(left), makeUnaryExpression(right));
        op.expressionvalue = new ExpressionValue(SymbolTable.INT);
        return op;
    }

    // Equality and logical

    private MJEqual makeEqual(SyntaxNode left, SyntaxNode right) {
        MJEqual op = new MJEqual(makeEqualityExpression(left), makeRelationalExpression(right));
        op.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
        return op;
    }

    private MJNotEqual makeNotEqual(SyntaxNode left, SyntaxNode right) {
        MJNotEqual op = new MJNotEqual(makeEqualityExpression(left), makeRelationalExpression(right));
        op.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
        return op;
    }

    private MJConjunction makeConjunction(SyntaxNode left, SyntaxNode right) {
        MJConjunction op = new MJConjunction(makeConditionalAndExpression(left), makeEqualityExpression(right));
        op.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
        return op;
    }

    private MJDisjunction makeDisjunction(SyntaxNode left, SyntaxNode right) {
        MJDisjunction op = new MJDisjunction(makeConditionalOrExpression(left), makeConditionalAndExpression(right));
        op.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
        return op;
    }

    // Unary

    private MJNegation makeNegation(SyntaxNode operand) {
        MJNegation neg = new MJNegation(makeUnaryExpression(operand));
        neg.expressionvalue = new ExpressionValue(SymbolTable.BOOL);
        return neg;
    }

    // Ternary

    private MJTernaryOperation makeTernary(SyntaxNode condition, SyntaxNode thenExpr, SyntaxNode elseExpr) {
        Conditional_or_expression cond = makeConditionalOrExpression(condition);
        MJExpression thenE = makeExpression(thenExpr);
        Conditional_expression elseE = makeConditionalExpression(elseExpr);
        MJTernaryOperation ternary = new MJTernaryOperation(cond, thenE, elseE);
        ternary.expressionvalue = thenE.expressionvalue;
        return ternary;
    }

    private Equality_expression makeEqualityExpression(SyntaxNode node) {
        if (node instanceof Equality_expression) return (Equality_expression) node;
        Relational_expression relational = makeRelationalExpression(node);
        MJRelationalExpression equality = new MJRelationalExpression(relational);
        equality.expressionvalue = relational.expressionvalue;
        return equality;
    }

    private Conditional_and_expression makeConditionalAndExpression(SyntaxNode node) {
        if (node instanceof Conditional_and_expression) return (Conditional_and_expression) node;
        Equality_expression equality = makeEqualityExpression(node);
        MJEqualityExpression conditionalAnd = new MJEqualityExpression(equality);
        conditionalAnd.expressionvalue = equality.expressionvalue;
        return conditionalAnd;
    }

    private Conditional_or_expression makeConditionalOrExpression(SyntaxNode node) {
        if (node instanceof Conditional_or_expression) return (Conditional_or_expression) node;
        Conditional_and_expression conditionalAnd = makeConditionalAndExpression(node);
        MJConditionalAndExpression conditionalOr = new MJConditionalAndExpression(conditionalAnd);
        conditionalOr.expressionvalue = conditionalAnd.expressionvalue;
        return conditionalOr;
    }

    private Conditional_expression makeConditionalExpression(SyntaxNode node) {
        if (node instanceof Conditional_expression) return (Conditional_expression) node;
        Conditional_or_expression conditionalOr = makeConditionalOrExpression(node);
        MJConditionalOrExpression conditional = new MJConditionalOrExpression(conditionalOr);
        conditional.expressionvalue = conditionalOr.expressionvalue;
        return conditional;
    }

    private Assignment_expression makeAssignmentExpression(SyntaxNode node) {
        if (node instanceof Assignment_expression) return (Assignment_expression) node;
        if (node instanceof Assignment) {
            MJAssignmentExpression assignment = new MJAssignmentExpression((Assignment) node);
            assignment.expressionvalue = ((Assignment) node).expressionvalue;
            return assignment;
        }
        Conditional_expression conditional = makeConditionalExpression(node);
        MJConditionalExpression assignmentExpr = new MJConditionalExpression(conditional);
        assignmentExpr.expressionvalue = conditional.expressionvalue;
        return assignmentExpr;
    }

    private MJAssignment makeAssignment(SyntaxNode lhs, SyntaxNode rhs) {
        return new MJAssignment(makeLeftHandSide(lhs), makeAssignmentExpression(rhs));
    }

    private Left_hand_side makeLeftHandSide(SyntaxNode node) {
        if (node instanceof Left_hand_side) return (Left_hand_side) node;
        if (node instanceof Name) {
            MJLeftHandSideName lhs = new MJLeftHandSideName((Name) node);
            lhs.expressionvalue = ((Name) node).expressionvalue;
            return lhs;
        }
        if (node instanceof Field_access) {
            MJLeftHandSideFieldAccess lhs = new MJLeftHandSideFieldAccess((Field_access) node);
            lhs.expressionvalue = ((Field_access) node).expressionvalue;
            return lhs;
        }
        if (node instanceof Array_access) {
            MJLeftHandSideArrayAccess lhs = new MJLeftHandSideArrayAccess((Array_access) node);
            lhs.expressionvalue = ((Array_access) node).expressionvalue;
            return lhs;
        }
        throw new IllegalArgumentException("Unexpected node type: " + node.getClass().getName());
    }

    private Expression_opt makeExpressionOpt(SyntaxNode node) {
        if (node == null) return makeNoExpression();
        if (node instanceof Expression_opt) return (Expression_opt) node;
        MJExpression expression = makeExpression(node);
        MJExpressionOption option = new MJExpressionOption(expression);
        option.expressionvalue = expression.expressionvalue;
        return option;
    }

    private Expression_opt makeNoExpression() {
        return new MJNoExpression();
    }

    private MJExpression makeExpression(SyntaxNode node) {
        if (node instanceof MJExpression) return (MJExpression) node;
        Assignment_expression assignment = makeAssignmentExpression(node);
        MJExpression expression = new MJExpression(assignment);
        expression.expressionvalue = assignment.expressionvalue;
        return expression;
    }

}
