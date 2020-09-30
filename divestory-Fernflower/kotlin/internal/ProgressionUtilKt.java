package kotlin.internal;

import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0006\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002\u001a \u0010\u0000\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a \u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u0001H\u0001\u001a \u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0005H\u0001\u001a\u0018\u0010\n\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0002\u001a\u0018\u0010\n\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005H\u0002¨\u0006\u000b"},
   d2 = {"differenceModulo", "", "a", "b", "c", "", "getProgressionLastElement", "start", "end", "step", "mod", "kotlin-stdlib"},
   k = 2,
   mv = {1, 1, 16}
)
public final class ProgressionUtilKt {
   private static final int differenceModulo(int var0, int var1, int var2) {
      return mod(mod(var0, var2) - mod(var1, var2), var2);
   }

   private static final long differenceModulo(long var0, long var2, long var4) {
      return mod(mod(var0, var4) - mod(var2, var4), var4);
   }

   public static final int getProgressionLastElement(int var0, int var1, int var2) {
      if (var2 > 0) {
         if (var0 < var1) {
            var1 -= differenceModulo(var1, var0, var2);
         }
      } else {
         if (var2 >= 0) {
            throw (Throwable)(new IllegalArgumentException("Step is zero."));
         }

         if (var0 > var1) {
            var1 += differenceModulo(var0, var1, -var2);
         }
      }

      return var1;
   }

   public static final long getProgressionLastElement(long var0, long var2, long var4) {
      long var7;
      int var6 = (var7 = var4 - 0L) == 0L ? 0 : (var7 < 0L ? -1 : 1);
      if (var6 > 0) {
         if (var0 < var2) {
            var2 -= differenceModulo(var2, var0, var4);
         }
      } else {
         if (var6 >= 0) {
            throw (Throwable)(new IllegalArgumentException("Step is zero."));
         }

         if (var0 > var2) {
            var2 += differenceModulo(var0, var2, -var4);
         }
      }

      return var2;
   }

   private static final int mod(int var0, int var1) {
      var0 %= var1;
      if (var0 < 0) {
         var0 += var1;
      }

      return var0;
   }

   private static final long mod(long var0, long var2) {
      var0 %= var2;
      if (var0 < 0L) {
         var0 += var2;
      }

      return var0;
   }
}
