package org.shouthost.essentials.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.server.MinecraftServer;
import org.shouthost.essentials.api.ITick;
import org.shouthost.essentials.commands.*;
import org.shouthost.essentials.events.PlayerEvents;
import org.shouthost.essentials.json.books.Books;
import org.shouthost.essentials.json.kits.Kit;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.tickhandler.ITickEvent;
import org.shouthost.essentials.utils.config.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;


@Mod(name = "Essentials", modid = "essentials", version = "")

public class Essentials {

	public static ArrayList<Kit> usableKit = new ArrayList();
	public static HashMap<UUID, Players> playersList = new HashMap<UUID, Players>();
	public static ArrayList<Books> book = new ArrayList<Books>();
	public static File base, players, kits, books, warps;

	public static Essentials instance;

	public static ConcurrentLinkedQueue<ITick> tasks = new ConcurrentLinkedQueue<ITick>();
	public static MinecraftServer server = MinecraftServer.getServer();
	public static PlayerEvents playerEvent;
	public static ITickEvent tickHandler;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		base = new File("Essentials");
		if (!base.exists()) base.mkdir();
		players = new File(base, "players");
		if (!players.exists()) players.mkdir();
		kits = new File(base, "kits");
		if (!kits.exists()) kits.mkdir();
		books = new File(base, "books");
		if (!books.exists()) books.mkdir();
		warps = new File(base, "warps");
		if (!warps.exists()) warps.mkdir();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		playerEvent = new PlayerEvents();
		tickHandler = new ITickEvent();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandItem());
		event.registerServerCommand(new CommandMute());
		event.registerServerCommand(new CommandSethome());
		event.registerServerCommand(new CommandHome());
		event.registerServerCommand(new CommandHeal());
		event.registerServerCommand(new CommandLag());
		event.registerServerCommand(new CommandBurn());
		//Data.LoadPlayers();
		Data.LoadKits();
		Data.LoadBooks();
	}


}
