package org.shouthost.essentials.utils.config;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Iterator;

public class ItemDB {

	public HashMap<String, ItemStack> items = new HashMap<String, ItemStack>();
	@Deprecated
	public ItemDB() {
		Iterator<Item> it = Item.itemRegistry.iterator();
		Iterator<Block> bl = Block.blockRegistry.iterator();
		while (it.hasNext()) {
			Item t = it.next();
			String itemName = t.getUnlocalizedName().substring(5);
			registerIntoMemory(itemName, t);
		}
		while (bl.hasNext()) {
			Block b = bl.next();
			String itemName = b.getLocalizedName();
			registerIntoMemory(itemName, b);
		}

	}

	@Deprecated
	public void registerIntoMemory(String name, Item item) {
		if (items.containsKey(name) || items.containsValue(new ItemStack(item))) return;
		items.put(name, new ItemStack(item));

	}

	@Deprecated
	public void registerIntoMemory(String name, Block block) {
		if (items.containsKey(name) || items.containsValue(new ItemStack(block))) return;
		items.put(name, new ItemStack(block));
	}


}
