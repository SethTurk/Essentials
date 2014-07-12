package org.shouthost.essentials.factory.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;

@Cancelable
public class EntityExplodeEvent extends EntityEvent {
	public final World world;
	public final int x;
	public final int y;
	public final int z;
	public final String type;

	public EntityExplodeEvent(Entity entity) {
		super(entity);
		this.world = entity.worldObj;
		this.x = (int) entity.posX;
		this.y = (int) entity.posY;
		this.z = (int) entity.posZ;
		this.type = entity.getCommandSenderName();
	}
}
