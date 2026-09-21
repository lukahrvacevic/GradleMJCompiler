package rs.ac.bg.etf.pp1.logger;

import rs.ac.bg.etf.pp1.util.Context;
import rs.ac.bg.etf.pp1.logger.CompilerDiagnostics.Diagnostic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CompilerLogger {

    private static final Context.Key<CompilerLogger> loggerKey = new Context.Key<>();

    private final List<String> messages = new ArrayList<>();

    public static CompilerLogger getInstance(Context context) {
        CompilerLogger logger = context.get(loggerKey);
        if (logger == null) {
            logger = new CompilerLogger(context);
        }
        return logger;
    }
    private CompilerLogger(Context context) {
        context.put(loggerKey, this);
    }

    public void render(Diagnostic diagnostic) {
        String format = "[" + diagnostic.getLevel() + "]"
                + (diagnostic.getLine() >= 0 ? " at line " + diagnostic.getLine() : "")
                + ": " + diagnostic.getMessage();
        messages.add(format);
        System.err.println(format);
    }

    public void renderAll(List<Diagnostic> diagnostics) {
        for (Diagnostic diagnostic : diagnostics) {
            render(diagnostic);
        }
    }

    public void writeToFile(Path file) throws IOException {
        Files.write(file, messages);
    }
}
