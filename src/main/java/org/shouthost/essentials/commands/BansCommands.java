package org.shouthost.essentials.commands;

import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.entity.Player;

import java.util.List;

public class BansCommands extends CommandListener {
    @Commands(name = "ban",
            permission = "essentials.command.ban",
            syntax = "<name> [time] [reason]",
            description = "",
            commandblocks = false,
            console = false)
    public static void CommandBan(Player player, List<String> args) {
        player.sendMessage("This is just a test");
        //Player target = getPlayerFromString(args.get(0));
    }

    @Commands(name = "kick",
            permission = "essentials.command.kick",
            syntax = "<name> [reason]",
            description = "",
            commandblocks = true,
            console = true)
    public static void kick(Player player, List<String> args) {
        Player target = getPlayerFromString(args.get(0));
        if (target == null) {
            player.sendMessage(EnumChatFormatting.RED + "Player " + args.get(0) + " does not exist on the server.");
            return;
        }
        //build reason
        StringBuilder sb = new StringBuilder();
        if (args.size() >= 2)
            for (int i = 1; i < args.size(); i++)
                sb.append(args.get(i));
        else
            sb.append("You have been kicked from the server ");
        target.kick(sb.toString());
    }
}
