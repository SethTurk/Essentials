package org.shouthost.essentials.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.entity.Player;
import org.shouthost.essentials.tasks.BackupTask;
import org.shouthost.essentials.utils.DateUtils;
import org.shouthost.essentials.utils.KitTemplate;

import java.lang.management.ManagementFactory;
import java.util.List;

public class UtilsCommands extends CommandListener {
    @Commands(name = "setkit",
            permission = "essentials.command.setkit",
            disableInProduction = true)
    public static void setkit(Player player, List<String> args) {
        InventoryPlayer pl = player.getPlayer().inventory;
        KitTemplate kit = new KitTemplate(args.get(0), pl.mainInventory, null);
        kit.constructKitFromInventory();
    }

    @Commands(name = "kit",
            permission = "essentials.command.kit",
            disableInProduction = true)
    public static void kit(Player player, List<String> args) {
        KitTemplate kit = new KitTemplate(args.get(0));
        kit.reconstructKit();
        kit.giveKitToPlayer(player.getPlayer());
    }

    @Commands(name = "backup",
            permission = "essentials.command.backup",
            disableInProduction = true)
    public static void backup(Player player, List<String> args) {
        Essentials.schedule.scheduleAsyncTaskDelay(new BackupTask(), 5L);
    }

    @Commands(name = "refresh",
            permission = "essentials.command.refresh",
            syntax = "Resend chunks around you (Use with caution)",
            description = "Refresh chunks in a given radius (WIP)")
    public static void refresh(Player player, List<String> args) {
        if (args.size() == 2) {
            Player player2 = getPlayerFromString(args.get(0));
            int count = player2.refresh(Integer.parseInt(args.get(1)));
            player2.sendSuccessMessage("%s chunks have been refreshed", String.valueOf(count));
        } else {
            int count = player.refresh(Integer.parseInt(args.get(0)));
            player.sendSuccessMessage("%s chunks have been refreshed", String.valueOf(count));
        }
    }

    @Commands(name = "sudo",
            permission = "essentials.command.sudo",
            syntax = "<player> <command>",
            alias = {"do", "make"},
            description = "Force a player to issue a command",
            disableInProduction = true)
    public static void sudo(Player player, List<String> args) {
        Player t = getPlayerFromString(args.get(0));
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.size(); i++)
            sb.append(args.get(i)).append(" ");
        t.exec(sb.toString());
    }

    @Commands(name = "inventorysee",
            permission = "essentials.command.inventorysee",
            syntax = "<player>",
            alias = {"invsee"},
            description = "Able to see players inventory (to be rewritten)")
    public static void invsee(Player player, List<String> args) {
        if (!args.isEmpty()) {
            Player target = getPlayerFromString(args.get(0));
            if (target == null || target.getPlayer().isDead) {
                return;
            }
            player.viewInventory(target);
        } else {
            player.sendErrorMessage("Invalid usage of command");
            throw new WrongUsageException(EnumChatFormatting.RED + "/invsee <player>");
        }
    }

    @Commands(name = "heal",
            permission = "essentials.command.heal",
            syntax = "[player]",
            description = "")
    public static void heal(Player player, List<String> args) {
        if (args.isEmpty()) {
            if (!player.hasPermission("essentials.command.heal.self")) {
                player.sendErrorMessage("You do not have permission to perform this action");
                return;
            }
            player.setHealth(200);
            player.sendSuccessMessage("You have been healed");
        } else {
            Player target = getPlayerFromString(args.get(0));
            if (target == null) {
                player.sendErrorMessage("Player does not exist");
                return;
            }
            target.setHealth(200);
        }
    }

    @Commands(name = "powertools",
            permission = "essentials.command.powertools",
            syntax = "[set|del] [name] [command]",
            description = "To assign a command to an item",
            disableInProduction = true)
    public static void powertools(Player player, List<String> args) {
        if (!args.isEmpty()) {
            if (args.size() == 2 && args.get(0).equalsIgnoreCase("del")) {
                if (player.removePowerTool(player.getPowerTool(args.get(1))))
                    player.sendSuccessMessage("Your powertool have been removed");
                else player.sendErrorMessage("Your powertool was not successfully remove. Does it exist?");
            } else if (args.size() == 3 && args.get(0).equalsIgnoreCase("set")) {
                String name = args.get(1);
                String command = args.get(2);
                if (player.getPlayer().canCommandSenderUseCommand(0, command)) {
                    ItemStack item = player.getPlayer().getHeldItem();
                    if (item == null) {
                        player.sendErrorMessage("You can not set powertool to your hand");
                        return;
                    }
                    if (player.addPowerTool(name, item, command)) {
                        player.sendSuccessMessage("Tool have been assigned with command");
                    } else {
                        player.sendErrorMessage("Unable to set tool");
                    }

                } else {
                    player.sendErrorMessage("You do not have permission to use this command");
                }
            }
        }
    }

    @Commands(name = "burn",
            permission = "essentials.command.burn",
            syntax = "[player]",
            description = "To burn a player")
    public static void burn(Player player, List<String> args) {
        if (args.isEmpty()) {
            player.setFire(2);
        } else {
            Player target = getPlayerFromString(args.get(0));
            if (target == null) {
                player.sendErrorMessage("Cannot burn what doesn't exist");
                return;
            }
            target.setFire(2);
        }
    }

    //TODO: Test on server
    @Commands(name = "vanish",
            alias = {"v"},
            permission = "essentials.command.vanish",
            disableInProduction = true)
    public static void vanish(Player player, List<String> args) {
        player.vanish();
        player.sendSuccessMessage("You are " + (player.isVanished() ? "vanished!" : "not vanished!"));
    }

    @Commands(name = "kill",
            permission = "essentials.command.kill",
            syntax = "[player]",
            description = "To kill a player (and soon an entity)")
    public static void kill(Player player, List<String> args) {
        if (args.isEmpty()) {
            player.suicide();
        } else {
            Player target = getPlayerFromString(args.get(0));
            if (target != null) {
                target.suicide();
            } else {
                player.sendErrorMessage("Player " + args.get(0) + " does not exist");
            }
        }
    }

    @Commands(name = "gamemode",
            permission = "essentials.command.gamemode",
            syntax = "[name|mode] [mode]",
            description = "",
            alias = {"gm"},
            disableInProduction = true)
    public static void give(Player player, List<String> args) {
        if (args.size() == 1) {
            String mode = args.get(0);

            if (mode.equalsIgnoreCase("s") || mode.equalsIgnoreCase("survival") || mode.equalsIgnoreCase("0")) {
                player.setGameMode(WorldSettings.GameType.SURVIVAL);
                player.sendSuccessMessage("Your gamemode have been changed");
            } else if (mode.equalsIgnoreCase("c") || mode.equalsIgnoreCase("creative") || mode.equalsIgnoreCase("1")) {
                player.setGameMode(WorldSettings.GameType.CREATIVE);
                player.sendSuccessMessage("Your gamemode have been changed");
            } else if (mode.equalsIgnoreCase("a") || mode.equalsIgnoreCase("adventure") || mode.equalsIgnoreCase("2")) {
                player.setGameMode(WorldSettings.GameType.ADVENTURE);
                player.sendSuccessMessage("Your gamemode have been changed");
            } else {
                player.sendErrorMessage("%s is an invalid gamemode", mode);
            }

        }
    }

    @Commands(name = "lag",
            permission = "essentials.command.lag",
            alias = {"gc"})
    public static void lag(Player player, List<String> args) {
        if (args.isEmpty()) {
            //Uptime
            Long uptime = ManagementFactory.getRuntimeMXBean().getUptime();
            //Memory
            long maxmem = Runtime.getRuntime().totalMemory() / 1024L / 1024L;
            long freemem = Runtime.getRuntime().freeMemory() / 1024L / 1024L;
            long memuse = maxmem - freemem / 1024L / 1024L;

            player.sendMessage("Uptime: %s", DateUtils.toTime(uptime));//Will implement a parser for uptime
            player.sendMessage("Maximum memory: %d MB.", maxmem);
            player.sendMessage("Used Memory: %d MB.", memuse);
            player.sendMessage("Free memory: %d MB.", freemem);
            int count = 0;
            int totalchunks = 0;
            int totalentity = 0;
            int totaltileentity = 0;
            int totaltps = 0;
            for (WorldServer world : MinecraftServer.getServer().worldServers) {
                totalchunks = totalchunks + world.theChunkProviderServer.getLoadedChunkCount();
                totalentity = totalentity + world.loadedEntityList.size();
                totaltileentity = totaltileentity + world.loadedTileEntityList.size();
                totaltps = totaltps + (int) getWorldTPS(world);
                count++;
            }
            player.sendMessage("Total TPS: " + getColorForTPS(totaltps / count) + (totaltps / count));
            player.sendMessage("Total Chunks Loaded: " + (totalchunks));
            player.sendMessage("Total Loaded Entity: " + (totalentity));
            player.sendMessage("Total Tile Entities: " + (totaltileentity));
            player.sendMessage("Total Worlds: " + count);
        } else if (args.size() == 1) {
            int dim = CommandBase.parseInt(player.getPlayer(), args.get(0));
            WorldServer world = MinecraftServer.getServer().worldServerForDimension(dim);
            if (world == null) {
                player.sendErrorMessage("Dimension %d does not exist.", dim);
                world = MinecraftServer.getServer().worldServerForDimension(0);
            }
            provideInfo(player, world);
        }


    }

    private static void provideInfo(Player player, WorldServer world) {
        String dim = world.provider.getDimensionName();
        int loadedChunksCount = world.theChunkProviderServer.getLoadedChunkCount();
        int loadedEntityCount = world.loadedEntityList.size();
        int loadedTileCount = world.loadedTileEntityList.size();
        double tps = getWorldTPS(world);
        player.sendMessage("World \"%s\"", dim);
        player.sendMessage("    TPS: %s%s", getColorForTPS((int) tps), tps);
        player.sendMessage("    Loaded Chunks: %d", loadedChunksCount);
        player.sendMessage("    Entity Count: %d", loadedEntityCount);
        player.sendMessage("    TileEntity Count: %d", loadedTileCount);
    }

    private static long mean(long[] values) {

        long sum = 0l;
        for (long v : values) {
            sum += v;
        }

        return sum / values.length;
    }

    private static EnumChatFormatting getColorForTPS(int tps) {
        if (tps >= 1 && tps <= 7)
            return EnumChatFormatting.RED;
        else if (tps >= 8 && tps <= 13)
            return EnumChatFormatting.YELLOW;
        else if (tps >= 14 && tps <= 18)
            return EnumChatFormatting.DARK_GREEN;
        else if (tps >= 19 && tps == 20)
            return EnumChatFormatting.GREEN;
        else
            return EnumChatFormatting.RESET;
    }

    private static double getWorldTPS(World world) {
        double worldTickTime = mean(MinecraftServer.getServer().worldTickTimes.get(world.provider.dimensionId)) * 1.0E-6D;
        return Math.min(1000.0 / worldTickTime, 20);
    }
}
