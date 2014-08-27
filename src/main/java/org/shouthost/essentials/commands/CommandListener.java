package org.shouthost.essentials.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.entity.Player;

public class CommandListener {
    public static Player getPlayerFromString(String name) {
        EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);
        if (player == null) return null;
        if (Essentials.playerList.getIfPresent(player.getPersistentID()) != null)
            return Essentials.playerList.getIfPresent(player.getPersistentID());
        else
            return new Player(player);
    }
}
