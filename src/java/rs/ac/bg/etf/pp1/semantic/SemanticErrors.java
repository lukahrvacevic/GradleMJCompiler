package rs.ac.bg.etf.pp1.semantic;

import rs.ac.bg.etf.pp1.logger.DiagnosticError;
import rs.ac.bg.etf.pp1.symbols.Symbol;
import rs.ac.bg.etf.pp1.symbols.Symbol.*;
import rs.ac.bg.etf.pp1.symbols.Type;

public abstract class SemanticErrors {

    public static final class NameOverridesSuper extends DiagnosticError {
        public NameOverridesSuper(Symbol member, Symbol superMember, int line) {
            super(member.getKindName() + " '" + member + "' overrides " + superMember.getKindName() + " inherited from super class.", line);
        }
    }

    public static final class MethodDoesNotMatchSuper extends DiagnosticError {
        public MethodDoesNotMatchSuper(MethodSymbol member, int line) {
            super("Method '" + member.getFullName() + "' does not match super.", line);
        }
    }

    public static final class AbstractMethodsNotImplemented extends DiagnosticError {
        public AbstractMethodsNotImplemented(int line) {
            super("Class much implent all abstract methods.", line);
        }
    }

    public static final class CannotExtendFromType extends DiagnosticError {
        public CannotExtendFromType(Type type, int line) {
            super("Cannot extend from type '" + type + "'.", line);
        }
    }

    public static final class UninitializedConstant extends DiagnosticError {
        public UninitializedConstant(int line) {
            super("Constant might not be initialized.", line);
        }
    }

    public static final class ConstantDeclaredOutsideGlobalScope extends DiagnosticError {
        public ConstantDeclaredOutsideGlobalScope(int line) {
            super("Constants can only be declared in the global scope.", line);
        }
    }

    public static final class ConstantNotPrimitive extends DiagnosticError {
        public ConstantNotPrimitive(int line) {
            super("Constants must be primitive type.", line);
        }
    }

    public static final class InitializedVariable extends DiagnosticError {
        public InitializedVariable(int line) {
            super("Variable cannot be initialized here.", line);
        }
    }

    public static final class ConstantArray extends DiagnosticError {
        public ConstantArray(int line) {
            super("Arrays cannot be constant.", line);
        }
    }

    public static final class NameAlreadyDeclared extends DiagnosticError {
        public NameAlreadyDeclared(String name, int line) {
            super("Name '" + name + "' already declared.", line);
        }
    }

    public static final class RepeatedModifier extends DiagnosticError {
        public RepeatedModifier(String modifier, int line) {
            super("Repeated modifier '" + modifier + "'.", line);
        }
    }

    public static final class AbstractMethodWithBody extends DiagnosticError {
        public AbstractMethodWithBody(int line) {
            super("Abstract method cannot have a body.", line);
        }
    }

    public static final class MethodWithoutBody extends DiagnosticError {
        public MethodWithoutBody(int line) {
            super("Method must be implemented or marked as abstract.", line);
        }
    }

    public static final class MissingReturnValue extends DiagnosticError {
        public MissingReturnValue(int line) {
            super("Method must return a value.", line);
        }
    }

    public static final class VoidMethodReturns extends DiagnosticError {
        public VoidMethodReturns(int line) {
            super("Cannot return a value from a method with void result type.", line);
        }
    }

    public static final class NotCallable extends DiagnosticError {
        public NotCallable(String name, int line) {
            super("'" + name + "' is not callable.", line);
        }
    }

    public static final class ArgumentsNotMatching extends DiagnosticError {
        public ArgumentsNotMatching(String expected, String got, int line) {
            super("Expected: " + expected + ", got: " + got, line);
        }

        public ArgumentsNotMatching(Type expected, Type got, int line) {
            super("Expected: " + expected + ", got: " + got, line);
        }
    }

    public static final class NotAllowedHere extends DiagnosticError {
        public NotAllowedHere(String what, int line) {
            super("'" + what + "' not allowed here.", line);
        }
    }

    public static final class TypeNotFound extends DiagnosticError {
        public TypeNotFound(String name, int line) {
            super("Type '" + name + "' not found.", line);
        }
    }

    public static final class NotAType extends DiagnosticError {
        public NotAType(String name, int line) {
            super("'" + name + "' is not a type.", line);
        }
    }

    public static final class NotAssignable extends DiagnosticError {
        public NotAssignable(Type expected, Type got, int line) {
            super("Cannot convert '" + got + "' to '" + expected + "'.", line);
        }
    }

    public static final class NameNotFound extends DiagnosticError {
        public NameNotFound(String name, int line) {
            super("Name '" + name + "' not found.", line);
        }
    }

    public static final class NotAnArray extends DiagnosticError {
        public NotAnArray(String name, int line) {
            super("'" + name + "' is not an array.", line);
        }
    }

    public static final class CanOnlyBeUsedOnArrays extends DiagnosticError {
        public CanOnlyBeUsedOnArrays(String name, int line) {
            super("'" + name + "' can only be used on arrays.", line);
        }
    }

    public static final class CannotAccessFieldOnType extends DiagnosticError {
        public CannotAccessFieldOnType(Type type, int line) {
            super("Cannot access field on type '" + type + "'.", line);
        }
    }

    public static final class FieldNotFound extends DiagnosticError {
        public FieldNotFound(String name, Type type, int line) {
            super("Field '" + name + "' not found on type '" + type + "'.", line);
        }
    }

    public static final class OperatorCannotBeApplied extends DiagnosticError {
        public OperatorCannotBeApplied(String operator, Type left, Type right, int line) {
            super("Operator '" + operator + "' cannot be applied to types '" + left + "' and '" + right + "'.", line);
        }
    }

    public static final class ClassExpected extends DiagnosticError {
        public ClassExpected(int line) {
            super("Class expected.", line);
        }
    }

    public static final class VariableExpected extends DiagnosticError {
        public VariableExpected(int line) {
            super("Variable expected.", line);
        }
    }

    public static final class LocalOrGlobalVariableExpected extends DiagnosticError {
        public LocalOrGlobalVariableExpected(int line) {
            super("Local or global variable expected.", line);
        }
    }

    public static final class CannotPrintNonPrimitiveType extends DiagnosticError {
        public CannotPrintNonPrimitiveType(int line) {
            super("Cannot print non-primitive type.", line);
        }
    }

    public static final class TernaryOperatorTypesMismatch extends DiagnosticError {
        public TernaryOperatorTypesMismatch(Type trueType, Type falseType, int line) {
            super("Ternary operator types mismatch: " + trueType + " and " + falseType + ".", line);
        }
    }
}
