package okio;

import java.util.zip.Deflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0086\b¨\u0006\u0005"},
   d2 = {"deflate", "Lokio/DeflaterSink;", "Lokio/Sink;", "deflater", "Ljava/util/zip/Deflater;", "okio"},
   k = 2,
   mv = {1, 1, 16}
)
public final class _DeflaterSinkExtensions {
   public static final DeflaterSink deflate(Sink var0, Deflater var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$deflate");
      Intrinsics.checkParameterIsNotNull(var1, "deflater");
      return new DeflaterSink(var0, var1);
   }

   // $FF: synthetic method
   public static DeflaterSink deflate$default(Sink var0, Deflater var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = new Deflater();
      }

      Intrinsics.checkParameterIsNotNull(var0, "$this$deflate");
      Intrinsics.checkParameterIsNotNull(var1, "deflater");
      return new DeflaterSink(var0, var1);
   }
}
