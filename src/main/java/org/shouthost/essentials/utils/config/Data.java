package org.shouthost.essentials.utils.config;

import com.google.gson.Gson;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.json.books.Books;
import org.shouthost.essentials.json.kits.Kit;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.json.warps.Warps;

import java.io.*;
import java.util.UUID;

public class Data {

	public static void LoadKits() {
		for (String file : Essentials.kits.list()) {
			Gson gson = new Gson();
			if (file == null) break;
			File f = new File(Essentials.kits, file);
			if (f.exists() && f.isFile()) {
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(f));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Kit newKit = gson.fromJson(br, Kit.class);
				if (!Essentials.usableKit.contains(newKit))
					Essentials.usableKit.add(newKit);
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void LoadPlayers() {
		for (String file : Essentials.players.list()) {
			System.out.println("Loading " + file);
			Gson gson = new Gson();
			if (file == null) break;
			File f = new File(Essentials.players, file);
			if (f.exists() && f.isFile()) {
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(f));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Players player = gson.fromJson(br, Players.class);
				if (!Essentials.playersList.containsKey(player.getUuid()))
					Essentials.playersList.put(UUID.fromString(player.getUuid()), player);
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void LoadBooks() {
		for (String file : Essentials.books.list()) {
			Gson gson = new Gson();
			if (file == null) break;
			File f = new File(Essentials.books, file);
			if (f.exists() && f.isFile()) {
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(f));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Books b = gson.fromJson(br, Books.class);
				if (!Essentials.book.contains(b))
					Essentials.book.add(b);
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void LoadWarps() {
		for (String file : Essentials.warps.list()) {
			Gson gson = new Gson();
			if (file == null) break;
			File f = new File(Essentials.warps, file);
			if (f.exists() && f.isFile()) {
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(f));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Warps b = gson.fromJson(br, Warps.class);
				Warp warp = new Warp(b);
				if (!Essentials.warpList.contains(warp))
					Essentials.warpList.add(warp);
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
