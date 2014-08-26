package org.shouthost.essentials.commands;

import org.shouthost.essentials.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darius on 5/20/2014.
 */
public class CommandPardon extends Command {
    @Override
    public String getPermissionNode() {
        return "essentials.command.pardon";
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
        return "pardon";
    }

    @Override
    public String getCommandUsage(Player player) {
        return "/pardon <player>";
    }

    @Override
    public void processCommand(Player player, List<String> args) {

    }

    @Override
    public List getCommandAliases() {
        ArrayList aliases = new ArrayList();
        aliases.add("unban");
        return aliases;
    }
}
