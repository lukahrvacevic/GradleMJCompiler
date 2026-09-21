// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJNextStaticMethod extends Static_method_declarations {

    private Static_method_declarations static_method_declarations;
    private Static_method_declarations_element static_method_declarations_element;

    public MJNextStaticMethod (Static_method_declarations static_method_declarations, Static_method_declarations_element static_method_declarations_element) {
        this.static_method_declarations=static_method_declarations;
        if(static_method_declarations!=null) static_method_declarations.setParent(this);
        this.static_method_declarations_element=static_method_declarations_element;
        if(static_method_declarations_element!=null) static_method_declarations_element.setParent(this);
    }

    public Static_method_declarations getStatic_method_declarations() {
        return static_method_declarations;
    }

    public void setStatic_method_declarations(Static_method_declarations static_method_declarations) {
        this.static_method_declarations=static_method_declarations;
    }

    public Static_method_declarations_element getStatic_method_declarations_element() {
        return static_method_declarations_element;
    }

    public void setStatic_method_declarations_element(Static_method_declarations_element static_method_declarations_element) {
        this.static_method_declarations_element=static_method_declarations_element;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(static_method_declarations!=null) static_method_declarations.accept(visitor);
        if(static_method_declarations_element!=null) static_method_declarations_element.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(static_method_declarations!=null) static_method_declarations.traverseTopDown(visitor);
        if(static_method_declarations_element!=null) static_method_declarations_element.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(static_method_declarations!=null) static_method_declarations.traverseBottomUp(visitor);
        if(static_method_declarations_element!=null) static_method_declarations_element.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJNextStaticMethod(\n");

        if(static_method_declarations!=null)
            buffer.append(static_method_declarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(static_method_declarations_element!=null)
            buffer.append(static_method_declarations_element.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJNextStaticMethod]");
        return buffer.toString();
    }
}
