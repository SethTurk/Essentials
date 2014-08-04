package org.shouthost.essentials.commands;


import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandMute extends Command {
	@Override
	public String getCommandName() {
		return "mute";
	}

	@Override
	public String getCommandUsage(Player player) {
		return EnumChatFormatting.RED + "/mute <player> [<time>] [<reason>]";
	}

	@Override
	public void processCommand(Player player, List<String> args) {

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
