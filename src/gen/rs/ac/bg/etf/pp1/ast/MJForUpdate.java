// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJForUpdate extends For_update {

    private Statement_expression_list statement_expression_list;

    public MJForUpdate (Statement_expression_list statement_expression_list) {
        this.statement_expression_list=statement_expression_list;
        if(statement_expression_list!=null) statement_expression_list.setParent(this);
    }

    public Statement_expression_list getStatement_expression_list() {
        return statement_expression_list;
    }

    public void setStatement_expression_list(Statement_expression_list statement_expression_list) {
        this.statement_expression_list=statement_expression_list;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(statement_expression_list!=null) statement_expression_list.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(statement_expression_list!=null) statement_expression_list.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(statement_expression_list!=null) statement_expression_list.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJForUpdate(\n");

        if(statement_expression_list!=null)
            buffer.append(statement_expression_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJForUpdate]");
        return buffer.toString();
    }
}
