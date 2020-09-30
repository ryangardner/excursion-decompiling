package androidx.constraintlayout.motion.widget;

import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.motion.utils.Oscillator;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public abstract class KeyCycleOscillator {
   private static final String TAG = "KeyCycleOscillator";
   private CurveFit mCurveFit;
   protected ConstraintAttribute mCustom;
   private KeyCycleOscillator.CycleOscillator mCycleOscillator;
   private String mType;
   public int mVariesBy = 0;
   ArrayList<KeyCycleOscillator.WavePoint> mWavePoints = new ArrayList();
   private int mWaveShape = 0;

   static KeyCycleOscillator makeSpline(String var0) {
      if (var0.startsWith("CUSTOM")) {
         return new KeyCycleOscillator.CustomSet();
      } else {
         byte var1 = -1;
         switch(var0.hashCode()) {
         case -1249320806:
            if (var0.equals("rotationX")) {
               var1 = 3;
            }
            break;
         case -1249320805:
            if (var0.equals("rotationY")) {
               var1 = 4;
            }
            break;
         case -1225497657:
            if (var0.equals("translationX")) {
               var1 = 10;
            }
            break;
         case -1225497656:
            if (var0.equals("translationY")) {
               var1 = 11;
            }
            break;
         case -1225497655:
            if (var0.equals("translationZ")) {
               var1 = 12;
            }
            break;
         case -1001078227:
            if (var0.equals("progress")) {
               var1 = 13;
            }
            break;
         case -908189618:
            if (var0.equals("scaleX")) {
               var1 = 6;
            }
            break;
         case -908189617:
            if (var0.equals("scaleY")) {
               var1 = 7;
            }
            break;
         case -797520672:
            if (var0.equals("waveVariesBy")) {
               var1 = 9;
            }
            break;
         case -40300674:
            if (var0.equals("rotation")) {
               var1 = 2;
            }
            break;
         case -4379043:
            if (var0.equals("elevation")) {
               var1 = 1;
            }
            break;
         case 37232917:
            if (var0.equals("transitionPathRotate")) {
               var1 = 5;
            }
            break;
         case 92909918:
            if (var0.equals("alpha")) {
               var1 = 0;
            }
            break;
         case 156108012:
            if (var0.equals("waveOffset")) {
               var1 = 8;
            }
         }

         switch(var1) {
         case 0:
            return new KeyCycleOscillator.AlphaSet();
         case 1:
            return new KeyCycleOscillator.ElevationSet();
         case 2:
            return new KeyCycleOscillator.RotationSet();
         case 3:
            return new KeyCycleOscillator.RotationXset();
         case 4:
            return new KeyCycleOscillator.RotationYset();
         case 5:
            return new KeyCycleOscillator.PathRotateSet();
         case 6:
            return new KeyCycleOscillator.ScaleXset();
         case 7:
            return new KeyCycleOscillator.ScaleYset();
         case 8:
            return new KeyCycleOscillator.AlphaSet();
         case 9:
            return new KeyCycleOscillator.AlphaSet();
         case 10:
            return new KeyCycleOscillator.TranslationXset();
         case 11:
            return new KeyCycleOscillator.TranslationYset();
         case 12:
            return new KeyCycleOscillator.TranslationZset();
         case 13:
            return new KeyCycleOscillator.ProgressSet();
         default:
            return null;
         }
      }
   }

   public float get(float var1) {
      return (float)this.mCycleOscillator.getValues(var1);
   }

   public CurveFit getCurveFit() {
      return this.mCurveFit;
   }

   public float getSlope(float var1) {
      return (float)this.mCycleOscillator.getSlope(var1);
   }

   public void setPoint(int var1, int var2, int var3, float var4, float var5, float var6) {
      this.mWavePoints.add(new KeyCycleOscillator.WavePoint(var1, var4, var5, var6));
      if (var3 != -1) {
         this.mVariesBy = var3;
      }

      this.mWaveShape = var2;
   }

   public void setPoint(int var1, int var2, int var3, float var4, float var5, float var6, ConstraintAttribute var7) {
      this.mWavePoints.add(new KeyCycleOscillator.WavePoint(var1, var4, var5, var6));
      if (var3 != -1) {
         this.mVariesBy = var3;
      }

      this.mWaveShape = var2;
      this.mCustom = var7;
   }

   public abstract void setProperty(View var1, float var2);

   public void setType(String var1) {
      this.mType = var1;
   }

   public void setup(float var1) {
      int var2 = this.mWavePoints.size();
      if (var2 != 0) {
         Collections.sort(this.mWavePoints, new Comparator<KeyCycleOscillator.WavePoint>() {
            public int compare(KeyCycleOscillator.WavePoint var1, KeyCycleOscillator.WavePoint var2) {
               return Integer.compare(var1.mPosition, var2.mPosition);
            }
         });
         double[] var3 = new double[var2];
         double[][] var4 = new double[var2][2];
         this.mCycleOscillator = new KeyCycleOscillator.CycleOscillator(this.mWaveShape, this.mVariesBy, var2);
         Iterator var5 = this.mWavePoints.iterator();

         for(var2 = 0; var5.hasNext(); ++var2) {
            KeyCycleOscillator.WavePoint var6 = (KeyCycleOscillator.WavePoint)var5.next();
            var3[var2] = (double)var6.mPeriod * 0.01D;
            var4[var2][0] = (double)var6.mValue;
            var4[var2][1] = (double)var6.mOffset;
            this.mCycleOscillator.setPoint(var2, var6.mPosition, var6.mPeriod, var6.mOffset, var6.mValue);
         }

         this.mCycleOscillator.setup(var1);
         this.mCurveFit = CurveFit.get(0, var3, var4);
      }
   }

   public String toString() {
      String var1 = this.mType;
      DecimalFormat var2 = new DecimalFormat("##.##");

      StringBuilder var5;
      for(Iterator var3 = this.mWavePoints.iterator(); var3.hasNext(); var1 = var5.toString()) {
         KeyCycleOscillator.WavePoint var4 = (KeyCycleOscillator.WavePoint)var3.next();
         var5 = new StringBuilder();
         var5.append(var1);
         var5.append("[");
         var5.append(var4.mPosition);
         var5.append(" , ");
         var5.append(var2.format((double)var4.mValue));
         var5.append("] ");
      }

      return var1;
   }

   public boolean variesByPath() {
      int var1 = this.mVariesBy;
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   static class AlphaSet extends KeyCycleOscillator {
      public void setProperty(View var1, float var2) {
         var1.setAlpha(this.get(var2));
      }
   }

   static class CustomSet extends KeyCycleOscillator {
      float[] value = new float[1];

      public void setProperty(View var1, float var2) {
         this.value[0] = this.get(var2);
         this.mCustom.setInterpolatedValue(var1, this.value);
      }
   }

   static class CycleOscillator {
      private static final String TAG = "CycleOscillator";
      static final int UNSET = -1;
      CurveFit mCurveFit;
      public HashMap<String, ConstraintAttribute> mCustomConstraints = new HashMap();
      float[] mOffset;
      Oscillator mOscillator = new Oscillator();
      float mPathLength;
      float[] mPeriod;
      double[] mPosition;
      float[] mScale;
      double[] mSplineSlopeCache;
      double[] mSplineValueCache;
      float[] mValues;
      private final int mVariesBy;
      int mWaveShape;

      CycleOscillator(int var1, int var2, int var3) {
         this.mWaveShape = var1;
         this.mVariesBy = var2;
         this.mOscillator.setType(var1);
         this.mValues = new float[var3];
         this.mPosition = new double[var3];
         this.mPeriod = new float[var3];
         this.mOffset = new float[var3];
         this.mScale = new float[var3];
      }

      private ConstraintAttribute get(String var1, ConstraintAttribute.AttributeType var2) {
         ConstraintAttribute var3;
         if (this.mCustomConstraints.containsKey(var1)) {
            var3 = (ConstraintAttribute)this.mCustomConstraints.get(var1);
            if (var3.getType() != var2) {
               StringBuilder var4 = new StringBuilder();
               var4.append("ConstraintAttribute is already a ");
               var4.append(var3.getType().name());
               throw new IllegalArgumentException(var4.toString());
            }
         } else {
            ConstraintAttribute var5 = new ConstraintAttribute(var1, var2);
            this.mCustomConstraints.put(var1, var5);
            var3 = var5;
         }

         return var3;
      }

      public double getSlope(float var1) {
         CurveFit var2 = this.mCurveFit;
         double var3;
         double[] var7;
         if (var2 != null) {
            var3 = (double)var1;
            var2.getSlope(var3, this.mSplineSlopeCache);
            this.mCurveFit.getPos(var3, this.mSplineValueCache);
         } else {
            var7 = this.mSplineSlopeCache;
            var7[0] = 0.0D;
            var7[1] = 0.0D;
         }

         Oscillator var8 = this.mOscillator;
         double var5 = (double)var1;
         var3 = var8.getValue(var5);
         var5 = this.mOscillator.getSlope(var5);
         var7 = this.mSplineSlopeCache;
         return var7[0] + var3 * var7[1] + var5 * this.mSplineValueCache[1];
      }

      public double getValues(float var1) {
         CurveFit var2 = this.mCurveFit;
         if (var2 != null) {
            var2.getPos((double)var1, this.mSplineValueCache);
         } else {
            double[] var3 = this.mSplineValueCache;
            var3[0] = (double)this.mOffset[0];
            var3[1] = (double)this.mValues[0];
         }

         return this.mSplineValueCache[0] + this.mOscillator.getValue((double)var1) * this.mSplineValueCache[1];
      }

      public void setPoint(int var1, int var2, float var3, float var4, float var5) {
         this.mPosition[var1] = (double)var2 / 100.0D;
         this.mPeriod[var1] = var3;
         this.mOffset[var1] = var4;
         this.mValues[var1] = var5;
      }

      public void setup(float var1) {
         this.mPathLength = var1;
         double[][] var2 = new double[this.mPosition.length][2];
         float[] var3 = this.mValues;
         this.mSplineValueCache = new double[var3.length + 1];
         this.mSplineSlopeCache = new double[var3.length + 1];
         if (this.mPosition[0] > 0.0D) {
            this.mOscillator.addPoint(0.0D, this.mPeriod[0]);
         }

         double[] var6 = this.mPosition;
         int var4 = var6.length - 1;
         if (var6[var4] < 1.0D) {
            this.mOscillator.addPoint(1.0D, this.mPeriod[var4]);
         }

         for(var4 = 0; var4 < var2.length; ++var4) {
            var2[var4][0] = (double)this.mOffset[var4];
            int var5 = 0;

            while(true) {
               var3 = this.mValues;
               if (var5 >= var3.length) {
                  this.mOscillator.addPoint(this.mPosition[var4], this.mPeriod[var4]);
                  break;
               }

               var2[var5][1] = (double)var3[var5];
               ++var5;
            }
         }

         this.mOscillator.normalize();
         var6 = this.mPosition;
         if (var6.length > 1) {
            this.mCurveFit = CurveFit.get(0, var6, var2);
         } else {
            this.mCurveFit = null;
         }

      }
   }

   static class ElevationSet extends KeyCycleOscillator {
      public void setProperty(View var1, float var2) {
         if (VERSION.SDK_INT >= 21) {
            var1.setElevation(this.get(var2));
         }

      }
   }

   private static class IntDoubleSort {
      private static int partition(int[] var0, float[] var1, int var2, int var3) {
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

      static void sort(int[] var0, float[] var1, int var2, int var3) {
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

      private static void swap(int[] var0, float[] var1, int var2, int var3) {
         int var4 = var0[var2];
         var0[var2] = var0[var3];
         var0[var3] = var4;
         float var5 = var1[var2];
         var1[var2] = var1[var3];
         var1[var3] = var5;
      }
   }

   private static class IntFloatFloatSort {
      private static int partition(int[] var0, float[] var1, float[] var2, int var3, int var4) {
         int var5 = var0[var4];

         int var6;
         int var7;
         for(var6 = var3; var3 < var4; var6 = var7) {
            var7 = var6;
            if (var0[var3] <= var5) {
               swap(var0, var1, var2, var6, var3);
               var7 = var6 + 1;
            }

            ++var3;
         }

         swap(var0, var1, var2, var6, var4);
         return var6;
      }

      static void sort(int[] var0, float[] var1, float[] var2, int var3, int var4) {
         int[] var5 = new int[var0.length + 10];
         var5[0] = var4;
         var5[1] = var3;
         var3 = 2;

         while(var3 > 0) {
            --var3;
            int var6 = var5[var3];
            var4 = var3 - 1;
            int var7 = var5[var4];
            var3 = var4;
            if (var6 < var7) {
               int var8 = partition(var0, var1, var2, var6, var7);
               var3 = var4 + 1;
               var5[var4] = var8 - 1;
               var4 = var3 + 1;
               var5[var3] = var6;
               var6 = var4 + 1;
               var5[var4] = var7;
               var3 = var6 + 1;
               var5[var6] = var8 + 1;
            }
         }

      }

      private static void swap(int[] var0, float[] var1, float[] var2, int var3, int var4) {
         int var5 = var0[var3];
         var0[var3] = var0[var4];
         var0[var4] = var5;
         float var6 = var1[var3];
         var1[var3] = var1[var4];
         var1[var4] = var6;
         var6 = var2[var3];
         var2[var3] = var2[var4];
         var2[var4] = var6;
      }
   }

   static class PathRotateSet extends KeyCycleOscillator {
      public void setPathRotate(View var1, float var2, double var3, double var5) {
         var1.setRotation(this.get(var2) + (float)Math.toDegrees(Math.atan2(var5, var3)));
      }

      public void setProperty(View var1, float var2) {
      }
   }

   static class ProgressSet extends KeyCycleOscillator {
      boolean mNoMethod = false;

      public void setProperty(View var1, float var2) {
         if (var1 instanceof MotionLayout) {
            ((MotionLayout)var1).setProgress(this.get(var2));
         } else {
            if (this.mNoMethod) {
               return;
            }

            Method var3 = null;

            label26: {
               Method var4;
               try {
                  var4 = var1.getClass().getMethod("setProgress", Float.TYPE);
               } catch (NoSuchMethodException var7) {
                  this.mNoMethod = true;
                  break label26;
               }

               var3 = var4;
            }

            if (var3 != null) {
               try {
                  var3.invoke(var1, this.get(var2));
               } catch (IllegalAccessException var5) {
                  Log.e("KeyCycleOscillator", "unable to setProgress", var5);
               } catch (InvocationTargetException var6) {
                  Log.e("KeyCycleOscillator", "unable to setProgress", var6);
               }
            }
         }

      }
   }

   static class RotationSet extends KeyCycleOscillator {
      public void setProperty(View var1, float var2) {
         var1.setRotation(this.get(var2));
      }
   }

   static class RotationXset extends KeyCycleOscillator {
      public void setProperty(View var1, float var2) {
         var1.setRotationX(this.get(var2));
      }
   }

   static class RotationYset extends KeyCycleOscillator {
      public void setProperty(View var1, float var2) {
         var1.setRotationY(this.get(var2));
      }
   }

   static class ScaleXset extends KeyCycleOscillator {
      public void setProperty(View var1, float var2) {
         var1.setScaleX(this.get(var2));
      }
   }

   static class ScaleYset extends KeyCycleOscillator {
      public void setProperty(View var1, float var2) {
         var1.setScaleY(this.get(var2));
      }
   }

   static class TranslationXset extends KeyCycleOscillator {
      public void setProperty(View var1, float var2) {
         var1.setTranslationX(this.get(var2));
      }
   }

   static class TranslationYset extends KeyCycleOscillator {
      public void setProperty(View var1, float var2) {
         var1.setTranslationY(this.get(var2));
      }
   }

   static class TranslationZset extends KeyCycleOscillator {
      public void setProperty(View var1, float var2) {
         if (VERSION.SDK_INT >= 21) {
            var1.setTranslationZ(this.get(var2));
         }

      }
   }

   static class WavePoint {
      float mOffset;
      float mPeriod;
      int mPosition;
      float mValue;

      public WavePoint(int var1, float var2, float var3, float var4) {
         this.mPosition = var1;
         this.mValue = var4;
         this.mOffset = var3;
         this.mPeriod = var2;
      }
   }
}
