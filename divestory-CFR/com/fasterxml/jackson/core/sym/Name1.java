/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.sym.Name;

public final class Name1
extends Name {
    private static final Name1 EMPTY = new Name1("", 0, 0);
    private final int q;

    Name1(String string2, int n, int n2) {
        super(string2, n);
        this.q = n2;
    }

    public static Name1 getEmptyName() {
        return EMPTY;
    }

    @Override
    public boolean equals(int n) {
        if (n != this.q) return false;
        return true;
    }

    @Override
    public boolean equals(int n, int n2) {
        if (n != this.q) return false;
        if (n2 != 0) return false;
        return true;
    }

    @Override
    public boolean equals(int n, int n2, int n3) {
        return false;
    }

    @Override
    public boolean equals(int[] arrn, int n) {
        boolean bl;
        boolean bl2 = bl = false;
        if (n != 1) return bl2;
        bl2 = bl;
        if (arrn[0] != this.q) return bl2;
        return true;
    }
}

