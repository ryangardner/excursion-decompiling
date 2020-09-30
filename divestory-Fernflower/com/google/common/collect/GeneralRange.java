package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Comparator;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class GeneralRange<T> implements Serializable {
   private final Comparator<? super T> comparator;
   private final boolean hasLowerBound;
   private final boolean hasUpperBound;
   private final BoundType lowerBoundType;
   @NullableDecl
   private final T lowerEndpoint;
   @MonotonicNonNullDecl
   private transient GeneralRange<T> reverse;
   private final BoundType upperBoundType;
   @NullableDecl
   private final T upperEndpoint;

   private GeneralRange(Comparator<? super T> var1, boolean var2, @NullableDecl T var3, BoundType var4, boolean var5, @NullableDecl T var6, BoundType var7) {
      this.comparator = (Comparator)Preconditions.checkNotNull(var1);
      this.hasLowerBound = var2;
      this.hasUpperBound = var5;
      this.lowerEndpoint = var3;
      this.lowerBoundType = (BoundType)Preconditions.checkNotNull(var4);
      this.upperEndpoint = var6;
      this.upperBoundType = (BoundType)Preconditions.checkNotNull(var7);
      if (var2) {
         var1.compare(var3, var3);
      }

      if (var5) {
         var1.compare(var6, var6);
      }

      if (var2 && var5) {
         int var8 = var1.compare(var3, var6);
         boolean var9 = true;
         if (var8 <= 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkArgument(var2, "lowerEndpoint (%s) > upperEndpoint (%s)", var3, var6);
         if (var8 == 0) {
            boolean var10;
            if (var4 != BoundType.OPEN) {
               var10 = true;
            } else {
               var10 = false;
            }

            if (var7 == BoundType.OPEN) {
               var9 = false;
            }

            Preconditions.checkArgument(var10 | var9);
         }
      }

   }

   static <T> GeneralRange<T> all(Comparator<? super T> var0) {
      return new GeneralRange(var0, false, (Object)null, BoundType.OPEN, false, (Object)null, BoundType.OPEN);
   }

   static <T> GeneralRange<T> downTo(Comparator<? super T> var0, @NullableDecl T var1, BoundType var2) {
      return new GeneralRange(var0, true, var1, var2, false, (Object)null, BoundType.OPEN);
   }

   static <T extends Comparable> GeneralRange<T> from(Range<T> var0) {
      boolean var1 = var0.hasLowerBound();
      Comparable var2 = null;
      Comparable var3;
      if (var1) {
         var3 = var0.lowerEndpoint();
      } else {
         var3 = null;
      }

      BoundType var4;
      if (var0.hasLowerBound()) {
         var4 = var0.lowerBoundType();
      } else {
         var4 = BoundType.OPEN;
      }

      if (var0.hasUpperBound()) {
         var2 = var0.upperEndpoint();
      }

      BoundType var5;
      if (var0.hasUpperBound()) {
         var5 = var0.upperBoundType();
      } else {
         var5 = BoundType.OPEN;
      }

      return new GeneralRange(Ordering.natural(), var0.hasLowerBound(), var3, var4, var0.hasUpperBound(), var2, var5);
   }

   static <T> GeneralRange<T> range(Comparator<? super T> var0, @NullableDecl T var1, BoundType var2, @NullableDecl T var3, BoundType var4) {
      return new GeneralRange(var0, true, var1, var2, true, var3, var4);
   }

   static <T> GeneralRange<T> upTo(Comparator<? super T> var0, @NullableDecl T var1, BoundType var2) {
      return new GeneralRange(var0, false, (Object)null, BoundType.OPEN, true, var1, var2);
   }

   Comparator<? super T> comparator() {
      return this.comparator;
   }

   boolean contains(@NullableDecl T var1) {
      boolean var2;
      if (!this.tooLow(var1) && !this.tooHigh(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof GeneralRange;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         GeneralRange var5 = (GeneralRange)var1;
         var4 = var3;
         if (this.comparator.equals(var5.comparator)) {
            var4 = var3;
            if (this.hasLowerBound == var5.hasLowerBound) {
               var4 = var3;
               if (this.hasUpperBound == var5.hasUpperBound) {
                  var4 = var3;
                  if (this.getLowerBoundType().equals(var5.getLowerBoundType())) {
                     var4 = var3;
                     if (this.getUpperBoundType().equals(var5.getUpperBoundType())) {
                        var4 = var3;
                        if (Objects.equal(this.getLowerEndpoint(), var5.getLowerEndpoint())) {
                           var4 = var3;
                           if (Objects.equal(this.getUpperEndpoint(), var5.getUpperEndpoint())) {
                              var4 = true;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return var4;
   }

   BoundType getLowerBoundType() {
      return this.lowerBoundType;
   }

   T getLowerEndpoint() {
      return this.lowerEndpoint;
   }

   BoundType getUpperBoundType() {
      return this.upperBoundType;
   }

   T getUpperEndpoint() {
      return this.upperEndpoint;
   }

   boolean hasLowerBound() {
      return this.hasLowerBound;
   }

   boolean hasUpperBound() {
      return this.hasUpperBound;
   }

   public int hashCode() {
      return Objects.hashCode(this.comparator, this.getLowerEndpoint(), this.getLowerBoundType(), this.getUpperEndpoint(), this.getUpperBoundType());
   }

   GeneralRange<T> intersect(GeneralRange<T> var1) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkArgument(this.comparator.equals(var1.comparator));
      boolean var2 = this.hasLowerBound;
      Object var3 = this.getLowerEndpoint();
      BoundType var4 = this.getLowerBoundType();
      boolean var5;
      Object var6;
      BoundType var7;
      int var8;
      if (!this.hasLowerBound()) {
         var5 = var1.hasLowerBound;
         var6 = var1.getLowerEndpoint();
         var7 = var1.getLowerBoundType();
      } else {
         var5 = var2;
         var6 = var3;
         var7 = var4;
         if (var1.hasLowerBound()) {
            label53: {
               var8 = this.comparator.compare(this.getLowerEndpoint(), var1.getLowerEndpoint());
               if (var8 >= 0) {
                  var5 = var2;
                  var6 = var3;
                  var7 = var4;
                  if (var8 != 0) {
                     break label53;
                  }

                  var5 = var2;
                  var6 = var3;
                  var7 = var4;
                  if (var1.getLowerBoundType() != BoundType.OPEN) {
                     break label53;
                  }
               }

               var6 = var1.getLowerEndpoint();
               var7 = var1.getLowerBoundType();
               var5 = var2;
            }
         }
      }

      boolean var9 = this.hasUpperBound;
      Object var10 = this.getUpperEndpoint();
      BoundType var11 = this.getUpperBoundType();
      if (!this.hasUpperBound()) {
         var2 = var1.hasUpperBound;
         var3 = var1.getUpperEndpoint();
         var4 = var1.getUpperBoundType();
      } else {
         var2 = var9;
         var3 = var10;
         var4 = var11;
         if (var1.hasUpperBound()) {
            label44: {
               var8 = this.comparator.compare(this.getUpperEndpoint(), var1.getUpperEndpoint());
               if (var8 <= 0) {
                  var2 = var9;
                  var3 = var10;
                  var4 = var11;
                  if (var8 != 0) {
                     break label44;
                  }

                  var2 = var9;
                  var3 = var10;
                  var4 = var11;
                  if (var1.getUpperBoundType() != BoundType.OPEN) {
                     break label44;
                  }
               }

               var3 = var1.getUpperEndpoint();
               var4 = var1.getUpperBoundType();
               var2 = var9;
            }
         }
      }

      BoundType var12;
      if (var5 && var2) {
         var8 = this.comparator.compare(var6, var3);
         if (var8 > 0 || var8 == 0 && var7 == BoundType.OPEN && var4 == BoundType.OPEN) {
            var7 = BoundType.OPEN;
            var12 = BoundType.CLOSED;
            var6 = var3;
            return new GeneralRange(this.comparator, var5, var6, var7, var2, var3, var12);
         }
      }

      var12 = var4;
      return new GeneralRange(this.comparator, var5, var6, var7, var2, var3, var12);
   }

   boolean isEmpty() {
      boolean var1;
      if ((!this.hasUpperBound() || !this.tooLow(this.getUpperEndpoint())) && (!this.hasLowerBound() || !this.tooHigh(this.getLowerEndpoint()))) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   GeneralRange<T> reverse() {
      GeneralRange var1 = this.reverse;
      GeneralRange var2 = var1;
      if (var1 == null) {
         var2 = new GeneralRange(Ordering.from(this.comparator).reverse(), this.hasUpperBound, this.getUpperEndpoint(), this.getUpperBoundType(), this.hasLowerBound, this.getLowerEndpoint(), this.getLowerBoundType());
         var2.reverse = this;
         this.reverse = var2;
      }

      return var2;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.comparator);
      var1.append(":");
      byte var2;
      char var3;
      if (this.lowerBoundType == BoundType.CLOSED) {
         var2 = 91;
         var3 = (char)var2;
      } else {
         var2 = 40;
         var3 = (char)var2;
      }

      var1.append(var3);
      Object var4;
      if (this.hasLowerBound) {
         var4 = this.lowerEndpoint;
      } else {
         var4 = "-∞";
      }

      var1.append(var4);
      var1.append(',');
      if (this.hasUpperBound) {
         var4 = this.upperEndpoint;
      } else {
         var4 = "∞";
      }

      var1.append(var4);
      if (this.upperBoundType == BoundType.CLOSED) {
         var2 = 93;
         var3 = (char)var2;
      } else {
         var2 = 41;
         var3 = (char)var2;
      }

      var1.append(var3);
      return var1.toString();
   }

   boolean tooHigh(@NullableDecl T var1) {
      boolean var2 = this.hasUpperBound();
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         Object var4 = this.getUpperEndpoint();
         int var5 = this.comparator.compare(var1, var4);
         boolean var6;
         if (var5 > 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         boolean var7;
         if (var5 == 0) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (this.getUpperBoundType() == BoundType.OPEN) {
            var3 = true;
         }

         return var7 & var3 | var6;
      }
   }

   boolean tooLow(@NullableDecl T var1) {
      boolean var2 = this.hasLowerBound();
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         Object var4 = this.getLowerEndpoint();
         int var5 = this.comparator.compare(var1, var4);
         boolean var6;
         if (var5 < 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         boolean var7;
         if (var5 == 0) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (this.getLowerBoundType() == BoundType.OPEN) {
            var3 = true;
         }

         return var7 & var3 | var6;
      }
   }
}
