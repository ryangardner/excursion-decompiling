package androidx.transition;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;

public class SidePropagation extends VisibilityPropagation {
   private float mPropagationSpeed = 3.0F;
   private int mSide = 80;

   private int distance(View var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      byte var11;
      int var14;
      label44: {
         label43: {
            int var10 = this.mSide;
            var11 = 0;
            boolean var12 = true;
            boolean var13 = true;
            if (var10 == 8388611) {
               if (ViewCompat.getLayoutDirection(var1) != 1) {
                  var13 = false;
               }

               if (var13) {
                  break label43;
               }
            } else {
               var14 = var10;
               if (var10 != 8388613) {
                  break label44;
               }

               if (ViewCompat.getLayoutDirection(var1) == 1) {
                  var13 = var12;
               } else {
                  var13 = false;
               }

               if (!var13) {
                  break label43;
               }
            }

            var14 = 3;
            break label44;
         }

         var14 = 5;
      }

      if (var14 != 3) {
         if (var14 != 5) {
            if (var14 != 48) {
               if (var14 != 80) {
                  var2 = var11;
               } else {
                  var2 = var3 - var7 + Math.abs(var4 - var2);
               }
            } else {
               var2 = var9 - var3 + Math.abs(var4 - var2);
            }
         } else {
            var2 = var2 - var6 + Math.abs(var5 - var3);
         }
      } else {
         var2 = var8 - var2 + Math.abs(var5 - var3);
      }

      return var2;
   }

   private int getMaxDistance(ViewGroup var1) {
      int var2 = this.mSide;
      return var2 != 3 && var2 != 5 && var2 != 8388611 && var2 != 8388613 ? var1.getHeight() : var1.getWidth();
   }

   public long getStartDelay(ViewGroup var1, Transition var2, TransitionValues var3, TransitionValues var4) {
      if (var3 == null && var4 == null) {
         return 0L;
      } else {
         Rect var5 = var2.getEpicenter();
         byte var6;
         if (var4 != null && this.getViewVisibility(var3) != 0) {
            var3 = var4;
            var6 = 1;
         } else {
            var6 = -1;
         }

         int var7 = this.getViewX(var3);
         int var8 = this.getViewY(var3);
         int[] var20 = new int[2];
         var1.getLocationOnScreen(var20);
         int var9 = var20[0] + Math.round(var1.getTranslationX());
         int var10 = var20[1] + Math.round(var1.getTranslationY());
         int var11 = var9 + var1.getWidth();
         int var12 = var10 + var1.getHeight();
         int var13;
         int var14;
         if (var5 != null) {
            var13 = var5.centerX();
            var14 = var5.centerY();
         } else {
            var13 = (var9 + var11) / 2;
            var14 = (var10 + var12) / 2;
         }

         float var15 = (float)this.distance(var1, var7, var8, var13, var14, var9, var10, var11, var12) / (float)this.getMaxDistance(var1);
         long var16 = var2.getDuration();
         long var18 = var16;
         if (var16 < 0L) {
            var18 = 300L;
         }

         return (long)Math.round((float)(var18 * (long)var6) / this.mPropagationSpeed * var15);
      }
   }

   public void setPropagationSpeed(float var1) {
      if (var1 != 0.0F) {
         this.mPropagationSpeed = var1;
      } else {
         throw new IllegalArgumentException("propagationSpeed may not be 0");
      }
   }

   public void setSide(int var1) {
      this.mSide = var1;
   }
}
