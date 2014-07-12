package org.shouthost.essentials.json.players;

public class Homes {
	private String name;
	private int world = 0;
	private int x;
	private int y;
	private int z;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getWorld() {
		return world;
	}

	public void setWorld(int dim) {
		this.world = dim;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

}
