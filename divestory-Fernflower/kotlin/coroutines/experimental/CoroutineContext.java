package kotlin.coroutines.experimental;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001:\u0002\u0011\u0012J5\u0010\u0002\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u00032\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u0002H\u00030\u0006H&¢\u0006\u0002\u0010\bJ(\u0010\t\u001a\u0004\u0018\u0001H\n\"\b\b\u0000\u0010\n*\u00020\u00072\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\n0\fH¦\u0002¢\u0006\u0002\u0010\rJ\u0014\u0010\u000e\u001a\u00020\u00002\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\fH&J\u0011\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u0000H\u0096\u0002¨\u0006\u0013"},
   d2 = {"Lkotlin/coroutines/experimental/CoroutineContext;", "", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "(Lkotlin/coroutines/experimental/CoroutineContext$Key;)Lkotlin/coroutines/experimental/CoroutineContext$Element;", "minusKey", "plus", "context", "Element", "Key", "kotlin-stdlib-coroutines"},
   k = 1,
   mv = {1, 1, 16}
)
public interface CoroutineContext {
   <R> R fold(R var1, Function2<? super R, ? super CoroutineContext.Element, ? extends R> var2);

   <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> var1);

   CoroutineContext minusKey(CoroutineContext.Key<?> var1);

   CoroutineContext plus(CoroutineContext var1);

   @Metadata(
      bv = {1, 0, 3},
      k = 3,
      mv = {1, 1, 16}
   )
   public static final class DefaultImpls {
      public static CoroutineContext plus(CoroutineContext var0, CoroutineContext var1) {
         Intrinsics.checkParameterIsNotNull(var1, "context");
         if (var1 != EmptyCoroutineContext.INSTANCE) {
            var0 = (CoroutineContext)var1.fold(var0, (Function2)null.INSTANCE);
         }

         return var0;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J5\u0010\u0006\u001a\u0002H\u0007\"\u0004\b\u0000\u0010\u00072\u0006\u0010\b\u001a\u0002H\u00072\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u0002H\u00070\nH\u0016¢\u0006\u0002\u0010\u000bJ(\u0010\f\u001a\u0004\u0018\u0001H\r\"\b\b\u0000\u0010\r*\u00020\u00002\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\r0\u0003H\u0096\u0002¢\u0006\u0002\u0010\u000eJ\u0014\u0010\u000f\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016R\u0016\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0010"},
      d2 = {"Lkotlin/coroutines/experimental/CoroutineContext$Element;", "Lkotlin/coroutines/experimental/CoroutineContext;", "key", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "getKey", "()Lkotlin/coroutines/experimental/CoroutineContext$Key;", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "(Lkotlin/coroutines/experimental/CoroutineContext$Key;)Lkotlin/coroutines/experimental/CoroutineContext$Element;", "minusKey", "kotlin-stdlib-coroutines"},
      k = 1,
      mv = {1, 1, 16}
   )
   public interface Element extends CoroutineContext {
      <R> R fold(R var1, Function2<? super R, ? super CoroutineContext.Element, ? extends R> var2);

      <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> var1);

      CoroutineContext.Key<?> getKey();

      CoroutineContext minusKey(CoroutineContext.Key<?> var1);

      @Metadata(
         bv = {1, 0, 3},
         k = 3,
         mv = {1, 1, 16}
      )
      public static final class DefaultImpls {
         public static <R> R fold(CoroutineContext.Element var0, R var1, Function2<? super R, ? super CoroutineContext.Element, ? extends R> var2) {
            Intrinsics.checkParameterIsNotNull(var2, "operation");
            return var2.invoke(var1, var0);
         }

         public static <E extends CoroutineContext.Element> E get(CoroutineContext.Element var0, CoroutineContext.Key<E> var1) {
            Intrinsics.checkParameterIsNotNull(var1, "key");
            if (var0.getKey() == var1) {
               if (var0 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type E");
               }
            } else {
               var0 = null;
            }

            return var0;
         }

         public static CoroutineContext minusKey(CoroutineContext.Element var0, CoroutineContext.Key<?> var1) {
            Intrinsics.checkParameterIsNotNull(var1, "key");
            Object var2 = var0;
            if (var0.getKey() == var1) {
               var2 = EmptyCoroutineContext.INSTANCE;
            }

            return (CoroutineContext)var2;
         }

         public static CoroutineContext plus(CoroutineContext.Element var0, CoroutineContext var1) {
            Intrinsics.checkParameterIsNotNull(var1, "context");
            return CoroutineContext.DefaultImpls.plus((CoroutineContext)var0, var1);
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003¨\u0006\u0004"},
      d2 = {"Lkotlin/coroutines/experimental/CoroutineContext$Key;", "E", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "", "kotlin-stdlib-coroutines"},
      k = 1,
      mv = {1, 1, 16}
   )
   public interface Key<E extends CoroutineContext.Element> {
   }
}
