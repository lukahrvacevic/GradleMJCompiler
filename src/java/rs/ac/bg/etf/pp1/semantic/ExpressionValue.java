package rs.ac.bg.etf.pp1.semantic;

import rs.ac.bg.etf.pp1.symbols.Symbol;
import rs.ac.bg.etf.pp1.symbols.SymbolTable;
import rs.ac.bg.etf.pp1.symbols.Type;

public final class ExpressionValue {
    public final Type type;
    public final Symbol symbol; // optional; for names and fields.
    public final boolean isConstant;
    public final int value;

    public ExpressionValue(Type type) {
        this.type = type;
        this.symbol = SymbolTable.NO_SYMBOL;
        this.isConstant = false;
        this.value = 0;
    }

    public ExpressionValue(Type type, int value) {
        this.type = type;
        this.symbol = SymbolTable.NO_SYMBOL;
        this.isConstant = true;
        this.value = value;
    }

    public ExpressionValue(Symbol symbol) {
        this.type = symbol.getSymbolType();
        this.symbol = symbol;
        this.isConstant = false;
        this.value = 0;
    }
}
