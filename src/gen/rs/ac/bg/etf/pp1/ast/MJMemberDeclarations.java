// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJMemberDeclarations extends Member_declarations_opt {

    private Member_declarations member_declarations;

    public MJMemberDeclarations (Member_declarations member_declarations) {
        this.member_declarations=member_declarations;
        if(member_declarations!=null) member_declarations.setParent(this);
    }

    public Member_declarations getMember_declarations() {
        return member_declarations;
    }

    public void setMember_declarations(Member_declarations member_declarations) {
        this.member_declarations=member_declarations;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(member_declarations!=null) member_declarations.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(member_declarations!=null) member_declarations.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(member_declarations!=null) member_declarations.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJMemberDeclarations(\n");

        if(member_declarations!=null)
            buffer.append(member_declarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJMemberDeclarations]");
        return buffer.toString();
    }
}
