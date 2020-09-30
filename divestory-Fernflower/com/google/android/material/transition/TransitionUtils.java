package com.google.android.material.transition;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewParent;
import androidx.transition.Transition;
import androidx.transition.TransitionSet;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;

class TransitionUtils {
   private static final RectF transformAlphaRectF = new RectF();

   private TransitionUtils() {
   }

   static float calculateArea(RectF var0) {
      return var0.width() * var0.height();
   }

   static ShapeAppearanceModel convertToRelativeCornerSizes(ShapeAppearanceModel var0, final RectF var1) {
      return var0.withTransformedCornerSizes(new ShapeAppearanceModel.CornerSizeUnaryOperator() {
         public CornerSize apply(CornerSize var1x) {
            if (!(var1x instanceof RelativeCornerSize)) {
               var1x = new RelativeCornerSize(((CornerSize)var1x).getCornerSize(var1) / var1.height());
            }

            return (CornerSize)var1x;
         }
      });
   }

   static Shader createColorShader(int var0) {
      return new LinearGradient(0.0F, 0.0F, 0.0F, 0.0F, var0, var0, TileMode.CLAMP);
   }

   static <T> T defaultIfNull(T var0, T var1) {
      if (var0 == null) {
         var0 = var1;
      }

      return var0;
   }

   static View findAncestorById(View var0, int var1) {
      String var2;
      ViewParent var3;
      for(var2 = var0.getResources().getResourceName(var1); var0 != null; var0 = (View)var3) {
         if (var0.getId() == var1) {
            return var0;
         }

         var3 = var0.getParent();
         if (!(var3 instanceof View)) {
            break;
         }
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(var2);
      var4.append(" is not a valid ancestor");
      throw new IllegalArgumentException(var4.toString());
   }

   static View findDescendantOrAncestorById(View var0, int var1) {
      View var2 = var0.findViewById(var1);
      return var2 != null ? var2 : findAncestorById(var0, var1);
   }

   static RectF getLocationOnScreen(View var0) {
      int[] var1 = new int[2];
      var0.getLocationOnScreen(var1);
      int var2 = var1[0];
      int var3 = var1[1];
      int var4 = var0.getWidth();
      int var5 = var0.getHeight();
      return new RectF((float)var2, (float)var3, (float)(var4 + var2), (float)(var5 + var3));
   }

   static RectF getRelativeBounds(View var0) {
      return new RectF((float)var0.getLeft(), (float)var0.getTop(), (float)var0.getRight(), (float)var0.getBottom());
   }

   static Rect getRelativeBoundsRect(View var0) {
      return new Rect(var0.getLeft(), var0.getTop(), var0.getRight(), var0.getBottom());
   }

   private static boolean isShapeAppearanceSignificant(ShapeAppearanceModel var0, RectF var1) {
      boolean var2;
      if (var0.getTopLeftCornerSize().getCornerSize(var1) == 0.0F && var0.getTopRightCornerSize().getCornerSize(var1) == 0.0F && var0.getBottomRightCornerSize().getCornerSize(var1) == 0.0F && var0.getBottomLeftCornerSize().getCornerSize(var1) == 0.0F) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   static float lerp(float var0, float var1, float var2) {
      return var0 + var2 * (var1 - var0);
   }

   static float lerp(float var0, float var1, float var2, float var3, float var4) {
      if (var4 < var2) {
         return var0;
      } else {
         return var4 > var3 ? var1 : lerp(var0, var1, (var4 - var2) / (var3 - var2));
      }
   }

   static int lerp(int var0, int var1, float var2, float var3, float var4) {
      if (var4 < var2) {
         return var0;
      } else {
         return var4 > var3 ? var1 : (int)lerp((float)var0, (float)var1, (var4 - var2) / (var3 - var2));
      }
   }

   static ShapeAppearanceModel lerp(ShapeAppearanceModel var0, ShapeAppearanceModel var1, final RectF var2, final RectF var3, final float var4, final float var5, final float var6) {
      if (var6 < var4) {
         return var0;
      } else {
         return var6 > var5 ? var1 : transformCornerSizes(var0, var1, var2, new TransitionUtils.CornerSizeBinaryOperator() {
            public CornerSize apply(CornerSize var1, CornerSize var2x) {
               return new AbsoluteCornerSize(TransitionUtils.lerp(var1.getCornerSize(var2), var2x.getCornerSize(var3), var4, var5, var6));
            }
         });
      }
   }

   static void maybeAddTransition(TransitionSet var0, Transition var1) {
      if (var1 != null) {
         var0.addTransition(var1);
      }

   }

   static void maybeRemoveTransition(TransitionSet var0, Transition var1) {
      if (var1 != null) {
         var0.removeTransition(var1);
      }

   }

   private static int saveLayerAlphaCompat(Canvas var0, Rect var1, int var2) {
      transformAlphaRectF.set(var1);
      return VERSION.SDK_INT >= 21 ? var0.saveLayerAlpha(transformAlphaRectF, var2) : var0.saveLayerAlpha(transformAlphaRectF.left, transformAlphaRectF.top, transformAlphaRectF.right, transformAlphaRectF.bottom, var2, 31);
   }

   static void transform(Canvas var0, Rect var1, float var2, float var3, float var4, int var5, TransitionUtils.CanvasOperation var6) {
      if (var5 > 0) {
         int var7 = var0.save();
         var0.translate(var2, var3);
         var0.scale(var4, var4);
         if (var5 < 255) {
            saveLayerAlphaCompat(var0, var1, var5);
         }

         var6.run(var0);
         var0.restoreToCount(var7);
      }
   }

   static ShapeAppearanceModel transformCornerSizes(ShapeAppearanceModel var0, ShapeAppearanceModel var1, RectF var2, TransitionUtils.CornerSizeBinaryOperator var3) {
      ShapeAppearanceModel var4;
      if (isShapeAppearanceSignificant(var0, var2)) {
         var4 = var0;
      } else {
         var4 = var1;
      }

      return var4.toBuilder().setTopLeftCornerSize(var3.apply(var0.getTopLeftCornerSize(), var1.getTopLeftCornerSize())).setTopRightCornerSize(var3.apply(var0.getTopRightCornerSize(), var1.getTopRightCornerSize())).setBottomLeftCornerSize(var3.apply(var0.getBottomLeftCornerSize(), var1.getBottomLeftCornerSize())).setBottomRightCornerSize(var3.apply(var0.getBottomRightCornerSize(), var1.getBottomRightCornerSize())).build();
   }

   interface CanvasOperation {
      void run(Canvas var1);
   }

   interface CornerSizeBinaryOperator {
      CornerSize apply(CornerSize var1, CornerSize var2);
   }
}
