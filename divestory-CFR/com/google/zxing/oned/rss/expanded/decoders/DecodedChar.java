/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.oned.rss.expanded.decoders.DecodedObject;

final class DecodedChar
extends DecodedObject {
    static final char FNC1 = '$';
    private final char value;

    DecodedChar(int n, char c) {
        super(n);
        this.value = c;
    }

    char getValue() {
        return this.value;
    }

    boolean isFNC1() {
        if (this.value != '$') return false;
        return true;
    }
}

