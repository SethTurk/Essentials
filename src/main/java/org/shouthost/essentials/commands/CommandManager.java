package org.shouthost.essentials.commands;

import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import org.shouthost.essentials.core.Essentials;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CommandManager {
    public static ArrayList<String> registeredCommands = new ArrayList<String>();
    public static HashMap<String, CommandStructure> structure = new HashMap<>();
    protected static ArrayList<Command> commandsList = new ArrayList<Command>();
    private static ServerCommandManager mgr = (ServerCommandManager) MinecraftServer.getServer().getCommandManager();

    public static void registerCommands(List<CommandListener> list) {
        int i = 0;
        int page = 0;
        for (CommandListener commands : list) {
            for (final Method method : commands.getClass().getDeclaredMethods()) {
                if (!method.isAnnotationPresent(Commands.class)) {
                    continue;
                }
                final Commands cmd = method.getAnnotation(Commands.class);
                if (cmd.disableInProduction() && Essentials.debug) continue;
                if (!registeredCommands.contains(cmd.name())) {
                    registeredCommands.add(cmd.name());
                    if (i == 5) {
                        i = 0;
                        page++;
                    }
                    structure.put(i + "-page-" + page, new CommandStructure(cmd));
                    commandsList.add(new CommandModel(cmd, method));
                    i++;
                }
            }
        }

        //Now register the whole list to the ServerCommandManager
        for (Command command : commandsList) {
            mgr.registerCommand(command);
        }
    }

    public static class CommandStructure {
        public final String name;
        public final String permission;
        public final String description;
        public final String syntax;
        public final String[] alias;
        public final boolean console;
        public final boolean commandblock;

        public CommandStructure(Commands cmd) {
            this.name = cmd.name();
            this.permission = cmd.permission();
            this.description = cmd.description();
            this.syntax = cmd.syntax();
            this.alias = cmd.alias();
            this.console = cmd.console();
            this.commandblock = cmd.commandblocks();
        }
    }
}
