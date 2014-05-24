package org.shouthost.essentials.utils.config;

import com.google.gson.stream.JsonReader;
import net.minecraft.entity.player.EntityPlayer;
import org.shouthost.essentials.core.Essentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.shouthost.essentials.json.kits.Kit;
import org.shouthost.essentials.json.players.Players;

import java.io.*;
import java.util.ArrayList;

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

	public static void LoadPlayers(){
		for (String file : Essentials.players.list()) {
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
				if (!Essentials.playerList.contains(player))
					Essentials.playerList.add(player);
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
