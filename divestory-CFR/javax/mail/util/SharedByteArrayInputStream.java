/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.mail.internet.SharedInputStream;

public class SharedByteArrayInputStream
extends ByteArrayInputStream
implements SharedInputStream {
    protected int start = 0;

    public SharedByteArrayInputStream(byte[] arrby) {
        super(arrby);
    }

    public SharedByteArrayInputStream(byte[] arrby, int n, int n2) {
        super(arrby, n, n2);
        this.start = n;
    }

    @Override
    public long getPosition() {
        return this.pos - this.start;
    }

    @Override
    public InputStream newStream(long l, long l2) {
        if (l < 0L) throw new IllegalArgumentException("start < 0");
        long l3 = l2;
        if (l2 != -1L) return new SharedByteArrayInputStream(this.buf, this.start + (int)l, (int)(l3 - l));
        l3 = this.count - this.start;
        return new SharedByteArrayInputStream(this.buf, this.start + (int)l, (int)(l3 - l));
    }
}

