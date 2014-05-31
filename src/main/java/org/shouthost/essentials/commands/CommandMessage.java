package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandMessage extends ECommandBase{

	@Override
	public List<String> getCommandAliases() {
		ArrayList<String> aliasList = new ArrayList<String>();
		aliasList.add("msg");
		aliasList.add("pm");
		return aliasList;
	}

	@Override
	public String getPermissionNode() {
		return "essentials.message";
	}

	@Override
	public boolean canConsoleUseCommand() {
		return true;
	}

	@Override
	public boolean canCommandBlockUseCommand() {
		return true;
	}

	@Override
	protected void processCommand(ICommandSender commandSender, List<String> arguments) {

	}

	@Override
	public String getCommandName() {
		return "message";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/message [player] [message]";
	}


}
