package kotlin;

import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a!\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000b\b\u0000\u0010\u0002\u0018\u0001¢\u0006\u0002\b\u0003H\u0086\b¢\u0006\u0002\u0010\u0004¨\u0006\u0005"},
   d2 = {"emptyArray", "", "T", "Lkotlin/internal/PureReifiable;", "()[Ljava/lang/Object;", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class ArrayIntrinsicsKt {
   // $FF: synthetic method
   public static final <T> T[] emptyArray() {
      Intrinsics.reifiedOperationMarker(0, "T?");
      return new Object[0];
   }
}
