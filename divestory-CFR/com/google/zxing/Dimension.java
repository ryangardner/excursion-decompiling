/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing;

public final class Dimension {
    private final int height;
    private final int width;

    public Dimension(int n, int n2) {
        if (n < 0) throw new IllegalArgumentException();
        if (n2 < 0) throw new IllegalArgumentException();
        this.width = n;
        this.height = n2;
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = object instanceof Dimension;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (Dimension)object;
        bl3 = bl;
        if (this.width != ((Dimension)object).width) return bl3;
        bl3 = bl;
        if (this.height != ((Dimension)object).height) return bl3;
        return true;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int hashCode() {
        return this.width * 32713 + this.height;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.width);
        stringBuilder.append("x");
        stringBuilder.append(this.height);
        return stringBuilder.toString();
    }
}

