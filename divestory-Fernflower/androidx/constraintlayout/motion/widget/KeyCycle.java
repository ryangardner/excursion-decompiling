package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class KeyCycle extends Key {
   public static final int KEY_TYPE = 4;
   static final String NAME = "KeyCycle";
   private static final String TAG = "KeyCycle";
   private float mAlpha = Float.NaN;
   private int mCurveFit = 0;
   private float mElevation = Float.NaN;
   private float mProgress = Float.NaN;
   private float mRotation = Float.NaN;
   private float mRotationX = Float.NaN;
   private float mRotationY = Float.NaN;
   private float mScaleX = Float.NaN;
   private float mScaleY = Float.NaN;
   private String mTransitionEasing = null;
   private float mTransitionPathRotate = Float.NaN;
   private float mTranslationX = Float.NaN;
   private float mTranslationY = Float.NaN;
   private float mTranslationZ = Float.NaN;
   private float mWaveOffset = 0.0F;
   private float mWavePeriod = Float.NaN;
   private int mWaveShape = -1;
   private int mWaveVariesBy = -1;

   public KeyCycle() {
      this.mType = 4;
      this.mCustomConstraints = new HashMap();
   }

   public void addCycleValues(HashMap<String, KeyCycleOscillator> var1) {
      Iterator var2 = var1.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         if (var3.startsWith("CUSTOM")) {
            String var4 = var3.substring(7);
            ConstraintAttribute var6 = (ConstraintAttribute)this.mCustomConstraints.get(var4);
            if (var6 != null && var6.getType() == ConstraintAttribute.AttributeType.FLOAT_TYPE) {
               ((KeyCycleOscillator)var1.get(var3)).setPoint(this.mFramePosition, this.mWaveShape, this.mWaveVariesBy, this.mWavePeriod, this.mWaveOffset, var6.getValueToInterpolate(), var6);
            }
         }

         float var5 = this.getValue(var3);
         if (!Float.isNaN(var5)) {
            ((KeyCycleOscillator)var1.get(var3)).setPoint(this.mFramePosition, this.mWaveShape, this.mWaveVariesBy, this.mWavePeriod, this.mWaveOffset, var5);
         }
      }

   }

   public void addValues(HashMap<String, SplineSet> var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("add ");
      var2.append(var1.size());
      var2.append(" values");
      Debug.logStack("KeyCycle", var2.toString(), 2);
      Iterator var6 = var1.keySet().iterator();

      while(var6.hasNext()) {
         String var3 = (String)var6.next();
         SplineSet var4 = (SplineSet)var1.get(var3);
         byte var5 = -1;
         switch(var3.hashCode()) {
         case -1249320806:
            if (var3.equals("rotationX")) {
               var5 = 3;
            }
            break;
         case -1249320805:
            if (var3.equals("rotationY")) {
               var5 = 4;
            }
            break;
         case -1225497657:
            if (var3.equals("translationX")) {
               var5 = 8;
            }
            break;
         case -1225497656:
            if (var3.equals("translationY")) {
               var5 = 9;
            }
            break;
         case -1225497655:
            if (var3.equals("translationZ")) {
               var5 = 10;
            }
            break;
         case -1001078227:
            if (var3.equals("progress")) {
               var5 = 12;
            }
            break;
         case -908189618:
            if (var3.equals("scaleX")) {
               var5 = 6;
            }
            break;
         case -908189617:
            if (var3.equals("scaleY")) {
               var5 = 7;
            }
            break;
         case -40300674:
            if (var3.equals("rotation")) {
               var5 = 2;
            }
            break;
         case -4379043:
            if (var3.equals("elevation")) {
               var5 = 1;
            }
            break;
         case 37232917:
            if (var3.equals("transitionPathRotate")) {
               var5 = 5;
            }
            break;
         case 92909918:
            if (var3.equals("alpha")) {
               var5 = 0;
            }
            break;
         case 156108012:
            if (var3.equals("waveOffset")) {
               var5 = 11;
            }
         }

         switch(var5) {
         case 0:
            var4.setPoint(this.mFramePosition, this.mAlpha);
            break;
         case 1:
            var4.setPoint(this.mFramePosition, this.mElevation);
            break;
         case 2:
            var4.setPoint(this.mFramePosition, this.mRotation);
            break;
         case 3:
            var4.setPoint(this.mFramePosition, this.mRotationX);
            break;
         case 4:
            var4.setPoint(this.mFramePosition, this.mRotationY);
            break;
         case 5:
            var4.setPoint(this.mFramePosition, this.mTransitionPathRotate);
            break;
         case 6:
            var4.setPoint(this.mFramePosition, this.mScaleX);
            break;
         case 7:
            var4.setPoint(this.mFramePosition, this.mScaleY);
            break;
         case 8:
            var4.setPoint(this.mFramePosition, this.mTranslationX);
            break;
         case 9:
            var4.setPoint(this.mFramePosition, this.mTranslationY);
            break;
         case 10:
            var4.setPoint(this.mFramePosition, this.mTranslationZ);
            break;
         case 11:
            var4.setPoint(this.mFramePosition, this.mWaveOffset);
            break;
         case 12:
            var4.setPoint(this.mFramePosition, this.mProgress);
            break;
         default:
            StringBuilder var7 = new StringBuilder();
            var7.append("  UNKNOWN  ");
            var7.append(var3);
            Log.v("WARNING KeyCycle", var7.toString());
         }
      }

   }

   public void getAttributeNames(HashSet<String> var1) {
      if (!Float.isNaN(this.mAlpha)) {
         var1.add("alpha");
      }

      if (!Float.isNaN(this.mElevation)) {
         var1.add("elevation");
      }

      if (!Float.isNaN(this.mRotation)) {
         var1.add("rotation");
      }

      if (!Float.isNaN(this.mRotationX)) {
         var1.add("rotationX");
      }

      if (!Float.isNaN(this.mRotationY)) {
         var1.add("rotationY");
      }

      if (!Float.isNaN(this.mScaleX)) {
         var1.add("scaleX");
      }

      if (!Float.isNaN(this.mScaleY)) {
         var1.add("scaleY");
      }

      if (!Float.isNaN(this.mTransitionPathRotate)) {
         var1.add("transitionPathRotate");
      }

      if (!Float.isNaN(this.mTranslationX)) {
         var1.add("translationX");
      }

      if (!Float.isNaN(this.mTranslationY)) {
         var1.add("translationY");
      }

      if (!Float.isNaN(this.mTranslationZ)) {
         var1.add("translationZ");
      }

      if (this.mCustomConstraints.size() > 0) {
         Iterator var2 = this.mCustomConstraints.keySet().iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            StringBuilder var4 = new StringBuilder();
            var4.append("CUSTOM,");
            var4.append(var3);
            var1.add(var4.toString());
         }
      }

   }

   public float getValue(String var1) {
      byte var2;
      label66: {
         switch(var1.hashCode()) {
         case -1249320806:
            if (var1.equals("rotationX")) {
               var2 = 3;
               break label66;
            }
            break;
         case -1249320805:
            if (var1.equals("rotationY")) {
               var2 = 4;
               break label66;
            }
            break;
         case -1225497657:
            if (var1.equals("translationX")) {
               var2 = 8;
               break label66;
            }
            break;
         case -1225497656:
            if (var1.equals("translationY")) {
               var2 = 9;
               break label66;
            }
            break;
         case -1225497655:
            if (var1.equals("translationZ")) {
               var2 = 10;
               break label66;
            }
            break;
         case -1001078227:
            if (var1.equals("progress")) {
               var2 = 12;
               break label66;
            }
            break;
         case -908189618:
            if (var1.equals("scaleX")) {
               var2 = 6;
               break label66;
            }
            break;
         case -908189617:
            if (var1.equals("scaleY")) {
               var2 = 7;
               break label66;
            }
            break;
         case -40300674:
            if (var1.equals("rotation")) {
               var2 = 2;
               break label66;
            }
            break;
         case -4379043:
            if (var1.equals("elevation")) {
               var2 = 1;
               break label66;
            }
            break;
         case 37232917:
            if (var1.equals("transitionPathRotate")) {
               var2 = 5;
               break label66;
            }
            break;
         case 92909918:
            if (var1.equals("alpha")) {
               var2 = 0;
               break label66;
            }
            break;
         case 156108012:
            if (var1.equals("waveOffset")) {
               var2 = 11;
               break label66;
            }
         }

         var2 = -1;
      }

      switch(var2) {
      case 0:
         return this.mAlpha;
      case 1:
         return this.mElevation;
      case 2:
         return this.mRotation;
      case 3:
         return this.mRotationX;
      case 4:
         return this.mRotationY;
      case 5:
         return this.mTransitionPathRotate;
      case 6:
         return this.mScaleX;
      case 7:
         return this.mScaleY;
      case 8:
         return this.mTranslationX;
      case 9:
         return this.mTranslationY;
      case 10:
         return this.mTranslationZ;
      case 11:
         return this.mWaveOffset;
      case 12:
         return this.mProgress;
      default:
         StringBuilder var3 = new StringBuilder();
         var3.append("  UNKNOWN  ");
         var3.append(var1);
         Log.v("WARNING! KeyCycle", var3.toString());
         return Float.NaN;
      }
   }

   public void load(Context var1, AttributeSet var2) {
      KeyCycle.Loader.read(this, var1.obtainStyledAttributes(var2, R.styleable.KeyCycle));
   }

   public void setValue(String var1, Object var2) {
      byte var3;
      label82: {
         switch(var1.hashCode()) {
         case -1812823328:
            if (var1.equals("transitionEasing")) {
               var3 = 9;
               break label82;
            }
            break;
         case -1249320806:
            if (var1.equals("rotationX")) {
               var3 = 5;
               break label82;
            }
            break;
         case -1249320805:
            if (var1.equals("rotationY")) {
               var3 = 6;
               break label82;
            }
            break;
         case -1225497657:
            if (var1.equals("translationX")) {
               var3 = 11;
               break label82;
            }
            break;
         case -1225497656:
            if (var1.equals("translationY")) {
               var3 = 12;
               break label82;
            }
            break;
         case -1001078227:
            if (var1.equals("progress")) {
               var3 = 3;
               break label82;
            }
            break;
         case -908189618:
            if (var1.equals("scaleX")) {
               var3 = 7;
               break label82;
            }
            break;
         case -908189617:
            if (var1.equals("scaleY")) {
               var3 = 8;
               break label82;
            }
            break;
         case -40300674:
            if (var1.equals("rotation")) {
               var3 = 4;
               break label82;
            }
            break;
         case -4379043:
            if (var1.equals("elevation")) {
               var3 = 2;
               break label82;
            }
            break;
         case 37232917:
            if (var1.equals("transitionPathRotate")) {
               var3 = 10;
               break label82;
            }
            break;
         case 92909918:
            if (var1.equals("alpha")) {
               var3 = 0;
               break label82;
            }
            break;
         case 156108012:
            if (var1.equals("waveOffset")) {
               var3 = 15;
               break label82;
            }
            break;
         case 184161818:
            if (var1.equals("wavePeriod")) {
               var3 = 14;
               break label82;
            }
            break;
         case 579057826:
            if (var1.equals("curveFit")) {
               var3 = 1;
               break label82;
            }
            break;
         case 1317633238:
            if (var1.equals("mTranslationZ")) {
               var3 = 13;
               break label82;
            }
         }

         var3 = -1;
      }

      switch(var3) {
      case 0:
         this.mAlpha = this.toFloat(var2);
         break;
      case 1:
         this.mCurveFit = this.toInt(var2);
         break;
      case 2:
         this.mElevation = this.toFloat(var2);
         break;
      case 3:
         this.mProgress = this.toFloat(var2);
         break;
      case 4:
         this.mRotation = this.toFloat(var2);
         break;
      case 5:
         this.mRotationX = this.toFloat(var2);
         break;
      case 6:
         this.mRotationY = this.toFloat(var2);
         break;
      case 7:
         this.mScaleX = this.toFloat(var2);
         break;
      case 8:
         this.mScaleY = this.toFloat(var2);
         break;
      case 9:
         this.mTransitionEasing = var2.toString();
         break;
      case 10:
         this.mTransitionPathRotate = this.toFloat(var2);
         break;
      case 11:
         this.mTranslationX = this.toFloat(var2);
         break;
      case 12:
         this.mTranslationY = this.toFloat(var2);
         break;
      case 13:
         this.mTranslationZ = this.toFloat(var2);
         break;
      case 14:
         this.mWavePeriod = this.toFloat(var2);
         break;
      case 15:
         this.mWaveOffset = this.toFloat(var2);
      }

   }

   private static class Loader {
      private static final int ANDROID_ALPHA = 9;
      private static final int ANDROID_ELEVATION = 10;
      private static final int ANDROID_ROTATION = 11;
      private static final int ANDROID_ROTATION_X = 12;
      private static final int ANDROID_ROTATION_Y = 13;
      private static final int ANDROID_SCALE_X = 15;
      private static final int ANDROID_SCALE_Y = 16;
      private static final int ANDROID_TRANSLATION_X = 17;
      private static final int ANDROID_TRANSLATION_Y = 18;
      private static final int ANDROID_TRANSLATION_Z = 19;
      private static final int CURVE_FIT = 4;
      private static final int FRAME_POSITION = 2;
      private static final int PROGRESS = 20;
      private static final int TARGET_ID = 1;
      private static final int TRANSITION_EASING = 3;
      private static final int TRANSITION_PATH_ROTATE = 14;
      private static final int WAVE_OFFSET = 7;
      private static final int WAVE_PERIOD = 6;
      private static final int WAVE_SHAPE = 5;
      private static final int WAVE_VARIES_BY = 8;
      private static SparseIntArray mAttrMap;

      static {
         SparseIntArray var0 = new SparseIntArray();
         mAttrMap = var0;
         var0.append(R.styleable.KeyCycle_motionTarget, 1);
         mAttrMap.append(R.styleable.KeyCycle_framePosition, 2);
         mAttrMap.append(R.styleable.KeyCycle_transitionEasing, 3);
         mAttrMap.append(R.styleable.KeyCycle_curveFit, 4);
         mAttrMap.append(R.styleable.KeyCycle_waveShape, 5);
         mAttrMap.append(R.styleable.KeyCycle_wavePeriod, 6);
         mAttrMap.append(R.styleable.KeyCycle_waveOffset, 7);
         mAttrMap.append(R.styleable.KeyCycle_waveVariesBy, 8);
         mAttrMap.append(R.styleable.KeyCycle_android_alpha, 9);
         mAttrMap.append(R.styleable.KeyCycle_android_elevation, 10);
         mAttrMap.append(R.styleable.KeyCycle_android_rotation, 11);
         mAttrMap.append(R.styleable.KeyCycle_android_rotationX, 12);
         mAttrMap.append(R.styleable.KeyCycle_android_rotationY, 13);
         mAttrMap.append(R.styleable.KeyCycle_transitionPathRotate, 14);
         mAttrMap.append(R.styleable.KeyCycle_android_scaleX, 15);
         mAttrMap.append(R.styleable.KeyCycle_android_scaleY, 16);
         mAttrMap.append(R.styleable.KeyCycle_android_translationX, 17);
         mAttrMap.append(R.styleable.KeyCycle_android_translationY, 18);
         mAttrMap.append(R.styleable.KeyCycle_android_translationZ, 19);
         mAttrMap.append(R.styleable.KeyCycle_motionProgress, 20);
      }

      private static void read(KeyCycle var0, TypedArray var1) {
         int var2 = var1.getIndexCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            int var4 = var1.getIndex(var3);
            switch(mAttrMap.get(var4)) {
            case 1:
               if (MotionLayout.IS_IN_EDIT_MODE) {
                  var0.mTargetId = var1.getResourceId(var4, var0.mTargetId);
                  if (var0.mTargetId == -1) {
                     var0.mTargetString = var1.getString(var4);
                  }
               } else if (var1.peekValue(var4).type == 3) {
                  var0.mTargetString = var1.getString(var4);
               } else {
                  var0.mTargetId = var1.getResourceId(var4, var0.mTargetId);
               }
               break;
            case 2:
               var0.mFramePosition = var1.getInt(var4, var0.mFramePosition);
               break;
            case 3:
               var0.mTransitionEasing = var1.getString(var4);
               break;
            case 4:
               var0.mCurveFit = var1.getInteger(var4, var0.mCurveFit);
               break;
            case 5:
               var0.mWaveShape = var1.getInt(var4, var0.mWaveShape);
               break;
            case 6:
               var0.mWavePeriod = var1.getFloat(var4, var0.mWavePeriod);
               break;
            case 7:
               if (var1.peekValue(var4).type == 5) {
                  var0.mWaveOffset = var1.getDimension(var4, var0.mWaveOffset);
               } else {
                  var0.mWaveOffset = var1.getFloat(var4, var0.mWaveOffset);
               }
               break;
            case 8:
               var0.mWaveVariesBy = var1.getInt(var4, var0.mWaveVariesBy);
               break;
            case 9:
               var0.mAlpha = var1.getFloat(var4, var0.mAlpha);
               break;
            case 10:
               var0.mElevation = var1.getDimension(var4, var0.mElevation);
               break;
            case 11:
               var0.mRotation = var1.getFloat(var4, var0.mRotation);
               break;
            case 12:
               var0.mRotationX = var1.getFloat(var4, var0.mRotationX);
               break;
            case 13:
               var0.mRotationY = var1.getFloat(var4, var0.mRotationY);
               break;
            case 14:
               var0.mTransitionPathRotate = var1.getFloat(var4, var0.mTransitionPathRotate);
               break;
            case 15:
               var0.mScaleX = var1.getFloat(var4, var0.mScaleX);
               break;
            case 16:
               var0.mScaleY = var1.getFloat(var4, var0.mScaleY);
               break;
            case 17:
               var0.mTranslationX = var1.getDimension(var4, var0.mTranslationX);
               break;
            case 18:
               var0.mTranslationY = var1.getDimension(var4, var0.mTranslationY);
               break;
            case 19:
               if (VERSION.SDK_INT >= 21) {
                  var0.mTranslationZ = var1.getDimension(var4, var0.mTranslationZ);
               }
               break;
            case 20:
               var0.mProgress = var1.getFloat(var4, var0.mProgress);
               break;
            default:
               StringBuilder var5 = new StringBuilder();
               var5.append("unused attribute 0x");
               var5.append(Integer.toHexString(var4));
               var5.append("   ");
               var5.append(mAttrMap.get(var4));
               Log.e("KeyCycle", var5.toString());
            }
         }

      }
   }
}
