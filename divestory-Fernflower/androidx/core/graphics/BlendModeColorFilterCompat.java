package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;

public class BlendModeColorFilterCompat {
   private BlendModeColorFilterCompat() {
   }

   public static ColorFilter createBlendModeColorFilterCompat(int var0, BlendModeCompat var1) {
      int var2 = VERSION.SDK_INT;
      BlendMode var3 = null;
      Mode var4 = null;
      if (var2 >= 29) {
         var3 = BlendModeUtils.obtainBlendModeFromCompat(var1);
         BlendModeColorFilter var6 = var4;
         if (var3 != null) {
            var6 = new BlendModeColorFilter(var0, var3);
         }

         return var6;
      } else {
         var4 = BlendModeUtils.obtainPorterDuffFromCompat(var1);
         PorterDuffColorFilter var5 = var3;
         if (var4 != null) {
            var5 = new PorterDuffColorFilter(var0, var4);
         }

         return var5;
      }
   }
}
