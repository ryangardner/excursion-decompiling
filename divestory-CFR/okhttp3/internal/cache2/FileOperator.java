/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.cache2;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;

@Metadata(bv={1, 0, 3}, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bJ\u001e\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lokhttp3/internal/cache2/FileOperator;", "", "fileChannel", "Ljava/nio/channels/FileChannel;", "(Ljava/nio/channels/FileChannel;)V", "read", "", "pos", "", "sink", "Lokio/Buffer;", "byteCount", "write", "source", "okhttp"}, k=1, mv={1, 1, 16})
public final class FileOperator {
    private final FileChannel fileChannel;

    public FileOperator(FileChannel fileChannel) {
        Intrinsics.checkParameterIsNotNull(fileChannel, "fileChannel");
        this.fileChannel = fileChannel;
    }

    public final void read(long l, Buffer buffer, long l2) {
        Intrinsics.checkParameterIsNotNull(buffer, "sink");
        if (l2 < 0L) throw (Throwable)new IndexOutOfBoundsException();
        while (l2 > 0L) {
            long l3 = this.fileChannel.transferTo(l, l2, buffer);
            l += l3;
            l2 -= l3;
        }
    }

    public final void write(long l, Buffer buffer, long l2) throws IOException {
        Intrinsics.checkParameterIsNotNull(buffer, "source");
        if (l2 < 0L) throw (Throwable)new IndexOutOfBoundsException();
        if (l2 > buffer.size()) throw (Throwable)new IndexOutOfBoundsException();
        long l3 = l;
        l = l2;
        while (l > 0L) {
            l2 = this.fileChannel.transferFrom(buffer, l3, l);
            l3 += l2;
            l -= l2;
        }
    }
}

