// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJNameQualified extends Name {

    private Qualified_name qualified_name;

    public MJNameQualified (Qualified_name qualified_name) {
        this.qualified_name=qualified_name;
        if(qualified_name!=null) qualified_name.setParent(this);
    }

    public Qualified_name getQualified_name() {
        return qualified_name;
    }

    public void setQualified_name(Qualified_name qualified_name) {
        this.qualified_name=qualified_name;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(qualified_name!=null) qualified_name.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(qualified_name!=null) qualified_name.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(qualified_name!=null) qualified_name.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJNameQualified(\n");

        if(qualified_name!=null)
            buffer.append(qualified_name.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJNameQualified]");
        return buffer.toString();
    }
}
