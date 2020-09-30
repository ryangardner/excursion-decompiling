package androidx.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.Map;

public class ChangeImageTransform extends Transition {
   private static final Property<ImageView, Matrix> ANIMATED_TRANSFORM_PROPERTY = new Property<ImageView, Matrix>(Matrix.class, "animatedTransform") {
      public Matrix get(ImageView var1) {
         return null;
      }

      public void set(ImageView var1, Matrix var2) {
         ImageViewUtils.animateTransform(var1, var2);
      }
   };
   private static final TypeEvaluator<Matrix> NULL_MATRIX_EVALUATOR = new TypeEvaluator<Matrix>() {
      public Matrix evaluate(float var1, Matrix var2, Matrix var3) {
         return null;
      }
   };
   private static final String PROPNAME_BOUNDS = "android:changeImageTransform:bounds";
   private static final String PROPNAME_MATRIX = "android:changeImageTransform:matrix";
   private static final String[] sTransitionProperties = new String[]{"android:changeImageTransform:matrix", "android:changeImageTransform:bounds"};

   public ChangeImageTransform() {
   }

   public ChangeImageTransform(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   private void captureValues(TransitionValues var1) {
      View var2 = var1.view;
      if (var2 instanceof ImageView && var2.getVisibility() == 0) {
         ImageView var3 = (ImageView)var2;
         if (var3.getDrawable() == null) {
            return;
         }

         Map var4 = var1.values;
         var4.put("android:changeImageTransform:bounds", new Rect(var2.getLeft(), var2.getTop(), var2.getRight(), var2.getBottom()));
         var4.put("android:changeImageTransform:matrix", copyImageMatrix(var3));
      }

   }

   private static Matrix centerCropMatrix(ImageView var0) {
      Drawable var1 = var0.getDrawable();
      int var2 = var1.getIntrinsicWidth();
      float var3 = (float)var0.getWidth();
      float var4 = (float)var2;
      float var5 = var3 / var4;
      var2 = var1.getIntrinsicHeight();
      float var6 = (float)var0.getHeight();
      float var7 = (float)var2;
      var5 = Math.max(var5, var6 / var7);
      var2 = Math.round((var3 - var4 * var5) / 2.0F);
      int var8 = Math.round((var6 - var7 * var5) / 2.0F);
      Matrix var9 = new Matrix();
      var9.postScale(var5, var5);
      var9.postTranslate((float)var2, (float)var8);
      return var9;
   }

   private static Matrix copyImageMatrix(ImageView var0) {
      Drawable var1 = var0.getDrawable();
      if (var1.getIntrinsicWidth() > 0 && var1.getIntrinsicHeight() > 0) {
         int var2 = null.$SwitchMap$android$widget$ImageView$ScaleType[var0.getScaleType().ordinal()];
         if (var2 == 1) {
            return fitXYMatrix(var0);
         }

         if (var2 == 2) {
            return centerCropMatrix(var0);
         }
      }

      return new Matrix(var0.getImageMatrix());
   }

   private ObjectAnimator createMatrixAnimator(ImageView var1, Matrix var2, Matrix var3) {
      return ObjectAnimator.ofObject(var1, ANIMATED_TRANSFORM_PROPERTY, new TransitionUtils.MatrixEvaluator(), new Matrix[]{var2, var3});
   }

   private ObjectAnimator createNullAnimator(ImageView var1) {
      return ObjectAnimator.ofObject(var1, ANIMATED_TRANSFORM_PROPERTY, NULL_MATRIX_EVALUATOR, new Matrix[]{MatrixUtils.IDENTITY_MATRIX, MatrixUtils.IDENTITY_MATRIX});
   }

   private static Matrix fitXYMatrix(ImageView var0) {
      Drawable var1 = var0.getDrawable();
      Matrix var2 = new Matrix();
      var2.postScale((float)var0.getWidth() / (float)var1.getIntrinsicWidth(), (float)var0.getHeight() / (float)var1.getIntrinsicHeight());
      return var2;
   }

   public void captureEndValues(TransitionValues var1) {
      this.captureValues(var1);
   }

   public void captureStartValues(TransitionValues var1) {
      this.captureValues(var1);
   }

   public Animator createAnimator(ViewGroup var1, TransitionValues var2, TransitionValues var3) {
      Matrix var4 = null;
      ObjectAnimator var9 = var4;
      if (var2 != null) {
         if (var3 == null) {
            var9 = var4;
         } else {
            Rect var5 = (Rect)var2.values.get("android:changeImageTransform:bounds");
            Rect var6 = (Rect)var3.values.get("android:changeImageTransform:bounds");
            var9 = var4;
            if (var5 != null) {
               if (var6 == null) {
                  var9 = var4;
               } else {
                  Matrix var10 = (Matrix)var2.values.get("android:changeImageTransform:matrix");
                  var4 = (Matrix)var3.values.get("android:changeImageTransform:matrix");
                  boolean var7;
                  if ((var10 != null || var4 != null) && (var10 == null || !var10.equals(var4))) {
                     var7 = false;
                  } else {
                     var7 = true;
                  }

                  if (var5.equals(var6) && var7) {
                     return null;
                  }

                  ImageView var12 = (ImageView)var3.view;
                  Drawable var11 = var12.getDrawable();
                  int var8 = var11.getIntrinsicWidth();
                  int var14 = var11.getIntrinsicHeight();
                  if (var8 > 0 && var14 > 0) {
                     Matrix var13 = var10;
                     if (var10 == null) {
                        var13 = MatrixUtils.IDENTITY_MATRIX;
                     }

                     var10 = var4;
                     if (var4 == null) {
                        var10 = MatrixUtils.IDENTITY_MATRIX;
                     }

                     ANIMATED_TRANSFORM_PROPERTY.set(var12, var13);
                     var9 = this.createMatrixAnimator(var12, var13, var10);
                  } else {
                     var9 = this.createNullAnimator(var12);
                  }
               }
            }
         }
      }

      return var9;
   }

   public String[] getTransitionProperties() {
      return sTransitionProperties;
   }
}
