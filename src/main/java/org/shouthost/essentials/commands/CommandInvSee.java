package org.shouthost.essentials.commands;

import org.shouthost.essentials.utils.config.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandInvSee extends Command {
	@Override
	public List<String> getCommandAliases() {
		ArrayList<String> aliasList = new ArrayList<String>();
		aliasList.add("invsee");
		return aliasList;
	}

	@Override
	public String getPermissionNode() {
		return "essentials.inventorysee";
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
		if (args.size() <= 0) {
			player.viewInventory(player);
		} else {
			Player target = new Player(args.get(0));
			if (target == null || target.isDead) {
				return;
			}
			player.viewInventory(target);
		}
	}

	@Override
	public String getCommandName() {
		return "inventorysee";
	}

	@Override
	public String getCommandUsage(Player player) {
		return null;
	}
}
