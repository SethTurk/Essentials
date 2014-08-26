package org.shouthost.essentials.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;

public class PlayerEvents {
    public PlayerEvents() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onServerChatEvent(ServerChatEvent event) {
//		//Mute Check
//		Player player = new Player(event.player);
//		if (player.get().isMuted()) {
//			if (player.get().getMuteReason() != null) {
//				player.sendMessage(EnumChatFormatting.RED + "You are muted for " + player.get().getMuteReason());
//			} else {
//				player.sendMessage(EnumChatFormatting.RED + "You are muted");
//			}
//			event.setCanceled(true);
//			return;
//		}


    }

    @SubscribeEvent
    public void onLogin(PlayerLoggedInEvent event) {
        //TODO:Decided to load the files on when the player is Player class is called.
    }

    @SubscribeEvent
    public void onLogout(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event) {

    }

}
