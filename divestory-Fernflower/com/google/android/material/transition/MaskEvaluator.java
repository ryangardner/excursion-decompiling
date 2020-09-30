package com.google.android.material.transition;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.os.Build.VERSION;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;

class MaskEvaluator {
   private ShapeAppearanceModel currentShapeAppearanceModel;
   private final Path endPath = new Path();
   private final Path path = new Path();
   private final ShapeAppearancePathProvider pathProvider = new ShapeAppearancePathProvider();
   private final Path startPath = new Path();

   void clip(Canvas var1) {
      if (VERSION.SDK_INT >= 23) {
         var1.clipPath(this.path);
      } else {
         var1.clipPath(this.startPath);
         var1.clipPath(this.endPath, Op.UNION);
      }

   }

   void evaluate(float var1, ShapeAppearanceModel var2, ShapeAppearanceModel var3, RectF var4, RectF var5, RectF var6, MaterialContainerTransform.ProgressThresholds var7) {
      var2 = TransitionUtils.lerp(var2, var3, var4, var6, var7.getStart(), var7.getEnd(), var1);
      this.currentShapeAppearanceModel = var2;
      this.pathProvider.calculatePath(var2, 1.0F, var5, this.startPath);
      this.pathProvider.calculatePath(this.currentShapeAppearanceModel, 1.0F, var6, this.endPath);
      if (VERSION.SDK_INT >= 23) {
         this.path.op(this.startPath, this.endPath, android.graphics.Path.Op.UNION);
      }

   }

   ShapeAppearanceModel getCurrentShapeAppearanceModel() {
      return this.currentShapeAppearanceModel;
   }

   Path getPath() {
      return this.path;
   }
}
