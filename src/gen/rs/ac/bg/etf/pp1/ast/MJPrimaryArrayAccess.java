// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJPrimaryArrayAccess extends Primary_no_new_array {

    private Array_access array_access;

    public MJPrimaryArrayAccess (Array_access array_access) {
        this.array_access=array_access;
        if(array_access!=null) array_access.setParent(this);
    }

    public Array_access getArray_access() {
        return array_access;
    }

    public void setArray_access(Array_access array_access) {
        this.array_access=array_access;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(array_access!=null) array_access.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(array_access!=null) array_access.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(array_access!=null) array_access.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJPrimaryArrayAccess(\n");

        if(array_access!=null)
            buffer.append(array_access.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJPrimaryArrayAccess]");
        return buffer.toString();
    }
}
