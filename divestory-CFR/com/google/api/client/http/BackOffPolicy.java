/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import java.io.IOException;

@Deprecated
public interface BackOffPolicy {
    public static final long STOP = -1L;

    public long getNextBackOffMillis() throws IOException;

    public boolean isBackOffRequired(int var1);

    public void reset();
}

