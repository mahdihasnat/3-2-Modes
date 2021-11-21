package server.file;


import server.file.buffer.FileBuffer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UploadBuffer {
    ConcurrentMap<Integer , FileBuffer> queueOperation;
    ConcurrentMap<Integer , FileBuffer > runningOperation;
    int nextFileId ;

    private UploadBuffer() {
        queueOperation = new ConcurrentHashMap<>();
        runningOperation = new ConcurrentHashMap<>();
        nextFileId = 1;
    }

    public ConcurrentMap<Integer, FileBuffer> getQueueOperation() {
        return queueOperation;
    }
    public void moveFileBuffer(Integer fileId, FileBuffer fileBuffer)
    {
        queueOperation.remove(fileId,fileBuffer);
        runningOperation.put(fileId,fileBuffer);
    }

    public int addOperation(FileBuffer fileBuffer)
    {
        int fileId = nextFileId++;
        queueOperation.put(fileId , fileBuffer);
        System.out.println("One Upload Request added to Queue :" +fileBuffer);
        return fileId;
    }

    

    private static UploadBuffer instance;
    public static UploadBuffer getInstance() {
        if (instance == null) {
            instance = new UploadBuffer();
        }
        return instance;
    }

}
