package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import org.shouthost.essentials.utils.config.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandVanish extends ECommandBase {
	private ArrayList<UUID> vanishList = new ArrayList<UUID>();

	@Override
	public List<String> getCommandAliases() {
		ArrayList<String> aliasList = new ArrayList<String>();
		aliasList.add("v");
		return aliasList;
	}

	@Override
	public String getPermissionNode() {
		return "essentials.vanish";
	}

	@Override
	public boolean canConsoleUseCommand() {
		return false;
	}

	@Override
	public boolean canCommandBlockUseCommand() {
		return false;
	}

	@Override
	public String getCommandName() {
		return "vanish";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return null;
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {
		Player player = new Player((net.minecraft.entity.player.EntityPlayerMP) iCommandSender);
		if (vanishList.contains(player.getPlayer().getUniqueID())) {
			vanishList.remove(player.getPlayer().getUniqueID());
			MinecraftServer.getServer().getConfigurationManager().playerEntityList.add(player.getPlayer());
			player.getPlayer().removePotionEffect(Potion.invisibility.id);
		} else {
			vanishList.add(player.getPlayer().getUniqueID());
			MinecraftServer.getServer().getConfigurationManager().playerEntityList.remove(player.getPlayer());
			player.getPlayer().addPotionEffect(new PotionEffect(Potion.invisibility.id, 90000, 90000));
		}

	}
}
