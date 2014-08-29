package org.shouthost.essentials.utils;

import com.google.gson.Gson;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.json.books.Books;
import org.shouthost.essentials.json.warps.Warps;

import java.io.File;

public class DataUtils {

    public static void LoadAll() {
        LoadBooks();
        LoadWarps();
    }

    //TODO: Rewrite
//    public static void LoadKits() {
//        for (String file : Essentials.kits.list()) {
//            Gson gson = new Gson();
//            if (file == null) break;
//            File f = new File(Essentials.kits, file);
//            if (f.exists() && f.isFile()) {
//                BufferedReader br = null;
//                try {
//                    br = new BufferedReader(new FileReader(f));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                Kit newKit = gson.fromJson(br, Kit.class);
//                if (!Essentials.usableKit.contains(newKit))
//                    Essentials.usableKit.add(newKit);
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    public static void LoadBooks() {
        for (String file : FileUtils.listFilesInDirectory(Essentials.books)) {
            Gson gson = new Gson();
            if (file == null) break;
            String data = FileUtils.loadFile(new File(Essentials.books, file));
            if (data == null) break;
            Books book = gson.fromJson(data, Books.class);
            File f = new File(Essentials.books, file);
            if (!Essentials.book.contains(book))
                Essentials.book.add(book);
        }
    }

    public static void LoadWarps() {
        for (String file : FileUtils.listFilesInDirectory(Essentials.warps)) {
            Gson gson = new Gson();
            if (file == null) break;
            String data = FileUtils.loadFile(new File(Essentials.warps, file));
            if (data == null) break;
            Warps b = gson.fromJson(data, Warps.class);
            Warp warp = new Warp(b);
            warp.load();
        }
    }
}
