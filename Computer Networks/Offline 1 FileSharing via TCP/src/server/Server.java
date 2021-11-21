package server;

import java.net.ServerSocket;
import java.net.Socket;



public class Server {
    
	public static void main(String [] args) {
		Settings settings = Settings.getInstance();
		Thread bufferManagerThread = new BufferManagerThread();
		bufferManagerThread.start();

		try 
		{
			ServerSocket serverSocket = new ServerSocket(settings.getPORT());
			System.out.println("Server is running on port " + serverSocket.getLocalPort());
			System.out.println("Server ip address: "+serverSocket.getInetAddress());

			while(true) {
				System.out.println("Waiting for connection...");
				Socket socket = serverSocket.accept();
				System.out.println("Connection established");

				ClientHandler clientHandler = new ClientHandler(socket);
				clientHandler.start();

			}

		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.getMessage());
		}
	}
}
