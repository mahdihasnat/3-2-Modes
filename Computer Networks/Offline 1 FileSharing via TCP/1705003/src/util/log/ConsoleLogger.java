package util.log;

public class ConsoleLogger extends AbstractLogger {

    public ConsoleLogger(LogLevel... levels) {
        super(levels);
    }

    @Override
    public void printMessage(String msg) {
        System.out.println("ConsoleLogger:" + msg);
    }
}
