package org.shouthost.essentials.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.server.MinecraftServer;
import org.shouthost.essentials.commands.*;
import org.shouthost.essentials.entity.Player;
import org.shouthost.essentials.events.PlayerEvents;
import org.shouthost.essentials.json.books.Books;
import org.shouthost.essentials.json.kits.Kit;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.scheduler.Scheduler;
import org.shouthost.essentials.utils.DataUtils;
import org.shouthost.essentials.utils.Warp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


@Mod(name = "Essentials", modid = "essentials", version = "0.0.0", acceptableRemoteVersions = "*")

public class Essentials {

    public static ArrayList<Kit> usableKit = new ArrayList<Kit>();
    public static Scheduler schedule;
    public static HashMap<UUID, Players> playersList = new HashMap<UUID, Players>();
	public static HashMap<UUID, Player> playerList = new HashMap<UUID, Player>();
	public static HashMap<UUID, UUID> tpRequest = new HashMap<UUID, UUID>();
    public static ArrayList<Player> isFlying = new ArrayList<Player>();
    public static ArrayList<Warp> warpList = new ArrayList<Warp>();
	public static ArrayList<Books> book = new ArrayList<Books>();
	public static File base, players, kits, books, warps;
    public static ArrayList<UUID> vanishList = new ArrayList<UUID>();
    public static Essentials instance;
    public static MinecraftServer server = MinecraftServer.getServer();
	public static PlayerEvents playerEvent;
    private ArrayList<CommandListener> cmdList = new ArrayList<CommandListener>();

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
        schedule = new Scheduler();
        cmdList.add(new BansCommands());
        cmdList.add(new HomeCommands());
        cmdList.add(new ItemCommands());
        cmdList.add(new MessageCommands());
        cmdList.add(new TeleportCommands());
        cmdList.add(new ToolCommands());
        cmdList.add(new UtilsCommands());
    }

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
        CommandManager.registerCommands(cmdList);

		//Data.LoadPlayers();
		DataUtils.LoadKits();
		DataUtils.LoadBooks();
		DataUtils.LoadWarps();
	}


}
