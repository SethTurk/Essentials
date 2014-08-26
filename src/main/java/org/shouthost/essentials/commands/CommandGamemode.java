package org.shouthost.essentials.commands;

import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSettings;
import org.shouthost.essentials.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandGamemode extends Command {
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
    public String getCommandUsage(Player player) {
        return EnumChatFormatting.RED + "/gamemode [<player>] <mode>";
    }

    @Override
    public void processCommand(Player player, List<String> args) {
        if (args.isEmpty()) throw new WrongUsageException(getCommandUsage(player));
        if (args.size() == 1 && !(player.getPlayer() instanceof EntityPlayerMP)) {
            player.sendMessage(EnumChatFormatting.RED + "You can only change the mode of a player");
            return;
        } else if (args.size() == 1 && player.getPlayer() instanceof EntityPlayerMP) {
            String mode = args.get(0);
            if (mode.equalsIgnoreCase("s") || mode.equalsIgnoreCase("survival") || mode.equalsIgnoreCase("0")) {
                player.setGameMode(WorldSettings.GameType.SURVIVAL);
                player.sendMessage(EnumChatFormatting.GREEN + "");
                return;
            } else if (mode.equalsIgnoreCase("c") || mode.equalsIgnoreCase("creative") || mode.equalsIgnoreCase("1")) {
                player.setGameMode(WorldSettings.GameType.CREATIVE);
                return;
            } else if (mode.equalsIgnoreCase("a") || mode.equalsIgnoreCase("adventure") || mode.equalsIgnoreCase("2")) {
                player.setGameMode(WorldSettings.GameType.ADVENTURE);
                return;
            }
        }
    }

    @Override
    public String getPermissionNode() {
        return "essentials.command.gamemode";
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
