// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJUnaryExpression extends Multiplicative_expression {

    private Unary_expression unary_expression;

    public MJUnaryExpression (Unary_expression unary_expression) {
        this.unary_expression=unary_expression;
        if(unary_expression!=null) unary_expression.setParent(this);
    }

    public Unary_expression getUnary_expression() {
        return unary_expression;
    }

    public void setUnary_expression(Unary_expression unary_expression) {
        this.unary_expression=unary_expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(unary_expression!=null) unary_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(unary_expression!=null) unary_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(unary_expression!=null) unary_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJUnaryExpression(\n");

        if(unary_expression!=null)
            buffer.append(unary_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJUnaryExpression]");
        return buffer.toString();
    }
}
