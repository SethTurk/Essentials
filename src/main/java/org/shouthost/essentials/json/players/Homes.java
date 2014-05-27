package org.shouthost.essentials.json.players;

public class Homes {
	private String name;
	private int world;
	private int x;
	private int y;
	private int z;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public int getX(){
		return x;
	}

	public void setWorld(int dim){
		this.world = dim;
	}

	public int getWorld(){
		return world;
	}

	public void setX(int x){
		this.x = x;
	}

	public int getY(){
		return y;
	}

	public void setY(int y){
		this.y = y;
	}

	public int getZ(){
		return z;
	}

	public void setZ(int z){
		this.z = z;
	}

}
