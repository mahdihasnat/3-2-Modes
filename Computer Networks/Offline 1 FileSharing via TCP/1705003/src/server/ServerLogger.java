package server;

import util.log.ConsoleLogger;
import util.log.FileLogger;
import util.log.LogLevel;
import util.log.Logger;

import java.io.File;

public class ServerLogger {
    private static Logger logger = new FileLogger(new File("server_log.txt"), LogLevel.DEBUG, LogLevel.ERROR).appendNext(new ConsoleLogger(LogLevel.INFO));

    public static Logger getLogger() {
        return logger;
    }

}
