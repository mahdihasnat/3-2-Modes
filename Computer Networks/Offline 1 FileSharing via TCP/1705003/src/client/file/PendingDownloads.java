package client.file;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PendingDownloads {
    private static File root = new File("downloads");
    private ConcurrentMap<Integer, FileDownloadBuffer> fileConcurrentMap;

    private PendingDownloads() {
        fileConcurrentMap = new ConcurrentHashMap<>();
        if (!root.exists())
            root.mkdirs();
    }

    public void addFileDownloadBuffer(int fileId, String fileName) {
        File file = new File(root, fileName);
        fileConcurrentMap.put(fileId, new FileDownloadBuffer(file));
    }

    public void addData(int fileId, byte[] data) {
        fileConcurrentMap.get(fileId).addData(data);
    }

    public void completeDownload(int fileId) {
        fileConcurrentMap.get(fileId).writeData();
        fileConcurrentMap.remove(fileId);
    }


    private static PendingDownloads instance;

    public static PendingDownloads getInstance() {
        if (instance == null) {
            instance = new PendingDownloads();
        }
        return instance;
    }

}
