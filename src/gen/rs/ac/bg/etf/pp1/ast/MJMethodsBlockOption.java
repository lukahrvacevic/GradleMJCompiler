// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJMethodsBlockOption extends Methods_block_opt {

    private Methods_block methods_block;

    public MJMethodsBlockOption (Methods_block methods_block) {
        this.methods_block=methods_block;
        if(methods_block!=null) methods_block.setParent(this);
    }

    public Methods_block getMethods_block() {
        return methods_block;
    }

    public void setMethods_block(Methods_block methods_block) {
        this.methods_block=methods_block;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(methods_block!=null) methods_block.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(methods_block!=null) methods_block.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(methods_block!=null) methods_block.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJMethodsBlockOption(\n");

        if(methods_block!=null)
            buffer.append(methods_block.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJMethodsBlockOption]");
        return buffer.toString();
    }
}
