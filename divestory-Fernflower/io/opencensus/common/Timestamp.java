package io.opencensus.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Timestamp implements Comparable<Timestamp> {
   Timestamp() {
   }

   public static Timestamp create(long var0, int var2) {
      StringBuilder var3;
      if (var0 >= -315576000000L) {
         if (var0 <= 315576000000L) {
            if (var2 >= 0) {
               if (var2 <= 999999999) {
                  return new AutoValue_Timestamp(var0, var2);
               } else {
                  var3 = new StringBuilder();
                  var3.append("'nanos' is greater than maximum (999999999): ");
                  var3.append(var2);
                  throw new IllegalArgumentException(var3.toString());
               }
            } else {
               var3 = new StringBuilder();
               var3.append("'nanos' is less than zero: ");
               var3.append(var2);
               throw new IllegalArgumentException(var3.toString());
            }
         } else {
            var3 = new StringBuilder();
            var3.append("'seconds' is greater than maximum (315576000000): ");
            var3.append(var0);
            throw new IllegalArgumentException(var3.toString());
         }
      } else {
         var3 = new StringBuilder();
         var3.append("'seconds' is less than minimum (-315576000000): ");
         var3.append(var0);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   private static long floorDiv(long var0, long var2) {
      return BigDecimal.valueOf(var0).divide(BigDecimal.valueOf(var2), 0, RoundingMode.FLOOR).longValue();
   }

   private static long floorMod(long var0, long var2) {
      return var0 - floorDiv(var0, var2) * var2;
   }

   public static Timestamp fromMillis(long var0) {
      return create(floorDiv(var0, 1000L), (int)((long)((int)floorMod(var0, 1000L)) * 1000000L));
   }

   private static Timestamp ofEpochSecond(long var0, long var2) {
      return create(TimeUtils.checkedAdd(var0, floorDiv(var2, 1000000000L)), (int)floorMod(var2, 1000000000L));
   }

   private Timestamp plus(long var1, long var3) {
      return (var1 | var3) == 0L ? this : ofEpochSecond(TimeUtils.checkedAdd(TimeUtils.checkedAdd(this.getSeconds(), var1), var3 / 1000000000L), (long)this.getNanos() + var3 % 1000000000L);
   }

   public Timestamp addDuration(Duration var1) {
      return this.plus(var1.getSeconds(), (long)var1.getNanos());
   }

   public Timestamp addNanos(long var1) {
      return this.plus(0L, var1);
   }

   public int compareTo(Timestamp var1) {
      int var2 = TimeUtils.compareLongs(this.getSeconds(), var1.getSeconds());
      return var2 != 0 ? var2 : TimeUtils.compareLongs((long)this.getNanos(), (long)var1.getNanos());
   }

   public abstract int getNanos();

   public abstract long getSeconds();

   public Duration subtractTimestamp(Timestamp var1) {
      long var2 = this.getSeconds() - var1.getSeconds();
      int var4 = this.getNanos() - var1.getNanos();
      long var9;
      int var5 = (var9 = var2 - 0L) == 0L ? 0 : (var9 < 0L ? -1 : 1);
      long var6;
      int var8;
      if (var5 < 0 && var4 > 0) {
         var6 = var2 + 1L;
         var2 = (long)var4 - 1000000000L;
      } else {
         var6 = var2;
         var8 = var4;
         if (var5 <= 0) {
            return Duration.create(var6, var8);
         }

         var6 = var2;
         var8 = var4;
         if (var4 >= 0) {
            return Duration.create(var6, var8);
         }

         var6 = var2 - 1L;
         var2 = (long)var4 + 1000000000L;
      }

      var8 = (int)var2;
      return Duration.create(var6, var8);
   }
}
