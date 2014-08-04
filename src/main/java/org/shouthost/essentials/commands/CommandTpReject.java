package org.shouthost.essentials.commands;

import org.shouthost.essentials.utils.config.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandTpReject extends Command {
	@Override
	public List<String> getCommandAliases() {
		ArrayList<String> aliasList = new ArrayList<String>();
		aliasList.add("tpadeny");
		return aliasList;
	}

	@Override
	public String getPermissionNode() {
		return "essentials.tpa.reject";
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
	protected void processCommand(Player player, List<String> arguments) {

	}

	@Override
	public String getCommandName() {
		return null;
	}

	@Override
	public String getCommandUsage(Player player) {
		return null;
	}
}
