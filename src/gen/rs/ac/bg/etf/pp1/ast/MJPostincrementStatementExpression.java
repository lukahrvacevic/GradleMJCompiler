// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJPostincrementStatementExpression extends Statement_expression {

    private Postincrement_expression postincrement_expression;

    public MJPostincrementStatementExpression (Postincrement_expression postincrement_expression) {
        this.postincrement_expression=postincrement_expression;
        if(postincrement_expression!=null) postincrement_expression.setParent(this);
    }

    public Postincrement_expression getPostincrement_expression() {
        return postincrement_expression;
    }

    public void setPostincrement_expression(Postincrement_expression postincrement_expression) {
        this.postincrement_expression=postincrement_expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(postincrement_expression!=null) postincrement_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(postincrement_expression!=null) postincrement_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(postincrement_expression!=null) postincrement_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJPostincrementStatementExpression(\n");

        if(postincrement_expression!=null)
            buffer.append(postincrement_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJPostincrementStatementExpression]");
        return buffer.toString();
    }
}
