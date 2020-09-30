/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;

final class DataBlock {
    private final byte[] codewords;
    private final int numDataCodewords;

    private DataBlock(int n, byte[] arrby) {
        this.numDataCodewords = n;
        this.codewords = arrby;
    }

    static DataBlock[] getDataBlocks(byte[] arrby, Version arrdataBlock, ErrorCorrectionLevel object) {
        int n;
        int n2;
        int n3;
        if (arrby.length != arrdataBlock.getTotalCodewords()) throw new IllegalArgumentException();
        Version.ECBlocks eCBlocks = arrdataBlock.getECBlocksForLevel((ErrorCorrectionLevel)((Object)object));
        Version.ECB[] arreCB = eCBlocks.getECBlocks();
        int n4 = arreCB.length;
        int n5 = 0;
        for (n3 = 0; n3 < n4; n5 += arreCB[n3].getCount(), ++n3) {
        }
        arrdataBlock = new DataBlock[n5];
        int n6 = arreCB.length;
        n4 = 0;
        for (n3 = 0; n3 < n6; ++n3) {
            object = arreCB[n3];
            for (n2 = 0; n2 < ((Version.ECB)object).getCount(); ++n2, ++n4) {
                n = ((Version.ECB)object).getDataCodewords();
                arrdataBlock[n4] = new DataBlock(n, new byte[eCBlocks.getECCodewordsPerBlock() + n]);
            }
        }
        n3 = arrdataBlock[0].codewords.length;
        --n5;
        while (n5 >= 0 && arrdataBlock[n5].codewords.length != n3) {
            --n5;
        }
        n = n5 + 1;
        n6 = n3 - eCBlocks.getECCodewordsPerBlock();
        n5 = 0;
        for (n3 = 0; n3 < n6; ++n3) {
            for (n2 = 0; n2 < n4; ++n2, ++n5) {
                arrdataBlock[n2].codewords[n3] = arrby[n5];
            }
        }
        n3 = n5;
        for (n2 = n; n2 < n4; ++n2, ++n3) {
            arrdataBlock[n2].codewords[n6] = arrby[n3];
        }
        int n7 = arrdataBlock[0].codewords.length;
        n5 = n6;
        while (n5 < n7) {
            for (n2 = 0; n2 < n4; ++n2, ++n3) {
                n6 = n2 < n ? n5 : n5 + 1;
                arrdataBlock[n2].codewords[n6] = arrby[n3];
            }
            ++n5;
        }
        return arrdataBlock;
    }

    byte[] getCodewords() {
        return this.codewords;
    }

    int getNumDataCodewords() {
        return this.numDataCodewords;
    }
}

