package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0018\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a+\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0007¢\u0006\u0002\u0010\u0005\u001a\u0018\u0010\u0006\u001a\u00020\u0007*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0007¨\u0006\b"},
   d2 = {"getPolymorphicElement", "E", "Lkotlin/coroutines/CoroutineContext$Element;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Element;Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "minusPolymorphicKey", "Lkotlin/coroutines/CoroutineContext;", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class CoroutineContextImplKt {
   public static final <E extends CoroutineContext.Element> E getPolymorphicElement(CoroutineContext.Element var0, CoroutineContext.Key<E> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$getPolymorphicElement");
      Intrinsics.checkParameterIsNotNull(var1, "key");
      boolean var2 = var1 instanceof AbstractCoroutineContextKey;
      Object var3 = null;
      if (var2) {
         AbstractCoroutineContextKey var4 = (AbstractCoroutineContextKey)var1;
         CoroutineContext.Element var5 = (CoroutineContext.Element)var3;
         if (var4.isSubKey$kotlin_stdlib(var0.getKey())) {
            var5 = var4.tryCast$kotlin_stdlib(var0);
            if (!(var5 instanceof CoroutineContext.Element)) {
               var5 = (CoroutineContext.Element)var3;
            }
         }

         return var5;
      } else {
         if (var0.getKey() != var1) {
            var0 = null;
         }

         return var0;
      }
   }

   public static final CoroutineContext minusPolymorphicKey(CoroutineContext.Element var0, CoroutineContext.Key<?> var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$minusPolymorphicKey");
      Intrinsics.checkParameterIsNotNull(var1, "key");
      if (var1 instanceof AbstractCoroutineContextKey) {
         AbstractCoroutineContextKey var4 = (AbstractCoroutineContextKey)var1;
         Object var3 = var0;
         if (var4.isSubKey$kotlin_stdlib(var0.getKey())) {
            var3 = var0;
            if (var4.tryCast$kotlin_stdlib(var0) != null) {
               var3 = EmptyCoroutineContext.INSTANCE;
            }
         }

         return (CoroutineContext)var3;
      } else {
         Object var2 = var0;
         if (var0.getKey() == var1) {
            var2 = EmptyCoroutineContext.INSTANCE;
         }

         return (CoroutineContext)var2;
      }
   }
}
