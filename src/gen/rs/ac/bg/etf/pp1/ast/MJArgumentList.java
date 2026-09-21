// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJArgumentList extends Argument_list_opt {

    private Argument_list argument_list;

    public MJArgumentList (Argument_list argument_list) {
        this.argument_list=argument_list;
        if(argument_list!=null) argument_list.setParent(this);
    }

    public Argument_list getArgument_list() {
        return argument_list;
    }

    public void setArgument_list(Argument_list argument_list) {
        this.argument_list=argument_list;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(argument_list!=null) argument_list.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(argument_list!=null) argument_list.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(argument_list!=null) argument_list.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJArgumentList(\n");

        if(argument_list!=null)
            buffer.append(argument_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJArgumentList]");
        return buffer.toString();
    }
}
