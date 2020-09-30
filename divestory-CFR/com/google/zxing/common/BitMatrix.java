/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common;

import com.google.zxing.common.BitArray;
import java.util.Arrays;

public final class BitMatrix
implements Cloneable {
    private final int[] bits;
    private final int height;
    private final int rowSize;
    private final int width;

    public BitMatrix(int n) {
        this(n, n);
    }

    public BitMatrix(int n, int n2) {
        if (n < 1) throw new IllegalArgumentException("Both dimensions must be greater than 0");
        if (n2 < 1) throw new IllegalArgumentException("Both dimensions must be greater than 0");
        this.width = n;
        this.height = n2;
        this.rowSize = n = (n + 31) / 32;
        this.bits = new int[n * n2];
    }

    private BitMatrix(int n, int n2, int n3, int[] arrn) {
        this.width = n;
        this.height = n2;
        this.rowSize = n3;
        this.bits = arrn;
    }

    public static BitMatrix parse(String object, String charSequence, String string2) {
        int n;
        if (object == null) throw new IllegalArgumentException();
        boolean[] arrbl = new boolean[((String)object).length()];
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = -1;
        int n7 = 0;
        while (n3 < ((String)object).length()) {
            if (((String)object).charAt(n3) != '\n' && ((String)object).charAt(n3) != '\r') {
                if (((String)object).substring(n3, ((String)charSequence).length() + n3).equals(charSequence)) {
                    n3 += ((String)charSequence).length();
                    arrbl[n4] = true;
                } else {
                    if (!((String)object).substring(n3, string2.length() + n3).equals(string2)) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("illegal character encountered: ");
                        ((StringBuilder)charSequence).append(((String)object).substring(n3));
                        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                    }
                    n3 += string2.length();
                    arrbl[n4] = false;
                }
                ++n4;
                continue;
            }
            n = n5;
            int n8 = n6;
            int n9 = n7;
            if (n4 > n5) {
                if (n6 == -1) {
                    n6 = n4 - n5;
                } else if (n4 - n5 != n6) throw new IllegalArgumentException("row lengths do not match");
                n9 = n7 + 1;
                n = n4;
                n8 = n6;
            }
            ++n3;
            n5 = n;
            n6 = n8;
            n7 = n9;
        }
        n3 = n6;
        n = n7;
        if (n4 > n5) {
            if (n6 == -1) {
                n6 = n4 - n5;
            } else if (n4 - n5 != n6) throw new IllegalArgumentException("row lengths do not match");
            n = n7 + 1;
            n3 = n6;
        }
        object = new BitMatrix(n3, n);
        n6 = n2;
        while (n6 < n4) {
            if (arrbl[n6]) {
                ((BitMatrix)object).set(n6 % n3, n6 / n3);
            }
            ++n6;
        }
        return object;
    }

    public void clear() {
        int n = this.bits.length;
        int n2 = 0;
        while (n2 < n) {
            this.bits[n2] = 0;
            ++n2;
        }
    }

    public BitMatrix clone() {
        return new BitMatrix(this.width, this.height, this.rowSize, (int[])this.bits.clone());
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof BitMatrix;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (BitMatrix)object;
        bl = bl2;
        if (this.width != ((BitMatrix)object).width) return bl;
        bl = bl2;
        if (this.height != ((BitMatrix)object).height) return bl;
        bl = bl2;
        if (this.rowSize != ((BitMatrix)object).rowSize) return bl;
        bl = bl2;
        if (!Arrays.equals(this.bits, ((BitMatrix)object).bits)) return bl;
        return true;
    }

    public void flip(int n, int n2) {
        n2 = n2 * this.rowSize + n / 32;
        int[] arrn = this.bits;
        arrn[n2] = 1 << (n & 31) ^ arrn[n2];
    }

    public boolean get(int n, int n2) {
        int n3 = this.rowSize;
        int n4 = n / 32;
        n2 = this.bits[n2 * n3 + n4];
        boolean bl = true;
        if ((n2 >>> (n & 31) & 1) == 0) return false;
        return bl;
    }

    public int[] getBottomRightOnBit() {
        int n;
        for (n = this.bits.length - 1; n >= 0 && this.bits[n] == 0; --n) {
        }
        if (n < 0) {
            return null;
        }
        int n2 = this.rowSize;
        int n3 = n / n2;
        int n4 = this.bits[n];
        int n5 = 31;
        while (n4 >>> n5 == 0) {
            --n5;
        }
        return new int[]{n % n2 * 32 + n5, n3};
    }

    public int[] getEnclosingRectangle() {
        int n = this.width;
        int n2 = this.height;
        int n3 = -1;
        int n4 = -1;
        int n5 = 0;
        do {
            int n6;
            if (n5 < this.height) {
            } else {
                n5 = n3 - n;
                n6 = n4 - n2;
                if (n5 < 0) return null;
                if (n6 >= 0) return new int[]{n, n2, n5, n6};
                return null;
            }
            for (int i = 0; i < (n6 = this.rowSize); ++i) {
                int n7 = this.bits[n6 * n5 + i];
                int n8 = n;
                int n9 = n2;
                int n10 = n3;
                int n11 = n4;
                if (n7 != 0) {
                    n6 = n2;
                    if (n5 < n2) {
                        n6 = n5;
                    }
                    n2 = n4;
                    if (n5 > n4) {
                        n2 = n5;
                    }
                    int n12 = i * 32;
                    n4 = n;
                    if (n12 < n) {
                        n4 = 0;
                        while (n7 << 31 - n4 == 0) {
                            ++n4;
                        }
                        n11 = n4 + n12;
                        n4 = n;
                        if (n11 < n) {
                            n4 = n11;
                        }
                    }
                    n8 = n4;
                    n9 = n6;
                    n10 = n3;
                    n11 = n2;
                    if (n12 + 31 > n3) {
                        n = 31;
                        while (n7 >>> n == 0) {
                            --n;
                        }
                        n = n12 + n;
                        n8 = n4;
                        n9 = n6;
                        n10 = n3;
                        n11 = n2;
                        if (n > n3) {
                            n10 = n;
                            n11 = n2;
                            n9 = n6;
                            n8 = n4;
                        }
                    }
                }
                n = n8;
                n2 = n9;
                n3 = n10;
                n4 = n11;
            }
            ++n5;
        } while (true);
    }

    public int getHeight() {
        return this.height;
    }

    public BitArray getRow(int n, BitArray bitArray) {
        if (bitArray != null && bitArray.getSize() >= this.width) {
            bitArray.clear();
        } else {
            bitArray = new BitArray(this.width);
        }
        int n2 = this.rowSize;
        int n3 = 0;
        while (n3 < this.rowSize) {
            bitArray.setBulk(n3 * 32, this.bits[n * n2 + n3]);
            ++n3;
        }
        return bitArray;
    }

    public int getRowSize() {
        return this.rowSize;
    }

    public int[] getTopLeftOnBit() {
        int n;
        int[] arrn;
        for (n = 0; n < (arrn = this.bits).length && arrn[n] == 0; ++n) {
        }
        arrn = this.bits;
        if (n == arrn.length) {
            return null;
        }
        int n2 = this.rowSize;
        int n3 = n / n2;
        int n4 = arrn[n];
        int n5 = 0;
        while (n4 << 31 - n5 == 0) {
            ++n5;
        }
        return new int[]{n % n2 * 32 + n5, n3};
    }

    public int getWidth() {
        return this.width;
    }

    public int hashCode() {
        int n = this.width;
        return (((n * 31 + n) * 31 + this.height) * 31 + this.rowSize) * 31 + Arrays.hashCode(this.bits);
    }

    public void rotate180() {
        int n = this.getWidth();
        int n2 = this.getHeight();
        BitArray bitArray = new BitArray(n);
        BitArray bitArray2 = new BitArray(n);
        n = 0;
        while (n < (n2 + 1) / 2) {
            bitArray = this.getRow(n, bitArray);
            int n3 = n2 - 1 - n;
            bitArray2 = this.getRow(n3, bitArray2);
            bitArray.reverse();
            bitArray2.reverse();
            this.setRow(n, bitArray2);
            this.setRow(n3, bitArray);
            ++n;
        }
    }

    public void set(int n, int n2) {
        n2 = n2 * this.rowSize + n / 32;
        int[] arrn = this.bits;
        arrn[n2] = 1 << (n & 31) | arrn[n2];
    }

    public void setRegion(int n, int n2, int n3, int n4) {
        if (n2 < 0) throw new IllegalArgumentException("Left and top must be nonnegative");
        if (n < 0) throw new IllegalArgumentException("Left and top must be nonnegative");
        if (n4 < 1) throw new IllegalArgumentException("Height and width must be at least 1");
        if (n3 < 1) throw new IllegalArgumentException("Height and width must be at least 1");
        int n5 = n3 + n;
        if ((n4 += n2) > this.height) throw new IllegalArgumentException("The region must fit inside the matrix");
        if (n5 > this.width) throw new IllegalArgumentException("The region must fit inside the matrix");
        while (n2 < n4) {
            int n6 = this.rowSize;
            for (n3 = n; n3 < n5; ++n3) {
                int[] arrn = this.bits;
                int n7 = n3 / 32 + n6 * n2;
                arrn[n7] = arrn[n7] | 1 << (n3 & 31);
            }
            ++n2;
        }
    }

    public void setRow(int n, BitArray arrn) {
        int[] arrn2 = arrn.getBitArray();
        arrn = this.bits;
        int n2 = this.rowSize;
        System.arraycopy(arrn2, 0, arrn, n * n2, n2);
    }

    public String toString() {
        return this.toString("X ", "  ");
    }

    public String toString(String string2, String string3) {
        return this.toString(string2, string3, "\n");
    }

    @Deprecated
    public String toString(String string2, String string3, String string4) {
        StringBuilder stringBuilder = new StringBuilder(this.height * (this.width + 1));
        int n = 0;
        while (n < this.height) {
            for (int i = 0; i < this.width; ++i) {
                String string5 = this.get(i, n) ? string2 : string3;
                stringBuilder.append(string5);
            }
            stringBuilder.append(string4);
            ++n;
        }
        return stringBuilder.toString();
    }

    public void unset(int n, int n2) {
        n2 = n2 * this.rowSize + n / 32;
        int[] arrn = this.bits;
        arrn[n2] = 1 << (n & 31) & arrn[n2];
    }

    public void xor(BitMatrix bitMatrix) {
        if (this.width != bitMatrix.getWidth()) throw new IllegalArgumentException("input matrix dimensions do not match");
        if (this.height != bitMatrix.getHeight()) throw new IllegalArgumentException("input matrix dimensions do not match");
        if (this.rowSize != bitMatrix.getRowSize()) throw new IllegalArgumentException("input matrix dimensions do not match");
        BitArray bitArray = new BitArray(this.width / 32 + 1);
        int n = 0;
        while (n < this.height) {
            int n2 = this.rowSize;
            int[] arrn = bitMatrix.getRow(n, bitArray).getBitArray();
            for (int i = 0; i < this.rowSize; ++i) {
                int[] arrn2 = this.bits;
                int n3 = n2 * n + i;
                arrn2[n3] = arrn2[n3] ^ arrn[i];
            }
            ++n;
        }
    }
}

