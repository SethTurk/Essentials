package org.shouthost.essentials.commands;

import org.shouthost.essentials.entity.Player;

import java.util.List;

public class MessageCommands extends CommandListener {
    @Commands(name = "message",
            permission = "essentials.command.message",
            syntax = "<player> <message>",
            description = "",
            commandblocks = false,
            console = false,
            alias = {"msg"})
    public static void message(Player player, List<String> args) {
        if (args.isEmpty() || args.size() < 2)
            return;

        Player target = getPlayerFromString(args.get(0));

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.size(); i++)
            sb.append(args.get(i));

        player.sendMessageTo(target, sb.toString());
    }

}
