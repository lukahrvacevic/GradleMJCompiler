// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJExpression extends Expression {

    private Assignment_expression assignment_expression;

    public MJExpression (Assignment_expression assignment_expression) {
        this.assignment_expression=assignment_expression;
        if(assignment_expression!=null) assignment_expression.setParent(this);
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
        if(assignment_expression!=null) assignment_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(assignment_expression!=null) assignment_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(assignment_expression!=null) assignment_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJExpression(\n");

        if(assignment_expression!=null)
            buffer.append(assignment_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJExpression]");
        return buffer.toString();
    }
}
