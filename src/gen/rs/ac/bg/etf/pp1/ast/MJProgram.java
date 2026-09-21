// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJProgram extends Program {

    private String I1;
    private Static_declarations_opt static_declarations_opt;
    private Static_methods_block static_methods_block;

    public MJProgram (String I1, Static_declarations_opt static_declarations_opt, Static_methods_block static_methods_block) {
        this.I1=I1;
        this.static_declarations_opt=static_declarations_opt;
        if(static_declarations_opt!=null) static_declarations_opt.setParent(this);
        this.static_methods_block=static_methods_block;
        if(static_methods_block!=null) static_methods_block.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public Static_declarations_opt getStatic_declarations_opt() {
        return static_declarations_opt;
    }

    public void setStatic_declarations_opt(Static_declarations_opt static_declarations_opt) {
        this.static_declarations_opt=static_declarations_opt;
    }

    public Static_methods_block getStatic_methods_block() {
        return static_methods_block;
    }

    public void setStatic_methods_block(Static_methods_block static_methods_block) {
        this.static_methods_block=static_methods_block;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(static_declarations_opt!=null) static_declarations_opt.accept(visitor);
        if(static_methods_block!=null) static_methods_block.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(static_declarations_opt!=null) static_declarations_opt.traverseTopDown(visitor);
        if(static_methods_block!=null) static_methods_block.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(static_declarations_opt!=null) static_declarations_opt.traverseBottomUp(visitor);
        if(static_methods_block!=null) static_methods_block.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJProgram(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(static_declarations_opt!=null)
            buffer.append(static_declarations_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(static_methods_block!=null)
            buffer.append(static_methods_block.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJProgram]");
        return buffer.toString();
    }
}
