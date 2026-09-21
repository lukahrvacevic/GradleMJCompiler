// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJConditionalExpression extends Assignment_expression {

    private Conditional_expression conditional_expression;

    public MJConditionalExpression (Conditional_expression conditional_expression) {
        this.conditional_expression=conditional_expression;
        if(conditional_expression!=null) conditional_expression.setParent(this);
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
        if(conditional_expression!=null) conditional_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(conditional_expression!=null) conditional_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(conditional_expression!=null) conditional_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJConditionalExpression(\n");

        if(conditional_expression!=null)
            buffer.append(conditional_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJConditionalExpression]");
        return buffer.toString();
    }
}
