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
            description = "To kill yourself",
            commandblocks = false,
            console = false)
    public static void suicide(Player player, List<String> args) {
        player.suicide();
    }

    @Commands(name = "essentials",
            permission = "essentials.command.essentials",
            syntax = "[option]",
            description = "",
            alias = {"ess"},
            commandblocks = false,
            console = false)
    public static void essentials(Player player, List<String> args) {
        if (args.isEmpty() || (!args.isEmpty() && !args.get(0).equals("help"))) {
            player.sendMessage(EnumChatFormatting.GREEN + "do /ess help");
        } else if (!args.isEmpty() && args.get(0).equals("help")) {
            if (args.size() == 1 || (args.size() == 2 && args.get(1) == "1")) {
                //print first page of our commands
                player.sendMessage("Essentials Commands in Page 1");
                for (int i = 0; i < 5; i++) {
                    CommandManager.CommandStructure structure = CommandManager.structure.get(i + "-page-0");
                    player.sendMessage(EnumChatFormatting.GREEN + "/" + structure.name + EnumChatFormatting.YELLOW + " " + structure.syntax + EnumChatFormatting.RESET + " " + structure.description);
                }
                player.sendMessage("do /ess help 2 for next list");
            }
        }
    }
}
