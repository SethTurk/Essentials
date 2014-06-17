package org.shouthost.essentials.json.players;

public class Locations {
    private int world = 0;
    private double xPos;
    private double yPos;
    private double zPos;

    public int getWorld() {
        return world;
    }

    public void setWorld(int world) {
        this.world = world;
    }

    public double getPosX() {
        return xPos;
    }

    public void setPosX(double x) {
        this.xPos = x;
    }

    public double getPosY() {
        return yPos;
    }

    public void setPosY(double y) {
        this.yPos = y;
    }

    public double getPosZ() {
        return zPos;
    }

    public void setPosZ(double z) {
        this.zPos = z;
    }
}
