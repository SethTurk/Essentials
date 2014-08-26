package org.shouthost.essentials.commands;

import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class CommandManager {
    public static ArrayList<String> registeredCommands = new ArrayList<String>();
    protected static ArrayList<Command> commandsList = new ArrayList<Command>();
    private static ServerCommandManager mgr = (ServerCommandManager) MinecraftServer.getServer().getCommandManager();


    public static void registerCommands(List<CommandListener> list) {
        for (CommandListener commands : list) {
            System.out.println("Registering commands in " + commands.getClass().getName());
            for (final Method method : commands.getClass().getDeclaredMethods()) {
                if (!method.isAnnotationPresent(Commands.class)) {
                    continue;
                }
                final Commands cmd = method.getAnnotation(Commands.class);
                System.out.println("Found " + cmd.name() + " in " + commands.getClass().getName());
                if (!registeredCommands.contains(cmd.name())) {
                    registeredCommands.add(cmd.name());
                    commandsList.add(new CommandModel(cmd, method));
                }
            }
        }

        //Now register the whole list to the ServerCommandManager
        for (Command command : commandsList) {
            mgr.registerCommand(command);
        }
    }
}
