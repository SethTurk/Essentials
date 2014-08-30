package org.shouthost.essentials.commands;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import org.shouthost.essentials.core.Essentials;
import org.shouthost.essentials.entity.Player;
import org.shouthost.essentials.tasks.EntityBombTask;
import org.shouthost.essentials.utils.Vector;

import java.util.List;

public class ExtraCommands extends CommandListener {

    @Commands(name = "smite",
            permission = "essentials.command.smite",
            syntax = "[player]",
            alias = {"lightning", "thor", "strike"},
            description = "Strike a player or entity with lightning",
            commandblocks = true,
            disableInProduction = true)
    public static void smite(Player player, List<String> args) {
        if (!args.isEmpty()) {
            Player target = getPlayerFromString(args.get(0));
            player.strike(target.getLocation());
        } else {
            player.strike(player.getEyeLocation(false));
        }
    }

    @Commands(name = "entitycannon",
            permission = "essentials.command.cannon",
            description = "Spawn in a random entity with a blast",
            disableInProduction = true)
    //FIXME: Resolve CME. Might just push this onto the mainthread
    public static void entitycannon(final Player player, List<String> args) {
        Entity entity = null;
        if (args.isEmpty() || args.get(0).equalsIgnoreCase("cat")) {
            entity = new EntityOcelot(player.getWorld());
            ((EntityOcelot) entity).setTamed(true);
        } else if (args.get(0).equalsIgnoreCase("zombie")) {
            entity = new EntityZombie(player.getWorld());
        } else if (args.get(0).equalsIgnoreCase("creeper")) {
            entity = new EntityCreeper(player.getWorld());
        } else if (args.get(0).equalsIgnoreCase("dragon")) {
            entity = new EntityDragon(player.getWorld());
        }
        entity.setPosition(player.getPosX(), player.getPosY() + 1, player.getPosZ());
        Vector dir = player.getLocation().getDirection();
        entity.setVelocity(dir.getX(), dir.multiply(2).getY(), dir.getZ());
        player.getWorld().spawnEntityInWorld(entity);

        Essentials.schedule.scheduleAsyncTaskDelay(new EntityBombTask(entity), 2L);
    }


    @Commands(name = "summon",
            permission = "essentials.command.summon",
            description = "Spawn in an entity",
            alias = {"spawnentity"},
            disableInProduction = true)
    public static void summon(Player player, List<String> args) {
        if (args.isEmpty()) {
            player.sendMessage(EnumChatFormatting.RED + "/summon <entityname>");
        } else if (args.size() == 1) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            boolean flag = false;
            String name = args.get(0);
            nbttagcompound.setString("id", name);
            Entity entity = EntityList.createEntityFromNBT(nbttagcompound, player.getWorld());
            if (entity == null) {
                player.sendErrorMessage("Error spawning Entity");
            } else {
                //TODO:finish
            }
        }
    }

    @Commands(name = "fly",
            permission = "essentials.command.fly",
            syntax = "[player]",
            description = "Enable a player to fly")
    public static void fly(Player player, List<String> args) {
        if (args.isEmpty()) {
            player.fly(0.1f);
            player.sendSuccessMessage("Fly mode " + (player.canFly() ? "enabled" : "disabled"));
        } else if (args.size() == 1) {
            Player target = getPlayerFromString(args.get(0));
            if (target == null) {
                player.sendErrorMessage("Player is not online");
            } else {
                target.fly(0.1f);
                target.sendSuccessMessage("Fly mode " + (player.canFly() ? "enabled" : "disabled"));
            }
        }
    }

    @Commands(name = "god",
            permission = "essentials.command.god",
            description = "Make player invincible")
    public static void god(Player player, List<String> args) {
        player.god();
        player.sendSuccessMessage("God mode " + (player.isInvincible() ? "enabled" : "disabled"));
    }
}
