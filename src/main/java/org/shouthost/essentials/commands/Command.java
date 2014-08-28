package org.shouthost.essentials.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.player.EntityPlayer;
import org.shouthost.essentials.entity.Player;
import org.shouthost.permissionforge.api.IHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command extends CommandBase {

	public abstract String getPermissionNode();

	public abstract boolean canConsoleUseCommand();

	public abstract boolean canCommandBlockUseCommand();

	public boolean canUseWithoutPermission() {
		return false;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender commandSender) {
		if (!(commandSender instanceof EntityPlayer) && canConsoleUseCommand()) return true;
		else if (commandSender instanceof CommandBlockLogic && canCommandBlockUseCommand()) return true;
		else if (commandSender instanceof EntityPlayer &&
				(getPermissionNode() != null || getPermissionNode() != "") &&
				IHandler.permission != null &&
				!canUseWithoutPermission()) {
			Player player = CommandListener.getPlayerFromString(((EntityPlayer) commandSender).getDisplayName());
			return player.hasPermission(getPermissionNode());
		} else return canUseWithoutPermission() || super.canCommandSenderUseCommand(commandSender);
	}

	@Override
	public void processCommand(ICommandSender commandSender, String... argumentsArray) {
		if (!(commandSender instanceof EntityPlayer) && !canConsoleUseCommand())
			throw new WrongUsageException(getCommandUsage(commandSender));
		Player player = new Player(commandSender);
		processCommand(player, new ArrayList<>(Arrays.asList(argumentsArray)));
	}

	public abstract void processCommand(Player player, List<String> args);
}
