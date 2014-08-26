package org.shouthost.essentials.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import org.shouthost.essentials.entity.Player;
import org.shouthost.permissionforge.api.IHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command extends CommandBase {

    public abstract String getPermissionNode();

    public abstract boolean canConsoleUseCommand();

    public abstract boolean canCommandBlockUseCommand();

    public boolean canUseWithoutPermission() {
        return false;
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return getCommandUsage((Player) iCommandSender);
    }

    public abstract String getCommandUsage(Player player);

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender commandSender) {
        if (!(commandSender instanceof EntityPlayer) && canConsoleUseCommand()) return true;
        if (commandSender instanceof TileEntityCommandBlock && canCommandBlockUseCommand()) return true;
        if (commandSender instanceof EntityPlayer &&
                getPermissionNode() != null &&
                IHandler.permission != null &&
                !canUseWithoutPermission()) {
            Player player = new Player(commandSender);
            if (player != null) {
                return player.hasPermission(getPermissionNode());
            } else {
                return IHandler.permission.hasPermission((EntityPlayerMP) commandSender, ((EntityPlayerMP) commandSender).worldObj.getWorldInfo().getWorldName(), getPermissionNode());
            }

        }
        if (canUseWithoutPermission()) return true;
        return super.canCommandSenderUseCommand(commandSender);
    }

    @Override
    public final void processCommand(ICommandSender commandSender, String... argumentsArray) {
        if (!(commandSender instanceof EntityPlayer) && !canConsoleUseCommand())
            throw new WrongUsageException(getCommandUsage(commandSender));
        Player player = new Player(commandSender);
        processCommand(player, new ArrayList<String>(Arrays.asList(argumentsArray)));
    }

    public abstract void processCommand(Player player, List<String> args);

    public Player getPlayerFromString(String name) {
        return new Player(MinecraftServer.getServer().getConfigurationManager().func_152612_a(name));
    }

}
