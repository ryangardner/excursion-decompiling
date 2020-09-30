/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScheme;

public interface AuthCache {
    public void clear();

    public AuthScheme get(HttpHost var1);

    public void put(HttpHost var1, AuthScheme var2);

    public void remove(HttpHost var1);
}

