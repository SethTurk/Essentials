package org.shouthost.essentials.tasks;

import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.entity.Player;

import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BackupTask implements Runnable {
	public BackupTask() {

	}

	@Override
	public void run() {
		FMLLog.getLogger().log(Level.INFO, "Saving all player information");
		for (Map.Entry<UUID, Player> entry : Essentials.playerList.asMap().entrySet()) {
			entry.getValue().save();
		}
		FMLLog.getLogger().log(Level.INFO, "Backing up Essentials data");
		synchronized (Essentials.schedule.lock) {
			try {
				zipDirectory(Essentials.base, new File("backup.zip"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void zipDirectory(File directory, File zipfile) throws IOException {
		URI baseDir = directory.toURI();
		Deque<File> queue = new LinkedList<>();
		queue.push(directory);
		OutputStream out = new FileOutputStream(zipfile);
		Closeable res = out;
		try {
			ZipOutputStream zout = new ZipOutputStream(out);
			res = zout;
			while (!queue.isEmpty()) {
				directory = queue.removeFirst();
				List<File> dirFiles = Arrays.asList(directory.listFiles());
				if (dirFiles.size() != 0) {
					for (File child : dirFiles) {
						if (child != null) {
							String name = baseDir.relativize(child.toURI()).getPath();
							if (child.isDirectory()) {
								queue.push(child);
								name = name.endsWith("/") ? name : name + "/";
								zout.putNextEntry(new ZipEntry(name));
							} else {
								zout.putNextEntry(new ZipEntry(name));
								copy(child, zout);
								zout.closeEntry();
							}
						}
					}
				}
			}
		} finally {
			res.close();
		}
	}

	private void copy(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		while (true) {
			int readCount = in.read(buffer);
			if (readCount < 0) {
				break;
			}
			out.write(buffer, 0, readCount);
		}
	}

	private void copy(File file, OutputStream out) throws IOException {
		try (InputStream in = new FileInputStream(file)) {
			copy(in, out);
		}
	}
}
