package util;

import java.io.File;

public class DuplicateFileSave {
    public static File getUniqueFile(File file) {
        while (file.exists()) {
            String fileName = file.getName();
            String parentPath = file.getParent();
            int dot = fileName.lastIndexOf('.');
            String onlyName = (dot == -1 ? fileName : fileName.substring(0, dot));
            String extension = (dot == -1 ? "" : fileName.substring(dot + 1));
            int lastBeginParenthesis = onlyName.lastIndexOf('(');
            if (lastBeginParenthesis == -1) {
                file = new File(parentPath, onlyName + "(" + 1 + ")" + (dot == -1 ? "" : "." + extension));
            } else {
                int lastEndParenthesis = onlyName.lastIndexOf(')');
                if (lastEndParenthesis == -1 || lastEndParenthesis < lastBeginParenthesis || ( dot != -1 && lastEndParenthesis != dot - 1)) {
                    file = new File(parentPath, onlyName + "(" + 1 + ")" + (dot == -1 ? "" : "." + extension));
                } else {
                    String number = onlyName.substring(lastBeginParenthesis + 1, lastEndParenthesis);
                    try {
                        int newNumber = Integer.parseInt(number) + 1;
                        file = new File(parentPath, onlyName.substring(0, lastBeginParenthesis) + "(" + newNumber + ")" + (dot == -1 ? "" : "." + extension));
                    } catch (NumberFormatException e) {
                        file = new File(parentPath, onlyName + "(" + 1 + ")" + (dot == -1 ? "" : "." + extension));
                    }

                }
            }
        }
        return file;
    }
}
