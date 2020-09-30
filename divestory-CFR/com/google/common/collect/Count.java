/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Count
implements Serializable {
    private int value;

    Count(int n) {
        this.value = n;
    }

    public void add(int n) {
        this.value += n;
    }

    public int addAndGet(int n) {
        this.value = n = this.value + n;
        return n;
    }

    public boolean equals(@NullableDecl Object object) {
        if (!(object instanceof Count)) return false;
        if (((Count)object).value != this.value) return false;
        return true;
    }

    public int get() {
        return this.value;
    }

    public int getAndSet(int n) {
        int n2 = this.value;
        this.value = n;
        return n2;
    }

    public int hashCode() {
        return this.value;
    }

    public void set(int n) {
        this.value = n;
    }

    public String toString() {
        return Integer.toString(this.value);
    }
}

