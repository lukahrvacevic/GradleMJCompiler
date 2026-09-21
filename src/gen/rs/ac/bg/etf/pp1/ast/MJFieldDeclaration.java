// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJFieldDeclaration extends Field_declaration {

    private Modifiers_opt modifiers_opt;
    private MJType MJType;
    private Variable_declarators variable_declarators;

    public MJFieldDeclaration (Modifiers_opt modifiers_opt, MJType MJType, Variable_declarators variable_declarators) {
        this.modifiers_opt=modifiers_opt;
        if(modifiers_opt!=null) modifiers_opt.setParent(this);
        this.MJType=MJType;
        if(MJType!=null) MJType.setParent(this);
        this.variable_declarators=variable_declarators;
        if(variable_declarators!=null) variable_declarators.setParent(this);
    }

    public Modifiers_opt getModifiers_opt() {
        return modifiers_opt;
    }

    public void setModifiers_opt(Modifiers_opt modifiers_opt) {
        this.modifiers_opt=modifiers_opt;
    }

    public MJType getMJType() {
        return MJType;
    }

    public void setMJType(MJType MJType) {
        this.MJType=MJType;
    }

    public Variable_declarators getVariable_declarators() {
        return variable_declarators;
    }

    public void setVariable_declarators(Variable_declarators variable_declarators) {
        this.variable_declarators=variable_declarators;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(modifiers_opt!=null) modifiers_opt.accept(visitor);
        if(MJType!=null) MJType.accept(visitor);
        if(variable_declarators!=null) variable_declarators.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(modifiers_opt!=null) modifiers_opt.traverseTopDown(visitor);
        if(MJType!=null) MJType.traverseTopDown(visitor);
        if(variable_declarators!=null) variable_declarators.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(modifiers_opt!=null) modifiers_opt.traverseBottomUp(visitor);
        if(MJType!=null) MJType.traverseBottomUp(visitor);
        if(variable_declarators!=null) variable_declarators.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJFieldDeclaration(\n");

        if(modifiers_opt!=null)
            buffer.append(modifiers_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MJType!=null)
            buffer.append(MJType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(variable_declarators!=null)
            buffer.append(variable_declarators.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJFieldDeclaration]");
        return buffer.toString();
    }
}
