/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.BinaryShiftToken;
import com.google.zxing.aztec.encoder.SimpleToken;
import com.google.zxing.common.BitArray;

abstract class Token {
    static final Token EMPTY = new SimpleToken(null, 0, 0);
    private final Token previous;

    Token(Token token) {
        this.previous = token;
    }

    final Token add(int n, int n2) {
        return new SimpleToken(this, n, n2);
    }

    final Token addBinaryShift(int n, int n2) {
        return new BinaryShiftToken(this, n, n2);
    }

    abstract void appendTo(BitArray var1, byte[] var2);

    final Token getPrevious() {
        return this.previous;
    }
}

