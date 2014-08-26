package org.shouthost.essentials.commands;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldServer;
import org.shouthost.essentials.entity.Player;
import org.shouthost.essentials.json.players.Homes;
import org.shouthost.essentials.utils.Location;

import java.util.List;

public class HomeCommands extends CommandListener {
    @Commands(name = "home",
            permission = "essentials.command.home",
            syntax = "<name>",
            description = "",
            commandblocks = false,
            console = false)
    public static void home(Player player, List<String> args) {
        if (player.getPlayer().isRiding()) {
            player.sendMessage(EnumChatFormatting.RED + "You are not allowed to go home while riding an entity");
            return;
        }
        if (args.isEmpty()) {
            Homes home = player.getHome("home");
            //if (home == null)
            if (home == null && player.getHomeCount() == 0) {
                player.sendMessage(EnumChatFormatting.RED + "You have no set home!");
            } else if (home == null && player.getHomeCount() > 0) {
                StringBuilder sb = new StringBuilder();
                player.sendMessage(EnumChatFormatting.GREEN + "List of Homes:");
                for (String hm : player.getHomeList()) {
                    sb.append(hm).append(" ");
                }
                player.sendMessage(EnumChatFormatting.YELLOW + sb.toString());
            } else {
                WorldServer world = MinecraftServer.getServer().worldServerForDimension(home.getWorld());
                if (world == null) {
                    player.sendMessage(EnumChatFormatting.RED + "Dimension `" + home.getWorld() + " does not exist");
                    return;
                }
                player.teleportTo(world, home.getX(), home.getY(), home.getZ());
            }
        } else {
            Homes home = player.getHome(args.get(0));
            if (home == null) {
                player.sendMessage(EnumChatFormatting.RED + "Home `" + args.get(0) + "` does not exist!");
                return;
            }
            WorldServer world = MinecraftServer.getServer().worldServerForDimension(home.getWorld());
            if (world == null) {
                player.sendMessage(EnumChatFormatting.RED + "Dimension `" + home.getWorld() + " does not exist");
                return;
            }
            player.teleportTo(world, home.getX(), home.getY(), home.getZ());
        }
    }

    @Commands(name = "sethome",
            permission = "essentials.command.home.set",
            syntax = "<name>",
            description = "",
            commandblocks = false,
            console = false)
    public static void sethome(Player player, List<String> args) {
        if (args.isEmpty()) {
            String name = "home";
            player.setHome(name);
            player.sendMessage(EnumChatFormatting.GREEN + "Your home have been set!");
        } else {
            //find if home exist
            Homes home = player.getHome(args.get(0));
            if (home != null) {
                player.sendMessage(EnumChatFormatting.RED + "Home '" + args.get(0) + "' already exist!");
            } else {
                Location loc = player.getLocation();
                player.setHome(args.get(0), (int) player.getPosX(), (int) player.getPosY(), (int) player.getPosZ());
                player.sendMessage(EnumChatFormatting.GREEN + "Your home have been set!");
            }

        }
    }

    @Commands(name = "delhome",
            permission = "essentials.command.home.delete",
            syntax = "<name>",
            description = "",
            commandblocks = false,
            console = false)
    public static void delhome(Player player, List<String> args) {
        Homes home = player.getHome(args.get(0));
        if (home != null) {
            player.delhome(home);
            player.sendMessage(EnumChatFormatting.GREEN + "Home '" + args.get(0) + "' has been deleted");
        } else {
            player.sendMessage(EnumChatFormatting.RED + "'" + args.get(0) + "' is not a valid home entry");
        }
    }
}
