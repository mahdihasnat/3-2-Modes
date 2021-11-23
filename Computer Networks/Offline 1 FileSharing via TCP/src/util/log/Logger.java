package util.log;

public interface Logger {
    void logMessage(LogLevel severity, String msg);

    Logger appendNext(Logger logger);

}
