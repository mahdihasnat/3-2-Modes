package client.file;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UploadRequests {
    Queue<FileUploadInfo> concurrentLinkedQueue ;


    private UploadRequests() {
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

    
    
    private static UploadRequests instance;

    public static UploadRequests getInstance() {
        if (instance == null) {
            instance = new UploadRequests();
        }
        return instance;
    }
}
