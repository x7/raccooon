package org.classicDuel.classicDuel.task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.classicDuel.classicDuel.game.GameManager;
import org.classicDuel.classicDuel.game.GameState;
import org.classicDuel.classicDuel.scoreboards.PregameScoreboard;

import java.util.UUID;

public class StartingCountDownTask extends BukkitRunnable {

    private GameManager gameManager;
    private int time = 10;

    public StartingCountDownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    // chane countdown from 10 > 5?
    @Override
    public void run() {

        PregameScoreboard pregameScoreboard = PregameScoreboard.getPregameScoreboard();
        if(pregameScoreboard == null) {
            // idk sukkkkkkkk fing
        }

        if (time == 10) {
            sendMessageToPlayers(gameManager, ChatColor.YELLOW + "The game starts in " + ChatColor.RED + 10 + ChatColor.YELLOW + " seconds!", time);
            updateStartTime(gameManager, pregameScoreboard, "Starting in 10 seconds");
        }

        if (time <= 5 && time > 0) {
            sendMessageToPlayers(gameManager, ChatColor.YELLOW + "The game starts in " + ChatColor.RED + time + ChatColor.YELLOW + " seconds!", time);
            updateStartTime(gameManager, pregameScoreboard, "Starting in " + time + " seconds");
        }

        if (time == 0) {
            gameManager.setGameState(GameState.PLAYING);
            cancel();
            return;
        }

        time--;
    }

    public static void sendMessageToPlayers(GameManager gameManager, String message, int time) {
        for(UUID uuid : gameManager.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if(player == null) return;
            player.sendMessage(message);

            // if its 0 means don't send 1
            if(time != 0) {
                player.sendTitle(Integer.toString(time), null, 20, 20, 20);
            }
        }
    }

    // idk
    private static void updateStartTime(GameManager gameManager, PregameScoreboard pregameScoreboard, String message) {
        for(UUID uuid : gameManager.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if(player == null) return;
            pregameScoreboard.updateWaitingTeam(player, message);
        }
    }
}
