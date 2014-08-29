package org.shouthost.essentials.entity;

import java.util.UUID;


//TODO: Finish implementing offline features for players that are not online
public class OfflinePlayer extends Player {
    public OfflinePlayer(UUID uuid) {
        super(uuid);
    }
}
