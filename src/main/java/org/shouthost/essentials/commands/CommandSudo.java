package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandSudo extends ECommandBase {
    @Override
    public String getPermissionNode() {
        return "essentials.sudo";
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
    public boolean canUseWithoutPermission() {
        return false;
    }

    @Override
    public String getCommandName() {
        return "sudo";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/sudo <player> <command>";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, List<String> args) {
        Player executor = new Player(iCommandSender);
        if (args.isEmpty()) throw new WrongUsageException(getCommandUsage(iCommandSender));
        if (!args.isEmpty()) {
            Player target = new Player(args.get(0));
            target.exec(args.get(1));
        }
    }
}
