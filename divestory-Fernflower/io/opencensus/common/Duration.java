package io.opencensus.common;

import java.util.concurrent.TimeUnit;

public abstract class Duration implements Comparable<Duration> {
   Duration() {
   }

   public static Duration create(long var0, int var2) {
      StringBuilder var4;
      if (var0 >= -315576000000L) {
         if (var0 > 315576000000L) {
            var4 = new StringBuilder();
            var4.append("'seconds' is greater than maximum (315576000000): ");
            var4.append(var0);
            throw new IllegalArgumentException(var4.toString());
         } else if (var2 >= -999999999) {
            if (var2 > 999999999) {
               var4 = new StringBuilder();
               var4.append("'nanos' is greater than maximum (999999999): ");
               var4.append(var2);
               throw new IllegalArgumentException(var4.toString());
            } else {
               long var5;
               int var3 = (var5 = var0 - 0L) == 0L ? 0 : (var5 < 0L ? -1 : 1);
               if ((var3 >= 0 || var2 <= 0) && (var3 <= 0 || var2 >= 0)) {
                  return new AutoValue_Duration(var0, var2);
               } else {
                  var4 = new StringBuilder();
                  var4.append("'seconds' and 'nanos' have inconsistent sign: seconds=");
                  var4.append(var0);
                  var4.append(", nanos=");
                  var4.append(var2);
                  throw new IllegalArgumentException(var4.toString());
               }
            }
         } else {
            var4 = new StringBuilder();
            var4.append("'nanos' is less than minimum (-999999999): ");
            var4.append(var2);
            throw new IllegalArgumentException(var4.toString());
         }
      } else {
         var4 = new StringBuilder();
         var4.append("'seconds' is less than minimum (-315576000000): ");
         var4.append(var0);
         throw new IllegalArgumentException(var4.toString());
      }
   }

   public static Duration fromMillis(long var0) {
      return create(var0 / 1000L, (int)(var0 % 1000L * 1000000L));
   }

   public int compareTo(Duration var1) {
      int var2 = TimeUtils.compareLongs(this.getSeconds(), var1.getSeconds());
      return var2 != 0 ? var2 : TimeUtils.compareLongs((long)this.getNanos(), (long)var1.getNanos());
   }

   public abstract int getNanos();

   public abstract long getSeconds();

   public long toMillis() {
      return TimeUnit.SECONDS.toMillis(this.getSeconds()) + TimeUnit.NANOSECONDS.toMillis((long)this.getNanos());
   }
}
