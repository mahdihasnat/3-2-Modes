package server;

import server.file.UploadBuffer;
import server.file.buffer.FileBuffer;

import java.util.concurrent.ConcurrentMap;

public class BufferManagerThread extends Thread {
    public BufferManagerThread() {
        super();
    }

    @Override
    public void run() {
        UploadBuffer uploadBuffer = UploadBuffer.getInstance();

        while (true) {
            for (ConcurrentMap.Entry<Integer, FileBuffer> entry : uploadBuffer.getQueueOperation().entrySet()) {
                FileBuffer fileBuffer = entry.getValue();
                if (uploadBuffer.isBufferAvailable(fileBuffer.getFileLength())) {
                    int fileId = entry.getKey();
                    uploadBuffer.moveFileBuffer(fileId, fileBuffer);
                    if (!StudentDirectory.getInstance().sendBeginUpload(fileBuffer.getOwner(), fileId)) {
                        System.out.println("FileUpload operation aborted " + fileBuffer);
                        uploadBuffer.abortCurrentOperation(fileId);
                    }
                } else if (fileBuffer.getFileLength() > Settings.getInstance().getMAX_BUFFER_SIZE()) {
                    int fileId = entry.getKey();
                    StudentDirectory.getInstance().sendMessage(fileBuffer.getOwner(), "Upload Size exceeded Maximum allowed buffer in server! fileID = " + fileId);
                    uploadBuffer.discardQueueOperation(fileId);
                }
            }
        }
    }
}
