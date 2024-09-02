package me.bziluch.mobCustomStats;

import me.bziluch.mobCustomStats.commands.MobCustomStatsCommand;
import me.bziluch.mobCustomStats.filedata.McsConfig;
import me.bziluch.mobCustomStats.listeners.CreatureSpawnEventListener;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class MobCustomStats extends JavaPlugin {

    private static FileConfiguration configFile;
    private static ConsoleCommandSender consoleCommandSender;
    public static MobCustomStats plugin;

    @Override
    public void onEnable() {
        plugin = this;
        configFile = McsConfig.setupConfigFile();

        getServer().getPluginManager().registerEvents(new CreatureSpawnEventListener(), this);
        consoleCommandSender = getServer().getConsoleSender();

        getCommand("mobcustomstats").setExecutor(new MobCustomStatsCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FileConfiguration getConfigFile() {
        return configFile;
    }

    public static void reloadConfigFile() {
        configFile = McsConfig.loadConfigFile();
    }

    public static ConsoleCommandSender getConsoleCommandSender() {
        return consoleCommandSender;
    }

    public void saveConfigFile() throws IOException {
        configFile.save(McsConfig.getFile());
    }

    public static MobCustomStats getPlugin() {
        return plugin;
    }

}
