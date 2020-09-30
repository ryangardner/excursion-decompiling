/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.Token;
import com.google.zxing.common.BitArray;

final class BinaryShiftToken
extends Token {
    private final short binaryShiftByteCount;
    private final short binaryShiftStart;

    BinaryShiftToken(Token token, int n, int n2) {
        super(token);
        this.binaryShiftStart = (short)n;
        this.binaryShiftByteCount = (short)n2;
    }

    @Override
    public void appendTo(BitArray bitArray, byte[] arrby) {
        int n;
        int n2 = 0;
        while (n2 < (n = this.binaryShiftByteCount)) {
            if (n2 == 0 || n2 == 31 && n <= 62) {
                bitArray.appendBits(31, 5);
                n = this.binaryShiftByteCount;
                if (n > 62) {
                    bitArray.appendBits(n - 31, 16);
                } else if (n2 == 0) {
                    bitArray.appendBits(Math.min(n, 31), 5);
                } else {
                    bitArray.appendBits(n - 31, 5);
                }
            }
            bitArray.appendBits(arrby[this.binaryShiftStart + n2], 8);
            ++n2;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");
        stringBuilder.append(this.binaryShiftStart);
        stringBuilder.append("::");
        stringBuilder.append(this.binaryShiftStart + this.binaryShiftByteCount - 1);
        stringBuilder.append('>');
        return stringBuilder.toString();
    }
}

