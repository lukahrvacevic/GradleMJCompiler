// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJClassInstanceCreation extends Class_instance_creation_expression {

    private MJType MJType;

    public MJClassInstanceCreation (MJType MJType) {
        this.MJType=MJType;
        if(MJType!=null) MJType.setParent(this);
    }

    public MJType getMJType() {
        return MJType;
    }

    public void setMJType(MJType MJType) {
        this.MJType=MJType;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MJType!=null) MJType.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MJType!=null) MJType.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MJType!=null) MJType.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJClassInstanceCreation(\n");

        if(MJType!=null)
            buffer.append(MJType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJClassInstanceCreation]");
        return buffer.toString();
    }
}
