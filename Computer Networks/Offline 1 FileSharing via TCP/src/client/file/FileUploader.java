package client.file;

import util.ContinuousChunkReader;

import java.io.*;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.Arrays;


public class FileUploader extends Thread {
    DataInputStream networkIn;
    DataOutputStream netWorkOut;
    int chunkSize;
    File file;
    int fileId;

    public FileUploader(DataInputStream networkIn, DataOutputStream netWorkOut, int chunkSize, File file, int fileId) {
        this.networkIn = networkIn;
        this.netWorkOut = netWorkOut;
        this.chunkSize = chunkSize;
        this.file = file;
        this.fileId = fileId;
    }

    @Override
    public void run() {

        System.out.println(this);
        try {
            long totalSent = 0;
            long totalLength = file.length();
            synchronized (netWorkOut) {
                netWorkOut.writeUTF("fileupload");
                netWorkOut.writeInt(fileId);
            }
            System.out.println("File Upload Started #"+fileId);
            ContinuousChunkReader continuousChunkReader = new ContinuousChunkReader(file);
            while (true) {
                byte[] data = new byte[chunkSize];
                int length = continuousChunkReader.readNextChunk(data);
                //System.out.println("Read length:"+length);
                if (length == -1) {
                    synchronized (netWorkOut) {
                        //System.out.println("Sending completion jinish");
                        netWorkOut.writeUTF("completeupload");
                        //netWorkOut.writeInt(fileId);
                        netWorkOut.flush();
                    }
                    //System.out.println("Completion sent");
                    String result = networkIn.readUTF();
                    //System.out.println("result:"+result);
                    if(result.equals("nack"))
                    {
                        System.out.println("File upload failed| Integrity check failed "+file.getName());
                    }
                    else
                    {
                        System.out.println("File Uploaded successfully #"+fileId);
                    }
                    break;
                } else {
                    synchronized (netWorkOut) {
                        netWorkOut.writeUTF("uploadchunk");
                        //netWorkOut.writeInt(fileId);
                        netWorkOut.writeInt(length);
                        //byte[] d = Arrays.copyOfRange(data,0,length);
                        //netWorkOut.write(d);
                        netWorkOut.write(data , 0,length);
                        netWorkOut.flush();
                        //System.out.printf("File Uploading %4.2f #%d\n",1.0*totalSent/totalLength , fileId);
                    }
                    //TODO
                    /// check for ack within 30 sec

                    String result = networkIn.readUTF();

                    assert (result.equals("ack"));
                }

            }
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println("Upload failed! " + file.getName() + " not found in local storage");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Upload failed! " + file.getName() + " can not be read");
        }
    }

    @Override
    public String toString() {
        return "FileUploader{" +
                "networkIn=" + networkIn +
                ", netWorkOut=" + netWorkOut +
                ", chunkSize=" + chunkSize +
                ", file=" + file +
                ", fileId=" + fileId +
                '}';
    }
}
