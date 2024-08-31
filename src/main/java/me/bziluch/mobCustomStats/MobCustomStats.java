package me.bziluch.mobCustomStats;

import me.bziluch.mobCustomStats.listeners.CreatureSpawnEventListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MobCustomStats extends JavaPlugin {

    private static FileConfiguration configFile;

    @Override
    public void onEnable() {

        // Setup config file
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            this.saveDefaultConfig();
        }
        configFile = this.getConfig();

        getServer().getPluginManager().registerEvents(new CreatureSpawnEventListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FileConfiguration getConfigFile() {
        return configFile;
    }

}
