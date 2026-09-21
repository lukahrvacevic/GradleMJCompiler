// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJVoidMethodSignature extends Method_signature {

    private Modifiers_opt modifiers_opt;
    private String I2;
    private Formal_parameters_opt formal_parameters_opt;

    public MJVoidMethodSignature (Modifiers_opt modifiers_opt, String I2, Formal_parameters_opt formal_parameters_opt) {
        this.modifiers_opt=modifiers_opt;
        if(modifiers_opt!=null) modifiers_opt.setParent(this);
        this.I2=I2;
        this.formal_parameters_opt=formal_parameters_opt;
        if(formal_parameters_opt!=null) formal_parameters_opt.setParent(this);
    }

    public Modifiers_opt getModifiers_opt() {
        return modifiers_opt;
    }

    public void setModifiers_opt(Modifiers_opt modifiers_opt) {
        this.modifiers_opt=modifiers_opt;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public Formal_parameters_opt getFormal_parameters_opt() {
        return formal_parameters_opt;
    }

    public void setFormal_parameters_opt(Formal_parameters_opt formal_parameters_opt) {
        this.formal_parameters_opt=formal_parameters_opt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(modifiers_opt!=null) modifiers_opt.accept(visitor);
        if(formal_parameters_opt!=null) formal_parameters_opt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(modifiers_opt!=null) modifiers_opt.traverseTopDown(visitor);
        if(formal_parameters_opt!=null) formal_parameters_opt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(modifiers_opt!=null) modifiers_opt.traverseBottomUp(visitor);
        if(formal_parameters_opt!=null) formal_parameters_opt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJVoidMethodSignature(\n");

        if(modifiers_opt!=null)
            buffer.append(modifiers_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        if(formal_parameters_opt!=null)
            buffer.append(formal_parameters_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJVoidMethodSignature]");
        return buffer.toString();
    }
}
