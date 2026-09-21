// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJPrimaryArrayCreationExpression extends Primary {

    private Array_creation_expression array_creation_expression;

    public MJPrimaryArrayCreationExpression (Array_creation_expression array_creation_expression) {
        this.array_creation_expression=array_creation_expression;
        if(array_creation_expression!=null) array_creation_expression.setParent(this);
    }

    public Array_creation_expression getArray_creation_expression() {
        return array_creation_expression;
    }

    public void setArray_creation_expression(Array_creation_expression array_creation_expression) {
        this.array_creation_expression=array_creation_expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(array_creation_expression!=null) array_creation_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(array_creation_expression!=null) array_creation_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(array_creation_expression!=null) array_creation_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJPrimaryArrayCreationExpression(\n");

        if(array_creation_expression!=null)
            buffer.append(array_creation_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJPrimaryArrayCreationExpression]");
        return buffer.toString();
    }
}
