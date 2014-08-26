package org.shouthost.essentials.commands;

import net.minecraft.command.WrongUsageException;
import org.shouthost.essentials.entity.Player;

import java.util.List;

public class CommandTpa extends Command {
    @Override
    public String getPermissionNode() {
        return "essentials.command.tpa";
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
        if (args.isEmpty()) throw new WrongUsageException(getCommandUsage(player));
        Player target = (Player) getPlayerFromString(args.get(0));
        //TODO: Finish rewrite
    }

    @Override
    public String getCommandName() {
        return "tpa";
    }

    @Override
    public String getCommandUsage(Player player) {
        return "/tpa <player>";
    }
}
