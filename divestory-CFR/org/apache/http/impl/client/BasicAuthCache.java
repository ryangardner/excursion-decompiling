/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import java.util.HashMap;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScheme;
import org.apache.http.client.AuthCache;

public class BasicAuthCache
implements AuthCache {
    private final HashMap<HttpHost, AuthScheme> map = new HashMap();

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public AuthScheme get(HttpHost httpHost) {
        if (httpHost == null) throw new IllegalArgumentException("HTTP host may not be null");
        return this.map.get(httpHost);
    }

    @Override
    public void put(HttpHost httpHost, AuthScheme authScheme) {
        if (httpHost == null) throw new IllegalArgumentException("HTTP host may not be null");
        this.map.put(httpHost, authScheme);
    }

    @Override
    public void remove(HttpHost httpHost) {
        if (httpHost == null) throw new IllegalArgumentException("HTTP host may not be null");
        this.map.remove(httpHost);
    }

    public String toString() {
        return this.map.toString();
    }
}

