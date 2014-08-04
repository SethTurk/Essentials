package org.shouthost.essentials.commands;

import org.shouthost.essentials.utils.config.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandReply extends Command {

	@Override
	public List<String> getCommandAliases() {
		ArrayList<String> aliasList = new ArrayList<String>();
		aliasList.add("r");
		return aliasList;
	}

	@Override
	public String getPermissionNode() {
		return "essentials.message.reply";
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
		return "reply";
	}

	@Override
	public String getCommandUsage(Player player) {
		return "/reply <message>";
	}
}
