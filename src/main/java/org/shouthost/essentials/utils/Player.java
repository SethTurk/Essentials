package org.shouthost.essentials.utils;

import net.minecraft.entity.player.EntityPlayer;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.json.players.Players;

import java.util.Iterator;

public class Player {
	public void CreatePlayer(EntityPlayer player){
		Players newPlayer = new Players();
		newPlayer.setPlayername(player.getDisplayName());
		newPlayer.setPosX((int) player.posX);
		newPlayer.setPosY((int) player.posY);
		newPlayer.setPosZ((int) player.posZ);
		newPlayer.setBanned(false);
		newPlayer.setMuted(false);
		Essentials.playerList.add(newPlayer);
	}

	public Players FindPlayer(EntityPlayer player){
		Iterator<Players> p = Essentials.playerList.iterator();
		Players players = null;
		while(p.hasNext()){
			if(p.next().getPlayerName() == player.getDisplayName())
				players = p.next();
		}
		return players;
	}

	public void UpdatePlayerName(Players player, String name){
		player.setPlayername(name);
	}

	public void UpdatePlayerCoords(Players player, int x, int y, int z){
		player.setPosX(x);
		player.setPosY(y);
		player.setPosZ(z);
	}

	public void MutePlayer(Players player, String reason, int timeout){
		if(player.isMuted()) return;
		player.setMuted(true);
		player.setMuteReason(reason);
		player.setMuteTimeout(timeout);
	}

	public void WarnPlayer(Players player, String reason){
		player.setWarning(reason);
	}

	public void BanPlayer(Players player, String reason, int timeout){
		if(player.isBanned()) return;
		player.setBanned(true);
		player.setBanReason(reason);
		player.setBanTimeout(timeout);
	}

	public void UnbanPlayer(Players player){
		if(player.isBanned()) player.setBanned(false);
	}

	public void UpdateBan(Players player, String reason, int timeout){
		if(!player.getBanReason().contains(reason)) player.setBanReason(reason);
		if(player.getBanTimeout() != timeout) player.setBanTimeout(timeout);
	}
}
