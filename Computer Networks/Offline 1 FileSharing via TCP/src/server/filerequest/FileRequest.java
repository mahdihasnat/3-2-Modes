package server.filerequest;

public class FileRequest {
    String sid;
    String message;
    int requestId;

    public FileRequest(String sid, String message,int requestId) {
        this.sid = sid;
        this.message = message;
        this.requestId = requestId;
    }

    public String getSid() {
        return sid;
    }

    public String getMessage() {
        return message;
    }

    public int getRequestId() {
        return requestId;
    }


}
