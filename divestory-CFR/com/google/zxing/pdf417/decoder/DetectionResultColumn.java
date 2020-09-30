/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.decoder.BoundingBox;
import com.google.zxing.pdf417.decoder.Codeword;
import java.util.Formatter;

class DetectionResultColumn {
    private static final int MAX_NEARBY_DISTANCE = 5;
    private final BoundingBox boundingBox;
    private final Codeword[] codewords;

    DetectionResultColumn(BoundingBox boundingBox) {
        this.boundingBox = new BoundingBox(boundingBox);
        this.codewords = new Codeword[boundingBox.getMaxY() - boundingBox.getMinY() + 1];
    }

    final BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    final Codeword getCodeword(int n) {
        return this.codewords[this.imageRowToCodewordIndex(n)];
    }

    final Codeword getCodewordNearby(int n) {
        Object object = this.getCodeword(n);
        if (object != null) {
            return object;
        }
        int n2 = 1;
        while (n2 < 5) {
            int n3 = this.imageRowToCodewordIndex(n) - n2;
            if (n3 >= 0 && (object = this.codewords[n3]) != null) {
                return object;
            }
            n3 = this.imageRowToCodewordIndex(n) + n2;
            if (n3 < ((Codeword[])(object = this.codewords)).length && (object = object[n3]) != null) {
                return object;
            }
            ++n2;
        }
        return null;
    }

    final Codeword[] getCodewords() {
        return this.codewords;
    }

    final int imageRowToCodewordIndex(int n) {
        return n - this.boundingBox.getMinY();
    }

    final void setCodeword(int n, Codeword codeword) {
        this.codewords[this.imageRowToCodewordIndex((int)n)] = codeword;
    }

    public String toString() {
        Formatter formatter = new Formatter();
        Codeword[] arrcodeword = this.codewords;
        int n = arrcodeword.length;
        int n2 = 0;
        int n3 = 0;
        do {
            Object object;
            if (n2 >= n) {
                object = formatter.toString();
                formatter.close();
                return object;
            }
            object = arrcodeword[n2];
            if (object == null) {
                formatter.format("%3d:    |   %n", n3);
                ++n3;
            } else {
                formatter.format("%3d: %3d|%3d%n", n3, ((Codeword)object).getRowNumber(), ((Codeword)object).getValue());
                ++n3;
            }
            ++n2;
        } while (true);
    }
}

