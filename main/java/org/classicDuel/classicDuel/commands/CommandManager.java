package org.classicDuel.classicDuel.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.classicDuel.classicDuel.ClassicDuel;

public class CommandManager implements CommandExecutor {

    private ClassicDuel instance;

    public CommandManager(ClassicDuel instance) {
        this.instance = instance;

        instance.getCommand("map").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Only players can run commands on this server");
            return true;
        }

        Player player = (Player) commandSender;

        if(command.getName().equalsIgnoreCase("map")) {
            MapNameCommand.getMapName(instance, player);
            return true;
        }

        return true;
    }
}
