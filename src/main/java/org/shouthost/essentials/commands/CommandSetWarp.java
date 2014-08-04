package org.shouthost.essentials.commands;

import net.minecraft.command.WrongUsageException;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.config.Player;
import org.shouthost.essentials.utils.config.Warp;

import java.util.List;

public class CommandSetWarp extends Command {
	@Override
	public String getPermissionNode() {
		return "essentials.setwarp";
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
		if (args.isEmpty()) throw new WrongUsageException(getCommandUsage(player));
		Warp warp = new Warp(args.get(0), player.getLocation());
		warp.save();
		player.sendMessage(EnumChatFormatting.GREEN + "Warp '" + args.get(0) + "' have been created");
	}

	@Override
	public String getCommandName() {
		return "setwarp";
	}

	@Override
	public String getCommandUsage(Player player) {
		return EnumChatFormatting.RED + "/setwarp <name>";
	}
}
