package org.shouthost.essentials.commands;

import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.entity.Player;

import java.util.List;

public class CommonCommands extends CommandListener {
    @Commands(name = "ping",
            permission = "essentials.command.ping",
            syntax = "",
            description = "",
            commandblocks = false,
            console = false)
    public static void ping(Player player, List<String> args) {
        player.sendMessage(EnumChatFormatting.GREEN + "pong");
    }

    @Commands(name = "suicide",
            permission = "essentials.command.suicide",
            syntax = "",
            description = "",
            commandblocks = false,
            console = false)
    public static void suicide(Player player, List<String> args) {
        player.suicide();
    }
}
