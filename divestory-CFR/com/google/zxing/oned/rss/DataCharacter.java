/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss;

public class DataCharacter {
    private final int checksumPortion;
    private final int value;

    public DataCharacter(int n, int n2) {
        this.value = n;
        this.checksumPortion = n2;
    }

    public final boolean equals(Object object) {
        boolean bl = object instanceof DataCharacter;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (DataCharacter)object;
        bl = bl2;
        if (this.value != ((DataCharacter)object).value) return bl;
        bl = bl2;
        if (this.checksumPortion != ((DataCharacter)object).checksumPortion) return bl;
        return true;
    }

    public final int getChecksumPortion() {
        return this.checksumPortion;
    }

    public final int getValue() {
        return this.value;
    }

    public final int hashCode() {
        return this.value ^ this.checksumPortion;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.value);
        stringBuilder.append("(");
        stringBuilder.append(this.checksumPortion);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

