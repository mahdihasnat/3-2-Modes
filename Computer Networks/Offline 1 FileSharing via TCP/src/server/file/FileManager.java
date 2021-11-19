package server.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import server.Settings;

public class FileManager {
    ConcurrentMap<String , Files> privateFiles;
    ConcurrentMap<String , Files> publicFiles;
    File root;

    private FileManager(File root) {
        privateFiles = new ConcurrentHashMap<>();
        publicFiles = new ConcurrentHashMap<>();

        if(!root.exists()) root.mkdirs();
        this.root = root;
        
        for(File userDir: root.listFiles())
        {
            String sid = userDir.getName();
            privateFiles.put(sid , new Files( new File(userDir , "private") , sid , "private"));
            publicFiles.put(sid , new Files( new File(userDir , "public") , sid , "public"));
        }

    }

    public List<String> getRegisteredStudents() {
        return new ArrayList<>(privateFiles.keySet());
    }

    public boolean addNewStudent(String sid)
    {
        File userDir = new File(root, sid);
        if(!userDir.exists())
        {
            userDir.mkdir();
            File privateUserFiles =new File(userDir, "private");
            privateUserFiles.mkdir();
            privateFiles.put(sid ,  new Files(privateUserFiles , sid , "private" ));

            File publicUserFiles =new File(userDir, "public");
            publicUserFiles.mkdir();
            publicFiles.put(sid ,  new Files(publicUserFiles , sid , "public" ));

            return true;
        }

        return false;
    }

    public String getStudentFiles(String sid)
    {
        return privateFiles.get(sid).toString() + publicFiles.get(sid).toString();
    }
    public String getPublicFiles(String sid)
    {
        if(publicFiles.containsKey(sid))
            return publicFiles.get(sid).toString();
        else 
            return "Student with SID: "+sid + " not registered";
    }
    public String getAllPublicFiles()
    {
        String result = "owner visibility filenaame size\n";
        for(Files files : publicFiles.values())
        {
            result += files.toString();
        }
        return result;
    }


    private static FileManager instance;
    public static FileManager getInstance() {
        if(instance == null)
            instance = new FileManager(new File(Settings.getInstance().getPath()));
        return instance;
    }

    public static void main(String [] args)
    {
        FileManager fileManager = FileManager.getInstance();
        System.out.println(fileManager.getAllPublicFiles());
    }

    
}
