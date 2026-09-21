// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJForInitOpt extends For_init_opt {

    private For_init for_init;

    public MJForInitOpt (For_init for_init) {
        this.for_init=for_init;
        if(for_init!=null) for_init.setParent(this);
    }

    public For_init getFor_init() {
        return for_init;
    }

    public void setFor_init(For_init for_init) {
        this.for_init=for_init;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(for_init!=null) for_init.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(for_init!=null) for_init.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(for_init!=null) for_init.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJForInitOpt(\n");

        if(for_init!=null)
            buffer.append(for_init.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJForInitOpt]");
        return buffer.toString();
    }
}
