package org.shouthost.essentials.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import forgeperms.api.ForgePermsAPI;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.json.players.Homes;
import org.shouthost.essentials.json.players.Players;

import java.io.*;

public class Player {

	private EntityPlayer entityPlayer;
	private Players player;

	public Player(EntityPlayerMP playerMP){
		this((EntityPlayer)playerMP);
	}

	public Player(ICommandSender sender){
		this((EntityPlayer) sender);
	}

	public Player(EntityPlayer player){
		this.entityPlayer = player;
		if(exist()){
			load();
		}else{
			create();
		}
	}

	private boolean exist(){
		if(Essentials.playersList.containsKey(this.entityPlayer.getUniqueID()))
			return true;
		return false;
	}

	private void load(){
		this.player = Essentials.playersList.get(this.entityPlayer.getUniqueID());
	}

	private void create(){
		Players newPlayer = new Players();
		newPlayer.setPlayername(this.entityPlayer.getDisplayName());
		newPlayer.setUuid(this.entityPlayer.getUniqueID().toString());
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

	public Players get(){
		return this.player;
	}

	public void setHealth(int health){
		this.entityPlayer.setHealth(health);
	}

	public void setHome(String name, int x, int y, int z){
		Homes home = null;
		if(name.contains("home")){
			home = getHome("home");
		}else {
			home = new Homes();
			home.setName(name);
		}
		home.setWorld(this.entityPlayer.worldObj.provider.dimensionId);
		home.setX(x);
		home.setY(y);
		home.setZ(z);
		this.player.setHome(home);
	}

	public World getWorld(){
		return this.entityPlayer.worldObj;
	}

	public boolean has(String node){
		if(ForgePermsAPI.permManager == null) return false;
		return ForgePermsAPI.permManager.canAccess(this.entityPlayer.getDisplayName(), getWorld().getWorldInfo().getWorldName(), node);
	}

	public Homes getHome(String name){
		for(Homes home: this.player.getHomes()){
			System.out.println(""+home.getName().contains(name));
			if(home.getName().contains(name)){
				return home;
			}
		}
		return null;
	}

	public void UpdateName(Players player, String name){
		this.player.setPlayername(name);
	}

	public void UpdateCoords(int x, int y, int z){
		this.player.setPosX(x);
		this.player.setPosY(y);
		this.player.setPosZ(z);
	}

	public void Mute(String reason, int timeout){
		if(this.player.isMuted()) return;
		this.player.setMuted(true);
		this.player.setMuteReason(reason);
		this.player.setMuteTimeout(timeout);
	}

	public void Warn(String reason){
		this.player.setWarning(reason);
	}

	public void Ban(String reason, int timeout){
		if(this.player.isBanned() || MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getBannedList().containsKey(this.entityPlayer)) return;
		this.player.setBanned(true);
		this.player.setBanReason(reason);
		this.player.setBanTimeout(timeout);

	}

	public void Unban(){
		if(this.player.isBanned()) this.player.setBanned(false);
	}

	public void UpdateBan(String reason, int timeout){
		if(!this.player.getBanReason().contains(reason)) this.player.setBanReason(reason);
		if(this.player.getBanTimeout() != timeout) this.player.setBanTimeout(timeout);
	}

	public void save(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(this.player);
		System.out.println(json);
		File file = new File(Essentials.players, this.player.getUuid()+".json");
		if(file.exists() && file.isFile()) file.delete();
		try {
				Writer writer = new OutputStreamWriter(new FileOutputStream(file) , "UTF-8");
				writer.write(json);
				writer.close();
		} catch (IOException e) {
				e.printStackTrace();
		}
	}

	public void sendMessage(String msg){
		this.entityPlayer.addChatMessage(new ChatComponentText(msg));
	}

	public boolean sendMessageTo(String name, String msg){
		EntityPlayerMP target = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(name);
		if(target == null){
			this.entityPlayer.addChatMessage(new ChatComponentText("Player \""+name+"\" does not exist"));
			return false;
		}
		this.entityPlayer.addChatMessage(new ChatComponentText("[me -> "+this.player.getPlayerName()+"] "+msg));
		target.addChatMessage(new ChatComponentText("["+this.player.getPlayerName()+" -> me] "+msg));
		return true;
	}

	public void teleportTo(World world, int x, int y, int z){
		if(world == null) return;
		if(this.entityPlayer.worldObj.provider.dimensionId != world.provider.dimensionId) MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) this.entityPlayer, world.provider.dimensionId);
		this.entityPlayer.setPositionAndUpdate(x,y,z);
		UpdateCoords(x,y,z);
		save();
	}

	public void teleportTo(EntityPlayer target){
		if(target == null) return;
		teleportTo(target.worldObj, (int)target.posX, (int)target.posY, (int)target.posZ);
	}

	public float getHealth(){
		return this.entityPlayer.getHealth();
	}


}
