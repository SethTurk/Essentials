package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;

import java.util.List;

/**
 * Created by Darius on 5/20/2014.
 */
public class CommandBan extends ECommandBase {
    @Override
    public String getCommandName() {
        return "ban";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/ban <player> [reason]";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, List<String> args) {

    }

    @Override
    public String getPermissionNode() {
        return "essentials.ban";
    }

    @Override
    public boolean canConsoleUseCommand() {
        return true;
    }

    @Override
    public boolean canCommandBlockUseCommand() {
        return false;
    }
}
