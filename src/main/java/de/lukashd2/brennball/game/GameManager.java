package de.lukashd2.brennball.game;

import de.lukashd2.brennball.Brennball;
import de.lukashd2.brennball.tasks.GameStartCountdownTask;
import de.lukashd2.brennball.team.Team;
import de.lukashd2.brennball.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private static GameState gameState;
    private static Brennball brennball;
    private static TeamManager teamManager;
    private static TeleportManager teleportManager;
    private static RoundManager roundManager;

    public GameManager(Brennball plugin){
        brennball = plugin;
        teamManager = brennball.getTeamManager();
        teleportManager = new TeleportManager();
        roundManager = new RoundManager();

        teamManager.registerNewTeam("Rot", ChatColor.RED, Color.RED, true);
        teamManager.registerNewTeam("Blau", ChatColor.BLUE, Color.BLUE, false);

        setGameState(GameState.LOBBY);
    }

    public static void setGameState(GameState currentGameState){
        if(gameState == currentGameState) return;
        gameState = currentGameState;

        switch (gameState){
            case STARTING:
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.getInventory().clear();
                    player.getInventory().setArmorContents(null);
                });

                GameStartCountdownTask countdownTask = new GameStartCountdownTask();
                countdownTask.runTaskTimer(brennball, 0, 20);

                break;
            case RUNNING:
                ArrayList<Player> allPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());

                for(Player player : Bukkit.getOnlinePlayers()){
                    player.setGameMode(GameMode.ADVENTURE);

                    int random = new Random().nextInt(allPlayers.size());
                    Player randomPlayer = allPlayers.get(random);

                    if(!teamManager.isInTeam(randomPlayer)){
                        Team team = teamManager.getTeam("Rot");

                        int maxPlayersPerTeam = Bukkit.getOnlinePlayers().size() / 2;
                        if(team.getTeamPlayers().size() == maxPlayersPerTeam){
                            if(team.getName().equalsIgnoreCase("Rot")){
                                team = teamManager.getTeam("Blau");
                            }
                        }

                        if(!team.getTeamPlayers().contains(randomPlayer)){
                            team.addPlayerToTeam(randomPlayer);
                            teamManager.setTeamArmor(randomPlayer, team.getLeatherColor());
                            allPlayers.remove(randomPlayer);
                            randomPlayer.sendMessage(Brennball.PREFIX+"§7Du bist nun im Team "+team.getColor()+team.getName());
                        }
                        teleportManager.teleportPlayer(randomPlayer);
                    }
                }
                roundManager.startNewRound(teamManager.getRandomPlayerByTeam(teamManager.getNoTeamMiddle()));

                break;
            case ENDING:
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.kickPlayer("Server restarting");
                });
                Bukkit.shutdown();
                break;
        }
    }

    public static boolean isGameState(GameState gameState){
        return gameState == getGameState();
    }

    public static RoundManager getRoundManager() {
        return roundManager;
    }

    public static TeleportManager getTeleportManager() {
        return teleportManager;
    }

    public static TeamManager getTeamManager() {
        return teamManager;
    }

    public static GameState getGameState() {
        return gameState;
    }
}
