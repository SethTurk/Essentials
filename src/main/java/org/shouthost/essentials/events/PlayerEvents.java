package org.shouthost.essentials.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgeperms.api.ForgePermsAPI;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.utils.Chat;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
public class PlayerEvents {
	public PlayerEvents(){
		MinecraftForge.EVENT_BUS.register(this);
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
//		event.displayname = Chat.BuildUsernameFromGroup(event.entityPlayer);
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
	public void BannedCheck(PlayerLoggedInEvent event){
		//MinecraftServer.getServer().getConfigurationManager()
	}

}
