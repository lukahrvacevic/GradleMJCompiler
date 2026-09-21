// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJAddition extends Additive_expression {

    private Additive_expression additive_expression;
    private Multiplicative_expression multiplicative_expression;

    public MJAddition (Additive_expression additive_expression, Multiplicative_expression multiplicative_expression) {
        this.additive_expression=additive_expression;
        if(additive_expression!=null) additive_expression.setParent(this);
        this.multiplicative_expression=multiplicative_expression;
        if(multiplicative_expression!=null) multiplicative_expression.setParent(this);
    }

    public Additive_expression getAdditive_expression() {
        return additive_expression;
    }

    public void setAdditive_expression(Additive_expression additive_expression) {
        this.additive_expression=additive_expression;
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
        if(additive_expression!=null) additive_expression.accept(visitor);
        if(multiplicative_expression!=null) multiplicative_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(additive_expression!=null) additive_expression.traverseTopDown(visitor);
        if(multiplicative_expression!=null) multiplicative_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(additive_expression!=null) additive_expression.traverseBottomUp(visitor);
        if(multiplicative_expression!=null) multiplicative_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJAddition(\n");

        if(additive_expression!=null)
            buffer.append(additive_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(multiplicative_expression!=null)
            buffer.append(multiplicative_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJAddition]");
        return buffer.toString();
    }
}
