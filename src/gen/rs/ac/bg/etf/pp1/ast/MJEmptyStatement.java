// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJEmptyStatement extends Statement_without_trailing_substatement {

    private Empty_statement empty_statement;

    public MJEmptyStatement (Empty_statement empty_statement) {
        this.empty_statement=empty_statement;
        if(empty_statement!=null) empty_statement.setParent(this);
    }

    public Empty_statement getEmpty_statement() {
        return empty_statement;
    }

    public void setEmpty_statement(Empty_statement empty_statement) {
        this.empty_statement=empty_statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(empty_statement!=null) empty_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(empty_statement!=null) empty_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(empty_statement!=null) empty_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJEmptyStatement(\n");

        if(empty_statement!=null)
            buffer.append(empty_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJEmptyStatement]");
        return buffer.toString();
    }
}
