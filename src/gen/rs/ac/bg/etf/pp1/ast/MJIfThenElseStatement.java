// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJIfThenElseStatement extends Statement {

    private If_then_else_statement if_then_else_statement;

    public MJIfThenElseStatement (If_then_else_statement if_then_else_statement) {
        this.if_then_else_statement=if_then_else_statement;
        if(if_then_else_statement!=null) if_then_else_statement.setParent(this);
    }

    public If_then_else_statement getIf_then_else_statement() {
        return if_then_else_statement;
    }

    public void setIf_then_else_statement(If_then_else_statement if_then_else_statement) {
        this.if_then_else_statement=if_then_else_statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(if_then_else_statement!=null) if_then_else_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(if_then_else_statement!=null) if_then_else_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(if_then_else_statement!=null) if_then_else_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJIfThenElseStatement(\n");

        if(if_then_else_statement!=null)
            buffer.append(if_then_else_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJIfThenElseStatement]");
        return buffer.toString();
    }
}
