package org.shouthost.essentials.commands;

import forgeperms.api.ForgePermsAPI;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntityCommandBlock;
import org.shouthost.essentials.utils.config.Player;

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
	public String getCommandUsage(ICommandSender iCommandSender) {
		return getCommandUsage(new Player(iCommandSender));
	}

	public abstract String getCommandUsage(Player player);

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender commandSender) {
		if (!(commandSender instanceof EntityPlayer) && canConsoleUseCommand()) return true;
		if (commandSender instanceof TileEntityCommandBlock && canCommandBlockUseCommand()) return true;
		if (commandSender instanceof EntityPlayer &&
				getPermissionNode() != null &&
				ForgePermsAPI.permManager != null &&
				!canUseWithoutPermission()) {
			Player player = new Player(commandSender);
			//TODO: Do futher testing
			if (player != null) {
				return player.has(getPermissionNode());
			} else {
				return ForgePermsAPI.permManager.canAccess(((EntityPlayerMP) commandSender).getDisplayName(), ((EntityPlayerMP) commandSender).worldObj.getWorldInfo().getWorldName(), getPermissionNode());
			}

		}

		if (canUseWithoutPermission()) return true;
		return super.canCommandSenderUseCommand(commandSender);
	}

	@Override
	public final void processCommand(ICommandSender commandSender, String... argumentsArray) {
		if (!(commandSender instanceof EntityPlayer) && !canConsoleUseCommand())
			throw new WrongUsageException(getCommandUsage(commandSender));
		processCommand(new Player(commandSender), new ArrayList<String>(Arrays.asList(argumentsArray)));
	}

	protected abstract void processCommand(Player player, List<String> args);

	public Player getPlayerFromString(String name) {
		return new Player(name);
	}
}
