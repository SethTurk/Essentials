package org.shouthost.essentials.commands;

import org.shouthost.essentials.entity.Player;

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
        return "essentials.command.ban";
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
