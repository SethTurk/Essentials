package org.shouthost.essentials.commands;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandBurn extends Command {
	@Override
	public String getPermissionNode() {
		return "essentials.burn";
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
		if (args.isEmpty()) {
			if (!(player instanceof EntityPlayer)) {
				player.sendMessage("You can not use this command");
				return;
			}
			player.setFire(2);
			return;
		} else {
			Player target = new Player(args.get(0));
			if (target == null) {
				player.sendMessage(EnumChatFormatting.RED + "Player is not online.");
				return;
			}
			target.setFire(2);
			return;
		}
	}

	@Override
	public String getCommandName() {
		return "burn";
	}

	@Override
	public String getCommandUsage(Player player) {
		return "/burn [<player>]";
	}
}
