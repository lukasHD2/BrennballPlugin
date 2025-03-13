package de.lukashd2.brennball.game;

import de.lukashd2.brennball.utils.ItemBuilder;
import de.lukashd2.brennball.utils.LocationManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RoundManager {

    public static int roundLength;

    public RoundManager(){
        roundLength = 10;
    }

    public void startNewRound(Player player){
        if(!GameManager.isGameState(GameState.RUNNING)) return;

        if(roundLength == 0){
            GameManager.getTeamManager().switchTeams();
            setRoundLength(10);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                GameManager.getTeleportManager().teleportPlayer(onlinePlayer);
            }

            startNewRound(GameManager.getTeamManager().getRandomPlayerByTeam(GameManager.getTeamManager().getNoTeamMiddle()));
        } else {
            boolean test = GameManager.getTeamManager().getNoTeamMiddle().getPlayerRunning().get(player);
            player.sendMessage("§c> §7"+test);
            if(!GameManager.getTeamManager().getNoTeamMiddle().getPlayerRunning().get(player)){
                ItemStack snowball = new ItemBuilder(Material.SNOW_BALL).clearFlags().toItemStack();
                player.getInventory().addItem(snowball);
                player.teleport(LocationManager.getLocation("team3"));
                Bukkit.broadcastMessage("Runde noch übrig <> "+roundLength);
                roundLength--;
            } else {
                startNewRound(GameManager.getTeamManager().getRandomPlayerByTeam(GameManager.getTeamManager().getNoTeamMiddle()));
            }
        }
    }

    public static void setRoundLength(int roundLength) { RoundManager.roundLength = roundLength; }
}
