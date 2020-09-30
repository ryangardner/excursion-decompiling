package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bg\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fJ(\u0010\u0002\u001a\u0004\u0018\u0001H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u00012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0005H\u0096\u0002¢\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\t0\b\"\u0004\b\u0000\u0010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\bH&J\u0014\u0010\u000b\u001a\u00020\f2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0016J\u0014\u0010\r\u001a\u00020\u000e2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\bH\u0016¨\u0006\u0010"},
   d2 = {"Lkotlin/coroutines/ContinuationInterceptor;", "Lkotlin/coroutines/CoroutineContext$Element;", "get", "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "interceptContinuation", "Lkotlin/coroutines/Continuation;", "T", "continuation", "minusKey", "Lkotlin/coroutines/CoroutineContext;", "releaseInterceptedContinuation", "", "Key", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public interface ContinuationInterceptor extends CoroutineContext.Element {
   ContinuationInterceptor.Key Key = ContinuationInterceptor.Key.$$INSTANCE;

   <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> var1);

   <T> Continuation<T> interceptContinuation(Continuation<? super T> var1);

   CoroutineContext minusKey(CoroutineContext.Key<?> var1);

   void releaseInterceptedContinuation(Continuation<?> var1);

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
         boolean var2 = var1 instanceof AbstractCoroutineContextKey;
         AbstractCoroutineContextKey var3 = null;
         CoroutineContext.Element var4 = null;
         if (var2) {
            var3 = (AbstractCoroutineContextKey)var1;
            CoroutineContext.Element var5 = var4;
            if (var3.isSubKey$kotlin_stdlib(var0.getKey())) {
               var5 = var3.tryCast$kotlin_stdlib((CoroutineContext.Element)var0);
               if (!(var5 instanceof CoroutineContext.Element)) {
                  var5 = var4;
               }
            }

            return var5;
         } else {
            var4 = var3;
            if (ContinuationInterceptor.Key == var1) {
               if (var0 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type E");
               }

               var4 = (CoroutineContext.Element)var0;
            }

            return var4;
         }
      }

      public static CoroutineContext minusKey(ContinuationInterceptor var0, CoroutineContext.Key<?> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "key");
         if (var1 instanceof AbstractCoroutineContextKey) {
            AbstractCoroutineContextKey var2 = (AbstractCoroutineContextKey)var1;
            Object var3 = var0;
            if (var2.isSubKey$kotlin_stdlib(((ContinuationInterceptor)var0).getKey())) {
               var3 = var0;
               if (var2.tryCast$kotlin_stdlib((CoroutineContext.Element)var0) != null) {
                  var3 = EmptyCoroutineContext.INSTANCE;
               }
            }

            return (CoroutineContext)var3;
         } else {
            if (ContinuationInterceptor.Key == var1) {
               var0 = EmptyCoroutineContext.INSTANCE;
            }

            return (CoroutineContext)var0;
         }
      }

      public static CoroutineContext plus(ContinuationInterceptor var0, CoroutineContext var1) {
         Intrinsics.checkParameterIsNotNull(var1, "context");
         return CoroutineContext.Element.DefaultImpls.plus((CoroutineContext.Element)var0, var1);
      }

      public static void releaseInterceptedContinuation(ContinuationInterceptor var0, Continuation<?> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "continuation");
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"},
      d2 = {"Lkotlin/coroutines/ContinuationInterceptor$Key;", "Lkotlin/coroutines/CoroutineContext$Key;", "Lkotlin/coroutines/ContinuationInterceptor;", "()V", "kotlin-stdlib"},
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
