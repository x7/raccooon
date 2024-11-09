package org.classicDuel.classicDuel.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.classicDuel.classicDuel.game.GameManager;
import org.classicDuel.classicDuel.game.GameState;

import java.util.UUID;

public class EntityDeathEvent implements Listener {
    @EventHandler
    public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent event) {
        GameManager gameManager = GameManager.returnGameManager();
        if(gameManager.getGameState() != GameState.PLAYING) return;

        Entity entity = event.getEntity();
        Player deadPlayer = (Player) entity;

        if(!(entity.getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
        if(!(damageEvent.getDamager() instanceof Player)) return;

        Player player = (Player) damageEvent.getDamager();
        gameManager.addWinner(player.getUniqueId());

        for(UUID uuid : gameManager.getPlayers()) {
            Player player1 = Bukkit.getPlayer(uuid);
            if(player1 == null) continue;

            player1.sendMessage(ChatColor.WHITE + deadPlayer.getDisplayName() + " was killed by " + player.getDisplayName());
        }

        player.sendTitle(ChatColor.GREEN + "WINNER!", null, 20, 40, 20);
        deadPlayer.sendTitle(ChatColor.RED + "GAME OVER", null, 20, 40, 20);

        gameManager.setGameState(GameState.GAMEOVER);
    }
}
