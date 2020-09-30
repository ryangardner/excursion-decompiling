/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.Preconditions;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class ByteStreams {
    private static final int BUF_SIZE = 4096;

    private ByteStreams() {
    }

    public static long copy(InputStream inputStream2, OutputStream outputStream2) throws IOException {
        Preconditions.checkNotNull(inputStream2);
        Preconditions.checkNotNull(outputStream2);
        byte[] arrby = new byte[4096];
        long l = 0L;
        int n;
        while ((n = inputStream2.read(arrby)) != -1) {
            outputStream2.write(arrby, 0, n);
            l += (long)n;
        }
        return l;
    }

    public static InputStream limit(InputStream inputStream2, long l) {
        return new LimitedInputStream(inputStream2, l);
    }

    public static int read(InputStream inputStream2, byte[] arrby, int n, int n2) throws IOException {
        Preconditions.checkNotNull(inputStream2);
        Preconditions.checkNotNull(arrby);
        if (n2 < 0) throw new IndexOutOfBoundsException("len is negative");
        int n3 = 0;
        while (n3 < n2) {
            int n4 = inputStream2.read(arrby, n + n3, n2 - n3);
            if (n4 == -1) {
                return n3;
            }
            n3 += n4;
        }
        return n3;
    }

    private static final class LimitedInputStream
    extends FilterInputStream {
        private long left;
        private long mark = -1L;

        LimitedInputStream(InputStream inputStream2, long l) {
            super(inputStream2);
            Preconditions.checkNotNull(inputStream2);
            boolean bl = l >= 0L;
            Preconditions.checkArgument(bl, "limit must be non-negative");
            this.left = l;
        }

        @Override
        public int available() throws IOException {
            return (int)Math.min((long)this.in.available(), this.left);
        }

        @Override
        public void mark(int n) {
            synchronized (this) {
                this.in.mark(n);
                this.mark = this.left;
                return;
            }
        }

        @Override
        public int read() throws IOException {
            if (this.left == 0L) {
                return -1;
            }
            int n = this.in.read();
            if (n == -1) return n;
            --this.left;
            return n;
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            long l = this.left;
            if (l == 0L) {
                return -1;
            }
            if ((n = this.in.read(arrby, n, n2 = (int)Math.min((long)n2, l))) == -1) return n;
            this.left -= (long)n;
            return n;
        }

        @Override
        public void reset() throws IOException {
            synchronized (this) {
                if (!this.in.markSupported()) {
                    IOException iOException = new IOException("Mark not supported");
                    throw iOException;
                }
                if (this.mark != -1L) {
                    this.in.reset();
                    this.left = this.mark;
                    return;
                }
                IOException iOException = new IOException("Mark not set");
                throw iOException;
            }
        }

        @Override
        public long skip(long l) throws IOException {
            l = Math.min(l, this.left);
            l = this.in.skip(l);
            this.left -= l;
            return l;
        }
    }

}

