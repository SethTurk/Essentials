package org.shouthost.essentials.commands;

import org.shouthost.essentials.entity.Player;

import java.util.List;

public class CommandFly extends Command {
    @Override
    public String getPermissionNode() {
        return "essentials.command.fly";
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
    public String getCommandUsage(Player player) {
        return "/fly";
    }

    @Override
    public void processCommand(Player player, List<String> args) {

    }

    @Override
    public String getCommandName() {
        return "fly";
    }
}
