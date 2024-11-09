package org.classicDuel.classicDuel.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.classicDuel.classicDuel.ClassicDuel;
import org.classicDuel.classicDuel.game.GameManager;
import org.classicDuel.classicDuel.game.GameState;
import org.classicDuel.classicDuel.scoreboards.GameScoreboard;

import java.util.UUID;

public class GameCountDownTask extends BukkitRunnable {

    private GameManager gameManager;
    private int time = 480;

    public GameCountDownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        GameScoreboard gameScoreboard = GameScoreboard.getGameScoreboard();

        for(UUID uuid : gameManager.getAlivePlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if(player == null) continue;

            gameScoreboard.updateTimeLeftTeam(player, Integer.toString(time));
        }

        if(time == 0) {
            gameManager.setGameState(GameState.GAMEOVER);
            cancel();
        }

        time--;
    }
}
