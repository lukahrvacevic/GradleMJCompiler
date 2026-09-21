// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJContinueStatement extends Statement_without_trailing_substatement {

    private Continue_statement continue_statement;

    public MJContinueStatement (Continue_statement continue_statement) {
        this.continue_statement=continue_statement;
        if(continue_statement!=null) continue_statement.setParent(this);
    }

    public Continue_statement getContinue_statement() {
        return continue_statement;
    }

    public void setContinue_statement(Continue_statement continue_statement) {
        this.continue_statement=continue_statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(continue_statement!=null) continue_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(continue_statement!=null) continue_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(continue_statement!=null) continue_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJContinueStatement(\n");

        if(continue_statement!=null)
            buffer.append(continue_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJContinueStatement]");
        return buffer.toString();
    }
}
