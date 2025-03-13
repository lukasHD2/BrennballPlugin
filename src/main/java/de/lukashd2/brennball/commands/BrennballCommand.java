package de.lukashd2.brennball.commands;

import de.lukashd2.brennball.Brennball;
import de.lukashd2.brennball.game.GameManager;
import de.lukashd2.brennball.game.GameState;
import de.lukashd2.brennball.utils.LocationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BrennballCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender.isOp()){
            Player player = (Player) sender;
            if(args.length == 1){

                switch(args[0].toLowerCase()){
                    case "start":
                        if (GameManager.isGameState(GameState.LOBBY)) {
                            GameManager.setGameState(GameState.STARTING);
                            player.sendMessage(Brennball.PREFIX+"§7Du hast das Spiel §agestartet!");
                        }
                        break;
                    case "stop":
                        if (GameManager.isGameState(GameState.RUNNING)) {
                            GameManager.setGameState(GameState.ENDING);
                            player.sendMessage(Brennball.PREFIX+"§7Du hast das Spiel §cgestoppt!");
                        }
                        break;
                }
            } else if(args.length == 2){
                if(args[0].equalsIgnoreCase("setloc")){
                    switch (args[1].toLowerCase()){
                        case "team1":
                            LocationManager.setLocation("team1", player.getLocation());
                            player.sendMessage(Brennball.PREFIX+"§7Du hast den Spawnpunkt §4§l"+args[1].toLowerCase()+" §7gesetzt!");
                            break;
                        case "team2":
                            LocationManager.setLocation("team2", player.getLocation());
                            player.sendMessage(Brennball.PREFIX+"§7Du hast den Spawnpunkt §9§l"+args[1].toLowerCase()+" §7gesetzt!");
                            break;
                        case "team3":
                            LocationManager.setLocation("team3", player.getLocation());
                            player.sendMessage(Brennball.PREFIX+"§7Du hast den Spawnpunkt §9§l"+args[1].toLowerCase()+" §7gesetzt!");
                            break;
                        default:
                            player.sendMessage(Brennball.PREFIX+"§7Benutze §c/brennball setloc <team1:team2:team3>");
                            break;
                    }
                }
            }

        }

        return false;
    }
}
