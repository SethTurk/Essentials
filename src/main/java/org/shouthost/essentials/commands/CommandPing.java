package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.List;

/**
 * Created by Darius on 5/20/2014.
 */
public class CommandPing extends ECommandBase {
    @Override
    public String getCommandName() {
        return "ping";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, List<String> args) {
        iCommandSender.addChatMessage(new ChatComponentText("pong"));
    }

    @Override
    public String getPermissionNode() {
        return "essentials.ping";
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
    public boolean canUseWithoutPermission() {
        return false;
    }
}
