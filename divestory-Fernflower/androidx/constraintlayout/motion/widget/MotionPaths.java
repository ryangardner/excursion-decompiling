package androidx.constraintlayout.motion.widget;

import android.view.View;
import android.view.View.MeasureSpec;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;

class MotionPaths implements Comparable<MotionPaths> {
   static final int CARTESIAN = 2;
   public static final boolean DEBUG = false;
   static final int OFF_HEIGHT = 4;
   static final int OFF_PATH_ROTATE = 5;
   static final int OFF_POSITION = 0;
   static final int OFF_WIDTH = 3;
   static final int OFF_X = 1;
   static final int OFF_Y = 2;
   public static final boolean OLD_WAY = false;
   static final int PERPENDICULAR = 1;
   static final int SCREEN = 3;
   public static final String TAG = "MotionPaths";
   static String[] names = new String[]{"position", "x", "y", "width", "height", "pathRotate"};
   LinkedHashMap<String, ConstraintAttribute> attributes;
   float height;
   int mDrawPath = 0;
   Easing mKeyFrameEasing;
   int mMode;
   int mPathMotionArc;
   float mPathRotate = Float.NaN;
   float mProgress = Float.NaN;
   double[] mTempDelta;
   double[] mTempValue;
   float position;
   float time;
   float width;
   float x;
   float y;

   public MotionPaths() {
      this.mPathMotionArc = Key.UNSET;
      this.attributes = new LinkedHashMap();
      this.mMode = 0;
      this.mTempValue = new double[18];
      this.mTempDelta = new double[18];
   }

   public MotionPaths(int var1, int var2, KeyPosition var3, MotionPaths var4, MotionPaths var5) {
      this.mPathMotionArc = Key.UNSET;
      this.attributes = new LinkedHashMap();
      this.mMode = 0;
      this.mTempValue = new double[18];
      this.mTempDelta = new double[18];
      int var6 = var3.mPositionType;
      if (var6 != 1) {
         if (var6 != 2) {
            this.initCartesian(var3, var4, var5);
         } else {
            this.initScreen(var1, var2, var3, var4, var5);
         }
      } else {
         this.initPath(var3, var4, var5);
      }
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

   private static final float xRotate(float var0, float var1, float var2, float var3, float var4, float var5) {
      return (var4 - var2) * var1 - (var5 - var3) * var0 + var2;
   }

   private static final float yRotate(float var0, float var1, float var2, float var3, float var4, float var5) {
      return (var4 - var2) * var0 + (var5 - var3) * var1 + var3;
   }

   public void applyParameters(ConstraintSet.Constraint var1) {
      this.mKeyFrameEasing = Easing.getInterpolator(var1.motion.mTransitionEasing);
      this.mPathMotionArc = var1.motion.mPathMotionArc;
      this.mPathRotate = var1.motion.mPathRotate;
      this.mDrawPath = var1.motion.mDrawPath;
      this.mProgress = var1.propertySet.mProgress;
      Iterator var2 = var1.mCustomConstraints.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         ConstraintAttribute var4 = (ConstraintAttribute)var1.mCustomConstraints.get(var3);
         if (var4.getType() != ConstraintAttribute.AttributeType.STRING_TYPE) {
            this.attributes.put(var3, var4);
         }
      }

   }

   public int compareTo(MotionPaths var1) {
      return Float.compare(this.position, var1.position);
   }

   void different(MotionPaths var1, boolean[] var2, String[] var3, boolean var4) {
      var2[0] |= this.diff(this.position, var1.position);
      var2[1] |= this.diff(this.x, var1.x) | var4;
      boolean var5 = var2[2];
      var2[2] = var4 | this.diff(this.y, var1.y) | var5;
      var2[3] |= this.diff(this.width, var1.width);
      var4 = var2[4];
      var2[4] = this.diff(this.height, var1.height) | var4;
   }

   void fillStandard(double[] var1, int[] var2) {
      float var3 = this.position;
      int var4 = 0;
      float var5 = this.x;
      float var6 = this.y;
      float var7 = this.width;
      float var8 = this.height;
      float var9 = this.mPathRotate;

      int var11;
      for(int var10 = 0; var4 < var2.length; var10 = var11) {
         var11 = var10;
         if (var2[var4] < 6) {
            var11 = var2[var4];
            var1[var10] = (double)(new float[]{var3, var5, var6, var7, var8, var9})[var11];
            var11 = var10 + 1;
         }

         ++var4;
      }

   }

   void getBounds(int[] var1, double[] var2, float[] var3, int var4) {
      float var5 = this.width;
      float var6 = this.height;

      for(int var7 = 0; var7 < var1.length; ++var7) {
         float var8 = (float)var2[var7];
         int var9 = var1[var7];
         if (var9 != 3) {
            if (var9 == 4) {
               var6 = var8;
            }
         } else {
            var5 = var8;
         }
      }

      var3[var4] = var5;
      var3[var4 + 1] = var6;
   }

   void getCenter(int[] var1, double[] var2, float[] var3, int var4) {
      float var5 = this.x;
      float var6 = this.y;
      float var7 = this.width;
      float var8 = this.height;

      for(int var9 = 0; var9 < var1.length; ++var9) {
         float var10 = (float)var2[var9];
         int var11 = var1[var9];
         if (var11 != 1) {
            if (var11 != 2) {
               if (var11 != 3) {
                  if (var11 == 4) {
                     var8 = var10;
                  }
               } else {
                  var7 = var10;
               }
            } else {
               var6 = var10;
            }
         } else {
            var5 = var10;
         }
      }

      var3[var4] = var5 + var7 / 2.0F + 0.0F;
      var3[var4 + 1] = var6 + var8 / 2.0F + 0.0F;
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

   void getRect(int[] var1, double[] var2, float[] var3, int var4) {
      float var5 = this.x;
      float var6 = this.y;
      float var7 = this.width;
      float var8 = this.height;

      int var9;
      float var10;
      for(var9 = 0; var9 < var1.length; ++var9) {
         var10 = (float)var2[var9];
         int var11 = var1[var9];
         if (var11 != 1) {
            if (var11 != 2) {
               if (var11 != 3) {
                  if (var11 == 4) {
                     var8 = var10;
                  }
               } else {
                  var7 = var10;
               }
            } else {
               var6 = var10;
            }
         } else {
            var5 = var10;
         }
      }

      var10 = var7 + var5;
      var8 += var6;
      Float.isNaN(Float.NaN);
      Float.isNaN(Float.NaN);
      var9 = var4 + 1;
      var3[var4] = var5 + 0.0F;
      var4 = var9 + 1;
      var3[var9] = var6 + 0.0F;
      var9 = var4 + 1;
      var3[var4] = var10 + 0.0F;
      var4 = var9 + 1;
      var3[var9] = var6 + 0.0F;
      var9 = var4 + 1;
      var3[var4] = var10 + 0.0F;
      var4 = var9 + 1;
      var3[var9] = var8 + 0.0F;
      var3[var4] = var5 + 0.0F;
      var3[var4 + 1] = var8 + 0.0F;
   }

   boolean hasCustomData(String var1) {
      return this.attributes.containsKey(var1);
   }

   void initCartesian(KeyPosition var1, MotionPaths var2, MotionPaths var3) {
      float var4 = (float)var1.mFramePosition / 100.0F;
      this.time = var4;
      this.mDrawPath = var1.mDrawPath;
      float var5;
      if (Float.isNaN(var1.mPercentWidth)) {
         var5 = var4;
      } else {
         var5 = var1.mPercentWidth;
      }

      float var6;
      if (Float.isNaN(var1.mPercentHeight)) {
         var6 = var4;
      } else {
         var6 = var1.mPercentHeight;
      }

      float var7 = var3.width;
      float var8 = var2.width;
      float var9 = var3.height;
      float var10 = var2.height;
      this.position = this.time;
      float var11 = var2.x;
      float var12 = var8 / 2.0F;
      float var13 = var2.y;
      float var14 = var10 / 2.0F;
      float var15 = var3.x;
      float var16 = var7 / 2.0F;
      float var17 = var3.y;
      float var18 = var9 / 2.0F;
      var12 = var15 + var16 - (var12 + var11);
      var18 = var17 + var18 - (var13 + var14);
      var5 = (var7 - var8) * var5;
      var7 = var5 / 2.0F;
      this.x = (float)((int)(var11 + var12 * var4 - var7));
      var6 = (var9 - var10) * var6;
      var9 = var6 / 2.0F;
      this.y = (float)((int)(var13 + var18 * var4 - var9));
      this.width = (float)((int)(var8 + var5));
      this.height = (float)((int)(var10 + var6));
      if (Float.isNaN(var1.mPercentX)) {
         var5 = var4;
      } else {
         var5 = var1.mPercentX;
      }

      boolean var19 = Float.isNaN(var1.mAltPercentY);
      var10 = 0.0F;
      if (var19) {
         var6 = 0.0F;
      } else {
         var6 = var1.mAltPercentY;
      }

      if (!Float.isNaN(var1.mPercentY)) {
         var4 = var1.mPercentY;
      }

      if (!Float.isNaN(var1.mAltPercentX)) {
         var10 = var1.mAltPercentX;
      }

      this.mMode = 2;
      this.x = (float)((int)(var2.x + var5 * var12 + var10 * var18 - var7));
      this.y = (float)((int)(var2.y + var12 * var6 + var18 * var4 - var9));
      this.mKeyFrameEasing = Easing.getInterpolator(var1.mTransitionEasing);
      this.mPathMotionArc = var1.mPathMotionArc;
   }

   void initPath(KeyPosition var1, MotionPaths var2, MotionPaths var3) {
      float var4 = (float)var1.mFramePosition / 100.0F;
      this.time = var4;
      this.mDrawPath = var1.mDrawPath;
      float var5;
      if (Float.isNaN(var1.mPercentWidth)) {
         var5 = var4;
      } else {
         var5 = var1.mPercentWidth;
      }

      float var6;
      if (Float.isNaN(var1.mPercentHeight)) {
         var6 = var4;
      } else {
         var6 = var1.mPercentHeight;
      }

      float var7 = var3.width;
      float var8 = var2.width;
      float var9 = var3.height;
      float var10 = var2.height;
      this.position = this.time;
      if (!Float.isNaN(var1.mPercentX)) {
         var4 = var1.mPercentX;
      }

      float var11 = var2.x;
      float var12 = var2.width;
      float var13 = var12 / 2.0F;
      float var14 = var2.y;
      float var15 = var2.height;
      float var16 = var15 / 2.0F;
      float var17 = var3.x;
      float var18 = var3.width / 2.0F;
      float var19 = var3.y;
      float var20 = var3.height / 2.0F;
      var17 = var17 + var18 - (var13 + var11);
      var19 = var19 + var20 - (var16 + var14);
      var16 = var17 * var4;
      var7 = (var7 - var8) * var5;
      var5 = var7 / 2.0F;
      this.x = (float)((int)(var11 + var16 - var5));
      var11 = var4 * var19;
      var4 = (var9 - var10) * var6;
      var6 = var4 / 2.0F;
      this.y = (float)((int)(var14 + var11 - var6));
      this.width = (float)((int)(var12 + var7));
      this.height = (float)((int)(var15 + var4));
      if (Float.isNaN(var1.mPercentY)) {
         var4 = 0.0F;
      } else {
         var4 = var1.mPercentY;
      }

      var9 = -var19;
      this.mMode = 1;
      var5 = (float)((int)(var2.x + var16 - var5));
      this.x = var5;
      var6 = (float)((int)(var2.y + var11 - var6));
      this.y = var6;
      this.x = var5 + var9 * var4;
      this.y = var6 + var17 * var4;
      this.mKeyFrameEasing = Easing.getInterpolator(var1.mTransitionEasing);
      this.mPathMotionArc = var1.mPathMotionArc;
   }

   void initScreen(int var1, int var2, KeyPosition var3, MotionPaths var4, MotionPaths var5) {
      float var6 = (float)var3.mFramePosition / 100.0F;
      this.time = var6;
      this.mDrawPath = var3.mDrawPath;
      float var7;
      if (Float.isNaN(var3.mPercentWidth)) {
         var7 = var6;
      } else {
         var7 = var3.mPercentWidth;
      }

      float var8;
      if (Float.isNaN(var3.mPercentHeight)) {
         var8 = var6;
      } else {
         var8 = var3.mPercentHeight;
      }

      float var9 = var5.width;
      float var10 = var4.width;
      float var11 = var5.height;
      float var12 = var4.height;
      this.position = this.time;
      float var13 = var4.x;
      float var14 = var10 / 2.0F;
      float var15 = var4.y;
      float var16 = var12 / 2.0F;
      float var17 = var5.x;
      float var18 = var9 / 2.0F;
      float var19 = var5.y;
      float var20 = var11 / 2.0F;
      var7 = (var9 - var10) * var7;
      this.x = (float)((int)(var13 + (var17 + var18 - (var14 + var13)) * var6 - var7 / 2.0F));
      var8 = (var11 - var12) * var8;
      this.y = (float)((int)(var15 + (var19 + var20 - (var15 + var16)) * var6 - var8 / 2.0F));
      this.width = (float)((int)(var10 + var7));
      this.height = (float)((int)(var12 + var8));
      this.mMode = 3;
      if (!Float.isNaN(var3.mPercentX)) {
         var1 = (int)((float)var1 - this.width);
         this.x = (float)((int)(var3.mPercentX * (float)var1));
      }

      if (!Float.isNaN(var3.mPercentY)) {
         var1 = (int)((float)var2 - this.height);
         this.y = (float)((int)(var3.mPercentY * (float)var1));
      }

      this.mKeyFrameEasing = Easing.getInterpolator(var3.mTransitionEasing);
      this.mPathMotionArc = var3.mPathMotionArc;
   }

   void setBounds(float var1, float var2, float var3, float var4) {
      this.x = var1;
      this.y = var2;
      this.width = var3;
      this.height = var4;
   }

   void setDpDt(float var1, float var2, float[] var3, int[] var4, double[] var5, double[] var6) {
      int var7 = 0;
      float var8 = 0.0F;
      float var9 = 0.0F;
      float var10 = 0.0F;

      float var11;
      float var12;
      for(var11 = 0.0F; var7 < var4.length; ++var7) {
         var12 = (float)var5[var7];
         double var10000 = var6[var7];
         int var15 = var4[var7];
         if (var15 != 1) {
            if (var15 != 2) {
               if (var15 != 3) {
                  if (var15 == 4) {
                     var11 = var12;
                  }
               } else {
                  var9 = var12;
               }
            } else {
               var10 = var12;
            }
         } else {
            var8 = var12;
         }
      }

      var12 = var8 - 0.0F * var9 / 2.0F;
      var10 -= 0.0F * var11 / 2.0F;
      var3[0] = var12 * (1.0F - var1) + (var9 * 1.0F + var12) * var1 + 0.0F;
      var3[1] = var10 * (1.0F - var2) + (var11 * 1.0F + var10) * var2 + 0.0F;
   }

   void setView(View var1, int[] var2, double[] var3, double[] var4, double[] var5) {
      float var6 = this.x;
      float var7 = this.y;
      float var8 = this.width;
      float var9 = this.height;
      int var10;
      if (var2.length != 0 && this.mTempValue.length <= var2[var2.length - 1]) {
         var10 = var2[var2.length - 1] + 1;
         this.mTempValue = new double[var10];
         this.mTempDelta = new double[var10];
      }

      Arrays.fill(this.mTempValue, Double.NaN);

      for(var10 = 0; var10 < var2.length; ++var10) {
         this.mTempValue[var2[var10]] = var3[var10];
         this.mTempDelta[var2[var10]] = var4[var10];
      }

      var10 = 0;
      float var11 = Float.NaN;
      float var12 = 0.0F;
      float var13 = 0.0F;
      float var14 = 0.0F;
      float var15 = 0.0F;

      while(true) {
         double[] var27 = this.mTempValue;
         float var19;
         float var20;
         if (var10 >= var27.length) {
            if (Float.isNaN(var11)) {
               if (!Float.isNaN(Float.NaN)) {
                  var1.setRotation(Float.NaN);
               }
            } else {
               var19 = Float.NaN;
               if (Float.isNaN(Float.NaN)) {
                  var19 = 0.0F;
               }

               var20 = var13 / 2.0F;
               var15 /= 2.0F;
               var1.setRotation((float)((double)var19 + (double)var11 + Math.toDegrees(Math.atan2((double)(var14 + var15), (double)(var12 + var20)))));
            }

            var19 = var6 + 0.5F;
            int var21 = (int)var19;
            var6 = var7 + 0.5F;
            int var22 = (int)var6;
            int var23 = (int)(var19 + var8);
            int var24 = (int)(var6 + var9);
            int var25 = var23 - var21;
            int var26 = var24 - var22;
            boolean var28;
            if (var25 == var1.getMeasuredWidth() && var26 == var1.getMeasuredHeight()) {
               var28 = false;
            } else {
               var28 = true;
            }

            if (var28) {
               var1.measure(MeasureSpec.makeMeasureSpec(var25, 1073741824), MeasureSpec.makeMeasureSpec(var26, 1073741824));
            }

            var1.layout(var21, var22, var23, var24);
            return;
         }

         boolean var16 = Double.isNaN(var27[var10]);
         double var17 = 0.0D;
         if (!var16 || var5 != null && var5[var10] != 0.0D) {
            if (var5 != null) {
               var17 = var5[var10];
            }

            if (!Double.isNaN(this.mTempValue[var10])) {
               var17 += this.mTempValue[var10];
            }

            var19 = (float)var17;
            var20 = (float)this.mTempDelta[var10];
            if (var10 != 1) {
               if (var10 != 2) {
                  if (var10 != 3) {
                     if (var10 != 4) {
                        if (var10 == 5) {
                           var11 = var19;
                        }
                     } else {
                        var9 = var19;
                        var15 = var20;
                     }
                  } else {
                     var8 = var19;
                     var13 = var20;
                  }
               } else {
                  var7 = var19;
                  var14 = var20;
               }
            } else {
               var12 = var20;
               var6 = var19;
            }
         }

         ++var10;
      }
   }
}
