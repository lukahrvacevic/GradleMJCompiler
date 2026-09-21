// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJNextMethodVariable extends Method_variables {

    private Method_variables method_variables;
    private Method_variable method_variable;

    public MJNextMethodVariable (Method_variables method_variables, Method_variable method_variable) {
        this.method_variables=method_variables;
        if(method_variables!=null) method_variables.setParent(this);
        this.method_variable=method_variable;
        if(method_variable!=null) method_variable.setParent(this);
    }

    public Method_variables getMethod_variables() {
        return method_variables;
    }

    public void setMethod_variables(Method_variables method_variables) {
        this.method_variables=method_variables;
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
        if(method_variables!=null) method_variables.accept(visitor);
        if(method_variable!=null) method_variable.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_variables!=null) method_variables.traverseTopDown(visitor);
        if(method_variable!=null) method_variable.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_variables!=null) method_variables.traverseBottomUp(visitor);
        if(method_variable!=null) method_variable.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJNextMethodVariable(\n");

        if(method_variables!=null)
            buffer.append(method_variables.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(method_variable!=null)
            buffer.append(method_variable.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJNextMethodVariable]");
        return buffer.toString();
    }
}
