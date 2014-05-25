package org.shouthost.essentials.json.players;

public class Location {
	private double xPos;
	private double yPos;
	private double zPos;

	public double getPosX(){
		return xPos;
	}

	public double getPosY() {
		return yPos;
	}

	public double getPosZ() {
		return zPos;
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
