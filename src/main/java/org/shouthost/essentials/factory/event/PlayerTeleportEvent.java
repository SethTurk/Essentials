package org.shouthost.essentials.factory.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import org.shouthost.essentials.utils.compat.Location;
import org.shouthost.essentials.utils.config.Player;

@Cancelable
public class PlayerTeleportEvent extends Event {
	public final Player player;
	public final Location from;
	public final Location to;

	public PlayerTeleportEvent(Player player, Location from, Location to) {
		this.player = player;
		this.from = from;
		this.to = to;
	}
}
