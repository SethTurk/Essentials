package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandTpa extends ECommandBase {
	@Override
	public String getPermissionNode() {
		return "essentials.tpa";
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
	protected void processCommand(ICommandSender commandSender, List<String> args) {
		if (args.isEmpty()) throw new WrongUsageException(getCommandUsage(commandSender));
		Player player = new Player(commandSender);
		EntityPlayerMP target = getPlayerFromString(args.get(0));
	}

	@Override
	public String getCommandName() {
		return "tpa";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/tpa <player>";
	}
}
