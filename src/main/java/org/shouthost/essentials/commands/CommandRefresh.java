package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandRefresh extends ECommandBase {
	@Override
	public String getPermissionNode() {
		return "essentials.refresh";
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
	protected void processCommand(ICommandSender commandSender, List<String> args) {
		if (args.isEmpty() || (args.size() == 1 && !(commandSender instanceof EntityPlayerMP) && !canConsoleUseCommand()))
			throw new WrongUsageException(getCommandUsage(commandSender));
		Player player = new Player(args.size() == 2 ? getPlayerFromString(args.get(0)) : commandSender);
		player.refresh(Integer.parseInt(args.size() == 2 ? args.get(1) : args.get(0)));
		player.sendMessage(EnumChatFormatting.GREEN + "chunks have been refreshed");
	}

	@Override
	public String getCommandName() {
		return "refresh";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/refresh [<player>] [<radius>]";
	}
}
