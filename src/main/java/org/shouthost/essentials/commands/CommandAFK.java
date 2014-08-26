package org.shouthost.essentials.commands;

import org.shouthost.essentials.entity.Player;

import java.util.List;

public class CommandAFK extends Command {
	@Override
	public String getPermissionNode() {
        return "essentials.command.afk";
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
		return null;
	}

	@Override
	public String getCommandUsage(Player player) {
		return null;
	}

	@Override
	public void processCommand(Player player, List<String> args) {

	}
}
