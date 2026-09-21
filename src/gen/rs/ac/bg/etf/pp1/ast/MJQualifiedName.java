// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJQualifiedName extends Qualified_name {

    private Name name;
    private String I2;

    public MJQualifiedName (Name name, String I2) {
        this.name=name;
        if(name!=null) name.setParent(this);
        this.I2=I2;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name=name;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(name!=null) name.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(name!=null) name.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(name!=null) name.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJQualifiedName(\n");

        if(name!=null)
            buffer.append(name.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJQualifiedName]");
        return buffer.toString();
    }
}
