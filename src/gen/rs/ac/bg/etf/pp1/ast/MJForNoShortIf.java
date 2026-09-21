// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJForNoShortIf extends For_statement_no_short_if {

    private For_init_opt for_init_opt;
    private Expression_opt expression_opt;
    private For_update_opt for_update_opt;
    private Statement_no_short_if statement_no_short_if;

    public MJForNoShortIf (For_init_opt for_init_opt, Expression_opt expression_opt, For_update_opt for_update_opt, Statement_no_short_if statement_no_short_if) {
        this.for_init_opt=for_init_opt;
        if(for_init_opt!=null) for_init_opt.setParent(this);
        this.expression_opt=expression_opt;
        if(expression_opt!=null) expression_opt.setParent(this);
        this.for_update_opt=for_update_opt;
        if(for_update_opt!=null) for_update_opt.setParent(this);
        this.statement_no_short_if=statement_no_short_if;
        if(statement_no_short_if!=null) statement_no_short_if.setParent(this);
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

    public Statement_no_short_if getStatement_no_short_if() {
        return statement_no_short_if;
    }

    public void setStatement_no_short_if(Statement_no_short_if statement_no_short_if) {
        this.statement_no_short_if=statement_no_short_if;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(for_init_opt!=null) for_init_opt.accept(visitor);
        if(expression_opt!=null) expression_opt.accept(visitor);
        if(for_update_opt!=null) for_update_opt.accept(visitor);
        if(statement_no_short_if!=null) statement_no_short_if.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(for_init_opt!=null) for_init_opt.traverseTopDown(visitor);
        if(expression_opt!=null) expression_opt.traverseTopDown(visitor);
        if(for_update_opt!=null) for_update_opt.traverseTopDown(visitor);
        if(statement_no_short_if!=null) statement_no_short_if.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(for_init_opt!=null) for_init_opt.traverseBottomUp(visitor);
        if(expression_opt!=null) expression_opt.traverseBottomUp(visitor);
        if(for_update_opt!=null) for_update_opt.traverseBottomUp(visitor);
        if(statement_no_short_if!=null) statement_no_short_if.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJForNoShortIf(\n");

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

        if(statement_no_short_if!=null)
            buffer.append(statement_no_short_if.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJForNoShortIf]");
        return buffer.toString();
    }
}
