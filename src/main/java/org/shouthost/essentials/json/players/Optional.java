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

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

	public String getMuteReason() {
		return muteReason;
	}

	public void setMuteReason(String reason) {
		this.muteReason = reason;
	}

	public int getMuteTimeout() {
		return muteTimeout;
	}

	public void setMuteTimeout(int timeout) {
		this.muteTimeout = timeout;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public String getBanReason() {
		return banReason;
	}

	public void setBanReason(String reason) {
		this.banReason = reason;
	}

	public int getBanTimeout() {
		return banTimeout;
	}

	public void setBanTimeout(int timeout) {
		this.banTimeout = timeout;
	}

	public boolean getJailed() {
		return jailed;
	}

	public void setJailed(boolean jailed) {
		this.jailed = jailed;
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(String reason) {
		this.warnings.add(reason);
	}

}
