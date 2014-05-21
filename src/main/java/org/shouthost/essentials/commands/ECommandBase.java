package org.shouthost.essentials.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import forgeperms.api.ForgePermsAPI;
import net.minecraft.tileentity.TileEntityCommandBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ECommandBase extends CommandBase {

	public abstract String getPermissionNode();

	public abstract boolean canConsoleUseCommand();

	public abstract boolean canCommandBlockUseCommand();

	public boolean canUseWithoutPermission(){return false;}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender commandSender) {
		if(!(commandSender instanceof EntityPlayer) && canConsoleUseCommand())return true;
		if(commandSender instanceof TileEntityCommandBlock && canCommandBlockUseCommand())return true;
		if (commandSender instanceof EntityPlayer &&
			getPermissionNode() != null &&
			ForgePermsAPI.permManager != null) {
			//TODO: Convert from displayname to UUID before production.
			//TODO: Do futher testing
				return ForgePermsAPI.permManager.canAccess(((EntityPlayer) commandSender).getDisplayName(),((EntityPlayer) commandSender).worldObj.getWorldInfo().getWorldName(), getPermissionNode());
		}

		if(canUseWithoutPermission()) return true;
		return super.canCommandSenderUseCommand(commandSender);
	}
}
