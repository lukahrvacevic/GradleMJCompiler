// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJFirstMethodVariable extends Method_variables {

    private Method_variable method_variable;

    public MJFirstMethodVariable (Method_variable method_variable) {
        this.method_variable=method_variable;
        if(method_variable!=null) method_variable.setParent(this);
    }

    public Method_variable getMethod_variable() {
        return method_variable;
    }

    public void setMethod_variable(Method_variable method_variable) {
        this.method_variable=method_variable;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(method_variable!=null) method_variable.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_variable!=null) method_variable.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_variable!=null) method_variable.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJFirstMethodVariable(\n");

        if(method_variable!=null)
            buffer.append(method_variable.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJFirstMethodVariable]");
        return buffer.toString();
    }
}
