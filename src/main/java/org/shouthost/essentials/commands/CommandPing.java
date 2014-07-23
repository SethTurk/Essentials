package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandPing extends ECommandBase {
	@Override
	public String getCommandName() {
		return "ping";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/ping";
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {
		if (iCommandSender instanceof EntityPlayerMP) {
			Player player = new Player(iCommandSender);
			player.sendMessage(EnumChatFormatting.GREEN + "pong");
		} else {
			iCommandSender.addChatMessage(new ChatComponentText("pong"));
		}
	}

	@Override
	public String getPermissionNode() {
		return "essentials.ping";
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
	public boolean canUseWithoutPermission() {
		return false;
	}
}
