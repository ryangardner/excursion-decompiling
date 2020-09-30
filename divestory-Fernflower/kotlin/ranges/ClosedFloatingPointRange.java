package kotlin.ranges;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\bg\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\u0005H\u0016J\u001d\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00028\u00002\u0006\u0010\u000b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\f¨\u0006\r"},
   d2 = {"Lkotlin/ranges/ClosedFloatingPointRange;", "T", "", "Lkotlin/ranges/ClosedRange;", "contains", "", "value", "(Ljava/lang/Comparable;)Z", "isEmpty", "lessThanOrEquals", "a", "b", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Z", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public interface ClosedFloatingPointRange<T extends Comparable<? super T>> extends ClosedRange<T> {
   boolean contains(T var1);

   boolean isEmpty();

   boolean lessThanOrEquals(T var1, T var2);

   @Metadata(
      bv = {1, 0, 3},
      k = 3,
      mv = {1, 1, 16}
   )
   public static final class DefaultImpls {
      public static <T extends Comparable<? super T>> boolean contains(ClosedFloatingPointRange<T> var0, T var1) {
         Intrinsics.checkParameterIsNotNull(var1, "value");
         boolean var2;
         if (var0.lessThanOrEquals(var0.getStart(), var1) && var0.lessThanOrEquals(var1, var0.getEndInclusive())) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public static <T extends Comparable<? super T>> boolean isEmpty(ClosedFloatingPointRange<T> var0) {
         return var0.lessThanOrEquals(var0.getStart(), var0.getEndInclusive()) ^ true;
      }
   }
}
