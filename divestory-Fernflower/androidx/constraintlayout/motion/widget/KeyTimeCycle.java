package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class KeyTimeCycle extends Key {
   public static final int KEY_TYPE = 3;
   static final String NAME = "KeyTimeCycle";
   private static final String TAG = "KeyTimeCycle";
   private float mAlpha = Float.NaN;
   private int mCurveFit = -1;
   private float mElevation = Float.NaN;
   private float mProgress = Float.NaN;
   private float mRotation = Float.NaN;
   private float mRotationX = Float.NaN;
   private float mRotationY = Float.NaN;
   private float mScaleX = Float.NaN;
   private float mScaleY = Float.NaN;
   private String mTransitionEasing;
   private float mTransitionPathRotate = Float.NaN;
   private float mTranslationX = Float.NaN;
   private float mTranslationY = Float.NaN;
   private float mTranslationZ = Float.NaN;
   private float mWaveOffset = 0.0F;
   private CurveFit mWaveOffsetSpline;
   private float mWavePeriod = Float.NaN;
   private CurveFit mWavePeriodSpline;
   private int mWaveShape = 0;

   public KeyTimeCycle() {
      this.mType = 3;
      this.mCustomConstraints = new HashMap();
   }

   public void addTimeValues(HashMap<String, TimeCycleSplineSet> var1) {
      Iterator var2 = var1.keySet().iterator();

      while(true) {
         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            TimeCycleSplineSet var4 = (TimeCycleSplineSet)var1.get(var3);
            boolean var5 = var3.startsWith("CUSTOM");
            byte var6 = 7;
            if (var5) {
               var3 = var3.substring(7);
               ConstraintAttribute var7 = (ConstraintAttribute)this.mCustomConstraints.get(var3);
               if (var7 != null) {
                  ((TimeCycleSplineSet.CustomSet)var4).setPoint(this.mFramePosition, var7, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
               }
            } else {
               label97: {
                  switch(var3.hashCode()) {
                  case -1249320806:
                     if (var3.equals("rotationX")) {
                        var6 = 3;
                        break label97;
                     }
                     break;
                  case -1249320805:
                     if (var3.equals("rotationY")) {
                        var6 = 4;
                        break label97;
                     }
                     break;
                  case -1225497657:
                     if (var3.equals("translationX")) {
                        var6 = 8;
                        break label97;
                     }
                     break;
                  case -1225497656:
                     if (var3.equals("translationY")) {
                        var6 = 9;
                        break label97;
                     }
                     break;
                  case -1225497655:
                     if (var3.equals("translationZ")) {
                        var6 = 10;
                        break label97;
                     }
                     break;
                  case -1001078227:
                     if (var3.equals("progress")) {
                        var6 = 11;
                        break label97;
                     }
                     break;
                  case -908189618:
                     if (var3.equals("scaleX")) {
                        var6 = 6;
                        break label97;
                     }
                     break;
                  case -908189617:
                     if (var3.equals("scaleY")) {
                        break label97;
                     }
                     break;
                  case -40300674:
                     if (var3.equals("rotation")) {
                        var6 = 2;
                        break label97;
                     }
                     break;
                  case -4379043:
                     if (var3.equals("elevation")) {
                        var6 = 1;
                        break label97;
                     }
                     break;
                  case 37232917:
                     if (var3.equals("transitionPathRotate")) {
                        var6 = 5;
                        break label97;
                     }
                     break;
                  case 92909918:
                     if (var3.equals("alpha")) {
                        var6 = 0;
                        break label97;
                     }
                  }

                  var6 = -1;
               }

               switch(var6) {
               case 0:
                  if (!Float.isNaN(this.mAlpha)) {
                     var4.setPoint(this.mFramePosition, this.mAlpha, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                  }
                  break;
               case 1:
                  if (!Float.isNaN(this.mElevation)) {
                     var4.setPoint(this.mFramePosition, this.mElevation, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                  }
                  break;
               case 2:
                  if (!Float.isNaN(this.mRotation)) {
                     var4.setPoint(this.mFramePosition, this.mRotation, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                  }
                  break;
               case 3:
                  if (!Float.isNaN(this.mRotationX)) {
                     var4.setPoint(this.mFramePosition, this.mRotationX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                  }
                  break;
               case 4:
                  if (!Float.isNaN(this.mRotationY)) {
                     var4.setPoint(this.mFramePosition, this.mRotationY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                  }
                  break;
               case 5:
                  if (!Float.isNaN(this.mTransitionPathRotate)) {
                     var4.setPoint(this.mFramePosition, this.mTransitionPathRotate, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                  }
                  break;
               case 6:
                  if (!Float.isNaN(this.mScaleX)) {
                     var4.setPoint(this.mFramePosition, this.mScaleX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                  }
                  break;
               case 7:
                  if (!Float.isNaN(this.mScaleY)) {
                     var4.setPoint(this.mFramePosition, this.mScaleY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                  }
                  break;
               case 8:
                  if (!Float.isNaN(this.mTranslationX)) {
                     var4.setPoint(this.mFramePosition, this.mTranslationX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                  }
                  break;
               case 9:
                  if (!Float.isNaN(this.mTranslationY)) {
                     var4.setPoint(this.mFramePosition, this.mTranslationY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                  }
                  break;
               case 10:
                  if (!Float.isNaN(this.mTranslationZ)) {
                     var4.setPoint(this.mFramePosition, this.mTranslationZ, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                  }
                  break;
               case 11:
                  if (!Float.isNaN(this.mProgress)) {
                     var4.setPoint(this.mFramePosition, this.mProgress, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                  }
                  break;
               default:
                  StringBuilder var8 = new StringBuilder();
                  var8.append("UNKNOWN addValues \"");
                  var8.append(var3);
                  var8.append("\"");
                  Log.e("KeyTimeCycles", var8.toString());
               }
            }
         }

         return;
      }
   }

   public void addValues(HashMap<String, SplineSet> var1) {
      throw new IllegalArgumentException(" KeyTimeCycles do not support SplineSet");
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

      if (!Float.isNaN(this.mTranslationX)) {
         var1.add("translationX");
      }

      if (!Float.isNaN(this.mTranslationY)) {
         var1.add("translationY");
      }

      if (!Float.isNaN(this.mTranslationZ)) {
         var1.add("translationZ");
      }

      if (!Float.isNaN(this.mTransitionPathRotate)) {
         var1.add("transitionPathRotate");
      }

      if (!Float.isNaN(this.mScaleX)) {
         var1.add("scaleX");
      }

      if (!Float.isNaN(this.mScaleY)) {
         var1.add("scaleY");
      }

      if (!Float.isNaN(this.mProgress)) {
         var1.add("progress");
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

   int getCurveFit() {
      return this.mCurveFit;
   }

   public void load(Context var1, AttributeSet var2) {
      KeyTimeCycle.Loader.read(this, var1.obtainStyledAttributes(var2, R.styleable.KeyTimeCycle));
   }

   public void setInterpolation(HashMap<String, Integer> var1) {
      if (this.mCurveFit != -1) {
         if (!Float.isNaN(this.mAlpha)) {
            var1.put("alpha", this.mCurveFit);
         }

         if (!Float.isNaN(this.mElevation)) {
            var1.put("elevation", this.mCurveFit);
         }

         if (!Float.isNaN(this.mRotation)) {
            var1.put("rotation", this.mCurveFit);
         }

         if (!Float.isNaN(this.mRotationX)) {
            var1.put("rotationX", this.mCurveFit);
         }

         if (!Float.isNaN(this.mRotationY)) {
            var1.put("rotationY", this.mCurveFit);
         }

         if (!Float.isNaN(this.mTranslationX)) {
            var1.put("translationX", this.mCurveFit);
         }

         if (!Float.isNaN(this.mTranslationY)) {
            var1.put("translationY", this.mCurveFit);
         }

         if (!Float.isNaN(this.mTranslationZ)) {
            var1.put("translationZ", this.mCurveFit);
         }

         if (!Float.isNaN(this.mTransitionPathRotate)) {
            var1.put("transitionPathRotate", this.mCurveFit);
         }

         if (!Float.isNaN(this.mScaleX)) {
            var1.put("scaleX", this.mCurveFit);
         }

         if (!Float.isNaN(this.mScaleX)) {
            var1.put("scaleY", this.mCurveFit);
         }

         if (!Float.isNaN(this.mProgress)) {
            var1.put("progress", this.mCurveFit);
         }

         if (this.mCustomConstraints.size() > 0) {
            Iterator var2 = this.mCustomConstraints.keySet().iterator();

            while(var2.hasNext()) {
               String var3 = (String)var2.next();
               StringBuilder var4 = new StringBuilder();
               var4.append("CUSTOM,");
               var4.append(var3);
               var1.put(var4.toString(), this.mCurveFit);
            }
         }

      }
   }

   public void setValue(String var1, Object var2) {
      byte var3;
      label74: {
         switch(var1.hashCode()) {
         case -1812823328:
            if (var1.equals("transitionEasing")) {
               var3 = 9;
               break label74;
            }
            break;
         case -1249320806:
            if (var1.equals("rotationX")) {
               var3 = 5;
               break label74;
            }
            break;
         case -1249320805:
            if (var1.equals("rotationY")) {
               var3 = 6;
               break label74;
            }
            break;
         case -1225497657:
            if (var1.equals("translationX")) {
               var3 = 11;
               break label74;
            }
            break;
         case -1225497656:
            if (var1.equals("translationY")) {
               var3 = 12;
               break label74;
            }
            break;
         case -1001078227:
            if (var1.equals("progress")) {
               var3 = 3;
               break label74;
            }
            break;
         case -908189618:
            if (var1.equals("scaleX")) {
               var3 = 7;
               break label74;
            }
            break;
         case -908189617:
            if (var1.equals("scaleY")) {
               var3 = 8;
               break label74;
            }
            break;
         case -40300674:
            if (var1.equals("rotation")) {
               var3 = 4;
               break label74;
            }
            break;
         case -4379043:
            if (var1.equals("elevation")) {
               var3 = 2;
               break label74;
            }
            break;
         case 37232917:
            if (var1.equals("transitionPathRotate")) {
               var3 = 10;
               break label74;
            }
            break;
         case 92909918:
            if (var1.equals("alpha")) {
               var3 = 0;
               break label74;
            }
            break;
         case 579057826:
            if (var1.equals("curveFit")) {
               var3 = 1;
               break label74;
            }
            break;
         case 1317633238:
            if (var1.equals("mTranslationZ")) {
               var3 = 13;
               break label74;
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
      }

   }

   private static class Loader {
      private static final int ANDROID_ALPHA = 1;
      private static final int ANDROID_ELEVATION = 2;
      private static final int ANDROID_ROTATION = 4;
      private static final int ANDROID_ROTATION_X = 5;
      private static final int ANDROID_ROTATION_Y = 6;
      private static final int ANDROID_SCALE_X = 7;
      private static final int ANDROID_SCALE_Y = 14;
      private static final int ANDROID_TRANSLATION_X = 15;
      private static final int ANDROID_TRANSLATION_Y = 16;
      private static final int ANDROID_TRANSLATION_Z = 17;
      private static final int CURVE_FIT = 13;
      private static final int FRAME_POSITION = 12;
      private static final int PROGRESS = 18;
      private static final int TARGET_ID = 10;
      private static final int TRANSITION_EASING = 9;
      private static final int TRANSITION_PATH_ROTATE = 8;
      private static final int WAVE_OFFSET = 21;
      private static final int WAVE_PERIOD = 20;
      private static final int WAVE_SHAPE = 19;
      private static SparseIntArray mAttrMap;

      static {
         SparseIntArray var0 = new SparseIntArray();
         mAttrMap = var0;
         var0.append(R.styleable.KeyTimeCycle_android_alpha, 1);
         mAttrMap.append(R.styleable.KeyTimeCycle_android_elevation, 2);
         mAttrMap.append(R.styleable.KeyTimeCycle_android_rotation, 4);
         mAttrMap.append(R.styleable.KeyTimeCycle_android_rotationX, 5);
         mAttrMap.append(R.styleable.KeyTimeCycle_android_rotationY, 6);
         mAttrMap.append(R.styleable.KeyTimeCycle_android_scaleX, 7);
         mAttrMap.append(R.styleable.KeyTimeCycle_transitionPathRotate, 8);
         mAttrMap.append(R.styleable.KeyTimeCycle_transitionEasing, 9);
         mAttrMap.append(R.styleable.KeyTimeCycle_motionTarget, 10);
         mAttrMap.append(R.styleable.KeyTimeCycle_framePosition, 12);
         mAttrMap.append(R.styleable.KeyTimeCycle_curveFit, 13);
         mAttrMap.append(R.styleable.KeyTimeCycle_android_scaleY, 14);
         mAttrMap.append(R.styleable.KeyTimeCycle_android_translationX, 15);
         mAttrMap.append(R.styleable.KeyTimeCycle_android_translationY, 16);
         mAttrMap.append(R.styleable.KeyTimeCycle_android_translationZ, 17);
         mAttrMap.append(R.styleable.KeyTimeCycle_motionProgress, 18);
         mAttrMap.append(R.styleable.KeyTimeCycle_wavePeriod, 20);
         mAttrMap.append(R.styleable.KeyTimeCycle_waveOffset, 21);
         mAttrMap.append(R.styleable.KeyTimeCycle_waveShape, 19);
      }

      public static void read(KeyTimeCycle var0, TypedArray var1) {
         int var2 = var1.getIndexCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            int var4 = var1.getIndex(var3);
            switch(mAttrMap.get(var4)) {
            case 1:
               var0.mAlpha = var1.getFloat(var4, var0.mAlpha);
               break;
            case 2:
               var0.mElevation = var1.getDimension(var4, var0.mElevation);
               break;
            case 3:
            case 11:
            default:
               StringBuilder var5 = new StringBuilder();
               var5.append("unused attribute 0x");
               var5.append(Integer.toHexString(var4));
               var5.append("   ");
               var5.append(mAttrMap.get(var4));
               Log.e("KeyTimeCycle", var5.toString());
               break;
            case 4:
               var0.mRotation = var1.getFloat(var4, var0.mRotation);
               break;
            case 5:
               var0.mRotationX = var1.getFloat(var4, var0.mRotationX);
               break;
            case 6:
               var0.mRotationY = var1.getFloat(var4, var0.mRotationY);
               break;
            case 7:
               var0.mScaleX = var1.getFloat(var4, var0.mScaleX);
               break;
            case 8:
               var0.mTransitionPathRotate = var1.getFloat(var4, var0.mTransitionPathRotate);
               break;
            case 9:
               var0.mTransitionEasing = var1.getString(var4);
               break;
            case 10:
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
            case 12:
               var0.mFramePosition = var1.getInt(var4, var0.mFramePosition);
               break;
            case 13:
               var0.mCurveFit = var1.getInteger(var4, var0.mCurveFit);
               break;
            case 14:
               var0.mScaleY = var1.getFloat(var4, var0.mScaleY);
               break;
            case 15:
               var0.mTranslationX = var1.getDimension(var4, var0.mTranslationX);
               break;
            case 16:
               var0.mTranslationY = var1.getDimension(var4, var0.mTranslationY);
               break;
            case 17:
               if (VERSION.SDK_INT >= 21) {
                  var0.mTranslationZ = var1.getDimension(var4, var0.mTranslationZ);
               }
               break;
            case 18:
               var0.mProgress = var1.getFloat(var4, var0.mProgress);
               break;
            case 19:
               var0.mWaveShape = var1.getInt(var4, var0.mWaveShape);
               break;
            case 20:
               var0.mWavePeriod = var1.getFloat(var4, var0.mWavePeriod);
               break;
            case 21:
               if (var1.peekValue(var4).type == 5) {
                  var0.mWaveOffset = var1.getDimension(var4, var0.mWaveOffset);
               } else {
                  var0.mWaveOffset = var1.getFloat(var4, var0.mWaveOffset);
               }
            }
         }

      }
   }
}
