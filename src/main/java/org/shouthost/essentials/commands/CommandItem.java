package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.utils.config.Book;

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
		EntityPlayerMP player = (EntityPlayerMP) sender;
		float f = 0.7F;
		float d0 = player.worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
		float d1 = player.worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
		float d2 = player.worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
		ItemStack stack = Book.CreateBook(Essentials.book.get(0));
		EntityItem entityitem = new EntityItem(player.worldObj, player.posX + d0, player.posY + d1, player.posZ + d2, stack);
		entityitem.delayBeforeCanPickup = 10;
		if (stack.hasTagCompound()) {
			entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
		}
		player.worldObj.spawnEntityInWorld(entityitem);
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
