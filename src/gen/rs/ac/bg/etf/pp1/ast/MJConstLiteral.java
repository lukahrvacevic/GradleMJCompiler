// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJConstLiteral extends Literal {

    private Const_literal const_literal;

    public MJConstLiteral (Const_literal const_literal) {
        this.const_literal=const_literal;
        if(const_literal!=null) const_literal.setParent(this);
    }

    public Const_literal getConst_literal() {
        return const_literal;
    }

    public void setConst_literal(Const_literal const_literal) {
        this.const_literal=const_literal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(const_literal!=null) const_literal.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(const_literal!=null) const_literal.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(const_literal!=null) const_literal.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJConstLiteral(\n");

        if(const_literal!=null)
            buffer.append(const_literal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJConstLiteral]");
        return buffer.toString();
    }
}
