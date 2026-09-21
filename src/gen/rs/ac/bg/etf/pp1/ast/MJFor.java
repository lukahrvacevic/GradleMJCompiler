// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJFor extends For_statement {

    private For_init_opt for_init_opt;
    private Expression_opt expression_opt;
    private For_update_opt for_update_opt;
    private Statement statement;

    public MJFor (For_init_opt for_init_opt, Expression_opt expression_opt, For_update_opt for_update_opt, Statement statement) {
        this.for_init_opt=for_init_opt;
        if(for_init_opt!=null) for_init_opt.setParent(this);
        this.expression_opt=expression_opt;
        if(expression_opt!=null) expression_opt.setParent(this);
        this.for_update_opt=for_update_opt;
        if(for_update_opt!=null) for_update_opt.setParent(this);
        this.statement=statement;
        if(statement!=null) statement.setParent(this);
    }

    public For_init_opt getFor_init_opt() {
        return for_init_opt;
    }

    public void setFor_init_opt(For_init_opt for_init_opt) {
        this.for_init_opt=for_init_opt;
    }

    public Expression_opt getExpression_opt() {
        return expression_opt;
    }

    public void setExpression_opt(Expression_opt expression_opt) {
        this.expression_opt=expression_opt;
    }

    public For_update_opt getFor_update_opt() {
        return for_update_opt;
    }

    public void setFor_update_opt(For_update_opt for_update_opt) {
        this.for_update_opt=for_update_opt;
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
        if(for_init_opt!=null) for_init_opt.accept(visitor);
        if(expression_opt!=null) expression_opt.accept(visitor);
        if(for_update_opt!=null) for_update_opt.accept(visitor);
        if(statement!=null) statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(for_init_opt!=null) for_init_opt.traverseTopDown(visitor);
        if(expression_opt!=null) expression_opt.traverseTopDown(visitor);
        if(for_update_opt!=null) for_update_opt.traverseTopDown(visitor);
        if(statement!=null) statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(for_init_opt!=null) for_init_opt.traverseBottomUp(visitor);
        if(expression_opt!=null) expression_opt.traverseBottomUp(visitor);
        if(for_update_opt!=null) for_update_opt.traverseBottomUp(visitor);
        if(statement!=null) statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJFor(\n");

        if(for_init_opt!=null)
            buffer.append(for_init_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(expression_opt!=null)
            buffer.append(expression_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(for_update_opt!=null)
            buffer.append(for_update_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(statement!=null)
            buffer.append(statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJFor]");
        return buffer.toString();
    }
}
