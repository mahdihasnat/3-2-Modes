package server;

import server.file.FileManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class StudentDirectory {
    ConcurrentMap<String, ClientHandler> currentlyOnlineStudents;
    private List<String> registeredStudents;


    private StudentDirectory() {
        currentlyOnlineStudents = new ConcurrentHashMap<>();
        registeredStudents = FileManager.getInstance().getRegisteredStudents();
    }

    boolean addNewStudent(String sid, ClientHandler clientHandler) {
        if (currentlyOnlineStudents.containsKey(sid))
            return false;
        else {
            synchronized (registeredStudents) {
                if (!registeredStudents.contains(sid)) {
                    registeredStudents.add(sid);
                    FileManager.getInstance().addNewStudent(sid);
                }
                currentlyOnlineStudents.put(sid, clientHandler);

                System.out.println("Student with id: " + sid + " logged in");

                return true;
            }
        }
    }

    void logoutStudent(String sid) {
        //if(currentlyOnlineStudents.get(sid ) == clientHandler)
        System.out.println("Student with id: " + sid + " logged out");
        currentlyOnlineStudents.remove(sid);
    }

    public String getAllStudent() {
        String ret = "Total Registered: " + registeredStudents.size() + "\n";
        synchronized (registeredStudents) {
            for (String sid : registeredStudents)
                ret += sid + (currentlyOnlineStudents.containsKey(sid) ? " [Online] " : "") + "\n";
        }
        return ret;
    }

    public void broadCast(String message)
    {
        for(ClientHandler clientHandler : currentlyOnlineStudents.values())
        {
            DataOutputStream out = clientHandler.getOut();
            synchronized (out) {
                try {
                    out.writeUTF("message");
                    out.writeUTF(message);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void sendMessage(String sid , String message)
    {
        if(currentlyOnlineStudents.containsKey(sid)) {

            DataOutputStream out = currentlyOnlineStudents.get(sid).getOut();
            synchronized (out) {
                try {
                    out.writeUTF("message");
                    out.writeUTF(message);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public boolean sendMessages(String sid, String[] messages)
    {
        if(currentlyOnlineStudents.containsKey(sid))
        {
            DataOutputStream out = currentlyOnlineStudents.get(sid).getOut();
            synchronized (out)
            {
                try
                {
                    for(String message: messages)
                        out.writeUTF(message);
                    out.flush();
                } catch (IOException e) {
                    //e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean sendBeginUpload(String sid, int fileId)
    {
        if(currentlyOnlineStudents.containsKey(sid))
        {
            DataOutputStream out = currentlyOnlineStudents.get(sid).getOut();
            int chunkSize = Settings.getInstance().getRandomChunkSize();
            synchronized (out)
            {
                try
                {
                    out.writeUTF("beginupload");
                    out.writeInt(fileId);
                    out.writeInt(chunkSize);
                    out.flush();
                } catch (IOException e) {
                    //e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    private static StudentDirectory instance;

    public static StudentDirectory getInstance() {
        if (instance == null) {
            instance = new StudentDirectory();
        }
        return instance;
    }


    public static void main(String[] args) {
        StudentDirectory studentDirectory = StudentDirectory.getInstance();
        System.out.println(studentDirectory.registeredStudents);
        System.out.println(studentDirectory.addNewStudent("123", null));
        System.out.println(studentDirectory.addNewStudent("123", null));
    }

}
