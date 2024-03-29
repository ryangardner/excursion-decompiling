package androidx.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class Explode extends Visibility {
   private static final String PROPNAME_SCREEN_BOUNDS = "android:explode:screenBounds";
   private static final TimeInterpolator sAccelerate = new AccelerateInterpolator();
   private static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
   private int[] mTempLoc = new int[2];

   public Explode() {
      this.setPropagation(new CircularPropagation());
   }

   public Explode(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.setPropagation(new CircularPropagation());
   }

   private static float calculateDistance(float var0, float var1) {
      return (float)Math.sqrt((double)(var0 * var0 + var1 * var1));
   }

   private static float calculateMaxDistance(View var0, int var1, int var2) {
      var1 = Math.max(var1, var0.getWidth() - var1);
      var2 = Math.max(var2, var0.getHeight() - var2);
      return calculateDistance((float)var1, (float)var2);
   }

   private void calculateOut(View var1, Rect var2, int[] var3) {
      var1.getLocationOnScreen(this.mTempLoc);
      int[] var4 = this.mTempLoc;
      int var5 = var4[0];
      int var6 = var4[1];
      Rect var15 = this.getEpicenter();
      int var7;
      int var8;
      if (var15 == null) {
         var7 = var1.getWidth() / 2 + var5 + Math.round(var1.getTranslationX());
         var8 = var1.getHeight() / 2 + var6 + Math.round(var1.getTranslationY());
      } else {
         var7 = var15.centerX();
         var8 = var15.centerY();
      }

      int var9 = var2.centerX();
      int var10 = var2.centerY();
      float var11 = (float)(var9 - var7);
      float var12 = (float)(var10 - var8);
      float var13 = var11;
      float var14 = var12;
      if (var11 == 0.0F) {
         var13 = var11;
         var14 = var12;
         if (var12 == 0.0F) {
            var13 = (float)(Math.random() * 2.0D) - 1.0F;
            var14 = (float)(Math.random() * 2.0D) - 1.0F;
         }
      }

      var11 = calculateDistance(var13, var14);
      var13 /= var11;
      var14 /= var11;
      var11 = calculateMaxDistance(var1, var7 - var5, var8 - var6);
      var3[0] = Math.round(var13 * var11);
      var3[1] = Math.round(var11 * var14);
   }

   private void captureValues(TransitionValues var1) {
      View var2 = var1.view;
      var2.getLocationOnScreen(this.mTempLoc);
      int[] var3 = this.mTempLoc;
      int var4 = var3[0];
      int var5 = var3[1];
      int var6 = var2.getWidth();
      int var7 = var2.getHeight();
      var1.values.put("android:explode:screenBounds", new Rect(var4, var5, var6 + var4, var7 + var5));
   }

   public void captureEndValues(TransitionValues var1) {
      super.captureEndValues(var1);
      this.captureValues(var1);
   }

   public void captureStartValues(TransitionValues var1) {
      super.captureStartValues(var1);
      this.captureValues(var1);
   }

   public Animator onAppear(ViewGroup var1, View var2, TransitionValues var3, TransitionValues var4) {
      if (var4 == null) {
         return null;
      } else {
         Rect var10 = (Rect)var4.values.get("android:explode:screenBounds");
         float var5 = var2.getTranslationX();
         float var6 = var2.getTranslationY();
         this.calculateOut(var1, var10, this.mTempLoc);
         int[] var9 = this.mTempLoc;
         float var7 = (float)var9[0];
         float var8 = (float)var9[1];
         return TranslationAnimationCreator.createAnimation(var2, var4, var10.left, var10.top, var5 + var7, var6 + var8, var5, var6, sDecelerate, this);
      }
   }

   public Animator onDisappear(ViewGroup var1, View var2, TransitionValues var3, TransitionValues var4) {
      if (var3 == null) {
         return null;
      } else {
         Rect var5 = (Rect)var3.values.get("android:explode:screenBounds");
         int var6 = var5.left;
         int var7 = var5.top;
         float var8 = var2.getTranslationX();
         float var9 = var2.getTranslationY();
         int[] var13 = (int[])var3.view.getTag(R.id.transition_position);
         float var10;
         float var11;
         if (var13 != null) {
            var10 = (float)(var13[0] - var5.left) + var8;
            var11 = (float)(var13[1] - var5.top) + var9;
            var5.offsetTo(var13[0], var13[1]);
         } else {
            var10 = var8;
            var11 = var9;
         }

         this.calculateOut(var1, var5, this.mTempLoc);
         int[] var12 = this.mTempLoc;
         return TranslationAnimationCreator.createAnimation(var2, var3, var6, var7, var8, var9, var10 + (float)var12[0], var11 + (float)var12[1], sAccelerate, this);
      }
   }
}
