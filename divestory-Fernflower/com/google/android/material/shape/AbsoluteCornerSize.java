package com.google.android.material.shape;

import android.graphics.RectF;
import java.util.Arrays;

public final class AbsoluteCornerSize implements CornerSize {
   private final float size;

   public AbsoluteCornerSize(float var1) {
      this.size = var1;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof AbsoluteCornerSize)) {
         return false;
      } else {
         AbsoluteCornerSize var3 = (AbsoluteCornerSize)var1;
         if (this.size != var3.size) {
            var2 = false;
         }

         return var2;
      }
   }

   public float getCornerSize() {
      return this.size;
   }

   public float getCornerSize(RectF var1) {
      return this.size;
   }

   public int hashCode() {
      return Arrays.hashCode(new Object[]{this.size});
   }
}
