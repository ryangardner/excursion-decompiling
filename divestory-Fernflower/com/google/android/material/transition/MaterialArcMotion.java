package com.google.android.material.transition;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.transition.PathMotion;

public final class MaterialArcMotion extends PathMotion {
   private static PointF getControlPoint(float var0, float var1, float var2, float var3) {
      return var1 > var3 ? new PointF(var2, var1) : new PointF(var0, var3);
   }

   public Path getPath(float var1, float var2, float var3, float var4) {
      Path var5 = new Path();
      var5.moveTo(var1, var2);
      PointF var6 = getControlPoint(var1, var2, var3, var4);
      var5.quadTo(var6.x, var6.y, var3, var4);
      return var5;
   }
}
