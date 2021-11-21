package client.file;

import util.ContinuousChunkReader;

import java.io.*;


public class FileUploader extends Thread{
    DataInputStream networkIn;
    DataOutputStream netWorkOut;
    int chunkSize;
    File file;
    String fileId;

    public FileUploader(DataInputStream networkIn, DataOutputStream netWorkOut, int chunkSize, File file, String fileId) {
        this.networkIn = networkIn;
        this.netWorkOut = netWorkOut;
        this.chunkSize = chunkSize;
        this.file = file;
        this.fileId = fileId;
    }

    @Override
    public void run() {
        try {
            ContinuousChunkReader continuousChunkReader = new ContinuousChunkReader(file);
            while (true)
            {
                byte [] data = new byte[chunkSize];
                int length = continuousChunkReader.readNextChunk(data);
                if(length == -1 )
                {
                    synchronized (netWorkOut) {
                        netWorkOut.writeUTF("completeupload");
                        netWorkOut.writeUTF(fileId);
                    }
                    break;
                }
                else {
                    synchronized (netWorkOut)
                    {
                        netWorkOut.writeUTF("uploadchunk");
                        netWorkOut.writeUTF(fileId);
                        netWorkOut.writeInt(length);
                        netWorkOut.write(data);
                    }
                }
                /// check for ack within 30 sec
                ///
            }
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println("Upload failed! " + file.getName() + " not found in local storage");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Upload failed! "+ file.getName() + " can not be read");
        }


    }
}
