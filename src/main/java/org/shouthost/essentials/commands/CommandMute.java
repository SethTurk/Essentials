package org.shouthost.essentials.commands;


import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class CommandMute extends ECommandBase {
    @Override
    public String getCommandName() {
        return "mute";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return EnumChatFormatting.RED+"/mute <player> [<time>] [<reason>]";
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
