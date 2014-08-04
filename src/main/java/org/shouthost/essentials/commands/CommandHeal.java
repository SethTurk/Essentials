package org.shouthost.essentials.commands;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandHeal extends Command {
	@Override
	public String getPermissionNode() {
		return "essentials.heal";
	}

	@Override
	public boolean canConsoleUseCommand() {
		return true;
	}

	@Override
	public boolean canCommandBlockUseCommand() {
		return false;
	}

	@Override
	public String getCommandName() {
		return "heal";
	}

	@Override
	public String getCommandUsage(Player player) {
		return "/heal [<player>]";
	}

	@Override
	public void processCommand(Player player, List<String> args) {
		if (args.isEmpty()) {
			if (!(player instanceof EntityPlayer)) return;
			if (!player.has(getPermissionNode() + ".self")) {
				player.sendMessage(EnumChatFormatting.RED + "You do not have permission to this action");
				return;
			}
			player.setHealth(200);
			player.sendMessage(EnumChatFormatting.GREEN + "You have been healed");
			return;
		} else {
			Player target = new Player(getPlayerFromString(args.get(0)));
			if (target == null) {
				player.sendMessage(EnumChatFormatting.RED + "Player does not exist");
				return;
			}
			target.setHealth(200);
		}
	}
}
