package org.shouthost.essentials.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class KitInventory implements IInventory {
    private final EntityPlayerMP player;

    public KitInventory(EntityPlayerMP player) {
        this.player = player;
    }

    @Override
    public int getSizeInventory() {
        return 45;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int i2) {
        ItemStack stack = getStackInSlot(i);
        if (stack != null) {
            if (stack.stackSize <= i2) {
                setInventorySlotContents(i, null);
                markDirty();
                return stack;
            }
            ItemStack stack1 = stack.splitStack(i2);
            if (stack.stackSize == 0) {
                setInventorySlotContents(i, null);
            }
            markDirty();
            return stack1;
        }

        return null;

    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        ItemStack stack = getStackInSlot(i);
        if (stack != null) {
            setInventorySlotContents(i, null);
            return stack;
        }
        return null;

    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {

    }

    @Override
    public String getInventoryName() {
        return "Kit Inventory";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return player.inventory.getInventoryStackLimit();
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return false;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return false;
    }
}
