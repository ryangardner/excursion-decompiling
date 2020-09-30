package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0010\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001aG\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007¢\u0006\u0002\u0010\b\u001a?\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007¢\u0006\u0002\u0010\t\u001aG\u0010\n\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u0004\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007¢\u0006\u0002\u0010\b\u001a?\u0010\n\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u00012\u001a\u0010\u0005\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00010\u0006j\n\u0012\u0006\b\u0000\u0012\u0002H\u0001`\u0007H\u0007¢\u0006\u0002\u0010\t¨\u0006\u000b"},
   d2 = {"maxOf", "T", "a", "b", "c", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)Ljava/lang/Object;", "minOf", "kotlin-stdlib"},
   k = 5,
   mv = {1, 1, 16},
   xi = 1,
   xs = "kotlin/comparisons/ComparisonsKt"
)
class ComparisonsKt___ComparisonsKt extends ComparisonsKt___ComparisonsJvmKt {
   public ComparisonsKt___ComparisonsKt() {
   }

   public static final <T> T maxOf(T var0, T var1, T var2, Comparator<? super T> var3) {
      Intrinsics.checkParameterIsNotNull(var3, "comparator");
      return ComparisonsKt.maxOf(var0, ComparisonsKt.maxOf(var1, var2, var3), var3);
   }

   public static final <T> T maxOf(T var0, T var1, Comparator<? super T> var2) {
      Intrinsics.checkParameterIsNotNull(var2, "comparator");
      if (var2.compare(var0, var1) < 0) {
         var0 = var1;
      }

      return var0;
   }

   public static final <T> T minOf(T var0, T var1, T var2, Comparator<? super T> var3) {
      Intrinsics.checkParameterIsNotNull(var3, "comparator");
      return ComparisonsKt.minOf(var0, ComparisonsKt.minOf(var1, var2, var3), var3);
   }

   public static final <T> T minOf(T var0, T var1, Comparator<? super T> var2) {
      Intrinsics.checkParameterIsNotNull(var2, "comparator");
      if (var2.compare(var0, var1) > 0) {
         var0 = var1;
      }

      return var0;
   }
}
