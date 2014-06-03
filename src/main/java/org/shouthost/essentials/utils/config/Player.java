package org.shouthost.essentials.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import forgeperms.api.ForgePermsAPI;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.json.players.Homes;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.utils.compat.Location;

import java.io.*;
import java.util.UUID;

public class Player {

	private EntityPlayerMP entityPlayer;
	private Players player;

	public Player(ICommandSender sender) {
		this((EntityPlayerMP) sender);
	}

	public Player(EntityPlayerMP player) {
		this.entityPlayer = player;
		if (exist()) {
			load();
		} else {
			create();
		}
	}

	private boolean exist() {
		//first check to see if file exist
		File check = new File(Essentials.players, this.entityPlayer.getUniqueID().toString()+".json");
		if (check.exists()){
			if (Essentials.playersList.containsKey(this.entityPlayer.getUniqueID()))
				return true;
            loadIntoMemory(check);
            return true;
		}
		return false;
	}

	private void loadIntoMemory(File file){
		if(file == null) return;
		Gson gson = new Gson();
		BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Players player = gson.fromJson(br, Players.class);
		if (!Essentials.playersList.containsKey(player.getUuid()))
			Essentials.playersList.put(UUID.fromString(player.getUuid()), player);
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private void load() {
		this.player = Essentials.playersList.get(this.entityPlayer.getUniqueID());
	}

	private void create() {
		Players newPlayer = new Players();
		newPlayer.setPlayername(this.entityPlayer.getDisplayName());
		newPlayer.setUuid(this.entityPlayer.getUniqueID().toString().replaceAll("-",""));
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

	public Players get() {
		return this.player;
	}


	//this will be temp until most of whats needed gets implemented into this class
	public EntityPlayerMP getPlayer(){
		return entityPlayer;
	}

	public void setHome(String name, int x, int y, int z) {
		Homes home = null;
		if (name.contains("home")) {
			home = getHome("home");
		} else {
			home = new Homes();
			home.setName(name);
		}
		home.setWorld(this.entityPlayer.dimension);
		home.setX(x);
		home.setY(y);
		home.setZ(z);
		this.player.setHome(home);
	}

    public void kick(String reason){
        if(reason == null){
            this.entityPlayer.playerNetServerHandler.kickPlayerFromServer("Kicked from the server");
        }else{
            this.entityPlayer.playerNetServerHandler.kickPlayerFromServer(reason);
        }
    }

    public void exec(String command){

    }

    public void viewInventory(EntityPlayerMP target){
        if (target == null || target.isDead){
            target.closeScreen();
            return;
        }
        this.entityPlayer.displayGUIChest(new InventoryWatch(this.entityPlayer,target));
    }

    public void giveItem(ItemStack item){
        if(item == null) {
            sendMessage("Error");
            return;
        }
        this.entityPlayer.inventory.addItemStackToInventory(item);
        this.entityPlayer.inventoryContainer.detectAndSendChanges();
    }

    public void giveItem(String name){
        if(name == null) {
            sendMessage("Error");
            return;
        }
        if(Essentials.itemDB.items.containsKey(name)){
            ItemStack item = Essentials.itemDB.items.get(name);
            giveItem(item);
        }else{
            sendMessage("Error2");
            return;
        }
    }

	public World getWorld() {
		return this.entityPlayer.worldObj;
	}

	public boolean has(String node) {
		if (ForgePermsAPI.permManager == null) return false;
		return ForgePermsAPI.permManager.canAccess(this.entityPlayer.getDisplayName(), getWorld().getWorldInfo().getWorldName(), node);
	}

	public Homes getHome(String name) {
		for (Homes home : this.player.getHomes()) {
			if (home.getName().contains(name)) {
				return home;
			}
		}
		return null;
	}

	public int getHomeCount(){
		return get().getHomes().size();
	}

	public void UpdateName(Players player, String name) {
		this.player.setPlayername(name);
	}

	public void UpdateCoords(int x, int y, int z) {
		this.player.setPosX(x);
		this.player.setPosY(y);
		this.player.setPosZ(z);
	}

	public void UpdateCoords(Location location){
		UpdateCoords((int)location.getX(),(int)location.getY(),(int)location.getZ());
	}

	public void Mute(String reason, int timeout) {
		if (this.player.isMuted()) return;
		this.player.setMuted(true);
		this.player.setMuteReason(reason);
		this.player.setMuteTimeout(timeout);
	}

	public void Warn(String reason) {
		this.player.setWarning(reason);
	}

	public void Ban(String reason, int timeout) {
		if (this.player.isBanned()) return;
		this.player.setBanned(true);
		this.player.setBanReason(reason);
		this.player.setBanTimeout(timeout);
	}

	public void Unban() {
		if (this.player.isBanned()) this.player.setBanned(false);
	}

	public void UpdateBan(String reason, int timeout) {
		if (!this.player.getBanReason().contains(reason)) this.player.setBanReason(reason);
		if (this.player.getBanTimeout() != timeout) this.player.setBanTimeout(timeout);
	}

	public Location getLocation(){
		return new Location(getWorld(),this.entityPlayer.posX,this.entityPlayer.posY,this.entityPlayer.posZ);
	}

	public void save() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(this.player);
		File file = new File(Essentials.players, this.player.getUuid() + ".json");
		if (file.exists() && file.isFile()) file.delete();
		try {
			Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String msg) {
		this.entityPlayer.addChatMessage(new ChatComponentText(msg));
	}

	public boolean sendMessageTo(String name, String msg) {
		EntityPlayerMP target = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(name);
		if (target == null) {
			this.entityPlayer.addChatMessage(new ChatComponentText("Player \"" + name + "\" does not exist"));
			return false;
		}
		this.entityPlayer.addChatMessage(new ChatComponentText("[me -> " + this.player.getPlayerName() + "] " + msg));
		target.addChatMessage(new ChatComponentText("[" + this.player.getPlayerName() + " -> me] " + msg));
		return true;
	}

	public void teleportTo(World world, int x, int y, int z) {
		if (world == null) return;
		if (this.entityPlayer.worldObj.provider.dimensionId != world.provider.dimensionId)
			MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) this.entityPlayer, world.provider.dimensionId);
		this.entityPlayer.setPositionAndUpdate(x, y, z);
		UpdateCoords(x, y, z);
		save();
	}

	public void teleportTo(Location location){
		if(location == null) return;
		teleportTo(location.getWorld(), (int)location.getX(), (int)location.getY(), (int)location.getZ());
	}

	public void teleportTo(EntityPlayer target) {
		if (target == null) return;
		teleportTo(target.worldObj, (int) target.posX, (int) target.posY, (int) target.posZ);
	}

	public float getHealth() {
		return this.entityPlayer.getHealth();
	}

	public void setHealth(int health) {
		this.entityPlayer.setHealth(health);
	}


}
