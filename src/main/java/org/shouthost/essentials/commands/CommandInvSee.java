package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import org.shouthost.essentials.utils.config.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandInvSee extends ECommandBase {
    @Override
    public List<String> getCommandAliases() {
        ArrayList<String> aliasList = new ArrayList<String>();
        aliasList.add("invsee");
        return aliasList;
    }

    @Override
    public String getPermissionNode() {
        return "essentials.inventorysee";
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
        Player player = new Player((EntityPlayerMP) commandSender);
        if (args.size() <= 0) {
            player.viewInventory((net.minecraft.entity.player.EntityPlayerMP) commandSender);
        } else {
            EntityPlayerMP target = getPlayerFromString(args.get(0));
            if (target == null || target.isDead) {
                return;
            }
        }
    }

    @Override
    public String getCommandName() {
        return "inventorysee";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return null;
    }
}
