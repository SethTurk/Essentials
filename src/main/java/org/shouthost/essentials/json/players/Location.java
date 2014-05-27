package org.shouthost.essentials.json.players;

public class Location {
	private int world;
	private double xPos;
	private double yPos;
	private double zPos;

	public int getWorld(){
		return world;
	}

	public double getPosX(){
		return xPos;
	}

	public double getPosY() {
		return yPos;
	}

	public double getPosZ() {
		return zPos;
	}

	public void setWorld(int world){
		this.world = world;
	}

	public void setPosX(double x){
		this.xPos = x;
	}

	public void setPosY(double y){
		this.yPos = y;
	}

	public void setPosZ(double z){
		this.zPos = z;
	}
}
