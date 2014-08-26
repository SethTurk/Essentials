package org.shouthost.essentials.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
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
    public boolean canCommandSenderUseCommand(ICommandSender commandSender) {
        if (!(commandSender instanceof EntityPlayer) && canConsoleUseCommand()) return true;
        if (commandSender instanceof TileEntityCommandBlock && canCommandBlockUseCommand()) return true;
        if (commandSender instanceof EntityPlayer &&
                getPermissionNode() != null &&
                IHandler.permission != null &&
                !canUseWithoutPermission()) {
            Player player = new Player(commandSender);
            return player.hasPermission(getPermissionNode());
        }
        return canUseWithoutPermission() || super.canCommandSenderUseCommand(commandSender);
    }

    @Override
    public final void processCommand(ICommandSender commandSender, String... argumentsArray) {
        if (!(commandSender instanceof EntityPlayer) && !canConsoleUseCommand())
            throw new WrongUsageException(getCommandUsage(commandSender));
        Player player = new Player(commandSender);
        processCommand(player, new ArrayList<>(Arrays.asList(argumentsArray)));
    }

    public abstract void processCommand(Player player, List<String> args);
}
