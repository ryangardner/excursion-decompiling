/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.encoder;

import java.util.Arrays;

public class DefaultPlacement {
    private final byte[] bits;
    private final CharSequence codewords;
    private final int numcols;
    private final int numrows;

    public DefaultPlacement(CharSequence arrby, int n, int n2) {
        this.codewords = arrby;
        this.numcols = n;
        this.numrows = n2;
        arrby = new byte[n * n2];
        this.bits = arrby;
        Arrays.fill(arrby, (byte)-1);
    }

    private void corner1(int n) {
        this.module(this.numrows - 1, 0, n, 1);
        this.module(this.numrows - 1, 1, n, 2);
        this.module(this.numrows - 1, 2, n, 3);
        this.module(0, this.numcols - 2, n, 4);
        this.module(0, this.numcols - 1, n, 5);
        this.module(1, this.numcols - 1, n, 6);
        this.module(2, this.numcols - 1, n, 7);
        this.module(3, this.numcols - 1, n, 8);
    }

    private void corner2(int n) {
        this.module(this.numrows - 3, 0, n, 1);
        this.module(this.numrows - 2, 0, n, 2);
        this.module(this.numrows - 1, 0, n, 3);
        this.module(0, this.numcols - 4, n, 4);
        this.module(0, this.numcols - 3, n, 5);
        this.module(0, this.numcols - 2, n, 6);
        this.module(0, this.numcols - 1, n, 7);
        this.module(1, this.numcols - 1, n, 8);
    }

    private void corner3(int n) {
        this.module(this.numrows - 3, 0, n, 1);
        this.module(this.numrows - 2, 0, n, 2);
        this.module(this.numrows - 1, 0, n, 3);
        this.module(0, this.numcols - 2, n, 4);
        this.module(0, this.numcols - 1, n, 5);
        this.module(1, this.numcols - 1, n, 6);
        this.module(2, this.numcols - 1, n, 7);
        this.module(3, this.numcols - 1, n, 8);
    }

    private void corner4(int n) {
        this.module(this.numrows - 1, 0, n, 1);
        this.module(this.numrows - 1, this.numcols - 1, n, 2);
        this.module(0, this.numcols - 3, n, 3);
        this.module(0, this.numcols - 2, n, 4);
        this.module(0, this.numcols - 1, n, 5);
        this.module(1, this.numcols - 3, n, 6);
        this.module(1, this.numcols - 2, n, 7);
        this.module(1, this.numcols - 1, n, 8);
    }

    private void module(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = n2;
        if (n < 0) {
            n6 = this.numrows;
            n5 = n + n6;
            n6 = n2 + (4 - (n6 + 4) % 8);
        }
        n2 = n5;
        n = n6;
        if (n6 < 0) {
            n2 = this.numcols;
            n = n6 + n2;
            n2 = n5 + (4 - (n2 + 4) % 8);
        }
        n3 = this.codewords.charAt(n3);
        boolean bl = true;
        if ((n3 & 1 << 8 - n4) == 0) {
            bl = false;
        }
        this.setBit(n, n2, bl);
    }

    private void utah(int n, int n2, int n3) {
        int n4 = n - 2;
        int n5 = n2 - 2;
        this.module(n4, n5, n3, 1);
        int n6 = n2 - 1;
        this.module(n4, n6, n3, 2);
        n4 = n - 1;
        this.module(n4, n5, n3, 3);
        this.module(n4, n6, n3, 4);
        this.module(n4, n2, n3, 5);
        this.module(n, n5, n3, 6);
        this.module(n, n6, n3, 7);
        this.module(n, n2, n3, 8);
    }

    public final boolean getBit(int n, int n2) {
        n = this.bits[n2 * this.numcols + n];
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    final byte[] getBits() {
        return this.bits;
    }

    final int getNumcols() {
        return this.numcols;
    }

    final int getNumrows() {
        return this.numrows;
    }

    final boolean hasBit(int n, int n2) {
        if (this.bits[n2 * this.numcols + n] < 0) return false;
        return true;
    }

    /*
     * Unable to fully structure code
     */
    public final void place() {
        var1_1 = 0;
        var2_2 = 0;
        var3_3 = 4;
        do lbl-1000: // 3 sources:
        {
            var4_4 = var2_2;
            if (var3_3 == this.numrows) {
                var4_4 = var2_2;
                if (var1_1 == 0) {
                    this.corner1(var2_2);
                    var4_4 = var2_2 + 1;
                }
            }
            var5_5 = var4_4;
            if (var3_3 == this.numrows - 2) {
                var5_5 = var4_4;
                if (var1_1 == 0) {
                    var5_5 = var4_4;
                    if (this.numcols % 4 != 0) {
                        this.corner2(var4_4);
                        var5_5 = var4_4 + 1;
                    }
                }
            }
            var2_2 = var5_5;
            if (var3_3 == this.numrows - 2) {
                var2_2 = var5_5;
                if (var1_1 == 0) {
                    var2_2 = var5_5;
                    if (this.numcols % 8 == 4) {
                        this.corner3(var5_5);
                        var2_2 = var5_5 + 1;
                    }
                }
            }
            var6_6 = var1_1;
            var4_4 = var2_2;
            var5_5 = var3_3;
            if (var3_3 == this.numrows + 4) {
                var6_6 = var1_1;
                var4_4 = var2_2;
                var5_5 = var3_3;
                if (var1_1 == 2) {
                    var6_6 = var1_1;
                    var4_4 = var2_2;
                    var5_5 = var3_3;
                    if (this.numcols % 8 == 0) {
                        this.corner4(var2_2);
                        var4_4 = var2_2 + 1;
                        var5_5 = var3_3;
                        var6_6 = var1_1;
                    }
                }
            }
            do {
                var3_3 = var4_4;
                if (var5_5 < this.numrows) {
                    var3_3 = var4_4;
                    if (var6_6 >= 0) {
                        var3_3 = var4_4;
                        if (!this.hasBit(var6_6, var5_5)) {
                            this.utah(var5_5, var6_6, var4_4);
                            var3_3 = var4_4 + 1;
                        }
                    }
                }
                var2_2 = var5_5 - 2;
                var1_1 = var6_6 + 2;
                if (var2_2 < 0) break;
                var6_6 = var1_1;
                var4_4 = var3_3;
                var5_5 = var2_2;
            } while (var1_1 < this.numcols);
            var4_4 = var2_2 + 1;
            var2_2 = var1_1 + 3;
            var1_1 = var4_4;
            do {
                var4_4 = var3_3;
                if (var1_1 >= 0) {
                    var4_4 = var3_3;
                    if (var2_2 < this.numcols) {
                        var4_4 = var3_3;
                        if (!this.hasBit(var2_2, var1_1)) {
                            this.utah(var1_1, var2_2, var3_3);
                            var4_4 = var3_3 + 1;
                        }
                    }
                }
                var5_5 = var1_1 + 2;
                var6_6 = var2_2 - 2;
                if (var5_5 >= this.numrows) break;
                var2_2 = var6_6;
                var3_3 = var4_4;
                var1_1 = var5_5;
            } while (var6_6 >= 0);
            var7_7 = this.numrows;
            var1_1 = ++var6_6;
            var2_2 = var4_4;
            var3_3 = var5_5 += 3;
            if (var5_5 < var7_7) ** GOTO lbl-1000
            var8_8 = this.numcols;
            var1_1 = var6_6;
            var2_2 = var4_4;
            var3_3 = var5_5;
        } while (var6_6 < var8_8);
        if (this.hasBit(var8_8 - 1, var7_7 - 1) != false) return;
        this.setBit(this.numcols - 1, this.numrows - 1, true);
        this.setBit(this.numcols - 2, this.numrows - 2, true);
    }

    final void setBit(int n, int n2, boolean bl) {
        this.bits[n2 * this.numcols + n] = (byte)(bl ? 1 : 0);
    }
}

