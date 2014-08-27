package org.shouthost.essentials.commands;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.entity.Player;

import java.lang.management.ManagementFactory;
import java.util.List;

public class UtilsCommands extends CommandListener {

    @Commands(name = "refresh",
            permission = "essentials.command.refresh",
            syntax = "Resend chunks around you",
            description = "",
            commandblocks = false,
            console = false)
    public static void refresh(Player player, List<String> args) {
        if (args.size() == 2) {
            Player player2 = getPlayerFromString(args.get(0));
            int count = player2.refresh(Integer.parseInt(args.get(1)));
            player2.sendMessage(EnumChatFormatting.GREEN + String.valueOf(count) + "chunks have been refreshed");
        } else {
            int count = player.refresh(Integer.parseInt(args.get(0)));
            player.sendMessage(EnumChatFormatting.GREEN + String.valueOf(count) + "chunks have been refreshed");
        }
    }

    @Commands(name = "sudo",
            permission = "essentials.command.sudo",
            syntax = "<player> <command>",
            alias = {"do", "make"},
            description = "Force a player to issue a command",
            commandblocks = false,
            console = false)
    public static void sudo(Player player, List<String> args) {
        Player t = getPlayerFromString(args.get(0));
        String arguments = "";
        for (int i = 1; i < args.size(); i++)
            arguments += args.get(i) + " ";
        t.exec(arguments);
    }

    @Commands(name = "smite",
            permission = "essentials.command.smite",
            syntax = "[player]",
            alias = {"lightning", "thor", "strike"},
            description = "Strike a player or entity with lightning",
            commandblocks = false,
            console = false)
    public static void smite(Player player, List<String> args) {
        if (!args.isEmpty()) {
            Player target = getPlayerFromString(args.get(0));
            player.strike(target.getLocation());
        } else {
            player.strike(player.getEyeLocation(false));
        }
    }

    @Commands(name = "inventorysee",
            permission = "essentials.command.inventorysee",
            syntax = "[player]",
            alias = {"invsee"},
            description = "Able to see players inventory")
    public static void invsee(Player player, List<String> args) {
        if (args.isEmpty()) {
            player.viewInventory(player);
        } else {
            Player target = getPlayerFromString(args.get(0));
            if (target == null || target.getPlayer().isDead) {
                return;
            }
            player.viewInventory(target);
        }
    }

    @Commands(name = "heal",
            permission = "essentials.command.heal",
            syntax = "[player]",
            description = "",
            commandblocks = false,
            console = false)
    public static void heal(Player player, List<String> args) {
        if (args.isEmpty()) {
            if (!player.hasPermission("essentials.command.heal.self")) {
                player.sendMessage(EnumChatFormatting.RED + "You do not have permission to perform this action");
                return;
            }
            player.getPlayer().setHealth(200);
            player.sendMessage(EnumChatFormatting.GREEN + "You have been healed");
        } else {
            Player target = getPlayerFromString(args.get(0));
            if (target == null) {
                player.sendMessage(EnumChatFormatting.RED + "Player does not exist");
                return;
            }
            target.getPlayer().setHealth(200);
        }
    }

    @Commands(name = "burn",
            permission = "essentials.command.burn",
            syntax = "[player]",
            description = "",
            commandblocks = false,
            console = false)
    public static void burn(Player player, List<String> args) {
        if (args.isEmpty()) {
            player.getPlayer().setFire(2);
        } else {
            Player target = getPlayerFromString(args.get(0));
            if (target == null) {
                player.sendMessage(EnumChatFormatting.RED + "Cannot burn what doesn't exist");
                return;
            }
            target.getPlayer().setFire(2);
        }
    }

    //TODO: Rewrite to use the packet system
    @Commands(name = "vanish",
            alias = {"v"},
            permission = "essentials.command.vanish")
    public static void vanish(Player player, List<String> args) {
        if (Essentials.vanishList.contains(player.getPlayer().getUniqueID())) {
            Essentials.vanishList.remove(player.getPlayer().getUniqueID());
            MinecraftServer.getServer().getConfigurationManager().playerEntityList.add(player);
            player.getPlayer().removePotionEffect(Potion.invisibility.id);
        } else {
            Essentials.vanishList.add(player.getPlayer().getUniqueID());
            MinecraftServer.getServer().getConfigurationManager().playerEntityList.remove(player);
            player.getPlayer().addPotionEffect(new PotionEffect(Potion.invisibility.id, 90000, 90000));
        }
    }

    @Commands(name = "kill",
            permission = "essentials.command.kill",
            syntax = "[player]",
            description = "To kill a player (and soon an entity)",
            commandblocks = false,
            console = false)
    public static void kill(Player player, List<String> args) {
        if (args.isEmpty()) {
            player.suicide();
        } else {
            Player target = getPlayerFromString(args.get(0));
            if (target != null) {
                target.suicide();
            } else {
                player.sendMessage("Player " + args.get(0) + " does not exist");
            }
        }
    }

    @Commands(name = "lag",
            permission = "essentials.command.lag",
            alias = {"gc"})
    public static void lag(Player player, List<String> args) {
        //Uptime
        Long uptime = ManagementFactory.getRuntimeMXBean().getStartTime();

        //Memory
        long maxmem = Runtime.getRuntime().totalMemory() / 1024L / 1024L;
        long freemem = Runtime.getRuntime().freeMemory() / 1024L / 1024L;
        long memuse = maxmem - freemem / 1024L / 1024L;

        player.sendMessage("Uptime: " + uptime);//Will implement a parser for uptime
        player.sendMessage("Maximum memory: " + maxmem + " MB.");
        player.sendMessage("Used Memory: " + memuse + " MB.");
        player.sendMessage("Free memory: " + freemem + " MB.");

        for (WorldServer world : MinecraftServer.getServer().worldServers) {
            String dim = world.provider.getDimensionName();
            int loadedChunksCount = world.theChunkProviderServer.getLoadedChunkCount();
            int loadedEntityCount = world.loadedEntityList.size();
            int loadedTileCount = world.loadedTileEntityList.size();
            player.sendMessage("World \"" + dim + "\": " + loadedChunksCount + " chunks, " + loadedEntityCount + " entities, " + loadedTileCount + " tiles.");
        }
    }

    private static long mean(long[] values) {

        long sum = 0l;
        for (long v : values) {
            sum += v;
        }

        return sum / values.length;
    }

    private double getWorldTPS(World world) {
        double worldTickTime = mean(MinecraftServer.getServer().worldTickTimes.get(world.provider.dimensionId)) * 1.0E-6D;
        return Math.min(1000.0 / worldTickTime, 20);
    }
}
