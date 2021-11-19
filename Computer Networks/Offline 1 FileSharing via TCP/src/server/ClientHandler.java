package server;

import server.file.FileManager;
import server.filerequest.FileRequestHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private DataOutputStream out;


    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getOut()
    {
        return out;
    }

    public void run() {
        String sid = null;
        StudentDirectory studentDirectory = StudentDirectory.getInstance();
        FileManager fileManager = FileManager.getInstance();
        FileRequestHandler fileRequestHandler = FileRequestHandler.getInstance();

        try {

            out = new DataOutputStream(this.socket.getOutputStream());
            DataInputStream in = new DataInputStream(this.socket.getInputStream());

            sid = in.readUTF();


            if (studentDirectory.addNewStudent(sid, this)) {
                out.writeUTF("success");
                out.flush();
                while (true) {
                    String operation = in.readUTF();

                    synchronized (out) {

                        if (operation.equals("showstudents")) {
                            out.writeUTF("allstudents");
                            out.writeUTF(studentDirectory.getAllStudent());
                        } else if (operation.equals("showmyfiles")) {
                            out.writeUTF("myfiles");
                            out.writeUTF(fileManager.getStudentFiles(sid));
                        } else if (operation.equals("showallfiles")) {
                            out.writeUTF("allfiles");
                            out.writeUTF(fileManager.getAllPublicFiles());
                        } else if (operation.equals("showfiles")) {
                            String sid_other = in.readUTF();
                            out.writeUTF("allfiles");
                            out.writeUTF(fileManager.getPublicFiles(sid_other));
                        } else if (operation.equals("requestfile")) {
                            String msg = in.readUTF();
                            Thread broadcast = new BroadcastThread( sid + " requested file with note: " + msg);
                            broadcast.start();
                        } else {
                            out.writeUTF("Incorrect operation :" + operation);
                            System.out.println("opeation: " + operation + " not defined for " + sid);
                        }
                        out.flush();
                    }
                }
            } else {
                out.writeUTF("denay");
                out.flush();
                System.out.println("Refused multiple connection request with student id: " + sid);
            }

        } catch (IOException e) {

            if (sid != null) {
                studentDirectory.logoutStudent(sid);
            }

            //e.printStackTrace();
        }
    }
}
