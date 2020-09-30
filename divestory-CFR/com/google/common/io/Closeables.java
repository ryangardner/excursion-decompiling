/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Closeables {
    static final Logger logger = Logger.getLogger(Closeables.class.getName());

    private Closeables() {
    }

    public static void close(@NullableDecl Closeable closeable, boolean bl) throws IOException {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            if (!bl) throw iOException;
            logger.log(Level.WARNING, "IOException thrown while closing Closeable.", iOException);
        }
    }

    public static void closeQuietly(@NullableDecl InputStream inputStream2) {
        try {
            Closeables.close(inputStream2, true);
            return;
        }
        catch (IOException iOException) {
            throw new AssertionError(iOException);
        }
    }

    public static void closeQuietly(@NullableDecl Reader reader) {
        try {
            Closeables.close(reader, true);
            return;
        }
        catch (IOException iOException) {
            throw new AssertionError(iOException);
        }
    }
}

