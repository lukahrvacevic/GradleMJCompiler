// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJBlock extends Block {

    private Block_statements_opt block_statements_opt;

    public MJBlock (Block_statements_opt block_statements_opt) {
        this.block_statements_opt=block_statements_opt;
        if(block_statements_opt!=null) block_statements_opt.setParent(this);
    }

    public Block_statements_opt getBlock_statements_opt() {
        return block_statements_opt;
    }

    public void setBlock_statements_opt(Block_statements_opt block_statements_opt) {
        this.block_statements_opt=block_statements_opt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(block_statements_opt!=null) block_statements_opt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(block_statements_opt!=null) block_statements_opt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(block_statements_opt!=null) block_statements_opt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJBlock(\n");

        if(block_statements_opt!=null)
            buffer.append(block_statements_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJBlock]");
        return buffer.toString();
    }
}
