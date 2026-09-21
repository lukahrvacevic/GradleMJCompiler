// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJFirstModifier extends Modifiers {

    private Modifier modifier;

    public MJFirstModifier (Modifier modifier) {
        this.modifier=modifier;
        if(modifier!=null) modifier.setParent(this);
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
        if(modifier!=null) modifier.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(modifier!=null) modifier.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(modifier!=null) modifier.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJFirstModifier(\n");

        if(modifier!=null)
            buffer.append(modifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJFirstModifier]");
        return buffer.toString();
    }
}
