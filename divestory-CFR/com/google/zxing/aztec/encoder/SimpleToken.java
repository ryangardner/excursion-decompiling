/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.Token;
import com.google.zxing.common.BitArray;

final class SimpleToken
extends Token {
    private final short bitCount;
    private final short value;

    SimpleToken(Token token, int n, int n2) {
        super(token);
        this.value = (short)n;
        this.bitCount = (short)n2;
    }

    @Override
    void appendTo(BitArray bitArray, byte[] arrby) {
        bitArray.appendBits(this.value, this.bitCount);
    }

    public String toString() {
        short s = this.value;
        short s2 = this.bitCount;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('<');
        stringBuilder.append(Integer.toBinaryString(s & (1 << s2) - 1 | 1 << s2 | 1 << this.bitCount).substring(1));
        stringBuilder.append('>');
        return stringBuilder.toString();
    }
}

