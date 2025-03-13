package de.lukashd2.brennball.game;

import de.lukashd2.brennball.utils.LocationManager;
import org.bukkit.entity.Player;

public class TeleportManager {

    public void teleportPlayer(Player player){
        if(GameManager.getTeamManager().getPlayerTeam(player).isTeamMiddle()){
            player.teleport(LocationManager.getLocation("team1"));
        } else {
            player.teleport(LocationManager.getLocation("team2"));
        }
    }

}
