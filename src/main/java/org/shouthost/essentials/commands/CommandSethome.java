package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.json.players.Homes;
import org.shouthost.essentials.utils.compat.Location;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandSethome extends ECommandBase {
	@Override
	public String getPermissionNode() {
		return "essentials.home.set";
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
		return "sethome";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return null;
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {
		Player player = new Player((EntityPlayerMP) iCommandSender);
		if (args.isEmpty()) {
			String name = "home";
			player.sendMessage(player.getHomeList().toString());
			player.setHome(name);
//            player.save();
			player.sendMessage(EnumChatFormatting.GREEN + "Your home have been set!");
			return;
		} else {
			//find if home exist
			Homes home = player.getHome(args.get(0));
			if (home != null) {
				player.sendMessage(EnumChatFormatting.RED + "Home '" + args.get(0) + "' already exist!");
				return;
			} else if (home == null) {
				Location loc = player.getLocation();
				player.sendMessage(player.getHomeList().toString());
				player.setHome(args.get(0), (int) player.getPosX(), (int) player.getPosY(), (int) player.getPosZ());
//                player.save();
				player.sendMessage(EnumChatFormatting.GREEN + "Your home have been set!");
				return;
			}

		}
	}
}
