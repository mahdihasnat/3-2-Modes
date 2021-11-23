package server.file;

import server.ServerLogger;
import server.Settings;
import server.file.buffer.FileBuffer;
import util.log.LogLevel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UploadBuffer {
    private final ConcurrentMap<Integer, FileBuffer> queueOperation;
    private final ConcurrentMap<Integer, FileBuffer> runningOperation;
    private int nextFileId;
    private long currentBufferSize;
    private final Object bufferSizeLock = new Object();

    private UploadBuffer() {
        queueOperation = new ConcurrentHashMap<>();
        runningOperation = new ConcurrentHashMap<>();
        nextFileId = 1;
        currentBufferSize = 0;
    }

    public int getNewFileId() {
        return nextFileId++;
    }

    public boolean isBufferAvailable(long newFileSize) {
        synchronized (bufferSizeLock) {
            if (currentBufferSize + newFileSize <= Settings.getInstance().getMAX_BUFFER_SIZE()) {
                currentBufferSize += newFileSize;
                ServerLogger.getLogger().logMessage(LogLevel.DEBUG,
                        "Current buffer size: " + currentBufferSize + " after grant");
                return true;
            } else
                return false;
        }
    }

    public void releaseBuffer(long bufferSize) {
        synchronized (bufferSizeLock) {
            currentBufferSize -= bufferSize;
            ServerLogger.getLogger().logMessage(LogLevel.DEBUG, "releaseBuffer.bufferSize:" + bufferSize);
            ServerLogger.getLogger().logMessage(LogLevel.DEBUG,
                    "releaseBuffer.currentBufferSize:" + currentBufferSize + " after release");
        }
    }

    public FileBuffer getRunningFileBuffer(int fileId) {
        return runningOperation.get(fileId);
    }

    public void abortCurrentOperation(int fileId) {
        runningOperation.remove(fileId);
    }

    public void discardQueueOperation(int fileId) {
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
        ServerLogger.getLogger().logMessage(LogLevel.INFO, "One Upload Request added to Queue :" + fileBuffer);
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
