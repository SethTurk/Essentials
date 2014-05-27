package org.shouthost.essentials.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.json.players.Homes;
import org.shouthost.essentials.json.players.Players;

import java.io.*;
import java.util.Iterator;
import java.util.UUID;

public class Player {

	public static boolean PlayerExistInMemory(UUID uuid){
		Iterator<Players> lt = Essentials.playerList.iterator();
		while(lt.hasNext()){
			if(lt.next().getUuid() == uuid.toString()){
				return true;
			}
		}
		return false;
	}

	public static Players CreatePlayer(EntityPlayer player){
		Players newPlayer = new Players();
		newPlayer.setPlayername(player.getDisplayName());
		newPlayer.setUuid(player.getUniqueID().toString());
		newPlayer.setPosX(player.posX);
		newPlayer.setPosY(player.posY);
		newPlayer.setPosZ(player.posZ);
		newPlayer.setBanned(false);
		newPlayer.setMuted(false);
		newPlayer.setJailed(false);
		Essentials.playerList.add(newPlayer);
		if(Essentials.playerList.contains(newPlayer))
			System.out.println("Player "+newPlayer.getPlayerName()+" with UUID "+newPlayer.getUuid()+" have been created");
		return newPlayer;
	}

	public static Players FindPlayer(EntityPlayer player){
		for(Players p: Essentials.playerList){
			if(p.getUuid() == player.getUniqueID().toString())
				return p;
		}
		return null;
	}

	public static void SetPlayerHome(Players player, String name, int x, int y, int z){
		Homes home = new Homes();
		home.setName(name);
		home.setX(x);
		home.setY(y);
		home.setZ(z);
		player.setHome(home);
	}

	public static Homes GetPlayerHome(Players player, String name){
		for(Homes home: player.getHomes()){
			if(home.getName() == name){
				return home;
			}
		}
		return null;
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
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
