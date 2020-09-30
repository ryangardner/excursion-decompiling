/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.aztec.encoder;

import com.google.zxing.aztec.encoder.HighLevelEncoder;
import com.google.zxing.aztec.encoder.Token;
import com.google.zxing.common.BitArray;
import java.util.Iterator;
import java.util.LinkedList;

final class State {
    static final State INITIAL_STATE = new State(Token.EMPTY, 0, 0, 0);
    private final int binaryShiftByteCount;
    private final int bitCount;
    private final int mode;
    private final Token token;

    private State(Token token, int n, int n2, int n3) {
        this.token = token;
        this.mode = n;
        this.binaryShiftByteCount = n2;
        this.bitCount = n3;
    }

    State addBinaryShiftChar(int n) {
        int n2;
        int n3;
        Object object;
        int n4;
        Object object2;
        block4 : {
            int n5;
            block3 : {
                object = this.token;
                n5 = this.mode;
                n2 = this.bitCount;
                if (n5 == 4) break block3;
                object2 = object;
                n4 = n5;
                n3 = n2;
                if (n5 != 2) break block4;
            }
            n3 = HighLevelEncoder.LATCH_TABLE[n5][0];
            n4 = n3 >> 16;
            object2 = ((Token)object).add(65535 & n3, n4);
            n3 = n2 + n4;
            n4 = 0;
        }
        n2 = (n2 = this.binaryShiftByteCount) != 0 && n2 != 31 ? (n2 == 62 ? 9 : 8) : 18;
        object2 = object = new State((Token)object2, n4, this.binaryShiftByteCount + 1, n3 + n2);
        if (((State)object).binaryShiftByteCount != 2078) return object2;
        return ((State)object).endBinaryShift(n + 1);
    }

    State endBinaryShift(int n) {
        int n2 = this.binaryShiftByteCount;
        if (n2 != 0) return new State(this.token.addBinaryShift(n - n2, n2), this.mode, 0, this.bitCount);
        return this;
    }

    int getBinaryShiftByteCount() {
        return this.binaryShiftByteCount;
    }

    int getBitCount() {
        return this.bitCount;
    }

    int getMode() {
        return this.mode;
    }

    Token getToken() {
        return this.token;
    }

    boolean isBetterThanOrEqualTo(State state) {
        int n;
        block2 : {
            int n2;
            block3 : {
                n2 = this.bitCount + (HighLevelEncoder.LATCH_TABLE[this.mode][state.mode] >> 16);
                int n3 = state.binaryShiftByteCount;
                n = n2;
                if (n3 <= 0) break block2;
                int n4 = this.binaryShiftByteCount;
                if (n4 == 0) break block3;
                n = n2;
                if (n4 <= n3) break block2;
            }
            n = n2 + 10;
        }
        if (n > state.bitCount) return false;
        return true;
    }

    State latchAndAppend(int n, int n2) {
        int n3 = this.bitCount;
        Token token = this.token;
        int n4 = n3;
        Token token2 = token;
        if (n != this.mode) {
            int n5 = HighLevelEncoder.LATCH_TABLE[this.mode][n];
            n4 = n5 >> 16;
            token2 = token.add(65535 & n5, n4);
            n4 = n3 + n4;
        }
        if (n == 2) {
            n3 = 4;
            return new State(token2.add(n2, n3), n, 0, n4 + n3);
        }
        n3 = 5;
        return new State(token2.add(n2, n3), n, 0, n4 + n3);
    }

    State shiftAndAppend(int n, int n2) {
        int n3;
        Token token = this.token;
        if (this.mode == 2) {
            n3 = 4;
            return new State(token.add(HighLevelEncoder.SHIFT_TABLE[this.mode][n], n3).add(n2, 5), this.mode, 0, this.bitCount + n3 + 5);
        }
        n3 = 5;
        return new State(token.add(HighLevelEncoder.SHIFT_TABLE[this.mode][n], n3).add(n2, 5), this.mode, 0, this.bitCount + n3 + 5);
    }

    BitArray toBitArray(byte[] arrby) {
        Object object;
        Object object2 = new LinkedList<Token>();
        for (object = this.endBinaryShift((int)arrby.length).token; object != null; object = object.getPrevious()) {
            object2.addFirst(object);
        }
        object = new BitArray();
        object2 = object2.iterator();
        while (object2.hasNext()) {
            ((Token)object2.next()).appendTo((BitArray)object, arrby);
        }
        return object;
    }

    public String toString() {
        return String.format("%s bits=%d bytes=%d", HighLevelEncoder.MODE_NAMES[this.mode], this.bitCount, this.binaryShiftByteCount);
    }
}

