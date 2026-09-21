// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJPostincrement extends Postincrement_expression {

    private Postfix_expression postfix_expression;

    public MJPostincrement (Postfix_expression postfix_expression) {
        this.postfix_expression=postfix_expression;
        if(postfix_expression!=null) postfix_expression.setParent(this);
    }

    public Postfix_expression getPostfix_expression() {
        return postfix_expression;
    }

    public void setPostfix_expression(Postfix_expression postfix_expression) {
        this.postfix_expression=postfix_expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(postfix_expression!=null) postfix_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(postfix_expression!=null) postfix_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(postfix_expression!=null) postfix_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJPostincrement(\n");

        if(postfix_expression!=null)
            buffer.append(postfix_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJPostincrement]");
        return buffer.toString();
    }
}
