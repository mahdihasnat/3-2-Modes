package client.file;

import util.DuplicateFileSave;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileDownloadBuffer {
    List<byte[]> datas;
    File file;

    public FileDownloadBuffer(File file) {
        this.datas = new LinkedList<>();
        this.file = file;
    }

    public void addData(byte[] data) {
        datas.add(data);
    }

    public void writeData() {
        file = DuplicateFileSave.getUniqueFile(file);
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (byte[] data : datas)
                fileOutputStream.write(data);
            fileOutputStream.close();
            datas.clear();// data removed
            System.out.println("Downloaded file saved as " + file.getName());
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Cant save downloaded file to " + file.getAbsolutePath());
        }

    }

}
