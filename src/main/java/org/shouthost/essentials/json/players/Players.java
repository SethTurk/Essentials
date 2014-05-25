package org.shouthost.essentials.json.players;

import java.util.ArrayList;
import java.util.List;

public class Players {
	private String playername;
	private String uuid;
	private Location location = new Location();
	private List<Homes> homes = new ArrayList<Homes>();
	private int logout;
	private Optional optional = new Optional();

	public String getPlayerName(){
		return playername;
	}

	public void setPlayername(String playername){
		this.playername = playername;
	}

	public String getUuid(){
		return uuid;
	}

	public void setUuid(String uuid){
		this.uuid = uuid;
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
	public double getPosX(){
		return location.getPosX();
	}

	public double getPosY() {
		return location.getPosY();
	}

	public double getPosZ() {
		return location.getPosZ();
	}

	public void setPosX(double x){
		location.setPosX(x);
	}

	public void setPosY(double y){
		location.setPosY(y);
	}

	public void setPosZ(double z){
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

	public List<String> getWarning(){ return optional.getWarnings(); }

	public boolean getJailed(){ return optional.getJailed(); }

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

	public void setWarning(String reason){ optional.setWarnings(reason); }

	public void setJailed(boolean jailed){ optional.setJailed(jailed); }

}
