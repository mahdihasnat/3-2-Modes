package util.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class FileLogger extends AbstractLogger {
    private final File file;

    public FileLogger(File file, LogLevel... levels) {
        super(levels);
        this.file = file;
    }


    @Override
    public void printMessage(String msg) {
        file.getAbsoluteFile().getParentFile().mkdirs();
        PrintStream printStream;
        try {
            file.createNewFile();
            printStream = new PrintStream(new FileOutputStream(file, true));
            printStream.println(msg);
            printStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
