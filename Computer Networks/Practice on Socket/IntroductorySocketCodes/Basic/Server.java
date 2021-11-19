package Basic;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket welcomeSocket = new ServerSocket(6666);

        // while loop, so that server can connect to another client
        // after serving the current one
        while(true) {
            System.out.println("Waiting for connection...");
            Socket socket = welcomeSocket.accept();
            System.out.println("Connection established");
            System.out.println("Remote port: " + socket.getPort());
            System.out.println("Local port: " + socket.getLocalPort());

            // output buffer and input buffer
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Thread t = new DataSender(out);
            t.start();

            while (true)
            {
                char [] chs = new char[100];
                for(int i=0;i<100;i++)
                        chs[i]  = 'a';
                String s = new String(chs);

                out.writeUTF(s);

            }


        }

    }
}
