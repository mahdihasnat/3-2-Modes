package util.log;

public enum LogLevel {
    DEBUG, ERROR, INFO;

    public static LogLevel[] all() {
        return values();
    }
}
