package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

/**
 * Created by Darius on 5/20/2014.
 */
public class CommandPing extends ECommandBase {
	@Override
	public String getCommandName() {
		return "ping";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return null;
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {
		if (iCommandSender instanceof EntityPlayerMP) {
			Player player = new Player(iCommandSender);
			player.sendPacket(new S02PacketChat(new ChatComponentText(EnumChatFormatting.GREEN + "pong")));
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
