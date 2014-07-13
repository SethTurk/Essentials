package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandSetWarp extends ECommandBase {
	@Override
	public String getPermissionNode() {
		return "essentials.setwarp";
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
		if(args.isEmpty()) throw new WrongUsageException(getCommandUsage(commandSender));
		Player player = new Player(commandSender);
	}

	@Override
	public String getCommandName() {
		return "setwarp";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return EnumChatFormatting.RED+"/setwarp <name>";
	}
}
