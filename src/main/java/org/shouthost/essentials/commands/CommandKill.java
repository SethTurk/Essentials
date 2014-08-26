package org.shouthost.essentials.commands;

import org.shouthost.essentials.entity.Player;

import java.util.List;

public class CommandKill extends Command {
    @Override
    public String getPermissionNode() {
        return "essentials.command.kill";
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
    public String getCommandName() {
        return "kill";
    }

    @Override
    public String getCommandUsage(Player player) {
        return "/kill [<player>]";
    }

    @Override
    public void processCommand(Player player, List<String> args) {
        if (args.isEmpty()) {
            /*if (!(player instanceof EntityPlayer)) {
				player.sendMessage("You can not kill yourself");
				return;
			}*/
            player.suicide();
        } else if (!args.isEmpty()) {
            Player target = getPlayerFromString(args.get(0));
            if (target != null) {
                player.suicide();
            } else {
                player.sendMessage("Player " + args.get(0) + " does not exist");
            }
        }
    }
}
