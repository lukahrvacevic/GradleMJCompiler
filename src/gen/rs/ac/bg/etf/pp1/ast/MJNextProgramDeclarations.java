// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJNextProgramDeclarations extends Static_declarations {

    private Static_declarations static_declarations;
    private Static_declaration static_declaration;

    public MJNextProgramDeclarations (Static_declarations static_declarations, Static_declaration static_declaration) {
        this.static_declarations=static_declarations;
        if(static_declarations!=null) static_declarations.setParent(this);
        this.static_declaration=static_declaration;
        if(static_declaration!=null) static_declaration.setParent(this);
    }

    public Static_declarations getStatic_declarations() {
        return static_declarations;
    }

    public void setStatic_declarations(Static_declarations static_declarations) {
        this.static_declarations=static_declarations;
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
        if(static_declarations!=null) static_declarations.accept(visitor);
        if(static_declaration!=null) static_declaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(static_declarations!=null) static_declarations.traverseTopDown(visitor);
        if(static_declaration!=null) static_declaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(static_declarations!=null) static_declarations.traverseBottomUp(visitor);
        if(static_declaration!=null) static_declaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJNextProgramDeclarations(\n");

        if(static_declarations!=null)
            buffer.append(static_declarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(static_declaration!=null)
            buffer.append(static_declaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJNextProgramDeclarations]");
        return buffer.toString();
    }
}
