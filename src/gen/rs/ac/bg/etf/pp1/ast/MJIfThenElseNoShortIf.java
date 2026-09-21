// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJIfThenElseNoShortIf extends If_then_else_statement_no_short_if {

    private If_condition if_condition;
    private Statement_no_short_if statement_no_short_if;
    private Statement_no_short_if statement_no_short_if1;

    public MJIfThenElseNoShortIf (If_condition if_condition, Statement_no_short_if statement_no_short_if, Statement_no_short_if statement_no_short_if1) {
        this.if_condition=if_condition;
        if(if_condition!=null) if_condition.setParent(this);
        this.statement_no_short_if=statement_no_short_if;
        if(statement_no_short_if!=null) statement_no_short_if.setParent(this);
        this.statement_no_short_if1=statement_no_short_if1;
        if(statement_no_short_if1!=null) statement_no_short_if1.setParent(this);
    }

    public If_condition getIf_condition() {
        return if_condition;
    }

    public void setIf_condition(If_condition if_condition) {
        this.if_condition=if_condition;
    }

    public Statement_no_short_if getStatement_no_short_if() {
        return statement_no_short_if;
    }

    public void setStatement_no_short_if(Statement_no_short_if statement_no_short_if) {
        this.statement_no_short_if=statement_no_short_if;
    }

    public Statement_no_short_if getStatement_no_short_if1() {
        return statement_no_short_if1;
    }

    public void setStatement_no_short_if1(Statement_no_short_if statement_no_short_if1) {
        this.statement_no_short_if1=statement_no_short_if1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(if_condition!=null) if_condition.accept(visitor);
        if(statement_no_short_if!=null) statement_no_short_if.accept(visitor);
        if(statement_no_short_if1!=null) statement_no_short_if1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(if_condition!=null) if_condition.traverseTopDown(visitor);
        if(statement_no_short_if!=null) statement_no_short_if.traverseTopDown(visitor);
        if(statement_no_short_if1!=null) statement_no_short_if1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(if_condition!=null) if_condition.traverseBottomUp(visitor);
        if(statement_no_short_if!=null) statement_no_short_if.traverseBottomUp(visitor);
        if(statement_no_short_if1!=null) statement_no_short_if1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJIfThenElseNoShortIf(\n");

        if(if_condition!=null)
            buffer.append(if_condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(statement_no_short_if!=null)
            buffer.append(statement_no_short_if.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(statement_no_short_if1!=null)
            buffer.append(statement_no_short_if1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJIfThenElseNoShortIf]");
        return buffer.toString();
    }
}
