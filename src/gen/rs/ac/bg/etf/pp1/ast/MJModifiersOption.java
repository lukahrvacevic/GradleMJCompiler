// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJModifiersOption extends Modifiers_opt {

    private Modifiers modifiers;

    public MJModifiersOption (Modifiers modifiers) {
        this.modifiers=modifiers;
        if(modifiers!=null) modifiers.setParent(this);
    }

    public Modifiers getModifiers() {
        return modifiers;
    }

    public void setModifiers(Modifiers modifiers) {
        this.modifiers=modifiers;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(modifiers!=null) modifiers.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(modifiers!=null) modifiers.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(modifiers!=null) modifiers.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJModifiersOption(\n");

        if(modifiers!=null)
            buffer.append(modifiers.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJModifiersOption]");
        return buffer.toString();
    }
}
