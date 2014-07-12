package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

/**
 * Created by Darius on 5/28/2014.
 */
public class CommandBurn extends ECommandBase {
	@Override
	public String getPermissionNode() {
		return "essentials.burn";
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
		if (args.isEmpty()) {
			if (!(commandSender instanceof EntityPlayer)) {
				commandSender.addChatMessage(new ChatComponentText("You can not use this command"));
				return;
			}
			EntityPlayer player = (EntityPlayer) commandSender;
			player.setFire(2);
			return;
		} else {
			EntityPlayerMP target = MinecraftServer.getServer().getConfigurationManager().func_152612_a(args.get(0));
			if (target == null) {
				commandSender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Player is not online."));
				return;
			}
			target.setFire(2);
			return;
		}
	}

	@Override
	public String getCommandName() {
		return "burn";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/burn [<player>]";
	}
}
