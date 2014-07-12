package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;

import java.util.List;

/**
 * Created by Darius on 5/20/2014.
 */
public class CommandGive extends ECommandBase {
	@Override
	public String getPermissionNode() {
		return "essentials.give";
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
	public String getCommandName() {
		return "give";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return null;
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {

	}
}
