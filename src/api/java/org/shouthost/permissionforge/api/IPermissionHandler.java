package org.shouthost.permissionforge.api;

import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;
import java.util.UUID;

/**
 * IPermissionHandler
 * <p/>
 * This class is responsable for performing check to see if the player has the permission
 */

//TODO: Finish implementation
public interface IPermissionHandler {

    /**
     * Returns true if player has permission
     *
     * @param player
     * @param permission
     * @return
     */

    public boolean hasPermission(EntityPlayerMP player, String permission);

    /**
     * Returns true if player has permission in world
     *
     * @param player
     * @param permission
     * @param world
     * @return
     */
    public boolean hasPermission(EntityPlayerMP player, String permission, String world);

    /**
     * Returns true is uuid has permission in world
     *
     * @param uuid
     * @param permission
     * @param world
     * @return
     */
    public boolean hasPermission(UUID uuid, String permission, String world);

    /**
     * @param player
     * @param permission
     */
    public void setPermission(EntityPlayerMP player, List<String> permission);

    /**
     * @param uuid
     * @param permission
     */
    public void setPermission(UUID uuid, List<String> permission);

    /**
     * @param player
     * @param permission
     * @param world
     */
    public void setPermission(EntityPlayerMP player, List<String> permission, String world);

    /**
     * @param uuid
     * @param permission
     * @param world
     */
    public void setPermission(UUID uuid, List<String> permission, String world);

    /**
     * @param group
     * @param permission
     */
    public void setPermission(String group, List<String> permission);


}
