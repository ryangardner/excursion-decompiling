package com.google.android.material.shape;

import android.graphics.RectF;
import java.util.Arrays;

public final class RelativeCornerSize implements CornerSize {
   private final float percent;

   public RelativeCornerSize(float var1) {
      this.percent = var1;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof RelativeCornerSize)) {
         return false;
      } else {
         RelativeCornerSize var3 = (RelativeCornerSize)var1;
         if (this.percent != var3.percent) {
            var2 = false;
         }

         return var2;
      }
   }

   public float getCornerSize(RectF var1) {
      return this.percent * var1.height();
   }

   public float getRelativePercent() {
      return this.percent;
   }

   public int hashCode() {
      return Arrays.hashCode(new Object[]{this.percent});
   }
}
