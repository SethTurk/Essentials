package org.shouthost.essentials.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
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
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.json.players.Homes;
import org.shouthost.essentials.json.players.Players;
import org.shouthost.essentials.json.players.PowerTools;
import org.shouthost.essentials.utils.*;
import org.shouthost.permissionforge.api.IHandler;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Player {
    private Players player;
    private EntityPlayerMP ePlayer;
    private UUID uuid;

    public Player(EntityPlayer player) {
        if (player != null) {
            ePlayer = (EntityPlayerMP) player;
            createOrLoadPlayer();
        }
    }

    public Player(UUID uuid) {
        if (uuid != null) {
            this.uuid = uuid;
            createOrLoadPlayer(this.uuid);
        }
    }

    public Player(ICommandSender sender) {
        this((EntityPlayer) sender);
    }

    /**
     * When a player login to the server for the first time
     * we want to create the player file which will include
     * the basics information for this mod. If the player
     * already exist, we want to check to see if the file
     * exist that is marked with the player UUID and if it
     * does to load that file in memory.
     */
    private void createOrLoadPlayer() {
        //First check to see if player exist in memory
        if (Essentials.playersList.getIfPresent(ePlayer.getUniqueID()) != null) {
            player = Essentials.playersList.getIfPresent(ePlayer.getUniqueID());
            return;
        }
        //check and see if player file exist
        if (FileUtils.doesFileExist(new File(Essentials.players, ePlayer.getUniqueID().toString() + ".json"))) {
            String br = FileUtils.loadFile(new File(Essentials.players, ePlayer.getUniqueID().toString() + ".json"));
            player = new Gson().fromJson(br, Players.class);

            if (Essentials.playersList.getIfPresent(ePlayer.getUniqueID()) == null) {
                Essentials.playersList.put(ePlayer.getPersistentID(), player);
            }
            if (Essentials.playerList.getIfPresent(ePlayer.getUniqueID()) == null) {
                Essentials.playerList.put(ePlayer.getPersistentID(), this);
            }
            return;
        }
        //If all else fails then create new player


        player = new Players();
        player.setPlayername(ePlayer.getDisplayName());
        player.setUuid(ePlayer.getUniqueID().toString());
        player.setWorld(ePlayer.worldObj.provider.dimensionId);
        player.setPosX(ePlayer.posX);
        player.setPosY(ePlayer.posY);
        player.setPosZ(ePlayer.posZ);
        Essentials.playersList.put(ePlayer.getUniqueID(), player);
    }

    private void createOrLoadPlayer(UUID uuid) {
        //TODO: Create or load a player that is offline (or just by uuid)
        if (Essentials.playersList.getIfPresent(uuid) != null) {
            player = Essentials.playersList.getIfPresent(uuid);
            return;
        }
        //check and see if player file exist
        if (FileUtils.doesFileExist(new File(Essentials.players, uuid.toString() + ".json"))) {
            String br = FileUtils.loadFile(new File(Essentials.players, uuid.toString() + ".json"));
            player = new Gson().fromJson(br, Players.class);

            if (Essentials.playersList.getIfPresent(uuid) == null) {
                Essentials.playersList.put(uuid, player);
            }
            if (Essentials.playerList.getIfPresent(uuid) == null) {
                Essentials.playerList.put(uuid, this);
            }
            return;
        }
        //If all else fails then create new player


        player = new Players();
        player.setUuid(uuid.toString());
        Essentials.playersList.put(uuid, player);
    }

    public String getPlayerName() {
        return ePlayer.getDisplayName();
    }

    //TODO finish implementing everything so we dont have to call this directly
    protected Players get() {
        return this.player;
    }

    public void setHome(String name) {
        Homes home = null;//(name.equalsIgnoreCase("home") ? (getHome("home") != null ? getHome("Home") : new Homes().setName("home")) : new Homes());
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
        home.setWorld(getWorld().provider.dimensionId);
        int x = (int) this.getPosX();
        int y = (int) this.getPosY();
        int z = (int) this.getPosZ();
        home.setX(x);
        home.setY(y);
        home.setZ(z);
        this.player.setHome(home);

        save();
    }

    public boolean isMuted() {
        return this.player.isMuted();
    }

    public boolean isBanned() {
        return this.player.isBanned();
    }

    public boolean isJailed() {
        return this.player.getJailed();
    }

    public void setHome(String name, int x, int y, int z) {
        Homes home = (name.equalsIgnoreCase("home") ? getHome("home") : new Homes());
        home.setName(name);
        home.setWorld(getWorld().provider.dimensionId);
        home.setX(x);
        home.setY(y);
        home.setZ(z);
        this.player.setHome(home);
        save();
    }

    public void kick(String reason) {
        ePlayer.playerNetServerHandler.kickPlayerFromServer(reason != null ? reason : "Kicked from the server");
    }

    public void sendPacket(Packet packet) {
        ePlayer.playerNetServerHandler.sendPacket(packet);
    }

    public WorldSettings.GameType getGameMode() {
        return ePlayer.theItemInWorldManager.getGameType();
    }

    public void setGameMode(WorldSettings.GameType type) {
        ePlayer.theItemInWorldManager.setGameType(type);
    }

    public void delhome(Homes home) {
        if (getHome(home.getName()) != null) this.player.getHomes().remove(home);
        save();
    }

    public boolean doesPowerToolExist(ItemStack item) {
        for (PowerTools pt : this.player.getPtools()) {
            if (pt.getItemName().equalsIgnoreCase(item.getDisplayName())) {
                return true;
            }
        }
        return false;
    }

    public PowerTools getPowerTool(ItemStack item) {
        for (PowerTools pt : this.player.getPtools()) {
            if (pt.getItemName().equalsIgnoreCase(item.getDisplayName())) {
                return pt;
            }
        }
        return null;
    }

    public boolean doesPowerToolExist(String name) {
        for (PowerTools pt : this.player.getPtools()) {
            if (pt.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean removePowerTool(PowerTools pt) {
        if (doesPowerToolExist(pt.getName()) && this.player.getPtools().remove(pt)) {
            save();
            return true;
        }
        return false;
    }

    public PowerTools getPowerTool(String name) {
        for (PowerTools pt : this.player.getPtools()) {
            if (pt.getName().equalsIgnoreCase(name)) {
                return pt;
            }
        }
        return null;
    }

    public boolean addPowerTool(String name, ItemStack item, String command) {
        if ((item == null || command == null) || doesPowerToolExist(name)) return false;
        PowerTools pt = new PowerTools();
        pt.setItemName(item.getDisplayName());
        pt.setCommand(command);
        this.player.setPtools(pt);
        save();
        return true;
    }

    public void exec(String command) {
        MinecraftServer.getServer().getCommandManager().executeCommand(ePlayer, command);
    }

    public Location getLocation() {
        return new Location(getWorld(), getPosX(), getPosY(), getPosZ(), ePlayer.getRotationYawHead(), ePlayer.getEyeHeight());
    }

    public double getPosX() {
        return ePlayer.posX;
    }

    public double getPosY() {
        return ePlayer.posY;
    }

    public double getPosZ() {
        return ePlayer.posZ;
    }

    public boolean isDead() {
        return ePlayer.isDead;
    }

    public EntityPlayerMP getPlayer() {
        return ePlayer;
    }

    public void viewInventory(Player target) {
        //TODO: Add permission nodes to check to see if player if view and or modify inventory of its target
        if (target == null || target.isDead()) {
            target.getPlayer().closeScreen();
            return;
        }
        ePlayer.displayGUIChest(new InventoryWatch(this, target));
    }

    public void giveItem(ItemStack item) {
        if (item == null) {
            sendMessage("Error");
            return;
        }

        EntityItem i = ePlayer.dropPlayerItemWithRandomChoice(item, false);
        i.delayBeforeCanPickup = 0;
        i.func_145797_a(ePlayer.getDisplayName());

    }

    public void suicide() {
        //if (isDead()) return;
        ePlayer.setDead();
    }

    public World getWorld() {
        return ePlayer.worldObj;
    }

    public boolean hasPermission(String node) {
        return IHandler.permission.hasPermission(ePlayer, getWorld().getWorldInfo().getWorldName(), node);
    }

    public Homes getHome(String name) {
        for (Homes home : this.player.getHomes()) {
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
        save();
    }

    public void updateCoords(int x, int y, int z) {
        this.player.setPosX(x);
        this.player.setPosY(y);
        this.player.setPosZ(z);
        save();
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

    public String getMuteReason() {
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
        save();
    }

    public void unban() {
        if (!this.player.isBanned()) return;
        this.player.setBanned(false);
        save();
    }

    public void updateBan(String reason, int timeout) {
        if (!this.player.getBanReason().equalsIgnoreCase(reason)) this.player.setBanReason(reason);
        if (this.player.getBanTimeout() != timeout) this.player.setBanTimeout(timeout);
        save();
    }

    public void setHealth(float val) {
        ePlayer.setHealth(val);
    }

    public void save() {
        final Players p = this.player;
        Essentials.schedule.scheduleSyncTask(new Runnable() {
            @Override
            public void run() {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(p);
                File file = new File(Essentials.players, p.getUuid() + ".json");
                FileUtils.writeToFile(file, json);
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
        ePlayer.addChatMessage(new ChatComponentText(msg));
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
    }

    public void strike(Player target) {
        strike(target.getLocation());
    }

    public void strike(Location loc) {
        loc.getWorld().addWeatherEffect(new EntityLightningBolt(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
    }

    public void strike() {
        strike(this);
    }

    public int refresh(int r) {
        WorldServer worldServer = (WorldServer) ePlayer.worldObj;
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
        if (ePlayer.getHeldItem() != null)
            ePlayer.updateHeldItem();
        if (ePlayer.isRiding()) {
            ePlayer.updateRidden();
            ePlayer.updateRiderPosition();
        }
        return c.size();
    }

    public void teleportTo(World world, int x, int y, int z, float yaw, float pitch) {
        if (world == null) return;
        if (ePlayer.worldObj.provider.dimensionId != world.provider.dimensionId)
            MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(ePlayer, world.provider.dimensionId);
        ePlayer.setPositionAndUpdate(x, y, z);
        ePlayer.setPositionAndRotation(x, y, z, yaw, pitch);
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

        if (ePlayer.getHealth() == 0 || isDead()) return;
        if (ePlayer.ridingEntity != null || ePlayer.isRiding()) return;
        teleportTo(loc);

    }

    public void warpTo(String name) {
        for (Warp warp : Essentials.warpList)
            if (warp.name.equalsIgnoreCase(name)) teleport(warp.loc);
    }

    public InetSocketAddress getAddress() {
        if (ePlayer.isClientWorld()) return null;
        SocketAddress addr = ePlayer.playerNetServerHandler.netManager.getSocketAddress();
        if (addr instanceof InetSocketAddress) return (InetSocketAddress) addr;
        return null;
    }

    public void sendBlockChange(Location loc, Block block) {
        if (loc.getWorld().isRemote) return;
        if (ePlayer.playerNetServerHandler.netManager == null) return;
        S23PacketBlockChange change = new S23PacketBlockChange(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld());
        change.field_148883_d = block;
        change.field_148884_e = new Worlds(loc.getWorld()).getBlockMetadata(loc);
        sendPacket(change);
    }

    public void sendSound(Location loc, String name, float volume, float pitch) {
        if (loc.getWorld().isRemote) return;
        if (ePlayer.playerNetServerHandler.netManager == null) return;
        S29PacketSoundEffect sound = new S29PacketSoundEffect(name, loc.getX(), loc.getY(), loc.getZ(), volume, pitch);
        sendPacket(sound);
    }

    public void sendEffect(String particle, Location loc, double width, double motionX, double motionZ) {
        ePlayer.worldObj.spawnParticle(particle, loc.getX(), loc.getY(), loc.getZ(), width, motionX, motionZ);
    }

    public boolean isFlying() {
        return ePlayer.capabilities.isFlying;
    }

    public boolean canFly() {
        return ePlayer.capabilities.allowFlying;
    }

    //FIXME: find workaround for fly speed
    public void fly(float speed) {
        if (!ePlayer.capabilities.allowFlying || !ePlayer.capabilities.isFlying) {
            ePlayer.capabilities.allowFlying = true;
            ePlayer.capabilities.isFlying = true;
            //ePlayer.capabilities.setFlySpeed(speed);
        } else {
            ePlayer.capabilities.allowFlying = false;
            ePlayer.capabilities.isFlying = false;
            //ePlayer.capabilities.setFlySpeed(0);
        }
        ePlayer.sendPlayerAbilities();
    }

    public boolean isInvincible() {
        return ePlayer.capabilities.disableDamage;
    }

    public void god() {
        if (!isInvincible()) {
            ePlayer.capabilities.disableDamage = true;
        } else {
            ePlayer.capabilities.disableDamage = false;
        }
        ePlayer.sendPlayerAbilities();
    }

    public Location getEyeLocation(boolean air) {
        MovingObjectPosition loc = ePlayer.rayTrace(100, 1.5F);
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

    public void setFire(int i) {
        ePlayer.setFire(i);
    }

    public void removePotionEffect(int id) {
        ePlayer.removePotionEffect(id);
    }

    public void addPotionEffect(PotionEffect potionEffect) {
        ePlayer.addPotionEffect(potionEffect);
    }

    public UUID getUniqueID() {
        return ePlayer.getUniqueID();
    }

    public void sendErrorMessage(String message) {
        sendMessage(EnumChatFormatting.RED + message);
    }

    public void sendSuccessMessage(String message) {
        sendMessage(EnumChatFormatting.GREEN + message);
    }
}
