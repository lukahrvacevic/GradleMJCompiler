// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJStaticMethodsBlock extends Static_methods_block {

    private Static_method_declarations_opt static_method_declarations_opt;

    public MJStaticMethodsBlock (Static_method_declarations_opt static_method_declarations_opt) {
        this.static_method_declarations_opt=static_method_declarations_opt;
        if(static_method_declarations_opt!=null) static_method_declarations_opt.setParent(this);
    }

    public Static_method_declarations_opt getStatic_method_declarations_opt() {
        return static_method_declarations_opt;
    }

    public void setStatic_method_declarations_opt(Static_method_declarations_opt static_method_declarations_opt) {
        this.static_method_declarations_opt=static_method_declarations_opt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(static_method_declarations_opt!=null) static_method_declarations_opt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(static_method_declarations_opt!=null) static_method_declarations_opt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(static_method_declarations_opt!=null) static_method_declarations_opt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJStaticMethodsBlock(\n");

        if(static_method_declarations_opt!=null)
            buffer.append(static_method_declarations_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJStaticMethodsBlock]");
        return buffer.toString();
    }
}
