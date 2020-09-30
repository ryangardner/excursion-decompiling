/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.params.HttpParams;

public final class AuthSchemeRegistry {
    private final ConcurrentHashMap<String, AuthSchemeFactory> registeredSchemes = new ConcurrentHashMap();

    public AuthScheme getAuthScheme(String string2, HttpParams object) throws IllegalStateException {
        if (string2 == null) throw new IllegalArgumentException("Name may not be null");
        AuthSchemeFactory authSchemeFactory = this.registeredSchemes.get(string2.toLowerCase(Locale.ENGLISH));
        if (authSchemeFactory != null) {
            return authSchemeFactory.newInstance((HttpParams)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unsupported authentication scheme: ");
        ((StringBuilder)object).append(string2);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public List<String> getSchemeNames() {
        return new ArrayList<String>(this.registeredSchemes.keySet());
    }

    public void register(String string2, AuthSchemeFactory authSchemeFactory) {
        if (string2 == null) throw new IllegalArgumentException("Name may not be null");
        if (authSchemeFactory == null) throw new IllegalArgumentException("Authentication scheme factory may not be null");
        this.registeredSchemes.put(string2.toLowerCase(Locale.ENGLISH), authSchemeFactory);
    }

    public void setItems(Map<String, AuthSchemeFactory> map) {
        if (map == null) {
            return;
        }
        this.registeredSchemes.clear();
        this.registeredSchemes.putAll(map);
    }

    public void unregister(String string2) {
        if (string2 == null) throw new IllegalArgumentException("Name may not be null");
        this.registeredSchemes.remove(string2.toLowerCase(Locale.ENGLISH));
    }
}

