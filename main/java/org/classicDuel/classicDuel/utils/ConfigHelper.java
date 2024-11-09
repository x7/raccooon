package org.classicDuel.classicDuel.utils;

import org.classicDuel.classicDuel.ClassicDuel;

public class ConfigHelper {
    public static void updateConfigString(ClassicDuel instance, String path, String updatedValue) {
        String currentPath = instance.getConfig().getString(path);
        if(currentPath == null) return;

        instance.getConfig().set(path, updatedValue);
        saveConfig(instance);
    }

    public static void saveConfig(ClassicDuel instance) {
        instance.saveConfig();
    }
}
