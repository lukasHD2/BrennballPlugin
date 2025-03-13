package de.lukashd2.brennball.team;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Team {

    private final String name;
    private final ChatColor color;
    private final Color leatherColor;
    private Player currentPlayer;
    private int points;

    private boolean isTeamMiddle;

    private final List<Player> teamPlayers = new ArrayList<>();
    private final Map<Player, Boolean> playerRunning = new HashMap<>();

    public Team(String teamName, ChatColor teamColor, Color color, boolean isTeamMiddle){
        this.name = teamName;
        this.color = teamColor;
        this.leatherColor = color;
        this.isTeamMiddle = isTeamMiddle;
        this.points = 0;
    }

    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }

    public void addPlayerToTeam(Player player){
        if(!teamPlayers.contains(player)){
            teamPlayers.add(player);
            playerRunning.put(player, false);
        }
    }

    public void setPoints(int points){
        this.points = points;
    }

    public void setTeamMiddle(boolean teamMiddle){
        isTeamMiddle = teamMiddle;
    }

    public void removePlayerFromTeam(Player player){
        teamPlayers.remove(player);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getPoints() {
        return points;
    }

    public List<Player> getTeamPlayers() {
        return teamPlayers;
    }

    public Map<Player, Boolean> getPlayerRunning() {
        return playerRunning;
    }

    public String getName() {
        return name;
    }

    public boolean isTeamMiddle() {
        return isTeamMiddle;
    }

    public ChatColor getColor() {
        return color;
    }

    public Color getLeatherColor() {
        return leatherColor;
    }
}
