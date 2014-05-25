package org.shouthost.essentials.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.utils.config.Player;

import java.io.File;
import java.util.Iterator;

public class WorldEvents {
	public WorldEvents(){
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event){
		Iterator<Players> list = Essentials.playerList.iterator();
		while(list.hasNext()){
			Player.SavePlayer(list.next());
		}
	}


}
