package org.shouthost.essentials.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.*;
import org.shouthost.essentials.commands.*;
import org.shouthost.essentials.events.PlayerEvents;
import org.shouthost.essentials.json.books.Books;
import org.shouthost.essentials.json.books.Page;
import org.shouthost.essentials.json.kits.Kit;
import org.shouthost.essentials.json.players.Homes;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.utils.config.Data;
import org.shouthost.essentials.utils.config.Player;
import org.shouthost.essentials.utils.config.Reason;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


@Mod(name="Essentials",modid="essentials",version="")
public class Essentials {

	public static ArrayList<Kit> usableKit = new ArrayList();
	public static HashMap<UUID, Players> playersList = new HashMap<UUID, Players>();
	public static ArrayList<Books> book = new ArrayList<Books>();
	public static File base,players,kits,books;

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
		books = new File(base, "books");
		if(!books.exists()) books.mkdir();
	}

	@EventHandler
	public void init(FMLInitializationEvent event){
		playerEvent = new PlayerEvents();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event){
		event.registerServerCommand(new CommandItem());
		event.registerServerCommand(new CommandMute());
		event.registerServerCommand(new CommandSethome());
		event.registerServerCommand(new CommandHome());
		event.registerServerCommand(new CommandHeal());
		event.registerServerCommand(new CommandLag());
		event.registerServerCommand(new CommandBurn());
		Data.LoadPlayers();
		Data.LoadKits();
		Data.LoadBooks();
	}


}
