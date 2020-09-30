/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.sym.Name;
import java.util.Arrays;

public final class NameN
extends Name {
    private final int[] q;
    private final int q1;
    private final int q2;
    private final int q3;
    private final int q4;
    private final int qlen;

    NameN(String string2, int n, int n2, int n3, int n4, int n5, int[] arrn, int n6) {
        super(string2, n);
        this.q1 = n2;
        this.q2 = n3;
        this.q3 = n4;
        this.q4 = n5;
        this.q = arrn;
        this.qlen = n6;
    }

    private final boolean _equals2(int[] arrn) {
        int n = this.qlen;
        int n2 = 0;
        while (n2 < n - 4) {
            if (arrn[n2 + 4] != this.q[n2]) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    public static NameN construct(String string2, int n, int[] arrn, int n2) {
        if (n2 < 4) throw new IllegalArgumentException();
        int n3 = arrn[0];
        int n4 = arrn[1];
        int n5 = arrn[2];
        int n6 = arrn[3];
        if (n2 - 4 > 0) {
            arrn = Arrays.copyOfRange(arrn, 4, n2);
            return new NameN(string2, n, n3, n4, n5, n6, arrn, n2);
        }
        arrn = null;
        return new NameN(string2, n, n3, n4, n5, n6, arrn, n2);
    }

    @Override
    public boolean equals(int n) {
        return false;
    }

    @Override
    public boolean equals(int n, int n2) {
        return false;
    }

    @Override
    public boolean equals(int n, int n2, int n3) {
        return false;
    }

    @Override
    public boolean equals(int[] arrn, int n) {
        if (n != this.qlen) {
            return false;
        }
        if (arrn[0] != this.q1) {
            return false;
        }
        if (arrn[1] != this.q2) {
            return false;
        }
        if (arrn[2] != this.q3) {
            return false;
        }
        if (arrn[3] != this.q4) {
            return false;
        }
        switch (n) {
            default: {
                return this._equals2(arrn);
            }
            case 8: {
                if (arrn[7] != this.q[3]) {
                    return false;
                }
            }
            case 7: {
                if (arrn[6] != this.q[2]) {
                    return false;
                }
            }
            case 6: {
                if (arrn[5] != this.q[1]) {
                    return false;
                }
            }
            case 5: {
                if (arrn[4] == this.q[0]) return true;
                return false;
            }
            case 4: 
        }
        return true;
    }
}

