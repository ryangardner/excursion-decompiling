package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class TopKSelector<T> {
   private final T[] buffer;
   private int bufferSize;
   private final Comparator<? super T> comparator;
   private final int k;
   @NullableDecl
   private T threshold;

   private TopKSelector(Comparator<? super T> var1, int var2) {
      this.comparator = (Comparator)Preconditions.checkNotNull(var1, "comparator");
      this.k = var2;
      boolean var3;
      if (var2 >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "k must be nonnegative, was %s", var2);
      this.buffer = (Object[])(new Object[var2 * 2]);
      this.bufferSize = 0;
      this.threshold = null;
   }

   public static <T extends Comparable<? super T>> TopKSelector<T> greatest(int var0) {
      return greatest(var0, Ordering.natural());
   }

   public static <T> TopKSelector<T> greatest(int var0, Comparator<? super T> var1) {
      return new TopKSelector(Ordering.from(var1).reverse(), var0);
   }

   public static <T extends Comparable<? super T>> TopKSelector<T> least(int var0) {
      return least(var0, Ordering.natural());
   }

   public static <T> TopKSelector<T> least(int var0, Comparator<? super T> var1) {
      return new TopKSelector(var1, var0);
   }

   private int partition(int var1, int var2, int var3) {
      Object[] var4 = this.buffer;
      Object var5 = var4[var3];
      var4[var3] = var4[var2];

      int var6;
      for(var3 = var1; var1 < var2; var3 = var6) {
         var6 = var3;
         if (this.comparator.compare(this.buffer[var1], var5) < 0) {
            this.swap(var3, var1);
            var6 = var3 + 1;
         }

         ++var1;
      }

      var4 = this.buffer;
      var4[var2] = var4[var3];
      var4[var3] = var5;
      return var3;
   }

   private void swap(int var1, int var2) {
      Object[] var3 = this.buffer;
      Object var4 = var3[var1];
      var3[var1] = var3[var2];
      var3[var2] = var4;
   }

   private void trim() {
      int var1 = this.k * 2 - 1;
      int var2 = IntMath.log2(var1 + 0, RoundingMode.CEILING);
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;

      int var6;
      while(true) {
         var6 = var5;
         if (var3 >= var1) {
            break;
         }

         int var7 = this.partition(var3, var1, var3 + var1 + 1 >>> 1);
         int var8 = this.k;
         if (var7 > var8) {
            --var7;
            var8 = var3;
            var6 = var5;
         } else {
            var6 = var5;
            if (var7 >= var8) {
               break;
            }

            var8 = Math.max(var7, var3 + 1);
            var6 = var7;
            var7 = var1;
         }

         int var9 = var4 + 1;
         var1 = var7;
         var3 = var8;
         var4 = var9;
         var5 = var6;
         if (var9 >= var2 * 3) {
            Arrays.sort(this.buffer, var8, var7, this.comparator);
            break;
         }
      }

      this.bufferSize = this.k;
      this.threshold = this.buffer[var6];

      while(true) {
         ++var6;
         if (var6 >= this.k) {
            return;
         }

         if (this.comparator.compare(this.buffer[var6], this.threshold) > 0) {
            this.threshold = this.buffer[var6];
         }
      }
   }

   public void offer(@NullableDecl T var1) {
      int var2 = this.k;
      if (var2 != 0) {
         int var3 = this.bufferSize;
         if (var3 == 0) {
            this.buffer[0] = var1;
            this.threshold = var1;
            this.bufferSize = 1;
         } else {
            Object[] var4;
            if (var3 < var2) {
               var4 = this.buffer;
               this.bufferSize = var3 + 1;
               var4[var3] = var1;
               if (this.comparator.compare(var1, this.threshold) > 0) {
                  this.threshold = var1;
               }
            } else if (this.comparator.compare(var1, this.threshold) < 0) {
               var4 = this.buffer;
               var2 = this.bufferSize;
               var3 = var2 + 1;
               this.bufferSize = var3;
               var4[var2] = var1;
               if (var3 == this.k * 2) {
                  this.trim();
               }
            }
         }

      }
   }

   public void offerAll(Iterable<? extends T> var1) {
      this.offerAll(var1.iterator());
   }

   public void offerAll(Iterator<? extends T> var1) {
      while(var1.hasNext()) {
         this.offer(var1.next());
      }

   }

   public List<T> topK() {
      Arrays.sort(this.buffer, 0, this.bufferSize, this.comparator);
      int var1 = this.bufferSize;
      int var2 = this.k;
      if (var1 > var2) {
         Object[] var3 = this.buffer;
         Arrays.fill(var3, var2, var3.length, (Object)null);
         var1 = this.k;
         this.bufferSize = var1;
         this.threshold = this.buffer[var1 - 1];
      }

      return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(this.buffer, this.bufferSize)));
   }
}
