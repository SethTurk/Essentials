package org.shouthost.essentials.commands;

import org.shouthost.essentials.entity.Player;

import java.util.List;

public class CommandButcher extends Command {
    //TODO: Will this command stay or go?
    @Override
    public String getCommandName() {
        return "butcher";
    }

    @Override
    public String getCommandUsage(Player player) {
        return null;
    }

    @Override
    public void processCommand(Player player, List<String> args) {
        boolean passive = false;
        boolean hostile = true;
        final int radius = 10;
        if (!args.isEmpty()) {
            if (args.contains("+p") && !args.contains("-p")) passive = true;
            if (args.contains("+h") && !args.contains("-h")) hostile = true;
        }
    }

    @Override
    public String getPermissionNode() {
        return "essentials.command.butcher";
    }

    @Override
    public boolean canConsoleUseCommand() {
        return false;
    }

    @Override
    public boolean canCommandBlockUseCommand() {
        return false;
    }
}
