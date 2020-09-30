/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.DataMask;
import com.google.zxing.qrcode.decoder.FormatInformation;
import com.google.zxing.qrcode.decoder.Version;

final class BitMatrixParser {
    private final BitMatrix bitMatrix;
    private boolean mirror;
    private FormatInformation parsedFormatInfo;
    private Version parsedVersion;

    BitMatrixParser(BitMatrix bitMatrix) throws FormatException {
        int n = bitMatrix.getHeight();
        if (n < 21) throw FormatException.getFormatInstance();
        if ((n & 3) != 1) throw FormatException.getFormatInstance();
        this.bitMatrix = bitMatrix;
    }

    private int copyBit(int n, int n2, int n3) {
        boolean bl = this.mirror ? this.bitMatrix.get(n2, n) : this.bitMatrix.get(n, n2);
        if (!bl) return n3 << 1;
        return n3 << 1 | 1;
    }

    void mirror() {
        int n = 0;
        while (n < this.bitMatrix.getWidth()) {
            int n2;
            for (int i = n2 = n + 1; i < this.bitMatrix.getHeight(); ++i) {
                if (this.bitMatrix.get(n, i) == this.bitMatrix.get(i, n)) continue;
                this.bitMatrix.flip(i, n);
                this.bitMatrix.flip(n, i);
            }
            n = n2;
        }
    }

    byte[] readCodewords() throws FormatException {
        byte[] arrby = this.readFormatInformation();
        Version version = this.readVersion();
        arrby = DataMask.forReference(arrby.getDataMask());
        int n = this.bitMatrix.getHeight();
        arrby.unmaskBitMatrix(this.bitMatrix, n);
        BitMatrix bitMatrix = version.buildFunctionPattern();
        arrby = new byte[version.getTotalCodewords()];
        int n2 = n - 1;
        boolean bl = true;
        int n3 = n2;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        do {
            if (n3 <= 0) {
                if (n4 != version.getTotalCodewords()) throw FormatException.getFormatInstance();
                return arrby;
            }
            int n7 = n3;
            if (n3 == 6) {
                n7 = n3 - 1;
            }
            for (n3 = 0; n3 < n; ++n3) {
                int n8 = bl ? n2 - n3 : n3;
                int n9 = n6;
                int n10 = n5;
                for (int i = 0; i < 2; ++i) {
                    int n11 = n7 - i;
                    int n12 = n4;
                    n6 = n10++;
                    n5 = n9;
                    if (!bitMatrix.get(n11, n8)) {
                        n9 = n5 = n9 << 1;
                        if (this.bitMatrix.get(n11, n8)) {
                            n9 = n5 | 1;
                        }
                        n12 = n4;
                        n6 = n10;
                        n5 = n9;
                        if (n10 == 8) {
                            arrby[n4] = (byte)n9;
                            n12 = n4 + 1;
                            n6 = 0;
                            n5 = 0;
                        }
                    }
                    n4 = n12;
                    n10 = n6;
                    n9 = n5;
                }
                n5 = n10;
                n6 = n9;
            }
            bl ^= true;
            n3 = n7 - 2;
        } while (true);
    }

    FormatInformation readFormatInformation() throws FormatException {
        int n;
        int n2;
        FormatInformation formatInformation = this.parsedFormatInfo;
        if (formatInformation != null) {
            return formatInformation;
        }
        int n3 = 0;
        int n4 = 0;
        for (n2 = 0; n2 < 6; ++n2) {
            n4 = this.copyBit(n2, 8, n4);
        }
        n2 = this.copyBit(8, 7, this.copyBit(8, 8, this.copyBit(7, 8, n4)));
        for (n4 = 5; n4 >= 0; --n4) {
            n2 = this.copyBit(8, n4, n2);
        }
        int n5 = this.bitMatrix.getHeight();
        n4 = n3;
        for (n = n5 - 1; n >= n5 - 7; --n) {
            n4 = this.copyBit(8, n, n4);
        }
        n = n5 - 8;
        do {
            if (n >= n5) {
                this.parsedFormatInfo = formatInformation = FormatInformation.decodeFormatInformation(n2, n4);
                if (formatInformation == null) throw FormatException.getFormatInstance();
                return formatInformation;
            }
            n4 = this.copyBit(n, 8, n4);
            ++n;
        } while (true);
    }

    Version readVersion() throws FormatException {
        int n;
        Version version = this.parsedVersion;
        if (version != null) {
            return version;
        }
        int n2 = this.bitMatrix.getHeight();
        int n3 = (n2 - 17) / 4;
        if (n3 <= 6) {
            return Version.getVersionForNumber(n3);
        }
        int n4 = n2 - 11;
        int n5 = 5;
        int n6 = 0;
        int n7 = 0;
        for (n3 = 5; n3 >= 0; --n3) {
            for (n = n2 - 9; n >= n4; --n) {
                n7 = this.copyBit(n, n3, n7);
            }
        }
        version = Version.decodeVersionInformation(n7);
        n3 = n5;
        n7 = n6;
        if (version != null) {
            n3 = n5;
            n7 = n6;
            if (version.getDimensionForVersion() == n2) {
                this.parsedVersion = version;
                return version;
            }
        }
        do {
            if (n3 < 0) {
                version = Version.decodeVersionInformation(n7);
                if (version == null) throw FormatException.getFormatInstance();
                if (version.getDimensionForVersion() != n2) throw FormatException.getFormatInstance();
                this.parsedVersion = version;
                return version;
            }
            for (n = n2 - 9; n >= n4; --n) {
                n7 = this.copyBit(n3, n, n7);
            }
            --n3;
        } while (true);
    }

    void remask() {
        Object object = this.parsedFormatInfo;
        if (object == null) {
            return;
        }
        object = DataMask.forReference(((FormatInformation)object).getDataMask());
        int n = this.bitMatrix.getHeight();
        ((DataMask)object).unmaskBitMatrix(this.bitMatrix, n);
    }

    void setMirror(boolean bl) {
        this.parsedVersion = null;
        this.parsedFormatInfo = null;
        this.mirror = bl;
    }
}

