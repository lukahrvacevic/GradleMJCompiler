// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJFormalParameters extends Formal_parameters_opt {

    private Formal_parameters formal_parameters;

    public MJFormalParameters (Formal_parameters formal_parameters) {
        this.formal_parameters=formal_parameters;
        if(formal_parameters!=null) formal_parameters.setParent(this);
    }

    public Formal_parameters getFormal_parameters() {
        return formal_parameters;
    }

    public void setFormal_parameters(Formal_parameters formal_parameters) {
        this.formal_parameters=formal_parameters;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(formal_parameters!=null) formal_parameters.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(formal_parameters!=null) formal_parameters.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(formal_parameters!=null) formal_parameters.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJFormalParameters(\n");

        if(formal_parameters!=null)
            buffer.append(formal_parameters.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJFormalParameters]");
        return buffer.toString();
    }
}
