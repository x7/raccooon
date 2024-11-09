package org.classicDuel.classicDuel.game;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.classicDuel.classicDuel.ClassicDuel;
import org.classicDuel.classicDuel.world.WorldManager;

import java.util.*;

public class PlayerManager {
    public static void giveItems(Player player) {
        player.getInventory().clear();

        ItemStack ironSword = new ItemStack(Material.IRON_SWORD, 1);
        ItemStack fishingRod = new ItemStack(Material.FISHING_ROD, 1);
        ItemStack bow = new ItemStack(Material.BOW, 1);
        ItemStack arrows = new ItemStack(Material.ARROW, 5);

        ItemStack ironHelmet = new ItemStack(Material.IRON_HELMET, 1);
        ironHelmet.addEnchantment(Enchantment.PROTECTION, 2);
        ItemStack ironChestPlate = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ironChestPlate.addEnchantment(Enchantment.PROTECTION, 2);
        ItemStack ironLeggings = new ItemStack(Material.IRON_LEGGINGS, 1);
        ironLeggings.addEnchantment(Enchantment.PROTECTION, 2);
        ItemStack ironBoots = new ItemStack(Material.IRON_BOOTS, 1);
        ironBoots.addEnchantment(Enchantment.PROTECTION, 2);

        PlayerInventory inventory = player.getInventory();
        Inventory bottomInventory = player.getOpenInventory().getBottomInventory();
        Inventory topInventory = player.getOpenInventory().getTopInventory();

        bottomInventory.setItem(0, ironSword);
        bottomInventory.setItem(1, fishingRod);
        bottomInventory.setItem(2, bow);
        topInventory.setItem(0, arrows);

        inventory.setItem(EquipmentSlot.HEAD, ironHelmet);
        inventory.setItem(EquipmentSlot.CHEST, ironChestPlate);
        inventory.setItem(EquipmentSlot.LEGS, ironLeggings);
        inventory.setItem(EquipmentSlot.FEET, ironBoots);
    }

    public static void teleportPlayerSpawnPoint(ClassicDuel instance, GameManager gameManager, Player player, World world) {
        Configuration configuration = instance.getConfig().getDefaults();
        Set<String> mapKeys = WorldManager.getAllSections(configuration, "maps." + world.getName());
        List<String> mapSpawnKeys = new ArrayList<>(mapKeys);

        double x = 0;
        double y = 0;
        double z = 0;
        float yaw = 0;
        float pitch = 0;

        ArrayList<UUID> players = gameManager.getPlayers();
        int index = players.indexOf(player.getUniqueId()) + 1;

        for(String spawnKey : mapSpawnKeys) {
            String stringIndex = Integer.toString(index);
            if(!spawnKey.equalsIgnoreCase(stringIndex)) continue;

            x = configuration.getDouble("maps." + world.getName() + "." + spawnKey + ".x");
            y = configuration.getDouble("maps." + world.getName() + "." + spawnKey + ".y");
            z = configuration.getDouble("maps." + world.getName() + "." + spawnKey + ".z");
            yaw = (float) configuration.getDouble("maps." + world.getName() + "." + spawnKey + ".yaw");
            pitch = (float) configuration.getDouble("maps." + world.getName() + "." + spawnKey + ".float");
        }

        Location first_player_spawn = new Location(world, x, y, z, yaw, pitch);
        player.teleport(first_player_spawn);
    }
}
