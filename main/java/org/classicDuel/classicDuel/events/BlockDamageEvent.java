package org.classicDuel.classicDuel.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.checkerframework.checker.index.qual.PolyUpperBound;

public class BlockDamageEvent implements Listener {
    @EventHandler
    // doesnt even work useless ass event
    public void onBlockDamage(org.bukkit.event.block.BlockDamageEvent event) {
        event.setCancelled(true);
    }
}
