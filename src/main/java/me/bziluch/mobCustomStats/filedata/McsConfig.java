package me.bziluch.mobCustomStats.filedata;

import me.bziluch.mobCustomStats.MobCustomStats;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class McsConfig {

    private static File file;

    private static void loadFile() {
        file = new File(MobCustomStats.getPlugin().getDataFolder(), "config.yml");
    }

    public static FileConfiguration loadConfigFile() {
        loadFile();

        try {
            return YamlConfiguration.loadConfiguration(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FileConfiguration setupConfigFile() {
        // Setup config file
        loadFile();

        if (!file.exists()) {
            MobCustomStats.getPlugin().saveDefaultConfig();
        }

        return MobCustomStats.getPlugin().getConfig();
    }

    public static File getFile() {
        return file;
    }

}
