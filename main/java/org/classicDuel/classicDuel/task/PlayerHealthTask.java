package org.classicDuel.classicDuel.task;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.classicDuel.classicDuel.scoreboards.GameScoreboard;

import java.util.UUID;

public class PlayerHealthTask extends BukkitRunnable {

    private Player player;
    private Player opponent;

    public PlayerHealthTask(Player player, Player opponent) {
        this.player = player;
        this.opponent = opponent;
    }

    @Override
    public void run() {
        GameScoreboard gameScoreboard = GameScoreboard.getGameScoreboard();
        gameScoreboard.updateOpponentHealthTeam(player, Double.toString(opponent.getHealth()));
    }
}
