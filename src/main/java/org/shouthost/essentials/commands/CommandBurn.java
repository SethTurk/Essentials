package org.shouthost.essentials.commands;

import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.entity.Player;

import java.util.List;

public class CommandBurn extends Command {
	@Override
	public String getPermissionNode() {
        return "essentials.command.burn";
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
    public void processCommand(Player player, List<String> args) {
        if (args.isEmpty()) {
            /*if (!(player instanceof EntityPlayer)) {
                player.sendMessage("You can not use this command");
				return;
			}*/
            player.getPlayer().setFire(2);
			return;
		} else {
            Player target = (Player) getPlayerFromString(args.get(0));
            if (target == null) {
				player.sendMessage(EnumChatFormatting.RED + "Player is not online.");
				return;
			}
            target.getPlayer().setFire(2);
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
