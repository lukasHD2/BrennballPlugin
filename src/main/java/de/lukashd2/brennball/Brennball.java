package de.lukashd2.brennball;

import de.lukashd2.brennball.commands.BrennballCommand;
import de.lukashd2.brennball.events.Events;
import de.lukashd2.brennball.events.SnowballEvent;
import de.lukashd2.brennball.game.GameManager;
import de.lukashd2.brennball.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Brennball extends JavaPlugin {

    private static Brennball plugin;
    private TeamManager teamManager;
    private GameManager gameManager;

    public static String PREFIX = "§8[§5Brennball§8] §r";

    @Override
    public void onEnable() {
        plugin = this;
        teamManager = new TeamManager();
        gameManager = new GameManager(this);

        getCommand("brennball").setExecutor(new BrennballCommand());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new Events(), this);
        pluginManager.registerEvents(new SnowballEvent(), this);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public static Brennball getPlugin() {
        return plugin;
    }
}
