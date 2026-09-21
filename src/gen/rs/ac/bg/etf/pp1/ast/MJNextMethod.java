// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJNextMethod extends Method_declarations {

    private Method_declarations method_declarations;
    private Method_declarations_element method_declarations_element;

    public MJNextMethod (Method_declarations method_declarations, Method_declarations_element method_declarations_element) {
        this.method_declarations=method_declarations;
        if(method_declarations!=null) method_declarations.setParent(this);
        this.method_declarations_element=method_declarations_element;
        if(method_declarations_element!=null) method_declarations_element.setParent(this);
    }

    public Method_declarations getMethod_declarations() {
        return method_declarations;
    }

    public void setMethod_declarations(Method_declarations method_declarations) {
        this.method_declarations=method_declarations;
    }

    public Method_declarations_element getMethod_declarations_element() {
        return method_declarations_element;
    }

    public void setMethod_declarations_element(Method_declarations_element method_declarations_element) {
        this.method_declarations_element=method_declarations_element;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(method_declarations!=null) method_declarations.accept(visitor);
        if(method_declarations_element!=null) method_declarations_element.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_declarations!=null) method_declarations.traverseTopDown(visitor);
        if(method_declarations_element!=null) method_declarations_element.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_declarations!=null) method_declarations.traverseBottomUp(visitor);
        if(method_declarations_element!=null) method_declarations_element.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJNextMethod(\n");

        if(method_declarations!=null)
            buffer.append(method_declarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(method_declarations_element!=null)
            buffer.append(method_declarations_element.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJNextMethod]");
        return buffer.toString();
    }
}
