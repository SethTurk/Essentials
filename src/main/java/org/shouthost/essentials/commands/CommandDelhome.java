package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.json.players.Homes;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

/**
 * Created by darius on 7/11/14.
 */
public class CommandDelhome extends ECommandBase {
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
    protected void processCommand(ICommandSender commandSender, List<String> args) {
        if(args.isEmpty()) throw new WrongUsageException(getCommandUsage(commandSender));
        Player player = new Player(commandSender);
        Homes home = player.getHome(args.get(0));
        if(home != null){
            player.delhome(home);
            if(player.getHome(args.get(0)) == null){
                player.sendMessage(EnumChatFormatting.GREEN+"Home '"+args.get(0)+"' has been deleted");
                return;
            }
        }else{
            player.sendMessage(EnumChatFormatting.RED+"'"+args.get(0)+"' is not a valid home entry");
            throw new WrongUsageException(getCommandUsage(commandSender));
        }
    }

    @Override
    public String getCommandName() {
        return "delhome";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return EnumChatFormatting.RED+"/delhome <home>";
    }
}
