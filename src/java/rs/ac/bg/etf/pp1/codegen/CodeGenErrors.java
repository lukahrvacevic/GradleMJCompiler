package rs.ac.bg.etf.pp1.codegen;

import rs.ac.bg.etf.pp1.logger.DiagnosticError;

public abstract class CodeGenErrors {

    public static final class CodeTooBig extends DiagnosticError {
        public CodeTooBig() {
            super("Code too big.");
        }
    }

    public static final class UnexpectedOpcode extends DiagnosticError {
        public UnexpectedOpcode(int opcode) {
            super("Unexpected opcode: " + opcode);
        }
    }

}
