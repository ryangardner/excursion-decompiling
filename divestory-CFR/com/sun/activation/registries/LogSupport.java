/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.activation.registries;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogSupport {
    private static boolean debug = false;
    private static final Level level = Level.FINE;
    private static Logger logger;

    static {
        try {
            debug = Boolean.getBoolean("javax.activation.debug");
        }
        catch (Throwable throwable) {}
        logger = Logger.getLogger("javax.activation");
    }

    private LogSupport() {
    }

    public static boolean isLoggable() {
        if (debug) return true;
        if (logger.isLoggable(level)) return true;
        return false;
    }

    public static void log(String string2) {
        if (debug) {
            System.out.println(string2);
        }
        logger.log(level, string2);
    }

    public static void log(String string2, Throwable throwable) {
        if (debug) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2));
            stringBuilder.append("; Exception: ");
            stringBuilder.append(throwable);
            printStream.println(stringBuilder.toString());
        }
        logger.log(level, string2, throwable);
    }
}

