package org.classicDuel.classicDuel.events;

import org.classicDuel.classicDuel.ClassicDuel;

public class EventManager {

    private ClassicDuel instance;

    public EventManager(ClassicDuel instance) {
        this.instance = instance;

        // load plugins
        instance.getServer().getPluginManager().registerEvents(new PlayerJoinWorldEvent(), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerDisconnectEvent(), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerChatEvent(), instance);
        instance.getServer().getPluginManager().registerEvents(new EntityDamageEvent(), instance);
        instance.getServer().getPluginManager().registerEvents(new EntityDeathEvent(), instance);
        instance.getServer().getPluginManager().registerEvents(new BlockBreakEvent(), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerInteractEvent(), instance);
        instance.getServer().getPluginManager().registerEvents(new BlockDamageEvent(), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), instance);
    }
}
