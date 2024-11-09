package org.classicDuel.classicDuel.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.classicDuel.classicDuel.ClassicDuel;
import org.classicDuel.classicDuel.game.GameManager;
import org.classicDuel.classicDuel.game.GameState;
import org.classicDuel.classicDuel.game.PlayerManager;
import org.classicDuel.classicDuel.scoreboards.PregameScoreboard;
import org.classicDuel.classicDuel.world.WorldManager;

import java.io.IOException;
import java.util.*;

public class PlayerJoinWorldEvent implements Listener {

    private World world = null;
    private GameManager gameManager;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();
        ClassicDuel instance = ClassicDuel.getInstance();
        player.getInventory().clear();

        // server < world
        if (Bukkit.getServer().getOnlinePlayers().size() == 1) {
            new GameManager(instance);
            gameManager = GameManager.returnGameManager();
            world = WorldManager.pickRandomMap(instance);
        } else {
            gameManager = GameManager.returnGameManager();
        }

        gameManager.addToPlayers(player.getUniqueId());
        gameManager.setGameState(GameState.PREGAME);

        Configuration configuration = instance.getConfig().getDefaults();

        if(configuration == null) {
            System.out.println("No configuration file found.");
            return;
        }

        PlayerManager.teleportPlayerSpawnPoint(instance, gameManager, player, world);

        int maxPlayers = configuration.getInt("server.max-players");
        event.setJoinMessage(
                ChatColor.GOLD + player.getDisplayName() + ChatColor.YELLOW + " Has joined! " +
                "(" + ChatColor.GREEN + gameManager.getPlayers().size() + ChatColor.GREEN + "/" +
                ChatColor.BLUE + maxPlayers + ChatColor.YELLOW + ")!"
        );

        // starting state
        int minPlayers = configuration.getInt("server.min-players");
        if(gameManager.getGameState() == GameState.PREGAME && gameManager.getPlayers().size() == minPlayers) {
            gameManager.setGameState(GameState.STARTING);
        }
    }
}
