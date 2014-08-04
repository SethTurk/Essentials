package org.shouthost.essentials.commands;

import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandWarp extends Command {
	@Override
	public String getPermissionNode() {
		return "essentials.warp";
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
	protected void processCommand(Player player, List<String> args) {
		if (args.isEmpty() || (!(player instanceof EntityPlayerMP) && args.size() == 1))
			throw new WrongUsageException(getCommandUsage(player));
		//TODO: change it so you can send a player to a warp point
//		Player player = new Player(args.size() == 2 ? getPlayerFromString(args.get(0)) : commandSender);
		player.warpTo(args.get(0));
	}

	@Override
	public String getCommandName() {
		return "warp";
	}

	@Override
	public String getCommandUsage(Player player) {
		return EnumChatFormatting.RED + "/warp [<player>] <name>";
	}
}
