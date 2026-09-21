// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJArrayCreation extends Array_creation_expression {

    private MJType MJType;
    private Expression expression;

    public MJArrayCreation (MJType MJType, Expression expression) {
        this.MJType=MJType;
        if(MJType!=null) MJType.setParent(this);
        this.expression=expression;
        if(expression!=null) expression.setParent(this);
    }

    public MJType getMJType() {
        return MJType;
    }

    public void setMJType(MJType MJType) {
        this.MJType=MJType;
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
        if(MJType!=null) MJType.accept(visitor);
        if(expression!=null) expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MJType!=null) MJType.traverseTopDown(visitor);
        if(expression!=null) expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MJType!=null) MJType.traverseBottomUp(visitor);
        if(expression!=null) expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJArrayCreation(\n");

        if(MJType!=null)
            buffer.append(MJType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(expression!=null)
            buffer.append(expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJArrayCreation]");
        return buffer.toString();
    }
}
