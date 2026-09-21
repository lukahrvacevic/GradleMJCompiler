// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJBlockStatements extends Block_statements_opt {

    private Block_statements block_statements;

    public MJBlockStatements (Block_statements block_statements) {
        this.block_statements=block_statements;
        if(block_statements!=null) block_statements.setParent(this);
    }

    public Block_statements getBlock_statements() {
        return block_statements;
    }

    public void setBlock_statements(Block_statements block_statements) {
        this.block_statements=block_statements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(block_statements!=null) block_statements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(block_statements!=null) block_statements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(block_statements!=null) block_statements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJBlockStatements(\n");

        if(block_statements!=null)
            buffer.append(block_statements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJBlockStatements]");
        return buffer.toString();
    }
}
