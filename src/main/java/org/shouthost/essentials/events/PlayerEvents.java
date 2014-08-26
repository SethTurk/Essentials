package org.shouthost.essentials.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerEvents {
    public PlayerEvents() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void nameFormat(PlayerEvent.NameFormat event) {
        //if(event.entityPlayer.worldObj.isRemote) return;
//		StringBuilder sb = new StringBuilder();
//		World world = event.entityPlayer.worldObj;
//		String worldName = world.provider.getDimensionName();
//		if (worldName != null) {
//			String group = IHandler.chat.getPrefix(event.entityPlayer.getPersistentID(), worldName);
//			String prefix = ForgePermsAPI.chatManager.getGroupPrefix(worldName, group);
//			sb.append(prefix);
//			String fin = sb.toString() + event.username;
//			System.out.println(EnumChatFormatting.GREEN);
//			event.displayname = (EnumChatFormatting.GREEN + fin);
//		}
        //event.displayname = EnumChatFormatting.GREEN+event.username;
    }

    @SubscribeEvent
    public void onServerChatEvent(ServerChatEvent event) {
//		event.player.refreshDisplayName();
//		event.setCanceled(true);
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
//		MinecraftServer.getServer().addChatMessage(new ChatComponentText(event.username + " : " + event.message));


    }

    @SubscribeEvent
    public void onLogin(PlayerLoggedInEvent event) {
        //TODO:Decided to load the files on when the player is Player class is called.
    }

    @SubscribeEvent
    public void onLogout(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event) {

    }

}
