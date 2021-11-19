package Basic;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 6666);
        System.out.println("Connection established");
        System.out.println("Remote port: " + socket.getPort());
        System.out.println("Local port: " + socket.getLocalPort());

        // output buffer and input buffer
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());

        Thread t = new DataReceiver(in);
        t.start();

        while (true)
        {
            String s = in.readUTF();
            System.out.println(s);
        }


    }
}
