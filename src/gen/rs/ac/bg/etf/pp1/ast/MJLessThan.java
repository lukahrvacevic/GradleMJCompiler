// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJLessThan extends Relational_expression {

    private Relational_expression relational_expression;
    private Additive_expression additive_expression;

    public MJLessThan (Relational_expression relational_expression, Additive_expression additive_expression) {
        this.relational_expression=relational_expression;
        if(relational_expression!=null) relational_expression.setParent(this);
        this.additive_expression=additive_expression;
        if(additive_expression!=null) additive_expression.setParent(this);
    }

    public Relational_expression getRelational_expression() {
        return relational_expression;
    }

    public void setRelational_expression(Relational_expression relational_expression) {
        this.relational_expression=relational_expression;
    }

    public Additive_expression getAdditive_expression() {
        return additive_expression;
    }

    public void setAdditive_expression(Additive_expression additive_expression) {
        this.additive_expression=additive_expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(relational_expression!=null) relational_expression.accept(visitor);
        if(additive_expression!=null) additive_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(relational_expression!=null) relational_expression.traverseTopDown(visitor);
        if(additive_expression!=null) additive_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(relational_expression!=null) relational_expression.traverseBottomUp(visitor);
        if(additive_expression!=null) additive_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJLessThan(\n");

        if(relational_expression!=null)
            buffer.append(relational_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(additive_expression!=null)
            buffer.append(additive_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJLessThan]");
        return buffer.toString();
    }
}
