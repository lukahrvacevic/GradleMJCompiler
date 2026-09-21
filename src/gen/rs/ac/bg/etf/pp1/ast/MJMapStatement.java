// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJMapStatement extends Statement_without_trailing_substatement {

    private Map_statement map_statement;

    public MJMapStatement (Map_statement map_statement) {
        this.map_statement=map_statement;
        if(map_statement!=null) map_statement.setParent(this);
    }

    public Map_statement getMap_statement() {
        return map_statement;
    }

    public void setMap_statement(Map_statement map_statement) {
        this.map_statement=map_statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(map_statement!=null) map_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(map_statement!=null) map_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(map_statement!=null) map_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJMapStatement(\n");

        if(map_statement!=null)
            buffer.append(map_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJMapStatement]");
        return buffer.toString();
    }
}
