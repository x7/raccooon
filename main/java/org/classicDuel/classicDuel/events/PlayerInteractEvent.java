package org.classicDuel.classicDuel.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.classicDuel.classicDuel.game.PlayerManager;

public class PlayerInteractEvent implements Listener {
    @EventHandler
    public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
        Action action = event.getAction();

        if(action.equals(Action.LEFT_CLICK_BLOCK)) {
            event.setCancelled(true);
        }
    }
}
