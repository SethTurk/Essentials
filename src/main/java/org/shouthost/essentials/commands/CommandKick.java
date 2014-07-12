package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.utils.config.Player;

import java.util.List;

public class CommandKick extends ECommandBase {
	@Override
	public String getPermissionNode() {
		return "essentials.kick";
	}

	@Override
	public boolean canConsoleUseCommand() {
		return true;
	}

	@Override
	public boolean canCommandBlockUseCommand() {
		return false;
	}

	@Override
	public String getCommandName() {
		return "kick";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/kick <player> [reason]";
	}

	@Override
	public void processCommand(ICommandSender iCommandSender, List<String> args) {
		if (args.size() <= 0) throw new WrongUsageException(getCommandUsage(iCommandSender));
		EntityPlayerMP target = MinecraftServer.getServer().getConfigurationManager().func_152612_a(args.get(0));
		Player player = new Player(target);
		if (target == null || player == null) {
			iCommandSender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Player " + args.get(0) + " does not exist on the server."));
			return;
		}

		player.kick(args.get(1));
	}
}
