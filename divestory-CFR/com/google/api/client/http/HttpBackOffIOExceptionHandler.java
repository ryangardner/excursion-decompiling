/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.HttpIOExceptionHandler;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.util.BackOff;
import com.google.api.client.util.BackOffUtils;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.IOException;

public class HttpBackOffIOExceptionHandler
implements HttpIOExceptionHandler {
    private final BackOff backOff;
    private Sleeper sleeper = Sleeper.DEFAULT;

    public HttpBackOffIOExceptionHandler(BackOff backOff) {
        this.backOff = Preconditions.checkNotNull(backOff);
    }

    public final BackOff getBackOff() {
        return this.backOff;
    }

    public final Sleeper getSleeper() {
        return this.sleeper;
    }

    @Override
    public boolean handleIOException(HttpRequest httpRequest, boolean bl) throws IOException {
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        try {
            return BackOffUtils.next(this.sleeper, this.backOff);
        }
        catch (InterruptedException interruptedException) {
            return bl2;
        }
    }

    public HttpBackOffIOExceptionHandler setSleeper(Sleeper sleeper) {
        this.sleeper = Preconditions.checkNotNull(sleeper);
        return this;
    }
}

