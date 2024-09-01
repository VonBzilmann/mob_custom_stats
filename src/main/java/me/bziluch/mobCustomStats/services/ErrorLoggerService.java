package me.bziluch.mobCustomStats.services;

import me.bziluch.mobCustomStats.MobCustomStats;
import org.bukkit.ChatColor;

public class ErrorLoggerService {

    private int current = 0;
    private int limit;

    public ErrorLoggerService(String limitSourceKey, Integer defaultLimit) {
        limit = MobCustomStats.getConfigFile().getInt(limitSourceKey);
        if (limit == -1) {
            limit = Integer.MAX_VALUE;
        } else if (!(limit > 0)) {
            this.limit = defaultLimit;
            this.sendErrorMessage("Can't find count of max error messages in config.yml - setting to default (" + defaultLimit + ")");
        }
    }

    public void sendErrorMessage(String errorText) {
        if (this.current < this.limit) {
            MobCustomStats.getConsoleCommandSender().sendMessage(ChatColor.DARK_RED + "[MobCustomStats] WARNING: " + errorText);
            this.current += 1;
        }
    }
}
