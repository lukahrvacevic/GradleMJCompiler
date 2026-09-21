// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJPrimaryMethodInvocation extends Primary_no_new_array {

    private Method_invocation method_invocation;

    public MJPrimaryMethodInvocation (Method_invocation method_invocation) {
        this.method_invocation=method_invocation;
        if(method_invocation!=null) method_invocation.setParent(this);
    }

    public Method_invocation getMethod_invocation() {
        return method_invocation;
    }

    public void setMethod_invocation(Method_invocation method_invocation) {
        this.method_invocation=method_invocation;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(method_invocation!=null) method_invocation.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_invocation!=null) method_invocation.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_invocation!=null) method_invocation.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJPrimaryMethodInvocation(\n");

        if(method_invocation!=null)
            buffer.append(method_invocation.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJPrimaryMethodInvocation]");
        return buffer.toString();
    }
}
