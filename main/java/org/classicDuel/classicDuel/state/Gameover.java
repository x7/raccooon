package org.classicDuel.classicDuel.state;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.classicDuel.classicDuel.ClassicDuel;
import org.classicDuel.classicDuel.game.GameManager;
import org.classicDuel.classicDuel.game.PlayerManager;

import java.util.UUID;

public class Gameover {
    // GO
    public static void gameOverState(GameManager gameManager) {
        // cancel all task
        ClassicDuel.cancelAllTask();

        UUID winner = gameManager.getWinner().getFirst();

        for(UUID uuid : gameManager.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if(player == null) continue;
            gameOverMessage(player);
            // kick everyone in the server after 5 seconds (0 for now)
            player.kickPlayer("Game over rejoin to play");
        }
    }

    // todo: handle how accurcy n shit works
    private static void gameOverMessage(Player player) {
        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "                          Classic Duel - 00:45");
        player.sendMessage(" ");
        player.sendMessage("                   [MVP+] rose548   Rizz_k WINNER!");
        player.sendMessage(" ");
        player.sendMessage("                       0❤ - Damage Dealt - 20❤");
        player.sendMessage("                    N/A - Melee Accuracy - 72.2%");
        player.sendMessage("                       N/A - Bow Accuracy - 0%");
        player.sendMessage("                 1.5❤ - Health Regenerated - 0❤");
        player.sendMessage(" ");
        player.sendMessage("                 ❤❤❤❤❤❤❤❤❤❤ - Rizz_k's Health");
        player.sendMessage("                 Click to view Rizz_k's Inventory");
        player.sendMessage(" ");
        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
    }
}
