// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJMultiplicativeExpression extends Additive_expression {

    private Multiplicative_expression multiplicative_expression;

    public MJMultiplicativeExpression (Multiplicative_expression multiplicative_expression) {
        this.multiplicative_expression=multiplicative_expression;
        if(multiplicative_expression!=null) multiplicative_expression.setParent(this);
    }

    public Multiplicative_expression getMultiplicative_expression() {
        return multiplicative_expression;
    }

    public void setMultiplicative_expression(Multiplicative_expression multiplicative_expression) {
        this.multiplicative_expression=multiplicative_expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(multiplicative_expression!=null) multiplicative_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(multiplicative_expression!=null) multiplicative_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(multiplicative_expression!=null) multiplicative_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJMultiplicativeExpression(\n");

        if(multiplicative_expression!=null)
            buffer.append(multiplicative_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJMultiplicativeExpression]");
        return buffer.toString();
    }
}
