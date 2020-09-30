/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;

public class BasicCredentialsProvider
implements CredentialsProvider {
    private final ConcurrentHashMap<AuthScope, Credentials> credMap = new ConcurrentHashMap();

    private static Credentials matchCredentials(Map<AuthScope, Credentials> map, AuthScope authScope) {
        Credentials credentials = map.get(authScope);
        Object object = credentials;
        if (credentials != null) return object;
        int n = -1;
        Object object2 = null;
        Iterator<AuthScope> iterator2 = map.keySet().iterator();
        do {
            if (!iterator2.hasNext()) {
                object = credentials;
                if (object2 == null) return object;
                return map.get(object2);
            }
            object = iterator2.next();
            int n2 = authScope.match((AuthScope)object);
            if (n2 <= n) continue;
            object2 = object;
            n = n2;
        } while (true);
    }

    @Override
    public void clear() {
        this.credMap.clear();
    }

    @Override
    public Credentials getCredentials(AuthScope authScope) {
        if (authScope == null) throw new IllegalArgumentException("Authentication scope may not be null");
        return BasicCredentialsProvider.matchCredentials(this.credMap, authScope);
    }

    @Override
    public void setCredentials(AuthScope authScope, Credentials credentials) {
        if (authScope == null) throw new IllegalArgumentException("Authentication scope may not be null");
        this.credMap.put(authScope, credentials);
    }

    public String toString() {
        return this.credMap.toString();
    }
}

