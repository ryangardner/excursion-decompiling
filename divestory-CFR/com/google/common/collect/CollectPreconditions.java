/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;

final class CollectPreconditions {
    CollectPreconditions() {
    }

    static void checkEntryNotNull(Object object, Object object2) {
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("null key in entry: null=");
            ((StringBuilder)object).append(object2);
            throw new NullPointerException(((StringBuilder)object).toString());
        }
        if (object2 != null) {
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("null value in entry: ");
        ((StringBuilder)object2).append(object);
        ((StringBuilder)object2).append("=null");
        throw new NullPointerException(((StringBuilder)object2).toString());
    }

    static int checkNonnegative(int n, String string2) {
        if (n >= 0) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" cannot be negative but was: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static long checkNonnegative(long l, String string2) {
        if (l >= 0L) {
            return l;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" cannot be negative but was: ");
        stringBuilder.append(l);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static void checkPositive(int n, String string2) {
        if (n > 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" must be positive but was: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static void checkRemove(boolean bl) {
        Preconditions.checkState(bl, "no calls to next() since the last call to remove()");
    }
}

