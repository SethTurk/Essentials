package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;

/**
 * Created by Darius on 5/20/2014.
 */
public class CommandBan extends ECommandBase {
	@Override
	public String getCommandName() {
		return "ban";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return null;
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, String[] strings) {

	}

	@Override
	public String getPermissionNode() {
		return "minecraft.ban";
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
