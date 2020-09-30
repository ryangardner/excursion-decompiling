/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import java.io.Flushable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Flushables {
    private static final Logger logger = Logger.getLogger(Flushables.class.getName());

    private Flushables() {
    }

    public static void flush(Flushable flushable, boolean bl) throws IOException {
        try {
            flushable.flush();
            return;
        }
        catch (IOException iOException) {
            if (!bl) throw iOException;
            logger.log(Level.WARNING, "IOException thrown while flushing Flushable.", iOException);
        }
    }

    public static void flushQuietly(Flushable flushable) {
        try {
            Flushables.flush(flushable, true);
            return;
        }
        catch (IOException iOException) {
            logger.log(Level.SEVERE, "IOException should not have been thrown.", iOException);
        }
    }
}

