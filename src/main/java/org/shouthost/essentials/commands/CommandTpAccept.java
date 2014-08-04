package org.shouthost.essentials.commands;

import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.utils.config.Player;
import org.shouthost.essentials.utils.config.Utils;

import java.util.List;

public class CommandTpAccept extends Command {
	@Override
	public String getPermissionNode() {
		return "essentials.tpa.accept";
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
	protected void processCommand(Player player, List<String> args) {
		if (Essentials.tpRequest.containsKey(player.getPersistentID())) {
			Player target = Utils.getPlayerFromUUID(Essentials.tpRequest.get(player.getPersistentID()));
			target.sendMessage("" + player.getPlayerName() + " accepted request");
			player.teleport(target.getLocation());
			Essentials.tpRequest.remove(player.getPersistentID());
			return;
		} else {
			player.sendMessage(EnumChatFormatting.RED + "You have no request available at this time");
		}
	}

	@Override
	public String getCommandName() {
		return "tpaccept";
	}

	@Override
	public String getCommandUsage(Player player) {
		return "/tpaccept";
	}
}
