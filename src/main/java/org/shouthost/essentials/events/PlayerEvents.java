package org.shouthost.essentials.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.shouthost.essentials.core.Essentials;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.utils.config.Chat;
import org.shouthost.essentials.utils.config.Player;

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
		event.setCanceled(true);
		event.player.addChatMessage(new ChatComponentText(Chat.BuildUsername(event.player) + " " + event.message));

	}

	@SubscribeEvent
	public void onLogin(PlayerLoggedInEvent event){
		//ban section

		//adding or creating their data file
		Players player;
		if(!Player.PlayerExistInMemory(event.player.getUniqueID())){
			player = Player.CreatePlayer(event.player);
		}else{
			player = Player.FindPlayer(event.player);
		}

		//Do a check to see if username changed
		if(player.getPlayerName() != event.player.getDisplayName()){
			player.setPlayername(event.player.getDisplayName());
			Player.SavePlayer(player);
		}

		//adding to list
		Essentials.globalList.add(player);
	}

	@SubscribeEvent
	public void onLogout(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event){

	}

	@SubscribeEvent
	public void PlayerUpdateEvent(TickEvent.PlayerTickEvent event){
		//System.out.println("Player "+event.player.getDisplayName()+" updated x"+event.player.posX+" y"+event.player.posY+" z"+event.player.posZ);
	}

}
