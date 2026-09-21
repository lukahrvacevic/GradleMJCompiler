// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJTernaryOperation extends Conditional_expression {

    private Conditional_or_expression conditional_or_expression;
    private Expression expression;
    private Conditional_expression conditional_expression;

    public MJTernaryOperation (Conditional_or_expression conditional_or_expression, Expression expression, Conditional_expression conditional_expression) {
        this.conditional_or_expression=conditional_or_expression;
        if(conditional_or_expression!=null) conditional_or_expression.setParent(this);
        this.expression=expression;
        if(expression!=null) expression.setParent(this);
        this.conditional_expression=conditional_expression;
        if(conditional_expression!=null) conditional_expression.setParent(this);
    }

    public Conditional_or_expression getConditional_or_expression() {
        return conditional_or_expression;
    }

    public void setConditional_or_expression(Conditional_or_expression conditional_or_expression) {
        this.conditional_or_expression=conditional_or_expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression=expression;
    }

    public Conditional_expression getConditional_expression() {
        return conditional_expression;
    }

    public void setConditional_expression(Conditional_expression conditional_expression) {
        this.conditional_expression=conditional_expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(conditional_or_expression!=null) conditional_or_expression.accept(visitor);
        if(expression!=null) expression.accept(visitor);
        if(conditional_expression!=null) conditional_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(conditional_or_expression!=null) conditional_or_expression.traverseTopDown(visitor);
        if(expression!=null) expression.traverseTopDown(visitor);
        if(conditional_expression!=null) conditional_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(conditional_or_expression!=null) conditional_or_expression.traverseBottomUp(visitor);
        if(expression!=null) expression.traverseBottomUp(visitor);
        if(conditional_expression!=null) conditional_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJTernaryOperation(\n");

        if(conditional_or_expression!=null)
            buffer.append(conditional_or_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(expression!=null)
            buffer.append(expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(conditional_expression!=null)
            buffer.append(conditional_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJTernaryOperation]");
        return buffer.toString();
    }
}
