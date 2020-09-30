/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.common.BitMatrix;

abstract class DataMask {
    private static final DataMask[] DATA_MASKS = new DataMask[]{new DataMask000(), new DataMask001(), new DataMask010(), new DataMask011(), new DataMask100(), new DataMask101(), new DataMask110(), new DataMask111()};

    private DataMask() {
    }

    static DataMask forReference(int n) {
        if (n < 0) throw new IllegalArgumentException();
        if (n > 7) throw new IllegalArgumentException();
        return DATA_MASKS[n];
    }

    abstract boolean isMasked(int var1, int var2);

    final void unmaskBitMatrix(BitMatrix bitMatrix, int n) {
        int n2 = 0;
        while (n2 < n) {
            for (int i = 0; i < n; ++i) {
                if (!this.isMasked(n2, i)) continue;
                bitMatrix.flip(i, n2);
            }
            ++n2;
        }
    }

    private static final class DataMask000
    extends DataMask {
        private DataMask000() {
        }

        @Override
        boolean isMasked(int n, int n2) {
            boolean bl = true;
            if ((n + n2 & 1) != 0) return false;
            return bl;
        }
    }

    private static final class DataMask001
    extends DataMask {
        private DataMask001() {
        }

        @Override
        boolean isMasked(int n, int n2) {
            boolean bl = true;
            if ((n & 1) != 0) return false;
            return bl;
        }
    }

    private static final class DataMask010
    extends DataMask {
        private DataMask010() {
        }

        @Override
        boolean isMasked(int n, int n2) {
            if (n2 % 3 != 0) return false;
            return true;
        }
    }

    private static final class DataMask011
    extends DataMask {
        private DataMask011() {
        }

        @Override
        boolean isMasked(int n, int n2) {
            if ((n + n2) % 3 != 0) return false;
            return true;
        }
    }

    private static final class DataMask100
    extends DataMask {
        private DataMask100() {
        }

        @Override
        boolean isMasked(int n, int n2) {
            boolean bl = true;
            if (((n /= 2) + (n2 /= 3) & 1) != 0) return false;
            return bl;
        }
    }

    private static final class DataMask101
    extends DataMask {
        private DataMask101() {
        }

        @Override
        boolean isMasked(int n, int n2) {
            if (((n *= n2) & 1) + n % 3 != 0) return false;
            return true;
        }
    }

    private static final class DataMask110
    extends DataMask {
        private DataMask110() {
        }

        @Override
        boolean isMasked(int n, int n2) {
            boolean bl = true;
            if ((((n *= n2) & 1) + n % 3 & 1) != 0) return false;
            return bl;
        }
    }

    private static final class DataMask111
    extends DataMask {
        private DataMask111() {
        }

        @Override
        boolean isMasked(int n, int n2) {
            boolean bl = true;
            if (((n + n2 & 1) + n * n2 % 3 & 1) != 0) return false;
            return bl;
        }
    }

}

