package client.file;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PendingUploads {
    Queue<FileUploadInfo> concurrentLinkedQueue ;


    private PendingUploads() {
        this.concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
    }

    public void add(File file) {
        this.concurrentLinkedQueue.add(new FileUploadInfo(file));
    }

    public boolean assignFIllId(String fileName, int fileId)
    {
        for(FileUploadInfo fileUploadInfo : concurrentLinkedQueue)
        {
            if(fileUploadInfo.getFile().getName().equals(fileName))
            {
                fileUploadInfo.setFileId(fileId);
                return true;
            }
        }
        return false;
    }
    public File getFile(int fileId)
    {
        for(FileUploadInfo fileUploadInfo:  concurrentLinkedQueue)
        {
            if(fileUploadInfo.getFileId() == fileId)
            {
                concurrentLinkedQueue.remove(fileUploadInfo);
                return fileUploadInfo.getFile();
            }
        }
        return null;
    }
    public void removeFile(String fileName)
    {
        for(FileUploadInfo fileUploadInfo : concurrentLinkedQueue)
        {
            if(fileUploadInfo.getFile().getName().equals(fileName))
            {
                concurrentLinkedQueue.remove(fileUploadInfo);
                return ;
            }
        }
    }

    
    
    private static PendingUploads instance;

    public static PendingUploads getInstance() {
        if (instance == null) {
            instance = new PendingUploads();
        }
        return instance;
    }
}
