package rs.ac.bg.etf.pp1.symbols;

import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.codegen.BytecodeEmitter.Chain;
import rs.ac.bg.etf.pp1.symbols.Type.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;

import java.util.List;

public abstract class Symbol extends Obj implements Modifiable {
    protected Symbol owner;
    protected Flags flags;

    Symbol(int legacyKind, String name, Type type) {
        super(legacyKind, name, type, 0, 1);
        this.owner = null;
    }

    public Type getSymbolType() {
        return (Type) getType();
    }

    public Symbol getOwner() { return owner; }

    public void setOwner(Symbol owner) { this.owner = owner; }

    public Flags getModifiers() { return flags; }

    public void setModifiers(Flags flags) { this.flags = flags; }

    public boolean isProgram() { return false; }

    public boolean isStatic() { return false; }

    public boolean isConstant() { return false; }

    public boolean isDataHolder() { return false; }

    public boolean isParameter() { return false; }

    public boolean isField() { return false; }

    public boolean isMember() { return false; }

    public boolean isCallable() { return false; }

    public boolean isType() { return false; }

    public boolean isClass() { return false; }

    public boolean isVariable() { return this instanceof VariableSymbol || isParameter(); }

    public boolean isSameKind(Symbol other) {
        return getKind() == other.getKind();
    }

    public abstract String getKindName();

    public static ElementSymbol makeDummy(Type type) {
        return new ElementSymbol(type);
    }

    @Override
    public String toString() {
        return getName();
    }

    public static abstract class DataHolderSymbol extends Symbol {
        DataHolderSymbol(int legacyKind, String name, Type type) {
            super(legacyKind, name, type);
        }

        @Override
        public boolean isDataHolder() { return true; }
    }

    public static final class ConstantSymbol extends DataHolderSymbol {

        ConstantSymbol(String name, Type type, int value) {
            super(Obj.Con, name, type);
            setAdr(value);
        }

        @Override
        public boolean isConstant() { return true; }

        @Override
        public String getKindName() { return "Constant"; }
    }

    public static final class VariableSymbol extends DataHolderSymbol {
        public VariableSymbol(String name, Type type) {
            super(Obj.Var, name, type);
        }

        @Override
        public String getKindName() { return "Variable"; }

        @Override
        public boolean isStatic() { return owner.isProgram() || owner instanceof UniverseSymbol; }
    }

    public static final class ParameterSymbol extends DataHolderSymbol {
        ParameterSymbol(String name, Type type) {
            super(Obj.Var, name, type);
        }

        @Override
        public boolean isParameter() { return true; }

        @Override
        public String getKindName() { return "Parameter"; }
    }

    public static final class FieldSymbol extends DataHolderSymbol {
        FieldSymbol(String name, Type type) {
            super(Obj.Fld, name, type);
        }

        @Override
        public boolean isField() { return true; }

        @Override
        public boolean isMember() { return true; }

        @Override
        public String getKindName() { return "Field"; }
    }

    public static final class ElementSymbol extends DataHolderSymbol {
        private ElementSymbol(Type type) {
            super(Obj.Elem, "$DUMMY", type);
        }

        @Override
        public String getKindName() { return ""; }
    }

    public interface Writable extends Modifiable {
        void addSymbol(Symbol symbol);
        void write();
        Symbol find(String name);
        Symbol getOwner();
        boolean isClass();
        Scope getScope();
    }

    public static final class ProgramSymbol extends Symbol implements Writable {
        private final Scope locals;
        private int dataSize = 0;
        private MethodSymbol main;

        public ProgramSymbol(String name, Scope locals) {
            super(Obj.Prog, name, SymbolTable.VOID);
            this.locals = locals;
            this.flags = new Flags();
        }

        @Override
        public boolean isProgram() { return true; }

        public void addSymbol(Symbol symbol) {
            locals.addToLocals(symbol);
            symbol.setOwner(this);
            if (symbol instanceof VariableSymbol) dataSize += 1;
        }

        @Override
        public void write() {
            this.setLocals(locals.getLocals());
        }

        @Override
        public Symbol find(String name) {
            Obj result = locals.findSymbol(name);
            if (result != null)
                return (Symbol) result;
            return ((Writable) getOwner()).find(name);
        }

        @Override
        public String getKindName() { return "Program"; }

        public List<ClassSymbol> getClasses() {
            return getLocalSymbols().stream()
                    .map(obj -> (Symbol) obj)
                    .filter(Symbol::isClass)
                    .map(symbol -> (ClassSymbol) symbol)
                    .collect(java.util.stream.Collectors.toList());
        }

        public List<MethodSymbol> getMethods() {
            return getLocalSymbols().stream()
                    .map(obj -> (Symbol) obj)
                    .filter(Symbol::isCallable)
                    .map(symbol -> (MethodSymbol) symbol)
                    .collect(java.util.stream.Collectors.toList());
        }

        public MethodSymbol getMain() { return main; }

        public void setMain(MethodSymbol main) { this.main = main; }

        public int getStaticFieldCount() { return dataSize; }

        @Override
        public Scope getScope() { return locals; }
    }

    public static final class MethodSymbol extends Symbol implements Writable {
        private final Scope locals;
        private int parameterCount = 0;
        private boolean isDefined = false;
        public Chain forwardReference;
        private SyntaxNode body;
        private boolean isMain = false;

        MethodSymbol(String name, Type returnType, Scope locals) {
            super(Obj.Meth, name, returnType);
            this.locals = locals;
        }

        public boolean isDefined() { return isDefined; }

        @Override
        public void setAdr(int adr) {
            super.setAdr(adr);
            isDefined = true;
        }

        public SyntaxNode getBody() { return body; }

        public void setBody(SyntaxNode body) { this.body = body; }

        public boolean isMain() { return isMain; }

        public void setMain() {
            isMain = true;
            ((ProgramSymbol) owner).setMain(this);
        }

        public void addSymbol(Symbol symbol) {
            if (symbol.isParameter()) parameterCount++;
            locals.addToLocals(symbol);
            symbol.setOwner(this);
        }

        @Override
        public boolean isCallable() { return true; }

        @Override
        public boolean isMember() { return owner.isClass(); }

        @Override
        public boolean isStatic() { return owner.isProgram() || owner instanceof UniverseSymbol; }

        @Override
        public void write() {
            this.setLocals(locals.getLocals());
            setLevel(parameterCount);
        }

        @Override
        public Symbol find(String name) {
            Obj result = locals.findSymbol(name);
            if (result != null)
                return (Symbol) result;
            return ((Writable) getOwner()).find(name);
        }

        public List<Symbol> getSymbols() {
            return getLocalSymbols().stream()
                    .map(obj -> (Symbol) obj)
                    .collect(java.util.stream.Collectors.toList());
        }

        public Type getReturnType() { return (Type) getType(); }

        public boolean matchesSuper(MethodSymbol other) {
            if (parameterCount != other.parameterCount)
                return false;

            if (!getReturnType().equals(other.getReturnType()))
                return false;

            List<Symbol> thisParams = getSymbols();
            List<Symbol> otherParams = other.getSymbols();

            for (int i = 1; i < parameterCount; i++) { // skip "this"
                if (!thisParams.get(i).getSymbolType().equals(otherParams.get(i).getSymbolType())) return false;
            }

            return true;
        }

        public boolean matchesArguments(List<Type> arguments) {
            List<Symbol> parameters = getSymbols().subList(0, parameterCount);
            if (isMember()) parameters = parameters.subList(1, parameters.size());

            if (parameters.size() != arguments.size()) return false;

            for (int i = 0; i < parameters.size(); i++) {
                if (!arguments.get(i).assignable(parameters.get(i).getSymbolType())) return false;
            }
            return true;
        }

        @Override
        public String getKindName() { return "Method"; }

        public String getFullName() {
            return getOwner().getName() + "." + getName();
        }

        public String formatParameters() {
            List<Symbol> params = getSymbols();
            if (isMember()) params = params.subList(1, params.size());

            return params.stream()
                    .filter(Symbol::isParameter)
                    .map(Symbol::getSymbolType)
                    .map(rs.ac.bg.etf.pp1.symbols.Type::toString)
                    .collect(java.util.stream.Collectors.joining(", ", "(", ")"));
        }

        @Override
        public Scope getScope() { return locals; }
    }

    public static abstract class TypeSymbol extends Symbol {
        TypeSymbol(String name, Type type) {
            super(Obj.Type, name, type);
        }

        @Override
        public boolean isType() { return true; }
    }

    public static final class PrimitiveTypeSymbol extends TypeSymbol {
        PrimitiveTypeSymbol(String name, Type type) {
            super(name, type);
            setLevel(0);
        }

        @Override
        public String getKindName() { return "Type"; }
    }

    public static final class ArrayTypeSymbol extends TypeSymbol {
        ArrayTypeSymbol(ArrayType type) {
            super(type.getElementType().owner.getName() + "[]", type);
            setLevel(1);
        }

        @Override
        public String getKindName() { return "Variable"; }
    }

    public static final class ClassSymbol extends TypeSymbol implements Writable {
        public ClassSymbol(String name, ClassType thisType) {
            super(name, thisType);
        }

        public ClassType getThisType() {
            return (ClassType) getSymbolType();
        }

        @Override
        public boolean isClass() { return true; }

        @Override
        public void addSymbol(Symbol symbol) {
            getThisType().addMember(symbol);
            symbol.setOwner(this);
        }

        @Override
        public void write() {
            getThisType().write();
        }

        @Override
        public Symbol find(String name) {
            Symbol result = getThisType().find(name);
            if (result != SymbolTable.NO_SYMBOL)
                return result;
            return ((Writable) getOwner()).find(name);
        }

        @Override
        public String getKindName() { return "Class"; }

        @Override
        public Scope getScope() { return getThisType().scope; }
    }

    public static final class EnumSymbol extends TypeSymbol implements Writable {
        EnumSymbol(String name, EnumType enumType) {
            super(name, enumType);
        }

        public EnumType getThisType() {
            return (EnumType) getSymbolType();
        }

        @Override
        public void addSymbol(Symbol symbol) {
            getThisType().addElement(symbol);
            symbol.setOwner(this);
        }

        @Override
        public void write() { getThisType().write(); }

        @Override
        public Symbol find(String name) {
            Symbol result = getThisType().find(name);
            if (result != SymbolTable.NO_SYMBOL)
                return result;
            return ((Writable) getOwner()).find(name);
        }

        @Override
        public String getKindName() { return "Enum"; }

        @Override
        public Scope getScope() { return getThisType().scope; }
    }

    public static final class UniverseSymbol extends Symbol implements Writable {
        Scope universeScope;

        @Override
        public void addSymbol(Symbol symbol) {
            universeScope.addToLocals(symbol);
            symbol.setOwner(this);
        }

        @Override
        public void write() {}

        @Override
        public Symbol find(String name) {
            Obj result = universeScope.findSymbol(name);
            if (result != null) return (Symbol) result;
            return SymbolTable.NO_SYMBOL;
        }

        UniverseSymbol(Scope universeScope) {
            super(-1, "", null);
            this.universeScope = universeScope;
        }

        @Override
        public String getKindName() { return ""; }

        @Override
        public Scope getScope() { return universeScope; }
    }

    public static final class NoSymbol extends Symbol {
        NoSymbol(Type type) {
            super(Obj.Var, "noObj", type);
        }

        @Override
        public String getKindName() { return ""; }
    }
}