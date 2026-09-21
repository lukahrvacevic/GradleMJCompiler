// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJEqualityExpression extends Conditional_and_expression {

    private Equality_expression equality_expression;

    public MJEqualityExpression (Equality_expression equality_expression) {
        this.equality_expression=equality_expression;
        if(equality_expression!=null) equality_expression.setParent(this);
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
        if(equality_expression!=null) equality_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(equality_expression!=null) equality_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(equality_expression!=null) equality_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJEqualityExpression(\n");

        if(equality_expression!=null)
            buffer.append(equality_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJEqualityExpression]");
        return buffer.toString();
    }
}
