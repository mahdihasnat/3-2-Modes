package server.file;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Files {
    private List<File> files;
    private String owner;
    private String security;

    public Files(File root, String owner, String security) {
        this.owner = owner;
        this.security = security;
        files = Arrays.asList(root.listFiles());
    }

    public int totalMatch(String fileName) {
        int total = 0;
        for (File f : files)
            total += f.getName().startsWith(fileName) ? 1 : 0;
        return total;
    }

    public File getFile(String fileName) {
        for (File f : files)
            if (f.getName().startsWith(fileName))
                return f;
        return null;
    }

    @Override
    public String toString() {
        String result = "";
        for (File f : files)
            result += owner + " " + security + " " + f.getName() + " " + f.length() / 1024 / 1024 + " mb\n";
        return result;
    }

}
