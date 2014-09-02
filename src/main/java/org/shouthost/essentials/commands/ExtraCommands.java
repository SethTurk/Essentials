package org.shouthost.essentials.commands;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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

    @Commands(name = "book",
            permission = "essentials.command.book",
            description = "Allowing to create or edit a book",
            syntax = "",
            disableInProduction = true)
    public static void book(Player player, List<String> args) {
        if (args.isEmpty()) {
            player.sendErrorMessage("/book [create|edit] [author]");
        } else if (args.size() == 1 && args.get(0).equalsIgnoreCase("edit")) {
            if (player.getPlayer().getCurrentEquippedItem() == null || player.getPlayer().getCurrentEquippedItem().getItem() != Items.written_book) {
                player.sendErrorMessage("You do not have a written book equiped");
                return;
            } else if (player.getPlayer().getCurrentEquippedItem() != null && player.getPlayer().getCurrentEquippedItem().getItem() == Items.written_book) {
                ItemStack book = player.getPlayer().getCurrentEquippedItem();
                player.getPlayer().displayGUIBook(book);
            }
        }
    }


    @Commands(name = "nick",
            permission = "essentials.command.nick",
            description = "Give a player a nickname",
            alias = {"nickname"},
            syntax = "[player | nick] [nick]",
            disableInProduction = true)
    public static void nick(Player player, List<String> args) {
        if (!args.isEmpty()) {
            if (args.size() == 2) {
                Player target = getPlayerFromString(args.get(0));
                if (target == null) {
                    player.sendErrorMessage("Invalid player");
                } else {
                    target.setNickname(args.get(1));
                    player.sendSuccessMessage("Nickname successfully been applied");
                }
            } else if (args.size() == 1) {
                Player target = getPlayerFromString(args.get(0));
                //this will remove the nick name from the player profile
                if (target == null) {
                    player.sendErrorMessage("Invalid player");
                } else {
                    if (target.getNickname() == target.getPlayerName()) {
                        player.sendErrorMessage("Player does not have nickname");
                        return;
                    }
                    target.setNickname(null);
                    player.sendSuccessMessage("Nickname removed");
                }
            }
        }
    }

	@Commands(name = "feed",
			  permission = "essentials.command.feed",
			  description = "Feed a player")
	public static void feed(Player player, List<String> args) {
		if(!args.isEmpty()) {
			Player targetPlayer = getPlayerFromString(args.get(0));
			targetPlayer.setHunger(20);
			player.sendSuccessMessage("Succesfully fed " + targetPlayer.getNickname());
			targetPlayer.sendSuccessMessage("" + player.getNickname() + " succesfully fed you.");//TODO:(Optional) Make an option to show a message to target
		}else{
			player.setHunger(20);
			player.sendSuccessMessage("You were succesfully fed.");
		}
	}

	@Commands(name = "fireball",
			  permission = "essentials.command.fireball",
			  description = "Spit a fireball")
	public static void fireball(Player player, List<String> args) {
		EntityFireball entity = null;
		if(!args.isEmpty()) {
			if(args.get(0).equalsIgnoreCase("small") || args.get(0).equalsIgnoreCase("tiny") || args.get(0).equalsIgnoreCase("baby")) {
				entity = new EntitySmallFireball(player.getWorld());
			}else if(args.get(0).equalsIgnoreCase("large") || args.get(0).equalsIgnoreCase("big") || args.get(0).equalsIgnoreCase("giant")) {
				entity = new EntityLargeFireball(player.getWorld());
			}else{
				player.sendErrorMessage("Specify a size.");
				return;
			}
		}else{
			entity = new EntityLargeFireball(player.getWorld());

		}
		entity.setPosition(player.getPosX(), player.getPosY() + 1, player.getPosZ());
		Vector dir = player.getLocation().getDirection();
		entity.setVelocity(dir.getX(), dir.multiply(2).getY(), dir.getZ());
		player.getWorld().spawnEntityInWorld(entity);
	}

	@Commands(name = "xp",
			  permission = "essentials.commands.xp",
			  description = "Give a player xp",
			  alias = {"exp"})
	public static void xp(Player player, List<String> args) {
		if(!args.isEmpty()) {
			if(args.size() == 2) {
				if(args.get(0).equalsIgnoreCase("set")) {
					player.setXP(Integer.parseInt(args.get(1)));
					player.sendSuccessMessage("Succesfully set your xp to" + args.get(1) + ".");
				}else if(args.get(0).equalsIgnoreCase("remove")) {
					player.setXP(player.getXP() - Integer.parseInt(args.get(1)));
					player.sendSuccessMessage("Succesfully removed " + args.get(1) + " levels.");
				}
			}else if(args.size() == 3) {
				if(args.get(0).equalsIgnoreCase("set")) {
					Player targetPlayer = getPlayerFromString(args.get(3));
					targetPlayer.setXP(Integer.parseInt(args.get(1)));
					targetPlayer.sendSuccessMessage("Succesfully set your xp to" + args.get(1) + ".");
				}else if(args.get(0).equalsIgnoreCase("remove")) {
					Player targetPlayer = getPlayerFromString(args.get(3));
					targetPlayer.setXP(targetPlayer.getXP() - Integer.parseInt(args.get(1)));
					player.sendSuccessMessage("Succesfully removed " + args.get(1) + " levels from " + targetPlayer.getNickname() + ".");
				}
			}
		}
	}

	@Commands(name = "workbench",
			  permission = "essentials.commands.workbench",
			  description = "Opens a workbench interface")
	public static void workbench(Player player, List<String> args) {
		player.openWorkbench();
	}

	@Commands(name = "whois",
			  permission = "essentials.commands.whois",
			  description = "Provides information of a user",
			  alias = "lookup")
	public static void whois(Player player, List<String> args) {
		if(!args.isEmpty()) {
			String targetPlayer = args.get(0);

		}
	}

	@Commands(name = "repair",
			  permission = "essentials.commands.repair",
			  description = "Repairs held item.")
	public static void repair(Player player, List<String> args) {
		player.setHeldItemDurability(0);
	}

	public Player getPlayerFromNickname(String nick) {
//		Player player = get;
		return null;
	}
}
