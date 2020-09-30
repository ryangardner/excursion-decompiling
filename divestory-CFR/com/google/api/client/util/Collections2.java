/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import java.util.Collection;

public final class Collections2 {
    private Collections2() {
    }

    static <T> Collection<T> cast(Iterable<T> iterable) {
        return (Collection)iterable;
    }
}

