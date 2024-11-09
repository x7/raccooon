package org.classicDuel.classicDuel.state;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.classicDuel.classicDuel.ClassicDuel;
import org.classicDuel.classicDuel.game.GameManager;
import org.classicDuel.classicDuel.game.PlayerManager;
import org.classicDuel.classicDuel.scoreboards.GameScoreboard;
import org.classicDuel.classicDuel.task.PlayerHealthTask;

import java.util.ArrayList;
import java.util.UUID;

// Give items to players
// Scoreboard
public class Playing {

    private static GameScoreboard gameScoreboard = null;

    public static void playingState(GameManager gameManager) {
        if(gameScoreboard == null) {
            gameScoreboard = new GameScoreboard(gameManager);
        }

        gameManager.returnStartingTask().cancel();

        for(UUID uuid : gameManager.getPlayers()) {
            gameManager.addToAlivePlayers(uuid);
            Player player = Bukkit.getPlayer(uuid);
            if(player == null) continue;

            sendStartMessage(player, gameManager);
            PlayerManager.teleportPlayerSpawnPoint(ClassicDuel.getInstance(), gameManager, player, player.getWorld());
            PlayerManager.giveItems(player);

            gameScoreboard.createScoreboard(player);
            Player player1 = Bukkit.getPlayer(gameManager.returnOpponent(player.getUniqueId()));
            new PlayerHealthTask(player, player1).runTaskTimer(ClassicDuel.getInstance(), 0, 20);
        }
    }

    private static void sendStartMessage(Player player, GameManager gameManager) {
        UUID opponentUUID = gameManager.returnOpponent(player.getUniqueId());
        Player opponent = Bukkit.getPlayer(opponentUUID);
        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "                               Classic Duel");
        player.sendMessage(" ");
        player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "                     Eliminate your opponents!");
        player.sendMessage(" ");
        player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "                             Opponent: " + ChatColor.GOLD + opponent.getDisplayName());
        player.sendMessage(" ");
        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
    }
}
