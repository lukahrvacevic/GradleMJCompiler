// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJStatementWithoutTrailingSubstatementNoShortIf extends Statement_no_short_if {

    private Statement_without_trailing_substatement statement_without_trailing_substatement;

    public MJStatementWithoutTrailingSubstatementNoShortIf (Statement_without_trailing_substatement statement_without_trailing_substatement) {
        this.statement_without_trailing_substatement=statement_without_trailing_substatement;
        if(statement_without_trailing_substatement!=null) statement_without_trailing_substatement.setParent(this);
    }

    public Statement_without_trailing_substatement getStatement_without_trailing_substatement() {
        return statement_without_trailing_substatement;
    }

    public void setStatement_without_trailing_substatement(Statement_without_trailing_substatement statement_without_trailing_substatement) {
        this.statement_without_trailing_substatement=statement_without_trailing_substatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(statement_without_trailing_substatement!=null) statement_without_trailing_substatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(statement_without_trailing_substatement!=null) statement_without_trailing_substatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(statement_without_trailing_substatement!=null) statement_without_trailing_substatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJStatementWithoutTrailingSubstatementNoShortIf(\n");

        if(statement_without_trailing_substatement!=null)
            buffer.append(statement_without_trailing_substatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJStatementWithoutTrailingSubstatementNoShortIf]");
        return buffer.toString();
    }
}
