package org.shouthost.essentials.json.configuration;

import net.minecraft.util.EnumChatFormatting;

@Deprecated//TODO: Port over to new backend system
public class Configuration {
    private String OpNameColor = EnumChatFormatting.RED.toString();
    private String NicknamePrefix = "~";
	private int MaxNickLength;
	private boolean ChangeDisaplyname;
	private boolean TeleportSafety;
	private int TeleportCooldown;
	private int DefaultBalance;

	public int getDefaultBalance() {
		return DefaultBalance;
	}

	public void setDefaultBalance(int defaultBalance) {
		DefaultBalance = defaultBalance;
	}

	public String getOpNameColor() {
		return OpNameColor;
	}

	public void setOpNameColor(String opNameColor) {
		OpNameColor = opNameColor;
	}

	public String getNicknamePrefix() {
		return NicknamePrefix;
	}

	public void setNicknamePrefix(String nicknamePrefix) {
		NicknamePrefix = nicknamePrefix;
	}

	public int getMaxNickLength() {
		return MaxNickLength;
	}

	public void setMaxNickLength(int maxNickLength) {
		MaxNickLength = maxNickLength;
	}

	public boolean isChangeDisaplyname() {
		return ChangeDisaplyname;
	}

	public void setChangeDisaplyname(boolean changeDisaplyname) {
		ChangeDisaplyname = changeDisaplyname;
	}

	public boolean isTeleportSafety() {
		return TeleportSafety;
	}

	public void setTeleportSafety(boolean teleportSafety) {
		TeleportSafety = teleportSafety;
	}

	public int getTeleportCooldown() {
		return TeleportCooldown;
	}

	public void setTeleportCooldown(int teleportCooldown) {
		TeleportCooldown = teleportCooldown;
	}
}
