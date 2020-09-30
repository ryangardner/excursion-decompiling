/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;
import com.google.zxing.datamatrix.encoder.SymbolInfo;

final class EdifactEncoder
implements Encoder {
    EdifactEncoder() {
    }

    private static void encodeChar(char c, StringBuilder stringBuilder) {
        if (c >= ' ' && c <= '?') {
            stringBuilder.append(c);
            return;
        }
        if (c >= '@' && c <= '^') {
            stringBuilder.append((char)(c - 64));
            return;
        }
        HighLevelEncoder.illegalCharacter(c);
    }

    private static String encodeToCodewords(CharSequence charSequence, int n) {
        int n2 = charSequence.length() - n;
        if (n2 == 0) throw new IllegalStateException("StringBuilder must not be empty");
        char c = charSequence.charAt(n);
        char c2 = '\u0000';
        char c3 = n2 >= 2 ? charSequence.charAt(n + 1) : (char)'\u0000';
        char c4 = n2 >= 3 ? charSequence.charAt(n + 2) : (char)'\u0000';
        if (n2 >= 4) {
            c2 = charSequence.charAt(n + 3);
        }
        n = (c << 18) + (c3 << 12) + (c4 << 6) + c2;
        char c5 = (char)(n >> 16 & 255);
        char c6 = (char)(n >> 8 & 255);
        char c7 = (char)(n & 255);
        charSequence = new StringBuilder(3);
        ((StringBuilder)charSequence).append(c5);
        if (n2 >= 2) {
            ((StringBuilder)charSequence).append(c6);
        }
        if (n2 < 3) return ((StringBuilder)charSequence).toString();
        ((StringBuilder)charSequence).append(c7);
        return ((StringBuilder)charSequence).toString();
    }

    private static void handleEOD(EncoderContext encoderContext, CharSequence object) {
        int n;
        int n2;
        int n3;
        block13 : {
            n3 = object.length();
            if (n3 == 0) {
                encoderContext.signalEncoderChange(0);
                return;
            }
            n2 = 1;
            if (n3 != 1) break block13;
            encoderContext.updateSymbolInfo();
            int n4 = encoderContext.getSymbolInfo().getDataCapacity();
            int n5 = encoderContext.getCodewordCount();
            n = encoderContext.getRemainingCharacters();
            if (n == 0 && n4 - n5 <= 2) {
                encoderContext.signalEncoderChange(0);
                return;
            }
        }
        if (n3 <= 4) {
            n = n3 - 1;
            object = EdifactEncoder.encodeToCodewords((CharSequence)object, 0);
            if (!(encoderContext.hasMoreCharacters() ^ true) || n > 2) {
                n2 = 0;
            }
            n3 = n2;
            if (n <= 2) {
                encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + n);
                n3 = n2;
                if (encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount() >= 3) {
                    encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + ((String)object).length());
                    n3 = 0;
                }
            }
            if (n3 != 0) {
                encoderContext.resetSymbolInfo();
                encoderContext.pos -= n;
                return;
            }
            encoderContext.writeCodewords((String)object);
            return;
        }
        try {
            object = new IllegalStateException("Count must not exceed 4");
            throw object;
        }
        finally {
            encoderContext.signalEncoderChange(0);
        }
    }

    @Override
    public void encode(EncoderContext encoderContext) {
        StringBuilder stringBuilder = new StringBuilder();
        while (encoderContext.hasMoreCharacters()) {
            EdifactEncoder.encodeChar(encoderContext.getCurrentChar(), stringBuilder);
            ++encoderContext.pos;
            if (stringBuilder.length() < 4) continue;
            encoderContext.writeCodewords(EdifactEncoder.encodeToCodewords(stringBuilder, 0));
            stringBuilder.delete(0, 4);
            if (HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, this.getEncodingMode()) == this.getEncodingMode()) continue;
            encoderContext.signalEncoderChange(0);
            break;
        }
        stringBuilder.append('\u001f');
        EdifactEncoder.handleEOD(encoderContext, stringBuilder);
    }

    @Override
    public int getEncodingMode() {
        return 4;
    }
}

