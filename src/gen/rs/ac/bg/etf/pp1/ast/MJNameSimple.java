// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJNameSimple extends Name {

    private Simple_name simple_name;

    public MJNameSimple (Simple_name simple_name) {
        this.simple_name=simple_name;
        if(simple_name!=null) simple_name.setParent(this);
    }

    public Simple_name getSimple_name() {
        return simple_name;
    }

    public void setSimple_name(Simple_name simple_name) {
        this.simple_name=simple_name;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(simple_name!=null) simple_name.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(simple_name!=null) simple_name.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(simple_name!=null) simple_name.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJNameSimple(\n");

        if(simple_name!=null)
            buffer.append(simple_name.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJNameSimple]");
        return buffer.toString();
    }
}
