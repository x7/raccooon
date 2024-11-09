package org.classicDuel.classicDuel.game;

import org.bukkit.scheduler.BukkitTask;
import org.classicDuel.classicDuel.ClassicDuel;
import org.classicDuel.classicDuel.state.Gameover;
import org.classicDuel.classicDuel.state.Playing;
import org.classicDuel.classicDuel.state.Pregame;
import org.classicDuel.classicDuel.task.GameCountDownTask;
import org.classicDuel.classicDuel.task.StartingCountDownTask;

import java.util.ArrayList;
import java.util.UUID;

public class GameManager {

    private ArrayList<UUID> players = new ArrayList<>();
    private ArrayList<UUID> alivePlayers = new ArrayList<>();
    private ArrayList<UUID> winner = new ArrayList<>();

    private static GameManager gameManager;
    private GameState gameState;
    private ClassicDuel instance;

    private BukkitTask startingTask;
    private BukkitTask gameTask;

    public GameManager(ClassicDuel instance) {
        this.gameManager = this;
        this.instance = instance;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;

        switch(gameState) {
            case PREGAME:
                Pregame.pregameState(gameManager);
                break;
            case STARTING:
                startingTask = new StartingCountDownTask(gameManager).runTaskTimer(instance, 0L, 20);
                break;
            case PLAYING:
                Playing.playingState(gameManager);
                gameTask = new GameCountDownTask(gameManager).runTaskTimer(instance, 0L, 20);
                break;
            case GAMEOVER:
                Gameover.gameOverState(gameManager);
                break;
            case null, default:
                System.out.println("Invalid game state provided faggot");
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public ArrayList<UUID> getAlivePlayers() {
        return alivePlayers;
    }

    public ArrayList<UUID> getPlayers() {
        return players;
    }

    public void addToAlivePlayers(UUID uuid) {
        if(alivePlayers.contains(uuid)) return;
        alivePlayers.add(uuid);
    }

    public void addToPlayers(UUID uuid) {
        if(players.contains(uuid)) return;
        players.add(uuid);
    }

    public void removeFromPlayers(UUID uuid) {
        if(!players.contains(uuid)) return;
        players.remove(uuid);
    }

    public void removeFromAlivePlayers(UUID uuid) {
        if(!alivePlayers.contains(uuid)) return;
        alivePlayers.remove(uuid);
    }

    public ArrayList<UUID> getWinner() {
        return winner;
    }

    public void addWinner(UUID player) {
        winner.add(player);
    }

    public static GameManager returnGameManager() {
        return gameManager;
    }

    public BukkitTask returnStartingTask() {
        return startingTask;
    }

    public BukkitTask returnGameTask() {
        return gameTask;
    }

    public UUID returnOpponent(UUID nonOpponent) {
        UUID opponent = null;
        for(UUID uuid : players) {
            if(uuid.equals(nonOpponent)) continue;
            opponent = uuid;
        }

        return opponent;
    }
}
