package com.google.android.material.animation;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;

public class MatrixEvaluator implements TypeEvaluator<Matrix> {
   private final float[] tempEndValues = new float[9];
   private final Matrix tempMatrix = new Matrix();
   private final float[] tempStartValues = new float[9];

   public Matrix evaluate(float var1, Matrix var2, Matrix var3) {
      var2.getValues(this.tempStartValues);
      var3.getValues(this.tempEndValues);

      for(int var4 = 0; var4 < 9; ++var4) {
         float[] var8 = this.tempEndValues;
         float var5 = var8[var4];
         float[] var7 = this.tempStartValues;
         float var6 = var7[var4];
         var8[var4] = var7[var4] + (var5 - var6) * var1;
      }

      this.tempMatrix.setValues(this.tempEndValues);
      return this.tempMatrix;
   }
}
