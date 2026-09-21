// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJReturn extends Return_statement {

    private Expression_opt expression_opt;

    public MJReturn (Expression_opt expression_opt) {
        this.expression_opt=expression_opt;
        if(expression_opt!=null) expression_opt.setParent(this);
    }

    public Expression_opt getExpression_opt() {
        return expression_opt;
    }

    public void setExpression_opt(Expression_opt expression_opt) {
        this.expression_opt=expression_opt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(expression_opt!=null) expression_opt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(expression_opt!=null) expression_opt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(expression_opt!=null) expression_opt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJReturn(\n");

        if(expression_opt!=null)
            buffer.append(expression_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJReturn]");
        return buffer.toString();
    }
}
