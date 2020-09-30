/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import java.io.PrintStream;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class UncaughtExceptionHandlers {
    private UncaughtExceptionHandlers() {
    }

    public static Thread.UncaughtExceptionHandler systemExit() {
        return new Exiter(Runtime.getRuntime());
    }

    static final class Exiter
    implements Thread.UncaughtExceptionHandler {
        private static final Logger logger = Logger.getLogger(Exiter.class.getName());
        private final Runtime runtime;

        Exiter(Runtime runtime) {
            this.runtime = runtime;
        }

        @Override
        public void uncaughtException(Thread thread2, Throwable throwable) {
            try {
                logger.log(Level.SEVERE, String.format(Locale.ROOT, "Caught an exception in %s.  Shutting down.", thread2), throwable);
                return;
            }
            catch (Throwable throwable2) {
                try {
                    System.err.println(throwable.getMessage());
                    System.err.println(throwable2.getMessage());
                    return;
                }
                finally {
                    this.runtime.exit(1);
                }
            }
        }
    }

}

