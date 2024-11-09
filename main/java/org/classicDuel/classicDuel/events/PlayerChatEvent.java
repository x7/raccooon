package org.classicDuel.classicDuel.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat(ChatColor.RED + "[Raccoon] " + ChatColor.GOLD + player.getDisplayName() + " " + ChatColor.WHITE + event.getMessage());
    }
}
