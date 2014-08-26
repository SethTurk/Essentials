package org.shouthost.essentials.commands;

import org.shouthost.essentials.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSmite extends Command {
    @Override
    public String getPermissionNode() {
        return "essentials.command.smite";
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
    public String getCommandUsage(Player player) {
        return "/smite [<player>] [<damage>] ";
    }

    @Override
    public void processCommand(Player player, List<String> args) {
//		if (args.size() == 1 && (!canConsoleUseCommand() || !canCommandBlockUseCommand()))
//			throw new WrongUsageException(getCommandUsage(player));
        if (!args.isEmpty()) {
            Player target = getPlayerFromString(args.get(0));
            player.strike(target.getLocation());
        } else {
            player.strike(player.getEyeLocation(false));
        }
    }
}
