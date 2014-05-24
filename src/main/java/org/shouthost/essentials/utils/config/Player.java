package org.shouthost.essentials.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.entity.player.EntityPlayer;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.json.players.Players;

import java.io.*;
import java.util.Iterator;

public class Player {
	public static Players CreatePlayer(EntityPlayer player){
		Players newPlayer = new Players();
		newPlayer.setPlayername(player.getDisplayName());
		newPlayer.setUuid(player.getUniqueID().toString());
		newPlayer.setPosX((int) player.posX);
		newPlayer.setPosY((int) player.posY);
		newPlayer.setPosZ((int) player.posZ);
		newPlayer.setBanned(false);
		newPlayer.setMuted(false);
		Essentials.playerList.add(newPlayer);
		return newPlayer;
	}

	public static Players FindPlayer(EntityPlayer player){
		Iterator<Players> p = Essentials.playerList.iterator();
		Players players = null;
		while(p.hasNext()){
			if(p.next().getPlayerName() == player.getDisplayName())
				players = p.next();
		}
		return players;
	}

	public static void UpdatePlayerName(Players player, String name){
		player.setPlayername(name);
	}

	public static void UpdatePlayerCoords(Players player, int x, int y, int z){
		player.setPosX(x);
		player.setPosY(y);
		player.setPosZ(z);
	}

	public static void MutePlayer(Players player, String reason, int timeout){
		if(player.isMuted()) return;
		player.setMuted(true);
		player.setMuteReason(reason);
		player.setMuteTimeout(timeout);
	}

	public static void WarnPlayer(Players player, String reason){
		player.setWarning(reason);
	}

	public static void BanPlayer(Players player, String reason, int timeout){
		if(player.isBanned()) return;
		player.setBanned(true);
		player.setBanReason(reason);
		player.setBanTimeout(timeout);
	}

	public static void UnbanPlayer(Players player){
		if(player.isBanned()) player.setBanned(false);
	}

	public static void UpdateBan(Players player, String reason, int timeout){
		if(!player.getBanReason().contains(reason)) player.setBanReason(reason);
		if(player.getBanTimeout() != timeout) player.setBanTimeout(timeout);
	}

	public static void SavePlayer(Players player){
		Gson gson = new Gson();
		String json = gson.toJson(player);
		System.out.println(json);
		File file = new File(Essentials.players, player.getUuid()+".json");
		if(file.exists() && file.isFile()) file.delete();
		try {
				Writer writer = new OutputStreamWriter(new FileOutputStream(file) , "UTF-8");
				writer.write(json);
				writer.close();
		} catch (IOException e) {
				e.printStackTrace();
		}
	}
}
