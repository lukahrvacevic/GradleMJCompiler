// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJPrimaryExpression extends Postfix_expression {

    private Primary primary;

    public MJPrimaryExpression (Primary primary) {
        this.primary=primary;
        if(primary!=null) primary.setParent(this);
    }

    public Primary getPrimary() {
        return primary;
    }

    public void setPrimary(Primary primary) {
        this.primary=primary;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(primary!=null) primary.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(primary!=null) primary.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(primary!=null) primary.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJPrimaryExpression(\n");

        if(primary!=null)
            buffer.append(primary.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJPrimaryExpression]");
        return buffer.toString();
    }
}
