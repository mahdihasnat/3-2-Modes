package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {

    public static void main(String[] args) {
        Settings settings = Settings.getInstance();
        Thread bufferManagerThread = new BufferManagerThread();
        bufferManagerThread.start();

        System.out.println("Enter port no:");
        Scanner scanner = new Scanner(System.in);
        int port = scanner.nextInt();
        settings.setPORT(port);


        try {
            ServerSocket serverSocket = new ServerSocket(settings.getPORT());
            System.out.println("Server is running on port " + serverSocket.getLocalPort());
            System.out.println("Server ip address: " + serverSocket.getInetAddress());

            while (true) {
                System.out.println("Waiting for connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Connection established");

                ClientThread clientThread = new ClientThread(socket);
                clientThread.start();

            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
