package androidx.constraintlayout.motion.widget;

import java.util.Arrays;
import java.util.HashMap;

public class KeyCache {
   HashMap<Object, HashMap<String, float[]>> map = new HashMap();

   float getFloatValue(Object var1, String var2, int var3) {
      if (!this.map.containsKey(var1)) {
         return Float.NaN;
      } else {
         HashMap var4 = (HashMap)this.map.get(var1);
         if (!var4.containsKey(var2)) {
            return Float.NaN;
         } else {
            float[] var5 = (float[])var4.get(var2);
            return var5.length > var3 ? var5[var3] : Float.NaN;
         }
      }
   }

   void setFloatValue(Object var1, String var2, int var3, float var4) {
      if (!this.map.containsKey(var1)) {
         HashMap var5 = new HashMap();
         float[] var6 = new float[var3 + 1];
         var6[var3] = var4;
         var5.put(var2, var6);
         this.map.put(var1, var5);
      } else {
         HashMap var9 = (HashMap)this.map.get(var1);
         float[] var8;
         if (!var9.containsKey(var2)) {
            var8 = new float[var3 + 1];
            var8[var3] = var4;
            var9.put(var2, var8);
            this.map.put(var1, var9);
         } else {
            var8 = (float[])var9.get(var2);
            float[] var7 = var8;
            if (var8.length <= var3) {
               var7 = Arrays.copyOf(var8, var3 + 1);
            }

            var7[var3] = var4;
            var9.put(var2, var7);
         }
      }

   }
}
