// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJQualifiedArrayAccess extends Array_access {

    private Primary_no_new_array primary_no_new_array;
    private Expression expression;

    public MJQualifiedArrayAccess (Primary_no_new_array primary_no_new_array, Expression expression) {
        this.primary_no_new_array=primary_no_new_array;
        if(primary_no_new_array!=null) primary_no_new_array.setParent(this);
        this.expression=expression;
        if(expression!=null) expression.setParent(this);
    }

    public Primary_no_new_array getPrimary_no_new_array() {
        return primary_no_new_array;
    }

    public void setPrimary_no_new_array(Primary_no_new_array primary_no_new_array) {
        this.primary_no_new_array=primary_no_new_array;
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
        if(primary_no_new_array!=null) primary_no_new_array.accept(visitor);
        if(expression!=null) expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(primary_no_new_array!=null) primary_no_new_array.traverseTopDown(visitor);
        if(expression!=null) expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(primary_no_new_array!=null) primary_no_new_array.traverseBottomUp(visitor);
        if(expression!=null) expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJQualifiedArrayAccess(\n");

        if(primary_no_new_array!=null)
            buffer.append(primary_no_new_array.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(expression!=null)
            buffer.append(expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJQualifiedArrayAccess]");
        return buffer.toString();
    }
}
