package org.shouthost.essentials.commands;

import net.minecraft.command.ICommandSender;
import org.shouthost.essentials.entity.Player;
import scala.actors.threadpool.Arrays;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CommandModel extends Command {
    private final Commands cmd;
    //private final Class clazz;
    private final Method method;

    public CommandModel(Commands cmd, Method method) {
        this.cmd = cmd;
        this.method = method;
    }

    @Override
    public List getCommandAliases() {
        return new ArrayList<String>(Arrays.asList(cmd.alias()));
    }

    @Override
    public String getPermissionNode() {
        return cmd.permission();
    }

    @Override
    public boolean canConsoleUseCommand() {
        return cmd.console();
    }

    @Override
    public boolean canCommandBlockUseCommand() {
        return cmd.commandblocks();
    }

    @Override
    public String getCommandUsage(ICommandSender player) {
        return "/" + cmd.name() + " " + cmd.syntax();
    }

    @Override
    public void processCommand(Player player, List<String> args) {
        try {
            method.invoke(null, player, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommandName() {
        return cmd.name();
    }
}
