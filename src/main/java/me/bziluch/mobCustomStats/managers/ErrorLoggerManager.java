package me.bziluch.mobCustomStats.managers;

import me.bziluch.mobCustomStats.services.ErrorLoggerService;

public class ErrorLoggerManager {

    private static final ErrorLoggerService logger = new ErrorLoggerService("max_error_messages", 64);

    public static ErrorLoggerService getLogger() {
        return logger;
    }

}
