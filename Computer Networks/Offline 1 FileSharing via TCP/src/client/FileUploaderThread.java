package client;

import util.ContinuousChunkReader;
import util.log.LogLevel;

import java.io.*;


public class FileUploaderThread extends Thread {
    DataInputStream networkIn;
    DataOutputStream netWorkOut;
    int chunkSize;
    File file;
    int fileId;

    public FileUploaderThread(DataInputStream networkIn, DataOutputStream netWorkOut, int chunkSize, File file, int fileId) {
        this.networkIn = networkIn;
        this.netWorkOut = netWorkOut;
        this.chunkSize = chunkSize;
        this.file = file;
        this.fileId = fileId;
    }

    @Override
    public void run() {

        ClientLogger.getLogger().logMessage(LogLevel.DEBUG, this.toString() + " .run");


        long totalSent = 0;
        long totalLength = file.length();
        synchronized (netWorkOut) {
            try {
                netWorkOut.writeUTF("fileupload");
                netWorkOut.writeInt(fileId);
            } catch (IOException e) {
                ClientLogger.getLogger().logMessage(LogLevel.ERROR, e.toString());
                ClientLogger.getLogger().logMessage(LogLevel.DEBUG, this.toString());
                return;
            }

        }
        System.out.println("File Upload Started #" + fileId);
        ContinuousChunkReader continuousChunkReader = null;
        try {
            continuousChunkReader = new ContinuousChunkReader(file);
        } catch (FileNotFoundException e) {
            ClientLogger.getLogger().logMessage(LogLevel.ERROR, e.toString());
            ClientLogger.getLogger().logMessage(LogLevel.DEBUG, this.toString());
            System.out.println("Upload failed! " + file.getName() + " not found in local storage");
            return;
        }
        while (true) {
            byte[] data = new byte[chunkSize];
            int length = 0;
            try {
                length = continuousChunkReader.readNextChunk(data);
            } catch (IOException e) {
                ClientLogger.getLogger().logMessage(LogLevel.ERROR, e.toString());
                ClientLogger.getLogger().logMessage(LogLevel.DEBUG, this + ": " + e.toString());

                System.out.println("Upload failed! " + file.getName() + " can not be read");
                continuousChunkReader.close();
                return;
            }

            if (length == -1) {
                continuousChunkReader.close();
                ClientLogger.getLogger().logMessage(LogLevel.DEBUG, "FileUploaderThread.run:All data send #" + fileId);
                synchronized (netWorkOut) {
                    //System.out.println("Sending completion jinish");
                    try {
                        netWorkOut.writeUTF("completeupload");
                        netWorkOut.flush();
                    } catch (IOException e) {
                        ClientLogger.getLogger().logMessage(LogLevel.ERROR, e.toString());
                        ClientLogger.getLogger().logMessage(LogLevel.DEBUG, this.toString());
                        System.out.println("Upload failed! #" + fileId);
                        continuousChunkReader.close();
                        return;
                    }

                }
                //System.out.println("Completion sent");
                String result = null;
                try {
                    result = networkIn.readUTF();
                } catch (IOException e) {
                    ClientLogger.getLogger().logMessage(LogLevel.ERROR, e.toString());
                    ClientLogger.getLogger().logMessage(LogLevel.DEBUG, this.toString());
                    System.out.println("Upload failed! #" + fileId);
                }
                //System.out.println("result:"+result);
                if (result.equals("nack")) {
                    System.out.println("File upload failed| Integrity check failed " + file.getName());
                } else {
                    System.out.println("File Uploaded successfully #" + fileId);
                }
                break;
            } else {
                synchronized (netWorkOut) {
                    try {
                        netWorkOut.writeUTF("uploadchunk");
                        netWorkOut.writeInt(length);
                        netWorkOut.write(data, 0, length);
                        netWorkOut.flush();
                        totalSent += length;
                    } catch (IOException e) {
                        ClientLogger.getLogger().logMessage(LogLevel.ERROR, e.toString());
                        ClientLogger.getLogger().logMessage(LogLevel.DEBUG, this.toString());
                        System.out.println("Upload failed! #" + fileId);
                        continuousChunkReader.close();
                        return;
                    }
                    //System.out.printf("File Uploading %4.2f%% #%d\n",1.0*totalSent*100/totalLength , fileId);
                }
                /// check for ack within 30 sec

                long timeLimit = 30000;// in milisec

                long start = System.currentTimeMillis();
                boolean ackReceived = false;

                while (System.currentTimeMillis() - start <= timeLimit) {
                    try {
                        if (networkIn.available() > 0) {
                            ackReceived = networkIn.readUTF().equals("ack");
                            break;
                        }
                    } catch (IOException e) {
                        ClientLogger.getLogger().logMessage(LogLevel.ERROR, e.toString());
                        ClientLogger.getLogger().logMessage(LogLevel.DEBUG, this.toString());
                        System.out.println("Upload failed! #" + fileId);
                        continuousChunkReader.close();
                        return;
                    }
                }
                if (!ackReceived) {
                    try {
                        netWorkOut.writeUTF("abort");
                        netWorkOut.flush();
                    } catch (IOException e) {
                        ClientLogger.getLogger().logMessage(LogLevel.ERROR, e.toString());
                        ClientLogger.getLogger().logMessage(LogLevel.DEBUG, this.toString());
                        System.out.println("Upload failed! #" + fileId);
                        continuousChunkReader.close();
                        return;
                    }
                    ClientLogger.getLogger().logMessage(LogLevel.DEBUG , String.format("uploaded file %5.2f" , totalSent*100.0/totalLength));
                    System.out.println("File upload failed! poor network connection. #" + fileId);
                    break;
                }
            }
        }
        continuousChunkReader.close();

    }

    @Override
    public String toString() {
        return "FileUploaderThread{" +
                "networkIn=" + networkIn +
                ", netWorkOut=" + netWorkOut +
                ", chunkSize=" + chunkSize +
                ", file=" + file +
                ", fileId=" + fileId +
                '}';
    }
}
