// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJProgramVariable extends Static_declaration {

    private Field_declaration field_declaration;

    public MJProgramVariable (Field_declaration field_declaration) {
        this.field_declaration=field_declaration;
        if(field_declaration!=null) field_declaration.setParent(this);
    }

    public Field_declaration getField_declaration() {
        return field_declaration;
    }

    public void setField_declaration(Field_declaration field_declaration) {
        this.field_declaration=field_declaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(field_declaration!=null) field_declaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(field_declaration!=null) field_declaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(field_declaration!=null) field_declaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJProgramVariable(\n");

        if(field_declaration!=null)
            buffer.append(field_declaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJProgramVariable]");
        return buffer.toString();
    }
}
