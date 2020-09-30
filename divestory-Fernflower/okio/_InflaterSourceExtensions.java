package okio;

import java.util.zip.Inflater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0086\b¨\u0006\u0005"},
   d2 = {"inflate", "Lokio/InflaterSource;", "Lokio/Source;", "inflater", "Ljava/util/zip/Inflater;", "okio"},
   k = 2,
   mv = {1, 1, 16}
)
public final class _InflaterSourceExtensions {
   public static final InflaterSource inflate(Source var0, Inflater var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$inflate");
      Intrinsics.checkParameterIsNotNull(var1, "inflater");
      return new InflaterSource(var0, var1);
   }

   // $FF: synthetic method
   public static InflaterSource inflate$default(Source var0, Inflater var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = new Inflater();
      }

      Intrinsics.checkParameterIsNotNull(var0, "$this$inflate");
      Intrinsics.checkParameterIsNotNull(var1, "inflater");
      return new InflaterSource(var0, var1);
   }
}
