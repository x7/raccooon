package org.classicDuel.classicDuel.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.classicDuel.classicDuel.ClassicDuel;
import org.classicDuel.classicDuel.game.GameManager;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PregameScoreboard {

    private GameManager gameManager;
    private static Map<UUID, Scoreboard> pregameScoreboards = new ConcurrentHashMap<>();
    private static PregameScoreboard pregameScoreboard;

    public PregameScoreboard(GameManager gameManager) {
        pregameScoreboard = this;
        this.gameManager = gameManager;
    }

    public void createScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("ClassicDuels", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Classic Duels");

        ClassicDuel instance = ClassicDuel.getInstance();
        int maxPlayers = instance.getConfig().getInt("server.max-players");
        String version = instance.getConfig().getString("server.version");
        String modeName = instance.getConfig().getString("server.mode-name");
        String mapName = instance.getConfig().getString("server.current-map-name");

        // did in wrong order epic
        objective.getScore(ChatColor.GRAY + "09/11/2024").setScore(8);
        objective.getScore(ChatColor.WHITE + "").setScore(7);
        objective.getScore(ChatColor.WHITE + "Map: " + ChatColor.GREEN + mapName).setScore(6);
        objective.getScore(ChatColor.WHITE + "").setScore(4);
        objective.getScore(ChatColor.WHITE + "").setScore(2);
        objective.getScore(ChatColor.WHITE + "Mode: " + ChatColor.GREEN + modeName).setScore(1);
        objective.getScore(ChatColor.WHITE + "Version: " + ChatColor.GRAY + version).setScore(0);

        Team playersTeam = scoreboard.registerNewTeam("playersTeam");
        String playersTeamKey = ChatColor.GOLD.toString();
        playersTeam.addEntry(playersTeamKey); // ?
        playersTeam.setPrefix(ChatColor.WHITE + "Players: ");
        playersTeam.setSuffix(ChatColor.GREEN + "" + gameManager.getPlayers().size() + "/" + maxPlayers);
        objective.getScore(playersTeamKey).setScore(5);

        Team waitingTeam = scoreboard.registerNewTeam("waitingTeam");
        String waitingTeamKey = ChatColor.RED.toString();
        waitingTeam.addEntry(waitingTeamKey);
        waitingTeam.setPrefix("Waiting...");
        objective.getScore(waitingTeamKey).setScore(3);

        player.setScoreboard(scoreboard);
        addPregameScoreboard(player.getUniqueId(), scoreboard);
    }

    public void updatePlayersTeam(Player player) {
        Scoreboard scoreboard = player.getScoreboard(); // ??
        Team playersTeam = scoreboard.getTeam("playersTeam");

        int maxPlayers = ClassicDuel.getInstance().getConfig().getInt("server.max-players");
        playersTeam.setSuffix(ChatColor.GREEN + "" + gameManager.getPlayers().size() + "/" + maxPlayers);
    }

    public void updateWaitingTeam(Player player, String message) {
        Scoreboard scoreboard = player.getScoreboard();
        Team waitingTeam = scoreboard.getTeam("waitingTeam");

        waitingTeam.setPrefix(message); // RETURNS NULL CBA to fix
    }

    public Map<UUID, Scoreboard> getPregameScoreboards() {
        return pregameScoreboards;
    }

    public void addPregameScoreboard(UUID uuid, Scoreboard scoreboard) {
        if(pregameScoreboards.containsKey(uuid)) return;
        pregameScoreboards.put(uuid, scoreboard);
    }

    public void removePregameScoreboard(UUID uuid) {
        if(!pregameScoreboards.containsKey(uuid)) return;
        pregameScoreboards.remove(uuid);
    }

    public static PregameScoreboard getPregameScoreboard() {
        return pregameScoreboard;
    }
}
