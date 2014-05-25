package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.List;


public class CommandAFK extends ECommandBase {
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
	public String getCommandName() {
		return null;
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return null;
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {

	}
}
