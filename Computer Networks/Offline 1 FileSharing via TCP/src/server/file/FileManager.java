package server.file;

import server.Settings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FileManager {
    ConcurrentMap<String, Files> privateFiles;
    ConcurrentMap<String, Files> publicFiles;
    File root;

    private FileManager(File root) {
        privateFiles = new ConcurrentHashMap<>();
        publicFiles = new ConcurrentHashMap<>();

        if (!root.exists()) root.mkdirs();
        this.root = root;

        for (File userDir : root.listFiles()) {
            String sid = userDir.getName();
            privateFiles.put(sid, new Files(new File(userDir, "private"), sid, "private"));
            publicFiles.put(sid, new Files(new File(userDir, "public"), sid, "public"));
        }

    }

    public List<String> getRegisteredStudents() {
        return new ArrayList<>(privateFiles.keySet());
    }

    public boolean addNewStudent(String sid) {
        File userDir = new File(root, sid);
        if (!userDir.exists()) {
            userDir.mkdir();
            File privateUserFiles = new File(userDir, "private");
            privateUserFiles.mkdir();
            privateFiles.put(sid, new Files(privateUserFiles, sid, "private"));

            File publicUserFiles = new File(userDir, "public");
            publicUserFiles.mkdir();
            publicFiles.put(sid, new Files(publicUserFiles, sid, "public"));

            return true;
        }

        return false;
    }

    public int totalMatchFile(String owner, String visibility, String fileName) {
        Files files = null;
        if (visibility.equals("public")) {
            files = publicFiles.get(owner);
        } else if (visibility.equals("private")) {
            files = privateFiles.get(owner);
        }
        if (files == null)
            return 0;
        else {
            return files.totalMatch(fileName);
        }
    }

    public File getFile(String owner, String visibility, String fileName) {
        Files files = null;
        if (visibility.equals("public")) {
            files = publicFiles.get(owner);
        } else if (visibility.equals("private")) {
            files = privateFiles.get(owner);
        }
        if (files == null)
            return null;
        else {
            return files.getFile(fileName);
        }
    }

    public void refreshFiles(String sid) {
        File userDir = new File(root, sid);
        privateFiles.put(sid, new Files(new File(userDir, "private"), sid, "private"));
        publicFiles.put(sid, new Files(new File(userDir, "public"), sid, "public"));
    }

    public String getStudentFiles(String sid) {
        return privateFiles.get(sid).toString() + publicFiles.get(sid).toString();
    }

    public String getPublicFiles(String sid) {
        if (publicFiles.containsKey(sid))
            return publicFiles.get(sid).toString();
        else
            return "Student with SID: " + sid + " not registered";
    }

    public String getAllPublicFiles() {
        String result = "owner visibility filenaame size\n";
        for (Files files : publicFiles.values()) {
            result += files.toString();
        }
        return result;
    }


    private static FileManager instance;

    public static FileManager getInstance() {
        if (instance == null)
            instance = new FileManager(new File(Settings.getInstance().getPath()));
        return instance;
    }

    public static void main(String[] args) {
        FileManager fileManager = FileManager.getInstance();
        System.out.println(fileManager.getAllPublicFiles());
    }


}
