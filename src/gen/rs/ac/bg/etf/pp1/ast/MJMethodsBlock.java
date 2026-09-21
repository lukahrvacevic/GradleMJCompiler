// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJMethodsBlock extends Methods_block {

    private Method_declarations_opt method_declarations_opt;

    public MJMethodsBlock (Method_declarations_opt method_declarations_opt) {
        this.method_declarations_opt=method_declarations_opt;
        if(method_declarations_opt!=null) method_declarations_opt.setParent(this);
    }

    public Method_declarations_opt getMethod_declarations_opt() {
        return method_declarations_opt;
    }

    public void setMethod_declarations_opt(Method_declarations_opt method_declarations_opt) {
        this.method_declarations_opt=method_declarations_opt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(method_declarations_opt!=null) method_declarations_opt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_declarations_opt!=null) method_declarations_opt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_declarations_opt!=null) method_declarations_opt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJMethodsBlock(\n");

        if(method_declarations_opt!=null)
            buffer.append(method_declarations_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJMethodsBlock]");
        return buffer.toString();
    }
}
