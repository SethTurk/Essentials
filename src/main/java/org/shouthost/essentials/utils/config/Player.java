package org.shouthost.essentials.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.GameProfile;
import forgeperms.api.ForgePermsAPI;
import net.minecraft.block.Block;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.WorldInfo;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.json.players.Homes;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.utils.compat.Location;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player extends EntityPlayerMP {
	private Players player;
	// private List<Homes> homes;
	public Player(ICommandSender sender) {
		this((EntityPlayerMP) sender);
	}

	public Player(String name) {
		this(MinecraftServer.getServer().getConfigurationManager().func_152612_a(name));
	}

	public Player(EntityPlayerMP player) {
		super(player.mcServer, player.getServerForPlayer(), player.getGameProfile(), player.theItemInWorldManager);
		if (player != null) {
			if (exist()) load();
			else create();
		}
	}

	public Player(MinecraftServer p_i45285_1_, WorldServer p_i45285_2_, GameProfile p_i45285_3_, ItemInWorldManager p_i45285_4_) {
		super(p_i45285_1_, p_i45285_2_, p_i45285_3_, p_i45285_4_);
	}

	private boolean exist() {
		//first check to see if file exist
		File check = new File(Essentials.players, this.getUniqueID().toString().replaceAll("-", "") + ".json");
		if (check.exists()) {
			if (Essentials.playersList.containsKey(this.getUniqueID())) return true;
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
		if (!Essentials.playersList.containsKey(this.getPersistentID())) {
			Essentials.playersList.put(this.getPersistentID(), player);
		}
		if (!Essentials.playerList.containsKey(this.getPersistentID())) {
			Essentials.playerList.put(this.getPersistentID(), this);
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void load() {
		this.player = Essentials.playersList.get(this.getUniqueID());
	}

	private void create() {
		Players newPlayer = new Players();
		newPlayer.setPlayername(this.getDisplayName());
		newPlayer.setUuid(this.getUniqueID().toString().replaceAll("-", ""));
		newPlayer.setWorld(this.worldObj.provider.dimensionId);
		newPlayer.setPosX(this.posX);
		newPlayer.setPosY(this.posY);
		newPlayer.setPosZ(this.posZ);
		newPlayer.setBanned(false);
		newPlayer.setMuted(false);
		newPlayer.setJailed(false);
		this.player = newPlayer;
		Essentials.playersList.put(this.getUniqueID(), newPlayer);
	}

	public String getPlayerName() {
		return this.getDisplayName();
	}

	//TODO finish implementing everything so we dont have to call this directly
	public Players get() {
		return this.player;
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
			this.playerNetServerHandler.kickPlayerFromServer("Kicked from the server");
		} else {
			this.playerNetServerHandler.kickPlayerFromServer(reason);
		}
	}

	public void sendPacket(Packet packet) {
		this.playerNetServerHandler.sendPacket(packet);
	}

	public WorldSettings.GameType getGameMode() {
		return this.theItemInWorldManager.getGameType();
	}

	public void setGameMode(WorldSettings.GameType type) {
		this.theItemInWorldManager.setGameType(type);
	}

	public void delhome(Homes home) {
		if (getHome(home.getName()) != null) this.player.getHomes().remove(home);
	}

	public void exec(String command) {
		//TODO: make player execute a command ?
		ICommandManager sender = MinecraftServer.getServer().getCommandManager();
		sender.executeCommand((ICommandSender) this, command);
	}


	public Location getLocation() {
		return new Location(getWorld(), getPosX(), getPosY(), getPosZ(), getRotationYawHead(), getEyeHeight());
	}

	public double getPosX() {
		return this.posX;
	}

	public double getPosY() {
		return this.posY;
	}

	public double getPosZ() {
		return this.posZ;
	}

	public void viewInventory(Player target) {
		//TODO: Add permission nodes to check to see if player if view and or modify inventory of its target
		if (target == null || target.isDead) {
			target.closeScreen();
			return;
		}
		this.displayGUIChest(new InventoryWatch(this, target));
	}

	public void giveItem(ItemStack item) {
		if (item == null) {
			sendMessage("Error");
			return;
		}

		EntityItem i = this.dropPlayerItemWithRandomChoice(item, false);
		i.delayBeforeCanPickup = 0;
		i.func_145797_a(this.getDisplayName());

	}

	public void suicide() {
		if (this.isDead) return;
		this.setDead();
	}

	public World getWorld() {
		return this.worldObj;
	}

	public boolean has(String node) {
		if (ForgePermsAPI.permManager == null) return true;
		return ForgePermsAPI.permManager.canAccess(this.getDisplayName(), getWorld().getWorldInfo().getWorldName(), node);
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

	public String getMuteReaosn() {
		return player.getMuteReason();
	}

	public String getBannedReason() {
		return player.getBanReason();
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

	public void sendMessage(String... lines) {
		sendMessage(new ArrayList<String>(Arrays.asList(lines)));
	}

	public void sendMessage(ArrayList<String> lines) {
		for (String line : lines) sendMessage(line);
	}

	public void sendMessage(String msg) {
		if (msg.contains("\n")) {
			sendMessage(msg.split("\n"));
			return;
		}
		this.addChatMessage(new ChatComponentText(msg));
	}

	public boolean sendMessageTo(String name, String msg) {
		EntityPlayerMP target = MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);
		return target != null ? sendMessageTo(new Player(name), msg) : false;
	}

	public boolean sendMessageTo(Player target, String msg) {
		if (target == null) {
			sendMessage(EnumChatFormatting.RED + "Player does not exist");
			return false;
		}
		sendMessage(String.format("[me -> " + getPlayerName() + "] %s", msg));
		target.sendMessage(String.format("[" + getPlayerName() + " -> me] %s", msg));

		return true;
	}

	public void spawn() {
		//TODO: work on getting default spawn
		WorldInfo info = MinecraftServer.getServer().getEntityWorld().getWorldInfo();

	}

	public void strike(Player target) {
		strike(target.getLocation());
	}

	public Location getViewLocationAt() {

		return null;
	}

	public void strike(Location loc) {
		loc.getWorld().addWeatherEffect(new EntityLightningBolt(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
	}

	public void strike() {
		strike(this);
	}

	public int refresh(int r) {
		WorldServer worldServer = (WorldServer) this.worldObj;
		int radius = r != 0 ? (r <= 30 ? r : 30) : 20;
		int centerX = getLocation().getBlockX();
		int centerY = getLocation().getBlockY();
		int centerZ = getLocation().getBlockZ();
		ArrayList<Chunk> c = new ArrayList<Chunk>();
		for (int x = centerX - radius; x < centerX + radius; x++) {
			for (int y = centerY - radius; y < centerY + radius; y++) {
				for (int z = centerZ - radius; z < centerZ + radius; z++) {
					Chunk chunk = worldServer.getChunkFromBlockCoords(x, z);
					if (!c.contains(chunk)) {
						if (!worldServer.isAirBlock(x, y, z)) {
							c.add(chunk);
							worldServer.getPlayerManager().markBlockForUpdate(x, y, x);
						}
					}
				}
			}
		}
		if (this.getHeldItem() != null)
			this.updateHeldItem();
		if (this.isRiding()) {
			this.updateRidden();
			this.updateRiderPosition();
		}
		return c.size();
	}

	public void teleportTo(World world, int x, int y, int z, float yaw, float pitch) {
		if (world == null) return;
		if (this.worldObj.provider.dimensionId != world.provider.dimensionId)
			MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(this, world.provider.dimensionId);
		this.setPositionAndUpdate(x, y, z);
		this.setPositionAndRotation(x, y, z, yaw, pitch);
		updateCoords(x, y, z);
		save();
	}

	public void teleportTo(World world, int x, int y, int z) {
		teleportTo(world, x, y, z, 0, 0);
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

		if (getHealth() == 0 || this.isDead) return;
		if (this.ridingEntity != null || this.isRiding()) return;

		Location from = getLocation();
		Location to = loc;
		teleportTo(to);

	}

	public void warpTo(String name) {
		for (Warp warp : Essentials.warpList)
			if (warp.name.equalsIgnoreCase(name)) teleport(warp.loc);
	}

	public InetSocketAddress getAddress() {
		if (this.isClientWorld()) return null;
		SocketAddress addr = this.playerNetServerHandler.netManager.getSocketAddress();
		if (addr instanceof InetSocketAddress) return (InetSocketAddress) addr;
		return null;
	}

	public void sendBlockChange(Location loc, Block block) {
		if (loc.getWorld().isRemote) return;
		if (this.playerNetServerHandler.netManager == null) return;
		S23PacketBlockChange change = new S23PacketBlockChange(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld());
		change.field_148883_d = block;
		change.field_148884_e = new Worlds(loc.getWorld()).getBlockMetadata(loc);

		sendPacket(change);
	}

	public void sendSound(Location loc, String name, float volume, float pitch) {
		if (loc.getWorld().isRemote) return;
		if (this.playerNetServerHandler.netManager == null) return;
		S29PacketSoundEffect sound = new S29PacketSoundEffect(name, loc.getX(), loc.getY(), loc.getZ(), volume, pitch);
		sendPacket(sound);
	}

	public void sendEffect(String particle, Location loc, double width, double motionX, double motionZ) {
		//TODO: Work on effects
		//S28PacketEffect effects = new S28PacketEffect(id, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), );
//		sendPacket(effects);
		this.worldObj.spawnParticle(particle, loc.getX(), loc.getY(), loc.getZ(), width, motionX, motionZ);
	}

	public Location getEyeLocation(boolean air) {
		MovingObjectPosition loc = this.rayTrace(100, 1.5F);
		Location n = new Location(getWorld(), loc.blockX, loc.blockY, loc.blockZ);
		if (n.getBlock().equals(Blocks.air) && !air) {
			//iterate down until block isnt air since we dont want to get air block
			Block block = n.getBlock();
			int y = n.getBlockY();
			while (block.equals(Blocks.air)) {
				y--;
				block = getWorld().getBlock(loc.blockX, y, loc.blockZ);
			}
			return new Location(getWorld(), loc.blockX, y, loc.blockZ);
		}
		return n;
	}

}
