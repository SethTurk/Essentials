package org.shouthost.essentials.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.json.warps.Warps;
import org.shouthost.essentials.utils.compat.Location;

import java.io.*;

public class Warp {
	//TODO: Rethink on using this class for warps or not
	public final String name;
	public final Location loc;

	public Warp(Warps warp) {
		this(warp.getName(), MinecraftServer.getServer().worldServerForDimension(warp.getWorld()), warp.getX(), warp.getY(), warp.getZ(), warp.getYaw(), warp.getPitch());
	}

	public Warp(String name, Location location) {
		this(name, location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}

	public Warp(String name, World world, double x, double y, double z) {
		this(name, world, x, y, z, 0, 0);
	}

	public Warp(String name, World world, double x, double y, double z, float yaw, float pitch) {
		this.name = name;
		this.loc = new Location(world, x, y, z, yaw, pitch);
		if (!Essentials.warpList.contains(this))
			Essentials.warpList.add(this);
	}

	public void save() {
		final Warps warp = new Warps();
		warp.setName(name);
		warp.setWorld(loc.getWorldID());
		warp.setX(loc.getX());
		warp.setY(loc.getY());
		warp.setZ(loc.getZ());
		warp.setYaw(loc.getYaw());
		warp.setPitch(loc.getPitch());


		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(warp);
		File file = new File(Essentials.warps, warp.getName() + ".json");
		if (file.exists() && file.isFile()) file.delete();
		try {
			Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
