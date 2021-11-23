package util.log;

import java.io.File;

public class LoggerMain {

    public static void main(String[] args) {
        Logger logger = new ConsoleLogger(LogLevel.all()).appendNext(new FileLogger(new File("log.txt"), LogLevel.all()));
        int tot = 100;
        while (tot-- > 0)
            logger.logMessage(LogLevel.DEBUG, "hello");
    }


}
