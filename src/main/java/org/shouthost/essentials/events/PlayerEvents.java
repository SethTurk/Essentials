package org.shouthost.essentials.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.shouthost.essentials.factory.event.EntityExplodeEvent;
import org.shouthost.essentials.utils.config.Player;

public class PlayerEvents {
	public PlayerEvents() {
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
	public void onServerChatEvent(ServerChatEvent event) {
		//Mute Check
		Player player = new Player(event.player);
		if (player.get().isMuted()) {
			if (player.get().getMuteReason() != null) {
				player.sendMessage(EnumChatFormatting.RED + "You are muted for " + player.get().getMuteReason());
			} else {
				player.sendMessage(EnumChatFormatting.RED + "You are muted");
			}
			event.setCanceled(true);
			return;
		}


	}

	@SubscribeEvent
	public void onLogin(PlayerLoggedInEvent event) {
		//Decided to load the files on when the player is Player class is called.
		//this will not be the best for performance but will do for now
	}

	@SubscribeEvent
	public void onLogout(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event) {

	}

	@SubscribeEvent
	public void playerInteract(PlayerInteractEvent event) {
	}

	@SubscribeEvent
	public void explosion(EntityExplodeEvent event) {
		event.setCanceled(true);
	}

}
