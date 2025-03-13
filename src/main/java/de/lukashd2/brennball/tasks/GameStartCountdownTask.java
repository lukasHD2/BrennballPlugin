package de.lukashd2.brennball.tasks;

import de.lukashd2.brennball.Brennball;
import de.lukashd2.brennball.game.GameManager;
import de.lukashd2.brennball.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountdownTask extends BukkitRunnable {

    private int time = 11;

    @Override
    public void run() {
        time--;

        if(time <= 0){
            cancel();
            GameManager.setGameState(GameState.RUNNING);
            return;
        }

        if(time <= 5 && time >= 4) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendTitle("§a§l"+time, "", 0, 20, 0);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
            });
        }

        if(time <= 3 && time >= 2) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendTitle("§6§l"+time, "", 0, 20, 0);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
            });
        }

        if(time == 1){
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendTitle("§c§l"+time, "", 0, 20, 0);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
            });
        }

        Bukkit.broadcastMessage(Brennball.PREFIX + "§7Spiel startet in §a§l"+time+" §7Sekunden!");
    }
}
