package org.shouthost.essentials.commands;

import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandSpawn extends Command {
	@Override
	public String getPermissionNode() {
		return "essentials.spawn";
	}

	@Override
	public boolean canConsoleUseCommand() {
		return true;
	}

	@Override
	public boolean canCommandBlockUseCommand() {
		return false;
	}

	@Override
	protected void processCommand(Player player, List<String> args) {
		if (!(player instanceof EntityPlayerMP)) {
			player.sendMessage("You can perform this action");
			throw new WrongUsageException(getCommandUsage(player));
		}
		//TODO: Work on spawn
	}

	@Override
	public String getCommandName() {
		return "spawn";
	}

	@Override
	public String getCommandUsage(Player player) {
		return "/spawn [<player>]";
	}
}
