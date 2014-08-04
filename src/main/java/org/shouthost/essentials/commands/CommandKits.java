package org.shouthost.essentials.commands;

import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandKits extends Command {
	@Override
	public String getPermissionNode() {
		return "essentials.kits";
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
	public String getCommandName() {
		return "kits";
	}

	@Override
	public String getCommandUsage(Player player) {
		return EnumChatFormatting.RED + "/kits [<kitname>]";
	}

	@Override
	public void processCommand(Player player, List<String> args) {

	}
}
