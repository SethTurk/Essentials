package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

/**
 * Created by Darius on 5/20/2014.
 */
public class CommandKill extends ECommandBase {
    @Override
    public String getPermissionNode() {
        return "essentials.kill";
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
        return "kill";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/kill [<player>]";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, List<String> args) {
        if (args.isEmpty()) {
            if (!(iCommandSender instanceof EntityPlayer)) {
                iCommandSender.addChatMessage(new ChatComponentText("You can not kill yourself"));
                return;
            }
            Player player = new Player(iCommandSender);
            player.suicide();
        } else if (!args.isEmpty()) {
            Player player = new Player(args.get(0));
            if (player != null) {
                player.suicide();
            } else {
                iCommandSender.addChatMessage(new ChatComponentText("Player " + args.get(0) + " does not exist"));
            }
        }
    }
}
