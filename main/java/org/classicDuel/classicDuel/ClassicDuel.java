package org.classicDuel.classicDuel;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.classicDuel.classicDuel.commands.CommandManager;
import org.classicDuel.classicDuel.events.EventManager;

/*
ok so first we load into a random classic map world (store names in config.yml)
once picked a map we load in and "this is the pregame state" add scorreboarrrd etc
make it so if someone tries to join the server after max players > disconnect and say server full etc orr game in progress
*/

public final class ClassicDuel extends JavaPlugin {

    private static ClassicDuel instance;
    private static EventManager eventManager;
    private static CommandManager commandManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // config
        saveDefaultConfig();
        if(instance.getConfig().getDefaults() != null) {
            saveResource("config.yml", true);
        }

        // commands (commands i need to do)
        // /map > returns map name
        // no others check hypixel when doing them or more
        commandManager = new CommandManager(instance);


        // listeners
        eventManager = new EventManager(instance);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getPluginManager().disablePlugin(this);
    }

    public static ClassicDuel getInstance() {
        return instance;
    }

    public static void cancelAllTask() {
        Bukkit.getScheduler().cancelTasks(instance);
    }
}
