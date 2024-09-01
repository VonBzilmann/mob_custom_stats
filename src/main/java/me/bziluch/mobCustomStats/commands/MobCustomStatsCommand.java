package me.bziluch.mobCustomStats.commands;

import me.bziluch.mobCustomStats.MobCustomStats;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class MobCustomStatsCommand implements CommandExecutor {

    private static final String commandName = "mcs";
    private static final String prefix = "stats.";
    private static List<String> allowedKeys;
    private static final FileConfiguration configFile = MobCustomStats.getConfigFile();

    private static final String KEY_HEALTH = "health";
    private static final String KEY_EFFECTS = "effects";
    private static final String KEY_EQ = "equipment";

    public MobCustomStatsCommand() {
        String[] keys = {KEY_HEALTH, KEY_EFFECTS, KEY_EQ};
        allowedKeys = Arrays.asList(keys);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player && !commandSender.isOp() && !commandSender.hasPermission("mobcustomstats.manage")) {
            commandSender.sendMessage(ChatColor.DARK_RED + "You don't have permission to run this command.");
            return true;
        }

        if (strings.length > 0) {
            switch (strings[0]) {
                case "view-stat":
                    this.viewStat(commandSender, strings);
            }
            return true;
        }

        this.info(commandSender);
        return true;
    }

    // View subcommands
    private void info(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            commandSender.sendMessage(ChatColor.GOLD + "/" + commandName + " info - view list of commands");
            commandSender.sendMessage(ChatColor.GOLD + "/" + commandName + " config-reload - reload config.yml");
            commandSender.sendMessage(ChatColor.GOLD + "/" + commandName + " set-stat <key> <value> - set stat value (for array values, use | (pipe) separator)");
            commandSender.sendMessage(ChatColor.GOLD + "/" + commandName + " view-stat <key> - view stat value");
        }
    }

    private void viewStat(CommandSender commandSender, String[] strings)
    {
        if (strings.length != 2) {
            commandSender.sendMessage(ChatColor.GOLD + "Usage: /" + commandName + " view-stat <key>");
            return;
        }

        String[] splitKey = strings[1].split("\\.");
        if (!isSplitKeyValid(splitKey, commandSender)) {
            return;
        }

        String fullKeyIndex = prefix + strings[1];
        if (!configFile.contains(fullKeyIndex)) {
            commandSender.sendMessage(ChatColor.YELLOW + fullKeyIndex + " has no value");
            return;
        }

        String keyValue = "";
        switch (splitKey[1]) {
            case KEY_HEALTH:
                keyValue = configFile.getString(fullKeyIndex);
                break;
            case KEY_EFFECTS:
            case KEY_EQ:
                keyValue = "\n - " + String.join("\n - ", configFile.getStringList(fullKeyIndex));
                break;
        }
        commandSender.sendMessage(ChatColor.GREEN + fullKeyIndex + " contains value:");
        commandSender.sendMessage(ChatColor.AQUA + keyValue);
    }

    private void setStat(CommandSender commandSender, String[] strings)
    {
        if (strings.length != 3) {
            commandSender.sendMessage(ChatColor.GOLD + "Usage: /" + commandName + " set-stat <key> <value>");
            return;
        }

        String[] splitKey = strings[1].split("\\.");
        if (!isSplitKeyValid(splitKey, commandSender)) {
            return;
        }

        String fullKeyIndex = prefix + strings[1];
        switch (splitKey[1]) {
            case KEY_HEALTH:
                configFile.set(fullKeyIndex, splitKey[2]);
                break;
            case KEY_EFFECTS:
            case KEY_EQ:
                configFile.set(fullKeyIndex, splitKey[2].split("\\|"));
                break;
        }
        configFile.save();

    }


    private static String getAllowedKeysString(String pre) {
        return pre + String.join(", " + pre, allowedKeys);
    }
    private static String getAllowedKeysString() {
        return getAllowedKeysString("");
    }

    private static boolean isSplitKeyValid(String[] splitKey, CommandSender commandSender) {
        if (splitKey.length != 2 || !allowedKeys.contains(splitKey[1])) {
            commandSender.sendMessage(ChatColor.RED + "Provided key value is not valid. Allowed key values are: " + getAllowedKeysString("<mob_id>.") + ".");
            return false;
        }
        return true;
    }

}
