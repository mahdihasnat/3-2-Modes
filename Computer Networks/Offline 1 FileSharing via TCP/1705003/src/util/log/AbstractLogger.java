package util.log;

import java.util.Arrays;
import java.util.EnumSet;

public abstract class AbstractLogger implements Logger {
    private Logger nextLogger;
    private final EnumSet<LogLevel> set;

    public AbstractLogger(LogLevel... levels) {
        this.set = EnumSet.copyOf(Arrays.asList(levels));
    }

    @Override
    public final void logMessage(LogLevel severity, String msg) {
        if (set.contains(severity)) {
            printMessage(severity.toString() + ":" + msg);
        }
        if (nextLogger != null) nextLogger.logMessage(severity, msg);
    }

    public abstract void printMessage(String msg);

    @Override
    public final Logger appendNext(Logger logger) {
        nextLogger = logger;
        return this;
    }

}
