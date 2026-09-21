// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJPostdecrementExpression extends Postfix_expression {

    private Postdecrement_expression postdecrement_expression;

    public MJPostdecrementExpression (Postdecrement_expression postdecrement_expression) {
        this.postdecrement_expression=postdecrement_expression;
        if(postdecrement_expression!=null) postdecrement_expression.setParent(this);
    }

    public Postdecrement_expression getPostdecrement_expression() {
        return postdecrement_expression;
    }

    public void setPostdecrement_expression(Postdecrement_expression postdecrement_expression) {
        this.postdecrement_expression=postdecrement_expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(postdecrement_expression!=null) postdecrement_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(postdecrement_expression!=null) postdecrement_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(postdecrement_expression!=null) postdecrement_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJPostdecrementExpression(\n");

        if(postdecrement_expression!=null)
            buffer.append(postdecrement_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJPostdecrementExpression]");
        return buffer.toString();
    }
}
