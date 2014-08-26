package org.shouthost.essentials.commands;

import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.entity.Player;

import java.util.List;

public class CommandHeal extends Command {
    @Override
    public String getPermissionNode() {
        return "essentials.command.heal";
    }

    @Override
    public boolean canConsoleUseCommand() {
        return true;
    }

    @Override
    public boolean canCommandBlockUseCommand() {
        return false;
    }

    @Override
    public String getCommandName() {
        return "heal";
    }

    @Override
    public String getCommandUsage(Player player) {
        return "/heal [<player>]";
    }

    @Override
    public void processCommand(Player player, List<String> args) {
        if (args.isEmpty()) {
            //if (!(player instanceof EntityPlayer)) return;
            if (!player.hasPermission(getPermissionNode() + ".self")) {
                player.sendMessage(EnumChatFormatting.RED + "You do not have permission to this action");
                return;
            }
            player.getPlayer().setHealth(200);
            player.sendMessage(EnumChatFormatting.GREEN + "You have been healed");
            return;
        } else {
            Player target = getPlayerFromString(args.get(0));
            if (target == null) {
                player.sendMessage(EnumChatFormatting.RED + "Player does not exist");
                return;
            }
            target.getPlayer().setHealth(200);
        }
    }
}
