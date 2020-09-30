package kotlin;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u000f\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"},
   d2 = {"Lkotlin/NotImplementedError;", "Ljava/lang/Error;", "Lkotlin/Error;", "message", "", "(Ljava/lang/String;)V", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class NotImplementedError extends Error {
   public NotImplementedError() {
      this((String)null, 1, (DefaultConstructorMarker)null);
   }

   public NotImplementedError(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "message");
      super(var1);
   }

   // $FF: synthetic method
   public NotImplementedError(String var1, int var2, DefaultConstructorMarker var3) {
      if ((var2 & 1) != 0) {
         var1 = "An operation is not implemented.";
      }

      this(var1);
   }
}
