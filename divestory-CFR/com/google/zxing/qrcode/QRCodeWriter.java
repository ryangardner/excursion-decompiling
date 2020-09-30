/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import java.util.Map;

public final class QRCodeWriter
implements Writer {
    private static final int QUIET_ZONE_SIZE = 4;

    private static BitMatrix renderResult(QRCode object, int n, int n2, int n3) {
        ByteMatrix byteMatrix = ((QRCode)object).getMatrix();
        if (byteMatrix == null) throw new IllegalStateException();
        int n4 = byteMatrix.getWidth();
        int n5 = byteMatrix.getHeight();
        int n6 = n4 + (n3 *= 2);
        int n7 = n3 + n5;
        n = Math.max(n, n6);
        n3 = Math.max(n2, n7);
        int n8 = Math.min(n / n6, n3 / n7);
        n7 = (n - n4 * n8) / 2;
        n2 = (n3 - n5 * n8) / 2;
        object = new BitMatrix(n, n3);
        n = 0;
        while (n < n5) {
            n3 = n7;
            for (n6 = 0; n6 < n4; ++n6, n3 += n8) {
                if (byteMatrix.get(n6, n) != 1) continue;
                ((BitMatrix)object).setRegion(n3, n2, n8, n8);
            }
            ++n;
            n2 += n8;
        }
        return object;
    }

    @Override
    public BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n, int n2) throws WriterException {
        return this.encode(string2, barcodeFormat, n, n2, null);
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat enum_, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (((String)charSequence).isEmpty()) throw new IllegalArgumentException("Found empty contents");
        if (enum_ != BarcodeFormat.QR_CODE) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Can only encode QR_CODE, but got ");
            ((StringBuilder)charSequence).append(enum_);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        if (n >= 0 && n2 >= 0) {
            int n3;
            enum_ = ErrorCorrectionLevel.L;
            int n4 = n3 = 4;
            Enum enum_2 = enum_;
            if (map == null) return QRCodeWriter.renderResult(Encoder.encode((String)charSequence, (ErrorCorrectionLevel)enum_2, map), n, n2, n4);
            enum_2 = (ErrorCorrectionLevel)((Object)map.get((Object)EncodeHintType.ERROR_CORRECTION));
            if (enum_2 != null) {
                enum_ = enum_2;
            }
            Integer n5 = (Integer)map.get((Object)EncodeHintType.MARGIN);
            n4 = n3;
            enum_2 = enum_;
            if (n5 == null) return QRCodeWriter.renderResult(Encoder.encode((String)charSequence, (ErrorCorrectionLevel)enum_2, map), n, n2, n4);
            n4 = n5;
            enum_2 = enum_;
            return QRCodeWriter.renderResult(Encoder.encode((String)charSequence, (ErrorCorrectionLevel)enum_2, map), n, n2, n4);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Requested dimensions are too small: ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append('x');
        ((StringBuilder)charSequence).append(n2);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }
}

