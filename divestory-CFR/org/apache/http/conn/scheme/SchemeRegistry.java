/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.scheme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpHost;
import org.apache.http.conn.scheme.Scheme;

public final class SchemeRegistry {
    private final ConcurrentHashMap<String, Scheme> registeredSchemes = new ConcurrentHashMap();

    public final Scheme get(String string2) {
        if (string2 == null) throw new IllegalArgumentException("Name must not be null.");
        return this.registeredSchemes.get(string2);
    }

    public final Scheme getScheme(String string2) {
        Object object = this.get(string2);
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Scheme '");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("' not registered.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public final Scheme getScheme(HttpHost httpHost) {
        if (httpHost == null) throw new IllegalArgumentException("Host must not be null.");
        return this.getScheme(httpHost.getSchemeName());
    }

    public final List<String> getSchemeNames() {
        return new ArrayList<String>(this.registeredSchemes.keySet());
    }

    public final Scheme register(Scheme scheme) {
        if (scheme == null) throw new IllegalArgumentException("Scheme must not be null.");
        return this.registeredSchemes.put(scheme.getName(), scheme);
    }

    public void setItems(Map<String, Scheme> map) {
        if (map == null) {
            return;
        }
        this.registeredSchemes.clear();
        this.registeredSchemes.putAll(map);
    }

    public final Scheme unregister(String string2) {
        if (string2 == null) throw new IllegalArgumentException("Name must not be null.");
        return this.registeredSchemes.remove(string2);
    }
}

