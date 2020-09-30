/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Inflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.BufferedSource;
import okio.InflaterSource;
import okio.RealBufferedSource;
import okio.Segment;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0002\u0010\u0003J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u000eH\u0016J\b\u0010\u0015\u001a\u00020\u000eH\u0002J\b\u0010\u0016\u001a\u00020\u000eH\u0002J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0018H\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016J \u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u0018H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2={"Lokio/GzipSource;", "Lokio/Source;", "source", "(Lokio/Source;)V", "crc", "Ljava/util/zip/CRC32;", "inflater", "Ljava/util/zip/Inflater;", "inflaterSource", "Lokio/InflaterSource;", "section", "", "Lokio/RealBufferedSource;", "checkEqual", "", "name", "", "expected", "", "actual", "close", "consumeHeader", "consumeTrailer", "read", "", "sink", "Lokio/Buffer;", "byteCount", "timeout", "Lokio/Timeout;", "updateCrc", "buffer", "offset", "okio"}, k=1, mv={1, 1, 16})
public final class GzipSource
implements Source {
    private final CRC32 crc;
    private final Inflater inflater;
    private final InflaterSource inflaterSource;
    private byte section;
    private final RealBufferedSource source;

    public GzipSource(Source source2) {
        Intrinsics.checkParameterIsNotNull(source2, "source");
        this.source = new RealBufferedSource(source2);
        this.inflater = new Inflater(true);
        this.inflaterSource = new InflaterSource(this.source, this.inflater);
        this.crc = new CRC32();
    }

    private final void checkEqual(String string2, int n, int n2) {
        if (n2 == n) {
            return;
        }
        string2 = String.format("%s: actual 0x%08x != expected 0x%08x", Arrays.copyOf(new Object[]{string2, n2, n}, 3));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.String.format(this, *args)");
        throw (Throwable)new IOException(string2);
    }

    private final void consumeHeader() throws IOException {
        long l;
        this.source.require(10L);
        byte by = this.source.bufferField.getByte(3L);
        boolean bl = true;
        boolean bl2 = (by >> 1 & 1) == 1;
        if (bl2) {
            this.updateCrc(this.source.bufferField, 0L, 10L);
        }
        this.checkEqual("ID1ID2", 8075, this.source.readShort());
        this.source.skip(8L);
        boolean bl3 = (by >> 2 & 1) == 1;
        if (bl3) {
            this.source.require(2L);
            if (bl2) {
                this.updateCrc(this.source.bufferField, 0L, 2L);
            }
            l = this.source.bufferField.readShortLe();
            this.source.require(l);
            if (bl2) {
                this.updateCrc(this.source.bufferField, 0L, l);
            }
            this.source.skip(l);
        }
        if (bl3 = (by >> 3 & 1) == 1) {
            l = this.source.indexOf((byte)0);
            if (l == -1L) throw (Throwable)new EOFException();
            if (bl2) {
                this.updateCrc(this.source.bufferField, 0L, l + 1L);
            }
            this.source.skip(l + 1L);
        }
        if (bl3 = (by >> 4 & 1) == 1 ? bl : false) {
            l = this.source.indexOf((byte)0);
            if (l == -1L) throw (Throwable)new EOFException();
            if (bl2) {
                this.updateCrc(this.source.bufferField, 0L, l + 1L);
            }
            this.source.skip(l + 1L);
        }
        if (!bl2) return;
        this.checkEqual("FHCRC", this.source.readShortLe(), (short)this.crc.getValue());
        this.crc.reset();
    }

    private final void consumeTrailer() throws IOException {
        this.checkEqual("CRC", this.source.readIntLe(), (int)this.crc.getValue());
        this.checkEqual("ISIZE", this.source.readIntLe(), (int)this.inflater.getBytesWritten());
    }

    private final void updateCrc(Buffer object, long l, long l2) {
        Segment segment = ((Buffer)object).head;
        object = segment;
        long l3 = l;
        if (segment == null) {
            Intrinsics.throwNpe();
            l3 = l;
            object = segment;
        }
        while (l3 >= (long)(((Segment)object).limit - ((Segment)object).pos)) {
            l = l3 - (long)(((Segment)object).limit - ((Segment)object).pos);
            segment = ((Segment)object).next;
            object = segment;
            l3 = l;
            if (segment != null) continue;
            Intrinsics.throwNpe();
            object = segment;
            l3 = l;
        }
        while (l2 > 0L) {
            int n = (int)((long)((Segment)object).pos + l3);
            int n2 = (int)Math.min((long)(((Segment)object).limit - n), l2);
            this.crc.update(((Segment)object).data, n, n2);
            l2 -= (long)n2;
            object = ((Segment)object).next;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            l3 = 0L;
        }
    }

    @Override
    public void close() throws IOException {
        this.inflaterSource.close();
    }

    @Override
    public long read(Buffer object, long l) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "sink");
        long l2 = l LCMP 0L;
        boolean bl = l2 >= 0;
        if (!bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("byteCount < 0: ");
            ((StringBuilder)object).append(l);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }
        if (l2 == false) {
            return 0L;
        }
        if (this.section == 0) {
            this.consumeHeader();
            this.section = (byte)(true ? 1 : 0);
        }
        if (this.section == 1) {
            long l3 = ((Buffer)object).size();
            if ((l = this.inflaterSource.read((Buffer)object, l)) != -1L) {
                this.updateCrc((Buffer)object, l3, l);
                return l;
            }
            this.section = (byte)2;
        }
        if (this.section != 2) return -1L;
        this.consumeTrailer();
        this.section = (byte)3;
        if (!this.source.exhausted()) throw (Throwable)new IOException("gzip finished without exhausting source");
        return -1L;
    }

    @Override
    public Timeout timeout() {
        return this.source.timeout();
    }
}

