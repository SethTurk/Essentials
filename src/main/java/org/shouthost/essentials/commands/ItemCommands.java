package org.shouthost.essentials.commands;

import org.shouthost.essentials.entity.Player;

import java.util.List;

public class ItemCommands extends CommandListener {

    @Commands(name = "give",
            permission = "essentials.command.give",
            syntax = "[name|mode] [mode]",
            description = "",
            disableInProduction = true)
    public static void give(Player player, List<String> args) {
        
    }

    @Commands(name = "item",
            permission = "essentials.command.item",
            alias = {"i"},
            description = "Give an item",
            disableInProduction = true)
    public static void item(Player player, List<String> args) {
        //TODO: Rewrite
    }
}
