// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJMethodVariables extends Method_variables_opt {

    private Method_variables method_variables;

    public MJMethodVariables (Method_variables method_variables) {
        this.method_variables=method_variables;
        if(method_variables!=null) method_variables.setParent(this);
    }

    public Method_variables getMethod_variables() {
        return method_variables;
    }

    public void setMethod_variables(Method_variables method_variables) {
        this.method_variables=method_variables;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(method_variables!=null) method_variables.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_variables!=null) method_variables.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_variables!=null) method_variables.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJMethodVariables(\n");

        if(method_variables!=null)
            buffer.append(method_variables.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJMethodVariables]");
        return buffer.toString();
    }
}
