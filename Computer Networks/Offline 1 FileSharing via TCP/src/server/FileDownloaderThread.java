package server;

import java.io.*;

public class FileDownloaderThread extends Thread {
    File file;
    int chunkSize;
    int fileId;
    DataOutputStream netWorkOut;

    public FileDownloaderThread(File file, int chunkSize, int fileId, DataOutputStream netWorkOut) {
        this.file = file;
        this.chunkSize = chunkSize;
        this.fileId = fileId;
        this.netWorkOut = netWorkOut;
    }

    @Override
    public void run() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            try {
                synchronized (netWorkOut) {
                    netWorkOut.writeUTF("print");
                    netWorkOut.writeUTF("Download Error|File cannot be read in server");
                    netWorkOut.flush();
                }
            } catch (IOException ioException) {
                //ioException.printStackTrace();

            }
            return;
        }

        synchronized (netWorkOut) {
            try {

                netWorkOut.writeUTF("downloadbegin");
                netWorkOut.writeInt(fileId);
                netWorkOut.writeUTF(file.getName());
                netWorkOut.flush();

            } catch (IOException e) {
                //e.printStackTrace();
                return;
            }
        }

        while (true) {
            if (fileInputStream == null)
                break;
            byte[] datas = new byte[chunkSize];
            try {
                int lengthRead = fileInputStream.read(datas);
                if (lengthRead == -1) {
                    synchronized (netWorkOut) {
                        netWorkOut.writeUTF("downloadcomplete");
                        netWorkOut.writeInt(fileId);
                        netWorkOut.flush();
                    }
                    break;
                } else {
                    synchronized (netWorkOut) {
                        netWorkOut.writeUTF("downloadchunk");
                        netWorkOut.writeInt(fileId);
                        netWorkOut.writeInt(lengthRead);
                        netWorkOut.write(datas, 0, lengthRead);
                        netWorkOut.flush();
                    }
                }

            } catch (IOException e) {
                synchronized (netWorkOut) {
                    try {
                        netWorkOut.writeUTF("downloadabort");
                        netWorkOut.writeInt(fileId);
                        netWorkOut.flush();
                    } catch (IOException ex) {
                        //ex.printStackTrace();
                        return;
                    }
                }
            }
        }
    }
}
