package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;

public class KeyPosition extends KeyPositionBase {
   static final int KEY_TYPE = 2;
   static final String NAME = "KeyPosition";
   private static final String PERCENT_X = "percentX";
   private static final String PERCENT_Y = "percentY";
   private static final String TAG = "KeyPosition";
   public static final int TYPE_CARTESIAN = 0;
   public static final int TYPE_PATH = 1;
   public static final int TYPE_SCREEN = 2;
   float mAltPercentX;
   float mAltPercentY;
   private float mCalculatedPositionX;
   private float mCalculatedPositionY;
   int mDrawPath;
   int mPathMotionArc;
   float mPercentHeight;
   float mPercentWidth;
   float mPercentX;
   float mPercentY;
   int mPositionType;
   String mTransitionEasing = null;

   public KeyPosition() {
      this.mPathMotionArc = UNSET;
      this.mDrawPath = 0;
      this.mPercentWidth = Float.NaN;
      this.mPercentHeight = Float.NaN;
      this.mPercentX = Float.NaN;
      this.mPercentY = Float.NaN;
      this.mAltPercentX = Float.NaN;
      this.mAltPercentY = Float.NaN;
      this.mPositionType = 0;
      this.mCalculatedPositionX = Float.NaN;
      this.mCalculatedPositionY = Float.NaN;
      this.mType = 2;
   }

   private void calcCartesianPosition(float var1, float var2, float var3, float var4) {
      float var5 = var3 - var1;
      float var6 = var4 - var2;
      boolean var7 = Float.isNaN(this.mPercentX);
      float var8 = 0.0F;
      if (var7) {
         var3 = 0.0F;
      } else {
         var3 = this.mPercentX;
      }

      if (Float.isNaN(this.mAltPercentY)) {
         var4 = 0.0F;
      } else {
         var4 = this.mAltPercentY;
      }

      float var9;
      if (Float.isNaN(this.mPercentY)) {
         var9 = 0.0F;
      } else {
         var9 = this.mPercentY;
      }

      if (!Float.isNaN(this.mAltPercentX)) {
         var8 = this.mAltPercentX;
      }

      this.mCalculatedPositionX = (float)((int)(var1 + var3 * var5 + var8 * var6));
      this.mCalculatedPositionY = (float)((int)(var2 + var5 * var4 + var6 * var9));
   }

   private void calcPathPosition(float var1, float var2, float var3, float var4) {
      var3 -= var1;
      float var5 = var4 - var2;
      float var6 = -var5;
      var4 = this.mPercentX;
      float var7 = this.mPercentY;
      this.mCalculatedPositionX = var1 + var3 * var4 + var6 * var7;
      this.mCalculatedPositionY = var2 + var5 * var4 + var3 * var7;
   }

   private void calcScreenPosition(int var1, int var2) {
      float var3 = (float)(var1 - 0);
      float var4 = this.mPercentX;
      float var5 = (float)0;
      this.mCalculatedPositionX = var3 * var4 + var5;
      this.mCalculatedPositionY = (float)(var2 - 0) * var4 + var5;
   }

   public void addValues(HashMap<String, SplineSet> var1) {
   }

   void calcPosition(int var1, int var2, float var3, float var4, float var5, float var6) {
      int var7 = this.mPositionType;
      if (var7 != 1) {
         if (var7 != 2) {
            this.calcCartesianPosition(var3, var4, var5, var6);
         } else {
            this.calcScreenPosition(var1, var2);
         }
      } else {
         this.calcPathPosition(var3, var4, var5, var6);
      }
   }

   float getPositionX() {
      return this.mCalculatedPositionX;
   }

   float getPositionY() {
      return this.mCalculatedPositionY;
   }

   public boolean intersects(int var1, int var2, RectF var3, RectF var4, float var5, float var6) {
      this.calcPosition(var1, var2, var3.centerX(), var3.centerY(), var4.centerX(), var4.centerY());
      return Math.abs(var5 - this.mCalculatedPositionX) < 20.0F && Math.abs(var6 - this.mCalculatedPositionY) < 20.0F;
   }

   public void load(Context var1, AttributeSet var2) {
      KeyPosition.Loader.read(this, var1.obtainStyledAttributes(var2, R.styleable.KeyPosition));
   }

   public void positionAttributes(View var1, RectF var2, RectF var3, float var4, float var5, String[] var6, float[] var7) {
      int var8 = this.mPositionType;
      if (var8 != 1) {
         if (var8 != 2) {
            this.positionCartAttributes(var2, var3, var4, var5, var6, var7);
         } else {
            this.positionScreenAttributes(var1, var2, var3, var4, var5, var6, var7);
         }
      } else {
         this.positionPathAttributes(var2, var3, var4, var5, var6, var7);
      }
   }

   void positionCartAttributes(RectF var1, RectF var2, float var3, float var4, String[] var5, float[] var6) {
      float var7 = var1.centerX();
      float var8 = var1.centerY();
      float var9 = var2.centerX();
      float var10 = var2.centerY();
      var9 -= var7;
      var10 -= var8;
      if (var5[0] != null) {
         if ("percentX".equals(var5[0])) {
            var6[0] = (var3 - var7) / var9;
            var6[1] = (var4 - var8) / var10;
         } else {
            var6[1] = (var3 - var7) / var9;
            var6[0] = (var4 - var8) / var10;
         }
      } else {
         var5[0] = "percentX";
         var6[0] = (var3 - var7) / var9;
         var5[1] = "percentY";
         var6[1] = (var4 - var8) / var10;
      }

   }

   void positionPathAttributes(RectF var1, RectF var2, float var3, float var4, String[] var5, float[] var6) {
      float var7 = var1.centerX();
      float var8 = var1.centerY();
      float var9 = var2.centerX();
      float var10 = var2.centerY();
      var9 -= var7;
      float var11 = var10 - var8;
      var10 = (float)Math.hypot((double)var9, (double)var11);
      if ((double)var10 < 1.0E-4D) {
         System.out.println("distance ~ 0");
         var6[0] = 0.0F;
         var6[1] = 0.0F;
      } else {
         var9 /= var10;
         var11 /= var10;
         var4 -= var8;
         var7 = var3 - var7;
         var3 = (var9 * var4 - var7 * var11) / var10;
         var4 = (var9 * var7 + var11 * var4) / var10;
         if (var5[0] != null) {
            if ("percentX".equals(var5[0])) {
               var6[0] = var4;
               var6[1] = var3;
            }
         } else {
            var5[0] = "percentX";
            var5[1] = "percentY";
            var6[0] = var4;
            var6[1] = var3;
         }

      }
   }

   void positionScreenAttributes(View var1, RectF var2, RectF var3, float var4, float var5, String[] var6, float[] var7) {
      var2.centerX();
      var2.centerY();
      var3.centerX();
      var3.centerY();
      ViewGroup var10 = (ViewGroup)var1.getParent();
      int var8 = var10.getWidth();
      int var9 = var10.getHeight();
      if (var6[0] != null) {
         if ("percentX".equals(var6[0])) {
            var7[0] = var4 / (float)var8;
            var7[1] = var5 / (float)var9;
         } else {
            var7[1] = var4 / (float)var8;
            var7[0] = var5 / (float)var9;
         }
      } else {
         var6[0] = "percentX";
         var7[0] = var4 / (float)var8;
         var6[1] = "percentY";
         var7[1] = var5 / (float)var9;
      }

   }

   public void setValue(String var1, Object var2) {
      byte var3;
      label46: {
         switch(var1.hashCode()) {
         case -1812823328:
            if (var1.equals("transitionEasing")) {
               var3 = 0;
               break label46;
            }
            break;
         case -1127236479:
            if (var1.equals("percentWidth")) {
               var3 = 2;
               break label46;
            }
            break;
         case -1017587252:
            if (var1.equals("percentHeight")) {
               var3 = 3;
               break label46;
            }
            break;
         case -827014263:
            if (var1.equals("drawPath")) {
               var3 = 1;
               break label46;
            }
            break;
         case -200259324:
            if (var1.equals("sizePercent")) {
               var3 = 4;
               break label46;
            }
            break;
         case 428090547:
            if (var1.equals("percentX")) {
               var3 = 5;
               break label46;
            }
            break;
         case 428090548:
            if (var1.equals("percentY")) {
               var3 = 6;
               break label46;
            }
         }

         var3 = -1;
      }

      switch(var3) {
      case 0:
         this.mTransitionEasing = var2.toString();
         break;
      case 1:
         this.mDrawPath = this.toInt(var2);
         break;
      case 2:
         this.mPercentWidth = this.toFloat(var2);
         break;
      case 3:
         this.mPercentHeight = this.toFloat(var2);
         break;
      case 4:
         float var4 = this.toFloat(var2);
         this.mPercentWidth = var4;
         this.mPercentHeight = var4;
         break;
      case 5:
         this.mPercentX = this.toFloat(var2);
         break;
      case 6:
         this.mPercentY = this.toFloat(var2);
      }

   }

   private static class Loader {
      private static final int CURVE_FIT = 4;
      private static final int DRAW_PATH = 5;
      private static final int FRAME_POSITION = 2;
      private static final int PATH_MOTION_ARC = 10;
      private static final int PERCENT_HEIGHT = 12;
      private static final int PERCENT_WIDTH = 11;
      private static final int PERCENT_X = 6;
      private static final int PERCENT_Y = 7;
      private static final int SIZE_PERCENT = 8;
      private static final int TARGET_ID = 1;
      private static final int TRANSITION_EASING = 3;
      private static final int TYPE = 9;
      private static SparseIntArray mAttrMap;

      static {
         SparseIntArray var0 = new SparseIntArray();
         mAttrMap = var0;
         var0.append(R.styleable.KeyPosition_motionTarget, 1);
         mAttrMap.append(R.styleable.KeyPosition_framePosition, 2);
         mAttrMap.append(R.styleable.KeyPosition_transitionEasing, 3);
         mAttrMap.append(R.styleable.KeyPosition_curveFit, 4);
         mAttrMap.append(R.styleable.KeyPosition_drawPath, 5);
         mAttrMap.append(R.styleable.KeyPosition_percentX, 6);
         mAttrMap.append(R.styleable.KeyPosition_percentY, 7);
         mAttrMap.append(R.styleable.KeyPosition_keyPositionType, 9);
         mAttrMap.append(R.styleable.KeyPosition_sizePercent, 8);
         mAttrMap.append(R.styleable.KeyPosition_percentWidth, 11);
         mAttrMap.append(R.styleable.KeyPosition_percentHeight, 12);
         mAttrMap.append(R.styleable.KeyPosition_pathMotionArc, 10);
      }

      private static void read(KeyPosition var0, TypedArray var1) {
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
               if (var1.peekValue(var4).type == 3) {
                  var0.mTransitionEasing = var1.getString(var4);
               } else {
                  var0.mTransitionEasing = Easing.NAMED_EASING[var1.getInteger(var4, 0)];
               }
               break;
            case 4:
               var0.mCurveFit = var1.getInteger(var4, var0.mCurveFit);
               break;
            case 5:
               var0.mDrawPath = var1.getInt(var4, var0.mDrawPath);
               break;
            case 6:
               var0.mPercentX = var1.getFloat(var4, var0.mPercentX);
               break;
            case 7:
               var0.mPercentY = var1.getFloat(var4, var0.mPercentY);
               break;
            case 8:
               float var6 = var1.getFloat(var4, var0.mPercentHeight);
               var0.mPercentWidth = var6;
               var0.mPercentHeight = var6;
               break;
            case 9:
               var0.mPositionType = var1.getInt(var4, var0.mPositionType);
               break;
            case 10:
               var0.mPathMotionArc = var1.getInt(var4, var0.mPathMotionArc);
               break;
            case 11:
               var0.mPercentWidth = var1.getFloat(var4, var0.mPercentWidth);
               break;
            case 12:
               var0.mPercentHeight = var1.getFloat(var4, var0.mPercentHeight);
               break;
            default:
               StringBuilder var5 = new StringBuilder();
               var5.append("unused attribute 0x");
               var5.append(Integer.toHexString(var4));
               var5.append("   ");
               var5.append(mAttrMap.get(var4));
               Log.e("KeyPosition", var5.toString());
            }
         }

         if (var0.mFramePosition == -1) {
            Log.e("KeyPosition", "no frame position");
         }

      }
   }
}
