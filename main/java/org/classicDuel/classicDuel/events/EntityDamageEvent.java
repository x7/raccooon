package org.classicDuel.classicDuel.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.classicDuel.classicDuel.game.GameManager;
import org.classicDuel.classicDuel.game.GameState;

public class EntityDamageEvent implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        GameManager gameManager = GameManager.returnGameManager();

        if(gameManager.getGameState() != GameState.PLAYING) {
            event.setCancelled(true);
        }
    }
}
