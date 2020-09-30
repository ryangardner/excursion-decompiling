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
import java.util.Arrays;

public abstract class SplineSet {
   private static final String TAG = "SplineSet";
   private int count;
   protected CurveFit mCurveFit;
   protected int[] mTimePoints = new int[10];
   private String mType;
   protected float[] mValues = new float[10];

   static SplineSet makeCustomSpline(String var0, SparseArray<ConstraintAttribute> var1) {
      return new SplineSet.CustomSet(var0, var1);
   }

   static SplineSet makeSpline(String var0) {
      byte var1;
      label78: {
         switch(var0.hashCode()) {
         case -1249320806:
            if (var0.equals("rotationX")) {
               var1 = 3;
               break label78;
            }
            break;
         case -1249320805:
            if (var0.equals("rotationY")) {
               var1 = 4;
               break label78;
            }
            break;
         case -1225497657:
            if (var0.equals("translationX")) {
               var1 = 12;
               break label78;
            }
            break;
         case -1225497656:
            if (var0.equals("translationY")) {
               var1 = 13;
               break label78;
            }
            break;
         case -1225497655:
            if (var0.equals("translationZ")) {
               var1 = 14;
               break label78;
            }
            break;
         case -1001078227:
            if (var0.equals("progress")) {
               var1 = 15;
               break label78;
            }
            break;
         case -908189618:
            if (var0.equals("scaleX")) {
               var1 = 8;
               break label78;
            }
            break;
         case -908189617:
            if (var0.equals("scaleY")) {
               var1 = 9;
               break label78;
            }
            break;
         case -797520672:
            if (var0.equals("waveVariesBy")) {
               var1 = 11;
               break label78;
            }
            break;
         case -760884510:
            if (var0.equals("transformPivotX")) {
               var1 = 5;
               break label78;
            }
            break;
         case -760884509:
            if (var0.equals("transformPivotY")) {
               var1 = 6;
               break label78;
            }
            break;
         case -40300674:
            if (var0.equals("rotation")) {
               var1 = 2;
               break label78;
            }
            break;
         case -4379043:
            if (var0.equals("elevation")) {
               var1 = 1;
               break label78;
            }
            break;
         case 37232917:
            if (var0.equals("transitionPathRotate")) {
               var1 = 7;
               break label78;
            }
            break;
         case 92909918:
            if (var0.equals("alpha")) {
               var1 = 0;
               break label78;
            }
            break;
         case 156108012:
            if (var0.equals("waveOffset")) {
               var1 = 10;
               break label78;
            }
         }

         var1 = -1;
      }

      switch(var1) {
      case 0:
         return new SplineSet.AlphaSet();
      case 1:
         return new SplineSet.ElevationSet();
      case 2:
         return new SplineSet.RotationSet();
      case 3:
         return new SplineSet.RotationXset();
      case 4:
         return new SplineSet.RotationYset();
      case 5:
         return new SplineSet.PivotXset();
      case 6:
         return new SplineSet.PivotYset();
      case 7:
         return new SplineSet.PathRotate();
      case 8:
         return new SplineSet.ScaleXset();
      case 9:
         return new SplineSet.ScaleYset();
      case 10:
         return new SplineSet.AlphaSet();
      case 11:
         return new SplineSet.AlphaSet();
      case 12:
         return new SplineSet.TranslationXset();
      case 13:
         return new SplineSet.TranslationYset();
      case 14:
         return new SplineSet.TranslationZset();
      case 15:
         return new SplineSet.ProgressSet();
      default:
         return null;
      }
   }

   public float get(float var1) {
      return (float)this.mCurveFit.getPos((double)var1, 0);
   }

   public CurveFit getCurveFit() {
      return this.mCurveFit;
   }

   public float getSlope(float var1) {
      return (float)this.mCurveFit.getSlope((double)var1, 0);
   }

   public void setPoint(int var1, float var2) {
      int[] var3 = this.mTimePoints;
      if (var3.length < this.count + 1) {
         this.mTimePoints = Arrays.copyOf(var3, var3.length * 2);
         float[] var5 = this.mValues;
         this.mValues = Arrays.copyOf(var5, var5.length * 2);
      }

      var3 = this.mTimePoints;
      int var4 = this.count;
      var3[var4] = var1;
      this.mValues[var4] = var2;
      this.count = var4 + 1;
   }

   public abstract void setProperty(View var1, float var2);

   public void setType(String var1) {
      this.mType = var1;
   }

   public void setup(int var1) {
      int var2 = this.count;
      if (var2 != 0) {
         SplineSet.Sort.doubleQuickSort(this.mTimePoints, this.mValues, 0, var2 - 1);
         int var3 = 1;

         int var4;
         int[] var5;
         for(var4 = 1; var3 < this.count; var4 = var2) {
            var5 = this.mTimePoints;
            var2 = var4;
            if (var5[var3 - 1] != var5[var3]) {
               var2 = var4 + 1;
            }

            ++var3;
         }

         double[] var6 = new double[var4];
         double[][] var7 = new double[var4][1];
         var2 = 0;

         for(var3 = 0; var2 < this.count; ++var2) {
            if (var2 > 0) {
               var5 = this.mTimePoints;
               if (var5[var2] == var5[var2 - 1]) {
                  continue;
               }
            }

            var6[var3] = (double)this.mTimePoints[var2] * 0.01D;
            var7[var3][0] = (double)this.mValues[var2];
            ++var3;
         }

         this.mCurveFit = CurveFit.get(var1, var6, var7);
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
         var4.append(var2.format((double)this.mValues[var3]));
         var4.append("] ");
         var1 = var4.toString();
      }

      return var1;
   }

   static class AlphaSet extends SplineSet {
      public void setProperty(View var1, float var2) {
         var1.setAlpha(this.get(var2));
      }
   }

   static class CustomSet extends SplineSet {
      String mAttributeName;
      SparseArray<ConstraintAttribute> mConstraintAttributeList;
      float[] mTempValues;

      public CustomSet(String var1, SparseArray<ConstraintAttribute> var2) {
         this.mAttributeName = var1.split(",")[1];
         this.mConstraintAttributeList = var2;
      }

      public void setPoint(int var1, float var2) {
         throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute)");
      }

      public void setPoint(int var1, ConstraintAttribute var2) {
         this.mConstraintAttributeList.append(var1, var2);
      }

      public void setProperty(View var1, float var2) {
         this.mCurveFit.getPos((double)var2, this.mTempValues);
         ((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0)).setInterpolatedValue(var1, this.mTempValues);
      }

      public void setup(int var1) {
         int var2 = this.mConstraintAttributeList.size();
         int var3 = ((ConstraintAttribute)this.mConstraintAttributeList.valueAt(0)).noOfInterpValues();
         double[] var4 = new double[var2];
         this.mTempValues = new float[var3];
         double[][] var5 = new double[var2][var3];

         for(var3 = 0; var3 < var2; ++var3) {
            int var6 = this.mConstraintAttributeList.keyAt(var3);
            ConstraintAttribute var7 = (ConstraintAttribute)this.mConstraintAttributeList.valueAt(var3);
            var4[var3] = (double)var6 * 0.01D;
            var7.getValuesToInterpolate(this.mTempValues);
            var6 = 0;

            while(true) {
               float[] var8 = this.mTempValues;
               if (var6 >= var8.length) {
                  break;
               }

               var5[var3][var6] = (double)var8[var6];
               ++var6;
            }
         }

         this.mCurveFit = CurveFit.get(var1, var4, var5);
      }
   }

   static class ElevationSet extends SplineSet {
      public void setProperty(View var1, float var2) {
         if (VERSION.SDK_INT >= 21) {
            var1.setElevation(this.get(var2));
         }

      }
   }

   static class PathRotate extends SplineSet {
      public void setPathRotate(View var1, float var2, double var3, double var5) {
         var1.setRotation(this.get(var2) + (float)Math.toDegrees(Math.atan2(var5, var3)));
      }

      public void setProperty(View var1, float var2) {
      }
   }

   static class PivotXset extends SplineSet {
      public void setProperty(View var1, float var2) {
         var1.setPivotX(this.get(var2));
      }
   }

   static class PivotYset extends SplineSet {
      public void setProperty(View var1, float var2) {
         var1.setPivotY(this.get(var2));
      }
   }

   static class ProgressSet extends SplineSet {
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
                  Log.e("SplineSet", "unable to setProgress", var5);
               } catch (InvocationTargetException var6) {
                  Log.e("SplineSet", "unable to setProgress", var6);
               }
            }
         }

      }
   }

   static class RotationSet extends SplineSet {
      public void setProperty(View var1, float var2) {
         var1.setRotation(this.get(var2));
      }
   }

   static class RotationXset extends SplineSet {
      public void setProperty(View var1, float var2) {
         var1.setRotationX(this.get(var2));
      }
   }

   static class RotationYset extends SplineSet {
      public void setProperty(View var1, float var2) {
         var1.setRotationY(this.get(var2));
      }
   }

   static class ScaleXset extends SplineSet {
      public void setProperty(View var1, float var2) {
         var1.setScaleX(this.get(var2));
      }
   }

   static class ScaleYset extends SplineSet {
      public void setProperty(View var1, float var2) {
         var1.setScaleY(this.get(var2));
      }
   }

   private static class Sort {
      static void doubleQuickSort(int[] var0, float[] var1, int var2, int var3) {
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
               int var8 = var3 + 1;
               var4[var3] = var7 - 1;
               var2 = var8 + 1;
               var4[var8] = var5;
               var3 = var2 + 1;
               var4[var2] = var6;
               var2 = var3 + 1;
               var4[var3] = var7 + 1;
            }
         }

      }

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

      private static void swap(int[] var0, float[] var1, int var2, int var3) {
         int var4 = var0[var2];
         var0[var2] = var0[var3];
         var0[var3] = var4;
         float var5 = var1[var2];
         var1[var2] = var1[var3];
         var1[var3] = var5;
      }
   }

   static class TranslationXset extends SplineSet {
      public void setProperty(View var1, float var2) {
         var1.setTranslationX(this.get(var2));
      }
   }

   static class TranslationYset extends SplineSet {
      public void setProperty(View var1, float var2) {
         var1.setTranslationY(this.get(var2));
      }
   }

   static class TranslationZset extends SplineSet {
      public void setProperty(View var1, float var2) {
         if (VERSION.SDK_INT >= 21) {
            var1.setTranslationZ(this.get(var2));
         }

      }
   }
}
