package androidx.constraintlayout.motion.utils;

import androidx.constraintlayout.motion.widget.KeyCycleOscillator;
import androidx.constraintlayout.motion.widget.SplineSet;

public class VelocityMatrix {
   private static String TAG;
   float mDRotate;
   float mDScaleX;
   float mDScaleY;
   float mDTranslateX;
   float mDTranslateY;
   float mRotate;

   public void applyTransform(float var1, float var2, int var3, int var4, float[] var5) {
      float var6 = var5[0];
      float var7 = var5[1];
      var1 = (var1 - 0.5F) * 2.0F;
      float var8 = (var2 - 0.5F) * 2.0F;
      float var9 = this.mDTranslateX;
      float var10 = this.mDTranslateY;
      float var11 = this.mDScaleX;
      var2 = this.mDScaleY;
      float var12 = (float)Math.toRadians((double)this.mRotate);
      float var13 = (float)Math.toRadians((double)this.mDRotate);
      double var14 = (double)((float)(-var3) * var1);
      double var16 = (double)var12;
      double var18 = Math.sin(var16);
      double var20 = (double)((float)var4 * var8);
      float var22 = (float)(var14 * var18 - Math.cos(var16) * var20);
      var12 = (float)((double)((float)var3 * var1) * Math.cos(var16) - var20 * Math.sin(var16));
      var5[0] = var6 + var9 + var11 * var1 + var22 * var13;
      var5[1] = var7 + var10 + var2 * var8 + var13 * var12;
   }

   public void clear() {
      this.mDRotate = 0.0F;
      this.mDTranslateY = 0.0F;
      this.mDTranslateX = 0.0F;
      this.mDScaleY = 0.0F;
      this.mDScaleX = 0.0F;
   }

   public void setRotationVelocity(KeyCycleOscillator var1, float var2) {
      if (var1 != null) {
         this.mDRotate = var1.getSlope(var2);
      }

   }

   public void setRotationVelocity(SplineSet var1, float var2) {
      if (var1 != null) {
         this.mDRotate = var1.getSlope(var2);
         this.mRotate = var1.get(var2);
      }

   }

   public void setScaleVelocity(KeyCycleOscillator var1, KeyCycleOscillator var2, float var3) {
      if (var1 != null || var2 != null) {
         if (var1 == null) {
            this.mDScaleX = var1.getSlope(var3);
         }

         if (var2 == null) {
            this.mDScaleY = var2.getSlope(var3);
         }

      }
   }

   public void setScaleVelocity(SplineSet var1, SplineSet var2, float var3) {
      if (var1 != null) {
         this.mDScaleX = var1.getSlope(var3);
      }

      if (var2 != null) {
         this.mDScaleY = var2.getSlope(var3);
      }

   }

   public void setTranslationVelocity(KeyCycleOscillator var1, KeyCycleOscillator var2, float var3) {
      if (var1 != null) {
         this.mDTranslateX = var1.getSlope(var3);
      }

      if (var2 != null) {
         this.mDTranslateY = var2.getSlope(var3);
      }

   }

   public void setTranslationVelocity(SplineSet var1, SplineSet var2, float var3) {
      if (var1 != null) {
         this.mDTranslateX = var1.getSlope(var3);
      }

      if (var2 != null) {
         this.mDTranslateY = var2.getSlope(var3);
      }

   }
}
