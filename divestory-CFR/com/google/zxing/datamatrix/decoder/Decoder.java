/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.google.zxing.datamatrix.decoder.BitMatrixParser;
import com.google.zxing.datamatrix.decoder.DataBlock;
import com.google.zxing.datamatrix.decoder.DecodedBitStreamParser;
import com.google.zxing.datamatrix.decoder.Version;

public final class Decoder {
    private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.DATA_MATRIX_FIELD_256);

    private void correctErrors(byte[] arrby, int n) throws ChecksumException {
        int n2;
        int n3 = arrby.length;
        int[] arrn = new int[n3];
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            arrn[n2] = arrby[n2] & 255;
        }
        n2 = arrby.length;
        try {
            this.rsDecoder.decode(arrn, n2 - n);
            n2 = n4;
        }
        catch (ReedSolomonException reedSolomonException) {
            throw ChecksumException.getChecksumInstance();
        }
        while (n2 < n) {
            arrby[n2] = (byte)arrn[n2];
            ++n2;
        }
        return;
    }

    public DecoderResult decode(BitMatrix arrdataBlock) throws FormatException, ChecksumException {
        int n;
        arrdataBlock = new BitMatrixParser((BitMatrix)arrdataBlock);
        byte[] arrby = arrdataBlock.getVersion();
        arrdataBlock = DataBlock.getDataBlocks(arrdataBlock.readCodewords(), (Version)arrby);
        int n2 = arrdataBlock.length;
        int n3 = arrdataBlock.length;
        int n4 = 0;
        for (n = 0; n < n3; n4 += arrdataBlock[n].getNumDataCodewords(), ++n) {
        }
        byte[] arrby2 = new byte[n4];
        n4 = 0;
        while (n4 < n2) {
            DataBlock dataBlock = arrdataBlock[n4];
            arrby = dataBlock.getCodewords();
            n3 = dataBlock.getNumDataCodewords();
            this.correctErrors(arrby, n3);
            for (n = 0; n < n3; ++n) {
                arrby2[n * n2 + n4] = arrby[n];
            }
            ++n4;
        }
        return DecodedBitStreamParser.decode(arrby2);
    }

    public DecoderResult decode(boolean[][] arrbl) throws FormatException, ChecksumException {
        int n = arrbl.length;
        BitMatrix bitMatrix = new BitMatrix(n);
        int n2 = 0;
        while (n2 < n) {
            for (int i = 0; i < n; ++i) {
                if (!arrbl[n2][i]) continue;
                bitMatrix.set(i, n2);
            }
            ++n2;
        }
        return this.decode(bitMatrix);
    }
}

