package server;

import java.io.DataOutputStream;

public class BroadcastThread extends Thread{
    String message;

    public BroadcastThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        StudentDirectory studentDirectory = StudentDirectory.getInstance();
        studentDirectory.broadCast(message);
    }
}
