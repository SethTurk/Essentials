package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandReply extends ECommandBase {

	@Override
	public List<String> getCommandAliases() {
		ArrayList<String> aliasList = new ArrayList<String>();
		aliasList.add("r");
		return aliasList;
	}

	@Override
	public String getPermissionNode() {
		return null;
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
	protected void processCommand(ICommandSender commandSender, List<String> arguments) {

	}

	@Override
	public String getCommandName() {
		return "reply";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return null;
	}
}
