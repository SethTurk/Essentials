package org.shouthost.essentials.commands;

import org.shouthost.essentials.entity.Player;

import java.util.List;

public class CommandGive extends Command {
    @Override
    public String getPermissionNode() {
        return "essentials.command.give";
    }

    @Override
    public boolean canConsoleUseCommand() {
        return true;
    }

    @Override
    public boolean canCommandBlockUseCommand() {
        return true;
    }

    @Override
    public String getCommandName() {
        return "give";
    }

    @Override
    public String getCommandUsage(Player player) {
        return null;
    }

    @Override
    public void processCommand(Player player, List<String> args) {

    }
}
