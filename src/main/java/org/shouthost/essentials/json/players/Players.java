package org.shouthost.essentials.json.players;

import java.util.ArrayList;
import java.util.List;

public class Players {
	private String playername;
	private String uuid;
	private Locations location = new Locations();
	private List<Homes> homes = new ArrayList<Homes>();
	private int logout;
	private Optional optional = new Optional();

	public String getPlayerName() {
		return playername;
	}

	public void setPlayername(String playername) {
		this.playername = playername;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Homes> getHomes() {
		return homes;
	}

	public void setHome(Homes home) {
		homes.add(home);
	}

	public int getLogout() {
		return logout;
	}

	public void setLogout(int logout) {
		this.logout = logout;
	}


	//Locations
	public int getWorld() {
		return location.getWorld();
	}

	public void setWorld(int world) {
		location.setWorld(world);
	}

	public double getPosX() {
		return location.getPosX();
	}

	public void setPosX(double x) {
		location.setPosX(x);
	}

	public double getPosY() {
		return location.getPosY();
	}

	public void setPosY(double y) {
		location.setPosY(y);
	}

	public double getPosZ() {
		return location.getPosZ();
	}

	public void setPosZ(double z) {
		location.setPosZ(z);
	}

	//optional
	public boolean isMuted() {
		return optional.isMuted();
	}

	public void setMuted(boolean muted) {
		optional.setMuted(muted);
	}

	public String getMuteReason() {
		return optional.getMuteReason();
	}

	public void setMuteReason(String reason) {
		optional.setMuteReason(reason);
	}

	public int getMuteTimeout() {
		return optional.getMuteTimeout();
	}

	public void setMuteTimeout(int timeout) {
		optional.setMuteTimeout(timeout);
	}

	public boolean isBanned() {
		return optional.isBanned();
	}

	public void setBanned(boolean banned) {
		optional.setBanned(banned);
	}

	public String getBanReason() {
		return optional.getBanReason();
	}

	public void setBanReason(String reason) {
		optional.setBanReason(reason);
	}

	public int getBanTimeout() {
		return optional.getBanTimeout();
	}

	public void setBanTimeout(int timeout) {
		optional.setBanTimeout(timeout);
	}

	public List<String> getWarning() {
		return optional.getWarnings();
	}

	public void setWarning(String reason) {
		optional.setWarnings(reason);
	}

	public boolean getJailed() {
		return optional.getJailed();
	}

	public void setJailed(boolean jailed) {
		optional.setJailed(jailed);
	}

}
