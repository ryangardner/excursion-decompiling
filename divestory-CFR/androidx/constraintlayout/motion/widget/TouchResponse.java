/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.Xml
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 */
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
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
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
    private float mDragScale = 1.0f;
    private boolean mDragStarted = false;
    private float mDragThreshold = 10.0f;
    private int mFlags = 0;
    private float mLastTouchX;
    private float mLastTouchY;
    private int mLimitBoundsTo = -1;
    private float mMaxAcceleration = 1.2f;
    private float mMaxVelocity = 4.0f;
    private final MotionLayout mMotionLayout;
    private boolean mMoveWhenScrollAtTop = true;
    private int mOnTouchUp = 0;
    private int mTouchAnchorId = -1;
    private int mTouchAnchorSide = 0;
    private float mTouchAnchorX = 0.5f;
    private float mTouchAnchorY = 0.5f;
    private float mTouchDirectionX = 0.0f;
    private float mTouchDirectionY = 1.0f;
    private int mTouchRegionId = -1;
    private int mTouchSide = 0;

    static {
        float[] arrf = new float[]{1.0f, 0.5f};
        float[] arrf2 = new float[]{0.5f, 1.0f};
        float[] arrf3 = new float[]{0.5f, 0.5f};
        float[] arrf4 = new float[]{1.0f, 0.5f};
        TOUCH_SIDES = new float[][]{{0.5f, 0.0f}, {0.0f, 0.5f}, arrf, arrf2, arrf3, {0.0f, 0.5f}, arrf4};
        arrf = new float[]{0.0f, 1.0f};
        arrf2 = new float[]{-1.0f, 0.0f};
        arrf3 = new float[]{1.0f, 0.0f};
        arrf4 = new float[]{-1.0f, 0.0f};
        float[] arrf5 = new float[]{1.0f, 0.0f};
        TOUCH_DIRECTION = new float[][]{{0.0f, -1.0f}, arrf, arrf2, arrf3, arrf4, arrf5};
    }

    TouchResponse(Context context, MotionLayout motionLayout, XmlPullParser xmlPullParser) {
        this.mMotionLayout = motionLayout;
        this.fillFromAttributeList(context, Xml.asAttributeSet((XmlPullParser)xmlPullParser));
    }

    private void fill(TypedArray typedArray) {
        int n = typedArray.getIndexCount();
        int n2 = 0;
        while (n2 < n) {
            float[][] arrf;
            int n3 = typedArray.getIndex(n2);
            if (n3 == R.styleable.OnSwipe_touchAnchorId) {
                this.mTouchAnchorId = typedArray.getResourceId(n3, this.mTouchAnchorId);
            } else if (n3 == R.styleable.OnSwipe_touchAnchorSide) {
                this.mTouchAnchorSide = n3 = typedArray.getInt(n3, this.mTouchAnchorSide);
                arrf = TOUCH_SIDES;
                this.mTouchAnchorX = arrf[n3][0];
                this.mTouchAnchorY = arrf[n3][1];
            } else if (n3 == R.styleable.OnSwipe_dragDirection) {
                this.mTouchSide = n3 = typedArray.getInt(n3, this.mTouchSide);
                arrf = TOUCH_DIRECTION;
                this.mTouchDirectionX = arrf[n3][0];
                this.mTouchDirectionY = arrf[n3][1];
            } else if (n3 == R.styleable.OnSwipe_maxVelocity) {
                this.mMaxVelocity = typedArray.getFloat(n3, this.mMaxVelocity);
            } else if (n3 == R.styleable.OnSwipe_maxAcceleration) {
                this.mMaxAcceleration = typedArray.getFloat(n3, this.mMaxAcceleration);
            } else if (n3 == R.styleable.OnSwipe_moveWhenScrollAtTop) {
                this.mMoveWhenScrollAtTop = typedArray.getBoolean(n3, this.mMoveWhenScrollAtTop);
            } else if (n3 == R.styleable.OnSwipe_dragScale) {
                this.mDragScale = typedArray.getFloat(n3, this.mDragScale);
            } else if (n3 == R.styleable.OnSwipe_dragThreshold) {
                this.mDragThreshold = typedArray.getFloat(n3, this.mDragThreshold);
            } else if (n3 == R.styleable.OnSwipe_touchRegionId) {
                this.mTouchRegionId = typedArray.getResourceId(n3, this.mTouchRegionId);
            } else if (n3 == R.styleable.OnSwipe_onTouchUp) {
                this.mOnTouchUp = typedArray.getInt(n3, this.mOnTouchUp);
            } else if (n3 == R.styleable.OnSwipe_nestedScrollFlags) {
                this.mFlags = typedArray.getInteger(n3, 0);
            } else if (n3 == R.styleable.OnSwipe_limitBoundsTo) {
                this.mLimitBoundsTo = typedArray.getResourceId(n3, 0);
            }
            ++n2;
        }
    }

    private void fillFromAttributeList(Context context, AttributeSet attributeSet) {
        context = context.obtainStyledAttributes(attributeSet, R.styleable.OnSwipe);
        this.fill((TypedArray)context);
        context.recycle();
    }

    float dot(float f, float f2) {
        return f * this.mTouchDirectionX + f2 * this.mTouchDirectionY;
    }

    public int getAnchorId() {
        return this.mTouchAnchorId;
    }

    public int getFlags() {
        return this.mFlags;
    }

    RectF getLimitBoundsTo(ViewGroup viewGroup, RectF rectF) {
        int n = this.mLimitBoundsTo;
        if (n == -1) {
            return null;
        }
        if ((viewGroup = viewGroup.findViewById(n)) == null) {
            return null;
        }
        rectF.set((float)viewGroup.getLeft(), (float)viewGroup.getTop(), (float)viewGroup.getRight(), (float)viewGroup.getBottom());
        return rectF;
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

    float getProgressDirection(float f, float f2) {
        float f3 = this.mMotionLayout.getProgress();
        this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, f3, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        if (this.mTouchDirectionX != 0.0f) {
            float[] arrf = this.mAnchorDpDt;
            if (arrf[0] != 0.0f) return f * this.mTouchDirectionX / this.mAnchorDpDt[0];
            arrf[0] = 1.0E-7f;
            return f * this.mTouchDirectionX / this.mAnchorDpDt[0];
        }
        float[] arrf = this.mAnchorDpDt;
        if (arrf[1] != 0.0f) return f2 * this.mTouchDirectionY / this.mAnchorDpDt[1];
        arrf[1] = 1.0E-7f;
        return f2 * this.mTouchDirectionY / this.mAnchorDpDt[1];
    }

    RectF getTouchRegion(ViewGroup viewGroup, RectF rectF) {
        int n = this.mTouchRegionId;
        if (n == -1) {
            return null;
        }
        if ((viewGroup = viewGroup.findViewById(n)) == null) {
            return null;
        }
        rectF.set((float)viewGroup.getLeft(), (float)viewGroup.getTop(), (float)viewGroup.getRight(), (float)viewGroup.getBottom());
        return rectF;
    }

    int getTouchRegionId() {
        return this.mTouchRegionId;
    }

    void processTouchEvent(MotionEvent object, MotionLayout.MotionTracker motionTracker, int n, MotionScene arrf) {
        float f;
        motionTracker.addMovement((MotionEvent)object);
        n = object.getAction();
        if (n == 0) {
            this.mLastTouchX = object.getRawX();
            this.mLastTouchY = object.getRawY();
            this.mDragStarted = false;
            return;
        }
        if (n != 1) {
            float f2;
            if (n != 2) {
                return;
            }
            float f3 = object.getRawY() - this.mLastTouchY;
            float f4 = object.getRawX() - this.mLastTouchX;
            if (!(Math.abs(this.mTouchDirectionX * f4 + this.mTouchDirectionY * f3) > this.mDragThreshold)) {
                if (!this.mDragStarted) return;
            }
            float f5 = this.mMotionLayout.getProgress();
            if (!this.mDragStarted) {
                this.mDragStarted = true;
                this.mMotionLayout.setProgress(f5);
            }
            if ((n = this.mTouchAnchorId) != -1) {
                this.mMotionLayout.getAnchorDpDt(n, f5, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
            } else {
                f2 = Math.min(this.mMotionLayout.getWidth(), this.mMotionLayout.getHeight());
                arrf = this.mAnchorDpDt;
                arrf[1] = this.mTouchDirectionY * f2;
                arrf[0] = f2 * this.mTouchDirectionX;
            }
            f2 = this.mTouchDirectionX;
            arrf = this.mAnchorDpDt;
            if ((double)Math.abs((f2 * arrf[0] + this.mTouchDirectionY * arrf[1]) * this.mDragScale) < 0.01) {
                arrf = this.mAnchorDpDt;
                arrf[0] = 0.01f;
                arrf[1] = 0.01f;
            }
            f4 = this.mTouchDirectionX != 0.0f ? (f4 /= this.mAnchorDpDt[0]) : f3 / this.mAnchorDpDt[1];
            f4 = Math.max(Math.min(f5 + f4, 1.0f), 0.0f);
            if (f4 != this.mMotionLayout.getProgress()) {
                this.mMotionLayout.setProgress(f4);
                motionTracker.computeCurrentVelocity(1000);
                f5 = motionTracker.getXVelocity();
                f4 = motionTracker.getYVelocity();
                f4 = this.mTouchDirectionX != 0.0f ? f5 / this.mAnchorDpDt[0] : (f4 /= this.mAnchorDpDt[1]);
                this.mMotionLayout.mLastVelocity = f4;
            } else {
                this.mMotionLayout.mLastVelocity = 0.0f;
            }
            this.mLastTouchX = object.getRawX();
            this.mLastTouchY = object.getRawY();
            return;
        }
        this.mDragStarted = false;
        motionTracker.computeCurrentVelocity(1000);
        float f6 = motionTracker.getXVelocity();
        float f7 = motionTracker.getYVelocity();
        float f8 = this.mMotionLayout.getProgress();
        n = this.mTouchAnchorId;
        if (n != -1) {
            this.mMotionLayout.getAnchorDpDt(n, f8, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        } else {
            f = Math.min(this.mMotionLayout.getWidth(), this.mMotionLayout.getHeight());
            object = this.mAnchorDpDt;
            object[1] = this.mTouchDirectionY * f;
            object[0] = f * this.mTouchDirectionX;
        }
        f = this.mTouchDirectionX;
        object = this.mAnchorDpDt;
        float f9 = object[0];
        f9 = object[1];
        f6 = f != 0.0f ? (f6 /= object[0]) : f7 / object[1];
        f7 = !Float.isNaN(f6) ? f6 / 3.0f + f8 : f8;
        if (f7 != 0.0f && f7 != 1.0f && (n = this.mOnTouchUp) != 3) {
            object = this.mMotionLayout;
            f7 = (double)f7 < 0.5 ? 0.0f : 1.0f;
            ((MotionLayout)object).touchAnimateTo(n, f7, f6);
            if (!(0.0f >= f8)) {
                if (!(1.0f <= f8)) return;
            }
            this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
            return;
        }
        if (!(0.0f >= f7)) {
            if (!(1.0f <= f7)) return;
        }
        this.mMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
    }

    void scrollMove(float f, float f2) {
        float f3 = this.mMotionLayout.getProgress();
        if (!this.mDragStarted) {
            this.mDragStarted = true;
            this.mMotionLayout.setProgress(f3);
        }
        this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, f3, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        float f4 = this.mTouchDirectionX;
        float[] arrf = this.mAnchorDpDt;
        if ((double)Math.abs(f4 * arrf[0] + this.mTouchDirectionY * arrf[1]) < 0.01) {
            arrf = this.mAnchorDpDt;
            arrf[0] = 0.01f;
            arrf[1] = 0.01f;
        }
        f = (f4 = this.mTouchDirectionX) != 0.0f ? f * f4 / this.mAnchorDpDt[0] : f2 * this.mTouchDirectionY / this.mAnchorDpDt[1];
        f = Math.max(Math.min(f3 + f, 1.0f), 0.0f);
        if (f == this.mMotionLayout.getProgress()) return;
        this.mMotionLayout.setProgress(f);
    }

    void scrollUp(float f, float f2) {
        boolean bl = false;
        this.mDragStarted = false;
        float f3 = this.mMotionLayout.getProgress();
        this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, f3, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        float f4 = this.mTouchDirectionX;
        Object object = this.mAnchorDpDt;
        float f5 = object[0];
        float f6 = this.mTouchDirectionY;
        f5 = object[1];
        f5 = 0.0f;
        f = f4 != 0.0f ? f * f4 / object[0] : f2 * f6 / object[1];
        f2 = f3;
        if (!Float.isNaN(f)) {
            f2 = f3 + f / 3.0f;
        }
        if (f2 == 0.0f) return;
        int n = f2 != 1.0f ? 1 : 0;
        if (this.mOnTouchUp != 3) {
            bl = true;
        }
        if (!(bl & n)) return;
        object = this.mMotionLayout;
        n = this.mOnTouchUp;
        f2 = (double)f2 < 0.5 ? f5 : 1.0f;
        ((MotionLayout)object).touchAnimateTo(n, f2, f);
    }

    public void setAnchorId(int n) {
        this.mTouchAnchorId = n;
    }

    void setDown(float f, float f2) {
        this.mLastTouchX = f;
        this.mLastTouchY = f2;
    }

    public void setMaxAcceleration(float f) {
        this.mMaxAcceleration = f;
    }

    public void setMaxVelocity(float f) {
        this.mMaxVelocity = f;
    }

    public void setRTL(boolean bl) {
        float[][] arrf;
        if (bl) {
            arrf = TOUCH_DIRECTION;
            arrf[4] = arrf[3];
            arrf[5] = arrf[2];
            arrf = TOUCH_SIDES;
            arrf[5] = arrf[2];
            arrf[6] = arrf[1];
        } else {
            arrf = TOUCH_DIRECTION;
            arrf[4] = arrf[2];
            arrf[5] = arrf[3];
            arrf = TOUCH_SIDES;
            arrf[5] = arrf[1];
            arrf[6] = arrf[2];
        }
        arrf = TOUCH_SIDES;
        int n = this.mTouchAnchorSide;
        this.mTouchAnchorX = arrf[n][0];
        this.mTouchAnchorY = arrf[n][1];
        arrf = TOUCH_DIRECTION;
        n = this.mTouchSide;
        this.mTouchDirectionX = arrf[n][0];
        this.mTouchDirectionY = arrf[n][1];
    }

    public void setTouchAnchorLocation(float f, float f2) {
        this.mTouchAnchorX = f;
        this.mTouchAnchorY = f2;
    }

    void setUpTouchEvent(float f, float f2) {
        this.mLastTouchX = f;
        this.mLastTouchY = f2;
        this.mDragStarted = false;
    }

    void setupTouch() {
        Object object;
        int n = this.mTouchAnchorId;
        if (n != -1) {
            View view = this.mMotionLayout.findViewById(n);
            object = view;
            if (view == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("cannot find TouchAnchorId @id/");
                ((StringBuilder)object).append(Debug.getName(this.mMotionLayout.getContext(), this.mTouchAnchorId));
                Log.e((String)TAG, (String)((StringBuilder)object).toString());
                object = view;
            }
        } else {
            object = null;
        }
        if (!(object instanceof NestedScrollView)) return;
        object = (NestedScrollView)object;
        object.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        ((NestedScrollView)object).setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener(){

            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int n, int n2, int n3, int n4) {
            }
        });
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mTouchDirectionX);
        stringBuilder.append(" , ");
        stringBuilder.append(this.mTouchDirectionY);
        return stringBuilder.toString();
    }

}

