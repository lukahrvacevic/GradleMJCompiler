package rs.ac.bg.etf.pp1.logger;

import rs.ac.bg.etf.pp1.util.Context;

import java.util.ArrayList;
import java.util.List;

public final class CompilerDiagnostics {
    private static final Context.Key<CompilerDiagnostics> diagnosticsKey = new Context.Key<>();

    private final List<Diagnostic> diagnostics = new ArrayList<>();

    private CompilerDiagnostics(Context context) {
        context.put(diagnosticsKey, this);
    }

    public static CompilerDiagnostics getInstance(Context context) {
        CompilerDiagnostics cd = context.get(diagnosticsKey);
        if (cd == null) {
            cd = new CompilerDiagnostics(context);
        }
        return cd;
    }

    public void report(Diagnostic diagnostic) {
        diagnostics.add(diagnostic);
    }

    public void reportError(String message) {
        report(new Diagnostic(LogLevel.ERROR, message, -1));
    }

    public void report(DiagnosticError error) {
        report(new Diagnostic(error));
    }

    public boolean hasErrors() {
        return diagnostics.stream().anyMatch(Diagnostic::isError);
    }

    public List<Diagnostic> all() {
        return diagnostics;
    }

    public static final class Diagnostic {
        private final LogLevel level;
        private final String message;
        private final int line;

        public Diagnostic(LogLevel level, String message, int line) {
            this.level = level;
            this.message = message;
            this.line = line;
        }

        public Diagnostic(DiagnosticError error) {
            this(LogLevel.ERROR, error.getMessage(), error.getLine());
        }

        public LogLevel getLevel() {
            return level;
        }

        public String getMessage() {
            return message;
        }

        public int getLine() {
            return line;
        }

        public boolean isError() {
            return level == LogLevel.ERROR;
        }
    }
}
