package org.shouthost.essentials.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSettings;
import org.shouthost.essentials.entity.Player;

import java.util.List;

public class ItemCommands extends CommandListener {

    @Commands(name = "give",
            permission = "essentials.command.give",
            syntax = "[name|mode] [mode]",
            description = "")
    public static void give(Player player, List<String> args) {
        if (args.size() == 1 && !(player.getPlayer() instanceof EntityPlayerMP)) {
            player.sendMessage(EnumChatFormatting.RED + "You can only change the mode of a player");
        } else if (args.size() == 1) {
            String mode = args.get(0);
            if (mode.equalsIgnoreCase("s") || mode.equalsIgnoreCase("survival") || mode.equalsIgnoreCase("0")) {
                player.setGameMode(WorldSettings.GameType.SURVIVAL);
                player.sendMessage(EnumChatFormatting.GREEN + "");
            } else if (mode.equalsIgnoreCase("c") || mode.equalsIgnoreCase("creative") || mode.equalsIgnoreCase("1")) {
                player.setGameMode(WorldSettings.GameType.CREATIVE);
            } else if (mode.equalsIgnoreCase("a") || mode.equalsIgnoreCase("adventure") || mode.equalsIgnoreCase("2")) {
                player.setGameMode(WorldSettings.GameType.ADVENTURE);
            }
        }
    }

    @Commands(name = "item",
            permission = "essentials.command.item",
            alias = {"i"},
            description = "")
    public static void item(Player player, List<String> args) {
        ItemStack item = null;
        if (args.size() == 1) {
            item = new ItemStack(CommandBase.getItemByText(player.getPlayer(), args.get(0)), 64, 64);
        } else if (args.size() == 2) {
            int stackSize = Integer.parseInt(args.get(1));
            System.out.println("ItemStack size = " + stackSize);
            int i = CommandBase.parseIntBounded(player.getPlayer(), args.get(1), stackSize, stackSize);
            item = new ItemStack(CommandBase.getItemByText(player.getPlayer(), args.get(0)), stackSize, stackSize);
        }
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
}
