/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public abstract class OneDimensionalCodeWriter
implements Writer {
    protected static int appendPattern(boolean[] arrbl, int n, int[] arrn, boolean bl) {
        int n2 = arrn.length;
        int n3 = 0;
        int n4 = 0;
        int n5 = n;
        n = n3;
        while (n < n2) {
            int n6 = arrn[n];
            for (n3 = 0; n3 < n6; ++n3, ++n5) {
                arrbl[n5] = bl;
            }
            n4 += n6;
            bl ^= true;
            ++n;
        }
        return n4;
    }

    private static BitMatrix renderResult(boolean[] arrbl, int n, int n2, int n3) {
        int n4 = arrbl.length;
        int n5 = n3 + n4;
        n = Math.max(n, n5);
        n3 = Math.max(1, n2);
        n5 = n / n5;
        n2 = (n - n4 * n5) / 2;
        BitMatrix bitMatrix = new BitMatrix(n, n3);
        n = 0;
        while (n < n4) {
            if (arrbl[n]) {
                bitMatrix.setRegion(n2, 0, n5, n3);
            }
            ++n;
            n2 += n5;
        }
        return bitMatrix;
    }

    @Override
    public final BitMatrix encode(String string2, BarcodeFormat barcodeFormat, int n, int n2) throws WriterException {
        return this.encode(string2, barcodeFormat, n, n2, null);
    }

    @Override
    public BitMatrix encode(String charSequence, BarcodeFormat object, int n, int n2, Map<EncodeHintType, ?> map) throws WriterException {
        if (((String)charSequence).isEmpty()) throw new IllegalArgumentException("Found empty contents");
        if (n >= 0 && n2 >= 0) {
            int n3;
            int n4 = n3 = this.getDefaultMargin();
            if (map == null) return OneDimensionalCodeWriter.renderResult(this.encode((String)charSequence), n, n2, n4);
            object = (Integer)map.get((Object)EncodeHintType.MARGIN);
            n4 = n3;
            if (object == null) return OneDimensionalCodeWriter.renderResult(this.encode((String)charSequence), n, n2, n4);
            n4 = (Integer)object;
            return OneDimensionalCodeWriter.renderResult(this.encode((String)charSequence), n, n2, n4);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Negative size is not allowed. Input: ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append('x');
        ((StringBuilder)charSequence).append(n2);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public abstract boolean[] encode(String var1);

    public int getDefaultMargin() {
        return 10;
    }
}

