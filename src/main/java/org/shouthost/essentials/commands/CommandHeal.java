package org.shouthost.essentials.commands;

import forgeperms.api.ForgePermsAPI;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

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
        if (args.isEmpty()) {
            if (!(iCommandSender instanceof EntityPlayer)) return;
            p.setHealth(200);
            p.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "You have been healed"));
            return;
        } else {
            if (!ForgePermsAPI.permManager.canAccess(p.getDisplayName(), p.worldObj.getWorldInfo().getWorldName(), getPermissionNode() + ".self")) {
                p.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "You do not have permission to this action"));
                return;
            }
            EntityPlayerMP target = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(args.get(0));
            if (target == null) {
                p.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Player does not exist"));
                return;
            }
            target.setHealth(200);
        }
    }
}
