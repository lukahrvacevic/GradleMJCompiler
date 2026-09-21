package rs.ac.bg.etf.pp1.semantic;

import rs.ac.bg.etf.pp1.symbols.Symbol;
import rs.ac.bg.etf.pp1.symbols.Type;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public final class DumpTableVisitor extends SymbolTableVisitor {
    private static final String INDENT = "  ";
    private final StringBuilder output = new StringBuilder();
    private final StringBuilder indent = new StringBuilder();
    private boolean isDefinition = false;

    private void indent() {
        output.append(indent);
    }

    private void indentIn() {
        indent.append(INDENT);
    }

    private void indentOut() {
        indent.setLength(indent.length() - INDENT.length());
    }

    @Override
    public void visitObjNode(Obj obj) {
        Symbol symbol = (Symbol) obj;
        isDefinition = false;

        indent();
        output.append(symbol.getKindName()).append(" ").append(symbol.getName()).append(": ");

        if (symbol.isType()) isDefinition = true;

        Type type = symbol.getSymbolType();
        type.accept(this);

        output.append(" ").append(symbol.getAdr()).append(" ").append(symbol.getLevel()).append("\n");

        if (!(symbol instanceof Symbol.Writable)) return;

        indentIn();

        Symbol.Writable writable = (Symbol.Writable) symbol;
        writable.getScope().accept(this);

        indentOut();
    }

    @Override
    public void visitScopeNode(Scope scope) {
        for (Obj obj : scope.values()) {
            obj.accept(this);
        }
    }

    @Override
    public void visitStructNode(Struct struct) {
        Type type = (Type) struct;
        if (isDefinition && type.isClass()) {
            output.append("Class");
            if (((Type.ClassType) type).getSuperType() != null) {
                output.append(" extends ").append(((Type.ClassType) type).getSuperType().toString());
            }
        } else {
            output.append(type.toString());
        }
    }

    @Override
    public String getOutput() {
        return output.toString();
    }
}
