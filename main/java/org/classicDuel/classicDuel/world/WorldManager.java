package org.classicDuel.classicDuel.world;

import org.bukkit.*;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.classicDuel.classicDuel.ClassicDuel;
import org.classicDuel.classicDuel.utils.ConfigHelper;
import org.classicDuel.classicDuel.utils.FileHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class WorldManager {
    public static World pickRandomMap(ClassicDuel instance) throws IOException {
        Configuration configuration = instance.getConfig().getDefaults();

        if(configuration == null) {
            System.out.println(ChatColor.RED + "Configuration is null");
            return null;
        }

        Set<String> mapNames = getAllSections(configuration, "maps");
        if(mapNames.isEmpty()) {
            System.out.println(ChatColor.RED + "The maps array in your config is either null or empty.");
            return null;
        }

        List<String> mapList = new ArrayList<>(mapNames);

        Random random = new Random();
        String mapId = mapList.get(random.nextInt(mapList.size()));

        Path backup = Paths.get(System.getProperty("user.dir") + "/world_backups/" + mapId);
        Path newFolder = Paths.get(System.getProperty("user.dir") + "/" + mapId);
        FileHelper.copyFolderContents(backup, newFolder);

        // set map name
        String mapName = configuration.getString("maps." + mapId + ".map_name");
        ConfigHelper.updateConfigString(instance, "server.current-map-name", mapName);

        WorldCreator worldCreator = new WorldCreator(mapId);
        worldCreator.environment(World.Environment.NORMAL).createWorld();
        World world = Bukkit.getWorld(mapId);
        world.setAutoSave(false);
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setTime(1200);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);

        if(world == null) {
            System.out.println(ChatColor.RED + "The world ID " + mapId + " is invalid. No map found");
            return null;
        }

        System.out.println("Created new world with the map ID of " + mapId);

        return world;
    }

    // figure a way to not let anyone on the server til this is fully completed maybe a boolean idk
    public static void unloadWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world == null) return;

        Bukkit.getScheduler().runTaskLater(ClassicDuel.getInstance(), () -> {
            Bukkit.unloadWorld(world, false);

            File directory = new File(System.getProperty("user.dir") + "/" + worldName);

            if (!directory.exists()) {
                System.out.println("Unable to delete the world with map ID of " + worldName + " as the folder doesnt exist");
                return;
            }

            FileHelper.deleteDirectory(directory);
        }, 20L);

        ClassicDuel.cancelAllTask();
        System.out.println("Unloaded + deleted the world with the map ID of " + worldName);
    }

    public static Set<String> getAllSections(Configuration configuration, String sectionName) {
        ConfigurationSection mapsSection = configuration.getConfigurationSection(sectionName);
        if (mapsSection != null) {
            return mapsSection.getKeys(false);
        }
        return Collections.emptySet();
    }

}
