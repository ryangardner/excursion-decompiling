package androidx.constraintlayout.motion.widget;

import android.os.Build.VERSION;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

public abstract class TimeCycleSplineSet {
   private static final int CURVE_OFFSET = 2;
   private static final int CURVE_PERIOD = 1;
   private static final int CURVE_VALUE = 0;
   private static final String TAG = "SplineSet";
   private static float VAL_2PI;
   private int count;
   float last_cycle = Float.NaN;
   long last_time;
   private float[] mCache = new float[3];
   protected boolean mContinue = false;
   protected CurveFit mCurveFit;
   protected int[] mTimePoints = new int[10];
   private String mType;
   protected float[][] mValues = new float[10][3];
   protected int mWaveShape = 0;

   static TimeCycleSplineSet makeCustomSpline(String var0, SparseArray<ConstraintAttribute> var1) {
      return new TimeCycleSplineSet.CustomSet(var0, var1);
   }

   static TimeCycleSplineSet makeSpline(String var0, long var1) {
      byte var3;
      label64: {
         switch(var0.hashCode()) {
         case -1249320806:
            if (var0.equals("rotationX")) {
               var3 = 3;
               break label64;
            }
            break;
         case -1249320805:
            if (var0.equals("rotationY")) {
               var3 = 4;
               break label64;
            }
            break;
         case -1225497657:
            if (var0.equals("translationX")) {
               var3 = 8;
               break label64;
            }
            break;
         case -1225497656:
            if (var0.equals("translationY")) {
               var3 = 9;
               break label64;
            }
            break;
         case -1225497655:
            if (var0.equals("translationZ")) {
               var3 = 10;
               break label64;
            }
            break;
         case -1001078227:
            if (var0.equals("progress")) {
               var3 = 11;
               break label64;
            }
            break;
         case -908189618:
            if (var0.equals("scaleX")) {
               var3 = 6;
               break label64;
            }
            break;
         case -908189617:
            if (var0.equals("scaleY")) {
               var3 = 7;
               break label64;
            }
            break;
         case -40300674:
            if (var0.equals("rotation")) {
               var3 = 2;
               break label64;
            }
            break;
         case -4379043:
            if (var0.equals("elevation")) {
               var3 = 1;
               break label64;
            }
            break;
         case 37232917:
            if (var0.equals("transitionPathRotate")) {
               var3 = 5;
               break label64;
            }
            break;
         case 92909918:
            if (var0.equals("alpha")) {
               var3 = 0;
               break label64;
            }
         }

         var3 = -1;
      }

      Object var4;
      switch(var3) {
      case 0:
         var4 = new TimeCycleSplineSet.AlphaSet();
         break;
      case 1:
         var4 = new TimeCycleSplineSet.ElevationSet();
         break;
      case 2:
         var4 = new TimeCycleSplineSet.RotationSet();
         break;
      case 3:
         var4 = new TimeCycleSplineSet.RotationXset();
         break;
      case 4:
         var4 = new TimeCycleSplineSet.RotationYset();
         break;
      case 5:
         var4 = new TimeCycleSplineSet.PathRotate();
         break;
      case 6:
         var4 = new TimeCycleSplineSet.ScaleXset();
         break;
      case 7:
         var4 = new TimeCycleSplineSet.ScaleYset();
         break;
      case 8:
         var4 = new TimeCycleSplineSet.TranslationXset();
         break;
      case 9:
         var4 = new TimeCycleSplineSet.TranslationYset();
         break;
      case 10:
         var4 = new TimeCycleSplineSet.TranslationZset();
         break;
      case 11:
         var4 = new TimeCycleSplineSet.ProgressSet();
         break;
      default:
         return null;
      }

      ((TimeCycleSplineSet)var4).setStartTime(var1);
      return (TimeCycleSplineSet)var4;
   }

   protected float calcWave(float var1) {
      switch(this.mWaveShape) {
      case 1:
         return Math.signum(var1 * VAL_2PI);
      case 2:
         var1 = Math.abs(var1);
         break;
      case 3:
         return (var1 * 2.0F + 1.0F) % 2.0F - 1.0F;
      case 4:
         var1 = (var1 * 2.0F + 1.0F) % 2.0F;
         break;
      case 5:
         return (float)Math.cos((double)(var1 * VAL_2PI));
      case 6:
         var1 = 1.0F - Math.abs(var1 * 4.0F % 4.0F - 2.0F);
         var1 *= var1;
         break;
      default:
         return (float)Math.sin((double)(var1 * VAL_2PI));
      }

      return 1.0F - var1;
   }

   public float get(float var1, long var2, View var4, KeyCache var5) {
      this.mCurveFit.getPos((double)var1, this.mCache);
      float[] var6 = this.mCache;
      var1 = var6[1];
      float var13;
      int var7 = (var13 = var1 - 0.0F) == 0.0F ? 0 : (var13 < 0.0F ? -1 : 1);
      if (var7 == 0) {
         this.mContinue = false;
         return var6[2];
      } else {
         float var8;
         if (Float.isNaN(this.last_cycle)) {
            var8 = var5.getFloatValue(var4, this.mType, 0);
            this.last_cycle = var8;
            if (Float.isNaN(var8)) {
               this.last_cycle = 0.0F;
            }
         }

         long var9 = this.last_time;
         var1 = (float)(((double)this.last_cycle + (double)(var2 - var9) * 1.0E-9D * (double)var1) % 1.0D);
         this.last_cycle = var1;
         var5.setFloatValue(var4, this.mType, 0, var1);
         this.last_time = var2;
         var1 = this.mCache[0];
         var8 = this.calcWave(this.last_cycle);
         float var11 = this.mCache[2];
         boolean var12;
         if (var1 == 0.0F && var7 == 0) {
            var12 = false;
         } else {
            var12 = true;
         }

         this.mContinue = var12;
         return var8 * var1 + var11;
      }
   }

   public CurveFit getCurveFit() {
      return this.mCurveFit;
   }

   public void setPoint(int var1, float var2, float var3, int var4, float var5) {
      int[] var6 = this.mTimePoints;
      int var7 = this.count;
      var6[var7] = var1;
      float[][] var8 = this.mValues;
      var8[var7][0] = var2;
      var8[var7][1] = var3;
      var8[var7][2] = var5;
      this.mWaveShape = Math.max(this.mWaveShape, var4);
      ++this.count;
   }

   public abstract boolean setProperty(View var1, float var2, long var3, KeyCache var5);

   protected void setStartTime(long var1) {
      this.last_time = var1;
   }

   public void setType(String var1) {
      this.mType = var1;
   }

   public void setup(int var1) {
      int var2 = this.count;
      if (var2 == 0) {
         StringBuilder var10 = new StringBuilder();
         var10.append("Error no points added to ");
         var10.append(this.mType);
         Log.e("SplineSet", var10.toString());
      } else {
         TimeCycleSplineSet.Sort.doubleQuickSort(this.mTimePoints, this.mValues, 0, var2 - 1);
         int var4 = 1;
         var2 = 0;

         while(true) {
            int[] var3 = this.mTimePoints;
            if (var4 >= var3.length) {
               var4 = var2;
               if (var2 == 0) {
                  var4 = 1;
               }

               double[] var9 = new double[var4];
               double[][] var6 = new double[var4][3];
               var2 = 0;

               for(var4 = 0; var2 < this.count; ++var2) {
                  if (var2 > 0) {
                     int[] var7 = this.mTimePoints;
                     if (var7[var2] == var7[var2 - 1]) {
                        continue;
                     }
                  }

                  var9[var4] = (double)this.mTimePoints[var2] * 0.01D;
                  double[] var11 = var6[var4];
                  float[][] var8 = this.mValues;
                  var11[0] = (double)var8[var2][0];
                  var6[var4][1] = (double)var8[var2][1];
                  var6[var4][2] = (double)var8[var2][2];
                  ++var4;
               }

               this.mCurveFit = CurveFit.get(var1, var9, var6);
               return;
            }

            int var5 = var2;
            if (var3[var4] != var3[var4 - 1]) {
               var5 = var2 + 1;
            }

            ++var4;
            var2 = var5;
         }
      }
   }

   public String toString() {
      String var1 = this.mType;
      DecimalFormat var2 = new DecimalFormat("##.##");

      for(int var3 = 0; var3 < this.count; ++var3) {
         StringBuilder var4 = new StringBuilder();
         var4.append(var1);
         var4.append("[");
         var4.append(this.mTimePoints[var3]);
         var4.append(" , ");
         var4.append(var2.format(this.mValues[var3]));
         var4.append("] ");
         var1 = var4.toString();
      }

      return var1;
   }

   static class AlphaSet extends TimeCycleSplineSet {
      public boolean setProperty(View var1, float var2, long var3, KeyCache var5) {
         var1.setAlpha(this.get(var2, var3, var1, var5));
         return this.mContinue;
      }
   }

   static class CustomSet extends TimeCycleSplineSet {
      String mAttributeName;
      float[] mCache;
      SparseArray<ConstraintAttribute> mConstraintAttributeList;
      float[] mTempValues;
      SparseArray<float[]> mWaveProperties = new SparseArray();

      public CustomSet(String var1, SparseArray<ConstraintAttribute> var2) {
         this.mAttributeName = var1.split(",")[1];
         this.mConstraintAttributeList = var2;
      }

      public void setPoint(int var1, float var2, float var3, int var4, float var5) {
         throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
      }

      public void setPoint(int var1, ConstraintAttribute var2, float var3, int var4, float var5) {
         this.mConstraintAttributeList.append(var1, var2);
         this.mWaveProperties.append(var1, new float[]{var3, var5});
         this.mWaveShape = Math.max(this.mWaveShape, var4);
      }

      public boolean setProperty(View var1, float var2, long var3, KeyCache var5) {
         this.mCurveFit.getPos((double)var2, this.mTempValues);
         float[] var13 = this.mTempValues;
         var2 = var13[var13.length - 2];
         float var6 = var13[var13.length - 1];
         long var7 = this.last_time;
         this.last_cycle = (float)(((double)this.last_cycle + (double)(var3 - var7) * 1.0E-9D * (double)var2) % 1.0D);
         this.last_time = var3;
         float var9 = this.calcWave(this.last_cycle);
         this.mContinue = false;

         for(int var10 = 0; var10 < this.mCache.length; ++var10) {
            boolean var11 = this.mContinue;
            boolean var12;
            if ((double)this.mTempValues[var10] != 0.0D) {
               var12 = true;
            } else {
               var12 = false;
            }

            this.mContinue = var11 | var12;
            this.mCache[var10] = this.mTempValues[var10] * var9 + var6;
         }

         ((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0)).setInterpolatedValue(var1, this.mCache);
         if (var2 != 0.0F) {
            this.mContinue = true;
         }

         return this.mContinue;
      }

      public void setup(int var1) {
         int var2 = this.mConstraintAttributeList.size();
         int var3 = ((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0)).noOfInterpValues();
         double[] var4 = new double[var2];
         int var5 = var3 + 2;
         this.mTempValues = new float[var5];
         this.mCache = new float[var3];
         double[][] var6 = new double[var2][var5];

         for(var5 = 0; var5 < var2; ++var5) {
            int var7 = this.mConstraintAttributeList.keyAt(var5);
            ConstraintAttribute var8 = (ConstraintAttribute)this.mConstraintAttributeList.valueAt(var5);
            float[] var9 = (float[])this.mWaveProperties.valueAt(var5);
            var4[var5] = (double)var7 * 0.01D;
            var8.getValuesToInterpolate(this.mTempValues);
            var7 = 0;

            while(true) {
               float[] var10 = this.mTempValues;
               if (var7 >= var10.length) {
                  var6[var5][var3] = (double)var9[0];
                  var6[var5][var3 + 1] = (double)var9[1];
                  break;
               }

               var6[var5][var7] = (double)var10[var7];
               ++var7;
            }
         }

         this.mCurveFit = CurveFit.get(var1, var4, var6);
      }
   }

   static class ElevationSet extends TimeCycleSplineSet {
      public boolean setProperty(View var1, float var2, long var3, KeyCache var5) {
         if (VERSION.SDK_INT >= 21) {
            var1.setElevation(this.get(var2, var3, var1, var5));
         }

         return this.mContinue;
      }
   }

   static class PathRotate extends TimeCycleSplineSet {
      public boolean setPathRotate(View var1, KeyCache var2, float var3, long var4, double var6, double var8) {
         var1.setRotation(this.get(var3, var4, var1, var2) + (float)Math.toDegrees(Math.atan2(var8, var6)));
         return this.mContinue;
      }

      public boolean setProperty(View var1, float var2, long var3, KeyCache var5) {
         return this.mContinue;
      }
   }

   static class ProgressSet extends TimeCycleSplineSet {
      boolean mNoMethod = false;

      public boolean setProperty(View var1, float var2, long var3, KeyCache var5) {
         if (var1 instanceof MotionLayout) {
            ((MotionLayout)var1).setProgress(this.get(var2, var3, var1, var5));
         } else {
            if (this.mNoMethod) {
               return false;
            }

            Method var6 = null;

            label26: {
               Method var7;
               try {
                  var7 = var1.getClass().getMethod("setProgress", Float.TYPE);
               } catch (NoSuchMethodException var10) {
                  this.mNoMethod = true;
                  break label26;
               }

               var6 = var7;
            }

            if (var6 != null) {
               try {
                  var6.invoke(var1, this.get(var2, var3, var1, var5));
               } catch (IllegalAccessException var8) {
                  Log.e("SplineSet", "unable to setProgress", var8);
               } catch (InvocationTargetException var9) {
                  Log.e("SplineSet", "unable to setProgress", var9);
               }
            }
         }

         return this.mContinue;
      }
   }

   static class RotationSet extends TimeCycleSplineSet {
      public boolean setProperty(View var1, float var2, long var3, KeyCache var5) {
         var1.setRotation(this.get(var2, var3, var1, var5));
         return this.mContinue;
      }
   }

   static class RotationXset extends TimeCycleSplineSet {
      public boolean setProperty(View var1, float var2, long var3, KeyCache var5) {
         var1.setRotationX(this.get(var2, var3, var1, var5));
         return this.mContinue;
      }
   }

   static class RotationYset extends TimeCycleSplineSet {
      public boolean setProperty(View var1, float var2, long var3, KeyCache var5) {
         var1.setRotationY(this.get(var2, var3, var1, var5));
         return this.mContinue;
      }
   }

   static class ScaleXset extends TimeCycleSplineSet {
      public boolean setProperty(View var1, float var2, long var3, KeyCache var5) {
         var1.setScaleX(this.get(var2, var3, var1, var5));
         return this.mContinue;
      }
   }

   static class ScaleYset extends TimeCycleSplineSet {
      public boolean setProperty(View var1, float var2, long var3, KeyCache var5) {
         var1.setScaleY(this.get(var2, var3, var1, var5));
         return this.mContinue;
      }
   }

   private static class Sort {
      static void doubleQuickSort(int[] var0, float[][] var1, int var2, int var3) {
         int[] var4 = new int[var0.length + 10];
         var4[0] = var3;
         var4[1] = var2;
         var2 = 2;

         while(var2 > 0) {
            --var2;
            int var5 = var4[var2];
            var3 = var2 - 1;
            int var6 = var4[var3];
            var2 = var3;
            if (var5 < var6) {
               int var7 = partition(var0, var1, var5, var6);
               var2 = var3 + 1;
               var4[var3] = var7 - 1;
               var3 = var2 + 1;
               var4[var2] = var5;
               var5 = var3 + 1;
               var4[var3] = var6;
               var2 = var5 + 1;
               var4[var5] = var7 + 1;
            }
         }

      }

      private static int partition(int[] var0, float[][] var1, int var2, int var3) {
         int var4 = var0[var3];

         int var5;
         int var6;
         for(var5 = var2; var2 < var3; var5 = var6) {
            var6 = var5;
            if (var0[var2] <= var4) {
               swap(var0, var1, var5, var2);
               var6 = var5 + 1;
            }

            ++var2;
         }

         swap(var0, var1, var5, var3);
         return var5;
      }

      private static void swap(int[] var0, float[][] var1, int var2, int var3) {
         int var4 = var0[var2];
         var0[var2] = var0[var3];
         var0[var3] = var4;
         float[] var5 = var1[var2];
         var1[var2] = var1[var3];
         var1[var3] = var5;
      }
   }

   static class TranslationXset extends TimeCycleSplineSet {
      public boolean setProperty(View var1, float var2, long var3, KeyCache var5) {
         var1.setTranslationX(this.get(var2, var3, var1, var5));
         return this.mContinue;
      }
   }

   static class TranslationYset extends TimeCycleSplineSet {
      public boolean setProperty(View var1, float var2, long var3, KeyCache var5) {
         var1.setTranslationY(this.get(var2, var3, var1, var5));
         return this.mContinue;
      }
   }

   static class TranslationZset extends TimeCycleSplineSet {
      public boolean setProperty(View var1, float var2, long var3, KeyCache var5) {
         if (VERSION.SDK_INT >= 21) {
            var1.setTranslationZ(this.get(var2, var3, var1, var5));
         }

         return this.mContinue;
      }
   }
}
