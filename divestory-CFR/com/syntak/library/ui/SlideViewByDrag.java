/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.ObjectAnimator
 *  android.animation.TimeInterpolator
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 */
package com.syntak.library.ui;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.syntak.library.TimeOp;

public class SlideViewByDrag {
    private int THRESHOLD_CLICK_WITHIN_RADIUS = 10;
    private int THRESHOLD_CLICK_WITH_TIME_ELAPSED = 200;
    private float THRESHOLD_SWIPE_FRACTION_OF_VIEW = 0.3f;
    private float THRESHOLD_SWIPE_SPEED = 600.0f;
    private int animation_interval = 300;
    public View container = null;
    boolean isAnimated = false;
    boolean isContainerFound = false;
    boolean isOverlapped = false;
    boolean isSettleFound = false;
    boolean isSizeFound_L = false;
    boolean isSizeFound_T = false;
    public View ref_L = null;
    public View ref_T = null;
    float settle_L;
    float settle_M;
    float settle_T;
    int size_L = 0;
    int size_T = 0;
    SLIDE_DIRECTION slide_direction = SLIDE_DIRECTION.HORIZONTAL;
    public View view_L = null;
    public View view_M = null;
    public View view_T = null;

    public SlideViewByDrag(View view, View view2, View view3, View view4, View view5, View view6, SLIDE_DIRECTION sLIDE_DIRECTION) {
        this.isOverlapped = false;
        this.view_L = view;
        this.view_M = view2;
        this.view_T = view3;
        if (view4 != null) {
            view = view4;
        }
        this.ref_L = view;
        if (view5 != null) {
            view3 = view5;
        }
        this.ref_T = view3;
        this.container = view6;
        this.slide_direction = sLIDE_DIRECTION;
    }

    public SlideViewByDrag(View view, View view2, View view3, SLIDE_DIRECTION sLIDE_DIRECTION) {
        this.isOverlapped = true;
        this.view_L = view;
        this.view_M = view2;
        this.view_T = view3;
        this.ref_L = view;
        this.ref_T = view3;
        this.container = view2;
        this.slide_direction = sLIDE_DIRECTION;
    }

    static /* synthetic */ int access$100(SlideViewByDrag slideViewByDrag) {
        return slideViewByDrag.THRESHOLD_CLICK_WITHIN_RADIUS;
    }

    static /* synthetic */ int access$200(SlideViewByDrag slideViewByDrag) {
        return slideViewByDrag.THRESHOLD_CLICK_WITH_TIME_ELAPSED;
    }

    static /* synthetic */ float access$300(SlideViewByDrag slideViewByDrag) {
        return slideViewByDrag.THRESHOLD_SWIPE_FRACTION_OF_VIEW;
    }

    static /* synthetic */ float access$400(SlideViewByDrag slideViewByDrag) {
        return slideViewByDrag.THRESHOLD_SWIPE_SPEED;
    }

    static /* synthetic */ int access$500(SlideViewByDrag slideViewByDrag) {
        return slideViewByDrag.animation_interval;
    }

    static /* synthetic */ void access$600(SlideViewByDrag slideViewByDrag, float f, boolean bl, long l) {
        slideViewByDrag.move_container(f, bl, l);
    }

    private void change_container() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)this.container.getLayoutParams();
        if (this.slide_direction == SLIDE_DIRECTION.HORIZONTAL) {
            marginLayoutParams.leftMargin -= this.size_L;
            marginLayoutParams.rightMargin -= this.size_T;
        } else {
            marginLayoutParams.topMargin -= this.size_L;
            marginLayoutParams.bottomMargin -= this.size_T;
        }
        this.container.setLayoutParams((ViewGroup.LayoutParams)marginLayoutParams);
        float f = this.slide_direction == SLIDE_DIRECTION.HORIZONTAL ? this.container.getX() : this.container.getY();
        this.settle_M = f;
    }

    private void check_parameters() {
        if (this.view_M == null) {
            Log.d((String)"PrepareHori..SlideViews", (String)"Middle view cannot be null!");
        }
        if (this.container == null) {
            Log.d((String)"PrepareSlideViews", (String)"Contianer view cannot be null!");
        }
        if (this.view_L == null && this.view_T == null) {
            Log.d((String)"PrepareSlideViews", (String)"Leading & Tailing views cannot be null at the same time!");
        }
        if (this.ref_L != null) return;
        if (this.ref_T != null) return;
        Log.d((String)"PrepareSlideViews", (String)"Leading & Tailing reference views cannot be null at the same time!");
    }

    private void move_container(float f, boolean bl, long l) {
        float f2 = this.slide_direction == SLIDE_DIRECTION.HORIZONTAL ? this.container.getX() : this.container.getY();
        f2 = f - f2;
        View view = null;
        if (this.slide_direction == SLIDE_DIRECTION.HORIZONTAL) {
            if (bl) {
                view = this.container;
                view = ObjectAnimator.ofFloat((Object)view, (String)"translationX", (float[])new float[]{view.getTranslationX() + f2});
            } else {
                this.container.setX(f);
            }
        } else if (bl) {
            view = this.container;
            view = ObjectAnimator.ofFloat((Object)view, (String)"translationY", (float[])new float[]{view.getTranslationY() + f2});
        } else {
            this.container.setY(f);
        }
        if (!bl) return;
        view.setDuration(l);
        view.setInterpolator(new TimeInterpolator(){

            public float getInterpolation(float f) {
                return 2.0f * f - f * f;
            }
        });
        view.start();
    }

    public void OnMidViewClicked(View view, float f, long l) {
    }

    public void OnTouchDown() {
    }

    public void OnTouchUp() {
    }

    public void reset() {
        this.move_container(this.settle_M, false, 0L);
    }

    public SlideViewByDrag setAnimation(boolean bl) {
        this.isAnimated = bl;
        return this;
    }

    public SlideViewByDrag setAnimationInterval(int n) {
        this.animation_interval = n;
        return this;
    }

    public SlideViewByDrag setClickThresholds(int n, int n2) {
        this.THRESHOLD_CLICK_WITHIN_RADIUS = n;
        this.THRESHOLD_CLICK_WITH_TIME_ELAPSED = n2;
        return this;
    }

    public SlideViewByDrag setSwipeThresholds(int n, float f) {
        this.THRESHOLD_SWIPE_FRACTION_OF_VIEW = n;
        this.THRESHOLD_SWIPE_SPEED = f;
        return this;
    }

    public void start() {
        this.check_parameters();
        View view = this.ref_L;
        if (view != null) {
            if ((view = view.getViewTreeObserver()).isAlive()) {
                view.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

                    public void onGlobalLayout() {
                        SlideViewByDrag.this.ref_L.getViewTreeObserver().removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
                        SlideViewByDrag slideViewByDrag = SlideViewByDrag.this;
                        int n = slideViewByDrag.slide_direction == SLIDE_DIRECTION.HORIZONTAL ? SlideViewByDrag.this.ref_L.getWidth() : SlideViewByDrag.this.ref_L.getHeight();
                        slideViewByDrag.size_L = n;
                        SlideViewByDrag.this.isSizeFound_L = true;
                        if (SlideViewByDrag.this.isOverlapped) return;
                        if (!SlideViewByDrag.this.isContainerFound) return;
                        if (!SlideViewByDrag.this.isSizeFound_T) return;
                        SlideViewByDrag.this.change_container();
                    }
                });
            }
        } else {
            this.isSizeFound_L = true;
        }
        if ((view = this.ref_T) != null) {
            if ((view = view.getViewTreeObserver()).isAlive()) {
                view.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

                    public void onGlobalLayout() {
                        SlideViewByDrag.this.ref_T.getViewTreeObserver().removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
                        SlideViewByDrag slideViewByDrag = SlideViewByDrag.this;
                        int n = slideViewByDrag.slide_direction == SLIDE_DIRECTION.HORIZONTAL ? SlideViewByDrag.this.ref_T.getWidth() : SlideViewByDrag.this.ref_T.getHeight();
                        slideViewByDrag.size_T = n;
                        SlideViewByDrag.this.isSizeFound_T = true;
                        if (SlideViewByDrag.this.isOverlapped) return;
                        if (!SlideViewByDrag.this.isContainerFound) return;
                        if (!SlideViewByDrag.this.isSizeFound_L) return;
                        SlideViewByDrag.this.change_container();
                    }
                });
            }
        } else {
            this.isSizeFound_T = true;
        }
        if ((view = this.container.getViewTreeObserver()).isAlive()) {
            view.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

                public void onGlobalLayout() {
                    SlideViewByDrag.this.container.getViewTreeObserver().removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
                    SlideViewByDrag.this.isContainerFound = true;
                    if (!SlideViewByDrag.this.isOverlapped && SlideViewByDrag.this.isSizeFound_L && SlideViewByDrag.this.isSizeFound_T) {
                        SlideViewByDrag.this.change_container();
                    }
                    SlideViewByDrag slideViewByDrag = SlideViewByDrag.this;
                    float f = slideViewByDrag.slide_direction == SLIDE_DIRECTION.HORIZONTAL ? SlideViewByDrag.this.container.getX() : SlideViewByDrag.this.container.getY();
                    slideViewByDrag.settle_M = f;
                }
            });
        }
        this.container.setOnTouchListener(new View.OnTouchListener(){
            float container_current;
            float container_moved;
            float contrainer_start;
            int lastAction;
            private VelocityTracker mVelocityTracker = null;
            long ms_down;
            long ms_up;
            float pointer_current;
            float pointer_moved = 0.0f;
            float pointer_start;
            float start_y;
            boolean toward_trail;
            float view_move_offset;
            float vx;
            float vy;

            /*
             * Unable to fully structure code
             */
            public boolean onTouch(View var1_1, MotionEvent var2_2) {
                block28 : {
                    if (!SlideViewByDrag.this.isSettleFound) {
                        var1_1 = SlideViewByDrag.this;
                        var3_3 /* !! */  = var1_1.slide_direction == SLIDE_DIRECTION.HORIZONTAL ? SlideViewByDrag.this.container.getX() : SlideViewByDrag.this.container.getY();
                        var1_1.settle_M = var3_3 /* !! */ ;
                        var1_1 = SlideViewByDrag.this;
                        var1_1.settle_L = var1_1.settle_M + (float)SlideViewByDrag.this.size_L;
                        var1_1 = SlideViewByDrag.this;
                        var1_1.settle_T = var1_1.settle_M - (float)SlideViewByDrag.this.size_T;
                        SlideViewByDrag.this.isSettleFound = true;
                    }
                    var4_4 = var2_2.getActionMasked();
                    var5_5 = false;
                    var6_6 = false;
                    if (var4_4 == 0) break block28;
                    if (var4_4 == 1) ** GOTO lbl59
                    if (var4_4 != 2) {
                        if (var4_4 != 3) {
                            return false;
                        }
                    } else {
                        this.mVelocityTracker.addMovement((MotionEvent)var2_2);
                        this.mVelocityTracker.computeCurrentVelocity(1000);
                        this.vx = this.mVelocityTracker.getXVelocity();
                        this.vy = this.mVelocityTracker.getYVelocity();
                        var3_3 /* !! */  = SlideViewByDrag.this.slide_direction == SLIDE_DIRECTION.HORIZONTAL ? var2_2.getX() : var2_2.getY();
                        this.pointer_current = var3_3 /* !! */ ;
                        this.pointer_moved = var3_3 /* !! */  - this.pointer_start;
                        var3_3 /* !! */  = SlideViewByDrag.this.slide_direction == SLIDE_DIRECTION.HORIZONTAL ? SlideViewByDrag.this.container.getX() : SlideViewByDrag.this.container.getY();
                        this.container_current = var3_3 /* !! */ ;
                        this.container_moved = var3_3 /* !! */  - this.contrainer_start;
                        this.view_move_offset = var3_3 /* !! */  = this.pointer_moved;
                        if (var3_3 /* !! */  < 0.0f) {
                            var6_6 = true;
                        }
                        this.toward_trail = var6_6;
                        if (this.contrainer_start == SlideViewByDrag.this.settle_M) {
                            if (this.toward_trail) {
                                if (Math.abs(this.pointer_moved) >= (float)SlideViewByDrag.this.size_T) {
                                    this.pointer_moved = SlideViewByDrag.this.settle_T - SlideViewByDrag.this.settle_M;
                                }
                            } else if (Math.abs(this.pointer_moved) > (float)SlideViewByDrag.this.size_L) {
                                this.pointer_moved = SlideViewByDrag.this.settle_L - SlideViewByDrag.this.settle_M;
                            }
                        } else if (this.contrainer_start == SlideViewByDrag.this.settle_L) {
                            if (this.toward_trail) {
                                if (Math.abs(this.pointer_moved) > (float)SlideViewByDrag.this.size_L) {
                                    this.pointer_moved = SlideViewByDrag.this.settle_M - SlideViewByDrag.this.settle_L;
                                }
                            } else {
                                this.pointer_moved = 0.0f;
                            }
                        } else if (this.toward_trail) {
                            this.pointer_moved = 0.0f;
                        } else if (Math.abs(this.pointer_moved) > (float)SlideViewByDrag.this.size_T) {
                            this.pointer_moved = SlideViewByDrag.this.settle_M - SlideViewByDrag.this.settle_T;
                        }
                        if (SlideViewByDrag.this.slide_direction == SLIDE_DIRECTION.HORIZONTAL) {
                            SlideViewByDrag.this.container.setX(this.contrainer_start + this.pointer_moved);
                        } else {
                            SlideViewByDrag.this.container.setY(this.contrainer_start + this.pointer_moved);
                        }
                        this.lastAction = 2;
                        return true;
lbl59: // 1 sources:
                        SlideViewByDrag.this.OnTouchUp();
                    }
                    var3_3 /* !! */  = SlideViewByDrag.this.slide_direction == SLIDE_DIRECTION.HORIZONTAL ? SlideViewByDrag.this.container.getX() : SlideViewByDrag.this.container.getY();
                    this.container_current = var3_3 /* !! */ ;
                    this.container_moved = var3_3 /* !! */  - this.contrainer_start;
                    this.ms_up = var7_7 = TimeOp.getNow();
                    if (Math.abs(this.container_moved) < (float)SlideViewByDrag.access$100(SlideViewByDrag.this) && (var7_7 -= this.ms_down) < (long)SlideViewByDrag.access$200(SlideViewByDrag.this)) {
                        var1_1 = SlideViewByDrag.this;
                        var1_1.OnMidViewClicked(var1_1.view_M, this.container_moved, var7_7);
                    }
                    var3_3 /* !! */  = SlideViewByDrag.this.slide_direction == SLIDE_DIRECTION.HORIZONTAL ? this.vx : this.vy;
                    var9_8 = SlideViewByDrag.this.slide_direction == SLIDE_DIRECTION.HORIZONTAL ? var2_2.getX() : var2_2.getY();
                    this.pointer_current = var9_8;
                    this.pointer_moved = var9_8 -= this.pointer_start;
                    var6_6 = var5_5;
                    if (var9_8 < 0.0f) {
                        var6_6 = true;
                    }
                    this.toward_trail = var6_6;
                    var3_3 /* !! */  = this.contrainer_start == SlideViewByDrag.this.settle_M ? (this.toward_trail ? (!(Math.abs(this.pointer_moved) > (float)SlideViewByDrag.this.size_T * SlideViewByDrag.access$300(SlideViewByDrag.this)) && !(Math.abs(var3_3 /* !! */ ) > SlideViewByDrag.access$400(SlideViewByDrag.this)) ? SlideViewByDrag.this.settle_M : SlideViewByDrag.this.settle_T) : (!(Math.abs(this.pointer_moved) > (float)SlideViewByDrag.this.size_L * SlideViewByDrag.access$300(SlideViewByDrag.this)) && !(Math.abs(var3_3 /* !! */ ) > SlideViewByDrag.access$400(SlideViewByDrag.this)) ? SlideViewByDrag.this.settle_M : SlideViewByDrag.this.settle_L)) : (this.contrainer_start == SlideViewByDrag.this.settle_L ? (this.toward_trail ? (!(Math.abs(this.pointer_moved) > (float)SlideViewByDrag.this.size_L * SlideViewByDrag.access$300(SlideViewByDrag.this)) && !(Math.abs(var3_3 /* !! */ ) > SlideViewByDrag.access$400(SlideViewByDrag.this)) ? SlideViewByDrag.this.settle_L : SlideViewByDrag.this.settle_M) : SlideViewByDrag.this.settle_L) : (this.toward_trail ? SlideViewByDrag.this.settle_T : (!(Math.abs(this.pointer_moved) > (float)SlideViewByDrag.this.size_T * SlideViewByDrag.access$300(SlideViewByDrag.this)) && !(Math.abs(var3_3 /* !! */ ) > SlideViewByDrag.access$400(SlideViewByDrag.this)) ? SlideViewByDrag.this.settle_T : SlideViewByDrag.this.settle_M)));
                    var1_1 = SlideViewByDrag.this;
                    SlideViewByDrag.access$600((SlideViewByDrag)var1_1, var3_3 /* !! */ , var1_1.isAnimated, SlideViewByDrag.access$500(SlideViewByDrag.this));
                    return true;
                }
                SlideViewByDrag.this.OnTouchDown();
                this.ms_down = TimeOp.getNow();
                this.lastAction = 0;
                var3_3 /* !! */  = SlideViewByDrag.this.slide_direction == SLIDE_DIRECTION.HORIZONTAL ? var2_2.getX() : var2_2.getY();
                this.pointer_start = var3_3 /* !! */ ;
                this.pointer_moved = 0.0f;
                var1_1 = this.mVelocityTracker;
                if (var1_1 == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                } else {
                    var1_1.clear();
                }
                this.mVelocityTracker.addMovement((MotionEvent)var2_2);
                var3_3 /* !! */  = SlideViewByDrag.this.slide_direction == SLIDE_DIRECTION.HORIZONTAL ? SlideViewByDrag.this.container.getX() : SlideViewByDrag.this.container.getY();
                this.container_current = var3_3 /* !! */ ;
                var1_1 = new float[]{Math.abs(var3_3 /* !! */  - SlideViewByDrag.this.settle_L), Math.abs(this.container_current - SlideViewByDrag.this.settle_M), Math.abs(this.container_current - SlideViewByDrag.this.settle_T)};
                var2_2 = new float[]{SlideViewByDrag.this.settle_L, SlideViewByDrag.this.settle_M, SlideViewByDrag.this.settle_T};
                var10_10 /* !! */  = var1_1[0];
                var9_9 = var2_2[0];
                var4_4 = 1;
                do {
                    if (var4_4 >= 3) {
                        this.contrainer_start = var9_9;
                        return true;
                    }
                    var3_3 /* !! */  = (float)var10_10 /* !! */ ;
                    if (var1_1[var4_4] < var10_10 /* !! */ ) {
                        var3_3 /* !! */  = (float)var1_1[var4_4];
                        var9_9 = var2_2[var4_4];
                    }
                    ++var4_4;
                    var10_10 /* !! */  = var3_3 /* !! */ ;
                } while (true);
            }
        });
    }

    public void stop() {
    }

    public static final class SLIDE_DIRECTION
    extends Enum<SLIDE_DIRECTION> {
        private static final /* synthetic */ SLIDE_DIRECTION[] $VALUES;
        public static final /* enum */ SLIDE_DIRECTION HORIZONTAL;
        public static final /* enum */ SLIDE_DIRECTION VERTICAL;

        static {
            SLIDE_DIRECTION sLIDE_DIRECTION;
            HORIZONTAL = new SLIDE_DIRECTION();
            VERTICAL = sLIDE_DIRECTION = new SLIDE_DIRECTION();
            $VALUES = new SLIDE_DIRECTION[]{HORIZONTAL, sLIDE_DIRECTION};
        }

        public static SLIDE_DIRECTION valueOf(String string2) {
            return Enum.valueOf(SLIDE_DIRECTION.class, string2);
        }

        public static SLIDE_DIRECTION[] values() {
            return (SLIDE_DIRECTION[])$VALUES.clone();
        }
    }

}

