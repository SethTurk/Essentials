package org.shouthost.essentials.commands;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Blocks;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.tasks.TaskTest;
import org.shouthost.essentials.utils.config.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandTest extends ECommandBase{
	@Override
	public String getPermissionNode() {
		return "essentials.test";
	}

	@Override
	public boolean canConsoleUseCommand() {
		return false;
	}

	@Override
	public boolean canCommandBlockUseCommand() {
		return false;
	}

	@Override
	protected void processCommand(ICommandSender commandSender, List<String> args) {
		ArrayList<Block> blocksToClear = new ArrayList<Block>();
		for (Object obj : GameData.getBlockRegistry()) {
			if (obj instanceof Block) {

				String blockName = ((Block) obj).getUnlocalizedName();
				if (blockName.startsWith("tile."))
					blockName = blockName.substring(5, blockName.length());

				if (blockName.equalsIgnoreCase(args.get(0)) && !(obj == Blocks.air)) {
					blocksToClear.add((Block) obj);
				}
			}
		}
		Essentials.tickHandler.registerTask(new TaskTest(new Player((net.minecraft.entity.player.EntityPlayerMP) commandSender), 40, blocksToClear));
	}

	@Override
	public String getCommandName() {
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return null;
	}
}
