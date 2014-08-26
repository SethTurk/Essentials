package org.shouthost.essentials.commands;

import net.minecraft.command.WrongUsageException;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.entity.Player;

import java.util.List;

public class CommandTp extends Command {
    @Override
    public String getPermissionNode() {
        return "essentials.command.teleport";
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
    public void processCommand(Player player, List<String> args) {
        if (args.isEmpty()) throw new WrongUsageException(getCommandUsage(player));
        //TODO: Perform checks to see if they are teleporting to player, player -> player, or player -> coords
        if (args.size() == 3) {
            //teleport player to coords
            Player target = getPlayerFromString(args.get(0));
            if (target == null) throw new WrongUsageException(getCommandUsage(player));

            int x = Integer.parseInt(args.get(1));
            int y = Integer.parseInt(args.get(2));
            int z = Integer.parseInt(args.get(3));
            player.teleportTo(target.getLocation());
        } else if (args.size() == 2) {
            //teleport player -> player
            Player target1 = getPlayerFromString(args.get(0));
            Player target2 = getPlayerFromString(args.get(1));
            //TODO: add checks for both targets to point out who is online/exist
            if (target1 == null || target2 == null) throw new WrongUsageException(getCommandUsage(player));
            target1.teleportTo(target2.getLocation());
        }
    }

    @Override
    public String getCommandName() {
        return "tp";
    }

    @Override
    public String getCommandUsage(Player Player) {
        return EnumChatFormatting.RED + "/tp [player] [<x> <y> <z>]";
    }
}
