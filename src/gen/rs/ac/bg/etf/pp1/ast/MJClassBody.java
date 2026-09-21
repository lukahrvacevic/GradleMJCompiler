// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJClassBody extends Class_body {

    private Member_declarations_opt member_declarations_opt;
    private Methods_block_opt methods_block_opt;

    public MJClassBody (Member_declarations_opt member_declarations_opt, Methods_block_opt methods_block_opt) {
        this.member_declarations_opt=member_declarations_opt;
        if(member_declarations_opt!=null) member_declarations_opt.setParent(this);
        this.methods_block_opt=methods_block_opt;
        if(methods_block_opt!=null) methods_block_opt.setParent(this);
    }

    public Member_declarations_opt getMember_declarations_opt() {
        return member_declarations_opt;
    }

    public void setMember_declarations_opt(Member_declarations_opt member_declarations_opt) {
        this.member_declarations_opt=member_declarations_opt;
    }

    public Methods_block_opt getMethods_block_opt() {
        return methods_block_opt;
    }

    public void setMethods_block_opt(Methods_block_opt methods_block_opt) {
        this.methods_block_opt=methods_block_opt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(member_declarations_opt!=null) member_declarations_opt.accept(visitor);
        if(methods_block_opt!=null) methods_block_opt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(member_declarations_opt!=null) member_declarations_opt.traverseTopDown(visitor);
        if(methods_block_opt!=null) methods_block_opt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(member_declarations_opt!=null) member_declarations_opt.traverseBottomUp(visitor);
        if(methods_block_opt!=null) methods_block_opt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJClassBody(\n");

        if(member_declarations_opt!=null)
            buffer.append(member_declarations_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(methods_block_opt!=null)
            buffer.append(methods_block_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJClassBody]");
        return buffer.toString();
    }
}
