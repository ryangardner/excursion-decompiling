/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.util.zip.Deflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.DeflaterSink;
import okio.Sink;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0086\b\u00a8\u0006\u0005"}, d2={"deflate", "Lokio/DeflaterSink;", "Lokio/Sink;", "deflater", "Ljava/util/zip/Deflater;", "okio"}, k=2, mv={1, 1, 16})
public final class _DeflaterSinkExtensions {
    public static final DeflaterSink deflate(Sink sink2, Deflater deflater) {
        Intrinsics.checkParameterIsNotNull(sink2, "$this$deflate");
        Intrinsics.checkParameterIsNotNull(deflater, "deflater");
        return new DeflaterSink(sink2, deflater);
    }

    public static /* synthetic */ DeflaterSink deflate$default(Sink sink2, Deflater deflater, int n, Object object) {
        if ((n & 1) != 0) {
            deflater = new Deflater();
        }
        Intrinsics.checkParameterIsNotNull(sink2, "$this$deflate");
        Intrinsics.checkParameterIsNotNull(deflater, "deflater");
        return new DeflaterSink(sink2, deflater);
    }
}

