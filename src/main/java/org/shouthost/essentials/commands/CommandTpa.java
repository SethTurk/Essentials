package org.shouthost.essentials.commands;

import net.minecraft.command.WrongUsageException;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandTpa extends Command {
	@Override
	public String getPermissionNode() {
		return "essentials.tpa";
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
	protected void processCommand(Player player, List<String> args) {
		if (args.isEmpty()) throw new WrongUsageException(getCommandUsage(player));
		Player target = getPlayerFromString(args.get(0));
	}

	@Override
	public String getCommandName() {
		return "tpa";
	}

	@Override
	public String getCommandUsage(Player player) {
		return "/tpa <player>";
	}
}
