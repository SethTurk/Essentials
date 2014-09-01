package org.shouthost.essentials.core;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.server.MinecraftServer;
import org.shouthost.essentials.commands.*;
import org.shouthost.essentials.entity.Player;
import org.shouthost.essentials.events.PlayerEvents;
import org.shouthost.essentials.json.books.Books;
import org.shouthost.essentials.json.configuration.Configuration;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.scheduler.Scheduler;
import org.shouthost.essentials.utils.DataUtils;
import org.shouthost.essentials.utils.FileUtils;
import org.shouthost.essentials.utils.Warp;
import org.shouthost.permissionforge.api.IHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;


@Mod(name = "EssentialsForge", modid = "essentialsforge", version = "@VERSION@", acceptableRemoteVersions = "*")

public class Essentials {

	public static Scheduler schedule;
	public static ArrayList<Warp> warpList = new ArrayList<Warp>();
	public static ArrayList<Books> book = new ArrayList<Books>();
	public static File base, players, kits, books, warps;
	public static ArrayList<UUID> vanishList = new ArrayList<UUID>();
	public static Cache<UUID, Player> playerList = CacheBuilder.newBuilder().build();
	public static Cache<UUID, Players> playersList = CacheBuilder.newBuilder().build();
	//public static Essentials instance;
	public static MinecraftServer server = MinecraftServer.getServer();
	public static PlayerEvents playerEvent;
	public static boolean debug = true;
	private ArrayList<CommandListener> cmdList = new ArrayList<CommandListener>();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
        if ((event.getSide() == Side.CLIENT && !debug))
            throw new RuntimeException("This mod does not work on client side. DO NOT REPORT AS THIS WILL BE IGNORED");
        base = FileUtils.createDirectory("Essentials");
        players = FileUtils.createDirectory(base, "players");
		kits = FileUtils.createDirectory(base, "kits");
		books = FileUtils.createDirectory(base, "books");
		warps = FileUtils.createDirectory(base, "warps");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		schedule = new Scheduler();
        playerEvent = new PlayerEvents();

		cmdList.add(new BansCommands());
		cmdList.add(new HomeCommands());
		cmdList.add(new ItemCommands());
		cmdList.add(new MessageCommands());
		cmdList.add(new TeleportCommands());
		cmdList.add(new ToolCommands());
		cmdList.add(new UtilsCommands());
		cmdList.add(new CommonCommands());
        cmdList.add(new ExtraCommands());
		cmdList.add(new EconomyCommands());
    }

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
        if (!doesPermissionExExist())
            throw new RuntimeException("PermissionForge does not exist. DO NOT REPORT AS THIS WILL BE IGNORED");


        CommandManager.registerCommands(cmdList);

        DataUtils.LoadAll();
    }

	private boolean doesPermissionExExist() {
		if (debug) return true;
		return IHandler.permission != null && IHandler.chat != null;
	}

}
