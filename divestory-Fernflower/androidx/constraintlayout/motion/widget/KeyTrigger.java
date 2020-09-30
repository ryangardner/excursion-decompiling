package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.R;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

public class KeyTrigger extends Key {
   public static final int KEY_TYPE = 5;
   static final String NAME = "KeyTrigger";
   private static final String TAG = "KeyTrigger";
   RectF mCollisionRect;
   private String mCross = null;
   private int mCurveFit = -1;
   private Method mFireCross;
   private boolean mFireCrossReset;
   private float mFireLastPos;
   private Method mFireNegativeCross;
   private boolean mFireNegativeReset;
   private Method mFirePositiveCross;
   private boolean mFirePositiveReset;
   private float mFireThreshold;
   private String mNegativeCross;
   private String mPositiveCross;
   private boolean mPostLayout;
   RectF mTargetRect;
   private int mTriggerCollisionId;
   private View mTriggerCollisionView;
   private int mTriggerID;
   private int mTriggerReceiver;
   float mTriggerSlack;

   public KeyTrigger() {
      this.mTriggerReceiver = UNSET;
      this.mNegativeCross = null;
      this.mPositiveCross = null;
      this.mTriggerID = UNSET;
      this.mTriggerCollisionId = UNSET;
      this.mTriggerCollisionView = null;
      this.mTriggerSlack = 0.1F;
      this.mFireCrossReset = true;
      this.mFireNegativeReset = true;
      this.mFirePositiveReset = true;
      this.mFireThreshold = Float.NaN;
      this.mPostLayout = false;
      this.mCollisionRect = new RectF();
      this.mTargetRect = new RectF();
      this.mType = 5;
      this.mCustomConstraints = new HashMap();
   }

   private void setUpRect(RectF var1, View var2, boolean var3) {
      var1.top = (float)var2.getTop();
      var1.bottom = (float)var2.getBottom();
      var1.left = (float)var2.getLeft();
      var1.right = (float)var2.getRight();
      if (var3) {
         var2.getMatrix().mapRect(var1);
      }

   }

   public void addValues(HashMap<String, SplineSet> var1) {
   }

   public void conditionallyFire(float var1, View var2) {
      boolean var16;
      boolean var5;
      boolean var15;
      label149: {
         int var3 = this.mTriggerCollisionId;
         int var4 = UNSET;
         var5 = true;
         if (var3 != var4) {
            if (this.mTriggerCollisionView == null) {
               this.mTriggerCollisionView = ((ViewGroup)var2.getParent()).findViewById(this.mTriggerCollisionId);
            }

            this.setUpRect(this.mCollisionRect, this.mTriggerCollisionView, this.mPostLayout);
            this.setUpRect(this.mTargetRect, var2, this.mPostLayout);
            if (this.mCollisionRect.intersect(this.mTargetRect)) {
               if (this.mFireCrossReset) {
                  this.mFireCrossReset = false;
                  var16 = true;
               } else {
                  var16 = false;
               }

               if (this.mFirePositiveReset) {
                  this.mFirePositiveReset = false;
                  var5 = true;
               } else {
                  var5 = false;
               }

               this.mFireNegativeReset = true;
               var15 = false;
               break label149;
            }

            if (!this.mFireCrossReset) {
               this.mFireCrossReset = true;
               var16 = true;
            } else {
               var16 = false;
            }

            if (this.mFireNegativeReset) {
               this.mFireNegativeReset = false;
               var15 = true;
            } else {
               var15 = false;
            }

            this.mFirePositiveReset = true;
         } else {
            float var6;
            label146: {
               if (this.mFireCrossReset) {
                  var6 = this.mFireThreshold;
                  if ((var1 - var6) * (this.mFireLastPos - var6) < 0.0F) {
                     this.mFireCrossReset = false;
                     var16 = true;
                     break label146;
                  }
               } else if (Math.abs(var1 - this.mFireThreshold) > this.mTriggerSlack) {
                  this.mFireCrossReset = true;
               }

               var16 = false;
            }

            float var7;
            label141: {
               if (this.mFireNegativeReset) {
                  var6 = this.mFireThreshold;
                  var7 = var1 - var6;
                  if ((this.mFireLastPos - var6) * var7 < 0.0F && var7 < 0.0F) {
                     this.mFireNegativeReset = false;
                     var15 = true;
                     break label141;
                  }
               } else if (Math.abs(var1 - this.mFireThreshold) > this.mTriggerSlack) {
                  this.mFireNegativeReset = true;
               }

               var15 = false;
            }

            if (this.mFirePositiveReset) {
               var7 = this.mFireThreshold;
               var6 = var1 - var7;
               if ((this.mFireLastPos - var7) * var6 < 0.0F && var6 > 0.0F) {
                  this.mFirePositiveReset = false;
               } else {
                  var5 = false;
               }
               break label149;
            }

            if (Math.abs(var1 - this.mFireThreshold) > this.mTriggerSlack) {
               this.mFirePositiveReset = true;
            }
         }

         var5 = false;
      }

      this.mFireLastPos = var1;
      if (var15 || var16 || var5) {
         ((MotionLayout)var2.getParent()).fireTrigger(this.mTriggerID, var5, var1);
      }

      if (this.mTriggerReceiver != UNSET) {
         var2 = ((MotionLayout)var2.getParent()).findViewById(this.mTriggerReceiver);
      }

      StringBuilder var8;
      if (var15 && this.mNegativeCross != null) {
         if (this.mFireNegativeCross == null) {
            try {
               this.mFireNegativeCross = var2.getClass().getMethod(this.mNegativeCross);
            } catch (NoSuchMethodException var14) {
               var8 = new StringBuilder();
               var8.append("Could not find method \"");
               var8.append(this.mNegativeCross);
               var8.append("\"on class ");
               var8.append(var2.getClass().getSimpleName());
               var8.append(" ");
               var8.append(Debug.getName(var2));
               Log.e("KeyTrigger", var8.toString());
            }
         }

         try {
            this.mFireNegativeCross.invoke(var2);
         } catch (Exception var13) {
            var8 = new StringBuilder();
            var8.append("Exception in call \"");
            var8.append(this.mNegativeCross);
            var8.append("\"on class ");
            var8.append(var2.getClass().getSimpleName());
            var8.append(" ");
            var8.append(Debug.getName(var2));
            Log.e("KeyTrigger", var8.toString());
         }
      }

      if (var5 && this.mPositiveCross != null) {
         if (this.mFirePositiveCross == null) {
            try {
               this.mFirePositiveCross = var2.getClass().getMethod(this.mPositiveCross);
            } catch (NoSuchMethodException var12) {
               var8 = new StringBuilder();
               var8.append("Could not find method \"");
               var8.append(this.mPositiveCross);
               var8.append("\"on class ");
               var8.append(var2.getClass().getSimpleName());
               var8.append(" ");
               var8.append(Debug.getName(var2));
               Log.e("KeyTrigger", var8.toString());
            }
         }

         try {
            this.mFirePositiveCross.invoke(var2);
         } catch (Exception var11) {
            var8 = new StringBuilder();
            var8.append("Exception in call \"");
            var8.append(this.mPositiveCross);
            var8.append("\"on class ");
            var8.append(var2.getClass().getSimpleName());
            var8.append(" ");
            var8.append(Debug.getName(var2));
            Log.e("KeyTrigger", var8.toString());
         }
      }

      if (var16 && this.mCross != null) {
         if (this.mFireCross == null) {
            try {
               this.mFireCross = var2.getClass().getMethod(this.mCross);
            } catch (NoSuchMethodException var10) {
               var8 = new StringBuilder();
               var8.append("Could not find method \"");
               var8.append(this.mCross);
               var8.append("\"on class ");
               var8.append(var2.getClass().getSimpleName());
               var8.append(" ");
               var8.append(Debug.getName(var2));
               Log.e("KeyTrigger", var8.toString());
            }
         }

         try {
            this.mFireCross.invoke(var2);
         } catch (Exception var9) {
            var8 = new StringBuilder();
            var8.append("Exception in call \"");
            var8.append(this.mCross);
            var8.append("\"on class ");
            var8.append(var2.getClass().getSimpleName());
            var8.append(" ");
            var8.append(Debug.getName(var2));
            Log.e("KeyTrigger", var8.toString());
         }
      }

   }

   public void getAttributeNames(HashSet<String> var1) {
   }

   int getCurveFit() {
      return this.mCurveFit;
   }

   public void load(Context var1, AttributeSet var2) {
      KeyTrigger.Loader.read(this, var1.obtainStyledAttributes(var2, R.styleable.KeyTrigger), var1);
   }

   public void setValue(String var1, Object var2) {
   }

   private static class Loader {
      private static final int COLLISION = 9;
      private static final int CROSS = 4;
      private static final int FRAME_POS = 8;
      private static final int NEGATIVE_CROSS = 1;
      private static final int POSITIVE_CROSS = 2;
      private static final int POST_LAYOUT = 10;
      private static final int TARGET_ID = 7;
      private static final int TRIGGER_ID = 6;
      private static final int TRIGGER_RECEIVER = 11;
      private static final int TRIGGER_SLACK = 5;
      private static SparseIntArray mAttrMap;

      static {
         SparseIntArray var0 = new SparseIntArray();
         mAttrMap = var0;
         var0.append(R.styleable.KeyTrigger_framePosition, 8);
         mAttrMap.append(R.styleable.KeyTrigger_onCross, 4);
         mAttrMap.append(R.styleable.KeyTrigger_onNegativeCross, 1);
         mAttrMap.append(R.styleable.KeyTrigger_onPositiveCross, 2);
         mAttrMap.append(R.styleable.KeyTrigger_motionTarget, 7);
         mAttrMap.append(R.styleable.KeyTrigger_triggerId, 6);
         mAttrMap.append(R.styleable.KeyTrigger_triggerSlack, 5);
         mAttrMap.append(R.styleable.KeyTrigger_motion_triggerOnCollision, 9);
         mAttrMap.append(R.styleable.KeyTrigger_motion_postLayoutCollision, 10);
         mAttrMap.append(R.styleable.KeyTrigger_triggerReceiver, 11);
      }

      public static void read(KeyTrigger var0, TypedArray var1, Context var2) {
         int var3 = var1.getIndexCount();

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var1.getIndex(var4);
            switch(mAttrMap.get(var5)) {
            case 1:
               var0.mNegativeCross = var1.getString(var5);
               break;
            case 2:
               var0.mPositiveCross = var1.getString(var5);
               break;
            case 4:
               var0.mCross = var1.getString(var5);
               break;
            case 5:
               var0.mTriggerSlack = var1.getFloat(var5, var0.mTriggerSlack);
               break;
            case 6:
               var0.mTriggerID = var1.getResourceId(var5, var0.mTriggerID);
               break;
            case 7:
               if (MotionLayout.IS_IN_EDIT_MODE) {
                  var0.mTargetId = var1.getResourceId(var5, var0.mTargetId);
                  if (var0.mTargetId == -1) {
                     var0.mTargetString = var1.getString(var5);
                  }
               } else if (var1.peekValue(var5).type == 3) {
                  var0.mTargetString = var1.getString(var5);
               } else {
                  var0.mTargetId = var1.getResourceId(var5, var0.mTargetId);
               }
               break;
            case 8:
               var0.mFramePosition = var1.getInteger(var5, var0.mFramePosition);
               var0.mFireThreshold = ((float)var0.mFramePosition + 0.5F) / 100.0F;
               break;
            case 9:
               var0.mTriggerCollisionId = var1.getResourceId(var5, var0.mTriggerCollisionId);
               break;
            case 10:
               var0.mPostLayout = var1.getBoolean(var5, var0.mPostLayout);
               break;
            case 11:
               var0.mTriggerReceiver = var1.getResourceId(var5, var0.mTriggerReceiver);
            case 3:
            default:
               StringBuilder var6 = new StringBuilder();
               var6.append("unused attribute 0x");
               var6.append(Integer.toHexString(var5));
               var6.append("   ");
               var6.append(mAttrMap.get(var5));
               Log.e("KeyTrigger", var6.toString());
            }
         }

      }
   }
}
