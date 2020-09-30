/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Ordering;
import java.io.Serializable;

final class UsingToStringOrdering
extends Ordering<Object>
implements Serializable {
    static final UsingToStringOrdering INSTANCE = new UsingToStringOrdering();
    private static final long serialVersionUID = 0L;

    private UsingToStringOrdering() {
    }

    private Object readResolve() {
        return INSTANCE;
    }

    @Override
    public int compare(Object object, Object object2) {
        return object.toString().compareTo(object2.toString());
    }

    public String toString() {
        return "Ordering.usingToString()";
    }
}

