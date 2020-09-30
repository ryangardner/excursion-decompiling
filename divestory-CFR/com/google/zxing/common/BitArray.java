/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common;

import java.util.Arrays;

public final class BitArray
implements Cloneable {
    private int[] bits;
    private int size;

    public BitArray() {
        this.size = 0;
        this.bits = new int[1];
    }

    public BitArray(int n) {
        this.size = n;
        this.bits = BitArray.makeArray(n);
    }

    BitArray(int[] arrn, int n) {
        this.bits = arrn;
        this.size = n;
    }

    private void ensureCapacity(int n) {
        if (n <= this.bits.length * 32) return;
        int[] arrn = BitArray.makeArray(n);
        int[] arrn2 = this.bits;
        System.arraycopy(arrn2, 0, arrn, 0, arrn2.length);
        this.bits = arrn;
    }

    private static int[] makeArray(int n) {
        return new int[(n + 31) / 32];
    }

    public void appendBit(boolean bl) {
        this.ensureCapacity(this.size + 1);
        if (bl) {
            int[] arrn = this.bits;
            int n = this.size;
            int n2 = n / 32;
            arrn[n2] = 1 << (n & 31) | arrn[n2];
        }
        ++this.size;
    }

    public void appendBitArray(BitArray bitArray) {
        int n = bitArray.size;
        this.ensureCapacity(this.size + n);
        int n2 = 0;
        while (n2 < n) {
            this.appendBit(bitArray.get(n2));
            ++n2;
        }
    }

    public void appendBits(int n, int n2) {
        if (n2 < 0) throw new IllegalArgumentException("Num bits must be between 0 and 32");
        if (n2 > 32) throw new IllegalArgumentException("Num bits must be between 0 and 32");
        this.ensureCapacity(this.size + n2);
        while (n2 > 0) {
            boolean bl = true;
            if ((n >> n2 - 1 & 1) != 1) {
                bl = false;
            }
            this.appendBit(bl);
            --n2;
        }
    }

    public void clear() {
        int n = this.bits.length;
        int n2 = 0;
        while (n2 < n) {
            this.bits[n2] = 0;
            ++n2;
        }
    }

    public BitArray clone() {
        return new BitArray((int[])this.bits.clone(), this.size);
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof BitArray;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (BitArray)object;
        bl = bl2;
        if (this.size != ((BitArray)object).size) return bl;
        bl = bl2;
        if (!Arrays.equals(this.bits, ((BitArray)object).bits)) return bl;
        return true;
    }

    public void flip(int n) {
        int[] arrn = this.bits;
        int n2 = n / 32;
        arrn[n2] = 1 << (n & 31) ^ arrn[n2];
    }

    public boolean get(int n) {
        int n2 = this.bits[n / 32];
        boolean bl = true;
        if ((1 << (n & 31) & n2) == 0) return false;
        return bl;
    }

    public int[] getBitArray() {
        return this.bits;
    }

    public int getNextSet(int n) {
        int n2 = this.size;
        if (n >= n2) {
            return n2;
        }
        int n3 = n / 32;
        n2 = (1 << (n & 31)) - 1 & this.bits[n3];
        n = n3;
        do {
            if (n2 != 0) {
                n3 = n * 32 + Integer.numberOfTrailingZeros(n2);
                n2 = this.size;
                n = n3;
                if (n3 <= n2) return n;
                return n2;
            }
            int[] arrn = this.bits;
            if (++n == arrn.length) {
                return this.size;
            }
            n2 = arrn[n];
        } while (true);
    }

    public int getNextUnset(int n) {
        int n2 = this.size;
        if (n >= n2) {
            return n2;
        }
        n2 = n / 32;
        n = (1 << (n & 31)) - 1 & this.bits[n2];
        do {
            if (n != 0) {
                int n3 = n2 * 32 + Integer.numberOfTrailingZeros(n);
                n2 = this.size;
                n = n3;
                if (n3 <= n2) return n;
                return n2;
            }
            int[] arrn = this.bits;
            if (++n2 == arrn.length) {
                return this.size;
            }
            n = arrn[n2];
        } while (true);
    }

    public int getSize() {
        return this.size;
    }

    public int getSizeInBytes() {
        return (this.size + 7) / 8;
    }

    public int hashCode() {
        return this.size * 31 + Arrays.hashCode(this.bits);
    }

    public boolean isRange(int n, int n2, boolean bl) {
        if (n2 < n) throw new IllegalArgumentException();
        if (n2 == n) {
            return true;
        }
        int n3 = n2 - 1;
        int n4 = n / 32;
        int n5 = n3 / 32;
        int n6 = n4;
        while (n6 <= n5) {
            int n7;
            n2 = n6 > n4 ? 0 : n & 31;
            int n8 = n6 < n5 ? 31 : n3 & 31;
            if (n2 == 0 && n8 == 31) {
                n2 = -1;
            } else {
                n7 = 0;
                int n9 = n2;
                do {
                    n2 = n7;
                    if (n9 > n8) break;
                    n7 |= 1 << n9;
                    ++n9;
                } while (true);
            }
            n8 = this.bits[n6];
            n7 = bl ? n2 : 0;
            if ((n8 & n2) != n7) {
                return false;
            }
            ++n6;
        }
        return true;
    }

    public void reverse() {
        int n;
        int[] arrn = new int[this.bits.length];
        int n2 = (this.size - 1) / 32;
        int n3 = n2 + 1;
        for (n = 0; n < n3; ++n) {
            long l = this.bits[n];
            l = (l & 0x55555555L) << 1 | l >> 1 & 0x55555555L;
            l = (l & 0x33333333L) << 2 | l >> 2 & 0x33333333L;
            l = (l & 0xF0F0F0FL) << 4 | l >> 4 & 0xF0F0F0FL;
            l = (l & 0xFF00FFL) << 8 | l >> 8 & 0xFF00FFL;
            arrn[n2 - n] = (int)((l & 65535L) << 16 | l >> 16 & 65535L);
        }
        n = this.size;
        n2 = n3 * 32;
        if (n != n2) {
            int n4 = n2 - n;
            n = 1;
            for (n2 = 0; n2 < 31 - n4; ++n2) {
                n = n << 1 | 1;
            }
            int n5 = arrn[0] >> n4 & n;
            for (n2 = 1; n2 < n3; ++n2) {
                int n6 = arrn[n2];
                arrn[n2 - 1] = n5 | n6 << 32 - n4;
                n5 = n6 >> n4 & n;
            }
            arrn[n3 - 1] = n5;
        }
        this.bits = arrn;
    }

    public void set(int n) {
        int[] arrn = this.bits;
        int n2 = n / 32;
        arrn[n2] = 1 << (n & 31) | arrn[n2];
    }

    public void setBulk(int n, int n2) {
        this.bits[n / 32] = n2;
    }

    /*
     * Unable to fully structure code
     */
    public void setRange(int var1_1, int var2_2) {
        if (var2_2 < var1_1) throw new IllegalArgumentException();
        if (var2_2 == var1_1) {
            return;
        }
        var3_3 = var2_2 - 1;
        var4_4 = var1_1 / 32;
        var5_5 = var3_3 / 32;
        var6_6 = var4_4;
        while (var6_6 <= var5_5) {
            var7_7 = 0;
            var8_8 = var6_6 > var4_4 ? 0 : var1_1 & 31;
            var9_9 = var6_6 < var5_5 ? 31 : var3_3 & 31;
            var2_2 = var7_7;
            var10_10 = var8_8;
            if (var8_8 != 0) ** GOTO lbl-1000
            var2_2 = var7_7;
            var10_10 = var8_8;
            if (var9_9 == 31) {
                var8_8 = -1;
            } else lbl-1000: // 2 sources:
            {
                do {
                    var8_8 = var2_2;
                    if (var10_10 > var9_9) break;
                    var2_2 |= 1 << var10_10;
                    ++var10_10;
                } while (true);
            }
            var11_11 = this.bits;
            var11_11[var6_6] = var8_8 | var11_11[var6_6];
            ++var6_6;
        }
    }

    public void toBytes(int n, byte[] arrby, int n2, int n3) {
        int n4 = 0;
        int n5 = n;
        n = n4;
        while (n < n3) {
            int n6 = 0;
            for (int i = 0; i < 8; ++n5, ++i) {
                n4 = n6;
                if (this.get(n5)) {
                    n4 = n6 | 1 << 7 - i;
                }
                n6 = n4;
            }
            arrby[n2 + n] = (byte)n6;
            ++n;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.size);
        int n = 0;
        while (n < this.size) {
            char c;
            if ((n & 7) == 0) {
                stringBuilder.append(' ');
            }
            char c2 = this.get(n) ? (c = 'X') : (c = '.');
            stringBuilder.append(c2);
            ++n;
        }
        return stringBuilder.toString();
    }

    public void xor(BitArray bitArray) {
        int[] arrn;
        if (this.bits.length != bitArray.bits.length) throw new IllegalArgumentException("Sizes don't match");
        int n = 0;
        while (n < (arrn = this.bits).length) {
            arrn[n] = arrn[n] ^ bitArray.bits[n];
            ++n;
        }
    }
}

