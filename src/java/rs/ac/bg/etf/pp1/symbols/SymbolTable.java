package rs.ac.bg.etf.pp1.symbols;

import rs.ac.bg.etf.pp1.util.Context;
import rs.ac.bg.etf.pp1.symbols.Symbol.*;
import rs.ac.bg.etf.pp1.symbols.Type.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public final class SymbolTable {
    private static final Context.Key<SymbolTable> symbolTableKey = new Context.Key<>();

    public static final PrimitiveType INT = new PrimitiveType(Struct.Int);
    public static final PrimitiveType BOOL = new PrimitiveType(Struct.Bool);
    public static final PrimitiveType CHAR = new PrimitiveType(Struct.Char);
    public static final NullType NULL = new NullType();
    public static final VoidType VOID = new VoidType();

    public static final NoSymbol NO_SYMBOL = new NoSymbol(VOID);

    public static MethodSymbol findAnyInt;
    public static MethodSymbol findAnyChar;

    private Writable currentSymbol;

    private SymbolTable(Context context) {
        context.put(symbolTableKey, this);
    }

    public static SymbolTable getInstance(Context context) {
        SymbolTable symbolTable = context.get(symbolTableKey);
        if (symbolTable == null) {
            symbolTable = new SymbolTable(context);
        }
        return symbolTable;
    }

    public void initiate(String programName) {
        Tab.init();
        Tab.closeScope(); // flush everything
        Tab.openScope();

        currentSymbol = new UniverseSymbol(Tab.currentScope());

        addToScope(initiateType(INT, "int"));
        addToScope(initiateType(BOOL, "bool"));
        addToScope(initiateType(CHAR, "char"));
        initiateType(VOID, "void");

        addToScope(new ConstantSymbol("eol", CHAR, 10));
        addToScope(new ConstantSymbol("null", NULL, 0));

        openMethod("chr", CHAR, new Flags());
        addToScope(new ParameterSymbol("i", INT));
        closeScope();

        openMethod("ord", INT, new Flags());
        addToScope(new ParameterSymbol("ch", CHAR));
        closeScope();

        openMethod("len", INT, new Flags());
        declareParameter("arr", VOID, true);
        closeScope();

        openMethod("findAny<int>", BOOL, new Flags());
        declareParameter("arr", VOID, true);
        declareParameter("value", VOID, false);
        declareVariable("iter", INT);
        findAnyInt = (MethodSymbol) closeScope();

        openMethod("findAny<char>", BOOL, new Flags());
        declareParameter("arr", VOID, true);
        declareParameter("value", VOID, false);
        declareVariable("iter", INT);
        findAnyChar = (MethodSymbol) closeScope();

        openProgram(programName);
    }

    private static TypeSymbol initiateType(Type type, String name) {
        TypeSymbol clazz = new PrimitiveTypeSymbol(name, type);
        type.owner = clazz;
        return clazz;
    }

    public Symbol find(String name) {
        Obj result = Tab.find(name);
        if (result == Tab.noObj) {
            return NO_SYMBOL;
        }
        return (Symbol) result;
    }

    public Symbol findInScope(String name) {
        SymbolDataStructure symbols = Tab.currentScope().getLocals();
        if (symbols == null)
            return NO_SYMBOL;

        Obj result = symbols.searchKey(name);
        if (result != null)
            return (Symbol) result;

        return NO_SYMBOL;
    }

    private void openProgram(String name) {
        Writable parent = currentSymbol;
        Tab.openScope();
        ProgramSymbol program = new ProgramSymbol(name, Tab.currentScope());
        currentSymbol = program;
        parent.addSymbol(program);
    }

    public void openMethod(String name, Type returnType, Flags flags) {
        Writable parent = currentSymbol;
        Tab.openScope();
        MethodSymbol method = new MethodSymbol(name, returnType, Tab.currentScope());
        method.setModifiers(flags);
        currentSymbol = method;
        parent.addSymbol(method);
        if (parent.isClass()) {
            addToScope(new ParameterSymbol("this", ((ClassSymbol) parent).getThisType()));
        }
    }

    public void openEnum(String name) {
        Writable parent = currentSymbol;
        Tab.openScope();
        EnumType type = new EnumType(Tab.currentScope());
        EnumSymbol enumSymbol = new EnumSymbol(name, type);
        type.owner = enumSymbol;
        currentSymbol = enumSymbol;
        parent.addSymbol(enumSymbol);
    }

    public void openClass(String name, ClassType superClass, Flags flags) {
        Writable parent = currentSymbol;
        Tab.openScope();
        ClassType thisType = new ClassType(superClass, Tab.currentScope());
        ClassSymbol clazz = new ClassSymbol(name, thisType);
        thisType.owner = clazz;
        clazz.setModifiers(flags);
        currentSymbol = clazz;
        parent.addSymbol(clazz);
    }

    public void addToScope(Symbol symbol) {
        currentSymbol.addSymbol(symbol);
    }

    public ConstantSymbol declareConstant(String name, Type type, int value) {
        ConstantSymbol constant = new ConstantSymbol(name, type, value);
        addToScope(constant);
        return constant;
    }

    public DataHolderSymbol declareVariable(String name, Type type) {
        DataHolderSymbol variable;
        if (currentSymbol.isClass()) {
            variable = new FieldSymbol(name, type);
        } else {
            variable = new VariableSymbol(name, type);
        }
        addToScope(variable);
        return variable;
    }

    public DataHolderSymbol declareArray(String name, Type type) {
        ArrayType arrayType = new ArrayType(type);
        arrayType.owner = new ArrayTypeSymbol(arrayType);
        return declareVariable(name, arrayType);
    }

    public ParameterSymbol declareParameter(String name, Type type, boolean isArray) {
        if (isArray) {
            type = new ArrayType(type);
            type.owner = new ArrayTypeSymbol((ArrayType) type);
        }
        ParameterSymbol parameter = new ParameterSymbol(name, type);
        addToScope(parameter);
        return parameter;
    }

    public Writable closeScope() {
        Writable result = currentSymbol;
        currentSymbol.write();
        currentSymbol = (Writable) currentSymbol.getOwner();
        Tab.closeScope();
        return result;
    }

    public Writable currentWritable() { return currentSymbol; }

    public void dump(SymbolTableVisitor visitor) {
        currentSymbol.getScope().accept(visitor);
    }
}
