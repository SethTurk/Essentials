package org.shouthost.essentials.commands;

import net.minecraft.server.MinecraftServer;
import org.shouthost.essentials.entity.Player;

public class CommandListener {
    public static Player getPlayerFromString(String name) {
        return new Player(MinecraftServer.getServer().getConfigurationManager().func_152612_a(name));
    }
}
