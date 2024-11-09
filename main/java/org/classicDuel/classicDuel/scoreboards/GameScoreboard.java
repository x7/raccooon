package org.classicDuel.classicDuel.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.classicDuel.classicDuel.ClassicDuel;
import org.classicDuel.classicDuel.game.GameManager;
import org.classicDuel.classicDuel.utils.TimeHelper;

import javax.script.ScriptContext;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameScoreboard {

    private GameManager gameManager;
    private static GameScoreboard gameScoreboard;
    private static Map<UUID, Scoreboard> gameScoreboards = new ConcurrentHashMap<>();

    public GameScoreboard(GameManager gameManager) {
        this.gameManager = gameManager;
        gameScoreboard = this;
    }

    public void createScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("ClassicDuels_GAME", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Classic Duels");

        ClassicDuel instance = ClassicDuel.getInstance();
        String version = instance.getConfig().getString("server.version");
        String modeName = instance.getConfig().getString("server.mode-name");

        // did in wrong order epic
        objective.getScore(ChatColor.GRAY + "09/11/2024").setScore(8);
        objective.getScore(ChatColor.WHITE + "").setScore(7);
        objective.getScore(ChatColor.WHITE + "").setScore(5);
        objective.getScore(ChatColor.WHITE + "" + ChatColor.BOLD + "Opponent:").setScore(4);
        objective.getScore(ChatColor.WHITE + "").setScore(2);
        objective.getScore(ChatColor.WHITE + "Mode: " + ChatColor.GREEN + modeName).setScore(1);
        objective.getScore(ChatColor.WHITE + "Version: " + ChatColor.GRAY + version).setScore(0);

        Team timeLeft = scoreboard.registerNewTeam("timeLeftTeam");
        String timeLeftKey = ChatColor.BLUE.toString();
        timeLeft.addEntry(timeLeftKey);
        timeLeft.setPrefix(ChatColor.WHITE + "Time Left: ");
        timeLeft.setSuffix(ChatColor.GREEN + "8:00");
        objective.getScore(timeLeftKey).setScore(6);

        Player opponent = Bukkit.getPlayer(gameManager.returnOpponent(player.getUniqueId()));
        Team opponentHealth = scoreboard.registerNewTeam("opponentHealthTeam");
        String opponentHealthKey = ChatColor.GOLD.toString();
        opponentHealth.addEntry(opponentHealthKey);
        opponentHealth.setPrefix(ChatColor.RED + opponent.getDisplayName() + " ");
        opponentHealth.setSuffix(ChatColor.YELLOW + Double.toString(opponent.getHealth()) + ChatColor.RED + "❤");
        objective.getScore(opponentHealthKey).setScore(3);

        player.setScoreboard(scoreboard);
        addGameScoreboard(player.getUniqueId(), scoreboard);
    }

    // MESSAGE HAS TOO BE A NUMBERRRRR!!!!!
    public void updateTimeLeftTeam(Player player, String message) {
        Scoreboard scoreboard = player.getScoreboard();
        Team team = scoreboard.getTeam("timeLeftTeam");

        team.setSuffix(TimeHelper.formatTime(Integer.valueOf(message)));
    }

    public void updateOpponentHealthTeam(Player player, String message) {
        Scoreboard scoreboard = player.getScoreboard();
        Team team = scoreboard.getTeam("opponentHealthTeam");

        team.setSuffix(message);
    }

    public void addGameScoreboard(UUID uuid, Scoreboard scoreboard) {
        if(gameScoreboards.containsKey(uuid)) return;
        gameScoreboards.put(uuid, scoreboard);
    }

    public void removeGameScoreboard(UUID uuid) {
        if(!gameScoreboards.containsKey(uuid)) return;
        gameScoreboards.remove(uuid);
    }

    public static GameScoreboard getGameScoreboard() {
        return gameScoreboard;
    }

    public Map<UUID, Scoreboard> getGameScoreboards() {
        return gameScoreboards;
    }
}
