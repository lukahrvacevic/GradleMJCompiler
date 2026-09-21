// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJForStatementNoShortIf extends Statement_no_short_if {

    private For_statement_no_short_if for_statement_no_short_if;

    public MJForStatementNoShortIf (For_statement_no_short_if for_statement_no_short_if) {
        this.for_statement_no_short_if=for_statement_no_short_if;
        if(for_statement_no_short_if!=null) for_statement_no_short_if.setParent(this);
    }

    public For_statement_no_short_if getFor_statement_no_short_if() {
        return for_statement_no_short_if;
    }

    public void setFor_statement_no_short_if(For_statement_no_short_if for_statement_no_short_if) {
        this.for_statement_no_short_if=for_statement_no_short_if;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(for_statement_no_short_if!=null) for_statement_no_short_if.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(for_statement_no_short_if!=null) for_statement_no_short_if.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(for_statement_no_short_if!=null) for_statement_no_short_if.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJForStatementNoShortIf(\n");

        if(for_statement_no_short_if!=null)
            buffer.append(for_statement_no_short_if.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJForStatementNoShortIf]");
        return buffer.toString();
    }
}
