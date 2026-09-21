// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJFirstBlockStatement extends Block_statements {

    private Block_statement block_statement;

    public MJFirstBlockStatement (Block_statement block_statement) {
        this.block_statement=block_statement;
        if(block_statement!=null) block_statement.setParent(this);
    }

    public Block_statement getBlock_statement() {
        return block_statement;
    }

    public void setBlock_statement(Block_statement block_statement) {
        this.block_statement=block_statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(block_statement!=null) block_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(block_statement!=null) block_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(block_statement!=null) block_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJFirstBlockStatement(\n");

        if(block_statement!=null)
            buffer.append(block_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJFirstBlockStatement]");
        return buffer.toString();
    }
}
