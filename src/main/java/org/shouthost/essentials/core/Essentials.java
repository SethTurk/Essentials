package org.shouthost.essentials.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.server.MinecraftServer;
import org.shouthost.essentials.commands.*;
import org.shouthost.essentials.events.PlayerEvents;
import org.shouthost.essentials.factory.CraftTweaksEventFactory;
import org.shouthost.essentials.json.books.Books;
import org.shouthost.essentials.json.kits.Kit;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.json.warps.Warps;
import org.shouthost.essentials.scheduler.IScheduler;
import org.shouthost.essentials.utils.config.Data;
import org.shouthost.essentials.utils.config.ItemDB;
import org.shouthost.essentials.utils.config.Warp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


@Mod(name = "Essentials", modid = "essentials", version = "0.0.0")

public class Essentials {

	public static ArrayList<Kit> usableKit = new ArrayList();
	public static IScheduler schedule;
	public static HashMap<UUID, Players> playersList = new HashMap<UUID, Players>();
	public static HashMap<UUID, UUID> tpRequest = new HashMap<UUID, UUID>();
	public static HashMap<UUID, UUID> replyList = new HashMap<UUID, UUID>();
	public static ArrayList<Warp> warpList = new ArrayList<Warp>();
	public static ArrayList<Books> book = new ArrayList<Books>();
	public static File base, players, kits, books, warps;

	public static Essentials instance;
	public static MinecraftServer server = MinecraftServer.getServer();
	public static PlayerEvents playerEvent;
	public static ItemDB itemDB;

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
/*
        itemDB = new ItemDB();
*/
		schedule = new IScheduler();
		new CraftTweaksEventFactory();
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
		event.registerServerCommand(new CommandInvSee());
		event.registerServerCommand(new CommandSudo());
		event.registerServerCommand(new CommandVanish());
		event.registerServerCommand(new CommandKill());
		event.registerServerCommand(new CommandMessage());
		event.registerServerCommand(new CommandPing());
		event.registerServerCommand(new CommandWarp());
		event.registerServerCommand(new CommandRefresh());
		event.registerServerCommand(new CommandSmite());
		event.registerServerCommand(new CommandSetWarp());
		//Data.LoadPlayers();
		Data.LoadKits();
		Data.LoadBooks();
		Data.LoadWarps();
	}


}
