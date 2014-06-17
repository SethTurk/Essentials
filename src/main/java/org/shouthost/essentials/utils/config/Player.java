package org.shouthost.essentials.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import forgeperms.api.ForgePermsAPI;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.json.players.Homes;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.utils.compat.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Player {

    private EntityPlayerMP entityPlayer;
    private Players player;

    public Player(ICommandSender sender) {
        this((EntityPlayerMP) sender);
    }

    public Player(String name) {
        this(MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(name));
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
        File check = new File(Essentials.players, this.entityPlayer.getUniqueID().toString() + ".json");
        if (check.exists()) {
            if (Essentials.playersList.containsKey(this.entityPlayer.getUniqueID()))
                return true;
            loadIntoMemory(check);
            return true;
        }
        return false;
    }

    private void loadIntoMemory(File file) {
        if (file == null) return;
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
        newPlayer.setUuid(this.entityPlayer.getUniqueID().toString().replaceAll("-", ""));
        newPlayer.setWorld(this.entityPlayer.worldObj.provider.dimensionId); //TODO: Fix NullPointerException?
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

	public void setHome(String name){
		Homes home = null;
		if (name.equalsIgnoreCase("home")) {
			home = getHome("home");
			if(home == null){
				home = new Homes();
				home.setName(name);
			}
		} else {
			home = new Homes();
			home.setName(name);
		}
		System.out.println(home == null);
		//home.setWorld(0);
		int x = (int) this.getPosX();
		int y = (int) this.getPosY();
		int z = (int) this.getPosZ();
		if(home != null) {
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

    public void exec(String command) {
        //TODO: make player execute a command ?
        ICommandManager sender = MinecraftServer.getServer().getCommandManager();
        sender.executeCommand((ICommandSender) this.entityPlayer, command);
    }

    public Location getLocation() {
        return new Location(getWorld(), getPosX(), getPosY(), getPosZ());
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
        if (ForgePermsAPI.permManager == null) return false;
        return ForgePermsAPI.permManager.canAccess(this.entityPlayer.getDisplayName(), getWorld().getWorldInfo().getWorldName(), node);
    }

    public Homes getHome(String name) {
        for (Homes home : this.player.getHomes()) {
	        System.out.println(home.getName());
            if (home.getName().contains(name)) {
                return home;
            }
        }
		return null;
    }

    public List<String> getHomeList() {
        ArrayList<String> list = new ArrayList<String>();
        for(Homes home: get().getHomes()){
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
        if(timeout > 0)
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
       Thread saveState = new Thread() {
            @Override
            public void run() {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(p);
	            System.out.println(json);
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
        };
        saveState.start();
        try {
            saveState.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        saveState.interrupt();
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
        sendMessage("[me -> " + this.player.getPlayerName() + "] " + msg);
        target.addChatMessage(new ChatComponentText("[" + this.player.getPlayerName() + " -> me] " + msg));
        return true;
    }

    public void teleportTo(World world, int x, int y, int z) {
        if (world == null) return;
        if (this.entityPlayer.worldObj.provider.dimensionId != world.provider.dimensionId)
            MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) this.entityPlayer, world.provider.dimensionId);
        this.entityPlayer.setPositionAndUpdate(x, y, z);
        updateCoords(x, y, z);
        save();
    }

    public void teleportTo(Location location) {
        if (location == null) return;
        teleportTo(location.getWorld(), (int) location.getX(), (int) location.getY(), (int) location.getZ());
    }

    public void teleportTo(EntityPlayer target) {
        if (target == null) return;
        teleportTo(new Location(target.worldObj, (int) target.posX, (int) target.posY, (int) target.posZ));
    }

    public float getHealth() {
        return this.entityPlayer.getHealth();
    }

    public void setHealth(int health) {
        this.entityPlayer.setHealth(health);
    }


}
