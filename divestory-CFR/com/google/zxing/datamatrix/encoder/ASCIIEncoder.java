/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;

final class ASCIIEncoder
implements Encoder {
    ASCIIEncoder() {
    }

    private static char encodeASCIIDigits(char c, char c2) {
        if (HighLevelEncoder.isDigit(c) && HighLevelEncoder.isDigit(c2)) {
            return (char)((c - 48) * 10 + (c2 - 48) + 130);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("not digits: ");
        stringBuilder.append(c);
        stringBuilder.append(c2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void encode(EncoderContext object) {
        if (HighLevelEncoder.determineConsecutiveDigitCount(((EncoderContext)object).getMessage(), ((EncoderContext)object).pos) >= 2) {
            ((EncoderContext)object).writeCodeword(ASCIIEncoder.encodeASCIIDigits(((EncoderContext)object).getMessage().charAt(((EncoderContext)object).pos), ((EncoderContext)object).getMessage().charAt(((EncoderContext)object).pos + 1)));
            ((EncoderContext)object).pos += 2;
            return;
        }
        char c = ((EncoderContext)object).getCurrentChar();
        int n = HighLevelEncoder.lookAheadTest(((EncoderContext)object).getMessage(), ((EncoderContext)object).pos, this.getEncodingMode());
        if (n != this.getEncodingMode()) {
            if (n == 1) {
                ((EncoderContext)object).writeCodeword('\u00e6');
                ((EncoderContext)object).signalEncoderChange(1);
                return;
            }
            if (n == 2) {
                ((EncoderContext)object).writeCodeword('\u00ef');
                ((EncoderContext)object).signalEncoderChange(2);
                return;
            }
            if (n == 3) {
                ((EncoderContext)object).writeCodeword('\u00ee');
                ((EncoderContext)object).signalEncoderChange(3);
                return;
            }
            if (n == 4) {
                ((EncoderContext)object).writeCodeword('\u00f0');
                ((EncoderContext)object).signalEncoderChange(4);
                return;
            }
            if (n == 5) {
                ((EncoderContext)object).writeCodeword('\u00e7');
                ((EncoderContext)object).signalEncoderChange(5);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Illegal mode: ");
            ((StringBuilder)object).append(n);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        if (HighLevelEncoder.isExtendedASCII(c)) {
            ((EncoderContext)object).writeCodeword('\u00eb');
            ((EncoderContext)object).writeCodeword((char)(c - 128 + 1));
            ++((EncoderContext)object).pos;
            return;
        }
        ((EncoderContext)object).writeCodeword((char)(c + '\u0001'));
        ++((EncoderContext)object).pos;
    }

    @Override
    public int getEncodingMode() {
        return 0;
    }
}

