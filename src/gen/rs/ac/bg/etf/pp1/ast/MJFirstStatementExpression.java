// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJFirstStatementExpression extends Statement_expression_list {

    private Statement_expression statement_expression;

    public MJFirstStatementExpression (Statement_expression statement_expression) {
        this.statement_expression=statement_expression;
        if(statement_expression!=null) statement_expression.setParent(this);
    }

    public Statement_expression getStatement_expression() {
        return statement_expression;
    }

    public void setStatement_expression(Statement_expression statement_expression) {
        this.statement_expression=statement_expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(statement_expression!=null) statement_expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(statement_expression!=null) statement_expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(statement_expression!=null) statement_expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJFirstStatementExpression(\n");

        if(statement_expression!=null)
            buffer.append(statement_expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJFirstStatementExpression]");
        return buffer.toString();
    }
}
