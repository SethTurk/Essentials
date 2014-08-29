package org.shouthost.essentials.commands;

import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.entity.Player;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BansCommands extends CommandListener {
    @Commands(name = "ban",
            permission = "essentials.command.ban",
            syntax = "<name> [reason]",
            description = "Ban a player",
            disableInProduction = true)
    public static void ban(Player player, List<String> args) {

    }

    @Commands(name = "banip",
            permission = "essentials.command.banip",
            syntax = "<ip> [reason]",
            description = "Ban an IP",
            disableInProduction = true)
    public static void banip(Player player, List<String> args) {

    }

    @Commands(name = "tempban",
            permission = "essentials.command.tempban",
            syntax = "<name> [time] [reason]",
            description = "Ban a player for a set time",
            disableInProduction = true)
    public static void tempban(Player player, List<String> args) {

    }


    @Commands(name = "kick",
            permission = "essentials.command.kick",
            syntax = "<name> [reason]",
            description = "Kick a player",
            commandblocks = true,
            console = true)
    public static void kick(Player player, List<String> args) {
        Player target = getPlayerFromString(args.get(0));
        if (target == null) {
            player.sendErrorMessage("Player " + args.get(0) + " does not exist on the server.");
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

    @Commands(name = "kickall",
            permission = "essentials.command.kickall",
            syntax = "[reason]",
            description = "Kick every player",
            commandblocks = true,
            console = true)
    public static void kickall(Player player, List<String> args) {

        //build reason
        StringBuilder sb = new StringBuilder();
        if (args.size() >= 1)
            for (int i = 1; i < args.size(); i++)
                sb.append(args.get(i));
        else
            sb.append("You have been kicked from the server ");

        Iterator it = Essentials.playerList.asMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, Player> pl = (Map.Entry<UUID, Player>) it.next();
            Player target = pl.getValue();
            target.kick(sb.toString());
            it.remove();
        }
    }
}
