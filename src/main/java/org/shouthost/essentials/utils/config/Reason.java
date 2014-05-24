package org.shouthost.essentials.utils.config;

import java.util.UUID;

public class Reason {
	private UUID uuid;
	private String reason;
	private int timeout;

	public Reason(UUID uuid, String reason, int timeout){
		this.uuid = uuid;
		this.reason = reason;
		this.timeout = timeout;
	}

	public void updateReason(String reason){
		this.reason = reason;
	}

	public void updateTimeout(int timeout){
		this.timeout = timeout;
	}

	public String getReason(){return this.reason;}
}
