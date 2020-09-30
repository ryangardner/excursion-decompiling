/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class AppendableWriter
extends Writer {
    private boolean closed;
    private final Appendable target;

    AppendableWriter(Appendable appendable) {
        this.target = Preconditions.checkNotNull(appendable);
    }

    private void checkNotClosed() throws IOException {
        if (this.closed) throw new IOException("Cannot write to a closed writer.");
    }

    @Override
    public Writer append(char c) throws IOException {
        this.checkNotClosed();
        this.target.append(c);
        return this;
    }

    @Override
    public Writer append(@NullableDecl CharSequence charSequence) throws IOException {
        this.checkNotClosed();
        this.target.append(charSequence);
        return this;
    }

    @Override
    public Writer append(@NullableDecl CharSequence charSequence, int n, int n2) throws IOException {
        this.checkNotClosed();
        this.target.append(charSequence, n, n2);
        return this;
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
        Appendable appendable = this.target;
        if (!(appendable instanceof Closeable)) return;
        ((Closeable)((Object)appendable)).close();
    }

    @Override
    public void flush() throws IOException {
        this.checkNotClosed();
        Appendable appendable = this.target;
        if (!(appendable instanceof Flushable)) return;
        ((Flushable)((Object)appendable)).flush();
    }

    @Override
    public void write(int n) throws IOException {
        this.checkNotClosed();
        this.target.append((char)n);
    }

    @Override
    public void write(@NullableDecl String string2) throws IOException {
        this.checkNotClosed();
        this.target.append(string2);
    }

    @Override
    public void write(@NullableDecl String string2, int n, int n2) throws IOException {
        this.checkNotClosed();
        this.target.append(string2, n, n2 + n);
    }

    @Override
    public void write(char[] arrc, int n, int n2) throws IOException {
        this.checkNotClosed();
        this.target.append(new String(arrc, n, n2));
    }
}

