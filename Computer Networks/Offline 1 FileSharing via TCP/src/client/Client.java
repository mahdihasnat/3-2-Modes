package client;

import client.file.PendingUploads;
import client.message.MessageQueue;
import util.log.LogLevel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static String ipAddress = "10.18.122.211";
    public static int port = 60666;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter IP address: ");
        //String ipAddress = scanner.next();

        System.out.println("Enter Port no: ");
        //int port = scanner.nextInt();


        Socket socket = null;
        try {
            socket = new Socket(ipAddress, port);

            System.out.println("Connection established");
            System.out.println("Remote address: " + socket.getInetAddress());
            System.out.println("Remote port: " + socket.getPort());
            System.out.println("Local port: " + socket.getLocalPort());

            System.out.println("Enter Student ID: ");
            String sid = scanner.next();

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            synchronized (out) {
                out.writeUTF("login");
                out.writeUTF(sid);
                out.flush();
            }

            String connectionStatus = in.readUTF();

            System.out.println("connectionStatus :" + connectionStatus);

            if (connectionStatus.equals("denay")) {
                System.out.println("Student id is already in use");
            } else {

                Thread serverHandler = new ServerThread(in,out);
                serverHandler.start();

                System.out.println("Welcome " + sid + " !!");

                String msg = "showstudents - to show all student \n" +
                        "showmyfiles - to show my public and private files\n" +
                        "showallfiles - to show all public files\n" +
                        "showfiles SID - to show all public files of specific students\n" +
                        "requestfile message - request files from user with message\n" +
                        "showmessages - show all unread messages\n" +
                        "upload private filepath\n" +
                        "upload public filepath\n" +
                        "upload requestId filepath\n" +
                        "download sid public filename\n" +
                        "download sid private filename\n";

                MessageQueue messageQueue = MessageQueue.getInstance();
                PendingUploads pendingUploads = PendingUploads.getInstance();

                while (true) {

                    String operation = scanner.next();
                    String operand1 = null;

                    if (operation.equals("showmessages")) {
                        System.out.println(messageQueue.readMessages());
                    } else if (operation.equals("h")) {
                        System.out.println(msg);
                    } else {
                        if (operation.equals("showfiles") || operation.equals("upload") || operation.equals("download")
                        ) {
                            operand1 = scanner.next();
                        }

                        if (operation.equals("upload")) {
                            String filePath = scanner.nextLine().trim();
                            File file = new File(filePath);
                            if (!file.exists()) {
                                System.out.println("Upload failed! File not found " + filePath + " : " + file.getAbsolutePath());
                            } else {
                                long fileLength = file.length();
                                pendingUploads.add(file);
                                System.out.println("prndingUploads:" + pendingUploads);
                                synchronized (out) {
                                    out.writeUTF(operation);
                                    out.writeUTF(operand1);
                                    out.writeUTF(file.getName());
                                    out.writeLong(fileLength);
                                    out.flush();
                                }
                            }
                        } else if (operation.equals("download")) {
                            String visibility = scanner.next();
                            String fileName = scanner.nextLine().trim();

                            if (visibility.equals("public") || visibility.equals("private")) {
                                synchronized (out) {
                                    out.writeUTF("download");
                                    out.writeUTF(operand1);
                                    out.writeUTF(visibility);
                                    out.writeUTF(fileName);
                                }
                            } else {
                                System.out.println("select public or private\n");
                            }

                        } else {

                            synchronized (out) {
                                out.writeUTF(operation);
                                if (operand1 != null)
                                    out.writeUTF(operand1);
                                out.flush();
                            }
                        }


                    }
                }
            }

        } catch (IOException e) {
            ClientLogger.getLogger().logMessage(LogLevel.INFO,e.toString());
            ClientLogger.getLogger().logMessage(LogLevel.ERROR , e.toString());
            ClientLogger.getLogger().logMessage(LogLevel.DEBUG , e.getStackTrace().toString());
        }


    }
}
