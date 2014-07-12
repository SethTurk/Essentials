package org.shouthost.essentials.commands;

import forgeperms.api.ForgePermsAPI;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

/**
 * Created by Darius on 5/20/2014.
 */
public class CommandHeal extends ECommandBase {
    @Override
    public String getPermissionNode() {
        return "essentials.heal";
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
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/heal [<player>]";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, List<String> args) {
        EntityPlayer p = (EntityPlayer) iCommandSender;
        Player player = new Player(p);
        if (args.isEmpty()) {
            if (!(iCommandSender instanceof EntityPlayer)) return;
            if (!player.has(getPermissionNode() + ".self")) {
                player.sendMessage(EnumChatFormatting.RED + "You do not have permission to this action");
                return;
            }
            player.setHealth(200);
            player.sendMessage(EnumChatFormatting.GREEN + "You have been healed");
            return;
        } else {
            Player target = new Player(getPlayerFromString(args.get(0)));
            if (target == null) {
                player.sendMessage(EnumChatFormatting.RED + "Player does not exist");
                return;
            }
            target.setHealth(200);
        }
    }
}
