package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;

import java.util.List;

public class CommandTp extends ECommandBase {
    @Override
    public String getPermissionNode() {
        return "essentials.tp";
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
    protected void processCommand(ICommandSender commandSender, List<String> arguments) {

    }

    @Override
    public String getCommandName() {
        return null;
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return null;
    }
}
