package rs.ac.bg.etf.pp1.semantic;

public final class VariableDeclarator {
    public final String name;
    public final boolean isArray;

    public VariableDeclarator(String name, boolean isArray) {
        this.name = name;
        this.isArray = isArray;
    }
}
