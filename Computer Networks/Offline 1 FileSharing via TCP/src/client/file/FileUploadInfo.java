package client.file;

import java.io.File;

public class FileUploadInfo {
    File file;
    int fileId;

    public FileUploadInfo(File file) {
        this.file = file;
        this.fileId = -1;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "FileUploadInfo{" +
                "file=" + file +
                ", fileId=" + fileId +
                '}';
    }
}
