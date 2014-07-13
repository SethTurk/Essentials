package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darius on 5/20/2014.
 */
public class CommandPardon extends ECommandBase {
	@Override
	public String getPermissionNode() {
		return "essentials.pardon";
	}

	@Override
	public boolean canConsoleUseCommand() {
		return true;
	}

	@Override
	public boolean canCommandBlockUseCommand() {
		return false;
	}

	@Override
	public String getCommandName() {
		return "pardon";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/pardon <player>";
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {

	}

	@Override
	public List getCommandAliases() {
		ArrayList aliases = new ArrayList();
		aliases.add("unban");
		return aliases;
	}
}
