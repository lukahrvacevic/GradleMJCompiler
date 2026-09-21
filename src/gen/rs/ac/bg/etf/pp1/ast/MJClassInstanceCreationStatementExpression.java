// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJClassInstanceCreationStatementExpression extends Statement_expression {

    private Class_instance_creation_expression class_instance_creation_expression;

    public MJClassInstanceCreationStatementExpression (Class_instance_creation_expression class_instance_creation_expression) {
        this.class_instance_creation_expression=class_instance_creation_expression;
        if(class_instance_creation_expression!=null) class_instance_creation_expression.setParent(this);
    }

    public Class_instance_creation_expression getClass_instance_creation_expression() {
        return class_instance_creation_expression;
    }

    public void setClass_instance_creation_expression(Class_instance_creation_expression class_instance_creation_expression) {
        this.class_instance_creation_expression=class_instance_creation_expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(class_instance_creation_expression!=null) class_instance_creation_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(class_instance_creation_expression!=null) class_instance_creation_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(class_instance_creation_expression!=null) class_instance_creation_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJClassInstanceCreationStatementExpression(\n");

        if(class_instance_creation_expression!=null)
            buffer.append(class_instance_creation_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJClassInstanceCreationStatementExpression]");
        return buffer.toString();
    }
}
