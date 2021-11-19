package client;

import java.io.DataInputStream;
import java.io.IOException;

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
