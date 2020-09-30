package androidx.core.graphics;

import android.graphics.Path;
import android.graphics.PointF;
import java.util.ArrayList;
import java.util.Collection;

public final class PathUtils {
   private PathUtils() {
   }

   public static Collection<PathSegment> flatten(Path var0) {
      return flatten(var0, 0.5F);
   }

   public static Collection<PathSegment> flatten(Path var0, float var1) {
      float[] var12 = var0.approximate(var1);
      int var2 = var12.length / 3;
      ArrayList var3 = new ArrayList(var2);

      for(int var4 = 1; var4 < var2; ++var4) {
         int var5 = var4 * 3;
         int var6 = (var4 - 1) * 3;
         var1 = var12[var5];
         float var7 = var12[var5 + 1];
         float var8 = var12[var5 + 2];
         float var9 = var12[var6];
         float var10 = var12[var6 + 1];
         float var11 = var12[var6 + 2];
         if (var1 != var9 && (var7 != var10 || var8 != var11)) {
            var3.add(new PathSegment(new PointF(var10, var11), var9, new PointF(var7, var8), var1));
         }
      }

      return var3;
   }
}
