package de.lukashd2.brennball.events;

import de.lukashd2.brennball.game.GameManager;
import de.lukashd2.brennball.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class SnowballEvent implements Listener {

    @EventHandler
    public void onHit1(ProjectileHitEvent event){
        if(!(event.getEntity() instanceof Snowball)) return;

        if(event.getHitEntity() != null){
            ItemStack snowball = new ItemBuilder(Material.SNOW_BALL).clearFlags().toItemStack();
            Item item = event.getHitEntity().getLocation().getWorld().dropItem(event.getHitEntity().getLocation(), snowball);
        }

        if(event.getHitBlock() != null){
            if(event.getHitBlock().getType() == Material.GOLD_BLOCK) return;
            ItemStack snowball = new ItemBuilder(Material.SNOW_BALL).clearFlags().toItemStack();
            Item item = event.getEntity().getLocation().getWorld().dropItem(event.getEntity().getLocation(), snowball);
            item.setVelocity(new Vector());
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event){
        if(!(event.getEntity() instanceof Snowball)) return;
        if(event.getHitBlock() == null) return;
        if(event.getHitBlock().getType() != Material.GOLD_BLOCK) return;

        for (Player teamPlayer : GameManager.getTeamManager().getNoTeamMiddle().getTeamPlayers()) {
            if(GameManager.getTeamManager().getNoTeamMiddle().getPlayerRunning().get(teamPlayer)){
                Location blockLocation = new Location(teamPlayer.getWorld(), teamPlayer.getLocation().getX(), teamPlayer.getLocation().getY()-1, teamPlayer.getLocation().getZ());
                if(blockLocation.getBlock().getType() == Material.REDSTONE_BLOCK){
                    GameManager.getTeamManager().getNoTeamMiddle().getPlayerRunning().put(teamPlayer, false);
                    GameManager.getTeamManager().disbandPlayer(teamPlayer);
                }
            }
        }
        GameManager.getTeamManager().getNoTeamMiddle().setCurrentPlayer(null);
        GameManager.getRoundManager().startNewRound(GameManager.getTeamManager().getRandomPlayerByTeam(GameManager.getTeamManager().getNoTeamMiddle()));

    }

}
