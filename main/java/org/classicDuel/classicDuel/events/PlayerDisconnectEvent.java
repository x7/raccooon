package org.classicDuel.classicDuel.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.classicDuel.classicDuel.ClassicDuel;
import org.classicDuel.classicDuel.game.GameManager;
import org.classicDuel.classicDuel.game.GameState;
import org.classicDuel.classicDuel.scoreboards.PregameScoreboard;
import org.classicDuel.classicDuel.task.StartingCountDownTask;
import org.classicDuel.classicDuel.world.WorldManager;

import java.util.Map;
import java.util.UUID;

// fix the world blocks stupid world shit

public class PlayerDisconnectEvent implements Listener {
    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ClassicDuel instance = ClassicDuel.getInstance();

        Configuration configuration = instance.getConfig().getDefaults();

        if(configuration == null) {
            return;
        }

        GameManager gameManager = GameManager.returnGameManager();
        if(gameManager == null) return;

        gameManager.removeFromPlayers(player.getUniqueId());
        PregameScoreboard pregameScoreboard = PregameScoreboard.getPregameScoreboard();

        for(Map.Entry<UUID, Scoreboard> list : pregameScoreboard.getPregameScoreboards().entrySet()) {
            pregameScoreboard.removePregameScoreboard(list.getKey());
        }

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(scoreboard);

        if(gameManager.getPlayers().isEmpty()) {
            WorldManager.unloadWorld(player.getWorld().getName());
            return;
        }

        GameState gameState = gameManager.getGameState();
        String maxPlayers = Integer.toString(configuration.getInt("server.max-players"));

        switch(gameState) {
            case PREGAME:
                event.setQuitMessage(
                        ChatColor.GOLD + player.getDisplayName() + ChatColor.YELLOW + " Has left! " +
                                "(" + ChatColor.GREEN + gameManager.getPlayers().size() + ChatColor.GREEN + "/" +
                                ChatColor.BLUE + maxPlayers + ChatColor.YELLOW + ")!"
                );
            case STARTING:
                event.setQuitMessage(
                        ChatColor.GOLD + player.getDisplayName() + ChatColor.YELLOW + " Has left! " +
                                "(" + ChatColor.GREEN + gameManager.getPlayers().size() + ChatColor.GREEN + "/" +
                                ChatColor.BLUE + maxPlayers + ChatColor.YELLOW + ")!"
                );
                int minPlayers = configuration.getInt("server.min-players");
                if(gameManager.getGameState() == GameState.STARTING && gameManager.getPlayers().size() < minPlayers) {
                    gameManager.setGameState(GameState.PREGAME);
                    gameManager.returnStartingTask().cancel();
                    StartingCountDownTask.sendMessageToPlayers(gameManager, ChatColor.RED + "Countdown cancelled.", 0);
                }
                break;
            case PLAYING:
                gameManager.setGameState(GameState.GAMEOVER);
                gameManager.getPlayers().forEach(uuid -> Bukkit.getPlayer(uuid).sendMessage(ChatColor.WHITE + player.getDisplayName() + " has left the game. game over!"));
                break;
        }
    }
}
