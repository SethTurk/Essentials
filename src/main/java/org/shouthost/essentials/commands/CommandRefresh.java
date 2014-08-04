package org.shouthost.essentials.commands;

import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandRefresh extends Command {
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
	protected void processCommand(Player player, List<String> args) {
		if (args.isEmpty() || (args.size() == 1 && !(player instanceof EntityPlayerMP) && !canConsoleUseCommand()))
			throw new WrongUsageException(getCommandUsage(player));
		if (args.size() == 2) {
			Player player2 = new Player(args.get(0));
			player2.refresh(Integer.parseInt(args.get(1)));
			player2.sendMessage(EnumChatFormatting.GREEN + "chunks have been refreshed");
		} else {
			player.refresh(Integer.parseInt(args.get(0)));
			player.sendMessage(EnumChatFormatting.GREEN + "chunks have been refreshed");
		}
	}

	@Override
	public String getCommandName() {
		return "refresh";
	}

	@Override
	public String getCommandUsage(Player player) {
		return "/refresh [<player>] [<radius>]";
	}
}
