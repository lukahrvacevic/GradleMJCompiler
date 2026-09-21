// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJProgramDeclarations extends Static_declarations_opt {

    private Static_declarations static_declarations;

    public MJProgramDeclarations (Static_declarations static_declarations) {
        this.static_declarations=static_declarations;
        if(static_declarations!=null) static_declarations.setParent(this);
    }

    public Static_declarations getStatic_declarations() {
        return static_declarations;
    }

    public void setStatic_declarations(Static_declarations static_declarations) {
        this.static_declarations=static_declarations;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(static_declarations!=null) static_declarations.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(static_declarations!=null) static_declarations.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(static_declarations!=null) static_declarations.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJProgramDeclarations(\n");

        if(static_declarations!=null)
            buffer.append(static_declarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJProgramDeclarations]");
        return buffer.toString();
    }
}
