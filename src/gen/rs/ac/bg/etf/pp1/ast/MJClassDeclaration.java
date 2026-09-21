// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJClassDeclaration extends Class_delcaration {

    private Modifiers_opt modifiers_opt;
    private String I2;
    private Super_opt super_opt;
    private Class_body class_body;

    public MJClassDeclaration (Modifiers_opt modifiers_opt, String I2, Super_opt super_opt, Class_body class_body) {
        this.modifiers_opt=modifiers_opt;
        if(modifiers_opt!=null) modifiers_opt.setParent(this);
        this.I2=I2;
        this.super_opt=super_opt;
        if(super_opt!=null) super_opt.setParent(this);
        this.class_body=class_body;
        if(class_body!=null) class_body.setParent(this);
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

    public Super_opt getSuper_opt() {
        return super_opt;
    }

    public void setSuper_opt(Super_opt super_opt) {
        this.super_opt=super_opt;
    }

    public Class_body getClass_body() {
        return class_body;
    }

    public void setClass_body(Class_body class_body) {
        this.class_body=class_body;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(modifiers_opt!=null) modifiers_opt.accept(visitor);
        if(super_opt!=null) super_opt.accept(visitor);
        if(class_body!=null) class_body.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(modifiers_opt!=null) modifiers_opt.traverseTopDown(visitor);
        if(super_opt!=null) super_opt.traverseTopDown(visitor);
        if(class_body!=null) class_body.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(modifiers_opt!=null) modifiers_opt.traverseBottomUp(visitor);
        if(super_opt!=null) super_opt.traverseBottomUp(visitor);
        if(class_body!=null) class_body.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJClassDeclaration(\n");

        if(modifiers_opt!=null)
            buffer.append(modifiers_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        if(super_opt!=null)
            buffer.append(super_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(class_body!=null)
            buffer.append(class_body.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJClassDeclaration]");
        return buffer.toString();
    }
}
