package kotlin;

import kotlin.jvm.functions.Function1;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a-\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u001f\u0010\b\u001a\u00020\u00012\n\u0010\t\u001a\u00020\u0001\"\u00020\u0006H\u0087\bø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"},
   d2 = {"UIntArray", "Lkotlin/UIntArray;", "size", "", "init", "Lkotlin/Function1;", "Lkotlin/UInt;", "(ILkotlin/jvm/functions/Function1;)[I", "uintArrayOf", "elements", "uintArrayOf--ajY-9A", "([I)[I", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class UIntArrayKt {
   private static final int[] UIntArray(int var0, Function1<? super Integer, UInt> var1) {
      int[] var2 = new int[var0];

      for(int var3 = 0; var3 < var0; ++var3) {
         var2[var3] = ((UInt)var1.invoke(var3)).unbox-impl();
      }

      return UIntArray.constructor-impl(var2);
   }

   private static final int[] uintArrayOf__ajY_9A/* $FF was: uintArrayOf--ajY-9A*/(int... var0) {
      return var0;
   }
}
