// generated with ast extension for cup
// version 0.8
// 22/8/2026 1:5:49


package rs.ac.bg.etf.pp1.ast;

public class MJMethodDeclaration extends Method_declaration {

    private Method_signature method_signature;
    private Method_variables_opt method_variables_opt;
    private Method_body method_body;

    public MJMethodDeclaration (Method_signature method_signature, Method_variables_opt method_variables_opt, Method_body method_body) {
        this.method_signature=method_signature;
        if(method_signature!=null) method_signature.setParent(this);
        this.method_variables_opt=method_variables_opt;
        if(method_variables_opt!=null) method_variables_opt.setParent(this);
        this.method_body=method_body;
        if(method_body!=null) method_body.setParent(this);
    }

    public Method_signature getMethod_signature() {
        return method_signature;
    }

    public void setMethod_signature(Method_signature method_signature) {
        this.method_signature=method_signature;
    }

    public Method_variables_opt getMethod_variables_opt() {
        return method_variables_opt;
    }

    public void setMethod_variables_opt(Method_variables_opt method_variables_opt) {
        this.method_variables_opt=method_variables_opt;
    }

    public Method_body getMethod_body() {
        return method_body;
    }

    public void setMethod_body(Method_body method_body) {
        this.method_body=method_body;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(method_signature!=null) method_signature.accept(visitor);
        if(method_variables_opt!=null) method_variables_opt.accept(visitor);
        if(method_body!=null) method_body.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_signature!=null) method_signature.traverseTopDown(visitor);
        if(method_variables_opt!=null) method_variables_opt.traverseTopDown(visitor);
        if(method_body!=null) method_body.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_signature!=null) method_signature.traverseBottomUp(visitor);
        if(method_variables_opt!=null) method_variables_opt.traverseBottomUp(visitor);
        if(method_body!=null) method_body.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MJMethodDeclaration(\n");

        if(method_signature!=null)
            buffer.append(method_signature.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(method_variables_opt!=null)
            buffer.append(method_variables_opt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(method_body!=null)
            buffer.append(method_body.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MJMethodDeclaration]");
        return buffer.toString();
    }
}
