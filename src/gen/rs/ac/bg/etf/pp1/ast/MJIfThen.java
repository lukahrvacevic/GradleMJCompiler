// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJIfThen extends If_then_statement {

    private If_condition if_condition;
    private Statement statement;

    public MJIfThen (If_condition if_condition, Statement statement) {
        this.if_condition=if_condition;
        if(if_condition!=null) if_condition.setParent(this);
        this.statement=statement;
        if(statement!=null) statement.setParent(this);
    }

    public If_condition getIf_condition() {
        return if_condition;
    }

    public void setIf_condition(If_condition if_condition) {
        this.if_condition=if_condition;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement=statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(if_condition!=null) if_condition.accept(visitor);
        if(statement!=null) statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(if_condition!=null) if_condition.traverseTopDown(visitor);
        if(statement!=null) statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(if_condition!=null) if_condition.traverseBottomUp(visitor);
        if(statement!=null) statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJIfThen(\n");

        if(if_condition!=null)
            buffer.append(if_condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(statement!=null)
            buffer.append(statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJIfThen]");
        return buffer.toString();
    }
}
