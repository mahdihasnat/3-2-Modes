package server.file.buffer;

import server.StudentDirectory;
import server.filerequest.FileRequest;

public class RequestFileBuffer extends FileBuffer {

    String fileName;
    FileRequest fileRequest;

    public RequestFileBuffer(String owner, String fileName, long fileLength, FileRequest fileRequest) {
        super(owner, "public", fileName, fileLength);
        this.fileName = fileName;
        this.fileRequest = fileRequest;
    }

    @Override
    public boolean writeFile() {
        boolean ret = super.writeFile();
        if (ret) {
            StudentDirectory studentDirectory = StudentDirectory.getInstance();
            studentDirectory.sendMessage(fileRequest.getSid(), getOwner() + " uploaded file " + fileName + " upon your request #" + fileRequest.getRequestId());
        }
        return ret;
    }

    @Override
    public String toString() {
        return "RequestFileBuffer{" +
                "datas=" + datas +
                ", file=" + file +
                ", owner='" + owner + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileRequest=" + fileRequest +
                '}';
    }
}
