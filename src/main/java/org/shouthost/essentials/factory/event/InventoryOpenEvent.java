package org.shouthost.essentials.factory.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.inventory.IInventory;
import org.shouthost.essentials.utils.config.Player;

@Cancelable
public class InventoryOpenEvent extends Event {
	public final Player player;
	public final IInventory inventory;

	public InventoryOpenEvent(Player player, IInventory inventory) {
		this.player = player;
		this.inventory = inventory;
	}
}
