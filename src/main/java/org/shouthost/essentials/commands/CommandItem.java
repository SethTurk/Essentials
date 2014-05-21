package org.shouthost.essentials.commands;

import forgeperms.api.ForgePermsAPI;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class CommandItem extends ECommandBase {
	@Override
	public List<String> getCommandAliases() {
	    ArrayList<String> aliasList = new ArrayList<String>();
		aliasList.add("i");
		aliasList.add("item");
		return aliasList;
	}

	@Override
	public String getCommandName() {
		return "item";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/item";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length == 0){
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + getCommandUsage(sender)));
			return;
		}
		String group = ForgePermsAPI.chatManager.getPrimaryGroup(sender.getEntityWorld().getWorldInfo().getWorldName(),sender.getCommandSenderName());
		if(group != null) {
			sender.addChatMessage(new ChatComponentText("Your in a group - "+group));
			return;
		}else{
			sender.addChatMessage(new ChatComponentText("Your not in a group"));
			return;
		}
		//		String groupPrefix = ForgePermsAPI.chatManager.getGroupPrefix(sender.getEntityWorld().getWorldInfo().getWorldName(), )

	}

	@Override
	public String getPermissionNode() {
		return "essentials.item";
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
