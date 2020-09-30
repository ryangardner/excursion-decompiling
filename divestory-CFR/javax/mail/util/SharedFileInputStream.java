/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import javax.mail.internet.SharedInputStream;

public class SharedFileInputStream
extends BufferedInputStream
implements SharedInputStream {
    private static int defaultBufferSize = 2048;
    protected long bufpos;
    protected int bufsize;
    protected long datalen;
    protected RandomAccessFile in;
    private boolean master = true;
    private SharedFile sf;
    protected long start = 0L;

    public SharedFileInputStream(File file) throws IOException {
        this(file, defaultBufferSize);
    }

    public SharedFileInputStream(File file, int n) throws IOException {
        super(null);
        if (n <= 0) throw new IllegalArgumentException("Buffer size <= 0");
        this.init(new SharedFile(file), n);
    }

    public SharedFileInputStream(String string2) throws IOException {
        this(string2, defaultBufferSize);
    }

    public SharedFileInputStream(String string2, int n) throws IOException {
        super(null);
        if (n <= 0) throw new IllegalArgumentException("Buffer size <= 0");
        this.init(new SharedFile(string2), n);
    }

    private SharedFileInputStream(SharedFile sharedFile, long l, long l2, int n) {
        super(null);
        this.master = false;
        this.sf = sharedFile;
        this.in = sharedFile.open();
        this.start = l;
        this.bufpos = l;
        this.datalen = l2;
        this.bufsize = n;
        this.buf = new byte[n];
    }

    private void ensureOpen() throws IOException {
        if (this.in == null) throw new IOException("Stream closed");
    }

    private void fill() throws IOException {
        int n;
        if (this.markpos < 0) {
            this.pos = 0;
            this.bufpos += (long)this.count;
        } else if (this.pos >= this.buf.length) {
            if (this.markpos > 0) {
                n = this.pos - this.markpos;
                System.arraycopy(this.buf, this.markpos, this.buf, 0, n);
                this.pos = n;
                this.bufpos += (long)this.markpos;
                this.markpos = 0;
            } else if (this.buf.length >= this.marklimit) {
                this.markpos = -1;
                this.pos = 0;
                this.bufpos += (long)this.count;
            } else {
                int n2;
                n = n2 = this.pos * 2;
                if (n2 > this.marklimit) {
                    n = this.marklimit;
                }
                byte[] arrby = new byte[n];
                System.arraycopy(this.buf, 0, arrby, 0, this.pos);
                this.buf = arrby;
            }
        }
        this.count = this.pos;
        this.in.seek(this.bufpos + (long)this.pos);
        n = this.buf.length - this.pos;
        long l = this.bufpos;
        long l2 = this.start;
        long l3 = this.pos;
        long l4 = n;
        long l5 = this.datalen;
        if (l - l2 + l3 + l4 > l5) {
            n = (int)(l5 - (this.bufpos - this.start + (long)this.pos));
        }
        if ((n = this.in.read(this.buf, this.pos, n)) <= 0) return;
        this.count = n + this.pos;
    }

    private int in_available() throws IOException {
        return (int)(this.start + this.datalen - (this.bufpos + (long)this.count));
    }

    private void init(SharedFile object, int n) throws IOException {
        this.sf = object;
        this.in = object = ((SharedFile)object).open();
        this.start = 0L;
        this.datalen = ((RandomAccessFile)object).length();
        this.bufsize = n;
        this.buf = new byte[n];
    }

    private int read1(byte[] arrby, int n, int n2) throws IOException {
        int n3;
        int n4 = n3 = this.count - this.pos;
        if (n3 <= 0) {
            this.fill();
            n4 = n3 = this.count - this.pos;
            if (n3 <= 0) {
                return -1;
            }
        }
        n3 = n2;
        if (n4 < n2) {
            n3 = n4;
        }
        System.arraycopy(this.buf, this.pos, arrby, n, n3);
        this.pos += n3;
        return n3;
    }

    @Override
    public int available() throws IOException {
        synchronized (this) {
            this.ensureOpen();
            int n = this.count;
            int n2 = this.pos;
            int n3 = this.in_available();
            return n - n2 + n3;
        }
    }

    @Override
    public void close() throws IOException {
        if (this.in == null) {
            return;
        }
        try {
            if (this.master) {
                this.sf.forceClose();
                return;
            }
            this.sf.close();
            return;
        }
        finally {
            this.sf = null;
            this.in = null;
            this.buf = null;
        }
    }

    protected void finalize() throws Throwable {
        Object.super.finalize();
        this.close();
    }

    @Override
    public long getPosition() {
        if (this.in == null) throw new RuntimeException("Stream closed");
        return this.bufpos + (long)this.pos - this.start;
    }

    @Override
    public void mark(int n) {
        synchronized (this) {
            this.marklimit = n;
            this.markpos = this.pos;
            return;
        }
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public InputStream newStream(long l, long l2) {
        if (this.in == null) throw new RuntimeException("Stream closed");
        if (l < 0L) throw new IllegalArgumentException("start < 0");
        long l3 = l2;
        if (l2 != -1L) return new SharedFileInputStream(this.sf, this.start + (long)((int)l), (int)(l3 - l), this.bufsize);
        l3 = this.datalen;
        return new SharedFileInputStream(this.sf, this.start + (long)((int)l), (int)(l3 - l), this.bufsize);
    }

    @Override
    public int read() throws IOException {
        synchronized (this) {
            int n;
            block4 : {
                this.ensureOpen();
                if (this.pos < this.count) break block4;
                this.fill();
                n = this.pos;
                int n2 = this.count;
                if (n < n2) break block4;
                return -1;
            }
            byte[] arrby = this.buf;
            n = this.pos;
            this.pos = n + 1;
            n = arrby[n];
            return n & 255;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public int read(byte[] object, int n, int n2) throws IOException {
        synchronized (this) {
            this.ensureOpen();
            int n3 = n + n2;
            int n4 = ((byte[])object).length;
            if ((n | n2 | n3 | n4 - n3) < 0) {
                IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException();
                throw indexOutOfBoundsException;
            }
            if (n2 == 0) {
                return 0;
            }
            if (n4 <= 0) {
                return n4;
            }
            for (n3 = n4 = this.read1((byte[])object, (int)n, (int)n2); n3 < n2; n3 += n4) {
                n4 = this.read1((byte[])object, n + n3, n2 - n3);
                if (n4 <= 0) return n3;
            }
            return n3;
        }
    }

    @Override
    public void reset() throws IOException {
        synchronized (this) {
            this.ensureOpen();
            if (this.markpos >= 0) {
                this.pos = this.markpos;
                return;
            }
            IOException iOException = new IOException("Resetting to invalid mark");
            throw iOException;
        }
    }

    @Override
    public long skip(long l) throws IOException {
        synchronized (this) {
            long l2;
            long l3;
            block8 : {
                block7 : {
                    this.ensureOpen();
                    if (l > 0L) break block7;
                    return 0L;
                }
                l2 = l3 = (long)(this.count - this.pos);
                if (l3 > 0L) break block8;
                this.fill();
                int n = this.count;
                int n2 = this.pos;
                l2 = l3 = (long)(n - n2);
                if (l3 <= 0L) {
                    return 0L;
                }
            }
            l3 = l;
            if (l2 < l) {
                l3 = l2;
            }
            this.pos = (int)((long)this.pos + l3);
            return l3;
        }
    }

    static class SharedFile {
        private int cnt;
        private RandomAccessFile in;

        SharedFile(File file) throws IOException {
            this.in = new RandomAccessFile(file, "r");
        }

        SharedFile(String string2) throws IOException {
            this.in = new RandomAccessFile(string2, "r");
        }

        public void close() throws IOException {
            synchronized (this) {
                int n;
                if (this.cnt <= 0) return;
                this.cnt = n = this.cnt - 1;
                if (n > 0) return;
                this.in.close();
                return;
            }
        }

        protected void finalize() throws Throwable {
            super.finalize();
            this.in.close();
        }

        /*
         * Enabled unnecessary exception pruning
         * Converted monitor instructions to comments
         */
        public void forceClose() throws IOException {
            // MONITORENTER : this
            if (this.cnt > 0) {
                this.cnt = 0;
                this.in.close();
                return;
            }
            try {
                this.in.close();
                // MONITOREXIT : this
                return;
            }
            catch (IOException iOException) {
                return;
            }
        }

        public RandomAccessFile open() {
            ++this.cnt;
            return this.in;
        }
    }

}

