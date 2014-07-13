package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.utils.config.Player;
import org.shouthost.essentials.utils.config.Warp;

import java.util.List;

public class CommandWarp extends ECommandBase {
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
	protected void processCommand(ICommandSender commandSender, List<String> args) {
		if(args.isEmpty() || (!(commandSender instanceof EntityPlayerMP) && args.size() == 1)) throw new WrongUsageException(getCommandUsage(commandSender));
		Player player = new Player(args.size() == 2 ? getPlayerFromString(args.get(0)) : commandSender);
		player.warpTo(args.size() == 2 ? args.get(1) : args.get(0));
	}

	@Override
	public String getCommandName() {
		return "warp";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return EnumChatFormatting.RED+"/warp [<player>] <name>";
	}
}
