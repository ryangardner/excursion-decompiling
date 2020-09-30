/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.google.zxing.qrcode.decoder.BitMatrixParser;
import com.google.zxing.qrcode.decoder.DataBlock;
import com.google.zxing.qrcode.decoder.DecodedBitStreamParser;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.FormatInformation;
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
import com.google.zxing.qrcode.decoder.Version;
import java.util.Map;

public final class Decoder {
    private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

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

    private DecoderResult decode(BitMatrixParser arrdataBlock, Map<DecodeHintType, ?> map) throws FormatException, ChecksumException {
        int n;
        Version version = arrdataBlock.readVersion();
        ErrorCorrectionLevel errorCorrectionLevel = arrdataBlock.readFormatInformation().getErrorCorrectionLevel();
        arrdataBlock = DataBlock.getDataBlocks(arrdataBlock.readCodewords(), version, errorCorrectionLevel);
        int n2 = arrdataBlock.length;
        int n3 = 0;
        for (n = 0; n < n2; n3 += arrdataBlock[n].getNumDataCodewords(), ++n) {
        }
        byte[] arrby = new byte[n3];
        int n4 = arrdataBlock.length;
        n3 = 0;
        n = 0;
        while (n3 < n4) {
            DataBlock dataBlock = arrdataBlock[n3];
            byte[] arrby2 = dataBlock.getCodewords();
            int n5 = dataBlock.getNumDataCodewords();
            this.correctErrors(arrby2, n5);
            for (n2 = 0; n2 < n5; ++n2, ++n) {
                arrby[n] = arrby2[n2];
            }
            ++n3;
        }
        return DecodedBitStreamParser.decode(arrby, version, errorCorrectionLevel, map);
    }

    public DecoderResult decode(BitMatrix bitMatrix) throws ChecksumException, FormatException {
        return this.decode(bitMatrix, null);
    }

    /*
     * WARNING - void declaration
     */
    public DecoderResult decode(BitMatrix object, Map<DecodeHintType, ?> object2) throws FormatException, ChecksumException {
        Object object3 = new BitMatrixParser((BitMatrix)object);
        Object var4_8 = null;
        try {
            return this.decode((BitMatrixParser)object3, (Map<DecodeHintType, ?>)object2);
        }
        catch (ChecksumException checksumException) {
        }
        catch (FormatException formatException) {
            object = null;
        }
        try {
            ((BitMatrixParser)object3).remask();
            ((BitMatrixParser)object3).setMirror(true);
            ((BitMatrixParser)object3).readVersion();
            ((BitMatrixParser)object3).readFormatInformation();
            ((BitMatrixParser)object3).mirror();
            object3 = this.decode((BitMatrixParser)object3, (Map<DecodeHintType, ?>)object2);
            object2 = new QRCodeDecoderMetaData(true);
            ((DecoderResult)object3).setOther(object2);
            return object3;
        }
        catch (ChecksumException checksumException) {
        }
        catch (FormatException formatException) {
            // empty catch block
        }
        if (var4_8 != null) throw var4_8;
        if (object == null) void var2_6;
        throw var2_6;
        throw object;
    }

    public DecoderResult decode(boolean[][] arrbl) throws ChecksumException, FormatException {
        return this.decode(arrbl, null);
    }

    public DecoderResult decode(boolean[][] arrbl, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
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
        return this.decode(bitMatrix, map);
    }
}

