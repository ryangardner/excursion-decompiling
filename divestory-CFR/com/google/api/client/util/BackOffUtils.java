/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.BackOff;
import com.google.api.client.util.Sleeper;
import java.io.IOException;

public final class BackOffUtils {
    private BackOffUtils() {
    }

    public static boolean next(Sleeper sleeper, BackOff backOff) throws InterruptedException, IOException {
        long l = backOff.nextBackOffMillis();
        if (l == -1L) {
            return false;
        }
        sleeper.sleep(l);
        return true;
    }
}

