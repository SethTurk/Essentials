package org.shouthost.essentials.utils.compat;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class Location {
	private World world;
	private double x;
	private double y;
	private double z;
	private float pitch;
	private float yaw;

	public Location(final World world, final double x, final double y, final double z) {
		this(world, x, y, z, 0, 0);
	}


	public Location(final World world, final double x, final double y, final double z, final float yaw, final float pitch) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public static int locToBlock(double loc) {
		return NumberConversions.floor(loc);
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public int getWorldID() {
		return world.provider.dimensionId;
	}

	public Chunk getChunk() {
		return getWorld().getChunkFromBlockCoords((int) x, (int) z); //Implement a getChunkAt
	}

	public Block getBlock() {
		return getWorld().getBlock((int) x, (int) y, (int) z); // implement a getBlockAt
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getBlockX() {
		return locToBlock(x);
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getBlockY() {
		return locToBlock(y);
	}

	public double getZ() {
		return z;
	}

	public void setZ(double Z) {
		this.z = z;
	}

	public int getBlockZ() {
		return locToBlock(z);
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public Vector getDirection() {
		Vector vector = new Vector();

		double rotX = this.getYaw();
		double rotY = this.getPitch();

		vector.setY(-Math.sin(Math.toRadians(rotY)));

		double xz = Math.cos(Math.toRadians(rotY));

		vector.setX(-xz * Math.sin(Math.toRadians(rotX)));
		vector.setZ(xz * Math.cos(Math.toRadians(rotX)));

		return vector;
	}

	public Location setDirection(Vector vector) {

		final double _2PI = 2 * Math.PI;
		final double x = vector.getX();
		final double z = vector.getZ();

		if (x == 0 && z == 0) {
			pitch = vector.getY() > 0 ? -90 : 90;
			return this;
		}

		double theta = Math.atan2(-x, z);
		yaw = (float) Math.toDegrees((theta + _2PI) % _2PI);

		double x2 = NumberConversions.square(x);
		double z2 = NumberConversions.square(z);
		double xz = Math.sqrt(x2 + z2);
		pitch = (float) Math.toDegrees(Math.atan(-vector.getY() / xz));

		return this;
	}

	public Location add(Location vec) {
		if (vec == null || vec.getWorld() != getWorld()) {
			throw new IllegalArgumentException("Cannot add Locations of differing worlds");
		}

		x += vec.x;
		y += vec.y;
		z += vec.z;
		return this;
	}

	public Location subtract(Vector vec) {
		this.x -= vec.getX();
		this.y -= vec.getY();
		this.z -= vec.getZ();
		return this;
	}

	public Location subtract(double x, double y, double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public double length() {
		return Math.sqrt(NumberConversions.square(x) + NumberConversions.square(y) + NumberConversions.square(z));
	}

	public double lengthSquared() {
		return NumberConversions.square(x) + NumberConversions.square(y) + NumberConversions.square(z);
	}

	public double distance(Location o) {
		return Math.sqrt(distanceSquared(o));
	}

	public double distanceSquared(Location o) {
		if (o == null) {
			throw new IllegalArgumentException("Cannot measure distance to a null location");
		} else if (o.getWorld() == null || getWorld() == null) {
			throw new IllegalArgumentException("Cannot measure distance to a null world");
		} else if (o.getWorld() != getWorld()) {
			throw new IllegalArgumentException("Cannot measure distance between " + getWorld().getWorldInfo().getWorldName() + " and " + o.getWorld().getWorldInfo().getWorldName());
		}

		return NumberConversions.square(x - o.x) + NumberConversions.square(y - o.y) + NumberConversions.square(z - o.z);
	}

	public Location multiply(double m) {
		x *= m;
		y *= m;
		z *= m;
		return this;
	}

	public Location zero() {
		x = 0;
		y = 0;
		z = 0;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Location other = (Location) obj;

		if (this.world != other.world && (this.world == null || !this.world.equals(other.world))) {
			return false;
		}
		if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
			return false;
		}
		if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
			return false;
		}
		if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
			return false;
		}
		if (Float.floatToIntBits(this.pitch) != Float.floatToIntBits(other.pitch)) {
			return false;
		}
		if (Float.floatToIntBits(this.yaw) != Float.floatToIntBits(other.yaw)) {
			return false;
		}
		return true;
	}

	public Vector toVector() {
		return new Vector(x, y, z);
	}

}
