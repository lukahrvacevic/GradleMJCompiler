// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJVariableDeclarator extends Variable_declarators {

    private Variable_declarator variable_declarator;

    public MJVariableDeclarator (Variable_declarator variable_declarator) {
        this.variable_declarator=variable_declarator;
        if(variable_declarator!=null) variable_declarator.setParent(this);
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
        if(variable_declarator!=null) variable_declarator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(variable_declarator!=null) variable_declarator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(variable_declarator!=null) variable_declarator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJVariableDeclarator(\n");

        if(variable_declarator!=null)
            buffer.append(variable_declarator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJVariableDeclarator]");
        return buffer.toString();
    }
}
