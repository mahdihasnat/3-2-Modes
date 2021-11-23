package util;

import client.ClientLogger;
import util.log.LogLevel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ContinuousChunkReader {
    FileInputStream fis;

    public ContinuousChunkReader(File file) throws FileNotFoundException {
        this.fis = new FileInputStream(file);
    }

    public int readNextChunk(byte[] data) throws IOException {
        return fis.read(data);
    }

    public void close() {
        try {
            fis.close();
        } catch (IOException e) {
            ClientLogger.getLogger().logMessage(LogLevel.ERROR, e.toString());
            //e.printStackTrace();
            System.out.println("cannot close fileInputStream");
        }
    }

    public static void main(String[] args) {
        File f = new File("readme.md");
        try {
            ContinuousChunkReader continuousChunkReader = new ContinuousChunkReader(f);
            int tot = (int) (f.length() / 5) + 2;
            while (tot-- > 0) {
                byte[] x = new byte[5];
                System.out.println(continuousChunkReader.readNextChunk(x));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
