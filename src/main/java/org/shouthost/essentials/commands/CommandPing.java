package org.shouthost.essentials.commands;

import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.entity.Player;

import java.util.List;

public class CommandPing extends Command {
    @Override
    public String getCommandName() {
        return "ping";
    }

    @Override
    public String getCommandUsage(Player player) {
        return "/ping";
    }

    @Override
    public void processCommand(Player player, List<String> args) {
        player.sendMessage(EnumChatFormatting.GREEN + "pong");
    }

    @Override
    public String getPermissionNode() {
        return "essentials.command.ping";
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
