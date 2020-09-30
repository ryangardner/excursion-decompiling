/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.cookie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.params.HttpParams;

public final class CookieSpecRegistry {
    private final ConcurrentHashMap<String, CookieSpecFactory> registeredSpecs = new ConcurrentHashMap();

    public CookieSpec getCookieSpec(String string2) throws IllegalStateException {
        return this.getCookieSpec(string2, null);
    }

    public CookieSpec getCookieSpec(String string2, HttpParams object) throws IllegalStateException {
        if (string2 == null) throw new IllegalArgumentException("Name may not be null");
        CookieSpecFactory cookieSpecFactory = this.registeredSpecs.get(string2.toLowerCase(Locale.ENGLISH));
        if (cookieSpecFactory != null) {
            return cookieSpecFactory.newInstance((HttpParams)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unsupported cookie spec: ");
        ((StringBuilder)object).append(string2);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public List<String> getSpecNames() {
        return new ArrayList<String>(this.registeredSpecs.keySet());
    }

    public void register(String string2, CookieSpecFactory cookieSpecFactory) {
        if (string2 == null) throw new IllegalArgumentException("Name may not be null");
        if (cookieSpecFactory == null) throw new IllegalArgumentException("Cookie spec factory may not be null");
        this.registeredSpecs.put(string2.toLowerCase(Locale.ENGLISH), cookieSpecFactory);
    }

    public void setItems(Map<String, CookieSpecFactory> map) {
        if (map == null) {
            return;
        }
        this.registeredSpecs.clear();
        this.registeredSpecs.putAll(map);
    }

    public void unregister(String string2) {
        if (string2 == null) throw new IllegalArgumentException("Id may not be null");
        this.registeredSpecs.remove(string2.toLowerCase(Locale.ENGLISH));
    }
}

