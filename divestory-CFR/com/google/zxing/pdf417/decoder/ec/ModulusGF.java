/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.pdf417.decoder.ec.ModulusPoly;

public final class ModulusGF {
    public static final ModulusGF PDF417_GF = new ModulusGF(929, 3);
    private final int[] expTable;
    private final int[] logTable;
    private final int modulus;
    private final ModulusPoly one;
    private final ModulusPoly zero;

    private ModulusGF(int n, int n2) {
        this.modulus = n;
        this.expTable = new int[n];
        this.logTable = new int[n];
        int n3 = 1;
        for (int i = 0; i < n; ++i) {
            this.expTable[i] = n3;
            n3 = n3 * n2 % n;
        }
        n2 = 0;
        do {
            if (n2 >= n - 1) {
                this.zero = new ModulusPoly(this, new int[]{0});
                this.one = new ModulusPoly(this, new int[]{1});
                return;
            }
            this.logTable[this.expTable[n2]] = n2;
            ++n2;
        } while (true);
    }

    int add(int n, int n2) {
        return (n + n2) % this.modulus;
    }

    ModulusPoly buildMonomial(int n, int n2) {
        if (n < 0) throw new IllegalArgumentException();
        if (n2 == 0) {
            return this.zero;
        }
        int[] arrn = new int[n + 1];
        arrn[0] = n2;
        return new ModulusPoly(this, arrn);
    }

    int exp(int n) {
        return this.expTable[n];
    }

    ModulusPoly getOne() {
        return this.one;
    }

    int getSize() {
        return this.modulus;
    }

    ModulusPoly getZero() {
        return this.zero;
    }

    int inverse(int n) {
        if (n == 0) throw new ArithmeticException();
        return this.expTable[this.modulus - this.logTable[n] - 1];
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
        return arrn[(arrn2[n] + arrn2[n2]) % (this.modulus - 1)];
    }

    int subtract(int n, int n2) {
        int n3 = this.modulus;
        return (n + n3 - n2) % n3;
    }
}

