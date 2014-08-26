package org.shouthost.permissionforge.api;

import net.minecraft.entity.player.EntityPlayerMP;

import java.util.UUID;

/**
 * IChatHandler
 * <p/>
 * Class is responible for handling prefix, suffix, and several options within
 * PermissionEx
 */

//TODO: Finish implementation of the api
public interface IChatHandler {

    public String getUsernameFromUUID(UUID uuid);

    public UUID getUUIDFromUsername(String name);

    public String getPrefix(EntityPlayerMP player, String world);

    public String getSuffix(EntityPlayerMP player, String world);

    public String getPrefix(UUID uuid, String world);

    public String getSuffix(UUID uuid, String world);

}
