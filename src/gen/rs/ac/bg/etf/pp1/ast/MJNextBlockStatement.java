// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJNextBlockStatement extends Block_statements {

    private Block_statements block_statements;
    private Block_statement block_statement;

    public MJNextBlockStatement (Block_statements block_statements, Block_statement block_statement) {
        this.block_statements=block_statements;
        if(block_statements!=null) block_statements.setParent(this);
        this.block_statement=block_statement;
        if(block_statement!=null) block_statement.setParent(this);
    }

    public Block_statements getBlock_statements() {
        return block_statements;
    }

    public void setBlock_statements(Block_statements block_statements) {
        this.block_statements=block_statements;
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
        if(block_statements!=null) block_statements.accept(visitor);
        if(block_statement!=null) block_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(block_statements!=null) block_statements.traverseTopDown(visitor);
        if(block_statement!=null) block_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(block_statements!=null) block_statements.traverseBottomUp(visitor);
        if(block_statement!=null) block_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJNextBlockStatement(\n");

        if(block_statements!=null)
            buffer.append(block_statements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(block_statement!=null)
            buffer.append(block_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJNextBlockStatement]");
        return buffer.toString();
    }
}
