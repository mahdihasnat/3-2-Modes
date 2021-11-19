package server;

import server.file.FileManager;

import java.io.DataOutputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class StudentDirectory {
    ConcurrentMap<String, ClientHandler> currentlyOnlineStudents;
    List<String> registeredStudents;


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
