// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJProgramDeclaration extends Static_declarations {

    private Static_declaration static_declaration;

    public MJProgramDeclaration (Static_declaration static_declaration) {
        this.static_declaration=static_declaration;
        if(static_declaration!=null) static_declaration.setParent(this);
    }

    public Static_declaration getStatic_declaration() {
        return static_declaration;
    }

    public void setStatic_declaration(Static_declaration static_declaration) {
        this.static_declaration=static_declaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(static_declaration!=null) static_declaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(static_declaration!=null) static_declaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(static_declaration!=null) static_declaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJProgramDeclaration(\n");

        if(static_declaration!=null)
            buffer.append(static_declaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJProgramDeclaration]");
        return buffer.toString();
    }
}
