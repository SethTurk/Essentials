package org.shouthost.essentials.json.players;

import java.util.List;

public class Players {
	private String playername;
	private List<Homes> homes;
	private int logout;
	private Location location;
	private Optional optional;

	public String getPlayerName(){
		return playername;
	}

	public void setPlayername(String playername){
		this.playername = playername;
	}

	public List<Homes> getHomes(){
		return homes;
	}

	public void setHome(Homes home){
		homes.add(home);
	}

	public int getLogout(){
		return logout;
	}
	public void setLogout(int logout){
		this.logout = logout;
	}


	//Location
	public int getPosX(){
		return location.getPosX();
	}

	public int getPosY() {
		return location.getPosY();
	}

	public int getPosZ() {
		return location.getPosZ();
	}

	public void setPosX(int x){
		location.setPosX(x);
	}

	public void setPosY(int y){
		location.setPosY(y);
	}

	public void setPosZ(int z){
		location.setPosZ(z);
	}

	//optional
	public boolean isMuted(){
		return optional.isMuted();
	}

	public String getMuteReason(){
		return optional.getMuteReason();
	}

	public int getMuteTimeout(){
		return optional.getMuteTimeout();
	}

	public boolean isBanned(){
		return optional.isBanned();
	}

	public String getBanReason(){
		return optional.getBanReason();
	}

	public int getBanTimeout(){
		return optional.getBanTimeout();
	}

	public void setMuted(boolean muted){
		optional.setMuted(muted);
	}

	public void setMuteReason(String reason){
		optional.setMuteReason(reason);
	}

	public void setMuteTimeout(int timeout){
		optional.setMuteTimeout(timeout);
	}

	public void setBanned(boolean banned){
		optional.setBanned(banned);
	}

	public void setBanReason(String reason){
		optional.setBanReason(reason);
	}

	public void setBanTimeout(int timeout){
		optional.setBanTimeout(timeout);
	}


}
