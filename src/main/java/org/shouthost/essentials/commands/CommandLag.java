package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darius on 5/21/2014.
 */
public class CommandLag extends ECommandBase {
	@Override
	public List<String> getCommandAliases() {
		ArrayList<String> aliasList = new ArrayList<String>();
		aliasList.add("gc");
		aliasList.add("tps");
		return aliasList;
	}

	@Override
	public String getCommandName() {
		return "lag";
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
		return "essentials.lag";
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
	public boolean canUseWithoutPermission() {
		return false;
	}
}
