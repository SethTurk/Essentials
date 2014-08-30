package org.shouthost.essentials.utils;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import java.util.List;

public class ServerUtils {

	public static void broadcastToWorld(World world, String message) {
		List<EntityPlayerMP> list = world.playerEntities;
		for (EntityPlayerMP player : list) {

		}
	}

	public static void broadcastToAll(String message) {

	}

	public static void broadcastToAdmins(String message) {

	}
}
