package org.shouthost.essentials.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.shouthost.essentials.commands.CommandListener;
import org.shouthost.essentials.entity.Player;

public class EssentialsChat {

    public EssentialsChat() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onNameFormatEvent(PlayerEvent.NameFormat event) {
        //This event is used to format the player name. This will get moved to its own module in the future
        Player player = CommandListener.getPlayerFromString(event.username);
        if (player == null) return;
        event.displayname = String.format("%s%s%s", player.getPrefix().replaceAll("&", "§"), player.getNickname().replaceAll("&", "§"), player.getSuffix().replaceAll("&", "§"));
        //event.entityPlayer.refreshDisplayName();
    }

    @SubscribeEvent
    public void onServerChatEvent(ServerChatEvent event) {
        Player player = CommandListener.getPlayerFromString(event.username);
        if (player == null) return;
        if (player.isMuted()) {
            player.sendMessage(EnumChatFormatting.RED + "You are muted for: " + EnumChatFormatting.YELLOW + player.getMuteReason());
            event.setCanceled(true);
        }
    }
}
