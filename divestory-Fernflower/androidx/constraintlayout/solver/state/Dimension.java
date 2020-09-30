package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;

public class Dimension {
   public static final Object FIXED_DIMENSION = new Object();
   public static final Object PARENT_DIMENSION = new Object();
   public static final Object PERCENT_DIMENSION = new Object();
   public static final Object SPREAD_DIMENSION = new Object();
   public static final Object WRAP_DIMENSION = new Object();
   private final int WRAP_CONTENT = -2;
   Object mInitialValue;
   boolean mIsSuggested;
   int mMax = Integer.MAX_VALUE;
   int mMin = 0;
   float mPercent = 1.0F;
   float mRatio = 1.0F;
   int mValue = 0;

   private Dimension() {
      this.mInitialValue = WRAP_DIMENSION;
      this.mIsSuggested = false;
   }

   private Dimension(Object var1) {
      this.mInitialValue = WRAP_DIMENSION;
      this.mIsSuggested = false;
      this.mInitialValue = var1;
   }

   public static Dimension Fixed(int var0) {
      Dimension var1 = new Dimension(FIXED_DIMENSION);
      var1.fixed(var0);
      return var1;
   }

   public static Dimension Fixed(Object var0) {
      Dimension var1 = new Dimension(FIXED_DIMENSION);
      var1.fixed(var0);
      return var1;
   }

   public static Dimension Parent() {
      return new Dimension(PARENT_DIMENSION);
   }

   public static Dimension Percent(Object var0, float var1) {
      Dimension var2 = new Dimension(PERCENT_DIMENSION);
      var2.percent(var0, var1);
      return var2;
   }

   public static Dimension Spread() {
      return new Dimension(SPREAD_DIMENSION);
   }

   public static Dimension Suggested(int var0) {
      Dimension var1 = new Dimension();
      var1.suggested(var0);
      return var1;
   }

   public static Dimension Suggested(Object var0) {
      Dimension var1 = new Dimension();
      var1.suggested(var0);
      return var1;
   }

   public static Dimension Wrap() {
      return new Dimension(WRAP_DIMENSION);
   }

   public void apply(State var1, ConstraintWidget var2, int var3) {
      byte var4 = 2;
      Object var5;
      byte var6;
      if (var3 == 0) {
         if (this.mIsSuggested) {
            var2.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
            var5 = this.mInitialValue;
            if (var5 == WRAP_DIMENSION) {
               var6 = 1;
            } else if (var5 == PERCENT_DIMENSION) {
               var6 = var4;
            } else {
               var6 = 0;
            }

            var2.setHorizontalMatchStyle(var6, this.mMin, this.mMax, this.mPercent);
         } else {
            var3 = this.mMin;
            if (var3 > 0) {
               var2.setMinWidth(var3);
            }

            var3 = this.mMax;
            if (var3 < Integer.MAX_VALUE) {
               var2.setMaxWidth(var3);
            }

            var5 = this.mInitialValue;
            if (var5 == WRAP_DIMENSION) {
               var2.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            } else if (var5 == PARENT_DIMENSION) {
               var2.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
            } else if (var5 == null) {
               var2.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
               var2.setWidth(this.mValue);
            }
         }
      } else if (this.mIsSuggested) {
         var2.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
         var5 = this.mInitialValue;
         if (var5 == WRAP_DIMENSION) {
            var6 = 1;
         } else if (var5 == PERCENT_DIMENSION) {
            var6 = var4;
         } else {
            var6 = 0;
         }

         var2.setVerticalMatchStyle(var6, this.mMin, this.mMax, this.mPercent);
      } else {
         var3 = this.mMin;
         if (var3 > 0) {
            var2.setMinHeight(var3);
         }

         var3 = this.mMax;
         if (var3 < Integer.MAX_VALUE) {
            var2.setMaxHeight(var3);
         }

         var5 = this.mInitialValue;
         if (var5 == WRAP_DIMENSION) {
            var2.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
         } else if (var5 == PARENT_DIMENSION) {
            var2.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
         } else if (var5 == null) {
            var2.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            var2.setHeight(this.mValue);
         }
      }

   }

   public Dimension fixed(int var1) {
      this.mInitialValue = null;
      this.mValue = var1;
      return this;
   }

   public Dimension fixed(Object var1) {
      this.mInitialValue = var1;
      if (var1 instanceof Integer) {
         this.mValue = (Integer)var1;
         this.mInitialValue = null;
      }

      return this;
   }

   float getRatio() {
      return this.mRatio;
   }

   int getValue() {
      return this.mValue;
   }

   public Dimension max(int var1) {
      if (this.mMax >= 0) {
         this.mMax = var1;
      }

      return this;
   }

   public Dimension max(Object var1) {
      Object var2 = WRAP_DIMENSION;
      if (var1 == var2 && this.mIsSuggested) {
         this.mInitialValue = var2;
         this.mMax = Integer.MAX_VALUE;
      }

      return this;
   }

   public Dimension min(int var1) {
      if (var1 >= 0) {
         this.mMin = var1;
      }

      return this;
   }

   public Dimension min(Object var1) {
      if (var1 == WRAP_DIMENSION) {
         this.mMin = -2;
      }

      return this;
   }

   public Dimension percent(Object var1, float var2) {
      this.mPercent = var2;
      return this;
   }

   public Dimension ratio(float var1) {
      return this;
   }

   void setRatio(float var1) {
      this.mRatio = var1;
   }

   void setValue(int var1) {
      this.mIsSuggested = false;
      this.mInitialValue = null;
      this.mValue = var1;
   }

   public Dimension suggested(int var1) {
      this.mIsSuggested = true;
      return this;
   }

   public Dimension suggested(Object var1) {
      this.mInitialValue = var1;
      this.mIsSuggested = true;
      return this;
   }

   public static enum Type {
      FIXED,
      MATCH_CONSTRAINT,
      MATCH_PARENT,
      WRAP;

      static {
         Dimension.Type var0 = new Dimension.Type("MATCH_CONSTRAINT", 3);
         MATCH_CONSTRAINT = var0;
      }
   }
}
