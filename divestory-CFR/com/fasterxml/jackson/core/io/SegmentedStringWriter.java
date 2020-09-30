/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.io.Writer;

public final class SegmentedStringWriter
extends Writer {
    private final TextBuffer _buffer;

    public SegmentedStringWriter(BufferRecycler bufferRecycler) {
        this._buffer = new TextBuffer(bufferRecycler);
    }

    @Override
    public Writer append(char c) {
        this.write(c);
        return this;
    }

    @Override
    public Writer append(CharSequence charSequence) {
        charSequence = charSequence.toString();
        this._buffer.append((String)charSequence, 0, ((String)charSequence).length());
        return this;
    }

    @Override
    public Writer append(CharSequence charSequence, int n, int n2) {
        charSequence = charSequence.subSequence(n, n2).toString();
        this._buffer.append((String)charSequence, 0, ((String)charSequence).length());
        return this;
    }

    @Override
    public void close() {
    }

    @Override
    public void flush() {
    }

    public String getAndClear() {
        String string2 = this._buffer.contentsAsString();
        this._buffer.releaseBuffers();
        return string2;
    }

    @Override
    public void write(int n) {
        this._buffer.append((char)n);
    }

    @Override
    public void write(String string2) {
        this._buffer.append(string2, 0, string2.length());
    }

    @Override
    public void write(String string2, int n, int n2) {
        this._buffer.append(string2, n, n2);
    }

    @Override
    public void write(char[] arrc) {
        this._buffer.append(arrc, 0, arrc.length);
    }

    @Override
    public void write(char[] arrc, int n, int n2) {
        this._buffer.append(arrc, n, n2);
    }
}

