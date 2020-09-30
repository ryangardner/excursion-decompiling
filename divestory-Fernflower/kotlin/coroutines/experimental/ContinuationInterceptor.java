package kotlin.coroutines.experimental;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006J\"\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003H&¨\u0006\u0007"},
   d2 = {"Lkotlin/coroutines/experimental/ContinuationInterceptor;", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "interceptContinuation", "Lkotlin/coroutines/experimental/Continuation;", "T", "continuation", "Key", "kotlin-stdlib-coroutines"},
   k = 1,
   mv = {1, 1, 16}
)
public interface ContinuationInterceptor extends CoroutineContext.Element {
   ContinuationInterceptor.Key Key = ContinuationInterceptor.Key.$$INSTANCE;

   <T> Continuation<T> interceptContinuation(Continuation<? super T> var1);

   @Metadata(
      bv = {1, 0, 3},
      k = 3,
      mv = {1, 1, 16}
   )
   public static final class DefaultImpls {
      public static <R> R fold(ContinuationInterceptor var0, R var1, Function2<? super R, ? super CoroutineContext.Element, ? extends R> var2) {
         Intrinsics.checkParameterIsNotNull(var2, "operation");
         return CoroutineContext.Element.DefaultImpls.fold((CoroutineContext.Element)var0, var1, var2);
      }

      public static <E extends CoroutineContext.Element> E get(ContinuationInterceptor var0, CoroutineContext.Key<E> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "key");
         return CoroutineContext.Element.DefaultImpls.get((CoroutineContext.Element)var0, var1);
      }

      public static CoroutineContext minusKey(ContinuationInterceptor var0, CoroutineContext.Key<?> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "key");
         return CoroutineContext.Element.DefaultImpls.minusKey((CoroutineContext.Element)var0, var1);
      }

      public static CoroutineContext plus(ContinuationInterceptor var0, CoroutineContext var1) {
         Intrinsics.checkParameterIsNotNull(var1, "context");
         return CoroutineContext.Element.DefaultImpls.plus((CoroutineContext.Element)var0, var1);
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"},
      d2 = {"Lkotlin/coroutines/experimental/ContinuationInterceptor$Key;", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "Lkotlin/coroutines/experimental/ContinuationInterceptor;", "()V", "kotlin-stdlib-coroutines"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Key implements CoroutineContext.Key<ContinuationInterceptor> {
      // $FF: synthetic field
      static final ContinuationInterceptor.Key $$INSTANCE = new ContinuationInterceptor.Key();

      private Key() {
      }
   }
}
