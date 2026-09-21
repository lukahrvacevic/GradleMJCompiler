// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJRelationalExpression extends Equality_expression {

    private Relational_expression relational_expression;

    public MJRelationalExpression (Relational_expression relational_expression) {
        this.relational_expression=relational_expression;
        if(relational_expression!=null) relational_expression.setParent(this);
    }

    public Relational_expression getRelational_expression() {
        return relational_expression;
    }

    public void setRelational_expression(Relational_expression relational_expression) {
        this.relational_expression=relational_expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(relational_expression!=null) relational_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(relational_expression!=null) relational_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(relational_expression!=null) relational_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJRelationalExpression(\n");

        if(relational_expression!=null)
            buffer.append(relational_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJRelationalExpression]");
        return buffer.toString();
    }
}
