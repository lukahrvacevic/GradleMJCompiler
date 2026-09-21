package rs.ac.bg.etf.pp1.logger;

public abstract class DiagnosticError {
    private final String message;
    private final int line;

    public DiagnosticError(String message) {
        this(message, -1);
    }

    public DiagnosticError(String message, int line) {
        this.message = message;
        this.line = line;
    }

    public String getMessage() {
        return message;
    }

    public int getLine() {
        return line;
    }
}
