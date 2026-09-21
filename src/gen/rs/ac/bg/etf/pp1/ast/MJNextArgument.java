// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJNextArgument extends Argument_list {

    private Argument_list argument_list;
    private Expression expression;

    public MJNextArgument (Argument_list argument_list, Expression expression) {
        this.argument_list=argument_list;
        if(argument_list!=null) argument_list.setParent(this);
        this.expression=expression;
        if(expression!=null) expression.setParent(this);
    }

    public Argument_list getArgument_list() {
        return argument_list;
    }

    public void setArgument_list(Argument_list argument_list) {
        this.argument_list=argument_list;
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
        if(argument_list!=null) argument_list.accept(visitor);
        if(expression!=null) expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(argument_list!=null) argument_list.traverseTopDown(visitor);
        if(expression!=null) expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(argument_list!=null) argument_list.traverseBottomUp(visitor);
        if(expression!=null) expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJNextArgument(\n");

        if(argument_list!=null)
            buffer.append(argument_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(expression!=null)
            buffer.append(expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJNextArgument]");
        return buffer.toString();
    }
}
