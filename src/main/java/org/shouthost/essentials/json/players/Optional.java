package org.shouthost.essentials.json.players;

import java.util.List;

public class Optional {
	private boolean muted;
	private String muteReason;
	private int muteTimeout;
	private boolean banned;
	private String banReason;
	private int banTimeout;
	private List<String> warnings;
	private boolean jailed;

	public boolean isMuted(){
		return muted;
	}

	public String getMuteReason(){
		return muteReason;
	}

	public int getMuteTimeout(){
		return muteTimeout;
	}

	public boolean isBanned(){
		return banned;
	}

	public String getBanReason(){
		return banReason;
	}

	public int getBanTimeout(){
		return banTimeout;
	}

	public boolean getJailed(){ return jailed; }

	public void setMuted(boolean muted){
		this.muted = muted;
	}

	public void setMuteReason(String reason){
		this.muteReason = reason;
	}

	public void setMuteTimeout(int timeout){
		this.muteTimeout = timeout;
	}

	public void setBanned(boolean banned){
		this.banned = banned;
	}

	public void setBanReason(String reason){
		this.banReason = reason;
	}

	public void setBanTimeout(int timeout){
		this.banTimeout = timeout;
	}

	public List<String> getWarnings(){
		return warnings;
	}

	public void setWarnings(String reason){
		this.warnings.add(reason);
	}

	public void setJailed(boolean jailed) { this.jailed = jailed; }

}
