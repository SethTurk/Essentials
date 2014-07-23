package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import org.shouthost.essentials.utils.config.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSmite extends ECommandBase {
	@Override
	public String getPermissionNode() {
		return "essentials.smite";
	}

	@Override
	public List getCommandAliases() {
		ArrayList cmd = new ArrayList();
		cmd.add("lightning");
		cmd.add("strike");
		return cmd;
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
		return "smite";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/smite [<player>] [<damage>] ";
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {
		if (args.size() == 1 && !(iCommandSender instanceof EntityPlayerMP))
			throw new WrongUsageException(getCommandUsage(iCommandSender));
		Player player = new Player(!args.isEmpty() ? (getPlayerFromString(args.get(0)) != null ? getPlayerFromString(args.get(0)) : iCommandSender) : iCommandSender);
		player.strike(player.getEyeLocation(false));
	}
}
