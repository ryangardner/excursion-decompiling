/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.datamatrix.encoder.C40Encoder;
import com.google.zxing.datamatrix.encoder.EncoderContext;
import com.google.zxing.datamatrix.encoder.HighLevelEncoder;
import com.google.zxing.datamatrix.encoder.SymbolInfo;

final class X12Encoder
extends C40Encoder {
    X12Encoder() {
    }

    @Override
    public void encode(EncoderContext encoderContext) {
        StringBuilder stringBuilder = new StringBuilder();
        while (encoderContext.hasMoreCharacters()) {
            char c = encoderContext.getCurrentChar();
            ++encoderContext.pos;
            this.encodeChar(c, stringBuilder);
            if (stringBuilder.length() % 3 != 0) continue;
            X12Encoder.writeNextTriplet(encoderContext, stringBuilder);
            int n = HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, this.getEncodingMode());
            if (n == this.getEncodingMode()) continue;
            encoderContext.signalEncoderChange(n);
            break;
        }
        this.handleEOD(encoderContext, stringBuilder);
    }

    @Override
    int encodeChar(char c, StringBuilder stringBuilder) {
        if (c == '\r') {
            stringBuilder.append('\u0000');
            return 1;
        }
        if (c == '*') {
            stringBuilder.append('\u0001');
            return 1;
        }
        if (c == '>') {
            stringBuilder.append('\u0002');
            return 1;
        }
        if (c == ' ') {
            stringBuilder.append('\u0003');
            return 1;
        }
        if (c >= '0' && c <= '9') {
            stringBuilder.append((char)(c - 48 + 4));
            return 1;
        }
        if (c >= 'A' && c <= 'Z') {
            stringBuilder.append((char)(c - 65 + 14));
            return 1;
        }
        HighLevelEncoder.illegalCharacter(c);
        return 1;
    }

    @Override
    public int getEncodingMode() {
        return 3;
    }

    @Override
    void handleEOD(EncoderContext encoderContext, StringBuilder stringBuilder) {
        encoderContext.updateSymbolInfo();
        int n = encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount();
        int n2 = stringBuilder.length();
        encoderContext.pos -= n2;
        if (encoderContext.getRemainingCharacters() > 1 || n > 1 || encoderContext.getRemainingCharacters() != n) {
            encoderContext.writeCodeword('\u00fe');
        }
        if (encoderContext.getNewEncoding() >= 0) return;
        encoderContext.signalEncoderChange(0);
    }
}

