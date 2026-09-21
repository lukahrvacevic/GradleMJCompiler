// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJNextModifier extends Modifiers {

    private Modifiers modifiers;
    private Modifier modifier;

    public MJNextModifier (Modifiers modifiers, Modifier modifier) {
        this.modifiers=modifiers;
        if(modifiers!=null) modifiers.setParent(this);
        this.modifier=modifier;
        if(modifier!=null) modifier.setParent(this);
    }

    public Modifiers getModifiers() {
        return modifiers;
    }

    public void setModifiers(Modifiers modifiers) {
        this.modifiers=modifiers;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public void setModifier(Modifier modifier) {
        this.modifier=modifier;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(modifiers!=null) modifiers.accept(visitor);
        if(modifier!=null) modifier.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(modifiers!=null) modifiers.traverseTopDown(visitor);
        if(modifier!=null) modifier.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(modifiers!=null) modifiers.traverseBottomUp(visitor);
        if(modifier!=null) modifier.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJNextModifier(\n");

        if(modifiers!=null)
            buffer.append(modifiers.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(modifier!=null)
            buffer.append(modifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJNextModifier]");
        return buffer.toString();
    }
}
