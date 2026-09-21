// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJForStatement extends Statement {

    private For_statement for_statement;

    public MJForStatement (For_statement for_statement) {
        this.for_statement=for_statement;
        if(for_statement!=null) for_statement.setParent(this);
    }

    public For_statement getFor_statement() {
        return for_statement;
    }

    public void setFor_statement(For_statement for_statement) {
        this.for_statement=for_statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(for_statement!=null) for_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(for_statement!=null) for_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(for_statement!=null) for_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJForStatement(\n");

        if(for_statement!=null)
            buffer.append(for_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJForStatement]");
        return buffer.toString();
    }
}
