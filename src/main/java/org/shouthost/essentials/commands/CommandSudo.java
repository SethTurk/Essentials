package org.shouthost.essentials.commands;

import net.minecraft.command.WrongUsageException;
import org.shouthost.essentials.entity.Player;

import java.util.List;

public class CommandSudo extends Command {
    @Override
    public String getPermissionNode() {
        return "essentials.command.sudo";
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
    public String getCommandUsage(Player player) {
        return "/sudo <player> <command>";
    }

    @Override
    public void processCommand(Player player, List<String> args) {
        if (args.isEmpty() || args.size() < 2) throw new WrongUsageException(getCommandUsage(player));
        if (!args.isEmpty()) {
            Player t = (Player) getPlayerFromString(args.get(0));
            if (t == null) throw new WrongUsageException(getCommandUsage(player));
            //Player target = new Player(t);
            //TODO add arguments with the commands
            String cPa = "";
            for (int i = 1; i < args.size(); i++)
                cPa += args.get(i) + " ";
            t.exec(cPa);
        }
    }
}
