// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJMemberDeclaration extends Member_declarations {

    private Member_declaration member_declaration;

    public MJMemberDeclaration (Member_declaration member_declaration) {
        this.member_declaration=member_declaration;
        if(member_declaration!=null) member_declaration.setParent(this);
    }

    public Member_declaration getMember_declaration() {
        return member_declaration;
    }

    public void setMember_declaration(Member_declaration member_declaration) {
        this.member_declaration=member_declaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(member_declaration!=null) member_declaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(member_declaration!=null) member_declaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(member_declaration!=null) member_declaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJMemberDeclaration(\n");

        if(member_declaration!=null)
            buffer.append(member_declaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJMemberDeclaration]");
        return buffer.toString();
    }
}
