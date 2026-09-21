// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJMethodInvocation extends Method_invocation {

    private Name name;
    private Argument_list_opt argument_list_opt;

    public MJMethodInvocation (Name name, Argument_list_opt argument_list_opt) {
        this.name=name;
        if(name!=null) name.setParent(this);
        this.argument_list_opt=argument_list_opt;
        if(argument_list_opt!=null) argument_list_opt.setParent(this);
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name=name;
    }

    public Argument_list_opt getArgument_list_opt() {
        return argument_list_opt;
    }

    public void setArgument_list_opt(Argument_list_opt argument_list_opt) {
        this.argument_list_opt=argument_list_opt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(name!=null) name.accept(visitor);
        if(argument_list_opt!=null) argument_list_opt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(name!=null) name.traverseTopDown(visitor);
        if(argument_list_opt!=null) argument_list_opt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(name!=null) name.traverseBottomUp(visitor);
        if(argument_list_opt!=null) argument_list_opt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJMethodInvocation(\n");

        if(name!=null)
            buffer.append(name.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(argument_list_opt!=null)
            buffer.append(argument_list_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJMethodInvocation]");
        return buffer.toString();
    }
}
