package org.shouthost.essentials.utils;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import org.shouthost.essentials.core.Essentials;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//FIXME: Work on file backend api and implement this into it
public class KitTemplate {
    private String name;
    private ItemStack[] mainInventory;
    private ItemStack[] armorInventory;//ignore for now

    public KitTemplate(String name) {
        this.name = name;
    }

    public KitTemplate(String name, ItemStack[] main, ItemStack[] armor) {
        this.name = name;
        this.armorInventory = null;//armor.clone();
        this.mainInventory = main.clone();
    }

    public void constructKitFromInventory() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("size", mainInventory.length);
        for (int i = 0; i < mainInventory.length; i++) {
            if (mainInventory[i] != null)
                System.out.println(mainInventory[i].getTagCompound().toString());
//                try {
//                    System.out.println(i);
//                    nbt.setByteArray(""+i, CompressedStreamTools.compress(mainInventory[i].copy().stackTagCompound));
//                } catch (IOException ignored) {
//
//                }
        }
//        try {
//            CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(new File(Essentials.kits, name.toLowerCase()+".bin")));
//        } catch (IOException ignored) {
//
//        }
    }

    public void reconstructKit() {
        try {
            NBTTagCompound data = CompressedStreamTools.readCompressed(new FileInputStream(new File(Essentials.kits, name.toLowerCase() + ".bin")));
            int size = data.getInteger("size");
            for (int i = 0; i < size; i++) {
                mainInventory[i].loadItemStackFromNBT(CompressedStreamTools.func_152457_a(data.getByteArray("" + i), NBTSizeTracker.field_152451_a));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void giveKitToPlayer(EntityPlayerMP player) {
        InventoryPlayer pl = player.inventory;
        for (int i = 0; i < pl.mainInventory.length; i++) {
            if (pl.mainInventory[i] == null) {
                pl.mainInventory[i].setTagCompound(mainInventory[i].getTagCompound());
            }
        }

    }
}
