package org.shouthost.essentials.utils;

import net.minecraft.entity.player.EntityPlayer;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.entity.Player;

import java.util.UUID;

public class Utils {
    public static Player getPlayerFromUUID(UUID uuid) {
        if (Essentials.playerList.containsKey(uuid)) return Essentials.playerList.get(uuid);
        return null;
    }

    public static EntityPlayer getOfflinePlayer(UUID uuid) {

        return null;
    }
}
