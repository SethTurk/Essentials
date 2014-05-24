package org.shouthost.essentials.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.shouthost.essentials.core.Essentials;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import org.shouthost.essentials.utils.config.Chat;

public class PlayerEvents {
	public PlayerEvents(){
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	@SubscribeEvent
	public void nameFormat(PlayerEvent.NameFormat event) {
//		StringBuilder sb = new StringBuilder();
//		World world = event.entityPlayer.worldObj;
//		String worldName = world.getWorldInfo().getWorldName();
//		if(worldName != null) {
//			System.out.println(worldName +"~~~~~~~~~~~");
//			//String group = ForgePermsAPI.chatManager.getPrimaryGroup(worldName, event.username);
//			//if (group != null) {
//				String groupPrefix = ForgePermsAPI.chatManager.getPlayerPrefix(worldName,event.displayname);
//				//String groupSuffix = ForgePermsAPI.chatManager.getGroupSuffix(worldName, "default");
//				sb.append(groupPrefix);//.append(event.username);//.append(groupSuffix);//.append(RESET);
//				event.displayname = sb.toString() + event.displayname;
//			//}
//		}
//		String name = Chat.BuildUsernameFromGroup(event.entityPlayer);
//		if(name != null){
//			event.displayname = name;
//		}
	}

	@SubscribeEvent
	public void MuteCheck(ServerChatEvent event){
		if(Essentials.muteList.containsKey(event.player.getUniqueID()) && Essentials.muteList.get(event.player.getUniqueID()) != null){
			event.player.addChatMessage(new ChatComponentText("You are muted for "+Essentials.muteList.get(event.player.getUniqueID()).getReason()));
			event.setCanceled(true);
			return;
		}else if(Essentials.muteList.containsKey(event.player.getUniqueID()) && Essentials.muteList.get(event.player.getUniqueID()) == null){
			event.player.addChatMessage(new ChatComponentText("You are muted"));
			event.setCanceled(true);
			return;
		}
	}

	@SubscribeEvent
	public void PlayerChat(ServerChatEvent event){
		if(event.isCanceled()) return;
		event.player.addChatMessage(new ChatComponentText(Chat.BuildUsernameFromGroup(event.player) + " " + event.message));
	}


	@SubscribeEvent
	public void BannedCheck(PlayerLoggedInEvent event){
		//MinecraftServer.getServer().getConfigurationManager()
	}

	@SubscribeEvent
	public void PlayerUpdateEvent(TickEvent.PlayerTickEvent event){
		//System.out.println("Player "+event.player.getDisplayName()+" updated x"+event.player.posX+" y"+event.player.posY+" z"+event.player.posZ);
	}

}
