package server.file.buffer;

import server.StudentDirectory;

import java.io.File;

public class RequestFileBuffer extends FileBuffer{

    String requester;
    String fileName;
    String requestId;

    public RequestFileBuffer(String owner, String requestId, String fileName,long fileLength, String requester) {
        super(owner, "public", fileName,fileLength);
        this.fileName=fileName;
        this.requester = requester;
        this.requestId = requestId;
    }

    @Override
    boolean writeFile() {
        boolean ret = super.writeFile();
        if(ret)
        {
            StudentDirectory studentDirectory = StudentDirectory.getInstance();
            studentDirectory.sendMessage(requester , getOwner() + " uploaded file "+fileName + " upon your request #"+requestId );
        }
        return ret;
    }
}
