package org.shouthost.essentials.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	public static boolean doesFileExist(File file) {
		return file.exists() && file.isFile();
	}

	public static boolean doesDirectoryExist(File dir) {
		return dir.exists() && dir.isDirectory();
	}

	public static String loadFile(File file) {
		byte[] encoded = new byte[0];
		try {
			encoded = Files.readAllBytes(Paths.get(file.toURI()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(encoded);
	}

	public static void writeToFile(File file, String data) {
		try {
			FileWriter writer = new FileWriter(file, false);
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public static File createDirectory(String name) {
		File file = new File(name);
		if (!file.exists()) file.mkdir();
		return file;
	}

	public static File createDirectory(File base, String name) {
		File file = new File(base, name);
		if (!file.exists()) file.mkdir();
		return file;
	}

}
