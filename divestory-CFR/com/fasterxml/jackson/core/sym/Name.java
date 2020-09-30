/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.sym;

public abstract class Name {
    protected final int _hashCode;
    protected final String _name;

    protected Name(String string2, int n) {
        this._name = string2;
        this._hashCode = n;
    }

    public abstract boolean equals(int var1);

    public abstract boolean equals(int var1, int var2);

    public abstract boolean equals(int var1, int var2, int var3);

    public boolean equals(Object object) {
        if (object != this) return false;
        return true;
    }

    public abstract boolean equals(int[] var1, int var2);

    public String getName() {
        return this._name;
    }

    public final int hashCode() {
        return this._hashCode;
    }

    public String toString() {
        return this._name;
    }
}

