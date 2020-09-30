package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import androidx.constraintlayout.widget.R;
import androidx.core.widget.NestedScrollView;
import org.xmlpull.v1.XmlPullParser;

class TouchResponse {
   private static final boolean DEBUG = false;
   static final int FLAG_DISABLE_POST_SCROLL = 1;
   static final int FLAG_DISABLE_SCROLL = 2;
   private static final int SIDE_BOTTOM = 3;
   private static final int SIDE_END = 6;
   private static final int SIDE_LEFT = 1;
   private static final int SIDE_MIDDLE = 4;
   private static final int SIDE_RIGHT = 2;
   private static final int SIDE_START = 5;
   private static final int SIDE_TOP = 0;
   private static final String TAG = "TouchResponse";
   private static final float[][] TOUCH_DIRECTION;
   private static final int TOUCH_DOWN = 1;
   private static final int TOUCH_END = 5;
   private static final int TOUCH_LEFT = 2;
   private static final int TOUCH_RIGHT = 3;
   private static final float[][] TOUCH_SIDES;
   private static final int TOUCH_START = 4;
   private static final int TOUCH_UP = 0;
   private float[] mAnchorDpDt = new float[2];
   private float mDragScale = 1.0F;
   private boolean mDragStarted = false;
   private float mDragThreshold = 10.0F;
   private int mFlags = 0;
   private float mLastTouchX;
   private float mLastTouchY;
   private int mLimitBoundsTo = -1;
   private float mMaxAcceleration = 1.2F;
   private float mMaxVelocity = 4.0F;
   private final MotionLayout mMotionLayout;
   private boolean mMoveWhenScrollAtTop = true;
   private int mOnTouchUp = 0;
   private int mTouchAnchorId = -1;
   private int mTouchAnchorSide = 0;
   private float mTouchAnchorX = 0.5F;
   private float mTouchAnchorY = 0.5F;
   private float mTouchDirectionX = 0.0F;
   private float mTouchDirectionY = 1.0F;
   private int mTouchRegionId = -1;
   private int mTouchSide = 0;

   static {
      float[] var0 = new float[]{1.0F, 0.5F};
      float[] var1 = new float[]{0.5F, 1.0F};
      float[] var2 = new float[]{0.5F, 0.5F};
      float[] var3 = new float[]{1.0F, 0.5F};
      TOUCH_SIDES = new float[][]{{0.5F, 0.0F}, {0.0F, 0.5F}, var0, var1, var2, {0.0F, 0.5F}, var3};
      var0 = new float[]{0.0F, 1.0F};
      var1 = new float[]{-1.0F, 0.0F};
      var2 = new float[]{1.0F, 0.0F};
      var3 = new float[]{-1.0F, 0.0F};
      float[] var4 = new float[]{1.0F, 0.0F};
      TOUCH_DIRECTION = new float[][]{{0.0F, -1.0F}, var0, var1, var2, var3, var4};
   }

   TouchResponse(Context var1, MotionLayout var2, XmlPullParser var3) {
      this.mMotionLayout = var2;
      this.fillFromAttributeList(var1, Xml.asAttributeSet(var3));
   }

   private void fill(TypedArray var1) {
      int var2 = var1.getIndexCount();

      for(int var3 = 0; var3 < var2; ++var3) {
         int var4 = var1.getIndex(var3);
         if (var4 == R.styleable.OnSwipe_touchAnchorId) {
            this.mTouchAnchorId = var1.getResourceId(var4, this.mTouchAnchorId);
         } else {
            float[][] var5;
            if (var4 == R.styleable.OnSwipe_touchAnchorSide) {
               var4 = var1.getInt(var4, this.mTouchAnchorSide);
               this.mTouchAnchorSide = var4;
               var5 = TOUCH_SIDES;
               this.mTouchAnchorX = var5[var4][0];
               this.mTouchAnchorY = var5[var4][1];
            } else if (var4 == R.styleable.OnSwipe_dragDirection) {
               var4 = var1.getInt(var4, this.mTouchSide);
               this.mTouchSide = var4;
               var5 = TOUCH_DIRECTION;
               this.mTouchDirectionX = var5[var4][0];
               this.mTouchDirectionY = var5[var4][1];
            } else if (var4 == R.styleable.OnSwipe_maxVelocity) {
               this.mMaxVelocity = var1.getFloat(var4, this.mMaxVelocity);
            } else if (var4 == R.styleable.OnSwipe_maxAcceleration) {
               this.mMaxAcceleration = var1.getFloat(var4, this.mMaxAcceleration);
            } else if (var4 == R.styleable.OnSwipe_moveWhenScrollAtTop) {
               this.mMoveWhenScrollAtTop = var1.getBoolean(var4, this.mMoveWhenScrollAtTop);
            } else if (var4 == R.styleable.OnSwipe_dragScale) {
               this.mDragScale = var1.getFloat(var4, this.mDragScale);
            } else if (var4 == R.styleable.OnSwipe_dragThreshold) {
               this.mDragThreshold = var1.getFloat(var4, this.mDragThreshold);
            } else if (var4 == R.styleable.OnSwipe_touchRegionId) {
               this.mTouchRegionId = var1.getResourceId(var4, this.mTouchRegionId);
            } else if (var4 == R.styleable.OnSwipe_onTouchUp) {
               this.mOnTouchUp = var1.getInt(var4, this.mOnTouchUp);
            } else if (var4 == R.styleable.OnSwipe_nestedScrollFlags) {
               this.mFlags = var1.getInteger(var4, 0);
            } else if (var4 == R.styleable.OnSwipe_limitBoundsTo) {
               this.mLimitBoundsTo = var1.getResourceId(var4, 0);
            }
         }
      }

   }

   private void fillFromAttributeList(Context var1, AttributeSet var2) {
      TypedArray var3 = var1.obtainStyledAttributes(var2, R.styleable.OnSwipe);
      this.fill(var3);
      var3.recycle();
   }

   float dot(float var1, float var2) {
      return var1 * this.mTouchDirectionX + var2 * this.mTouchDirectionY;
   }

   public int getAnchorId() {
      return this.mTouchAnchorId;
   }

   public int getFlags() {
      return this.mFlags;
   }

   RectF getLimitBoundsTo(ViewGroup var1, RectF var2) {
      int var3 = this.mLimitBoundsTo;
      if (var3 == -1) {
         return null;
      } else {
         View var4 = var1.findViewById(var3);
         if (var4 == null) {
            return null;
         } else {
            var2.set((float)var4.getLeft(), (float)var4.getTop(), (float)var4.getRight(), (float)var4.getBottom());
            return var2;
         }
      }
   }

   int getLimitBoundsToId() {
      return this.mLimitBoundsTo;
   }

   float getMaxAcceleration() {
      return this.mMaxAcceleration;
   }

   public float getMaxVelocity() {
      return this.mMaxVelocity;
   }

   boolean getMoveWhenScrollAtTop() {
      return this.mMoveWhenScrollAtTop;
   }

   float getProgressDirection(float var1, float var2) {
      float var3 = this.mMotionLayout.getProgress();
      this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, var3, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
      float[] var4;
      if (this.mTouchDirectionX != 0.0F) {
         var4 = this.mAnchorDpDt;
         if (var4[0] == 0.0F) {
            var4[0] = 1.0E-7F;
         }

         var1 = var1 * this.mTouchDirectionX / this.mAnchorDpDt[0];
      } else {
         var4 = this.mAnchorDpDt;
         if (var4[1] == 0.0F) {
            var4[1] = 1.0E-7F;
         }

         var1 = var2 * this.mTouchDirectionY / this.mAnchorDpDt[1];
      }

      return var1;
   }

   RectF getTouchRegion(ViewGroup var1, RectF var2) {
      int var3 = this.mTouchRegionId;
      if (var3 == -1) {
         return null;
      } else {
         View var4 = var1.findViewById(var3);
         if (var4 == null) {
            return null;
         } else {
            var2.set((float)var4.getLeft(), (float)var4.getTop(), (float)var4.getRight(), (float)var4.getBottom());
            return var2;
         }
      }
   }

   int getTouchRegionId() {
      return this.mTouchRegionId;
   }

   void processTouchEvent(MotionEvent var1, MotionLayout.MotionTracker var2, int var3, MotionScene var4) {
      var2.addMovement(var1);
      var3 = var1.getAction();
      if (var3 != 0) {
         float var5;
         float var6;
         float var7;
         float var8;
         if (var3 != 1) {
            if (var3 == 2) {
               var5 = var1.getRawY() - this.mLastTouchY;
               var6 = var1.getRawX() - this.mLastTouchX;
               if (Math.abs(this.mTouchDirectionX * var6 + this.mTouchDirectionY * var5) > this.mDragThreshold || this.mDragStarted) {
                  var7 = this.mMotionLayout.getProgress();
                  if (!this.mDragStarted) {
                     this.mDragStarted = true;
                     this.mMotionLayout.setProgress(var7);
                  }

                  var3 = this.mTouchAnchorId;
                  float[] var12;
                  if (var3 != -1) {
                     this.mMotionLayout.getAnchorDpDt(var3, var7, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
                  } else {
                     var8 = (float)Math.min(this.mMotionLayout.getWidth(), this.mMotionLayout.getHeight());
                     var12 = this.mAnchorDpDt;
                     var12[1] = this.mTouchDirectionY * var8;
                     var12[0] = var8 * this.mTouchDirectionX;
                  }

                  var8 = this.mTouchDirectionX;
                  var12 = this.mAnchorDpDt;
                  if ((double)Math.abs((var8 * var12[0] + this.mTouchDirectionY * var12[1]) * this.mDragScale) < 0.01D) {
                     var12 = this.mAnchorDpDt;
                     var12[0] = 0.01F;
                     var12[1] = 0.01F;
                  }

                  if (this.mTouchDirectionX != 0.0F) {
                     var6 /= this.mAnchorDpDt[0];
                  } else {
                     var6 = var5 / this.mAnchorDpDt[1];
                  }

                  var6 = Math.max(Math.min(var7 + var6, 1.0F), 0.0F);
                  if (var6 != this.mMotionLayout.getProgress()) {
                     this.mMotionLayout.setProgress(var6);
                     var2.computeCurrentVelocity(1000);
                     var7 = var2.getXVelocity();
                     var6 = var2.getYVelocity();
                     if (this.mTouchDirectionX != 0.0F) {
                        var6 = var7 / this.mAnchorDpDt[0];
                     } else {
                        var6 /= this.mAnchorDpDt[1];
                     }

                     this.mMotionLayout.mLastVelocity = var6;
                  } else {
                     this.mMotionLayout.mLastVelocity = 0.0F;
                  }

                  this.mLastTouchX = var1.getRawX();
                  this.mLastTouchY = var1.getRawY();
               }
            }
         } else {
            this.mDragStarted = false;
            var2.computeCurrentVelocity(1000);
            var6 = var2.getXVelocity();
            var7 = var2.getYVelocity();
            var5 = this.mMotionLayout.getProgress();
            var3 = this.mTouchAnchorId;
            float[] var10;
            if (var3 != -1) {
               this.mMotionLayout.getAnchorDpDt(var3, var5, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
            } else {
               var8 = (float)Math.min(this.mMotionLayout.getWidth(), this.mMotionLayout.getHeight());
               var10 = this.mAnchorDpDt;
               var10[1] = this.mTouchDirectionY * var8;
               var10[0] = var8 * this.mTouchDirectionX;
            }

            var8 = this.mTouchDirectionX;
            var10 = this.mAnchorDpDt;
            float var9 = var10[0];
            var9 = var10[1];
            if (var8 != 0.0F) {
               var6 /= var10[0];
            } else {
               var6 = var7 / var10[1];
            }

            if (!Float.isNaN(var6)) {
               var7 = var6 / 3.0F + var5;
            } else {
               var7 = var5;
            }

            if (var7 != 0.0F && var7 != 1.0F) {
               var3 = this.mOnTouchUp;
               if (var3 != 3) {
                  MotionLayout var11 = this.mMotionLayout;
                  if ((double)var7 < 0.5D) {
                     var7 = 0.0F;
                  } else {
                     var7 = 1.0F;
                  }

                  var11.touchAnimateTo(var3, var7, var6);
                  if (0.0F >= var5 || 1.0F <= var5) {
                     this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
                  }

                  return;
               }
            }

            if (0.0F >= var7 || 1.0F <= var7) {
               this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
            }
         }
      } else {
         this.mLastTouchX = var1.getRawX();
         this.mLastTouchY = var1.getRawY();
         this.mDragStarted = false;
      }

   }

   void scrollMove(float var1, float var2) {
      float var3 = this.mMotionLayout.getProgress();
      if (!this.mDragStarted) {
         this.mDragStarted = true;
         this.mMotionLayout.setProgress(var3);
      }

      this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, var3, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
      float var4 = this.mTouchDirectionX;
      float[] var5 = this.mAnchorDpDt;
      if ((double)Math.abs(var4 * var5[0] + this.mTouchDirectionY * var5[1]) < 0.01D) {
         var5 = this.mAnchorDpDt;
         var5[0] = 0.01F;
         var5[1] = 0.01F;
      }

      var4 = this.mTouchDirectionX;
      if (var4 != 0.0F) {
         var1 = var1 * var4 / this.mAnchorDpDt[0];
      } else {
         var1 = var2 * this.mTouchDirectionY / this.mAnchorDpDt[1];
      }

      var1 = Math.max(Math.min(var3 + var1, 1.0F), 0.0F);
      if (var1 != this.mMotionLayout.getProgress()) {
         this.mMotionLayout.setProgress(var1);
      }

   }

   void scrollUp(float var1, float var2) {
      boolean var3 = false;
      this.mDragStarted = false;
      float var4 = this.mMotionLayout.getProgress();
      this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, var4, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
      float var5 = this.mTouchDirectionX;
      float[] var6 = this.mAnchorDpDt;
      float var7 = var6[0];
      float var8 = this.mTouchDirectionY;
      var7 = var6[1];
      var7 = 0.0F;
      if (var5 != 0.0F) {
         var1 = var1 * var5 / var6[0];
      } else {
         var1 = var2 * var8 / var6[1];
      }

      var2 = var4;
      if (!Float.isNaN(var1)) {
         var2 = var4 + var1 / 3.0F;
      }

      if (var2 != 0.0F) {
         boolean var9;
         if (var2 != 1.0F) {
            var9 = true;
         } else {
            var9 = false;
         }

         if (this.mOnTouchUp != 3) {
            var3 = true;
         }

         if (var3 & var9) {
            MotionLayout var10 = this.mMotionLayout;
            int var11 = this.mOnTouchUp;
            if ((double)var2 < 0.5D) {
               var2 = var7;
            } else {
               var2 = 1.0F;
            }

            var10.touchAnimateTo(var11, var2, var1);
         }
      }

   }

   public void setAnchorId(int var1) {
      this.mTouchAnchorId = var1;
   }

   void setDown(float var1, float var2) {
      this.mLastTouchX = var1;
      this.mLastTouchY = var2;
   }

   public void setMaxAcceleration(float var1) {
      this.mMaxAcceleration = var1;
   }

   public void setMaxVelocity(float var1) {
      this.mMaxVelocity = var1;
   }

   public void setRTL(boolean var1) {
      float[][] var2;
      if (var1) {
         var2 = TOUCH_DIRECTION;
         var2[4] = var2[3];
         var2[5] = var2[2];
         var2 = TOUCH_SIDES;
         var2[5] = var2[2];
         var2[6] = var2[1];
      } else {
         var2 = TOUCH_DIRECTION;
         var2[4] = var2[2];
         var2[5] = var2[3];
         var2 = TOUCH_SIDES;
         var2[5] = var2[1];
         var2[6] = var2[2];
      }

      var2 = TOUCH_SIDES;
      int var3 = this.mTouchAnchorSide;
      this.mTouchAnchorX = var2[var3][0];
      this.mTouchAnchorY = var2[var3][1];
      var2 = TOUCH_DIRECTION;
      var3 = this.mTouchSide;
      this.mTouchDirectionX = var2[var3][0];
      this.mTouchDirectionY = var2[var3][1];
   }

   public void setTouchAnchorLocation(float var1, float var2) {
      this.mTouchAnchorX = var1;
      this.mTouchAnchorY = var2;
   }

   void setUpTouchEvent(float var1, float var2) {
      this.mLastTouchX = var1;
      this.mLastTouchY = var2;
      this.mDragStarted = false;
   }

   void setupTouch() {
      int var1 = this.mTouchAnchorId;
      View var3;
      if (var1 != -1) {
         View var2 = this.mMotionLayout.findViewById(var1);
         var3 = var2;
         if (var2 == null) {
            StringBuilder var4 = new StringBuilder();
            var4.append("cannot find TouchAnchorId @id/");
            var4.append(Debug.getName(this.mMotionLayout.getContext(), this.mTouchAnchorId));
            Log.e("TouchResponse", var4.toString());
            var3 = var2;
         }
      } else {
         var3 = null;
      }

      if (var3 instanceof NestedScrollView) {
         NestedScrollView var5 = (NestedScrollView)var3;
         var5.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View var1, MotionEvent var2) {
               return false;
            }
         });
         var5.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            public void onScrollChange(NestedScrollView var1, int var2, int var3, int var4, int var5) {
            }
         });
      }

   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.mTouchDirectionX);
      var1.append(" , ");
      var1.append(this.mTouchDirectionY);
      return var1.toString();
   }
}
