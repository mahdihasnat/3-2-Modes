package server.file.buffer;

public class UserFileBuffer extends FileBuffer {
    public UserFileBuffer(String owner, String visibility, String fileName, long fileLength) {
        super(owner, visibility, fileName, fileLength);
    }

    @Override
    public String toString() {
        return "UserFileBuffer{" +
                "datas=" + datas +
                ", file=" + file +
                ", owner='" + owner + '\'' +
                '}';
    }
}
