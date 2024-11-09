package org.classicDuel.classicDuel.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;
import org.classicDuel.classicDuel.ClassicDuel;

public class MapNameCommand {
    public static void getMapName(ClassicDuel instance, Player player) {
        String mapName = instance.getConfig().getString("server.current-map-name");

        // map name not yet been registered so idk
        if(mapName.equalsIgnoreCase("no map name")) {
            player.sendMessage(ChatColor.RED + "No map name found.");
            return;
        }

        player.sendMessage(ChatColor.WHITE + "You are currently playing on " + ChatColor.GREEN + mapName);
    }
}
