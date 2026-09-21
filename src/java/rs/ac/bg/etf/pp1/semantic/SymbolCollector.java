package rs.ac.bg.etf.pp1.semantic;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.logger.CompilerDiagnostics;
import rs.ac.bg.etf.pp1.symbols.Flags;
import rs.ac.bg.etf.pp1.symbols.Flags.Modifier;
import rs.ac.bg.etf.pp1.symbols.Symbol;
import rs.ac.bg.etf.pp1.symbols.Symbol.*;
import rs.ac.bg.etf.pp1.symbols.SymbolTable;
import rs.ac.bg.etf.pp1.symbols.Type;
import rs.ac.bg.etf.pp1.symbols.Type.*;
import rs.ac.bg.etf.pp1.util.Context;
import rs.ac.bg.etf.pp1.util.TreeVisitor;

public final class SymbolCollector extends TreeVisitor {

    private final CompilerDiagnostics diagnostics;
    private final SymbolTable table;

    private Flags flags;
    private Type type;

    public SymbolCollector(Context context) {
        diagnostics = CompilerDiagnostics.getInstance(context);
        table = SymbolTable.getInstance(context);
    }

    public void enterProgram(MJProgram tree) {
        String programName = tree.getI1();
        table.initiate(programName);

        tree.getStatic_declarations_opt().accept(this);
        tree.getStatic_methods_block().accept(this);

        tree.programsymbol = (ProgramSymbol) table.closeScope();
    }

    // Classes

    @Override
    public void visit(MJClassDeclaration tree) {
        getModifiers(tree.getModifiers_opt());
        checkClassModifiers(tree.getModifiers_opt());
        String name = tree.getI2();
        ClassType superType = getSuperType(tree.getSuper_opt());

        table.openClass(name, superType, flags);

        tree.getClass_body().accept(this);

        ClassSymbol clazz = (ClassSymbol) table.closeScope();
        tree.classsymbol = clazz;
        ClassType thisType = clazz.getThisType();

        if (thisType.getSuperType() == null) return;

        for (Symbol member : thisType.getClassMembers()) {
            Symbol superMember = thisType.getSuperType().find(member.getName());
            if (superMember == SymbolTable.NO_SYMBOL) continue;

            if (!member.isSameKind(superMember)) {
                diagnostics.report(new SemanticErrors.NameOverridesSuper(member, superMember, tree.getLine()));
                continue;
            }

            if (!member.isCallable()) continue;

            if (!((MethodSymbol) member).matchesSuper((MethodSymbol) superMember)) {
                diagnostics.report(new SemanticErrors.MethodDoesNotMatchSuper((MethodSymbol) member, tree.getLine()));
            }
        }

        if (clazz.getModifiers().has(Modifier.ABSTRACT)) return;

        for (Symbol superMethod : thisType.getSuperType().getClassMembers()) {
            if (!superMethod.isCallable()) continue;
            if (!superMethod.getModifiers().has(Modifier.ABSTRACT)) continue;
            Symbol thisMethod = thisType.findOwn(superMethod.getName());
            if (thisMethod != SymbolTable.NO_SYMBOL) continue;

            diagnostics.report(new SemanticErrors.AbstractMethodsNotImplemented(tree.getLine()));
        }
    }

    private ClassType getSuperType(Super_opt tree) {
        if (!(tree instanceof MJSuper))
            return null;

        Type type = getType(((MJSuper) tree).getMJType());
        if (type == SymbolTable.VOID)
            return null;

        if (!type.isClass()) {
            diagnostics.report(new SemanticErrors.CannotExtendFromType(type, tree.getLine()));
            return null;
        }

        return (ClassType) type;
    }

    // Fields

    @Override
    public void visit(MJFieldDeclaration tree) {
        getModifiers(tree.getModifiers_opt());
        type = getType(tree.getMJType());

        checkFieldModifiers(tree.getModifiers_opt());

        tree.getVariable_declarators().accept(this);
    }

    @Override
    public void visit(MJVariable tree) {
        if (flags.has(Modifier.CONST)) {
            diagnostics.report(new SemanticErrors.UninitializedConstant(tree.getLine()));
        }

        tree.getVariable_declarator_id().accept(this);
        VariableDeclarator declarator = tree.getVariable_declarator_id().variabledeclarator;
        if (table.findInScope(declarator.name) != SymbolTable.NO_SYMBOL) {
            diagnostics.report(new SemanticErrors.NameAlreadyDeclared(declarator.name, tree.getLine()));
            return;
        }

        if (declarator.isArray) {
            tree.dataholdersymbol = table.declareArray(declarator.name, type);
        } else {
            tree.dataholdersymbol = table.declareVariable(declarator.name, type);
        }
    }

    @Override
    public void visit(MJConstant tree) {
        if (!flags.has(Modifier.CONST))
            diagnostics.report(new SemanticErrors.InitializedVariable(tree.getLine()));

        tree.getVariable_declarator_id().accept(this);
        VariableDeclarator declarator = tree.getVariable_declarator_id().variabledeclarator;
        if (table.findInScope(declarator.name) != SymbolTable.NO_SYMBOL) {
            diagnostics.report(new SemanticErrors.NameAlreadyDeclared(declarator.name, tree.getLine()));
            return;
        }

        if (declarator.isArray) {
            diagnostics.report(new SemanticErrors.ConstantArray(tree.getLine()));
            tree.dataholdersymbol = table.declareArray(declarator.name, type);
            return;
        }

        tree.getConst_literal().accept(this);
        Type literalType = tree.getConst_literal().expressionvalue.type;
        if (literalType != type)
            diagnostics.report(new SemanticErrors.NotAssignable(literalType, type, tree.getLine()));

        int value = tree.getConst_literal().expressionvalue.value;

        tree.dataholdersymbol = table.declareConstant(declarator.name, type, value);
    }

    @Override
    public void visit(MJVariableName tree) {
        String name = tree.getI1();
        tree.variabledeclarator = new VariableDeclarator(name, false);
    }

    @Override
    public void visit(MJArrayName tree) {
        String name = tree.getI1();
        tree.variabledeclarator = new VariableDeclarator(name, true);
    }

    // Methods

    @Override
    public void visit(MJMethodDeclaration tree) {
        tree.getMethod_signature().accept(this);
        tree.getMethod_variables_opt().accept(this);

        MethodSymbol method = (MethodSymbol) table.closeScope();
        tree.methodsymbol = method;
        method.setBody(tree.getMethod_body());

        if (table.currentWritable() instanceof ProgramSymbol && method.getName().equals("main")) {
            method.setMain();
        }
    }

    @Override
    public void visit(MJTypeMethodSignature tree) {
        getModifiers(tree.getModifiers_opt());
        type = getType(tree.getMJType());
        String name = tree.getI3();

        declareMethod(name, type, tree.getFormal_parameters_opt(), tree);
    }

    @Override
    public void visit(MJVoidMethodSignature tree) {
        getModifiers(tree.getModifiers_opt());
        type = SymbolTable.VOID;
        String name = tree.getI2();
        declareMethod(name, type, tree.getFormal_parameters_opt(), tree);
    }

    private void declareMethod(
            String name,
            Type returnType,
            Formal_parameters_opt parametersTree,
            SyntaxNode tree
    ) {
        if (table.find(name) != SymbolTable.NO_SYMBOL) {
            diagnostics.report(new SemanticErrors.NameAlreadyDeclared(name, tree.getLine()));
            // to allow the semantic pass to continue, we still must create this method symbol,
            // though it won't be added to the table.
        }

        checkMethodModifiers(tree);

        table.openMethod(name, returnType, flags);

        parametersTree.accept(this);
    }

    @Override
    public void visit(MJParameter tree) {
        type = getType(tree.getMJType());
        tree.getVariable_declarator_id().accept(this);
        VariableDeclarator declarator = tree.getVariable_declarator_id().variabledeclarator;
        if (table.findInScope(declarator.name) != SymbolTable.NO_SYMBOL) {
            diagnostics.report(new SemanticErrors.NameAlreadyDeclared(declarator.name, tree.getLine()));
            return;
        }
        tree.parametersymbol = table.declareParameter(declarator.name, type, declarator.isArray);
    }

    // Literals

    @Override
    public void visit(MJConstLiteral tree) {
        tree.expressionvalue = tree.getConst_literal().expressionvalue;
    }

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

    // Modifiers

    @Override
    public void visit(MJConstModifier tree) {
        if (flags.has(Modifier.CONST)) {
            diagnostics.report(new SemanticErrors.RepeatedModifier("const", tree.getLine()));
        }
        flags.add(Modifier.CONST);
    }

    @Override
    public void visit(MJAbstractModifier tree) {
        if (flags.has(Modifier.ABSTRACT)) {
            diagnostics.report(new SemanticErrors.RepeatedModifier("abstract", tree.getLine()));
        }
        flags.add(Modifier.ABSTRACT);
    }

    private void getModifiers(SyntaxNode tree) {
        flags = new Flags();
        tree.childrenAccept(this);
    }

    private void checkClassModifiers(SyntaxNode tree) {
        for (Modifier flag : flags) {
            if (flag != Modifier.ABSTRACT) {
                diagnostics.report(new SemanticErrors.NotAllowedHere("Modifier '" + flag.name(), tree.getLine()));
                flags.remove(flag);
            }
        }
    }

    private void checkFieldModifiers(SyntaxNode tree) {
        for (Modifier flag : flags) {
            if (flag != Modifier.CONST) {
                diagnostics.report(new SemanticErrors.NotAllowedHere("Modifier '" + flag.name(), tree.getLine()));
                flags.remove(flag);
            }
        }

        if (!flags.has(Modifier.CONST))
            return;

        if (!(table.currentWritable() instanceof ProgramSymbol)) {
            diagnostics.report(new SemanticErrors.ConstantDeclaredOutsideGlobalScope(tree.getLine()));
            flags.remove(Modifier.CONST);
        }

        if (!type.isPrimitive()) {
            diagnostics.report(new SemanticErrors.ConstantNotPrimitive(tree.getLine()));
            flags.remove(Modifier.CONST);
        }
    }

    private void checkMethodModifiers(SyntaxNode tree) {
        for (Modifier flag : flags) if (flag != Modifier.ABSTRACT) {
            diagnostics.report(new SemanticErrors.NotAllowedHere("Modifier '" + flag.name(), tree.getLine()));
            flags.remove(flag);
        }

        if (!table.currentWritable().getModifiers().has(Modifier.ABSTRACT) && flags.has(Modifier.ABSTRACT)) {
            diagnostics.report(new SemanticErrors.NotAllowedHere("Modifier 'abstract'", tree.getLine()));
            flags.remove(Modifier.ABSTRACT);
        }
    }

    // Types

    private Type getType(MJType tree) {
        String name = tree.getI1();
        Symbol found = table.find(name);

        if (found == SymbolTable.NO_SYMBOL) {
            diagnostics.report(new SemanticErrors.TypeNotFound(name, tree.getLine()));
            return SymbolTable.VOID;
        }

        if (!found.isType()) {
            diagnostics.report(new SemanticErrors.NotAType(name, tree.getLine()));
            return SymbolTable.VOID;
        }

        return found.getSymbolType();
    }
}
