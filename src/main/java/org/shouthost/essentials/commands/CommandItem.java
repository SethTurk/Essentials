package org.shouthost.essentials.commands;

import forgeperms.api.ForgePermsAPI;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.utils.config.Player;

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
		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED.toString()));
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
