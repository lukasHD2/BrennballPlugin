package de.lukashd2.brennball.events;

import de.lukashd2.brennball.game.GameManager;
import de.lukashd2.brennball.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Events implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.getPlayer().getInventory().clear();
        event.getPlayer().getInventory().setArmorContents(null);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        if(!GameManager.isGameState(GameState.RUNNING)) return;
        if(GameManager.getTeamManager().getPlayerTeam(event.getPlayer()) == null) return;
        if(GameManager.getTeamManager().getPlayerTeam(event.getPlayer()).getCurrentPlayer() == event.getPlayer()) return;

        if(GameManager.getTeamManager().getPlayerTeam(event.getPlayer()).getPlayerRunning().get(event.getPlayer())){
            if(event.getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.DIAMOND_ORE)){
                GameManager.getTeamManager().getPlayerTeam(event.getPlayer()).getPlayerRunning().put(event.getPlayer(), false);
                GameManager.getTeamManager().disbandPlayer(event.getPlayer());
                event.getPlayer().sendMessage("NO");
            } else if(event.getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.DIAMOND_BLOCK)){
                GameManager.getTeamManager().getPlayerTeam(event.getPlayer()).getPlayerRunning().put(event.getPlayer(), false);
                int currentPoints = GameManager.getTeamManager().getPlayerTeam(event.getPlayer()).getPoints();
                GameManager.getTeamManager().getPlayerTeam(event.getPlayer()).setPoints(currentPoints+1);
                event.getPlayer().sendMessage("YES");
            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event){
        if(event.getWhoClicked().getGameMode() != GameMode.CREATIVE){
            if(GameManager.isGameState(GameState.RUNNING)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(event.getItem().getType() != Material.SNOW_BALL) return;
        if(GameManager.getTeamManager().getPlayerTeam(event.getPlayer()).isTeamMiddle()) return;

        if(event.getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType() != Material.DIAMOND_BLOCK){
            event.setCancelled(true);
        }

        event.getPlayer().sendMessage("Action passed");
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            if(event.getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType() == Material.DIAMOND_BLOCK){
                if(GameManager.getTeamManager().getPlayerTeam(event.getPlayer()).getCurrentPlayer() != event.getPlayer()){
                    GameManager.getTeamManager().getPlayerTeam(event.getPlayer()).setCurrentPlayer(event.getPlayer());
                    GameManager.getTeamManager().getPlayerTeam(event.getPlayer()).getPlayerRunning().put(event.getPlayer(), true);
                    event.getPlayer().sendMessage("§7<> §c"+GameManager.getTeamManager().getPlayerTeam(event.getPlayer()).getPlayerRunning().get(event.getPlayer()));
                    event.setCancelled(false);
                }
            } else {
                event.setCancelled(true);
            }
        }


    }

}
