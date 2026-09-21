package rs.ac.bg.etf.pp1.symbols;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.lang.annotation.ElementType;
import java.util.*;

public abstract class Type extends Struct {
    protected Symbol owner;

    public Type(int legacyKind, Symbol owner) {
        super(legacyKind);
        this.owner = owner;
    }

    public Symbol getOwner() {
        return owner;
    }

    public boolean isPrimitive() {
        return false;
    }

    public boolean isReference() { return false; }

    public boolean isClass() { return false; }

    public boolean isEnum() { return false; }

    public boolean isArray() { return false; }

    public boolean isVoid() { return false; }

    public boolean assignable(Type other) {
        if (this == other)
            return true;

        return xAssignable(other);
    }

    protected boolean xAssignable(Type other) {
        return false;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Type)) return false;
        Type other = (Type) o;
        return xEquals(other);
    }

    protected boolean xEquals(Type other) {
        return this == other;
    }

    @Override
    public String toString() { return getOwner().getName(); }

    public static ArrayType arrayOf(Type elementType) {
        ArrayType arrayType = new ArrayType(elementType);
        arrayType.owner = new Symbol.ArrayTypeSymbol(arrayType);
        return arrayType;
    }

    public static final class PrimitiveType extends Type {
        public PrimitiveType(int legacyKind) {
            super(legacyKind, null);
        }

        @Override
        public boolean isPrimitive() { return true; }

        @Override
        protected boolean xAssignable(Type other) {
            if (other.isEnum() && getKind() == Struct.Int)
                return true;
            return getKind() == other.getKind();
        }
    }

    public interface Accessible {
        Symbol find(String name);
    }

    public static final class EnumType extends Type implements Accessible {
        Scope scope;
        public EnumType(Scope scope) {
            super(Struct.Enum, null);
            this.scope = scope;
        }

        @Override
        public boolean isEnum() { return true; }

        public void addElement(Symbol symbol) {
            scope.addToLocals(symbol);
        }

        public Symbol find(String name) {
            Obj symbol = scope.findSymbol(name);
            if (symbol != null)
                return (Symbol) symbol;
            return SymbolTable.NO_SYMBOL;
        }

        public void write() {
            setMembers(scope.getLocals());
        }

        @Override
        protected boolean xAssignable(Type other) {
            if (other.getKind() == Struct.Int)
                return true;

            return this == other;
        }
    }

    public static abstract class ReferenceType extends Type {
        public ReferenceType(int legacyKind, Symbol owner) {
            super(legacyKind, owner);
        }

        @Override
        public boolean isReference() { return true; }
    }

    public static final class ArrayType extends ReferenceType {
        public ArrayType(Type elementType) {
            super(Struct.Array, null);
            setElementType(elementType);
        }

        public Type getElementType() { return (Type) getElemType(); }

        @Override
        public boolean isArray() { return true; }

        @Override
        protected boolean xAssignable(Type other) {
            return xEquals(other);
        }

        @Override
        protected boolean xEquals(Type other) {
            if (!other.isArray())
                return false;

            return getElementType().equals(((ArrayType) other).getElementType());
        }
    }

    public static final class ClassType extends ReferenceType implements Accessible {
        Scope scope;
        private int size = 4;

        public ClassType(ClassType superType, Scope scope) {
            super(Struct.Class, null);
            this.scope = scope;
            setElementType(superType);
            inheritFields();
        }

        private void inheritFields() {
            if (getSuperType() == null) return;

            getSuperType().getClassMembers().stream()
                    .filter(Symbol::isField)
                    .forEach(this::addMember);
        }

        public ClassType getSuperType() {
            return (ClassType) getElemType();
        }

        @Override
        public boolean isClass() { return true; }

        public void addMember(Symbol symbol) {
            scope.addToLocals(symbol);
            if (symbol.isField()) size += 4;
        }

        public void write() {
            setMembers(scope.getLocals());
        }

        public Symbol find(String name) {
            Obj symbol = scope.findSymbol(name);
            if (symbol != null)
                return (Symbol) symbol;

            if (getSuperType() != null)
                return getSuperType().find(name);

            return SymbolTable.NO_SYMBOL;
        }

        public Symbol findOwn(String name) {
            Obj symbol = scope.findSymbol(name);
            if (symbol != null)
                return (Symbol) symbol;

            return SymbolTable.NO_SYMBOL;
        }

        public Collection<Symbol> getClassMembers() {
            return getMembers().stream().map(obj -> (Symbol) obj).collect(java.util.stream.Collectors.toList());
        }

        public List<Symbol.MethodSymbol> getMethods() {
            HashMap<String, Symbol.MethodSymbol> methods = new LinkedHashMap<>();

            if (getSuperType() != null) {
                getSuperType().getMethods().forEach(m -> methods.put(m.getName(), m));
            }

            getClassMembers().stream()
                    .filter(Symbol::isCallable)
                    .map(symbol -> (Symbol.MethodSymbol) symbol)
                    .forEach(m -> methods.put(m.getName(), m));

            return new ArrayList<>(methods.values());
        }

        public int getSize() { return size; }

        @Override
        protected boolean xAssignable(Type other) {
            if (!other.isClass())
                return false;

            return extendsFrom((ClassType) other);
        }

        public boolean extendsFrom(ClassType other) {
            if (getSuperType() == null)
                return false;

            if (getSuperType().equals(other))
                return true;

            return getSuperType().extendsFrom(other);
        }
    }

    public static final class NullType extends ReferenceType {
        public NullType() {
            super(Struct.Class, null);
        }

        @Override
        protected boolean xAssignable(Type other) { return other.isReference(); }

        @Override
        public String toString() { return "null"; }
    }

    public static final class VoidType extends Type {
        public VoidType() {
            super(Struct.None, null);
        }

        @Override
        public boolean isVoid() { return true; }
    }
}
