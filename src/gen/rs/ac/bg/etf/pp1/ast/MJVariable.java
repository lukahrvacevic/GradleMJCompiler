// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJVariable extends Variable_declarator {

    private Variable_declarator_id variable_declarator_id;

    public MJVariable (Variable_declarator_id variable_declarator_id) {
        this.variable_declarator_id=variable_declarator_id;
        if(variable_declarator_id!=null) variable_declarator_id.setParent(this);
    }

    public Variable_declarator_id getVariable_declarator_id() {
        return variable_declarator_id;
    }

    public void setVariable_declarator_id(Variable_declarator_id variable_declarator_id) {
        this.variable_declarator_id=variable_declarator_id;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(variable_declarator_id!=null) variable_declarator_id.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(variable_declarator_id!=null) variable_declarator_id.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(variable_declarator_id!=null) variable_declarator_id.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJVariable(\n");

        if(variable_declarator_id!=null)
            buffer.append(variable_declarator_id.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJVariable]");
        return buffer.toString();
    }
}
