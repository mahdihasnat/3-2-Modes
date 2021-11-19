package client;

import client.message.MessageQueue;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter IP address: ");
        //String ipAddress = scanner.next();
        String ipAddress = "localhost";
        System.out.println("Enter Port no: ");
        //int port = scanner.nextInt();
        int port = 6666;


        Socket socket = null;
        try {
            socket = new Socket(ipAddress, port);

            System.out.println("Connection established");
            System.out.println("Remote port: " + socket.getPort());
            System.out.println("Local port: " + socket.getLocalPort());

            System.out.println("Enter Student ID: ");
            String sid = scanner.next();

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(sid);

            DataInputStream in = new DataInputStream(socket.getInputStream());

            String connectionStatus = in.readUTF();

            System.out.println("connectionStatus :" + connectionStatus);

            if (connectionStatus.equals("denay")) {
                System.out.println("Student id is already in use");
            } else {

                Thread serverHandler = new ServerHandler(in);
                serverHandler.start();

                System.out.println("Welcome " + sid + " !!");

                String msg = "showstudents - to show all student \n" +
                        "showmyfiles - to show my public and private files\n" +
                        "showallfiles - to show all public files\n" +
                        "showfiles SID - to show all public files of specific students\n" +
                        "requestfile message - request files from user with message\n" +
                        "showmessages - show all unread messages\n";

                MessageQueue messageQueue = MessageQueue.getInstance();



                while (true) {
                    System.out.println(msg);
                    String operation = scanner.next();
                    String operand1 = null;

                    if (operation.equals("showmessages")) {
                        System.out.println(messageQueue.readMessages());

                    } else {
                        if (operation.equals("showfiles")) {
                            operand1 = scanner.next();
                        }
                        out.writeUTF(operation);
                        if (operand1 != null)
                            out.writeUTF(operand1);
                        out.flush();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
