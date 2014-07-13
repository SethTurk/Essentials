package org.shouthost.essentials.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import forgeperms.api.ForgePermsAPI;
import net.minecraft.block.Block;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.MinecraftForge;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.factory.event.LightningStrikeEvent;
import org.shouthost.essentials.factory.event.PlayerTeleportEvent;
import org.shouthost.essentials.json.players.Homes;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.utils.compat.Location;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

public class Player {

	private EntityPlayerMP entityPlayer;
	private Players player;

	// private List<Homes> homes;
	public Player(ICommandSender sender) {
		this((EntityPlayerMP) sender);
	}

	public Player(String name) {
		this(MinecraftServer.getServer().getConfigurationManager().func_152612_a(name));
	}

	public Player(EntityPlayerMP player) {
		if (player != null) {
			this.entityPlayer = player;
			if (exist()) {
				load();
			} else {
				create();
			}
		}
	}

	private boolean exist() {
		//first check to see if file exist
		File check = new File(Essentials.players, this.entityPlayer.getUniqueID().toString().replaceAll("-", "") + ".json");
		if (check.exists()) {
			if (Essentials.playersList.containsKey(this.entityPlayer.getUniqueID())) {
				return true;
			}
			return loadIntoMemory(check);
		}
		return false;
	}

	private boolean loadIntoMemory(File file) {
		if (file == null) return false;
		Gson gson = new Gson();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		Players player = gson.fromJson(br, Players.class);
		if (!Essentials.playersList.containsKey(entityPlayer.getPersistentID()))
			Essentials.playersList.put(entityPlayer.getPersistentID(), player);
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void load() {
		this.player = Essentials.playersList.get(this.entityPlayer.getUniqueID());
	}

	private void create() {
		Players newPlayer = new Players();
		newPlayer.setPlayername(this.entityPlayer.getDisplayName());
		newPlayer.setUuid(this.entityPlayer.getUniqueID().toString().replaceAll("-", ""));
		newPlayer.setWorld(this.entityPlayer.worldObj.provider.dimensionId);
		newPlayer.setPosX(this.entityPlayer.posX);
		newPlayer.setPosY(this.entityPlayer.posY);
		newPlayer.setPosZ(this.entityPlayer.posZ);
		newPlayer.setBanned(false);
		newPlayer.setMuted(false);
		newPlayer.setJailed(false);
		this.player = newPlayer;
		Essentials.playersList.put(this.entityPlayer.getUniqueID(), newPlayer);
	}

	public UUID getUUID() {
		return UUID.fromString(get().getUuid());
	}


	//TODO finish implementing everything so we dont have to call this directly
	public Players get() {
		return this.player;
	}

	//this will be temp until most of whats needed gets implemented into this class
	public EntityPlayerMP getPlayer() {
		return entityPlayer;
	}

	public void setHome(String name) {
		Homes home = null;
		if (name.equalsIgnoreCase("home")) {
			home = getHome("home");
			if (home == null) {
				home = new Homes();
				home.setName("home");
			}

		} else {
			home = new Homes();
			home.setName(name);
		}
		System.out.println(home == null);
		home.setWorld(getWorld().provider.dimensionId);
		int x = (int) this.getPosX();
		int y = (int) this.getPosY();
		int z = (int) this.getPosZ();
		if (home != null) {
			home.setX(x);
			home.setY(y);
			home.setZ(z);
			this.player.setHome(home);
		}
		save();
	}

	public void setHome(String name, int x, int y, int z) {
		Homes home = null;
		if (name.equalsIgnoreCase("home")) {
			home = getHome("home");
		} else {
			home = new Homes();
			home.setName(name);
		}
		home.setWorld(getWorld().provider.dimensionId);
		home.setX(x);
		home.setY(y);
		home.setZ(z);
		this.player.setHome(home);
		save();
	}

	public void kick(String reason) {
		if (reason == null) {
			this.entityPlayer.playerNetServerHandler.kickPlayerFromServer("Kicked from the server");
		} else {
			this.entityPlayer.playerNetServerHandler.kickPlayerFromServer(reason);
		}
	}

	public void sendPacket(Packet packet) {
		this.entityPlayer.playerNetServerHandler.sendPacket(packet);
	}

	public WorldSettings.GameType getGameMode() {
		return this.entityPlayer.theItemInWorldManager.getGameType();
	}

	public void setGameMode(WorldSettings.GameType type) {
		this.entityPlayer.theItemInWorldManager.setGameType(type);
	}

	public void delhome(Homes home) {
		if(getHome(home.getName()) != null) this.player.getHomes().remove(home);
	}

	public void exec(String command) {
		//TODO: make player execute a command ?
		ICommandManager sender = MinecraftServer.getServer().getCommandManager();
		sender.executeCommand((ICommandSender) this.entityPlayer, command);
	}

	public Location getLocation() {
		return new Location(getWorld(), getPosX(), getPosY(), getPosZ(), entityPlayer.getRotationYawHead(), entityPlayer.getEyeHeight());
	}

	public double getPosX() {
		return this.entityPlayer.posX;
	}

	public double getPosY() {
		return this.entityPlayer.posY;
	}

	public double getPosZ() {
		return this.entityPlayer.posZ;
	}

	public void viewInventory(EntityPlayerMP target) {
		//TODO: Add permission nodes to check to see if player if view and or modify inventory of its target
		if (target == null || target.isDead) {
			target.closeScreen();
			return;
		}
		this.entityPlayer.displayGUIChest(new InventoryWatch(this.entityPlayer, target));
	}

	public void giveItem(ItemStack item) {
		if (item == null) {
			sendMessage("Error");
			return;
		}

		EntityItem i = getPlayer().dropPlayerItemWithRandomChoice(item, false);
		i.delayBeforeCanPickup = 0;
		i.func_145797_a(this.entityPlayer.getDisplayName());

	}

	public void suicide() {
		if (this.entityPlayer.isDead) return;
		getPlayer().setDead();
	}

	public World getWorld() {
		return this.entityPlayer.worldObj;
	}

	public boolean has(String node) {
		if (ForgePermsAPI.permManager == null) return true;
		return ForgePermsAPI.permManager.canAccess(this.entityPlayer.getDisplayName(), getWorld().getWorldInfo().getWorldName(), node);
	}

	public Homes getHome(String name) {
		for (Homes home : this.player.getHomes()) {
			sendMessage(home.getName());
			if (home.getName().equalsIgnoreCase(name)) {
				return home;
			}
		}
		return null;
	}

	public List<String> getHomeList() {
		ArrayList<String> list = new ArrayList<String>();
		for (Homes home : get().getHomes()) {
			if (!list.contains(home.getName())) list.add(home.getName());
		}
		return list;
	}

	public int getHomeCount() {
		return get().getHomes().size();
	}

	public void updateName(Players player, String name) {
		this.player.setPlayername(name);
	}

	public void updateCoords(int x, int y, int z) {
		this.player.setPosX(x);
		this.player.setPosY(y);
		this.player.setPosZ(z);
	}

	public void updateCoords(Location location) {
		updateCoords((int) location.getX(), (int) location.getY(), (int) location.getZ());
	}

	public void mute(String reason, int timeout) {
		if (this.player.isMuted()) return;
		this.player.setMuted(true);
		this.player.setMuteReason(reason);
		if (timeout > 0)
			this.player.setMuteTimeout(timeout);
		save();
	}

	public void unMute() {
		if (!this.player.isMuted()) return;
		this.player.setMuted(false);
		save();
	}

	public void warn(String reason) {
		this.player.setWarning(reason);
		save();
	}

	public void ban(String reason, int timeout) {
		if (this.player.isBanned()) return;
		this.player.setBanned(true);
		this.player.setBanReason(reason);
		this.player.setBanTimeout(timeout);
	}

	public void unban() {
		if (this.player.isBanned()) this.player.setBanned(false);
	}

	public void updateBan(String reason, int timeout) {
		if (!this.player.getBanReason().contains(reason)) this.player.setBanReason(reason);
		if (this.player.getBanTimeout() != timeout) this.player.setBanTimeout(timeout);
	}

	public void save() {
		final Players p = this.player;
		Essentials.schedule.scheduleSyncTask(new Runnable() {
			@Override
			public void run() {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(p);
				File file = new File(Essentials.players, p.getUuid() + ".json");
				if (file.exists() && file.isFile()) file.delete();
				try {
					Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
					writer.write(json);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void sendMessage(String msg) {
		this.entityPlayer.addChatMessage(new ChatComponentText(msg));
	}

	public boolean sendMessageTo(String name, String msg) {
		EntityPlayerMP target = MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);//getPlayerForUsername
		if (target == null) {
			sendMessage(EnumChatFormatting.RED+"Player \"" + name + "\" does not exist");
			return false;
		}
		sendMessage("[me -> " + this.player.getPlayerName() + "] " + msg);
		new Player(target).sendMessage("[" + this.player.getPlayerName() + " -> me] " + msg);
		return true;
	}

	public void spawn(){
		//TODO: work on getting default spawn
		WorldInfo info = MinecraftServer.getServer().getEntityWorld().getWorldInfo();

	}

	public void strike(Player target){
		strike(target.getLocation());
	}

	public Location getViewLocationAt(){

		return null;
	}

	public void strike(Location loc){
		LightningStrikeEvent event = new LightningStrikeEvent(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		MinecraftForge.EVENT_BUS.post(event);
		if(event.isCanceled()) return;
		loc.getWorld().addWeatherEffect(new EntityLightningBolt(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
	}

	public void strike(){
		strike(this);
	}

	public int refresh(int r) {
		final WorldServer worldServer = (WorldServer) entityPlayer.worldObj;
		final int radius = r != 0 ? (r <= 30 ? r : 30) : 20;
		final int centerX = getLocation().getBlockX();
		final int centerY = getLocation().getBlockY();
		final int centerZ = getLocation().getBlockZ();
				ArrayList<Chunk> c = new ArrayList<Chunk>();
				for (int x = centerX - radius; x < centerX + radius; x++) {
					for (int y = centerY - radius; y < centerY + radius; y++) {
						for (int z = centerZ - radius; z < centerZ + radius; z++) {
							Chunk chunk = worldServer.getChunkFromBlockCoords(x,z);
							if (!c.contains(chunk)) {
								if(!worldServer.isAirBlock(x,y,z)) {
									c.add(chunk);
									worldServer.getPlayerManager().markBlockForUpdate(x, y, x);
								}
							}
						}
					}
				}
		if(entityPlayer.getHeldItem() != null)
			entityPlayer.updateHeldItem();
		if(entityPlayer.isRiding()) {
			entityPlayer.updateRidden();
			entityPlayer.updateRiderPosition();
		}
		return c.size();
	}

	public void teleportTo(World world, int x, int y, int z, float yaw, float pitch){
		if (world == null) return;
		if (this.entityPlayer.worldObj.provider.dimensionId != world.provider.dimensionId)
			MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) this.entityPlayer, world.provider.dimensionId);
		this.entityPlayer.setPositionAndUpdate(x, y, z);
		entityPlayer.setPositionAndRotation(x,y,z,yaw,pitch);
		updateCoords(x, y, z);
		save();
	}

	public void teleportTo(World world, int x, int y, int z) {
		teleportTo(world,x,y,z,0,0);
	}

	public void teleportTo(Location location) {
		if (location == null) return;
		teleportTo(location.getWorld(), (int) location.getX(), (int) location.getY(), (int) location.getZ(), location.getYaw(), location.getPitch());
	}

	public void teleportTo(EntityPlayer target) {
		if (target == null) return;
		teleportTo(new Location(target.worldObj, (int) target.posX, (int) target.posY, (int) target.posZ));
	}

	public void teleport(Player ePlayer) {
		teleport(ePlayer.getLocation());
	}

	public void teleport(Location loc) {

		if (getHealth() == 0 || entityPlayer.isDead) return;
		if (entityPlayer.ridingEntity != null || entityPlayer.isRiding()) return;

		Location from = getLocation();
		Location to = loc;

		PlayerTeleportEvent event = new PlayerTeleportEvent(this, from, to);
		MinecraftForge.EVENT_BUS.post(event);
		if (!event.isCanceled()) {
			teleportTo(to);
		}
	}

	public void warpTo(String name){
		for(Warp warp : Essentials.warpList)
			if(warp.name.equalsIgnoreCase(name)) teleport(warp.loc);
	}

	public float getHealth() {
		return this.entityPlayer.isDead ? 0 : this.entityPlayer.getHealth();
	}

	public void setHealth(int health) {
		this.entityPlayer.setHealth(health);
	}

	public InetSocketAddress getAddress() {
		if (entityPlayer.isClientWorld()) return null;
		SocketAddress addr = entityPlayer.playerNetServerHandler.netManager.getSocketAddress();
		if (addr instanceof InetSocketAddress) return (InetSocketAddress) addr;
		return null;
	}

	public void sendBlockChange(Location loc, Block block) {
		if (loc.getWorld().isRemote) return;
		if (entityPlayer.playerNetServerHandler.netManager == null) return;
		S23PacketBlockChange change = new S23PacketBlockChange(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld());
		change.field_148883_d = block;
		change.field_148884_e = new Worlds(loc.getWorld()).getBlockMetadata(loc);
		sendPacket(change);
	}

}
