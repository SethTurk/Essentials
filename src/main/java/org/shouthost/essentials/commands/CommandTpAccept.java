package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.utils.config.Player;
import org.shouthost.essentials.utils.config.Utils;

import java.util.List;

public class CommandTpAccept extends ECommandBase {
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
	protected void processCommand(ICommandSender commandSender, List<String> args) {
		Player player = new Player(commandSender);
		if (Essentials.tpRequest.containsKey(player.getUUID())) {
			Player target = Utils.getPlayerFromUUID(Essentials.tpRequest.get(player.getUUID()));
			target.sendMessage("" + player.getPlayerName() + " accepted request");
			player.teleport(target.getLocation());
			Essentials.tpRequest.remove(player.getUUID());
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
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/tpaccept";
	}
}
