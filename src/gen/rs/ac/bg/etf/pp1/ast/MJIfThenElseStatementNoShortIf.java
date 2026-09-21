// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJIfThenElseStatementNoShortIf extends Statement_no_short_if {

    private If_then_else_statement_no_short_if if_then_else_statement_no_short_if;

    public MJIfThenElseStatementNoShortIf (If_then_else_statement_no_short_if if_then_else_statement_no_short_if) {
        this.if_then_else_statement_no_short_if=if_then_else_statement_no_short_if;
        if(if_then_else_statement_no_short_if!=null) if_then_else_statement_no_short_if.setParent(this);
    }

    public If_then_else_statement_no_short_if getIf_then_else_statement_no_short_if() {
        return if_then_else_statement_no_short_if;
    }

    public void setIf_then_else_statement_no_short_if(If_then_else_statement_no_short_if if_then_else_statement_no_short_if) {
        this.if_then_else_statement_no_short_if=if_then_else_statement_no_short_if;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(if_then_else_statement_no_short_if!=null) if_then_else_statement_no_short_if.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(if_then_else_statement_no_short_if!=null) if_then_else_statement_no_short_if.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(if_then_else_statement_no_short_if!=null) if_then_else_statement_no_short_if.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJIfThenElseStatementNoShortIf(\n");

        if(if_then_else_statement_no_short_if!=null)
            buffer.append(if_then_else_statement_no_short_if.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJIfThenElseStatementNoShortIf]");
        return buffer.toString();
    }
}
