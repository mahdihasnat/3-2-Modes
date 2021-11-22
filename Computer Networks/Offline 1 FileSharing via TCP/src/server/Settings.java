package server;

import java.util.Random;

public class Settings {
    // sizes are in byte
    private long MAX_BUFFER_SIZE;
    private int MIN_CHUNK_SIZE;
    private int MAX_CHUNK_SIZE;
    private int PORT;
    private String path;

    private Settings() {
        MAX_BUFFER_SIZE = 1024 * 1024 * 1024;
        MIN_CHUNK_SIZE = 1;
        MAX_CHUNK_SIZE = 1;
        PORT = 60666;
        path = "data\\";
    }

    public String getPath() {
        return path;
    }

    public int getRandomChunkSize() {
        return new Random().nextInt(MAX_CHUNK_SIZE - MIN_CHUNK_SIZE) + MIN_CHUNK_SIZE;
    }

    public long getMAX_BUFFER_SIZE() {
        return MAX_BUFFER_SIZE;
    }

    public int getMAX_CHUNK_SIZE() {
        return MAX_CHUNK_SIZE;
    }

    public int getPORT() {
        return PORT;
    }


    private static Settings instance;

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

}
