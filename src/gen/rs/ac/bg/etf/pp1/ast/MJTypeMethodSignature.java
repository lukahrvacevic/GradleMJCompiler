// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJTypeMethodSignature extends Method_signature {

    private Modifiers_opt modifiers_opt;
    private MJType MJType;
    private String I3;
    private Formal_parameters_opt formal_parameters_opt;

    public MJTypeMethodSignature (Modifiers_opt modifiers_opt, MJType MJType, String I3, Formal_parameters_opt formal_parameters_opt) {
        this.modifiers_opt=modifiers_opt;
        if(modifiers_opt!=null) modifiers_opt.setParent(this);
        this.MJType=MJType;
        if(MJType!=null) MJType.setParent(this);
        this.I3=I3;
        this.formal_parameters_opt=formal_parameters_opt;
        if(formal_parameters_opt!=null) formal_parameters_opt.setParent(this);
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

    public String getI3() {
        return I3;
    }

    public void setI3(String I3) {
        this.I3=I3;
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
        if(MJType!=null) MJType.accept(visitor);
        if(formal_parameters_opt!=null) formal_parameters_opt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(modifiers_opt!=null) modifiers_opt.traverseTopDown(visitor);
        if(MJType!=null) MJType.traverseTopDown(visitor);
        if(formal_parameters_opt!=null) formal_parameters_opt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(modifiers_opt!=null) modifiers_opt.traverseBottomUp(visitor);
        if(MJType!=null) MJType.traverseBottomUp(visitor);
        if(formal_parameters_opt!=null) formal_parameters_opt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJTypeMethodSignature(\n");

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

        buffer.append(" "+tab+I3);
        buffer.append("\n");

        if(formal_parameters_opt!=null)
            buffer.append(formal_parameters_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJTypeMethodSignature]");
        return buffer.toString();
    }
}
