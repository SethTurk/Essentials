package org.shouthost.essentials.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.utils.config.Player;

import java.io.File;
import java.util.*;

public class WorldEvents {
	public WorldEvents(){
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event){
		//Get the list of players on the server
		List<EntityPlayer> list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		for(EntityPlayer p: list){
				p.addChatMessage(new ChatComponentText("Saving your data"));
				Player player = new Player(p);
				player.save();
		}
	}
}


