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
import org.shouthost.essentials.utils.InventoryWatch;
import org.shouthost.essentials.utils.Location;
import org.shouthost.essentials.utils.Warp;
import org.shouthost.essentials.utils.Worlds;
import org.shouthost.permissionforge.api.IHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {
    private Players player;
    private EntityPlayerMP ePlayer;

    public Player(EntityPlayer player) {
        if (player != null) {
            ePlayer = (EntityPlayerMP) player;
            if (player != null) {
                if (exist()) load();
                else create();
            }
        }
    }

    public Player(ICommandSender sender) {
        this((EntityPlayer) sender);
    }

    private boolean exist() {
        //first check to see if file exist
        File check = new File(Essentials.players, ePlayer.getUniqueID().toString().replaceAll("-", "") + ".json");
        if (check.exists()) {
            if (Essentials.playersList.containsKey(ePlayer.getUniqueID())) return true;
            try {
                return loadIntoMemory(check);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean loadIntoMemory(File file) throws IOException {
        if (file == null) return false;
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader(file));
        this.player = gson.fromJson(br, Players.class);
        if (!Essentials.playersList.containsKey(ePlayer.getPersistentID())) {
            Essentials.playersList.put(ePlayer.getPersistentID(), this.player);
        }
        if (!Essentials.playerList.containsKey(ePlayer.getPersistentID())) {
            Essentials.playerList.put(ePlayer.getPersistentID(), this);
        }
        br.close();
        return true;
    }

    private void load() {
        this.player = Essentials.playersList.get(ePlayer.getUniqueID());
    }

    private void create() {
        Players newPlayer = new Players();
        newPlayer.setPlayername(ePlayer.getDisplayName());
        newPlayer.setUuid(ePlayer.getUniqueID().toString().replaceAll("-", ""));
        newPlayer.setWorld(ePlayer.worldObj.provider.dimensionId);
        newPlayer.setPosX(ePlayer.posX);
        newPlayer.setPosY(ePlayer.posY);
        newPlayer.setPosZ(ePlayer.posZ);
        newPlayer.setBanned(false);
        newPlayer.setMuted(false);
        newPlayer.setJailed(false);
        this.player = newPlayer;
        Essentials.playersList.put(ePlayer.getUniqueID(), newPlayer);
    }

    public String getPlayerName() {
        return ePlayer.getDisplayName();
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
            ePlayer.playerNetServerHandler.kickPlayerFromServer("Kicked from the server");
        } else {
            ePlayer.playerNetServerHandler.kickPlayerFromServer(reason);
        }
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
    }

    public void exec(String command) {
        //TODO: make player execute a command ?
        MinecraftServer.getServer().getCommandManager().executeCommand((ICommandSender) this, command);
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

    //@Deprecated
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
        if (isDead()) return;
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
        ePlayer.addChatMessage(new ChatComponentText(msg));
    }

    private EntityPlayerMP resolveName(String name) {
        return MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);
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

        Location from = getLocation();
        Location to = loc;
        teleportTo(to);

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

    public void fly(float speed) {
        if (!Essentials.isFlying.contains(this)) {
            Essentials.isFlying.add(this);
            ePlayer.capabilities.allowFlying = true;
        } else {
            Essentials.isFlying.remove(this);
            ePlayer.capabilities.allowFlying = false;
            ePlayer.capabilities.isFlying = false;
        }

        ePlayer.capabilities.setFlySpeed(speed == 0 ? 2 : speed);
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
}
