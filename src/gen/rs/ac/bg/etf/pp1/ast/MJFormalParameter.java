// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJFormalParameter extends Formal_parameters {

    private Formal_parameter formal_parameter;

    public MJFormalParameter (Formal_parameter formal_parameter) {
        this.formal_parameter=formal_parameter;
        if(formal_parameter!=null) formal_parameter.setParent(this);
    }

    public Formal_parameter getFormal_parameter() {
        return formal_parameter;
    }

    public void setFormal_parameter(Formal_parameter formal_parameter) {
        this.formal_parameter=formal_parameter;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(formal_parameter!=null) formal_parameter.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(formal_parameter!=null) formal_parameter.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(formal_parameter!=null) formal_parameter.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJFormalParameter(\n");

        if(formal_parameter!=null)
            buffer.append(formal_parameter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJFormalParameter]");
        return buffer.toString();
    }
}
