package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;

import java.util.List;

/**
 * Created by Darius on 5/20/2014.
 */
public class CommandButcher extends ECommandBase {
	//TODO: Will this command stay or go?
	@Override
	public String getCommandName() {
		return "butcher";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return null;
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {

	}

	@Override
	public String getPermissionNode() {
		return "essentials.butcher";
	}

	@Override
	public boolean canConsoleUseCommand() {
		return false;
	}

	@Override
	public boolean canCommandBlockUseCommand() {
		return false;
	}
}
