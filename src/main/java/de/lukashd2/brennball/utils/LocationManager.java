package de.lukashd2.brennball.utils;

import de.lukashd2.brennball.Brennball;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LocationManager {

    private static final File file = new File(Brennball.getPlugin().getDataFolder(), "spawns.yml");
    private static final YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    private static void save(){
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setLocation(String locationName, Location location){
        cfg.set(locationName+".WORLD", location.getWorld().getName());
        cfg.set(locationName+".X", location.getX());
        cfg.set(locationName+".Y", location.getY());
        cfg.set(locationName+".Z", location.getZ());
        cfg.set(locationName+".YAW", location.getYaw());
        cfg.set(locationName+".PITCH", location.getPitch());
        save();
    }

    public static Location getLocation(String locationName){
        World world = Bukkit.getWorld(cfg.getString(locationName+".WORLD"));
        double x = cfg.getDouble(locationName+".X");
        double y = cfg.getDouble(locationName+".Y");
        double z = cfg.getDouble(locationName+".Z");
        Location location = new Location(world, x, y, z);
        location.setYaw(cfg.getInt(locationName+".YAW"));
        location.setPitch(cfg.getInt(locationName+".PITCH"));
        return location;
    }

}
