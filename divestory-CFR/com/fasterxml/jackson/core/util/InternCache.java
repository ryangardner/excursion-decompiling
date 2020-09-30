/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.util;

import java.util.concurrent.ConcurrentHashMap;

public final class InternCache
extends ConcurrentHashMap<String, String> {
    private static final int MAX_ENTRIES = 180;
    public static final InternCache instance = new InternCache();
    private static final long serialVersionUID = 1L;
    private final Object lock = new Object();

    private InternCache() {
        super(180, 0.8f, 4);
    }

    public String intern(String string2) {
        Object object = (String)this.get(string2);
        if (object != null) {
            return object;
        }
        if (this.size() >= 180) {
            object = this.lock;
            synchronized (object) {
                if (this.size() >= 180) {
                    this.clear();
                }
            }
        }
        string2 = string2.intern();
        this.put(string2, string2);
        return string2;
    }
}

