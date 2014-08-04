package org.shouthost.essentials.commands;

import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandBan extends Command {
	@Override
	public String getCommandName() {
		return "ban";
	}

	@Override
	public String getCommandUsage(Player player) {
		return "/ban <player> [reason]";
	}

	@Override
	public void processCommand(Player player, List<String> args) {

	}

	@Override
	public String getPermissionNode() {
		return "essentials.ban";
	}

	@Override
	public boolean canConsoleUseCommand() {
		return true;
	}

	@Override
	public boolean canCommandBlockUseCommand() {
		return false;
	}
}
