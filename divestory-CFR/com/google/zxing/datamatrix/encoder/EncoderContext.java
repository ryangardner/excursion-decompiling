/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import com.google.zxing.datamatrix.encoder.SymbolInfo;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import java.nio.charset.Charset;

final class EncoderContext {
    private final StringBuilder codewords;
    private Dimension maxSize;
    private Dimension minSize;
    private final String msg;
    private int newEncoding;
    int pos;
    private SymbolShapeHint shape;
    private int skipAtEnd;
    private SymbolInfo symbolInfo;

    EncoderContext(String string2) {
        byte[] arrby = string2.getBytes(Charset.forName("ISO-8859-1"));
        StringBuilder stringBuilder = new StringBuilder(arrby.length);
        int n = arrby.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.msg = stringBuilder.toString();
                this.shape = SymbolShapeHint.FORCE_NONE;
                this.codewords = new StringBuilder(string2.length());
                this.newEncoding = -1;
                return;
            }
            char c = (char)(arrby[n2] & 255);
            if (c == '?') {
                if (string2.charAt(n2) != '?') throw new IllegalArgumentException("Message contains characters outside ISO-8859-1 encoding.");
            }
            stringBuilder.append(c);
            ++n2;
        } while (true);
    }

    private int getTotalMessageCharCount() {
        return this.msg.length() - this.skipAtEnd;
    }

    public int getCodewordCount() {
        return this.codewords.length();
    }

    public StringBuilder getCodewords() {
        return this.codewords;
    }

    public char getCurrent() {
        return this.msg.charAt(this.pos);
    }

    public char getCurrentChar() {
        return this.msg.charAt(this.pos);
    }

    public String getMessage() {
        return this.msg;
    }

    public int getNewEncoding() {
        return this.newEncoding;
    }

    public int getRemainingCharacters() {
        return this.getTotalMessageCharCount() - this.pos;
    }

    public SymbolInfo getSymbolInfo() {
        return this.symbolInfo;
    }

    public boolean hasMoreCharacters() {
        if (this.pos >= this.getTotalMessageCharCount()) return false;
        return true;
    }

    public void resetEncoderSignal() {
        this.newEncoding = -1;
    }

    public void resetSymbolInfo() {
        this.symbolInfo = null;
    }

    public void setSizeConstraints(Dimension dimension, Dimension dimension2) {
        this.minSize = dimension;
        this.maxSize = dimension2;
    }

    public void setSkipAtEnd(int n) {
        this.skipAtEnd = n;
    }

    public void setSymbolShape(SymbolShapeHint symbolShapeHint) {
        this.shape = symbolShapeHint;
    }

    public void signalEncoderChange(int n) {
        this.newEncoding = n;
    }

    public void updateSymbolInfo() {
        this.updateSymbolInfo(this.getCodewordCount());
    }

    public void updateSymbolInfo(int n) {
        SymbolInfo symbolInfo = this.symbolInfo;
        if (symbolInfo != null) {
            if (n <= symbolInfo.getDataCapacity()) return;
        }
        this.symbolInfo = SymbolInfo.lookup(n, this.shape, this.minSize, this.maxSize, true);
    }

    public void writeCodeword(char c) {
        this.codewords.append(c);
    }

    public void writeCodewords(String string2) {
        this.codewords.append(string2);
    }
}

