package kotlin.coroutines.experimental.jvm.internal;

import kotlin.Metadata;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.ContinuationInterceptor;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a*\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0000\u001a \u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001¨\u0006\u0007"},
   d2 = {"interceptContinuationIfNeeded", "Lkotlin/coroutines/experimental/Continuation;", "T", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "continuation", "normalizeContinuation", "kotlin-stdlib-coroutines"},
   k = 2,
   mv = {1, 1, 16}
)
public final class CoroutineIntrinsics {
   public static final <T> Continuation<T> interceptContinuationIfNeeded(CoroutineContext var0, Continuation<? super T> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "context");
      Intrinsics.checkParameterIsNotNull(var1, "continuation");
      ContinuationInterceptor var2 = (ContinuationInterceptor)var0.get((CoroutineContext.Key)ContinuationInterceptor.Key);
      Continuation var3 = var1;
      if (var2 != null) {
         Continuation var4 = var2.interceptContinuation(var1);
         var3 = var1;
         if (var4 != null) {
            var3 = var4;
         }
      }

      return var3;
   }

   public static final <T> Continuation<T> normalizeContinuation(Continuation<? super T> var0) {
      Intrinsics.checkParameterIsNotNull(var0, "continuation");
      Continuation var1;
      if (!(var0 instanceof CoroutineImpl)) {
         var1 = null;
      } else {
         var1 = var0;
      }

      CoroutineImpl var2 = (CoroutineImpl)var1;
      var1 = var0;
      if (var2 != null) {
         Continuation var3 = var2.getFacade();
         var1 = var0;
         if (var3 != null) {
            var1 = var3;
         }
      }

      return var1;
   }
}
