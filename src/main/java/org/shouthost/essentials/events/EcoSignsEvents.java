package org.shouthost.essentials.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSign;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.shouthost.essentials.commands.CommandListener;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.entity.Player;
import scala.actors.threadpool.Arrays;

import java.util.List;

public class EcoSignsEvents {
    public EcoSignsEvents() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void signShopCheck(PlayerInteractEvent event) {
        if (Essentials.debug && !event.world.isRemote) {
            Block block = event.world.getBlock(event.x, event.y, event.z);

            if ((block == Blocks.standing_sign || block == Blocks.wall_sign) && block instanceof BlockSign) {
                TileEntitySign sign = (TileEntitySign) event.world.getTileEntity(event.x, event.y, event.z);
                List<String> text = Arrays.asList(sign.signText);
                if (!sign.isInvalid() && text.get(0) == "SignShop") {
                    Player player = CommandListener.getPlayerFromEntity((EntityPlayerMP) event.entityPlayer);
                    int cost = Integer.parseInt(text.get(1));
                    int sellPrice = Integer.parseInt(text.get(2));
					Item item = (Item) Item.itemRegistry.getObject(text.get(3));
					int amount = Integer.parseInt(text.get(3));

                    //TODO: Rework this check
                    Player seller = CommandListener.getPlayerFromString(text.get(5));//TODO:
                    ItemStack stack = new ItemStack(item);
                    if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                        if (player.getBalance() >= cost) {
                            //TODO:See if the player has room in inventory for items being bought.
                            player.setBalance(player.getBalance() - cost);
                            player.giveItem(new ItemStack(item, amount));
                            player.sendSuccessMessage("Succesfully bought " + amount + " " + item.getItemStackDisplayName(new ItemStack(item)) + " for " + cost + ".");
                        } else {
                            player.sendErrorMessage("Invalid funds.");
                        }
                    } else if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                        //TODO:Check to see if the player has what the shops buying.
//						if(player.hasItem(item) && player.getItemStackAmount(item) == amount)
                        player.setBalance(player.getBalance() + sellPrice);
                        player.sendSuccessMessage("Succesfully sold " + amount + " " + item.getItemStackDisplayName(new ItemStack(item)) + " for " + sellPrice + ".");
                    }
                }
            }
        }
    }
}
