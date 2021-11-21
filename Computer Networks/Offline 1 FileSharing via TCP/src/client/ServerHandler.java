package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import client.file.FileUploadInfo;
import client.file.FileUploader;
import client.file.PendingUploads;
import client.message.Message;
import client.message.MessageQueue;

public class ServerHandler extends Thread {
    DataInputStream dataInputStream;

    public ServerHandler(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
        
        MessageQueue messageQueue = MessageQueue.getInstance();
        PendingUploads pendingUploads = PendingUploads.getInstance();

        try {
            while (true) {

                String reqCode = dataInputStream.readUTF();


                System.out.println("received req code :"+reqCode);

                if(reqCode.equals( "allstudents"))
                {
                    System.out.println(dataInputStream.readUTF());
                }
                else if(reqCode.equals("allfiles"))
                {
                    System.out.println(dataInputStream.readUTF());
                }
                else if(reqCode.equals("myfiles"))
                {
                    System.out.println(dataInputStream.readUTF());
                }
                else if(reqCode.equals("message"))
                {
                    Message message = new Message(dataInputStream.readUTF());
                    messageQueue.addMessage(message);
                }
                else if(reqCode.equals("fileid"))
                {
                    String fileName = dataInputStream.readUTF();
                    int fileId = dataInputStream.readInt();
                    System.out.println("FileId received for "+fileName + " is "+fileId);
                    if(!pendingUploads.assignFIllId(fileName,fileId))
                    {
                        System.out.println("FileUploadInfo not found in queue!");
                    }
                }
                else if(reqCode.equals("fileiderror"))
                {
                    String fileName = dataInputStream.readUTF();
                    pendingUploads.removeFile(fileName);
                }
                else if(reqCode.equals("beginupload"))
                {
                    int fileId = dataInputStream.readInt();
                    int chunkSize = dataInputStream.readInt();
                    System.out.println("Server granted to upload file. "+fileId);
                    File file = PendingUploads.getInstance().getFile(fileId);
                    if(file == null)
                    {
                        System.out.println("File not found in pending uploads! #" +fileId);
                    }
                    else
                    {
                        Socket socket = new Socket(Client.ipAddress , Client.port);
                        Thread fileUploader = new FileUploader(
                                new DataInputStream(socket.getInputStream()),
                                new DataOutputStream(socket.getOutputStream()),
                                chunkSize,
                                file,
                                fileId
                        );
                        fileUploader.start();
                    }
                }
                else if(reqCode.equals("print"))
                {
                    System.out.println(dataInputStream.readUTF());
                }
                else
                {
                    System.out.println("ReqCode: "+reqCode+" not defined");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
