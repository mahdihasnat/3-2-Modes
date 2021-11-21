package server;

import server.file.UploadBuffer;
import server.file.buffer.FileBuffer;


import java.util.concurrent.ConcurrentMap;

public class BufferManagerThread extends Thread{
    public BufferManagerThread() {
        super();
    }

    @Override
    public void run() {
        UploadBuffer uploadBuffer = UploadBuffer.getInstance();
        while(true)
        {

        }
    }
}
