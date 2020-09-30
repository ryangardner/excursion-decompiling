package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;

public abstract class LinearTransformation {
   public static LinearTransformation forNaN() {
      return LinearTransformation.NaNLinearTransformation.INSTANCE;
   }

   public static LinearTransformation horizontal(double var0) {
      Preconditions.checkArgument(DoubleUtils.isFinite(var0));
      return new LinearTransformation.RegularLinearTransformation(0.0D, var0);
   }

   public static LinearTransformation.LinearTransformationBuilder mapping(double var0, double var2) {
      boolean var4;
      if (DoubleUtils.isFinite(var0) && DoubleUtils.isFinite(var2)) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      return new LinearTransformation.LinearTransformationBuilder(var0, var2);
   }

   public static LinearTransformation vertical(double var0) {
      Preconditions.checkArgument(DoubleUtils.isFinite(var0));
      return new LinearTransformation.VerticalLinearTransformation(var0);
   }

   public abstract LinearTransformation inverse();

   public abstract boolean isHorizontal();

   public abstract boolean isVertical();

   public abstract double slope();

   public abstract double transform(double var1);

   public static final class LinearTransformationBuilder {
      private final double x1;
      private final double y1;

      private LinearTransformationBuilder(double var1, double var3) {
         this.x1 = var1;
         this.y1 = var3;
      }

      // $FF: synthetic method
      LinearTransformationBuilder(double var1, double var3, Object var5) {
         this(var1, var3);
      }

      public LinearTransformation and(double var1, double var3) {
         boolean var5 = DoubleUtils.isFinite(var1);
         boolean var6 = true;
         if (var5 && DoubleUtils.isFinite(var3)) {
            var5 = true;
         } else {
            var5 = false;
         }

         Preconditions.checkArgument(var5);
         double var7 = this.x1;
         if (var1 == var7) {
            if (var3 != this.y1) {
               var5 = var6;
            } else {
               var5 = false;
            }

            Preconditions.checkArgument(var5);
            return new LinearTransformation.VerticalLinearTransformation(this.x1);
         } else {
            return this.withSlope((var3 - this.y1) / (var1 - var7));
         }
      }

      public LinearTransformation withSlope(double var1) {
         Preconditions.checkArgument(Double.isNaN(var1) ^ true);
         return (LinearTransformation)(DoubleUtils.isFinite(var1) ? new LinearTransformation.RegularLinearTransformation(var1, this.y1 - this.x1 * var1) : new LinearTransformation.VerticalLinearTransformation(this.x1));
      }
   }

   private static final class NaNLinearTransformation extends LinearTransformation {
      static final LinearTransformation.NaNLinearTransformation INSTANCE = new LinearTransformation.NaNLinearTransformation();

      public LinearTransformation inverse() {
         return this;
      }

      public boolean isHorizontal() {
         return false;
      }

      public boolean isVertical() {
         return false;
      }

      public double slope() {
         return Double.NaN;
      }

      public String toString() {
         return "NaN";
      }

      public double transform(double var1) {
         return Double.NaN;
      }
   }

   private static final class RegularLinearTransformation extends LinearTransformation {
      @LazyInit
      LinearTransformation inverse;
      final double slope;
      final double yIntercept;

      RegularLinearTransformation(double var1, double var3) {
         this.slope = var1;
         this.yIntercept = var3;
         this.inverse = null;
      }

      RegularLinearTransformation(double var1, double var3, LinearTransformation var5) {
         this.slope = var1;
         this.yIntercept = var3;
         this.inverse = var5;
      }

      private LinearTransformation createInverse() {
         if (this.slope != 0.0D) {
            double var1 = this.slope;
            return new LinearTransformation.RegularLinearTransformation(1.0D / var1, this.yIntercept * -1.0D / var1, this);
         } else {
            return new LinearTransformation.VerticalLinearTransformation(this.yIntercept, this);
         }
      }

      public LinearTransformation inverse() {
         LinearTransformation var1 = this.inverse;
         LinearTransformation var2 = var1;
         if (var1 == null) {
            var2 = this.createInverse();
            this.inverse = var2;
         }

         return var2;
      }

      public boolean isHorizontal() {
         boolean var1;
         if (this.slope == 0.0D) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public boolean isVertical() {
         return false;
      }

      public double slope() {
         return this.slope;
      }

      public String toString() {
         return String.format("y = %g * x + %g", this.slope, this.yIntercept);
      }

      public double transform(double var1) {
         return var1 * this.slope + this.yIntercept;
      }
   }

   private static final class VerticalLinearTransformation extends LinearTransformation {
      @LazyInit
      LinearTransformation inverse;
      final double x;

      VerticalLinearTransformation(double var1) {
         this.x = var1;
         this.inverse = null;
      }

      VerticalLinearTransformation(double var1, LinearTransformation var3) {
         this.x = var1;
         this.inverse = var3;
      }

      private LinearTransformation createInverse() {
         return new LinearTransformation.RegularLinearTransformation(0.0D, this.x, this);
      }

      public LinearTransformation inverse() {
         LinearTransformation var1 = this.inverse;
         LinearTransformation var2 = var1;
         if (var1 == null) {
            var2 = this.createInverse();
            this.inverse = var2;
         }

         return var2;
      }

      public boolean isHorizontal() {
         return false;
      }

      public boolean isVertical() {
         return true;
      }

      public double slope() {
         throw new IllegalStateException();
      }

      public String toString() {
         return String.format("x = %g", this.x);
      }

      public double transform(double var1) {
         throw new IllegalStateException();
      }
   }
}
