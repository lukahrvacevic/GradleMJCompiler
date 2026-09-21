// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJReturnStatement extends Statement_without_trailing_substatement {

    private Return_statement return_statement;

    public MJReturnStatement (Return_statement return_statement) {
        this.return_statement=return_statement;
        if(return_statement!=null) return_statement.setParent(this);
    }

    public Return_statement getReturn_statement() {
        return return_statement;
    }

    public void setReturn_statement(Return_statement return_statement) {
        this.return_statement=return_statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(return_statement!=null) return_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(return_statement!=null) return_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(return_statement!=null) return_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJReturnStatement(\n");

        if(return_statement!=null)
            buffer.append(return_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJReturnStatement]");
        return buffer.toString();
    }
}
