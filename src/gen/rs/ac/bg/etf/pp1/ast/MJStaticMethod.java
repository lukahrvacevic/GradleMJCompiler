// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJStaticMethod extends Static_method_declarations {

    private Static_method_declarations_element static_method_declarations_element;

    public MJStaticMethod (Static_method_declarations_element static_method_declarations_element) {
        this.static_method_declarations_element=static_method_declarations_element;
        if(static_method_declarations_element!=null) static_method_declarations_element.setParent(this);
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
        if(static_method_declarations_element!=null) static_method_declarations_element.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(static_method_declarations_element!=null) static_method_declarations_element.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(static_method_declarations_element!=null) static_method_declarations_element.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJStaticMethod(\n");

        if(static_method_declarations_element!=null)
            buffer.append(static_method_declarations_element.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJStaticMethod]");
        return buffer.toString();
    }
}
