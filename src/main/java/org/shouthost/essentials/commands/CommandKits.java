package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

/**
 * Created by Darius on 5/20/2014.
 */
public class CommandKits extends ECommandBase {
	@Override
	public String getPermissionNode() {
		return "essentials.kits";
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
		return "kits";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return EnumChatFormatting.RED + "/kits [<kitname>]";
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {

	}
}
