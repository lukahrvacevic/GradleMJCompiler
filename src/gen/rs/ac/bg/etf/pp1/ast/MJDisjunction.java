// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJDisjunction extends Conditional_or_expression {

    private Conditional_or_expression conditional_or_expression;
    private Conditional_and_expression conditional_and_expression;

    public MJDisjunction (Conditional_or_expression conditional_or_expression, Conditional_and_expression conditional_and_expression) {
        this.conditional_or_expression=conditional_or_expression;
        if(conditional_or_expression!=null) conditional_or_expression.setParent(this);
        this.conditional_and_expression=conditional_and_expression;
        if(conditional_and_expression!=null) conditional_and_expression.setParent(this);
    }

    public Conditional_or_expression getConditional_or_expression() {
        return conditional_or_expression;
    }

    public void setConditional_or_expression(Conditional_or_expression conditional_or_expression) {
        this.conditional_or_expression=conditional_or_expression;
    }

    public Conditional_and_expression getConditional_and_expression() {
        return conditional_and_expression;
    }

    public void setConditional_and_expression(Conditional_and_expression conditional_and_expression) {
        this.conditional_and_expression=conditional_and_expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(conditional_or_expression!=null) conditional_or_expression.accept(visitor);
        if(conditional_and_expression!=null) conditional_and_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(conditional_or_expression!=null) conditional_or_expression.traverseTopDown(visitor);
        if(conditional_and_expression!=null) conditional_and_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(conditional_or_expression!=null) conditional_or_expression.traverseBottomUp(visitor);
        if(conditional_and_expression!=null) conditional_and_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJDisjunction(\n");

        if(conditional_or_expression!=null)
            buffer.append(conditional_or_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(conditional_and_expression!=null)
            buffer.append(conditional_and_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJDisjunction]");
        return buffer.toString();
    }
}
