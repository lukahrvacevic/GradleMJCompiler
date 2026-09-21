// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJExpressionStatement extends Statement_without_trailing_substatement {

    private Expression_statement expression_statement;

    public MJExpressionStatement (Expression_statement expression_statement) {
        this.expression_statement=expression_statement;
        if(expression_statement!=null) expression_statement.setParent(this);
    }

    public Expression_statement getExpression_statement() {
        return expression_statement;
    }

    public void setExpression_statement(Expression_statement expression_statement) {
        this.expression_statement=expression_statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(expression_statement!=null) expression_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(expression_statement!=null) expression_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(expression_statement!=null) expression_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJExpressionStatement(\n");

        if(expression_statement!=null)
            buffer.append(expression_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJExpressionStatement]");
        return buffer.toString();
    }
}
