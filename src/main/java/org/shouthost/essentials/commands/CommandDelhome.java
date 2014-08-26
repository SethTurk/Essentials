package org.shouthost.essentials.commands;

import net.minecraft.command.WrongUsageException;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.entity.Player;
import org.shouthost.essentials.json.players.Homes;

import java.util.List;

public class CommandDelhome extends Command {
    @Override
    public String getPermissionNode() {
        return "essentials.command.home.delete";
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
        Homes home = player.getHome(args.get(0));
        if (home != null) {
            player.delhome(home);
            if (home == null && player.getHome(args.get(0)) == null) {
                player.sendMessage(EnumChatFormatting.GREEN + "Home '" + args.get(0) + "' has been deleted");
                return;
            }
        } else {
            player.sendMessage(EnumChatFormatting.RED + "'" + args.get(0) + "' is not a valid home entry");
            throw new WrongUsageException(getCommandUsage(player));
        }
    }

    @Override
    public String getCommandName() {
        return "delhome";
    }

    @Override
    public String getCommandUsage(Player player) {
        return EnumChatFormatting.RED + "/delhome <home>";
    }
}
