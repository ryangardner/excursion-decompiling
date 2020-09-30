/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common.reedsolomon;

import com.google.zxing.common.reedsolomon.GenericGFPoly;

public final class GenericGF {
    public static final GenericGF AZTEC_DATA_10;
    public static final GenericGF AZTEC_DATA_12;
    public static final GenericGF AZTEC_DATA_6;
    public static final GenericGF AZTEC_DATA_8;
    public static final GenericGF AZTEC_PARAM;
    public static final GenericGF DATA_MATRIX_FIELD_256;
    public static final GenericGF MAXICODE_FIELD_64;
    public static final GenericGF QR_CODE_FIELD_256;
    private final int[] expTable;
    private final int generatorBase;
    private final int[] logTable;
    private final GenericGFPoly one;
    private final int primitive;
    private final int size;
    private final GenericGFPoly zero;

    static {
        GenericGF genericGF;
        AZTEC_DATA_12 = new GenericGF(4201, 4096, 1);
        AZTEC_DATA_10 = new GenericGF(1033, 1024, 1);
        AZTEC_DATA_6 = new GenericGF(67, 64, 1);
        AZTEC_PARAM = new GenericGF(19, 16, 1);
        QR_CODE_FIELD_256 = new GenericGF(285, 256, 0);
        DATA_MATRIX_FIELD_256 = genericGF = new GenericGF(301, 256, 1);
        AZTEC_DATA_8 = genericGF;
        MAXICODE_FIELD_64 = AZTEC_DATA_6;
    }

    public GenericGF(int n, int n2, int n3) {
        this.primitive = n;
        this.size = n2;
        this.generatorBase = n3;
        this.expTable = new int[n2];
        this.logTable = new int[n2];
        n3 = 1;
        for (int i = 0; i < n2; ++i) {
            int n4;
            this.expTable[i] = n3;
            n3 = n4 = n3 * 2;
            if (n4 < n2) continue;
            n3 = (n4 ^ n) & n2 - 1;
        }
        n = 0;
        do {
            if (n >= n2 - 1) {
                this.zero = new GenericGFPoly(this, new int[]{0});
                this.one = new GenericGFPoly(this, new int[]{1});
                return;
            }
            this.logTable[this.expTable[n]] = n;
            ++n;
        } while (true);
    }

    static int addOrSubtract(int n, int n2) {
        return n ^ n2;
    }

    GenericGFPoly buildMonomial(int n, int n2) {
        if (n < 0) throw new IllegalArgumentException();
        if (n2 == 0) {
            return this.zero;
        }
        int[] arrn = new int[n + 1];
        arrn[0] = n2;
        return new GenericGFPoly(this, arrn);
    }

    int exp(int n) {
        return this.expTable[n];
    }

    public int getGeneratorBase() {
        return this.generatorBase;
    }

    GenericGFPoly getOne() {
        return this.one;
    }

    public int getSize() {
        return this.size;
    }

    GenericGFPoly getZero() {
        return this.zero;
    }

    int inverse(int n) {
        if (n == 0) throw new ArithmeticException();
        return this.expTable[this.size - this.logTable[n] - 1];
    }

    int log(int n) {
        if (n == 0) throw new IllegalArgumentException();
        return this.logTable[n];
    }

    int multiply(int n, int n2) {
        if (n == 0) return 0;
        if (n2 == 0) {
            return 0;
        }
        int[] arrn = this.expTable;
        int[] arrn2 = this.logTable;
        return arrn[(arrn2[n] + arrn2[n2]) % (this.size - 1)];
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GF(0x");
        stringBuilder.append(Integer.toHexString(this.primitive));
        stringBuilder.append(',');
        stringBuilder.append(this.size);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

