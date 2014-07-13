package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandSudo extends ECommandBase {
	@Override
	public String getPermissionNode() {
		return "essentials.sudo";
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

	@Override
	public String getCommandName() {
		return "sudo";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/sudo <player> <command>";
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {
		Player executor = new Player(iCommandSender);
		if (args.isEmpty() || args.size() < 2) throw new WrongUsageException(getCommandUsage(iCommandSender));
		if (!args.isEmpty()) {
			EntityPlayerMP t = getPlayerFromString(args.get(0));
			if(t == null) throw new WrongUsageException(getCommandUsage(iCommandSender));
			Player target = new Player(t);
			//TODO add arguments with the commands
			String cPa = "";
			for (int i = 1; i < args.size(); i++)
				cPa += args.get(i) + " ";
			target.exec(cPa);
		}
	}
}
