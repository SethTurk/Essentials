package org.shouthost.essentials.commands;

import forgeperms.api.ForgePermsAPI;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import org.shouthost.essentials.utils.config.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ECommandBase extends CommandBase {

	public abstract String getPermissionNode();

	public abstract boolean canConsoleUseCommand();

	public abstract boolean canCommandBlockUseCommand();

	public boolean canUseWithoutPermission() {
		return false;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender commandSender) {
		if (!(commandSender instanceof EntityPlayer) && canConsoleUseCommand()) return true;
		if (commandSender instanceof TileEntityCommandBlock && canCommandBlockUseCommand()) return true;
		if (commandSender instanceof EntityPlayer &&
				getPermissionNode() != null &&
				ForgePermsAPI.permManager != null) {
			Player player = new Player((net.minecraft.entity.player.EntityPlayerMP) commandSender);
			//TODO: Do futher testing
			return player.has(getPermissionNode());
		}

		if (canUseWithoutPermission()) return true;
		return super.canCommandSenderUseCommand(commandSender);
	}

	@Override
	public final void processCommand(ICommandSender commandSender, String... argumentsArray) {
		processCommand(commandSender, new ArrayList<String>(Arrays.asList(argumentsArray)));
	}

	protected abstract void processCommand(ICommandSender commandSender, List<String> arguments);

}
