package org.shouthost.essentials.commands;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import org.shouthost.essentials.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandVanish extends Command {
    private ArrayList<UUID> vanishList = new ArrayList<UUID>();

    @Override
    public List<String> getCommandAliases() {
        ArrayList<String> aliasList = new ArrayList<String>();
        aliasList.add("v");
        return aliasList;
    }

    @Override
    public String getPermissionNode() {
        return "essentials.command.vanish";
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
    public String getCommandName() {
        return "vanish";
    }

    @Override
    public String getCommandUsage(Player player) {
        return null;
    }

    //TODO: Use packet system
    @Override
    public void processCommand(Player player, List<String> args) {
        if (vanishList.contains(player.getPlayer().getUniqueID())) {
            vanishList.remove(player.getPlayer().getUniqueID());
            MinecraftServer.getServer().getConfigurationManager().playerEntityList.add(player);
            player.getPlayer().removePotionEffect(Potion.invisibility.id);
        } else {
            vanishList.add(player.getPlayer().getUniqueID());
            MinecraftServer.getServer().getConfigurationManager().playerEntityList.remove(player);
            player.getPlayer().addPotionEffect(new PotionEffect(Potion.invisibility.id, 90000, 90000));
        }

    }
}
