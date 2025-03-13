package de.lukashd2.brennball.team;

import de.lukashd2.brennball.Brennball;
import de.lukashd2.brennball.game.GameManager;
import de.lukashd2.brennball.game.GameState;
import de.lukashd2.brennball.utils.ItemBuilder;
import de.lukashd2.brennball.utils.Timer;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TeamManager {

    private final List<Team> teamList = new ArrayList<>();

    public void registerNewTeam(String teamName, ChatColor teamColor, Color color, boolean isTeamMiddle){
        Team team = new Team(teamName, teamColor, color, isTeamMiddle);
        if(!teamList.contains(team)){
            teamList.add(team);
        }
    }

    public void setTeamArmor(Player player, Color color){
        player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setLeatherColor(color).setUnbreakable().toItemStack());
        player.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherColor(color).setUnbreakable().toItemStack());
        player.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherColor(color).setUnbreakable().toItemStack());
        player.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setLeatherColor(color).setUnbreakable().toItemStack());
    }

    public Player getRandomPlayerByTeam(Team team) {
        List<Player> availablePlayers = team.getTeamPlayers().stream()
                .filter(player -> !team.getPlayerRunning().get(player))
                .collect(Collectors.toList());

        if (availablePlayers.isEmpty()) {
            Bukkit.broadcastMessage("Es gibt keinen weiteren Spieler. Shutdown!");
            Timer.sleep(5, TimeUnit.SECONDS);
            Bukkit.shutdown();
            return null; // Kein Spieler verfügbar
        }

        int randomIndex = new Random().nextInt(availablePlayers.size());
        Player selectedPlayer = availablePlayers.get(randomIndex);
        Bukkit.broadcastMessage("Ein neuer Spieler spawnt > " + selectedPlayer.getName());
        return selectedPlayer;
    }

    public Team getTeam(String teamName){
        List<Team> team = teamList.stream().filter(team1 -> team1.getName().contains(teamName)).collect(Collectors.toList());
        return team.get(0);
    }

    public void disbandPlayer(Player player){
        player.sendMessage(Brennball.PREFIX + "§7Du bist raus!!");
        GameManager.getTeleportManager().teleportPlayer(player);
    }

    public Team getRandomTeam(){
        int random = new Random().nextInt(teamList.size());
        return teamList.get(random);
    }

    public Team getNoTeamMiddle(){
        List<Team> middleTeam = teamList.stream().filter(team -> !team.isTeamMiddle()).collect(Collectors.toList());
        return middleTeam.get(0);
    }

    public Team getTeamMiddle(){
        List<Team> middleTeam = teamList.stream().filter(Team::isTeamMiddle).collect(Collectors.toList());
        return middleTeam.get(0);
    }

    public boolean isInTeam(Player player, String teamName){
        List<Team> team = teamList.stream().filter(team1 -> team1.getName().contains(teamName)).collect(Collectors.toList());
        return team.get(0).getTeamPlayers().contains(player);
    }

    public Team getPlayerTeam(Player player){
        List<Team> team = teamList.stream().filter(team1 -> team1.getTeamPlayers().contains(player)).collect(Collectors.toList());
        return team.get(0);
    }

    public boolean isInTeam(Player player){
        for (Team team : teamList) {
            if(team.getTeamPlayers().contains(player)){
                return true;
            }
        }
        return false;
    }

    public void switchTeams() {
        Team red = getTeam("Rot");
        Team blue = getTeam("Blau");
        if(red.isTeamMiddle() && !blue.isTeamMiddle()){
            red.setTeamMiddle(false);
            blue.setTeamMiddle(true);
        } else if(!red.isTeamMiddle() && blue.isTeamMiddle()) {
            GameManager.setGameState(GameState.ENDING);
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.kickPlayer("Game ending!");
            });
        }
    }
}
