package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSettings;
import org.shouthost.essentials.utils.config.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darius on 5/20/2014.
 */
public class CommandGamemode extends ECommandBase {
    @Override
    public List getCommandAliases() {
        ArrayList cmd = new ArrayList();
        cmd.add("gm");
        return cmd;
    }

    @Override
    public String getCommandName() {
        return "gamemode";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return EnumChatFormatting.RED+"/gamemode [<player>] <mode>";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, List<String> args) {
        if(args.isEmpty()) throw new WrongUsageException(getCommandUsage(iCommandSender));
        if(args.size() == 1 && !(iCommandSender instanceof EntityPlayerMP)){
            iCommandSender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"You can only change the mode of a player"));
            return;
        }else if(args.size() == 1 && iCommandSender instanceof EntityPlayerMP){
            Player player = new Player(iCommandSender);
            String mode = args.get(0);
            if(mode.equalsIgnoreCase("s") || mode.equalsIgnoreCase("survival") || mode.equalsIgnoreCase("0")){
                player.setGameMode(WorldSettings.GameType.SURVIVAL);
                player.sendMessage(EnumChatFormatting.GREEN+"");
                return;
            }else if (mode.equalsIgnoreCase("c") || mode.equalsIgnoreCase("creative") || mode.equalsIgnoreCase("1")){
                player.setGameMode(WorldSettings.GameType.CREATIVE);
                return;
            }else if (mode.equalsIgnoreCase("a") || mode.equalsIgnoreCase("adventure") || mode.equalsIgnoreCase("2")){
                player.setGameMode(WorldSettings.GameType.ADVENTURE);
                return;
            }
        }
    }

    @Override
    public String getPermissionNode() {
        return "minecraft.command.gamemode";
    }

    @Override
    public boolean canConsoleUseCommand() {
        return false;
    }

    @Override
    public boolean canCommandBlockUseCommand() {
        return false;
    }
}
