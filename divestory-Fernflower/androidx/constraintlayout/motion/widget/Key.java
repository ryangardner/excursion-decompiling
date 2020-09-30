package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Key {
   static final String ALPHA = "alpha";
   static final String CUSTOM = "CUSTOM";
   static final String ELEVATION = "elevation";
   static final String PIVOT_X = "transformPivotX";
   static final String PIVOT_Y = "transformPivotY";
   static final String PROGRESS = "progress";
   static final String ROTATION = "rotation";
   static final String ROTATION_X = "rotationX";
   static final String ROTATION_Y = "rotationY";
   static final String SCALE_X = "scaleX";
   static final String SCALE_Y = "scaleY";
   static final String TRANSITION_PATH_ROTATE = "transitionPathRotate";
   static final String TRANSLATION_X = "translationX";
   static final String TRANSLATION_Y = "translationY";
   static final String TRANSLATION_Z = "translationZ";
   public static int UNSET;
   static final String WAVE_OFFSET = "waveOffset";
   static final String WAVE_PERIOD = "wavePeriod";
   static final String WAVE_VARIES_BY = "waveVariesBy";
   HashMap<String, ConstraintAttribute> mCustomConstraints;
   int mFramePosition;
   int mTargetId;
   String mTargetString;
   protected int mType;

   public Key() {
      int var1 = UNSET;
      this.mFramePosition = var1;
      this.mTargetId = var1;
      this.mTargetString = null;
   }

   public abstract void addValues(HashMap<String, SplineSet> var1);

   abstract void getAttributeNames(HashSet<String> var1);

   abstract void load(Context var1, AttributeSet var2);

   boolean matches(String var1) {
      String var2 = this.mTargetString;
      return var2 != null && var1 != null ? var1.matches(var2) : false;
   }

   public void setInterpolation(HashMap<String, Integer> var1) {
   }

   public abstract void setValue(String var1, Object var2);

   boolean toBoolean(Object var1) {
      boolean var2;
      if (var1 instanceof Boolean) {
         var2 = (Boolean)var1;
      } else {
         var2 = Boolean.parseBoolean(var1.toString());
      }

      return var2;
   }

   float toFloat(Object var1) {
      float var2;
      if (var1 instanceof Float) {
         var2 = (Float)var1;
      } else {
         var2 = Float.parseFloat(var1.toString());
      }

      return var2;
   }

   int toInt(Object var1) {
      int var2;
      if (var1 instanceof Integer) {
         var2 = (Integer)var1;
      } else {
         var2 = Integer.parseInt(var1.toString());
      }

      return var2;
   }
}
