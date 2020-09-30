/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

final class CharSequenceReader
extends Reader {
    private int mark;
    private int pos;
    private CharSequence seq;

    public CharSequenceReader(CharSequence charSequence) {
        this.seq = Preconditions.checkNotNull(charSequence);
    }

    private void checkOpen() throws IOException {
        if (this.seq == null) throw new IOException("reader closed");
    }

    private boolean hasRemaining() {
        if (this.remaining() <= 0) return false;
        return true;
    }

    private int remaining() {
        return this.seq.length() - this.pos;
    }

    @Override
    public void close() throws IOException {
        synchronized (this) {
            this.seq = null;
            return;
        }
    }

    @Override
    public void mark(int n) throws IOException {
        synchronized (this) {
            boolean bl = n >= 0;
            Preconditions.checkArgument(bl, "readAheadLimit (%s) may not be negative", n);
            this.checkOpen();
            this.mark = this.pos;
            return;
        }
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public int read() throws IOException {
        synchronized (this) {
            int n;
            this.checkOpen();
            if (this.hasRemaining()) {
                CharSequence charSequence = this.seq;
                n = this.pos;
                this.pos = n + 1;
                n = charSequence.charAt(n);
                return n;
            } else {
                n = -1;
            }
            return n;
        }
    }

    @Override
    public int read(CharBuffer charBuffer) throws IOException {
        synchronized (this) {
            Preconditions.checkNotNull(charBuffer);
            this.checkOpen();
            boolean bl = this.hasRemaining();
            if (!bl) {
                return -1;
            }
            int n = Math.min(charBuffer.remaining(), this.remaining());
            int n2 = 0;
            while (n2 < n) {
                CharSequence charSequence = this.seq;
                int n3 = this.pos;
                this.pos = n3 + 1;
                charBuffer.put(charSequence.charAt(n3));
                ++n2;
            }
            return n;
        }
    }

    @Override
    public int read(char[] arrc, int n, int n2) throws IOException {
        synchronized (this) {
            Preconditions.checkPositionIndexes(n, n + n2, arrc.length);
            this.checkOpen();
            boolean bl = this.hasRemaining();
            if (!bl) {
                return -1;
            }
            int n3 = Math.min(n2, this.remaining());
            n2 = 0;
            while (n2 < n3) {
                CharSequence charSequence = this.seq;
                int n4 = this.pos;
                this.pos = n4 + 1;
                arrc[n + n2] = charSequence.charAt(n4);
                ++n2;
            }
            return n3;
        }
    }

    @Override
    public boolean ready() throws IOException {
        synchronized (this) {
            this.checkOpen();
            return true;
        }
    }

    @Override
    public void reset() throws IOException {
        synchronized (this) {
            this.checkOpen();
            this.pos = this.mark;
            return;
        }
    }

    @Override
    public long skip(long l) throws IOException {
        synchronized (this) {
            boolean bl = l >= 0L;
            Preconditions.checkArgument(bl, "n (%s) may not be negative", l);
            this.checkOpen();
            int n = (int)Math.min((long)this.remaining(), l);
            this.pos += n;
            return n;
        }
    }
}

