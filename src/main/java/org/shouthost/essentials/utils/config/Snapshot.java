package org.shouthost.essentials.utils.config;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.shouthost.essentials.utils.compat.Location;

/*
 * Snapshot.java
 *
 * Snapshot class is responsable for creating a useable snapshot of blocks or tile entities, which could be used
 * for temp storage, repairing a corrupted block, clearing its inventory, and more.
 *
 * This is not to be used so easily by any mod since the main goal is to make a usable class for this mod only to use
 * to interact with tile entities.
 *
 * This may soon change so that it can support blocks, entities (creatures), possibly even item entites.
 */

public class Snapshot {
	public Worlds world;
	private Location location;
	private TileEntity tileEntity;
	private Block block;
	private int meta;
	private NBTTagCompound nbt = new NBTTagCompound();
	public Snapshot(TileEntity te){
		this(new Location(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord));
	}
	public Snapshot(Location loc){
		this(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	public Snapshot(World world, int x, int y, int z){
		this.world = new Worlds(world);
		this.location = new Location(world,x,y,z);
		this.tileEntity = this.world.getTileEntity(location);
		this.block = this.world.getBlock(location);
		this.meta = this.world.getBlockMetadata(location);
	}

	public void take(){
			try
			{
				NBTTagCompound compound = new NBTTagCompound();
				tileEntity.writeToNBT(compound);
				nbt = compound;
			}
			catch (Exception e)
			{
			}
	}

	public void restore(){
		world.setBlock(location, block, meta, 3);
		tileEntity = world.getTileEntity(location);
		if (tileEntity != null && nbt != null)
		{
			tileEntity.readFromNBT(nbt);
		}
	}

	public Location getLocation(){
		return location;
	}

	public void remove(){
		if(tileEntity != null)
			tileEntity.invalidate();
		world.setBlockToAir(location);
		world.removeTileEntity(location);
//
	}

	public String getNBT(){
		return nbt.toString();
	}

}
