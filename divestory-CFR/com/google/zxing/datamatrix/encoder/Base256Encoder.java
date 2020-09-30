/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;
import com.google.zxing.datamatrix.encoder.SymbolInfo;

final class Base256Encoder
implements Encoder {
    Base256Encoder() {
    }

    private static char randomize255State(char c, int n) {
        if ((c = (char)(c + (n * 149 % 255 + 1))) > '\u00ff') return (char)(c - 256);
        return c;
    }

    @Override
    public void encode(EncoderContext object) {
        int n;
        block6 : {
            int n2;
            StringBuilder stringBuilder;
            int n3;
            block4 : {
                block5 : {
                    stringBuilder = new StringBuilder();
                    n3 = 0;
                    stringBuilder.append('\u0000');
                    while (((EncoderContext)object).hasMoreCharacters()) {
                        stringBuilder.append(((EncoderContext)object).getCurrentChar());
                        ++((EncoderContext)object).pos;
                        n2 = HighLevelEncoder.lookAheadTest(((EncoderContext)object).getMessage(), ((EncoderContext)object).pos, this.getEncodingMode());
                        if (n2 == this.getEncodingMode()) continue;
                        ((EncoderContext)object).signalEncoderChange(n2);
                        break;
                    }
                    n = stringBuilder.length() - 1;
                    n2 = ((EncoderContext)object).getCodewordCount() + n + 1;
                    ((EncoderContext)object).updateSymbolInfo(n2);
                    n2 = ((EncoderContext)object).getSymbolInfo().getDataCapacity() - n2 > 0 ? 1 : 0;
                    if (!((EncoderContext)object).hasMoreCharacters() && n2 == 0) break block4;
                    if (n > 249) break block5;
                    stringBuilder.setCharAt(0, (char)n);
                    break block4;
                }
                if (n <= 249 || n > 1555) break block6;
                stringBuilder.setCharAt(0, (char)(n / 250 + 249));
                stringBuilder.insert(1, (char)(n % 250));
            }
            n = stringBuilder.length();
            n2 = n3;
            while (n2 < n) {
                ((EncoderContext)object).writeCodeword(Base256Encoder.randomize255State(stringBuilder.charAt(n2), ((EncoderContext)object).getCodewordCount() + 1));
                ++n2;
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Message length not in valid ranges: ");
        ((StringBuilder)object).append(n);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    @Override
    public int getEncodingMode() {
        return 5;
    }
}

