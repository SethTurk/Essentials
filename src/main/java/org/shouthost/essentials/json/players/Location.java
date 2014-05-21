package org.shouthost.essentials.json.players;

public class Location {
	private int xPos;
	private int yPos;
	private int zPos;

	public int getPosX(){
		return xPos;
	}

	public int getPosY() {
		return yPos;
	}

	public int getPosZ() {
		return zPos;
	}

	public void setPosX(int x){
		this.xPos = x;
	}

	public void setPosY(int y){
		this.yPos = y;
	}

	public void setPosZ(int z){
		this.zPos = z;
	}
}
