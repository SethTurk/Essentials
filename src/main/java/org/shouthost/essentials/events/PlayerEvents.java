package org.shouthost.essentials.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.shouthost.essentials.commands.CommandListener;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.entity.Player;
import org.shouthost.essentials.json.players.PowerTools;

public class PlayerEvents {
    public PlayerEvents() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
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

    @SubscribeEvent
    public void onLogin(PlayerLoggedInEvent event) {
        if (Essentials.playerList.getIfPresent(event.player.getPersistentID()) == null) {
            Essentials.playerList.put(event.player.getPersistentID(), CommandListener.getPlayerFromString(event.player.getDisplayName()));
        }
    }

    @SubscribeEvent
    public void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (Essentials.playerList.getIfPresent(event.player.getPersistentID()) == null) {
            Player player = Essentials.playerList.getIfPresent(event.player.getPersistentID());
            player.updateCoords(player.getLocation());
            player.save();
            Essentials.playerList.invalidate(event.player.getPersistentID());
            Essentials.playersList.invalidate(event.player.getPersistentID());
        }
    }

    //FIXME: Fix hanging when checking for powertool
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void powerToolsCheck(PlayerInteractEvent event) {
        if (event.world.isRemote) return;
        EntityPlayerMP pl = (EntityPlayerMP) event.entityPlayer;
        Player player = new Player(pl);
        PlayerInteractEvent.Action action = event.action;
        if ((action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) && pl.getCurrentEquippedItem() != null) {
            if (player.doesPowerToolExist(pl.getCurrentEquippedItem())) {
                PowerTools pt = player.getPowerTool(pl.getCurrentEquippedItem());
                if (pl.canCommandSenderUseCommand(0, pt.getCommand())) {
                    player.exec(pt.getCommand());
                }
            }

        }
    }

}
