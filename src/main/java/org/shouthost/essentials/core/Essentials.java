package org.shouthost.essentials.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.*;
import org.shouthost.essentials.commands.CommandItem;
import org.shouthost.essentials.commands.CommandMute;
import org.shouthost.essentials.events.PlayerEvents;
import org.shouthost.essentials.utils.Reason;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


@Mod(name="Essentials",modid="essentials",version="")
public class Essentials {

	public static HashMap<UUID, Reason>muteList = new HashMap<UUID, Reason>();
	public static HashMap<UUID, Reason>banList = new HashMap<UUID, Reason>();
	public static ArrayList usableKit = new ArrayList();
	public static File base,players,kits;
	@Instance("Essentials")
	public static Essentials instance;

	public static PlayerEvents playerEvent;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		base = new File("Essentials");
		if(!base.exists())base.mkdir();
		players = new File(base, "players");
		if(!players.exists()) players.mkdir();
		kits = new File(base,"kits");
		if(!kits.exists()) kits.mkdir();

	}

	@EventHandler
	public void init(FMLInitializationEvent event){
		playerEvent = new PlayerEvents();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event){
		event.registerServerCommand(new CommandItem());
		event.registerServerCommand(new CommandMute());
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event){

	}

}
