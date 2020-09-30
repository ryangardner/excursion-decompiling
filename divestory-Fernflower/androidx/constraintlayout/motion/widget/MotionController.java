package androidx.constraintlayout.motion.widget;

import android.graphics.RectF;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.utils.VelocityMatrix;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class MotionController {
   private static final boolean DEBUG = false;
   public static final int DRAW_PATH_AS_CONFIGURED = 4;
   public static final int DRAW_PATH_BASIC = 1;
   public static final int DRAW_PATH_CARTESIAN = 3;
   public static final int DRAW_PATH_NONE = 0;
   public static final int DRAW_PATH_RECTANGLE = 5;
   public static final int DRAW_PATH_RELATIVE = 2;
   public static final int DRAW_PATH_SCREEN = 6;
   private static final boolean FAVOR_FIXED_SIZE_VIEWS = false;
   public static final int HORIZONTAL_PATH_X = 2;
   public static final int HORIZONTAL_PATH_Y = 3;
   public static final int PATH_PERCENT = 0;
   public static final int PATH_PERPENDICULAR = 1;
   private static final String TAG = "MotionController";
   public static final int VERTICAL_PATH_X = 4;
   public static final int VERTICAL_PATH_Y = 5;
   private int MAX_DIMENSION = 4;
   String[] attributeTable;
   private CurveFit mArcSpline;
   private int[] mAttributeInterpCount;
   private String[] mAttributeNames;
   private HashMap<String, SplineSet> mAttributesMap;
   String mConstraintTag;
   private int mCurveFitType = -1;
   private HashMap<String, KeyCycleOscillator> mCycleMap;
   private MotionPaths mEndMotionPath = new MotionPaths();
   private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
   int mId;
   private double[] mInterpolateData;
   private int[] mInterpolateVariables;
   private double[] mInterpolateVelocity;
   private ArrayList<Key> mKeyList = new ArrayList();
   private KeyTrigger[] mKeyTriggers;
   private ArrayList<MotionPaths> mMotionPaths = new ArrayList();
   float mMotionStagger = Float.NaN;
   private int mPathMotionArc;
   private CurveFit[] mSpline;
   float mStaggerOffset = 0.0F;
   float mStaggerScale = 1.0F;
   private MotionPaths mStartMotionPath = new MotionPaths();
   private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
   private HashMap<String, TimeCycleSplineSet> mTimeCycleAttributesMap;
   private float[] mValuesBuff = new float[4];
   private float[] mVelocity = new float[1];
   View mView;

   MotionController(View var1) {
      this.mPathMotionArc = Key.UNSET;
      this.setView(var1);
   }

   private float getAdjustedPosition(float var1, float[] var2) {
      float var3 = 0.0F;
      float var4 = 1.0F;
      float var5;
      float var6;
      if (var2 != null) {
         var2[0] = 1.0F;
         var5 = var1;
      } else {
         var5 = var1;
         if ((double)this.mStaggerScale != 1.0D) {
            var6 = var1;
            if (var1 < this.mStaggerOffset) {
               var6 = 0.0F;
            }

            var1 = this.mStaggerOffset;
            var5 = var6;
            if (var6 > var1) {
               var5 = var6;
               if ((double)var6 < 1.0D) {
                  var5 = (var6 - var1) * this.mStaggerScale;
               }
            }
         }
      }

      Easing var7 = this.mStartMotionPath.mKeyFrameEasing;
      var1 = Float.NaN;
      Iterator var8 = this.mMotionPaths.iterator();
      var6 = var3;

      while(var8.hasNext()) {
         MotionPaths var9 = (MotionPaths)var8.next();
         if (var9.mKeyFrameEasing != null) {
            if (var9.time < var5) {
               var7 = var9.mKeyFrameEasing;
               var6 = var9.time;
            } else if (Float.isNaN(var1)) {
               var1 = var9.time;
            }
         }
      }

      var3 = var5;
      if (var7 != null) {
         if (Float.isNaN(var1)) {
            var1 = var4;
         }

         var1 -= var6;
         double var10 = (double)((var5 - var6) / var1);
         var1 = (float)var7.get(var10) * var1 + var6;
         var3 = var1;
         if (var2 != null) {
            var2[0] = (float)var7.getDiff(var10);
            var3 = var1;
         }
      }

      return var3;
   }

   private float getPreCycleDistance() {
      float[] var1 = new float[2];
      float var2 = 1.0F / (float)99;
      double var3 = 0.0D;
      double var5 = 0.0D;
      int var7 = 0;

      float var8;
      float var13;
      for(var8 = 0.0F; var7 < 100; var8 = var13) {
         float var9 = (float)var7 * var2;
         double var10 = (double)var9;
         Easing var12 = this.mStartMotionPath.mKeyFrameEasing;
         var13 = Float.NaN;
         Iterator var14 = this.mMotionPaths.iterator();
         float var15 = 0.0F;

         while(var14.hasNext()) {
            MotionPaths var16 = (MotionPaths)var14.next();
            if (var16.mKeyFrameEasing != null) {
               if (var16.time < var9) {
                  var12 = var16.mKeyFrameEasing;
                  var15 = var16.time;
               } else if (Float.isNaN(var13)) {
                  var13 = var16.time;
               }
            }
         }

         if (var12 != null) {
            float var17 = var13;
            if (Float.isNaN(var13)) {
               var17 = 1.0F;
            }

            var13 = var17 - var15;
            var10 = (double)((float)var12.get((double)((var9 - var15) / var13)) * var13 + var15);
         }

         this.mSpline[0].getPos(var10, this.mInterpolateData);
         this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, var1, 0);
         var13 = var8;
         if (var7 > 0) {
            var13 = (float)((double)var8 + Math.hypot(var5 - (double)var1[1], var3 - (double)var1[0]));
         }

         var3 = (double)var1[0];
         var5 = (double)var1[1];
         ++var7;
      }

      return var8;
   }

   private void insertKey(MotionPaths var1) {
      int var2 = Collections.binarySearch(this.mMotionPaths, var1);
      if (var2 == 0) {
         StringBuilder var3 = new StringBuilder();
         var3.append(" KeyPath positon \"");
         var3.append(var1.position);
         var3.append("\" outside of range");
         Log.e("MotionController", var3.toString());
      }

      this.mMotionPaths.add(-var2 - 1, var1);
   }

   private void readView(MotionPaths var1) {
      var1.setBounds((float)((int)this.mView.getX()), (float)((int)this.mView.getY()), (float)this.mView.getWidth(), (float)this.mView.getHeight());
   }

   void addKey(Key var1) {
      this.mKeyList.add(var1);
   }

   void addKeys(ArrayList<Key> var1) {
      this.mKeyList.addAll(var1);
   }

   void buildBounds(float[] var1, int var2) {
      float var3 = 1.0F / (float)(var2 - 1);
      HashMap var4 = this.mAttributesMap;
      SplineSet var14;
      if (var4 != null) {
         var14 = (SplineSet)var4.get("translationX");
      }

      var4 = this.mAttributesMap;
      if (var4 != null) {
         var14 = (SplineSet)var4.get("translationY");
      }

      var4 = this.mCycleMap;
      KeyCycleOscillator var15;
      if (var4 != null) {
         var15 = (KeyCycleOscillator)var4.get("translationX");
      }

      var4 = this.mCycleMap;
      if (var4 != null) {
         var15 = (KeyCycleOscillator)var4.get("translationY");
      }

      for(int var5 = 0; var5 < var2; ++var5) {
         float var6 = (float)var5 * var3;
         float var7 = this.mStaggerScale;
         float var8 = 0.0F;
         float var9 = var6;
         if (var7 != 1.0F) {
            var7 = var6;
            if (var6 < this.mStaggerOffset) {
               var7 = 0.0F;
            }

            var6 = this.mStaggerOffset;
            var9 = var7;
            if (var7 > var6) {
               var9 = var7;
               if ((double)var7 < 1.0D) {
                  var9 = (var7 - var6) * this.mStaggerScale;
               }
            }
         }

         double var10 = (double)var9;
         Easing var16 = this.mStartMotionPath.mKeyFrameEasing;
         var7 = Float.NaN;
         Iterator var12 = this.mMotionPaths.iterator();
         var6 = var8;

         while(var12.hasNext()) {
            MotionPaths var13 = (MotionPaths)var12.next();
            if (var13.mKeyFrameEasing != null) {
               if (var13.time < var9) {
                  var16 = var13.mKeyFrameEasing;
                  var6 = var13.time;
               } else if (Float.isNaN(var7)) {
                  var7 = var13.time;
               }
            }
         }

         if (var16 != null) {
            var8 = var7;
            if (Float.isNaN(var7)) {
               var8 = 1.0F;
            }

            var7 = var8 - var6;
            var10 = (double)((float)var16.get((double)((var9 - var6) / var7)) * var7 + var6);
         }

         this.mSpline[0].getPos(var10, this.mInterpolateData);
         CurveFit var17 = this.mArcSpline;
         if (var17 != null) {
            double[] var18 = this.mInterpolateData;
            if (var18.length > 0) {
               var17.getPos(var10, var18);
            }
         }

         this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, var1, var5 * 2);
      }

   }

   int buildKeyBounds(float[] var1, int[] var2) {
      if (var1 == null) {
         return 0;
      } else {
         double[] var3 = this.mSpline[0].getTimePoints();
         int var5;
         if (var2 != null) {
            Iterator var4 = this.mMotionPaths.iterator();

            for(var5 = 0; var4.hasNext(); ++var5) {
               var2[var5] = ((MotionPaths)var4.next()).mMode;
            }
         }

         var5 = 0;

         int var6;
         for(var6 = 0; var5 < var3.length; ++var5) {
            this.mSpline[0].getPos(var3[var5], this.mInterpolateData);
            this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, var1, var6);
            var6 += 2;
         }

         return var6 / 2;
      }
   }

   int buildKeyFrames(float[] var1, int[] var2) {
      if (var1 == null) {
         return 0;
      } else {
         double[] var3 = this.mSpline[0].getTimePoints();
         int var5;
         if (var2 != null) {
            Iterator var4 = this.mMotionPaths.iterator();

            for(var5 = 0; var4.hasNext(); ++var5) {
               var2[var5] = ((MotionPaths)var4.next()).mMode;
            }
         }

         var5 = 0;

         int var6;
         for(var6 = 0; var5 < var3.length; ++var5) {
            this.mSpline[0].getPos(var3[var5], this.mInterpolateData);
            this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, var1, var6);
            var6 += 2;
         }

         return var6 / 2;
      }
   }

   void buildPath(float[] var1, int var2) {
      float var3 = 1.0F / (float)(var2 - 1);
      HashMap var4 = this.mAttributesMap;
      KeyCycleOscillator var5 = null;
      SplineSet var21;
      if (var4 == null) {
         var21 = null;
      } else {
         var21 = (SplineSet)var4.get("translationX");
      }

      HashMap var6 = this.mAttributesMap;
      SplineSet var22;
      if (var6 == null) {
         var22 = null;
      } else {
         var22 = (SplineSet)var6.get("translationY");
      }

      HashMap var7 = this.mCycleMap;
      KeyCycleOscillator var23;
      if (var7 == null) {
         var23 = null;
      } else {
         var23 = (KeyCycleOscillator)var7.get("translationX");
      }

      HashMap var8 = this.mCycleMap;
      if (var8 != null) {
         var5 = (KeyCycleOscillator)var8.get("translationY");
      }

      for(int var9 = 0; var9 < var2; ++var9) {
         float var10 = (float)var9 * var3;
         float var11 = this.mStaggerScale;
         float var12 = 0.0F;
         float var13 = var10;
         if (var11 != 1.0F) {
            var11 = var10;
            if (var10 < this.mStaggerOffset) {
               var11 = 0.0F;
            }

            var10 = this.mStaggerOffset;
            var13 = var11;
            if (var11 > var10) {
               var13 = var11;
               if ((double)var11 < 1.0D) {
                  var13 = (var11 - var10) * this.mStaggerScale;
               }
            }
         }

         double var14 = (double)var13;
         Easing var24 = this.mStartMotionPath.mKeyFrameEasing;
         var11 = Float.NaN;
         Iterator var16 = this.mMotionPaths.iterator();

         for(var10 = var12; var16.hasNext(); var11 = var12) {
            MotionPaths var17 = (MotionPaths)var16.next();
            Easing var18 = var24;
            float var19 = var10;
            var12 = var11;
            if (var17.mKeyFrameEasing != null) {
               if (var17.time < var13) {
                  var18 = var17.mKeyFrameEasing;
                  var19 = var17.time;
                  var12 = var11;
               } else {
                  var18 = var24;
                  var19 = var10;
                  var12 = var11;
                  if (Float.isNaN(var11)) {
                     var12 = var17.time;
                     var19 = var10;
                     var18 = var24;
                  }
               }
            }

            var24 = var18;
            var10 = var19;
         }

         if (var24 != null) {
            var12 = var11;
            if (Float.isNaN(var11)) {
               var12 = 1.0F;
            }

            var11 = var12 - var10;
            var14 = (double)((float)var24.get((double)((var13 - var10) / var11)) * var11 + var10);
         }

         this.mSpline[0].getPos(var14, this.mInterpolateData);
         CurveFit var28 = this.mArcSpline;
         if (var28 != null) {
            double[] var25 = this.mInterpolateData;
            if (var25.length > 0) {
               var28.getPos(var14, var25);
            }
         }

         MotionPaths var27 = this.mStartMotionPath;
         int[] var26 = this.mInterpolateVariables;
         double[] var29 = this.mInterpolateData;
         int var20 = var9 * 2;
         var27.getCenter(var26, var29, var1, var20);
         if (var23 != null) {
            var1[var20] += var23.get(var13);
         } else if (var21 != null) {
            var1[var20] += var21.get(var13);
         }

         if (var5 != null) {
            ++var20;
            var1[var20] += var5.get(var13);
         } else if (var22 != null) {
            ++var20;
            var1[var20] += var22.get(var13);
         }
      }

   }

   void buildRect(float var1, float[] var2, int var3) {
      var1 = this.getAdjustedPosition(var1, (float[])null);
      this.mSpline[0].getPos((double)var1, this.mInterpolateData);
      this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, var2, var3);
   }

   void buildRectangles(float[] var1, int var2) {
      float var3 = 1.0F / (float)(var2 - 1);

      for(int var4 = 0; var4 < var2; ++var4) {
         float var5 = this.getAdjustedPosition((float)var4 * var3, (float[])null);
         this.mSpline[0].getPos((double)var5, this.mInterpolateData);
         this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, var1, var4 * 8);
      }

   }

   int getAttributeValues(String var1, float[] var2, int var3) {
      SplineSet var4 = (SplineSet)this.mAttributesMap.get(var1);
      if (var4 == null) {
         return -1;
      } else {
         for(var3 = 0; var3 < var2.length; ++var3) {
            var2[var3] = var4.get((float)(var3 / (var2.length - 1)));
         }

         return var2.length;
      }
   }

   void getDpDt(float var1, float var2, float var3, float[] var4) {
      var1 = this.getAdjustedPosition(var1, this.mVelocity);
      CurveFit[] var5 = this.mSpline;
      int var6 = 0;
      if (var5 == null) {
         float var10 = this.mEndMotionPath.x - this.mStartMotionPath.x;
         float var11 = this.mEndMotionPath.y - this.mStartMotionPath.y;
         float var12 = this.mEndMotionPath.width;
         var1 = this.mStartMotionPath.width;
         float var13 = this.mEndMotionPath.height;
         float var14 = this.mStartMotionPath.height;
         var4[0] = var10 * (1.0F - var2) + (var12 - var1 + var10) * var2;
         var4[1] = var11 * (1.0F - var3) + (var13 - var14 + var11) * var3;
      } else {
         CurveFit var15 = var5[0];
         double var7 = (double)var1;
         var15.getSlope(var7, this.mInterpolateVelocity);
         this.mSpline[0].getPos(var7, this.mInterpolateData);
         var1 = this.mVelocity[0];

         while(true) {
            double[] var9 = this.mInterpolateVelocity;
            if (var6 >= var9.length) {
               var15 = this.mArcSpline;
               if (var15 != null) {
                  var9 = this.mInterpolateData;
                  if (var9.length > 0) {
                     var15.getPos(var7, var9);
                     this.mArcSpline.getSlope(var7, this.mInterpolateVelocity);
                     this.mStartMotionPath.setDpDt(var2, var3, var4, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
                  }

                  return;
               } else {
                  this.mStartMotionPath.setDpDt(var2, var3, var4, this.mInterpolateVariables, var9, this.mInterpolateData);
                  return;
               }
            }

            var9[var6] *= (double)var1;
            ++var6;
         }
      }
   }

   public int getDrawPath() {
      int var1 = this.mStartMotionPath.mDrawPath;

      for(Iterator var2 = this.mMotionPaths.iterator(); var2.hasNext(); var1 = Math.max(var1, ((MotionPaths)var2.next()).mDrawPath)) {
      }

      return Math.max(var1, this.mEndMotionPath.mDrawPath);
   }

   float getFinalX() {
      return this.mEndMotionPath.x;
   }

   float getFinalY() {
      return this.mEndMotionPath.y;
   }

   MotionPaths getKeyFrame(int var1) {
      return (MotionPaths)this.mMotionPaths.get(var1);
   }

   public int getKeyFrameInfo(int var1, int[] var2) {
      float[] var3 = new float[2];
      Iterator var4 = this.mKeyList.iterator();
      int var5 = 0;
      int var6 = 0;

      while(true) {
         Key var7;
         do {
            if (!var4.hasNext()) {
               return var5;
            }

            var7 = (Key)var4.next();
         } while(var7.mType != var1 && var1 == -1);

         var2[var6] = 0;
         int var8 = var6 + 1;
         var2[var8] = var7.mType;
         ++var8;
         var2[var8] = var7.mFramePosition;
         float var9 = (float)var7.mFramePosition / 100.0F;
         this.mSpline[0].getPos((double)var9, this.mInterpolateData);
         this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, var3, 0);
         ++var8;
         var2[var8] = Float.floatToIntBits(var3[0]);
         int var10 = var8 + 1;
         var2[var10] = Float.floatToIntBits(var3[1]);
         var8 = var10;
         if (var7 instanceof KeyPosition) {
            KeyPosition var11 = (KeyPosition)var7;
            var8 = var10 + 1;
            var2[var8] = var11.mPositionType;
            ++var8;
            var2[var8] = Float.floatToIntBits(var11.mPercentX);
            ++var8;
            var2[var8] = Float.floatToIntBits(var11.mPercentY);
         }

         ++var8;
         var2[var6] = var8 - var6;
         ++var5;
         var6 = var8;
      }
   }

   float getKeyFrameParameter(int var1, float var2, float var3) {
      float var4 = this.mEndMotionPath.x - this.mStartMotionPath.x;
      float var5 = this.mEndMotionPath.y - this.mStartMotionPath.y;
      float var6 = this.mStartMotionPath.x;
      float var7 = this.mStartMotionPath.width / 2.0F;
      float var8 = this.mStartMotionPath.y;
      float var9 = this.mStartMotionPath.height / 2.0F;
      float var10 = (float)Math.hypot((double)var4, (double)var5);
      if ((double)var10 < 1.0E-7D) {
         return Float.NaN;
      } else {
         var2 -= var6 + var7;
         var9 = var3 - (var8 + var9);
         if ((float)Math.hypot((double)var2, (double)var9) == 0.0F) {
            return 0.0F;
         } else {
            var3 = var2 * var4 + var9 * var5;
            if (var1 != 0) {
               if (var1 != 1) {
                  if (var1 != 2) {
                     if (var1 != 3) {
                        if (var1 != 4) {
                           return var1 != 5 ? 0.0F : var9 / var5;
                        } else {
                           return var2 / var5;
                        }
                     } else {
                        return var9 / var4;
                     }
                  } else {
                     return var2 / var4;
                  }
               } else {
                  return (float)Math.sqrt((double)(var10 * var10 - var3 * var3));
               }
            } else {
               return var3 / var10;
            }
         }
      }
   }

   KeyPositionBase getPositionKeyframe(int var1, int var2, float var3, float var4) {
      RectF var5 = new RectF();
      var5.left = this.mStartMotionPath.x;
      var5.top = this.mStartMotionPath.y;
      var5.right = var5.left + this.mStartMotionPath.width;
      var5.bottom = var5.top + this.mStartMotionPath.height;
      RectF var6 = new RectF();
      var6.left = this.mEndMotionPath.x;
      var6.top = this.mEndMotionPath.y;
      var6.right = var6.left + this.mEndMotionPath.width;
      var6.bottom = var6.top + this.mEndMotionPath.height;
      Iterator var7 = this.mKeyList.iterator();

      while(var7.hasNext()) {
         Key var8 = (Key)var7.next();
         if (var8 instanceof KeyPositionBase) {
            KeyPositionBase var9 = (KeyPositionBase)var8;
            if (var9.intersects(var1, var2, var5, var6, var3, var4)) {
               return var9;
            }
         }
      }

      return null;
   }

   void getPostLayoutDvDp(float var1, int var2, int var3, float var4, float var5, float[] var6) {
      var1 = this.getAdjustedPosition(var1, this.mVelocity);
      HashMap var7 = this.mAttributesMap;
      KeyCycleOscillator var8 = null;
      SplineSet var28;
      if (var7 == null) {
         var28 = null;
      } else {
         var28 = (SplineSet)var7.get("translationX");
      }

      HashMap var9 = this.mAttributesMap;
      SplineSet var31;
      if (var9 == null) {
         var31 = null;
      } else {
         var31 = (SplineSet)var9.get("translationY");
      }

      HashMap var10 = this.mAttributesMap;
      SplineSet var32;
      if (var10 == null) {
         var32 = null;
      } else {
         var32 = (SplineSet)var10.get("rotation");
      }

      HashMap var11 = this.mAttributesMap;
      SplineSet var33;
      if (var11 == null) {
         var33 = null;
      } else {
         var33 = (SplineSet)var11.get("scaleX");
      }

      HashMap var12 = this.mAttributesMap;
      SplineSet var34;
      if (var12 == null) {
         var34 = null;
      } else {
         var34 = (SplineSet)var12.get("scaleY");
      }

      HashMap var13 = this.mCycleMap;
      KeyCycleOscillator var35;
      if (var13 == null) {
         var35 = null;
      } else {
         var35 = (KeyCycleOscillator)var13.get("translationX");
      }

      HashMap var14 = this.mCycleMap;
      KeyCycleOscillator var36;
      if (var14 == null) {
         var36 = null;
      } else {
         var36 = (KeyCycleOscillator)var14.get("translationY");
      }

      HashMap var15 = this.mCycleMap;
      KeyCycleOscillator var37;
      if (var15 == null) {
         var37 = null;
      } else {
         var37 = (KeyCycleOscillator)var15.get("rotation");
      }

      HashMap var16 = this.mCycleMap;
      KeyCycleOscillator var38;
      if (var16 == null) {
         var38 = null;
      } else {
         var38 = (KeyCycleOscillator)var16.get("scaleX");
      }

      HashMap var17 = this.mCycleMap;
      if (var17 != null) {
         var8 = (KeyCycleOscillator)var17.get("scaleY");
      }

      VelocityMatrix var39 = new VelocityMatrix();
      var39.clear();
      var39.setRotationVelocity(var32, var1);
      var39.setTranslationVelocity(var28, var31, var1);
      var39.setScaleVelocity(var33, var34, var1);
      var39.setRotationVelocity(var37, var1);
      var39.setTranslationVelocity(var35, var36, var1);
      var39.setScaleVelocity(var38, var8, var1);
      CurveFit var18 = this.mArcSpline;
      double var19;
      double[] var30;
      if (var18 != null) {
         var30 = this.mInterpolateData;
         if (var30.length > 0) {
            var19 = (double)var1;
            var18.getPos(var19, var30);
            this.mArcSpline.getSlope(var19, this.mInterpolateVelocity);
            this.mStartMotionPath.setDpDt(var4, var5, var6, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
         }

         var39.applyTransform(var4, var5, var2, var3, var6);
      } else {
         CurveFit[] var40 = this.mSpline;
         int var21 = 0;
         if (var40 == null) {
            float var22 = this.mEndMotionPath.x - this.mStartMotionPath.x;
            float var23 = this.mEndMotionPath.y - this.mStartMotionPath.y;
            float var24 = this.mEndMotionPath.width;
            float var25 = this.mStartMotionPath.width;
            float var26 = this.mEndMotionPath.height;
            float var27 = this.mStartMotionPath.height;
            var6[0] = var22 * (1.0F - var4) + (var24 - var25 + var22) * var4;
            var6[1] = var23 * (1.0F - var5) + (var26 - var27 + var23) * var5;
            var39.clear();
            var39.setRotationVelocity(var32, var1);
            var39.setTranslationVelocity(var28, var31, var1);
            var39.setScaleVelocity(var33, var34, var1);
            var39.setRotationVelocity(var37, var1);
            var39.setTranslationVelocity(var35, var36, var1);
            var39.setScaleVelocity(var38, var8, var1);
            var39.applyTransform(var4, var5, var2, var3, var6);
         } else {
            var1 = this.getAdjustedPosition(var1, this.mVelocity);
            CurveFit var29 = this.mSpline[0];
            var19 = (double)var1;
            var29.getSlope(var19, this.mInterpolateVelocity);
            this.mSpline[0].getPos(var19, this.mInterpolateData);
            var1 = this.mVelocity[0];

            while(true) {
               var30 = this.mInterpolateVelocity;
               if (var21 >= var30.length) {
                  this.mStartMotionPath.setDpDt(var4, var5, var6, this.mInterpolateVariables, var30, this.mInterpolateData);
                  var39.applyTransform(var4, var5, var2, var3, var6);
                  return;
               }

               var30[var21] *= (double)var1;
               ++var21;
            }
         }
      }
   }

   float getStartX() {
      return this.mStartMotionPath.x;
   }

   float getStartY() {
      return this.mStartMotionPath.y;
   }

   public int getkeyFramePositions(int[] var1, float[] var2) {
      Iterator var3 = this.mKeyList.iterator();
      int var4 = 0;

      for(int var5 = 0; var3.hasNext(); ++var4) {
         Key var6 = (Key)var3.next();
         var1[var4] = var6.mFramePosition + var6.mType * 1000;
         float var7 = (float)var6.mFramePosition / 100.0F;
         this.mSpline[0].getPos((double)var7, this.mInterpolateData);
         this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, var2, var5);
         var5 += 2;
      }

      return var4;
   }

   boolean interpolate(View var1, float var2, long var3, KeyCache var5) {
      float var6 = this.getAdjustedPosition(var2, (float[])null);
      HashMap var7 = this.mAttributesMap;
      if (var7 != null) {
         Iterator var34 = var7.values().iterator();

         while(var34.hasNext()) {
            ((SplineSet)var34.next()).setProperty(var1, var6);
         }
      }

      var7 = this.mTimeCycleAttributesMap;
      Iterator var8;
      boolean var9;
      TimeCycleSplineSet.PathRotate var35;
      if (var7 != null) {
         var8 = var7.values().iterator();
         var35 = null;
         var9 = false;

         while(var8.hasNext()) {
            TimeCycleSplineSet var10 = (TimeCycleSplineSet)var8.next();
            if (var10 instanceof TimeCycleSplineSet.PathRotate) {
               var35 = (TimeCycleSplineSet.PathRotate)var10;
            } else {
               var9 |= var10.setProperty(var1, var6, var3, var5);
            }
         }
      } else {
         var35 = null;
         var9 = false;
      }

      CurveFit[] var36 = this.mSpline;
      int var14;
      boolean var15;
      double[] var38;
      if (var36 != null) {
         CurveFit var37 = var36[0];
         double var11 = (double)var6;
         var37.getPos(var11, this.mInterpolateData);
         this.mSpline[0].getSlope(var11, this.mInterpolateVelocity);
         CurveFit var42 = this.mArcSpline;
         if (var42 != null) {
            var38 = this.mInterpolateData;
            if (var38.length > 0) {
               var42.getPos(var11, var38);
               this.mArcSpline.getSlope(var11, this.mInterpolateVelocity);
            }
         }

         this.mStartMotionPath.setView(var1, this.mInterpolateVariables, this.mInterpolateData, this.mInterpolateVelocity, (double[])null);
         HashMap var39 = this.mAttributesMap;
         if (var39 != null) {
            var8 = var39.values().iterator();

            while(var8.hasNext()) {
               SplineSet var43 = (SplineSet)var8.next();
               if (var43 instanceof SplineSet.PathRotate) {
                  SplineSet.PathRotate var13 = (SplineSet.PathRotate)var43;
                  double[] var44 = this.mInterpolateVelocity;
                  var13.setPathRotate(var1, var6, var44[0], var44[1]);
               }
            }
         }

         if (var35 != null) {
            var38 = this.mInterpolateVelocity;
            var9 |= var35.setPathRotate(var1, var5, var6, var3, var38[0], var38[1]);
         }

         var14 = 1;

         label85:
         while(true) {
            CurveFit[] var30 = this.mSpline;
            if (var14 >= var30.length) {
               if (this.mStartPoint.mVisibilityMode == 0) {
                  if (var6 <= 0.0F) {
                     var1.setVisibility(this.mStartPoint.visibility);
                  } else if (var6 >= 1.0F) {
                     var1.setVisibility(this.mEndPoint.visibility);
                  } else if (this.mEndPoint.visibility != this.mStartPoint.visibility) {
                     var1.setVisibility(0);
                  }
               }

               var15 = var9;
               if (this.mKeyTriggers != null) {
                  var14 = 0;

                  while(true) {
                     KeyTrigger[] var31 = this.mKeyTriggers;
                     var15 = var9;
                     if (var14 >= var31.length) {
                        break label85;
                     }

                     var31[var14].conditionallyFire(var6, var1);
                     ++var14;
                  }
               }
               break;
            }

            var30[var14].getPos(var11, this.mValuesBuff);
            ((ConstraintAttribute)this.mStartMotionPath.attributes.get(this.mAttributeNames[var14 - 1])).setInterpolatedValue(var1, this.mValuesBuff);
            ++var14;
         }
      } else {
         float var16 = this.mStartMotionPath.x;
         float var17 = this.mEndMotionPath.x;
         float var18 = this.mStartMotionPath.x;
         float var19 = this.mStartMotionPath.y;
         float var20 = this.mEndMotionPath.y;
         float var21 = this.mStartMotionPath.y;
         float var22 = this.mStartMotionPath.width;
         float var23 = this.mEndMotionPath.width;
         float var24 = this.mStartMotionPath.width;
         float var25 = this.mStartMotionPath.height;
         float var26 = this.mEndMotionPath.height;
         var2 = this.mStartMotionPath.height;
         var18 = var16 + (var17 - var18) * var6 + 0.5F;
         int var27 = (int)var18;
         var20 = var19 + (var20 - var21) * var6 + 0.5F;
         var14 = (int)var20;
         int var28 = (int)(var18 + var22 + (var23 - var24) * var6);
         int var29 = (int)(var20 + var25 + (var26 - var2) * var6);
         if (this.mEndMotionPath.width != this.mStartMotionPath.width || this.mEndMotionPath.height != this.mStartMotionPath.height) {
            var1.measure(MeasureSpec.makeMeasureSpec(var28 - var27, 1073741824), MeasureSpec.makeMeasureSpec(var29 - var14, 1073741824));
         }

         var1.layout(var27, var14, var28, var29);
         var15 = var9;
      }

      HashMap var32 = this.mCycleMap;
      if (var32 != null) {
         Iterator var33 = var32.values().iterator();

         while(var33.hasNext()) {
            KeyCycleOscillator var40 = (KeyCycleOscillator)var33.next();
            if (var40 instanceof KeyCycleOscillator.PathRotateSet) {
               KeyCycleOscillator.PathRotateSet var41 = (KeyCycleOscillator.PathRotateSet)var40;
               var38 = this.mInterpolateVelocity;
               var41.setPathRotate(var1, var6, var38[0], var38[1]);
            } else {
               var40.setProperty(var1, var6);
            }
         }
      }

      return var15;
   }

   String name() {
      return this.mView.getContext().getResources().getResourceEntryName(this.mView.getId());
   }

   void positionKeyframe(View var1, KeyPositionBase var2, float var3, float var4, String[] var5, float[] var6) {
      RectF var7 = new RectF();
      var7.left = this.mStartMotionPath.x;
      var7.top = this.mStartMotionPath.y;
      var7.right = var7.left + this.mStartMotionPath.width;
      var7.bottom = var7.top + this.mStartMotionPath.height;
      RectF var8 = new RectF();
      var8.left = this.mEndMotionPath.x;
      var8.top = this.mEndMotionPath.y;
      var8.right = var8.left + this.mEndMotionPath.width;
      var8.bottom = var8.top + this.mEndMotionPath.height;
      var2.positionAttributes(var1, var7, var8, var3, var4, var5, var6);
   }

   public void setDrawPath(int var1) {
      this.mStartMotionPath.mDrawPath = var1;
   }

   void setEndState(ConstraintWidget var1, ConstraintSet var2) {
      this.mEndMotionPath.time = 1.0F;
      this.mEndMotionPath.position = 1.0F;
      this.readView(this.mEndMotionPath);
      this.mEndMotionPath.setBounds((float)var1.getX(), (float)var1.getY(), (float)var1.getWidth(), (float)var1.getHeight());
      this.mEndMotionPath.applyParameters(var2.getParameters(this.mId));
      this.mEndPoint.setState(var1, var2, this.mId);
   }

   public void setPathMotionArc(int var1) {
      this.mPathMotionArc = var1;
   }

   void setStartCurrentState(View var1) {
      this.mStartMotionPath.time = 0.0F;
      this.mStartMotionPath.position = 0.0F;
      this.mStartMotionPath.setBounds(var1.getX(), var1.getY(), (float)var1.getWidth(), (float)var1.getHeight());
      this.mStartPoint.setState(var1);
   }

   void setStartState(ConstraintWidget var1, ConstraintSet var2) {
      this.mStartMotionPath.time = 0.0F;
      this.mStartMotionPath.position = 0.0F;
      this.readView(this.mStartMotionPath);
      this.mStartMotionPath.setBounds((float)var1.getX(), (float)var1.getY(), (float)var1.getWidth(), (float)var1.getHeight());
      ConstraintSet.Constraint var3 = var2.getParameters(this.mId);
      this.mStartMotionPath.applyParameters(var3);
      this.mMotionStagger = var3.motion.mMotionStagger;
      this.mStartPoint.setState(var1, var2, this.mId);
   }

   public void setView(View var1) {
      this.mView = var1;
      this.mId = var1.getId();
      LayoutParams var2 = var1.getLayoutParams();
      if (var2 instanceof ConstraintLayout.LayoutParams) {
         this.mConstraintTag = ((ConstraintLayout.LayoutParams)var2).getConstraintTag();
      }

   }

   public void setup(int var1, int var2, float var3, long var4) {
      new HashSet();
      HashSet var6 = new HashSet();
      HashSet var7 = new HashSet();
      HashSet var8 = new HashSet();
      HashMap var9 = new HashMap();
      if (this.mPathMotionArc != Key.UNSET) {
         this.mStartMotionPath.mPathMotionArc = this.mPathMotionArc;
      }

      this.mStartPoint.different(this.mEndPoint, var7);
      ArrayList var10 = this.mKeyList;
      Iterator var11;
      ArrayList var12;
      Key var13;
      if (var10 != null) {
         var11 = var10.iterator();
         var10 = null;

         while(true) {
            var12 = var10;
            if (!var11.hasNext()) {
               break;
            }

            var13 = (Key)var11.next();
            if (var13 instanceof KeyPosition) {
               KeyPosition var34 = (KeyPosition)var13;
               this.insertKey(new MotionPaths(var1, var2, var34, this.mStartMotionPath, this.mEndMotionPath));
               if (var34.mCurveFit != Key.UNSET) {
                  this.mCurveFitType = var34.mCurveFit;
               }
            } else if (var13 instanceof KeyCycle) {
               var13.getAttributeNames(var8);
            } else if (var13 instanceof KeyTimeCycle) {
               var13.getAttributeNames(var6);
            } else if (var13 instanceof KeyTrigger) {
               var12 = var10;
               if (var10 == null) {
                  var12 = new ArrayList();
               }

               var12.add((KeyTrigger)var13);
               var10 = var12;
            } else {
               var13.setInterpolation(var9);
               var13.getAttributeNames(var7);
            }
         }
      } else {
         var12 = null;
      }

      if (var12 != null) {
         this.mKeyTriggers = (KeyTrigger[])var12.toArray(new KeyTrigger[0]);
      }

      Key var36;
      String var37;
      Iterator var43;
      if (!var7.isEmpty()) {
         this.mAttributesMap = new HashMap();
         var11 = var7.iterator();

         while(var11.hasNext()) {
            String var41 = (String)var11.next();
            SplineSet var31;
            if (!var41.startsWith("CUSTOM,")) {
               var31 = SplineSet.makeSpline(var41);
            } else {
               SparseArray var14 = new SparseArray();
               String var38 = var41.split(",")[1];
               Iterator var15 = this.mKeyList.iterator();

               while(var15.hasNext()) {
                  Key var16 = (Key)var15.next();
                  if (var16.mCustomConstraints != null) {
                     ConstraintAttribute var30 = (ConstraintAttribute)var16.mCustomConstraints.get(var38);
                     if (var30 != null) {
                        var14.append(var16.mFramePosition, var30);
                     }
                  }
               }

               var31 = SplineSet.makeCustomSpline(var41, var14);
            }

            if (var31 != null) {
               var31.setType(var41);
               this.mAttributesMap.put(var41, var31);
            }
         }

         var10 = this.mKeyList;
         if (var10 != null) {
            var43 = var10.iterator();

            while(var43.hasNext()) {
               var36 = (Key)var43.next();
               if (var36 instanceof KeyAttributes) {
                  var36.addValues(this.mAttributesMap);
               }
            }
         }

         this.mStartPoint.addValues(this.mAttributesMap, 0);
         this.mEndPoint.addValues(this.mAttributesMap, 100);

         for(var43 = this.mAttributesMap.keySet().iterator(); var43.hasNext(); ((SplineSet)this.mAttributesMap.get(var37)).setup(var1)) {
            var37 = (String)var43.next();
            if (var9.containsKey(var37)) {
               var1 = (Integer)var9.get(var37);
            } else {
               var1 = 0;
            }
         }
      }

      if (!var6.isEmpty()) {
         if (this.mTimeCycleAttributesMap == null) {
            this.mTimeCycleAttributesMap = new HashMap();
         }

         var43 = var6.iterator();

         label328:
         while(true) {
            String var22;
            do {
               if (!var43.hasNext()) {
                  var10 = this.mKeyList;
                  if (var10 != null) {
                     var43 = var10.iterator();

                     while(var43.hasNext()) {
                        var36 = (Key)var43.next();
                        if (var36 instanceof KeyTimeCycle) {
                           ((KeyTimeCycle)var36).addTimeValues(this.mTimeCycleAttributesMap);
                        }
                     }
                  }

                  for(var43 = this.mTimeCycleAttributesMap.keySet().iterator(); var43.hasNext(); ((TimeCycleSplineSet)this.mTimeCycleAttributesMap.get(var37)).setup(var1)) {
                     var37 = (String)var43.next();
                     if (var9.containsKey(var37)) {
                        var1 = (Integer)var9.get(var37);
                     } else {
                        var1 = 0;
                     }
                  }
                  break label328;
               }

               var22 = (String)var43.next();
            } while(this.mTimeCycleAttributesMap.containsKey(var22));

            TimeCycleSplineSet var39;
            if (!var22.startsWith("CUSTOM,")) {
               var39 = TimeCycleSplineSet.makeSpline(var22, var4);
            } else {
               SparseArray var47 = new SparseArray();
               var37 = var22.split(",")[1];
               Iterator var42 = this.mKeyList.iterator();

               while(var42.hasNext()) {
                  var13 = (Key)var42.next();
                  if (var13.mCustomConstraints != null) {
                     ConstraintAttribute var32 = (ConstraintAttribute)var13.mCustomConstraints.get(var37);
                     if (var32 != null) {
                        var47.append(var13.mFramePosition, var32);
                     }
                  }
               }

               var39 = TimeCycleSplineSet.makeCustomSpline(var22, var47);
            }

            if (var39 != null) {
               var39.setType(var22);
               this.mTimeCycleAttributesMap.put(var22, var39);
            }
         }
      }

      int var17 = this.mMotionPaths.size() + 2;
      MotionPaths[] var23 = new MotionPaths[var17];
      var23[0] = this.mStartMotionPath;
      var23[var17 - 1] = this.mEndMotionPath;
      if (this.mMotionPaths.size() > 0 && this.mCurveFitType == -1) {
         this.mCurveFitType = 0;
      }

      Iterator var45 = this.mMotionPaths.iterator();

      for(var1 = 1; var45.hasNext(); ++var1) {
         var23[var1] = (MotionPaths)var45.next();
      }

      HashSet var33 = new HashSet();
      Iterator var26 = this.mEndMotionPath.attributes.keySet().iterator();

      StringBuilder var48;
      while(var26.hasNext()) {
         var37 = (String)var26.next();
         if (this.mStartMotionPath.attributes.containsKey(var37)) {
            var48 = new StringBuilder();
            var48.append("CUSTOM,");
            var48.append(var37);
            if (!var7.contains(var48.toString())) {
               var33.add(var37);
            }
         }
      }

      String[] var46 = (String[])var33.toArray(new String[0]);
      this.mAttributeNames = var46;
      this.mAttributeInterpCount = new int[var46.length];
      var1 = 0;

      while(true) {
         var46 = this.mAttributeNames;
         if (var1 >= var46.length) {
            boolean var18;
            if (var23[0].mPathMotionArc != Key.UNSET) {
               var18 = true;
            } else {
               var18 = false;
            }

            int var19 = 18 + this.mAttributeNames.length;
            boolean[] var50 = new boolean[var19];

            for(var1 = 1; var1 < var17; ++var1) {
               var23[var1].different(var23[var1 - 1], var50, this.mAttributeNames, var18);
            }

            var1 = 1;

            int var20;
            for(var20 = 0; var1 < var19; var20 = var2) {
               var2 = var20;
               if (var50[var1]) {
                  var2 = var20 + 1;
               }

               ++var1;
            }

            int[] var51 = new int[var20];
            this.mInterpolateVariables = var51;
            this.mInterpolateData = new double[var51.length];
            this.mInterpolateVelocity = new double[var51.length];
            var1 = 1;

            for(var2 = 0; var1 < var19; var2 = var20) {
               var20 = var2;
               if (var50[var1]) {
                  this.mInterpolateVariables[var2] = var1;
                  var20 = var2 + 1;
               }

               ++var1;
            }

            double[][] var40 = new double[var17][this.mInterpolateVariables.length];
            double[] var35 = new double[var17];

            for(var1 = 0; var1 < var17; ++var1) {
               var23[var1].fillStandard(var40[var1], this.mInterpolateVariables);
               var35[var1] = (double)var23[var1].time;
            }

            var1 = 0;

            while(true) {
               var51 = this.mInterpolateVariables;
               if (var1 >= var51.length) {
                  this.mSpline = new CurveFit[this.mAttributeNames.length + 1];
                  var1 = 0;

                  while(true) {
                     String[] var52 = this.mAttributeNames;
                     if (var1 >= var52.length) {
                        this.mSpline[0] = CurveFit.get(this.mCurveFitType, var35, var40);
                        if (var23[0].mPathMotionArc != Key.UNSET) {
                           var51 = new int[var17];
                           double[] var27 = new double[var17];
                           double[][] var56 = new double[var17][2];

                           for(var1 = 0; var1 < var17; ++var1) {
                              var51[var1] = var23[var1].mPathMotionArc;
                              var27[var1] = (double)var23[var1].time;
                              var56[var1][0] = (double)var23[var1].x;
                              var56[var1][1] = (double)var23[var1].y;
                           }

                           this.mArcSpline = CurveFit.getArc(var51, var27, var56);
                        }

                        float var21 = Float.NaN;
                        this.mCycleMap = new HashMap();
                        if (this.mKeyList != null) {
                           var43 = var8.iterator();

                           while(var43.hasNext()) {
                              String var29 = (String)var43.next();
                              KeyCycleOscillator var57 = KeyCycleOscillator.makeSpline(var29);
                              if (var57 != null) {
                                 var3 = var21;
                                 if (var57.variesByPath()) {
                                    var3 = var21;
                                    if (Float.isNaN(var21)) {
                                       var3 = this.getPreCycleDistance();
                                    }
                                 }

                                 var57.setType(var29);
                                 this.mCycleMap.put(var29, var57);
                                 var21 = var3;
                              }
                           }

                           var43 = this.mKeyList.iterator();

                           while(var43.hasNext()) {
                              var36 = (Key)var43.next();
                              if (var36 instanceof KeyCycle) {
                                 ((KeyCycle)var36).addCycleValues(this.mCycleMap);
                              }
                           }

                           var45 = this.mCycleMap.values().iterator();

                           while(var45.hasNext()) {
                              ((KeyCycleOscillator)var45.next()).setup(var21);
                           }
                        }

                        return;
                     }

                     double[][] var55 = (double[][])null;
                     String var44 = var52[var1];
                     double[] var54 = null;
                     var19 = 0;

                     for(var20 = 0; var19 < var17; var20 = var2) {
                        double[][] var24 = var55;
                        double[] var28 = var54;
                        var2 = var20;
                        if (var23[var19].hasCustomData(var44)) {
                           var24 = var55;
                           if (var55 == null) {
                              var54 = new double[var17];
                              var24 = new double[var17][var23[var19].getCustomDataCount(var44)];
                           }

                           var54[var20] = (double)var23[var19].time;
                           var23[var19].getCustomData(var44, var24[var20], 0);
                           var2 = var20 + 1;
                           var28 = var54;
                        }

                        ++var19;
                        var55 = var24;
                        var54 = var28;
                     }

                     var54 = Arrays.copyOf(var54, var20);
                     var55 = (double[][])Arrays.copyOf(var55, var20);
                     CurveFit[] var25 = this.mSpline;
                     ++var1;
                     var25[var1] = CurveFit.get(this.mCurveFitType, var54, var55);
                  }
               }

               if (var51[var1] < MotionPaths.names.length) {
                  StringBuilder var53 = new StringBuilder();
                  var53.append(MotionPaths.names[this.mInterpolateVariables[var1]]);
                  var53.append(" [");
                  var37 = var53.toString();

                  for(var2 = 0; var2 < var17; ++var2) {
                     var48 = new StringBuilder();
                     var48.append(var37);
                     var48.append(var40[var2][var1]);
                     var37 = var48.toString();
                  }
               }

               ++var1;
            }
         }

         var37 = var46[var1];
         this.mAttributeInterpCount[var1] = 0;

         for(var2 = 0; var2 < var17; ++var2) {
            if (var23[var2].attributes.containsKey(var37)) {
               int[] var49 = this.mAttributeInterpCount;
               var49[var1] += ((ConstraintAttribute)var23[var2].attributes.get(var37)).noOfInterpValues();
               break;
            }
         }

         ++var1;
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(" start: x: ");
      var1.append(this.mStartMotionPath.x);
      var1.append(" y: ");
      var1.append(this.mStartMotionPath.y);
      var1.append(" end: x: ");
      var1.append(this.mEndMotionPath.x);
      var1.append(" y: ");
      var1.append(this.mEndMotionPath.y);
      return var1.toString();
   }
}
