/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.ws;

import java.io.Closeable;
import java.io.IOException;
import java.util.zip.Deflater;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.ws.MessageDeflaterKt;
import okio.Buffer;
import okio.ByteString;
import okio.DeflaterSink;
import okio.Sink;

@Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016J\u000e\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u0006J\u0014\u0010\u000f\u001a\u00020\u0003*\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lokhttp3/internal/ws/MessageDeflater;", "Ljava/io/Closeable;", "noContextTakeover", "", "(Z)V", "deflatedBytes", "Lokio/Buffer;", "deflater", "Ljava/util/zip/Deflater;", "deflaterSink", "Lokio/DeflaterSink;", "close", "", "deflate", "buffer", "endsWith", "suffix", "Lokio/ByteString;", "okhttp"}, k=1, mv={1, 1, 16})
public final class MessageDeflater
implements Closeable {
    private final Buffer deflatedBytes;
    private final Deflater deflater;
    private final DeflaterSink deflaterSink;
    private final boolean noContextTakeover;

    public MessageDeflater(boolean bl) {
        this.noContextTakeover = bl;
        this.deflatedBytes = new Buffer();
        this.deflater = new Deflater(-1, true);
        this.deflaterSink = new DeflaterSink((Sink)this.deflatedBytes, this.deflater);
    }

    private final boolean endsWith(Buffer buffer, ByteString byteString) {
        return buffer.rangeEquals(buffer.size() - (long)byteString.size(), byteString);
    }

    @Override
    public void close() throws IOException {
        this.deflaterSink.close();
    }

    public final void deflate(Buffer buffer) throws IOException {
        Closeable closeable;
        block6 : {
            block5 : {
                Intrinsics.checkParameterIsNotNull(buffer, "buffer");
                boolean bl = this.deflatedBytes.size() == 0L;
                if (!bl) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
                if (this.noContextTakeover) {
                    this.deflater.reset();
                }
                this.deflaterSink.write(buffer, buffer.size());
                this.deflaterSink.flush();
                if (!this.endsWith(this.deflatedBytes, MessageDeflaterKt.access$getEMPTY_DEFLATE_BLOCK$p())) break block5;
                long l = this.deflatedBytes.size();
                long l2 = 4;
                closeable = Buffer.readAndWriteUnsafe$default(this.deflatedBytes, null, 1, null);
                Throwable throwable = null;
                try {
                    ((Buffer.UnsafeCursor)closeable).resizeBuffer(l - l2);
                }
                catch (Throwable throwable2) {
                    try {
                        throw throwable2;
                    }
                    catch (Throwable throwable3) {
                        CloseableKt.closeFinally(closeable, throwable2);
                        throw throwable3;
                    }
                }
                CloseableKt.closeFinally(closeable, throwable);
                break block6;
            }
            this.deflatedBytes.writeByte(0);
        }
        closeable = this.deflatedBytes;
        buffer.write((Buffer)closeable, closeable.size());
    }
}

