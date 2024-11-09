package org.classicDuel.classicDuel.state;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.classicDuel.classicDuel.game.GameManager;
import org.classicDuel.classicDuel.scoreboards.PregameScoreboard;

import java.util.UUID;

// Handle pregame shit like scoreboard bla bla bla
public class Pregame {

    private static PregameScoreboard pregameScoreboard = null;

    // recall this everytime someone joins the world ? _ ? yup fuck it
    public static void pregameState(GameManager gameManager) {
        if(pregameScoreboard == null) {
            pregameScoreboard = new PregameScoreboard(gameManager);
        }

        for(UUID uuid : gameManager.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if(player == null) return;

            if(pregameScoreboard.getPregameScoreboards().containsKey(player.getUniqueId())) {
                pregameScoreboard.updatePlayersTeam(player);
                continue;
            }

            pregameScoreboard.createScoreboard(player);
        }
    }

    public static PregameScoreboard getPregameScoreboard() {
        return pregameScoreboard;
    }
}
