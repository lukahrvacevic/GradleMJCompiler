// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJForUpdateOpt extends For_update_opt {

    private For_update for_update;

    public MJForUpdateOpt (For_update for_update) {
        this.for_update=for_update;
        if(for_update!=null) for_update.setParent(this);
    }

    public For_update getFor_update() {
        return for_update;
    }

    public void setFor_update(For_update for_update) {
        this.for_update=for_update;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(for_update!=null) for_update.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(for_update!=null) for_update.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(for_update!=null) for_update.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJForUpdateOpt(\n");

        if(for_update!=null)
            buffer.append(for_update.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJForUpdateOpt]");
        return buffer.toString();
    }
}
