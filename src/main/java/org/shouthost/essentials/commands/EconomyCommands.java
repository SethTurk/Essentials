package org.shouthost.essentials.commands;

import org.shouthost.essentials.entity.Player;

import java.util.List;

public class EconomyCommands extends CommandListener {

    @Commands(name = "economy",
            permission = "essentials.commands.economy",
            alias = {"eco"},
            disableInProduction = true)
    public static void economy(Player player, List<String> args) {

    }

	@Commands(name = "setbalance",
	          permission = "essentials.commands.setbalance",
			  disableInProduction = true)
    @Deprecated
    public static void setbalance(Player player, List<String> args) {
		if(args.isEmpty()) {
			player.sendErrorMessage("Needs more arguements.");
		}else if(args.size() == 1){
			int bal = Integer.parseInt(args.get(0));
			if(bal >= 0){
				player.setBalance(bal);
			}
		}else if (args.size() == 2){
			Player target = getPlayerFromString(args.get(0));
			if(target != null){
				int bal = Integer.parseInt(args.get(1));
				if(bal >= 0){
					target.setBalance(bal);
				}
			}else {
				player.sendErrorMessage("Invalid player name");
			}
		}
	}

	@Commands(name = "resetbalance",
			  permission = "essentials.commands.resetbalance",
			  disableInProduction = true)
    @Deprecated
    public void resetbalance(Player player) {
        //TODO: Add additional codes
        player.setBalance(0);//TODO: Add configuration default balance
	}
}
