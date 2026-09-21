// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJQualifiedMap extends Map_statement {

    private Left_hand_side left_hand_side;
    private Primary primary;
    private String I3;
    private Expression expression;

    public MJQualifiedMap (Left_hand_side left_hand_side, Primary primary, String I3, Expression expression) {
        this.left_hand_side=left_hand_side;
        if(left_hand_side!=null) left_hand_side.setParent(this);
        this.primary=primary;
        if(primary!=null) primary.setParent(this);
        this.I3=I3;
        this.expression=expression;
        if(expression!=null) expression.setParent(this);
    }

    public Left_hand_side getLeft_hand_side() {
        return left_hand_side;
    }

    public void setLeft_hand_side(Left_hand_side left_hand_side) {
        this.left_hand_side=left_hand_side;
    }

    public Primary getPrimary() {
        return primary;
    }

    public void setPrimary(Primary primary) {
        this.primary=primary;
    }

    public String getI3() {
        return I3;
    }

    public void setI3(String I3) {
        this.I3=I3;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression=expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(left_hand_side!=null) left_hand_side.accept(visitor);
        if(primary!=null) primary.accept(visitor);
        if(expression!=null) expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(left_hand_side!=null) left_hand_side.traverseTopDown(visitor);
        if(primary!=null) primary.traverseTopDown(visitor);
        if(expression!=null) expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(left_hand_side!=null) left_hand_side.traverseBottomUp(visitor);
        if(primary!=null) primary.traverseBottomUp(visitor);
        if(expression!=null) expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJQualifiedMap(\n");

        if(left_hand_side!=null)
            buffer.append(left_hand_side.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(primary!=null)
            buffer.append(primary.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I3);
        buffer.append("\n");

        if(expression!=null)
            buffer.append(expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJQualifiedMap]");
        return buffer.toString();
    }
}
