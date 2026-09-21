// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJMethodsOption extends Method_declarations_opt {

    private Method_declarations method_declarations;

    public MJMethodsOption (Method_declarations method_declarations) {
        this.method_declarations=method_declarations;
        if(method_declarations!=null) method_declarations.setParent(this);
    }

    public Method_declarations getMethod_declarations() {
        return method_declarations;
    }

    public void setMethod_declarations(Method_declarations method_declarations) {
        this.method_declarations=method_declarations;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(method_declarations!=null) method_declarations.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_declarations!=null) method_declarations.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_declarations!=null) method_declarations.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJMethodsOption(\n");

        if(method_declarations!=null)
            buffer.append(method_declarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJMethodsOption]");
        return buffer.toString();
    }
}
