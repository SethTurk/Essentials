package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.compat.Location;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandTp extends ECommandBase {
    @Override
    public String getPermissionNode() {
        return "essentials.tp";
    }

    @Override
    public boolean canConsoleUseCommand() {
        return false;
    }

    @Override
    public boolean canCommandBlockUseCommand() {
        return false;
    }

    @Override
    protected void processCommand(ICommandSender commandSender, List<String> args) {
        if (args.isEmpty()) throw new WrongUsageException(getCommandUsage(commandSender));
        //TODO: Perform checks to see if they are teleporting to player, player -> player, or player -> coords
        if (args.size() == 3) {
            //teleport player to coords
            EntityPlayerMP target = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(args.get(0));
            if (target == null) throw new WrongUsageException(getCommandUsage(commandSender));
            Player player = new Player(target);
            int x = Integer.parseInt(args.get(1));
            int y = Integer.parseInt(args.get(2));
            int z = Integer.parseInt(args.get(3));
            player.teleportTo(new Location(target.worldObj, x, y, z));
        } else if (args.size() == 2) {
            //teleport player -> player
            EntityPlayerMP target1 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(args.get(0));
            EntityPlayerMP target2 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(args.get(1));
            //TODO: add checks for both targets to point out who is online/exist
            if (target1 == null || target2 == null) return;

            Player player = new Player(target1);
            player.teleportTo(target2);
        }
    }

    @Override
    public String getCommandName() {
        return null;
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return EnumChatFormatting.RED + "/tp [<player> [<x> <y> <z> | <player>]";
    }
}
