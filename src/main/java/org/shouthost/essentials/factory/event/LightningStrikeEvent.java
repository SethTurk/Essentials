package org.shouthost.essentials.factory.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.world.World;

@Cancelable
public class LightningStrikeEvent extends Event {
	public final World world;
	public final int x;
	public final int y;
	public final int z;

	public LightningStrikeEvent(World world, int x, int y, int z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
