package org.shouthost.essentials.commands;

import net.minecraft.command.WrongUsageException;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandKick extends Command {
	@Override
	public String getPermissionNode() {
		return "essentials.kick";
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
		return "kick";
	}

	@Override
	public String getCommandUsage(Player player) {
		return "/kick <player> [reason]";
	}

	@Override
	public void processCommand(Player player, List<String> args) {
		if (args.size() <= 0) throw new WrongUsageException(getCommandUsage(player));
		Player target = new Player(args.get(0));
		if (target == null) {
			player.sendMessage(EnumChatFormatting.RED + "Player " + args.get(0) + " does not exist on the server.");
			return;
		}

		target.kick(args.get(1));
	}
}
