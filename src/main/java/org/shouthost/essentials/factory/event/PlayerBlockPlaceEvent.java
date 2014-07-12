package org.shouthost.essentials.factory.event;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerBlockPlaceEvent extends Event {
	public final EntityPlayer player;
	public final int x;
	public final int y;
	public final int z;


	public PlayerBlockPlaceEvent(EntityPlayer player, int x, int y, int z) {
		this.player = player;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
