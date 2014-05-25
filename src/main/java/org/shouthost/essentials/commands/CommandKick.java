package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

/**
 * Created by Darius on 5/21/2014.
 */
public class CommandKick extends ECommandBase {
	@Override
	public String getPermissionNode() {
		return "essentials.kick";
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
	public String getCommandName() {
		return "kick";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/kick <player> [reason]";
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {
		if(args.size() <= 0) throw new WrongUsageException(getCommandUsage(iCommandSender));
		EntityPlayerMP target = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(args.get(1));
		if(target == null){
			iCommandSender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Player "+args.get(1)+ " does not exist on the server."));
			return;
		}
		String reason = null;
		if(args.get(2) != null){
			reason = args.get(2);
		}else{
			reason = "Kicked from the server";
		}
		target.playerNetServerHandler.kickPlayerFromServer(reason);
	}
}
