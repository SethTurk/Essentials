package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.item.ItemStack;
import org.shouthost.essentials.utils.config.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandItem extends ECommandBase {
	@Override
	public List<String> getCommandAliases() {
		ArrayList<String> aliasList = new ArrayList<String>();
		aliasList.add("i");
		aliasList.add("item");
		return aliasList;
	}

	@Override
	public String getCommandName() {
		return "item";
	}

	@Override
	public String getCommandUsage(ICommandSender iCommandSender) {
		return "/item";
	}

	@Override
	public void processCommand(ICommandSender sender, List<String> args) {
		if (args.isEmpty()) throw new WrongUsageException(getCommandUsage(sender));
		Player player = new Player(sender);
		//TODO: rethink method for getItemByName()
		ItemStack item = null;
		if (args.size() == 1) {
			item = new ItemStack(getItemByText(sender, args.get(0)), 64, 64);
		} else if (args.size() == 2) {
			int stackSize = Integer.parseInt(args.get(1));
			System.out.println("ItemStack size = " + stackSize);
			//int i = parseIntBounded(sender, args.get(1), stackSize, stackSize);
			item = new ItemStack(getItemByText(sender, args.get(0)), stackSize, stackSize);
		}
		if (item == null) return;
		player.giveItem(item);
//		EntityPlayerMP player = (EntityPlayerMP) sender;
//		float f = 0.7F;
//		float d0 = player.worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
//		float d1 = player.worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
//		float d2 = player.worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
//		ItemStack stack = Book.CreateBook(Essentials.book.get(0));
//		EntityItem entityitem = new EntityItem(player.worldObj, player.posX + d0, player.posY + d1, player.posZ + d2, stack);
//		entityitem.delayBeforeCanPickup = 10;
//		if (stack.hasTagCompound()) {
//			entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
//		}
//		player.worldObj.spawnEntityInWorld(entityitem);
	}

	@Override
	public String getPermissionNode() {
		return "essentials.item";
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
	public boolean canUseWithoutPermission() {
		return false;
	}
}
