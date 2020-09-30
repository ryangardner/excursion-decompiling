package okio;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0086\b¨\u0006\u0003"},
   d2 = {"gzip", "Lokio/GzipSink;", "Lokio/Sink;", "okio"},
   k = 2,
   mv = {1, 1, 16}
)
public final class _GzipSinkExtensions {
   public static final GzipSink gzip(Sink var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$gzip");
      return new GzipSink(var0);
   }
}
