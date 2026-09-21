// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJConjunction extends Conditional_and_expression {

    private Conditional_and_expression conditional_and_expression;
    private Equality_expression equality_expression;

    public MJConjunction (Conditional_and_expression conditional_and_expression, Equality_expression equality_expression) {
        this.conditional_and_expression=conditional_and_expression;
        if(conditional_and_expression!=null) conditional_and_expression.setParent(this);
        this.equality_expression=equality_expression;
        if(equality_expression!=null) equality_expression.setParent(this);
    }

    public Conditional_and_expression getConditional_and_expression() {
        return conditional_and_expression;
    }

    public void setConditional_and_expression(Conditional_and_expression conditional_and_expression) {
        this.conditional_and_expression=conditional_and_expression;
    }

    public Equality_expression getEquality_expression() {
        return equality_expression;
    }

    public void setEquality_expression(Equality_expression equality_expression) {
        this.equality_expression=equality_expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(conditional_and_expression!=null) conditional_and_expression.accept(visitor);
        if(equality_expression!=null) equality_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(conditional_and_expression!=null) conditional_and_expression.traverseTopDown(visitor);
        if(equality_expression!=null) equality_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(conditional_and_expression!=null) conditional_and_expression.traverseBottomUp(visitor);
        if(equality_expression!=null) equality_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJConjunction(\n");

        if(conditional_and_expression!=null)
            buffer.append(conditional_and_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(equality_expression!=null)
            buffer.append(equality_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJConjunction]");
        return buffer.toString();
    }
}
