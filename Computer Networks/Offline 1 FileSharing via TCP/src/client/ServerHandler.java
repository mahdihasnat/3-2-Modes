package client;

import java.io.DataInputStream;
import java.io.IOException;

import client.file.UploadRequests;
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
        UploadRequests uploadRequests = UploadRequests.getInstance();

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
                    if(!uploadRequests.assignFIllId(fileName,fileId))
                    {
                        System.out.println("FileUploadInfo not found in queue!");
                    }
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
