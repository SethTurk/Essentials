package org.shouthost.essentials.commands;


import net.minecraft.command.ICommandSender;

import java.util.List;

public class CommandMute extends ECommandBase {
    @Override
    public String getCommandName() {
        return "mute";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, List<String> args) {

    }

    @Override
    public String getPermissionNode() {
        return "essentials.mute";
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
