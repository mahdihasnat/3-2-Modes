package server.file;


import server.Settings;
import server.file.buffer.FileBuffer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UploadBuffer {
    private ConcurrentMap<Integer, FileBuffer> queueOperation;
    private ConcurrentMap<Integer, FileBuffer> runningOperation;
    private int nextFileId;
    private Long currentBufferSize ;


    private UploadBuffer() {
        queueOperation = new ConcurrentHashMap<>();
        runningOperation = new ConcurrentHashMap<>();
        nextFileId = 1;
        currentBufferSize = new Long(0);
    }

    public long getCurrentBufferSize() {
        return currentBufferSize;
    }

    public boolean isBufferAvailable(long newFileSize)
    {
        synchronized (currentBufferSize)
        {
            if(currentBufferSize+newFileSize <= Settings.getInstance().getMAX_BUFFER_SIZE())
            {
                currentBufferSize+=newFileSize;
                return true;
            }
            else return false;
        }
    }
    public void releaseBuffer(long bufferSize)
    {
        synchronized (currentBufferSize)
        {
            assert (bufferSize <= currentBufferSize);
            currentBufferSize-=bufferSize;
        }
    }

    public FileBuffer getRunningFileBuffer(int fileId)
    {
        return runningOperation.get(fileId);
    }

    public void abortCurrentOperation(int fileId)
    {
        runningOperation.remove(fileId);
    }
    public void discardQueueOperation(int fileId)
    {
        queueOperation.remove(fileId);
    }

    public ConcurrentMap<Integer, FileBuffer> getQueueOperation() {
        return queueOperation;
    }

    public void moveFileBuffer(Integer fileId, FileBuffer fileBuffer) {
        queueOperation.remove(fileId, fileBuffer);
        runningOperation.put(fileId, fileBuffer);
    }

    public int addOperation(FileBuffer fileBuffer) {
        int fileId = nextFileId++;
        queueOperation.put(fileId, fileBuffer);
        System.out.println("One Upload Request added to Queue :" + fileBuffer);
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
