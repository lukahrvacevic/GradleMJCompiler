// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJConstant extends Variable_declarator {

    private Variable_declarator_id variable_declarator_id;
    private Const_literal const_literal;

    public MJConstant (Variable_declarator_id variable_declarator_id, Const_literal const_literal) {
        this.variable_declarator_id=variable_declarator_id;
        if(variable_declarator_id!=null) variable_declarator_id.setParent(this);
        this.const_literal=const_literal;
        if(const_literal!=null) const_literal.setParent(this);
    }

    public Variable_declarator_id getVariable_declarator_id() {
        return variable_declarator_id;
    }

    public void setVariable_declarator_id(Variable_declarator_id variable_declarator_id) {
        this.variable_declarator_id=variable_declarator_id;
    }

    public Const_literal getConst_literal() {
        return const_literal;
    }

    public void setConst_literal(Const_literal const_literal) {
        this.const_literal=const_literal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(variable_declarator_id!=null) variable_declarator_id.accept(visitor);
        if(const_literal!=null) const_literal.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(variable_declarator_id!=null) variable_declarator_id.traverseTopDown(visitor);
        if(const_literal!=null) const_literal.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(variable_declarator_id!=null) variable_declarator_id.traverseBottomUp(visitor);
        if(const_literal!=null) const_literal.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJConstant(\n");

        if(variable_declarator_id!=null)
            buffer.append(variable_declarator_id.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(const_literal!=null)
            buffer.append(const_literal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJConstant]");
        return buffer.toString();
    }
}
