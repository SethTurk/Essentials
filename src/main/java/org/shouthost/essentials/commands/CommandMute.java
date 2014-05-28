package org.shouthost.essentials.commands;


import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import org.shouthost.essentials.core.Essentials;

import java.util.List;

public class CommandMute extends ECommandBase{
	@Override
	public String getCommandName() {
		return "mute";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return null;
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {
//		Essentials.muteList.put(((EntityPlayerMP)iCommandSender).getUniqueID(), null);
		iCommandSender.addChatMessage(new ChatComponentText("You have been muted"));
	}

	@Override
	public String getPermissionNode() {
		return "essentials.mute";
	}

	@Override
	public boolean canConsoleUseCommand() {
		return false;
	}

	@Override
	public boolean canCommandBlockUseCommand() {
		return false;
	}
}
