// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJMap extends Map_statement {

    private Left_hand_side left_hand_side;
    private Name name;
    private String I3;
    private Expression expression;

    public MJMap (Left_hand_side left_hand_side, Name name, String I3, Expression expression) {
        this.left_hand_side=left_hand_side;
        if(left_hand_side!=null) left_hand_side.setParent(this);
        this.name=name;
        if(name!=null) name.setParent(this);
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

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name=name;
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
        if(name!=null) name.accept(visitor);
        if(expression!=null) expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(left_hand_side!=null) left_hand_side.traverseTopDown(visitor);
        if(name!=null) name.traverseTopDown(visitor);
        if(expression!=null) expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(left_hand_side!=null) left_hand_side.traverseBottomUp(visitor);
        if(name!=null) name.traverseBottomUp(visitor);
        if(expression!=null) expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJMap(\n");

        if(left_hand_side!=null)
            buffer.append(left_hand_side.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(name!=null)
            buffer.append(name.toString("  "+tab));
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
        buffer.append(") [MJMap]");
        return buffer.toString();
    }
}
