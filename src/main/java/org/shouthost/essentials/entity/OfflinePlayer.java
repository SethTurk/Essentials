package org.shouthost.essentials.entity;

import java.util.UUID;


//TODO: Finish implementing offline features for players that are not online
public class OfflinePlayer extends Player {
    public OfflinePlayer(UUID uuid) {
        super(uuid);
    }

    @Override
    public String getPlayerName() {
        return super.getPlayerName();
    }

    @Override
    public boolean isMuted() {
        return super.isMuted();
    }

    @Override
    public boolean isBanned() {
        return super.isBanned();
    }

    @Override
    public boolean isJailed() {
        return super.isJailed();
    }

    @Override
    public void ban(String reason, int timeout) {
        super.ban(reason, timeout);
    }

    @Override
    public void warn(String reason) {
        super.warn(reason);
    }

    @Override
    public void mute(String reason, int timeout) {
        super.mute(reason, timeout);
    }

    @Override
    public void updateBan(String reason, int timeout) {
        super.updateBan(reason, timeout);
    }

    @Override
    public void unban() {
        super.unban();
    }

    @Override
    public String getBannedReason() {
        return super.getBannedReason();
    }

    @Override
    public String getMuteReason() {
        return super.getMuteReason();
    }

    @Override
    public void save() {
        super.save();
    }
}
