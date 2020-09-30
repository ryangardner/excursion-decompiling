package kotlin;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\u001a*\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\u001a(\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004¨\u0006\t"},
   d2 = {"lazy", "Lkotlin/Lazy;", "T", "initializer", "Lkotlin/Function0;", "lock", "", "mode", "Lkotlin/LazyThreadSafetyMode;", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/LazyKt"
)
class LazyKt__LazyJVMKt {
   public LazyKt__LazyJVMKt() {
   }

   public static final <T> Lazy<T> lazy(Object var0, Function0<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "initializer");
      return (Lazy)(new SynchronizedLazyImpl(var1, var0));
   }

   public static final <T> Lazy<T> lazy(LazyThreadSafetyMode var0, Function0<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "mode");
      Intrinsics.checkParameterIsNotNull(var1, "initializer");
      int var2 = LazyKt$WhenMappings.$EnumSwitchMapping$0[var0.ordinal()];
      Lazy var3;
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 != 3) {
               throw new NoWhenBranchMatchedException();
            }

            var3 = (Lazy)(new UnsafeLazyImpl(var1));
         } else {
            var3 = (Lazy)(new SafePublicationLazyImpl(var1));
         }
      } else {
         var3 = (Lazy)(new SynchronizedLazyImpl(var1, (Object)null, 2, (DefaultConstructorMarker)null));
      }

      return var3;
   }

   public static final <T> Lazy<T> lazy(Function0<? extends T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "initializer");
      return (Lazy)(new SynchronizedLazyImpl(var0, (Object)null, 2, (DefaultConstructorMarker)null));
   }
}
