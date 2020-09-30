/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.sym.Name;

public final class Name2
extends Name {
    private final int q1;
    private final int q2;

    Name2(String string2, int n, int n2, int n3) {
        super(string2, n);
        this.q1 = n2;
        this.q2 = n3;
    }

    @Override
    public boolean equals(int n) {
        return false;
    }

    @Override
    public boolean equals(int n, int n2) {
        if (n != this.q1) return false;
        if (n2 != this.q2) return false;
        return true;
    }

    @Override
    public boolean equals(int n, int n2, int n3) {
        return false;
    }

    @Override
    public boolean equals(int[] arrn, int n) {
        boolean bl = true;
        if (n != 2) return false;
        if (arrn[0] != this.q1) return false;
        if (arrn[1] != this.q2) return false;
        return bl;
    }
}

