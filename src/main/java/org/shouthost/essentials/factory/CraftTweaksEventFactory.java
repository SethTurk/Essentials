package org.shouthost.essentials.factory;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.common.MinecraftForge;
import org.shouthost.essentials.factory.event.EntityExplodeEvent;
import org.shouthost.essentials.factory.event.InventoryOpenEvent;
import org.shouthost.essentials.factory.event.LightningStrikeEvent;
import org.shouthost.essentials.utils.config.Player;

import java.util.Iterator;
import java.util.List;

public class CraftTweaksEventFactory {
	public CraftTweaksEventFactory() {
		new EntityEvent();
	}

	public static class InventoryEvent {
		public InventoryEvent() {
			FMLCommonHandler.instance().bus().register(this);
		}

		@SubscribeEvent
		public void InventoryCheck(TickEvent.PlayerTickEvent event) {
			Player player = new Player((net.minecraft.entity.player.EntityPlayerMP) event.player);
			IInventory inventory = event.player.inventory;
			InventoryOpenEvent ev = new InventoryOpenEvent(player, inventory);
			MinecraftForge.EVENT_BUS.post(ev);
			if (ev.isCanceled()) {
				inventory.closeInventory();
			}
		}
	}

	public static class PlayerEvent {
		public PlayerEvent() {
			FMLCommonHandler.instance().bus().register(this);
		}


	}

	public static class EntityEvent {

		public EntityEvent() {
			FMLCommonHandler.instance().bus().register(this);
			MinecraftForge.EVENT_BUS.register(this);
		}

		@SubscribeEvent
		public void onEntityExplodeTick(TickEvent.WorldTickEvent event) {
			List<Entity> list = event.world.loadedEntityList;
			Iterator<Entity> it = list.iterator();
			while (it.hasNext()) {
				Entity entity = it.next();
				if (entity instanceof EntityCreeper) {
					EntityCreeper creeper = (EntityCreeper) entity;
					int state = creeper.getCreeperState();
					if (state > 0 && creeper.isEntityAlive()) {
						EntityExplodeEvent ev = new EntityExplodeEvent(creeper);
						MinecraftForge.EVENT_BUS.post(ev);
						if (ev.isCanceled()) {
							creeper.setCreeperState(-1);
							return;
						} else {

						}
					}
				} else if (entity instanceof EntityWither) {
					EntityWither wither = (EntityWither) entity;

				} else if (entity instanceof EntityTNTPrimed) {
					EntityTNTPrimed tnt = (EntityTNTPrimed) entity;
					if (tnt.fuse == 1) {
						EntityExplodeEvent ev = new EntityExplodeEvent(tnt);
						MinecraftForge.EVENT_BUS.post(ev);
						if (ev.isCanceled()) {
							tnt.setDead();
							//new Worlds(tnt.worldObj).setBlockToAir(new Location(tnt.worldObj, tnt.posX, tnt.posY, tnt.posZ));
						}
					}
				} else if (entity instanceof EntityLightningBolt) {
					EntityLightningBolt lightning = (EntityLightningBolt) entity;
					LightningStrikeEvent ev = new LightningStrikeEvent(lightning.worldObj, (int) lightning.posX, (int) lightning.posY, (int) lightning.posZ);
					MinecraftForge.EVENT_BUS.post(ev);
					if (ev.isCanceled()) {
						lightning.setDead();
					}
				}
			}
		}
	}
}
