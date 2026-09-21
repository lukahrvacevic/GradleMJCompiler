// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJPrimaryLiteral extends Primary_no_new_array {

    private Literal literal;

    public MJPrimaryLiteral (Literal literal) {
        this.literal=literal;
        if(literal!=null) literal.setParent(this);
    }

    public Literal getLiteral() {
        return literal;
    }

    public void setLiteral(Literal literal) {
        this.literal=literal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(literal!=null) literal.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(literal!=null) literal.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(literal!=null) literal.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJPrimaryLiteral(\n");

        if(literal!=null)
            buffer.append(literal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJPrimaryLiteral]");
        return buffer.toString();
    }
}
