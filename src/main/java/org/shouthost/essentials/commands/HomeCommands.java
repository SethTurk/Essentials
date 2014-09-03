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
            description = "To teleport to a home")
    public static void home(Player player, List<String> args) {
        if (player.getPlayer().isRiding()) {
            player.sendErrorMessage("You are not allowed to go home while riding an entity");
            return;
        }
        if (args.isEmpty()) {
            Homes home = player.getHome("home");
            //if (home == null)
            if (home == null && player.getHomeCount() == 0) {
                player.sendErrorMessage("You have no set home!");
            } else if (home != null && player.getHomeCount() > 1) {
                StringBuilder sb = new StringBuilder();
                player.sendSuccessMessage("List of Homes:");
                for (String hm : player.getHomeList()) {
                    sb.append(hm).append(" ");
                }
                player.sendMessage(EnumChatFormatting.YELLOW + sb.toString());
            } else if (home != null) {
                WorldServer world = MinecraftServer.getServer().worldServerForDimension(home.getWorld());
                if (world == null) {
                    player.sendErrorMessage("Dimension `%s` does not exist", home.getWorld());
                    return;
                }
                player.teleportTo(world, home.getX(), home.getY(), home.getZ());
            }
        } else {
            Homes home = player.getHome(args.get(0));
            if (home == null) {
                player.sendErrorMessage("Home `%s` does not exist!", args.get(0));
                return;
            }
            WorldServer world = MinecraftServer.getServer().worldServerForDimension(home.getWorld());
            if (world == null) {
                player.sendErrorMessage("Dimension `%s` does not exist", home.getWorld());
                return;
            }
            player.teleportTo(world, home.getX(), home.getY(), home.getZ());
        }
    }

    @Commands(name = "sethome",
            permission = "essentials.command.home.set",
            syntax = "<name>",
            description = "To set a home at your current position")
    public static void sethome(Player player, List<String> args) {
        if (args.isEmpty()) {
            String name = "home";
            player.setHome(name);
            player.sendSuccessMessage("Your home have been set!");
        } else {
            //find if home exist
            Homes home = player.getHome(args.get(0));
            if (home != null) {
                player.sendErrorMessage("Home '%s' already exist!", args.get(0));
            } else {
                Location loc = player.getLocation();
                player.setHome(args.get(0), (int) player.getPosX(), (int) player.getPosY(), (int) player.getPosZ());
                player.sendSuccessMessage("Your home have been set!");
            }

        }
    }

    @Commands(name = "delhome",
            permission = "essentials.command.home.delete",
            syntax = "<name>",
            description = "To delete a home")
    public static void delhome(Player player, List<String> args) {
        Homes home = player.getHome(args.get(0));
        if (home != null) {
            player.delhome(home);
            player.sendSuccessMessage("Home '%s' has been deleted", args.get(0));
        } else {
            player.sendErrorMessage("'%s' is not a valid home entry", args.get(0));
        }
    }
}
