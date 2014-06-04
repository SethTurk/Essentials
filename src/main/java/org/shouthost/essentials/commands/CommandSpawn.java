package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandSpawn extends ECommandBase {
    @Override
    public String getPermissionNode() {
        return "essentials.spawn";
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
    protected void processCommand(ICommandSender commandSender, List<String> args) {
        if (args.isEmpty()) {
            if (!(commandSender instanceof EntityPlayerMP)) {
                commandSender.addChatMessage(new ChatComponentText("You can perform this action"));
                throw new WrongUsageException(getCommandUsage(commandSender));
            }
            Player player = new Player(commandSender);

        }
    }

    @Override
    public String getCommandName() {
        return "spawn";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/spawn [<player>]";
    }
}
