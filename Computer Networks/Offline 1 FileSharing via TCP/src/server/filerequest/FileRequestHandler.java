package server.filerequest;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FileRequestHandler {
    private int nextRequestId;
    private ConcurrentMap<Integer,FileRequest > fileRequestConcurrentMap;

    public FileRequestHandler() {
        nextRequestId = 1;
        fileRequestConcurrentMap = new ConcurrentHashMap<>();
    }
    public FileRequest addNewRequest(String sid,String msg)
    {
        FileRequest fileRequest = new FileRequest(sid , msg , nextRequestId++);
        fileRequestConcurrentMap.put(fileRequest.getRequestId() , fileRequest);
        return fileRequest;
    }
    public FileRequest getFileRequest(int requestId)
    {
        return fileRequestConcurrentMap.get(requestId);
    }



    private static FileRequestHandler instance;
    public static FileRequestHandler getInstance() {
        if (instance == null) {
            instance = new FileRequestHandler();
        }
        return instance;
    }
    
}
