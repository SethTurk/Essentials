package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.shouthost.essentials.utils.config.Player;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darius on 5/21/2014.
 */
public class CommandLag extends ECommandBase {
	private static long mean(long[] values) {

		long sum = 0l;
		for (long v : values) {
			sum += v;
		}

		return sum / values.length;
	}

	@Override
	public List<String> getCommandAliases() {
		ArrayList<String> aliasList = new ArrayList<String>();
		aliasList.add("gc");
		aliasList.add("tps");
		return aliasList;
	}

	@Override
	public String getCommandName() {
		return "lag";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return null;
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {
		Player player = new Player((net.minecraft.entity.player.EntityPlayerMP) iCommandSender);

		//Uptime
		Long uptime = ManagementFactory.getRuntimeMXBean().getStartTime();

		//Memory
		long maxmem = Runtime.getRuntime().totalMemory() / 1024L / 1024L;
		long freemem = Runtime.getRuntime().freeMemory() / 1024L / 1024L;
		long memuse = maxmem - freemem / 1024L / 1024L;

		player.sendMessage("Uptime: " + uptime);//Will implement a parser for uptime
		player.sendMessage("Maximum memory: " + maxmem + " MB.");
		player.sendMessage("Used Memory: " + memuse + " MB.");
		player.sendMessage("Free memory: " + freemem + " MB.");

		for (WorldServer world : MinecraftServer.getServer().worldServers) {
			String dim = world.provider.getDimensionName();
			int loadedChunksCount = world.theChunkProviderServer.getLoadedChunkCount();
			int loadedEntityCount = world.loadedEntityList.size();
			int loadedTileCount = world.loadedTileEntityList.size();
			player.sendMessage("World \"" + dim + "\": " + loadedChunksCount + " chunks, " + loadedEntityCount + " entities, " + loadedTileCount + " tiles.");
		}

	}

	@Override
	public String getPermissionNode() {
		return "essentials.lag";
	}

	@Override
	public boolean canConsoleUseCommand() {
		return false;
	}

	@Override
	public boolean canCommandBlockUseCommand() {
		return false;
	}

	@Override
	public boolean canUseWithoutPermission() {
		return false;
	}

	private double getWorldTPS(World world) {
		double worldTickTime = mean(MinecraftServer.getServer().worldTickTimes.get(world.provider.dimensionId)) * 1.0E-6D;
		return Math.min(1000.0 / worldTickTime, 20);
	}
}
