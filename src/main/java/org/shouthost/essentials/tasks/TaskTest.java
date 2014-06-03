package org.shouthost.essentials.tasks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import org.shouthost.essentials.api.IThread;
import org.shouthost.essentials.api.ITick;
import org.shouthost.essentials.utils.compat.Location;
import org.shouthost.essentials.utils.config.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TaskTest implements ITick {

	private static final int BLOCKS_PER_TICK = 25000;
	private static final int LAG_THREASHOLD = 1500;

	private boolean isComplete;
	private final Player player;
	private Collection<Location> blocksToRemove;
	private final World world;
	private int blockCounter;

	public TaskTest(Player player, int radius, Collection<Block> blocksToClear) {

		this.player = player;
		world = player.getWorld();

		if (world == null) {
			isComplete = true;
			return;
		}

		int centerX = (int) player.getPlayer().posX;
		int centerY = (int) player.getPlayer().posY;
		int centerZ = (int) player.getPlayer().posZ;

		blocksToRemove = new ArrayList<Location>();

		for (int x = centerX - radius; x < centerX + radius; x++) {
			for (int y = centerY - radius; y < centerY + radius; y++) {
				for (int z = centerZ - radius; z < centerZ + radius; z++)
					if (blocksToClear.contains(world.getBlock(x, y, z))) {
						blocksToRemove.add(new Location(world,x, y, z));
						blockCounter++;
					}
			}
		}

		player.sendMessage(String.format("Removing %s blocks", blockCounter));

		if (blockCounter > LAG_THREASHOLD)
			player.sendMessage("Removing a lot of blocks, Incomming lag");
	}

	@Override
	public void onTaskTick() {
		Iterator<Location> iterator = blocksToRemove.iterator();

		for (int i = 0; i < BLOCKS_PER_TICK; i++) {

			if (iterator.hasNext()) {
				Location block = iterator.next();
				world.setBlockToAir(block.getBlockX(), block.getBlockY(), block.getBlockZ());
				iterator.remove();
			}
		}

		if (blocksToRemove.isEmpty()) {
			isComplete = true;
		}
	}

	@Override
	public void onTaskComplete() {
		player.sendMessage("Finished removing blocks");
	}

	@Override
	public boolean isTaskCompleted() {
		return isComplete;
	}

	private class TempBlock {
		final int x;
		final int y;
		final int z;

		TempBlock(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

}
