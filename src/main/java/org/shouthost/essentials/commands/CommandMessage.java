package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import org.shouthost.essentials.utils.config.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandMessage extends ECommandBase {

	@Override
	public List<String> getCommandAliases() {
		ArrayList<String> aliasList = new ArrayList<String>();
		aliasList.add("msg");
		aliasList.add("pm");
		return aliasList;
	}

	@Override
	public String getPermissionNode() {
		return "essentials.message";
	}

	@Override
	public boolean canConsoleUseCommand() {
		return true;
	}

	@Override
	public boolean canCommandBlockUseCommand() {
		return true;
	}

	@Override
	protected void processCommand(ICommandSender commandSender, List<String> args) {
		if (args.isEmpty() || args.size() < 2) throw new WrongUsageException(getCommandUsage(commandSender));
		Player player = new Player(commandSender);
		String msg = "";
		for (int i = 1; i < args.size(); i++)
			msg += args.get(i) + " ";
		player.sendMessage(msg);
		player.sendMessageTo(args.get(0), msg);
	}

	@Override
	public String getCommandName() {
		return "message";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/message [player] [message]";
	}


}
