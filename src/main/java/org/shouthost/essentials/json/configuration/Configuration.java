package org.shouthost.essentials.json.configuration;

public class Configuration {
	private String op_name_color;
	private String nickname_prefix;
	private int max_nick_length;
	private boolean change_disaplyname;
	private boolean teleport_safety;
	private int teleport_cooldown;

	public String getOp_name_color(){
		return op_name_color;
	}
}
