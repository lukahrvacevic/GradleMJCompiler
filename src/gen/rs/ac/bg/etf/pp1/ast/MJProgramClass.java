// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJProgramClass extends Static_declaration {

    private Class_delcaration class_delcaration;

    public MJProgramClass (Class_delcaration class_delcaration) {
        this.class_delcaration=class_delcaration;
        if(class_delcaration!=null) class_delcaration.setParent(this);
    }

    public Class_delcaration getClass_delcaration() {
        return class_delcaration;
    }

    public void setClass_delcaration(Class_delcaration class_delcaration) {
        this.class_delcaration=class_delcaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(class_delcaration!=null) class_delcaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(class_delcaration!=null) class_delcaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(class_delcaration!=null) class_delcaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJProgramClass(\n");

        if(class_delcaration!=null)
            buffer.append(class_delcaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJProgramClass]");
        return buffer.toString();
    }
}
