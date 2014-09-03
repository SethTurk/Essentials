package org.shouthost.essentials.commands;

import org.shouthost.essentials.entity.Player;

import java.util.List;

public class CommonCommands extends CommandListener {
	@Commands(name = "ping",
			permission = "essentials.command.ping",
			description = "To test the server response time.")
	public static void ping(Player player, List<String> args) {
        player.sendSuccessMessage("pong");
    }

	@Commands(name = "suicide",
			permission = "essentials.command.suicide",
			description = "To kill yourself")
	public static void suicide(Player player, List<String> args) {
		player.suicide();
	}

	@Commands(name = "essentials",
			permission = "essentials.command.essentials",
			syntax = "[option]",
			description = "",
			alias = {"ess"},
			console = true)
	public static void essentials(Player player, List<String> args) {

	}
}
