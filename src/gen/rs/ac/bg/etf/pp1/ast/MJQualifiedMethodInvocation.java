// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJQualifiedMethodInvocation extends Method_invocation {

    private Primary primary;
    private String I2;
    private Argument_list_opt argument_list_opt;

    public MJQualifiedMethodInvocation (Primary primary, String I2, Argument_list_opt argument_list_opt) {
        this.primary=primary;
        if(primary!=null) primary.setParent(this);
        this.I2=I2;
        this.argument_list_opt=argument_list_opt;
        if(argument_list_opt!=null) argument_list_opt.setParent(this);
    }

    public Primary getPrimary() {
        return primary;
    }

    public void setPrimary(Primary primary) {
        this.primary=primary;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
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
        if(primary!=null) primary.accept(visitor);
        if(argument_list_opt!=null) argument_list_opt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(primary!=null) primary.traverseTopDown(visitor);
        if(argument_list_opt!=null) argument_list_opt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(primary!=null) primary.traverseBottomUp(visitor);
        if(argument_list_opt!=null) argument_list_opt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJQualifiedMethodInvocation(\n");

        if(primary!=null)
            buffer.append(primary.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        if(argument_list_opt!=null)
            buffer.append(argument_list_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJQualifiedMethodInvocation]");
        return buffer.toString();
    }
}
