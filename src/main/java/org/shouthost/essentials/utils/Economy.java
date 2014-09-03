package org.shouthost.essentials.utils;


import net.minecraft.entity.player.EntityPlayerMP;
import org.shouthost.essentials.commands.CommandListener;
import org.shouthost.essentials.entity.Player;

public class Economy {
    public static void addToPlayerBalance(EntityPlayerMP player, int bal) {
		Player pl = CommandListener.getPlayerFromEntity(player);
		pl.setBalance(pl.getBalance() + bal);
	}

	public static void removeFromPlayerBalance(EntityPlayerMP player, int bal) {
		Player pl = CommandListener.getPlayerFromEntity(player);
		if(pl.getBalance() >= bal) {
			pl.setBalance(pl.getBalance() - bal);
		}
	}
}
