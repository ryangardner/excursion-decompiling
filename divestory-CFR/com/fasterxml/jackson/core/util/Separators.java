/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.util;

import java.io.Serializable;

public class Separators
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final char arrayValueSeparator;
    private final char objectEntrySeparator;
    private final char objectFieldValueSeparator;

    public Separators() {
        this(':', ',', ',');
    }

    public Separators(char c, char c2, char c3) {
        this.objectFieldValueSeparator = c;
        this.objectEntrySeparator = c2;
        this.arrayValueSeparator = c3;
    }

    public static Separators createDefaultInstance() {
        return new Separators();
    }

    public char getArrayValueSeparator() {
        return this.arrayValueSeparator;
    }

    public char getObjectEntrySeparator() {
        return this.objectEntrySeparator;
    }

    public char getObjectFieldValueSeparator() {
        return this.objectFieldValueSeparator;
    }

    public Separators withArrayValueSeparator(char c) {
        if (this.arrayValueSeparator != c) return new Separators(this.objectFieldValueSeparator, this.objectEntrySeparator, c);
        return this;
    }

    public Separators withObjectEntrySeparator(char c) {
        if (this.objectEntrySeparator != c) return new Separators(this.objectFieldValueSeparator, c, this.arrayValueSeparator);
        return this;
    }

    public Separators withObjectFieldValueSeparator(char c) {
        if (this.objectFieldValueSeparator != c) return new Separators(c, this.objectEntrySeparator, this.arrayValueSeparator);
        return this;
    }
}

