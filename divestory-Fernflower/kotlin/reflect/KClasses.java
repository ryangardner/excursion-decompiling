package kotlin.reflect;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0010\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a+\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002H\u0007¢\u0006\u0002\u0010\u0005\u001a-\u0010\u0006\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002H\u0007¢\u0006\u0002\u0010\u0005¨\u0006\u0007"},
   d2 = {"cast", "T", "", "Lkotlin/reflect/KClass;", "value", "(Lkotlin/reflect/KClass;Ljava/lang/Object;)Ljava/lang/Object;", "safeCast", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class KClasses {
   public static final <T> T cast(KClass<T> var0, Object var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$cast");
      if (var0.isInstance(var1)) {
         if (var1 != null) {
            return var1;
         } else {
            throw new TypeCastException("null cannot be cast to non-null type T");
         }
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Value cannot be cast to ");
         var2.append(var0.getQualifiedName());
         throw (Throwable)(new ClassCastException(var2.toString()));
      }
   }

   public static final <T> T safeCast(KClass<T> var0, Object var1) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$safeCast");
      if (var0.isInstance(var1)) {
         if (var1 == null) {
            throw new TypeCastException("null cannot be cast to non-null type T");
         }
      } else {
         var1 = null;
      }

      return var1;
   }
}
