package androidx.constraintlayout.motion.widget;

import android.graphics.RectF;
import android.view.View;
import java.util.HashSet;

abstract class KeyPositionBase extends Key {
   protected static final float SELECTION_SLOPE = 20.0F;
   int mCurveFit;

   KeyPositionBase() {
      this.mCurveFit = UNSET;
   }

   abstract void calcPosition(int var1, int var2, float var3, float var4, float var5, float var6);

   void getAttributeNames(HashSet<String> var1) {
   }

   abstract float getPositionX();

   abstract float getPositionY();

   public abstract boolean intersects(int var1, int var2, RectF var3, RectF var4, float var5, float var6);

   abstract void positionAttributes(View var1, RectF var2, RectF var3, float var4, float var5, String[] var6, float[] var7);
}
