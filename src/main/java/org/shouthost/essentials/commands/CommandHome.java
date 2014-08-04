package org.shouthost.essentials.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldServer;
import org.shouthost.essentials.json.players.Homes;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandHome extends Command {
	@Override
	public String getPermissionNode() {
		return "essentials.home";
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
		return "home";
	}

	@Override
	public String getCommandUsage(Player player) {
		return null;
	}

	@Override
	public void processCommand(final Player player, List<String> args) {
		if (player.isRiding()) {
			player.sendMessage(EnumChatFormatting.RED + "You are not allowed to go home while riding an entity");
			return;
		}
		if (args.isEmpty()) {
			Homes home = player.getHome("home");
			if (home == null && player.getHomeCount() == 0) {
				player.sendMessage(EnumChatFormatting.RED + "You have no set home!");
				return;
			} else if (home == null && player.getHomeCount() == 0) {
				String h = "";
				for (String hm : player.getHomeList()) {
					h = h + " " + hm;
				}
				player.sendMessage(EnumChatFormatting.YELLOW + h);
				return;
			}

			WorldServer world = MinecraftServer.getServer().worldServerForDimension(home.getWorld());
			if (world == null) {
				player.sendMessage(EnumChatFormatting.RED + "The world your home is set in does not exist");
				return;
			}
			player.teleportTo(world, home.getX(), home.getY(), home.getZ());
			return;
		} else if (!args.isEmpty()) {
			Homes home = player.getHome(args.get(0));
			if (home == null) {
				player.sendMessage(EnumChatFormatting.RED + "You have no set home!");
				return;
			}
			WorldServer world = MinecraftServer.getServer().worldServerForDimension(home.getWorld());
			if (world == null) {
				player.sendMessage(EnumChatFormatting.RED + "The world your home is set in does not exist");
				return;
			}
			player.teleportTo(world, home.getX(), home.getY(), home.getZ());
			return;
		}
	}
}
