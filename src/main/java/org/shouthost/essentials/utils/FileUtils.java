package org.shouthost.essentials.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static boolean doesFileExist(File file) {
        return file.exists() && file.isFile();
    }

    public static boolean doesDirectoryExist(File dir) {
        return dir.exists() && dir.isDirectory();
    }

    public static BufferedReader loadFile(File file) {
        BufferedReader br = null;
        try {
            BufferedReader b = new BufferedReader(new FileReader(file));
            br = b;
            b.close();
        } catch (java.io.IOException ignored) {
            //ignored.printStackTrace();
        }
        return br;
    }

    public static List<String> listDirectory(File dir) {
        if (!dir.exists() || !dir.isDirectory()) return null;
        List<String> list = new ArrayList<String>();
        for (File directory : dir.listFiles())
            if (directory.isDirectory()) {
                list.add(directory.getName());
            }
        return list;
    }

    public static List<String> listFilesInDirectory(File dir) {
        if (!dir.exists() || !dir.isDirectory()) return null;
        List<String> list = new ArrayList<String>();
        for (File directory : dir.listFiles())
            if (directory.isFile()) {
                list.add(directory.getName());
            }
        return list;
    }

}
