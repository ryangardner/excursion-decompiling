package androidx.constraintlayout.motion.widget;

import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

class MotionConstrainedPoint implements Comparable<MotionConstrainedPoint> {
   static final int CARTESIAN = 2;
   public static final boolean DEBUG = false;
   static final int PERPENDICULAR = 1;
   public static final String TAG = "MotionPaths";
   static String[] names = new String[]{"position", "x", "y", "width", "height", "pathRotate"};
   private float alpha = 1.0F;
   private boolean applyElevation = false;
   LinkedHashMap<String, ConstraintAttribute> attributes = new LinkedHashMap();
   private float elevation = 0.0F;
   private float height;
   private int mDrawPath = 0;
   private Easing mKeyFrameEasing;
   int mMode = 0;
   private float mPathRotate = Float.NaN;
   private float mPivotX = Float.NaN;
   private float mPivotY = Float.NaN;
   private float mProgress = Float.NaN;
   double[] mTempDelta = new double[18];
   double[] mTempValue = new double[18];
   int mVisibilityMode = 0;
   private float position;
   private float rotation = 0.0F;
   private float rotationX = 0.0F;
   public float rotationY = 0.0F;
   private float scaleX = 1.0F;
   private float scaleY = 1.0F;
   private float translationX = 0.0F;
   private float translationY = 0.0F;
   private float translationZ = 0.0F;
   int visibility;
   private float width;
   private float x;
   private float y;

   public MotionConstrainedPoint() {
   }

   private boolean diff(float var1, float var2) {
      boolean var3 = Float.isNaN(var1);
      boolean var4 = true;
      boolean var5 = true;
      if (!var3 && !Float.isNaN(var2)) {
         if (Math.abs(var1 - var2) <= 1.0E-6F) {
            var5 = false;
         }

         return var5;
      } else {
         if (Float.isNaN(var1) != Float.isNaN(var2)) {
            var5 = var4;
         } else {
            var5 = false;
         }

         return var5;
      }
   }

   public void addValues(HashMap<String, SplineSet> var1, int var2) {
      Iterator var3 = var1.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         SplineSet var5 = (SplineSet)var1.get(var4);
         byte var6 = -1;
         switch(var4.hashCode()) {
         case -1249320806:
            if (var4.equals("rotationX")) {
               var6 = 3;
            }
            break;
         case -1249320805:
            if (var4.equals("rotationY")) {
               var6 = 4;
            }
            break;
         case -1225497657:
            if (var4.equals("translationX")) {
               var6 = 11;
            }
            break;
         case -1225497656:
            if (var4.equals("translationY")) {
               var6 = 12;
            }
            break;
         case -1225497655:
            if (var4.equals("translationZ")) {
               var6 = 13;
            }
            break;
         case -1001078227:
            if (var4.equals("progress")) {
               var6 = 8;
            }
            break;
         case -908189618:
            if (var4.equals("scaleX")) {
               var6 = 9;
            }
            break;
         case -908189617:
            if (var4.equals("scaleY")) {
               var6 = 10;
            }
            break;
         case -760884510:
            if (var4.equals("transformPivotX")) {
               var6 = 5;
            }
            break;
         case -760884509:
            if (var4.equals("transformPivotY")) {
               var6 = 6;
            }
            break;
         case -40300674:
            if (var4.equals("rotation")) {
               var6 = 2;
            }
            break;
         case -4379043:
            if (var4.equals("elevation")) {
               var6 = 1;
            }
            break;
         case 37232917:
            if (var4.equals("transitionPathRotate")) {
               var6 = 7;
            }
            break;
         case 92909918:
            if (var4.equals("alpha")) {
               var6 = 0;
            }
         }

         float var7 = 1.0F;
         float var8 = 0.0F;
         float var9 = 0.0F;
         float var10 = 0.0F;
         float var11 = 0.0F;
         float var12 = 0.0F;
         float var13 = 0.0F;
         float var14 = 0.0F;
         float var15 = 0.0F;
         float var16 = 0.0F;
         float var17 = 0.0F;
         float var18 = 0.0F;
         switch(var6) {
         case 0:
            if (!Float.isNaN(this.alpha)) {
               var7 = this.alpha;
            }

            var5.setPoint(var2, var7);
            break;
         case 1:
            if (Float.isNaN(this.elevation)) {
               var7 = var17;
            } else {
               var7 = this.elevation;
            }

            var5.setPoint(var2, var7);
            break;
         case 2:
            if (Float.isNaN(this.rotation)) {
               var7 = var16;
            } else {
               var7 = this.rotation;
            }

            var5.setPoint(var2, var7);
            break;
         case 3:
            if (Float.isNaN(this.rotationX)) {
               var7 = var15;
            } else {
               var7 = this.rotationX;
            }

            var5.setPoint(var2, var7);
            break;
         case 4:
            if (Float.isNaN(this.rotationY)) {
               var7 = var14;
            } else {
               var7 = this.rotationY;
            }

            var5.setPoint(var2, var7);
            break;
         case 5:
            if (Float.isNaN(this.mPivotX)) {
               var7 = var13;
            } else {
               var7 = this.mPivotX;
            }

            var5.setPoint(var2, var7);
            break;
         case 6:
            if (Float.isNaN(this.mPivotY)) {
               var7 = var12;
            } else {
               var7 = this.mPivotY;
            }

            var5.setPoint(var2, var7);
            break;
         case 7:
            if (Float.isNaN(this.mPathRotate)) {
               var7 = var11;
            } else {
               var7 = this.mPathRotate;
            }

            var5.setPoint(var2, var7);
            break;
         case 8:
            if (Float.isNaN(this.mProgress)) {
               var7 = var10;
            } else {
               var7 = this.mProgress;
            }

            var5.setPoint(var2, var7);
            break;
         case 9:
            if (!Float.isNaN(this.scaleX)) {
               var7 = this.scaleX;
            }

            var5.setPoint(var2, var7);
            break;
         case 10:
            if (!Float.isNaN(this.scaleY)) {
               var7 = this.scaleY;
            }

            var5.setPoint(var2, var7);
            break;
         case 11:
            if (Float.isNaN(this.translationX)) {
               var7 = var9;
            } else {
               var7 = this.translationX;
            }

            var5.setPoint(var2, var7);
            break;
         case 12:
            if (Float.isNaN(this.translationY)) {
               var7 = var8;
            } else {
               var7 = this.translationY;
            }

            var5.setPoint(var2, var7);
            break;
         case 13:
            if (Float.isNaN(this.translationZ)) {
               var7 = var18;
            } else {
               var7 = this.translationZ;
            }

            var5.setPoint(var2, var7);
            break;
         default:
            if (var4.startsWith("CUSTOM")) {
               String var19 = var4.split(",")[1];
               if (this.attributes.containsKey(var19)) {
                  ConstraintAttribute var20 = (ConstraintAttribute)this.attributes.get(var19);
                  if (var5 instanceof SplineSet.CustomSet) {
                     ((SplineSet.CustomSet)var5).setPoint(var2, var20);
                  } else {
                     StringBuilder var23 = new StringBuilder();
                     var23.append(var4);
                     var23.append(" splineSet not a CustomSet frame = ");
                     var23.append(var2);
                     var23.append(", value");
                     var23.append(var20.getValueToInterpolate());
                     var23.append(var5);
                     Log.e("MotionPaths", var23.toString());
                  }
               } else {
                  StringBuilder var21 = new StringBuilder();
                  var21.append("UNKNOWN customName ");
                  var21.append(var19);
                  Log.e("MotionPaths", var21.toString());
               }
            } else {
               StringBuilder var22 = new StringBuilder();
               var22.append("UNKNOWN spline ");
               var22.append(var4);
               Log.e("MotionPaths", var22.toString());
            }
         }
      }

   }

   public void applyParameters(View var1) {
      this.visibility = var1.getVisibility();
      float var2;
      if (var1.getVisibility() != 0) {
         var2 = 0.0F;
      } else {
         var2 = var1.getAlpha();
      }

      this.alpha = var2;
      this.applyElevation = false;
      if (VERSION.SDK_INT >= 21) {
         this.elevation = var1.getElevation();
      }

      this.rotation = var1.getRotation();
      this.rotationX = var1.getRotationX();
      this.rotationY = var1.getRotationY();
      this.scaleX = var1.getScaleX();
      this.scaleY = var1.getScaleY();
      this.mPivotX = var1.getPivotX();
      this.mPivotY = var1.getPivotY();
      this.translationX = var1.getTranslationX();
      this.translationY = var1.getTranslationY();
      if (VERSION.SDK_INT >= 21) {
         this.translationZ = var1.getTranslationZ();
      }

   }

   public void applyParameters(ConstraintSet.Constraint var1) {
      this.mVisibilityMode = var1.propertySet.mVisibilityMode;
      this.visibility = var1.propertySet.visibility;
      float var2;
      if (var1.propertySet.visibility != 0 && this.mVisibilityMode == 0) {
         var2 = 0.0F;
      } else {
         var2 = var1.propertySet.alpha;
      }

      this.alpha = var2;
      this.applyElevation = var1.transform.applyElevation;
      this.elevation = var1.transform.elevation;
      this.rotation = var1.transform.rotation;
      this.rotationX = var1.transform.rotationX;
      this.rotationY = var1.transform.rotationY;
      this.scaleX = var1.transform.scaleX;
      this.scaleY = var1.transform.scaleY;
      this.mPivotX = var1.transform.transformPivotX;
      this.mPivotY = var1.transform.transformPivotY;
      this.translationX = var1.transform.translationX;
      this.translationY = var1.transform.translationY;
      this.translationZ = var1.transform.translationZ;
      this.mKeyFrameEasing = Easing.getInterpolator(var1.motion.mTransitionEasing);
      this.mPathRotate = var1.motion.mPathRotate;
      this.mDrawPath = var1.motion.mDrawPath;
      this.mProgress = var1.propertySet.mProgress;
      Iterator var3 = var1.mCustomConstraints.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         ConstraintAttribute var5 = (ConstraintAttribute)var1.mCustomConstraints.get(var4);
         if (var5.getType() != ConstraintAttribute.AttributeType.STRING_TYPE) {
            this.attributes.put(var4, var5);
         }
      }

   }

   public int compareTo(MotionConstrainedPoint var1) {
      return Float.compare(this.position, var1.position);
   }

   void different(MotionConstrainedPoint var1, HashSet<String> var2) {
      if (this.diff(this.alpha, var1.alpha)) {
         var2.add("alpha");
      }

      if (this.diff(this.elevation, var1.elevation)) {
         var2.add("elevation");
      }

      int var3 = this.visibility;
      int var4 = var1.visibility;
      if (var3 != var4 && this.mVisibilityMode == 0 && (var3 == 0 || var4 == 0)) {
         var2.add("alpha");
      }

      if (this.diff(this.rotation, var1.rotation)) {
         var2.add("rotation");
      }

      if (!Float.isNaN(this.mPathRotate) || !Float.isNaN(var1.mPathRotate)) {
         var2.add("transitionPathRotate");
      }

      if (!Float.isNaN(this.mProgress) || !Float.isNaN(var1.mProgress)) {
         var2.add("progress");
      }

      if (this.diff(this.rotationX, var1.rotationX)) {
         var2.add("rotationX");
      }

      if (this.diff(this.rotationY, var1.rotationY)) {
         var2.add("rotationY");
      }

      if (this.diff(this.mPivotX, var1.mPivotX)) {
         var2.add("transformPivotX");
      }

      if (this.diff(this.mPivotY, var1.mPivotY)) {
         var2.add("transformPivotY");
      }

      if (this.diff(this.scaleX, var1.scaleX)) {
         var2.add("scaleX");
      }

      if (this.diff(this.scaleY, var1.scaleY)) {
         var2.add("scaleY");
      }

      if (this.diff(this.translationX, var1.translationX)) {
         var2.add("translationX");
      }

      if (this.diff(this.translationY, var1.translationY)) {
         var2.add("translationY");
      }

      if (this.diff(this.translationZ, var1.translationZ)) {
         var2.add("translationZ");
      }

   }

   void different(MotionConstrainedPoint var1, boolean[] var2, String[] var3) {
      var2[0] |= this.diff(this.position, var1.position);
      var2[1] |= this.diff(this.x, var1.x);
      var2[2] |= this.diff(this.y, var1.y);
      var2[3] |= this.diff(this.width, var1.width);
      boolean var4 = var2[4];
      var2[4] = this.diff(this.height, var1.height) | var4;
   }

   void fillStandard(double[] var1, int[] var2) {
      float var3 = this.position;
      int var4 = 0;
      float var5 = this.x;
      float var6 = this.y;
      float var7 = this.width;
      float var8 = this.height;
      float var9 = this.alpha;
      float var10 = this.elevation;
      float var11 = this.rotation;
      float var12 = this.rotationX;
      float var13 = this.rotationY;
      float var14 = this.scaleX;
      float var15 = this.scaleY;
      float var16 = this.mPivotX;
      float var17 = this.mPivotY;
      float var18 = this.translationX;
      float var19 = this.translationY;
      float var20 = this.translationZ;
      float var21 = this.mPathRotate;

      int var23;
      for(int var22 = 0; var4 < var2.length; var22 = var23) {
         var23 = var22;
         if (var2[var4] < 18) {
            var23 = var2[var4];
            var1[var22] = (double)(new float[]{var3, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20, var21})[var23];
            var23 = var22 + 1;
         }

         ++var4;
      }

   }

   int getCustomData(String var1, double[] var2, int var3) {
      ConstraintAttribute var7 = (ConstraintAttribute)this.attributes.get(var1);
      if (var7.noOfInterpValues() == 1) {
         var2[var3] = (double)var7.getValueToInterpolate();
         return 1;
      } else {
         int var4 = var7.noOfInterpValues();
         float[] var5 = new float[var4];
         var7.getValuesToInterpolate(var5);

         for(int var6 = 0; var6 < var4; ++var3) {
            var2[var3] = (double)var5[var6];
            ++var6;
         }

         return var4;
      }
   }

   int getCustomDataCount(String var1) {
      return ((ConstraintAttribute)this.attributes.get(var1)).noOfInterpValues();
   }

   boolean hasCustomData(String var1) {
      return this.attributes.containsKey(var1);
   }

   void setBounds(float var1, float var2, float var3, float var4) {
      this.x = var1;
      this.y = var2;
      this.width = var3;
      this.height = var4;
   }

   public void setState(View var1) {
      this.setBounds(var1.getX(), var1.getY(), (float)var1.getWidth(), (float)var1.getHeight());
      this.applyParameters(var1);
   }

   public void setState(ConstraintWidget var1, ConstraintSet var2, int var3) {
      this.setBounds((float)var1.getX(), (float)var1.getY(), (float)var1.getWidth(), (float)var1.getHeight());
      this.applyParameters(var2.getParameters(var3));
   }
}
