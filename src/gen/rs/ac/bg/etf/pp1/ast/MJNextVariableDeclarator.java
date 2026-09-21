// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJNextVariableDeclarator extends Variable_declarators {

    private Variable_declarators variable_declarators;
    private Variable_declarator variable_declarator;

    public MJNextVariableDeclarator (Variable_declarators variable_declarators, Variable_declarator variable_declarator) {
        this.variable_declarators=variable_declarators;
        if(variable_declarators!=null) variable_declarators.setParent(this);
        this.variable_declarator=variable_declarator;
        if(variable_declarator!=null) variable_declarator.setParent(this);
    }

    public Variable_declarators getVariable_declarators() {
        return variable_declarators;
    }

    public void setVariable_declarators(Variable_declarators variable_declarators) {
        this.variable_declarators=variable_declarators;
    }

    public Variable_declarator getVariable_declarator() {
        return variable_declarator;
    }

    public void setVariable_declarator(Variable_declarator variable_declarator) {
        this.variable_declarator=variable_declarator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(variable_declarators!=null) variable_declarators.accept(visitor);
        if(variable_declarator!=null) variable_declarator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(variable_declarators!=null) variable_declarators.traverseTopDown(visitor);
        if(variable_declarator!=null) variable_declarator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(variable_declarators!=null) variable_declarators.traverseBottomUp(visitor);
        if(variable_declarator!=null) variable_declarator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJNextVariableDeclarator(\n");

        if(variable_declarators!=null)
            buffer.append(variable_declarators.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(variable_declarator!=null)
            buffer.append(variable_declarator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJNextVariableDeclarator]");
        return buffer.toString();
    }
}
