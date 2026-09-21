// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJAssignment extends Assignment {

    private Left_hand_side left_hand_side;
    private Assignment_expression assignment_expression;

    public MJAssignment (Left_hand_side left_hand_side, Assignment_expression assignment_expression) {
        this.left_hand_side=left_hand_side;
        if(left_hand_side!=null) left_hand_side.setParent(this);
        this.assignment_expression=assignment_expression;
        if(assignment_expression!=null) assignment_expression.setParent(this);
    }

    public Left_hand_side getLeft_hand_side() {
        return left_hand_side;
    }

    public void setLeft_hand_side(Left_hand_side left_hand_side) {
        this.left_hand_side=left_hand_side;
    }

    public Assignment_expression getAssignment_expression() {
        return assignment_expression;
    }

    public void setAssignment_expression(Assignment_expression assignment_expression) {
        this.assignment_expression=assignment_expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(left_hand_side!=null) left_hand_side.accept(visitor);
        if(assignment_expression!=null) assignment_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(left_hand_side!=null) left_hand_side.traverseTopDown(visitor);
        if(assignment_expression!=null) assignment_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(left_hand_side!=null) left_hand_side.traverseBottomUp(visitor);
        if(assignment_expression!=null) assignment_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJAssignment(\n");

        if(left_hand_side!=null)
            buffer.append(left_hand_side.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(assignment_expression!=null)
            buffer.append(assignment_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJAssignment]");
        return buffer.toString();
    }
}
