package server.file.buffer;

import server.Settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class FileBuffer {
    List<byte[]> datas;
    File file;
    String owner;
    private long fileLength;

    public FileBuffer(String owner, String visibility, String fileName, long fileLength) {
        this.file = new File(Settings.getInstance().getPath() + owner + "\\" + visibility, fileName);
        this.datas = new LinkedList<byte[]>();
        this.owner = owner;
        this.fileLength = fileLength;
    }

    final public void addData(byte[] data) {
        datas.add(data);
    }

    final public String getOwner() {
        return owner;
    }

    final public long getFileLength() {
        return fileLength;
    }

    /// call this only one time
    // true if data is written and removed from heap
    // false otherwise
    public boolean writeFile() {

        //System.out.println(file.getAbsolutePath());
        if (file.exists())
            file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("File creation error : " + file.getName());
            return false;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (byte[] data : datas) {
                fileOutputStream.write(data);
            }
            fileOutputStream.close();
            datas.clear(); //data removed
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found! write error " + file.getName());
            //e.printStackTrace();
            return false;
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("File write error " + file.getName());
            return false;
        }
    }

    final public boolean checkIntegrity()
    {
        long totalLength = 0;
        for(byte[] d: datas)
            totalLength+=d.length;
        return totalLength == fileLength;
    }

    public static void main(String[] args) {
        FileBuffer fileBuffer = new UserFileBuffer("1", "public", "a.txt", 7);
        byte[] b = {'a', 'b'};
        fileBuffer.addData(b);
        fileBuffer.addData(b);
        byte[] c = {'x', 'y', 'z'};
        fileBuffer.addData(c);
        System.out.println(fileBuffer.writeFile());
    }


    @Override
    public String toString() {
        return "FileBuffer{" +
                "datas=" + datas +
                ", file=" + file +
                ", owner='" + owner + '\'' +
                ", fileLength=" + fileLength +
                '}';
    }
}
