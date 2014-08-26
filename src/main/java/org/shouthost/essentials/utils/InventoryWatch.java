package org.shouthost.essentials.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.shouthost.essentials.entity.Player;

public class InventoryWatch implements IInventory {
    private final Player viewer;
    private final Player player;

    public InventoryWatch(Player viewer, Player player) {
        this.viewer = viewer;
        this.player = player;
    }

    @Override
    public int getSizeInventory() {
        if (player.getPlayer() == null || player.getPlayer().isDead) {
            viewer.getPlayer().closeScreen();
        }

        return 45;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        if (player.getPlayer() == null || player.getPlayer().isDead) {
            viewer.getPlayer().closeScreen();
            return null;
        }

        if (var1 >= 0 && var1 < 27) {
            return player.getPlayer().inventory.mainInventory[var1 + 9];
        } else if (var1 >= 27 && var1 < 36) {
            return player.getPlayer().inventory.mainInventory[var1 - 27];
        } else if (var1 >= 36 && var1 < 40) {
            return player.getPlayer().inventory.armorInventory[39 - var1];
        } else return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (player.getPlayer() == null || player.getPlayer().isDead) {
            viewer.getPlayer().closeScreen();
            return null;
        }

        ItemStack stack = getStackInSlot(i);
        if (stack != null) {
            if (stack.stackSize <= j) {
                setInventorySlotContents(i, null);
                markDirty();
                return stack;
            }
            ItemStack stack1 = stack.splitStack(j);
            if (stack.stackSize == 0) {
                setInventorySlotContents(i, null);
            }
            markDirty();
            return stack1;
        } else
            return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        if (player.getPlayer() == null || player.getPlayer().isDead) {
            viewer.getPlayer().closeScreen();
            return null;
        }

        ItemStack stack = getStackInSlot(var1);
        if (stack != null) {
            setInventorySlotContents(var1, null);
            return stack;
        } else return null;
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {
        if (player.getPlayer() == null || player.getPlayer().isDead) {
            viewer.getPlayer().entityDropItem(var2, 0.5F);
            viewer.getPlayer().closeScreen();
            return;
        }

        if (var1 >= 0 && var1 < 27) {
            player.getPlayer().inventory.mainInventory[var1 + 9] = var2;
        } else if (var1 >= 27 && var1 < 36) {
            player.getPlayer().inventory.mainInventory[var1 - 27] = var2;
        } else if (var1 >= 36 && var1 < 40) {
            player.getPlayer().inventory.armorInventory[39 - var1] = var2;
        } else {
            viewer.getPlayer().entityDropItem(var2, 0.5F);
        }
    }

    @Override
    public String getInventoryName() {

        return player.getPlayer().getDisplayName();
    }

    @Override
    public boolean hasCustomInventoryName() {

        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        if (player.getPlayer() == null || player.getPlayer().isDead) {
            viewer.getPlayer().closeScreen();
            return 64;
        }

        return player.getPlayer().inventory.getInventoryStackLimit();
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        if (player.getPlayer() == null || player.getPlayer().isDead) {
            viewer.getPlayer().closeScreen();
            return false;
        }

        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }
}