/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.io.Closeable;
import java.io.IOException;
import kotlin.Metadata;
import okio.Buffer;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H&J\b\u0010\t\u001a\u00020\nH&\u00a8\u0006\u000b"}, d2={"Lokio/Source;", "Ljava/io/Closeable;", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "timeout", "Lokio/Timeout;", "okio"}, k=1, mv={1, 1, 16})
public interface Source
extends Closeable {
    @Override
    public void close() throws IOException;

    public long read(Buffer var1, long var2) throws IOException;

    public Timeout timeout();
}

