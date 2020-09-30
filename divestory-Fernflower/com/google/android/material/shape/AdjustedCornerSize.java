package com.google.android.material.shape;

import android.graphics.RectF;
import java.util.Arrays;

public final class AdjustedCornerSize implements CornerSize {
   private final float adjustment;
   private final CornerSize other;

   public AdjustedCornerSize(float var1, CornerSize var2) {
      while(var2 instanceof AdjustedCornerSize) {
         var2 = ((AdjustedCornerSize)var2).other;
         var1 += ((AdjustedCornerSize)var2).adjustment;
      }

      this.other = var2;
      this.adjustment = var1;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof AdjustedCornerSize)) {
         return false;
      } else {
         AdjustedCornerSize var3 = (AdjustedCornerSize)var1;
         if (!this.other.equals(var3.other) || this.adjustment != var3.adjustment) {
            var2 = false;
         }

         return var2;
      }
   }

   public float getCornerSize(RectF var1) {
      return Math.max(0.0F, this.other.getCornerSize(var1) + this.adjustment);
   }

   public int hashCode() {
      return Arrays.hashCode(new Object[]{this.other, this.adjustment});
   }
}
