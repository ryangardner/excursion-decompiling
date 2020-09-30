package com.google.common.cache;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class CacheStats {
   private final long evictionCount;
   private final long hitCount;
   private final long loadExceptionCount;
   private final long loadSuccessCount;
   private final long missCount;
   private final long totalLoadTime;

   public CacheStats(long var1, long var3, long var5, long var7, long var9, long var11) {
      boolean var13 = true;
      boolean var14;
      if (var1 >= 0L) {
         var14 = true;
      } else {
         var14 = false;
      }

      Preconditions.checkArgument(var14);
      if (var3 >= 0L) {
         var14 = true;
      } else {
         var14 = false;
      }

      Preconditions.checkArgument(var14);
      if (var5 >= 0L) {
         var14 = true;
      } else {
         var14 = false;
      }

      Preconditions.checkArgument(var14);
      if (var7 >= 0L) {
         var14 = true;
      } else {
         var14 = false;
      }

      Preconditions.checkArgument(var14);
      if (var9 >= 0L) {
         var14 = true;
      } else {
         var14 = false;
      }

      Preconditions.checkArgument(var14);
      if (var11 >= 0L) {
         var14 = var13;
      } else {
         var14 = false;
      }

      Preconditions.checkArgument(var14);
      this.hitCount = var1;
      this.missCount = var3;
      this.loadSuccessCount = var5;
      this.loadExceptionCount = var7;
      this.totalLoadTime = var9;
      this.evictionCount = var11;
   }

   public double averageLoadPenalty() {
      long var1 = LongMath.saturatedAdd(this.loadSuccessCount, this.loadExceptionCount);
      double var3;
      if (var1 == 0L) {
         var3 = 0.0D;
      } else {
         var3 = (double)this.totalLoadTime / (double)var1;
      }

      return var3;
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof CacheStats;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         CacheStats var5 = (CacheStats)var1;
         var4 = var3;
         if (this.hitCount == var5.hitCount) {
            var4 = var3;
            if (this.missCount == var5.missCount) {
               var4 = var3;
               if (this.loadSuccessCount == var5.loadSuccessCount) {
                  var4 = var3;
                  if (this.loadExceptionCount == var5.loadExceptionCount) {
                     var4 = var3;
                     if (this.totalLoadTime == var5.totalLoadTime) {
                        var4 = var3;
                        if (this.evictionCount == var5.evictionCount) {
                           var4 = true;
                        }
                     }
                  }
               }
            }
         }
      }

      return var4;
   }

   public long evictionCount() {
      return this.evictionCount;
   }

   public int hashCode() {
      return Objects.hashCode(this.hitCount, this.missCount, this.loadSuccessCount, this.loadExceptionCount, this.totalLoadTime, this.evictionCount);
   }

   public long hitCount() {
      return this.hitCount;
   }

   public double hitRate() {
      long var1 = this.requestCount();
      double var3;
      if (var1 == 0L) {
         var3 = 1.0D;
      } else {
         var3 = (double)this.hitCount / (double)var1;
      }

      return var3;
   }

   public long loadCount() {
      return LongMath.saturatedAdd(this.loadSuccessCount, this.loadExceptionCount);
   }

   public long loadExceptionCount() {
      return this.loadExceptionCount;
   }

   public double loadExceptionRate() {
      long var1 = LongMath.saturatedAdd(this.loadSuccessCount, this.loadExceptionCount);
      double var3;
      if (var1 == 0L) {
         var3 = 0.0D;
      } else {
         var3 = (double)this.loadExceptionCount / (double)var1;
      }

      return var3;
   }

   public long loadSuccessCount() {
      return this.loadSuccessCount;
   }

   public CacheStats minus(CacheStats var1) {
      return new CacheStats(Math.max(0L, LongMath.saturatedSubtract(this.hitCount, var1.hitCount)), Math.max(0L, LongMath.saturatedSubtract(this.missCount, var1.missCount)), Math.max(0L, LongMath.saturatedSubtract(this.loadSuccessCount, var1.loadSuccessCount)), Math.max(0L, LongMath.saturatedSubtract(this.loadExceptionCount, var1.loadExceptionCount)), Math.max(0L, LongMath.saturatedSubtract(this.totalLoadTime, var1.totalLoadTime)), Math.max(0L, LongMath.saturatedSubtract(this.evictionCount, var1.evictionCount)));
   }

   public long missCount() {
      return this.missCount;
   }

   public double missRate() {
      long var1 = this.requestCount();
      double var3;
      if (var1 == 0L) {
         var3 = 0.0D;
      } else {
         var3 = (double)this.missCount / (double)var1;
      }

      return var3;
   }

   public CacheStats plus(CacheStats var1) {
      return new CacheStats(LongMath.saturatedAdd(this.hitCount, var1.hitCount), LongMath.saturatedAdd(this.missCount, var1.missCount), LongMath.saturatedAdd(this.loadSuccessCount, var1.loadSuccessCount), LongMath.saturatedAdd(this.loadExceptionCount, var1.loadExceptionCount), LongMath.saturatedAdd(this.totalLoadTime, var1.totalLoadTime), LongMath.saturatedAdd(this.evictionCount, var1.evictionCount));
   }

   public long requestCount() {
      return LongMath.saturatedAdd(this.hitCount, this.missCount);
   }

   public String toString() {
      return MoreObjects.toStringHelper((Object)this).add("hitCount", this.hitCount).add("missCount", this.missCount).add("loadSuccessCount", this.loadSuccessCount).add("loadExceptionCount", this.loadExceptionCount).add("totalLoadTime", this.totalLoadTime).add("evictionCount", this.evictionCount).toString();
   }

   public long totalLoadTime() {
      return this.totalLoadTime;
   }
}
