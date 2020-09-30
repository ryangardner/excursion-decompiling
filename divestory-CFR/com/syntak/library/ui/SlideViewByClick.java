/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 */
package com.syntak.library.ui;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.syntak.library.R;
import com.syntak.library.UiOp;

public class SlideViewByClick {
    boolean auto_sizing = false;
    View back;
    CLICK_TYPE click_type_for_off = CLICK_TYPE.SHORT;
    CLICK_TYPE click_type_for_on = CLICK_TYPE.LONG;
    SLIDE_DIRECTION direction;
    View front;
    final int id_tag = R.id.tag_9;
    int size_back = 0;
    int size_front = 0;
    int thickness_front = 0;
    TOWARD toward;

    public SlideViewByClick(View view, View view2, SLIDE_DIRECTION sLIDE_DIRECTION, TOWARD tOWARD) {
        this.front = view;
        this.back = view2;
        this.direction = sLIDE_DIRECTION;
        this.toward = tOWARD;
    }

    public SlideViewByClick(View view, View view2, SLIDE_DIRECTION sLIDE_DIRECTION, TOWARD tOWARD, CLICK_TYPE cLICK_TYPE, CLICK_TYPE cLICK_TYPE2) {
        this.front = view;
        this.back = view2;
        this.direction = sLIDE_DIRECTION;
        this.toward = tOWARD;
        this.click_type_for_on = cLICK_TYPE;
        this.click_type_for_off = cLICK_TYPE2;
    }

    private void back_resizing() {
        this.back.post(new Runnable(){

            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = SlideViewByClick.this.back.getLayoutParams();
                if (SlideViewByClick.this.direction == SLIDE_DIRECTION.HORIZONTAL) {
                    layoutParams.height = SlideViewByClick.this.thickness_front;
                } else {
                    layoutParams.width = SlideViewByClick.this.thickness_front;
                }
                SlideViewByClick.this.back.setLayoutParams(layoutParams);
            }
        });
    }

    private void click_off(View view) {
        if ((Integer)view.getTag(this.id_tag) == 0) {
            this.OnFrontClick(view);
            return;
        }
        view.setTag(this.id_tag, (Object)0);
        UiOp.TRANSLATION_AXIS tRANSLATION_AXIS = this.direction == SLIDE_DIRECTION.HORIZONTAL ? UiOp.TRANSLATION_AXIS.X : UiOp.TRANSLATION_AXIS.Y;
        UiOp.viewAnimation(view, tRANSLATION_AXIS, 0.0f, 300, UiOp.INTERPOLATION_TYPE.ACCEL_DECEL, true);
    }

    private void click_on(View view) {
        if ((Integer)view.getTag(this.id_tag) != 0) return;
        view.setTag(this.id_tag, (Object)1);
        UiOp.TRANSLATION_AXIS tRANSLATION_AXIS = this.direction == SLIDE_DIRECTION.HORIZONTAL ? UiOp.TRANSLATION_AXIS.X : UiOp.TRANSLATION_AXIS.Y;
        int n = this.toward == TOWARD.LEAD ? -this.size_back : this.size_back;
        UiOp.viewAnimation(view, tRANSLATION_AXIS, n, 300, UiOp.INTERPOLATION_TYPE.ACCEL_DECEL, true);
    }

    public void OnFrontClick(View view) {
    }

    public void reset() {
        if ((Integer)this.front.getTag(this.id_tag) != 1) return;
        this.front.setTag(this.id_tag, (Object)0);
        View view = this.front;
        UiOp.TRANSLATION_AXIS tRANSLATION_AXIS = this.direction == SLIDE_DIRECTION.HORIZONTAL ? UiOp.TRANSLATION_AXIS.X : UiOp.TRANSLATION_AXIS.Y;
        UiOp.viewAnimation(view, tRANSLATION_AXIS, 0.0f, 1, UiOp.INTERPOLATION_TYPE.ACCEL_DECEL, true);
    }

    public SlideViewByClick setAutoSizing(boolean bl) {
        this.auto_sizing = bl;
        return this;
    }

    public void start() {
        if (this.click_type_for_on == CLICK_TYPE.SHORT) {
            this.front.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    SlideViewByClick.this.click_on(view);
                }
            });
        } else {
            this.front.setOnLongClickListener(new View.OnLongClickListener(){

                public boolean onLongClick(View view) {
                    SlideViewByClick.this.click_on(view);
                    return true;
                }
            });
        }
        if (this.click_type_for_off == CLICK_TYPE.SHORT) {
            this.front.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    SlideViewByClick.this.click_off(view);
                }
            });
        } else {
            this.front.setOnLongClickListener(new View.OnLongClickListener(){

                public boolean onLongClick(View view) {
                    SlideViewByClick.this.click_off(view);
                    return true;
                }
            });
        }
        this.front.setTag(this.id_tag, (Object)0);
        View view = this.back;
        if (view != null && (view = view.getViewTreeObserver()).isAlive()) {
            view.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

                public void onGlobalLayout() {
                    SlideViewByClick.this.back.getViewTreeObserver().removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
                    if (SlideViewByClick.this.direction == SLIDE_DIRECTION.HORIZONTAL) {
                        SlideViewByClick slideViewByClick = SlideViewByClick.this;
                        slideViewByClick.size_back = slideViewByClick.back.getWidth();
                    } else {
                        SlideViewByClick slideViewByClick = SlideViewByClick.this;
                        slideViewByClick.size_back = slideViewByClick.back.getHeight();
                    }
                    if (!SlideViewByClick.this.auto_sizing) return;
                    if (SlideViewByClick.this.thickness_front <= 0) return;
                    SlideViewByClick.this.back_resizing();
                }
            });
        }
        if ((view = this.front) == null) return;
        if (!(view = view.getViewTreeObserver()).isAlive()) return;
        view.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            public void onGlobalLayout() {
                SlideViewByClick.this.front.getViewTreeObserver().removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
                if (SlideViewByClick.this.direction == SLIDE_DIRECTION.HORIZONTAL) {
                    SlideViewByClick slideViewByClick = SlideViewByClick.this;
                    slideViewByClick.size_front = slideViewByClick.front.getWidth();
                    slideViewByClick = SlideViewByClick.this;
                    slideViewByClick.thickness_front = slideViewByClick.front.getHeight();
                } else {
                    SlideViewByClick slideViewByClick = SlideViewByClick.this;
                    slideViewByClick.size_front = slideViewByClick.front.getHeight();
                    slideViewByClick = SlideViewByClick.this;
                    slideViewByClick.thickness_front = slideViewByClick.front.getWidth();
                }
                if (!SlideViewByClick.this.auto_sizing) return;
                if (SlideViewByClick.this.size_back <= 0) return;
                SlideViewByClick.this.back_resizing();
            }
        });
    }

    public void stop() {
        this.click_off(this.front);
    }

    public static final class CLICK_TYPE
    extends Enum<CLICK_TYPE> {
        private static final /* synthetic */ CLICK_TYPE[] $VALUES;
        public static final /* enum */ CLICK_TYPE LONG;
        public static final /* enum */ CLICK_TYPE SHORT;

        static {
            CLICK_TYPE cLICK_TYPE;
            SHORT = new CLICK_TYPE();
            LONG = cLICK_TYPE = new CLICK_TYPE();
            $VALUES = new CLICK_TYPE[]{SHORT, cLICK_TYPE};
        }

        public static CLICK_TYPE valueOf(String string2) {
            return Enum.valueOf(CLICK_TYPE.class, string2);
        }

        public static CLICK_TYPE[] values() {
            return (CLICK_TYPE[])$VALUES.clone();
        }
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

    public static final class TOWARD
    extends Enum<TOWARD> {
        private static final /* synthetic */ TOWARD[] $VALUES;
        public static final /* enum */ TOWARD LEAD;
        public static final /* enum */ TOWARD TRAIL;

        static {
            TOWARD tOWARD;
            LEAD = new TOWARD();
            TRAIL = tOWARD = new TOWARD();
            $VALUES = new TOWARD[]{LEAD, tOWARD};
        }

        public static TOWARD valueOf(String string2) {
            return Enum.valueOf(TOWARD.class, string2);
        }

        public static TOWARD[] values() {
            return (TOWARD[])$VALUES.clone();
        }
    }

}

