/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.datamatrix.decoder.Version;

final class DataBlock {
    private final byte[] codewords;
    private final int numDataCodewords;

    private DataBlock(int n, byte[] arrby) {
        this.numDataCodewords = n;
        this.codewords = arrby;
    }

    static DataBlock[] getDataBlocks(byte[] arrby, Version version) {
        int n;
        int n2;
        Version.ECBlocks eCBlocks = version.getECBlocks();
        Version.ECB[] arreCB = eCBlocks.getECBlocks();
        int n3 = arreCB.length;
        int n4 = 0;
        for (n2 = 0; n2 < n3; n4 += arreCB[n2].getCount(), ++n2) {
        }
        DataBlock[] arrdataBlock = new DataBlock[n4];
        int n5 = arreCB.length;
        n2 = 0;
        for (n4 = 0; n4 < n5; ++n4) {
            Version.ECB eCB = arreCB[n4];
            for (n3 = 0; n3 < eCB.getCount(); ++n3, ++n2) {
                n = eCB.getDataCodewords();
                arrdataBlock[n2] = new DataBlock(n, new byte[eCBlocks.getECCodewords() + n]);
            }
        }
        int n6 = arrdataBlock[0].codewords.length - eCBlocks.getECCodewords();
        int n7 = n6 - 1;
        n4 = 0;
        for (n3 = 0; n3 < n7; ++n3) {
            for (n5 = 0; n5 < n2; ++n5, ++n4) {
                arrdataBlock[n5].codewords[n3] = arrby[n4];
            }
        }
        n5 = version.getVersionNumber() == 24 ? 1 : 0;
        n3 = n5 != 0 ? 8 : n2;
        for (n = 0; n < n3; ++n, ++n4) {
            arrdataBlock[n].codewords[n7] = arrby[n4];
        }
        int n8 = arrdataBlock[0].codewords.length;
        n = n4;
        n4 = n6;
        do {
            if (n4 >= n8) {
                if (n != arrby.length) throw new IllegalArgumentException();
                return arrdataBlock;
            }
            for (n3 = 0; n3 < n2; ++n3, ++n) {
                n6 = n5 != 0 ? (n3 + 8) % n2 : n3;
                n7 = n5 != 0 && n6 > 7 ? n4 - 1 : n4;
                arrdataBlock[n6].codewords[n7] = arrby[n];
            }
            ++n4;
        } while (true);
    }

    byte[] getCodewords() {
        return this.codewords;
    }

    int getNumDataCodewords() {
        return this.numDataCodewords;
    }
}

