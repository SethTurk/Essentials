package org.shouthost.essentials.commands;

import net.minecraft.entity.player.InventoryPlayer;
import org.shouthost.essentials.entity.Player;
import org.shouthost.essentials.utils.KitTemplate;

import java.util.List;

public class ToolCommands extends CommandListener {
	@Commands(name = "setkit",
			permission = "essentials.command.setkit",
			disableInProduction = true)
	public static void setkit(Player player, List<String> args) {
		InventoryPlayer pl = player.getPlayer().inventory;
		KitTemplate kit = new KitTemplate(args.get(0), pl.mainInventory, null);
		kit.constructKitFromInventory();
	}

	@Commands(name = "kit",
			permission = "essentials.command.kit",
			disableInProduction = true)
	public static void kit(Player player, List<String> args) {
		KitTemplate kit = new KitTemplate(args.get(0));
		kit.reconstructKit();
		kit.giveKitToPlayer(player.getPlayer());
	}
}
