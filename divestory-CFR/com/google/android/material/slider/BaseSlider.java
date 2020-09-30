/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Style
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Rect
 *  android.graphics.Region
 *  android.graphics.Region$Op
 *  android.graphics.Xfermode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.RippleDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityManager
 *  android.widget.SeekBar
 */
package com.google.android.material.slider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.SeekBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.R;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewOverlayImpl;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.slider.BaseOnChangeListener;
import com.google.android.material.slider.BaseOnSliderTouchListener;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.google.android.material.tooltip.TooltipDrawable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

abstract class BaseSlider<S extends BaseSlider<S, L, T>, L extends BaseOnChangeListener<S>, T extends BaseOnSliderTouchListener<S>>
extends View {
    private static final int DEF_STYLE_RES;
    private static final String EXCEPTION_ILLEGAL_DISCRETE_VALUE = "Value(%s) must be equal to valueFrom(%s) plus a multiple of stepSize(%s) when using stepSize(%s)";
    private static final String EXCEPTION_ILLEGAL_STEP_SIZE = "The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range";
    private static final String EXCEPTION_ILLEGAL_VALUE = "Slider value(%s) must be greater or equal to valueFrom(%s), and lower or equal to valueTo(%s)";
    private static final String EXCEPTION_ILLEGAL_VALUE_FROM = "valueFrom(%s) must be smaller than valueTo(%s)";
    private static final String EXCEPTION_ILLEGAL_VALUE_TO = "valueTo(%s) must be greater than valueFrom(%s)";
    private static final int HALO_ALPHA = 63;
    private static final String TAG;
    private static final double THRESHOLD = 1.0E-4;
    private static final int TIMEOUT_SEND_ACCESSIBILITY_EVENT = 200;
    private static final String WARNING_FLOATING_POINT_ERRROR = "Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the  value correctly.";
    private BaseSlider<S, L, T> accessibilityEventSender;
    private final AccessibilityHelper accessibilityHelper;
    private final AccessibilityManager accessibilityManager;
    private int activeThumbIdx = -1;
    private final Paint activeTicksPaint;
    private final Paint activeTrackPaint;
    private final List<L> changeListeners = new ArrayList<L>();
    private boolean dirtyConfig;
    private int focusedThumbIdx = -1;
    private boolean forceDrawCompatHalo;
    private LabelFormatter formatter;
    private ColorStateList haloColor;
    private final Paint haloPaint;
    private int haloRadius;
    private final Paint inactiveTicksPaint;
    private final Paint inactiveTrackPaint;
    private boolean isLongPress = false;
    private int labelBehavior;
    private final TooltipDrawableFactory labelMaker;
    private int labelPadding;
    private final List<TooltipDrawable> labels = new ArrayList<TooltipDrawable>();
    private MotionEvent lastEvent;
    private final int scaledTouchSlop;
    private float stepSize = 0.0f;
    private final MaterialShapeDrawable thumbDrawable = new MaterialShapeDrawable();
    private boolean thumbIsPressed = false;
    private final Paint thumbPaint;
    private int thumbRadius;
    private ColorStateList tickColorActive;
    private ColorStateList tickColorInactive;
    private float[] ticksCoordinates;
    private float touchDownX;
    private final List<T> touchListeners = new ArrayList<T>();
    private float touchPosition;
    private ColorStateList trackColorActive;
    private ColorStateList trackColorInactive;
    private int trackHeight;
    private int trackSidePadding;
    private int trackTop;
    private int trackWidth;
    private float valueFrom;
    private float valueTo;
    private ArrayList<Float> values = new ArrayList();
    private int widgetHeight;

    static {
        TAG = BaseSlider.class.getSimpleName();
        DEF_STYLE_RES = R.style.Widget_MaterialComponents_Slider;
    }

    public BaseSlider(Context context) {
        this(context, null);
    }

    public BaseSlider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.sliderStyle);
    }

    public BaseSlider(Context object, final AttributeSet attributeSet, final int n) {
        super(MaterialThemeOverlay.wrap(object, attributeSet, n, DEF_STYLE_RES), attributeSet, n);
        Paint paint;
        object = this.getContext();
        this.inactiveTrackPaint = paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        this.inactiveTrackPaint.setStrokeCap(Paint.Cap.ROUND);
        this.activeTrackPaint = paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        this.activeTrackPaint.setStrokeCap(Paint.Cap.ROUND);
        this.thumbPaint = paint = new Paint(1);
        paint.setStyle(Paint.Style.FILL);
        this.thumbPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.haloPaint = paint = new Paint(1);
        paint.setStyle(Paint.Style.FILL);
        this.inactiveTicksPaint = paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        this.inactiveTicksPaint.setStrokeCap(Paint.Cap.ROUND);
        this.activeTicksPaint = paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        this.activeTicksPaint.setStrokeCap(Paint.Cap.ROUND);
        this.loadResources(object.getResources());
        this.labelMaker = new TooltipDrawableFactory(){

            @Override
            public TooltipDrawable createTooltipDrawable() {
                TypedArray typedArray = ThemeEnforcement.obtainStyledAttributes(BaseSlider.this.getContext(), attributeSet, R.styleable.Slider, n, DEF_STYLE_RES, new int[0]);
                TooltipDrawable tooltipDrawable = BaseSlider.parseLabelDrawable(BaseSlider.this.getContext(), typedArray);
                typedArray.recycle();
                return tooltipDrawable;
            }
        };
        this.processAttributes((Context)object, attributeSet, n);
        this.setFocusable(true);
        this.setClickable(true);
        this.thumbDrawable.setShadowCompatibilityMode(2);
        this.scaledTouchSlop = ViewConfiguration.get((Context)object).getScaledTouchSlop();
        object = new AccessibilityHelper(this);
        this.accessibilityHelper = object;
        ViewCompat.setAccessibilityDelegate(this, (AccessibilityDelegateCompat)object);
        this.accessibilityManager = (AccessibilityManager)this.getContext().getSystemService("accessibility");
    }

    private void attachLabelToContentView(TooltipDrawable tooltipDrawable) {
        tooltipDrawable.setRelativeToView((View)ViewUtils.getContentView(this));
    }

    private Float calculateIncrementForKey(int n) {
        float f = this.isLongPress ? this.calculateStepIncrement(20) : this.calculateStepIncrement();
        if (n != 21) {
            if (n != 22) {
                if (n == 69) return Float.valueOf(-f);
                if (n == 70) return Float.valueOf(f);
                if (n == 81) return Float.valueOf(f);
                return null;
            }
            float f2 = f;
            if (!this.isRtl()) return Float.valueOf(f2);
            f2 = -f;
            return Float.valueOf(f2);
        }
        if (this.isRtl()) {
            return Float.valueOf(f);
        }
        f = -f;
        return Float.valueOf(f);
    }

    private float calculateStepIncrement() {
        float f;
        float f2 = f = this.stepSize;
        if (f != 0.0f) return f2;
        return 1.0f;
    }

    private float calculateStepIncrement(int n) {
        float f;
        float f2 = this.calculateStepIncrement();
        float f3 = (this.valueTo - this.valueFrom) / f2;
        if (!(f3 <= (f = (float)n))) return (float)Math.round(f3 / f) * f2;
        return f2;
    }

    private void calculateTicksCoordinates() {
        this.validateConfigurationIfDirty();
        int n = Math.min((int)((this.valueTo - this.valueFrom) / this.stepSize + 1.0f), this.trackWidth / (this.trackHeight * 2) + 1);
        float[] arrf = this.ticksCoordinates;
        if (arrf == null || arrf.length != n * 2) {
            this.ticksCoordinates = new float[n * 2];
        }
        float f = (float)this.trackWidth / (float)(n - 1);
        int n2 = 0;
        while (n2 < n * 2) {
            arrf = this.ticksCoordinates;
            arrf[n2] = (float)this.trackSidePadding + (float)(n2 / 2) * f;
            arrf[n2 + 1] = this.calculateTop();
            n2 += 2;
        }
    }

    private int calculateTop() {
        int n = this.trackTop;
        int n2 = this.labelBehavior;
        int n3 = 0;
        if (n2 != 1) return n + n3;
        n3 = this.labels.get(0).getIntrinsicHeight();
        return n + n3;
    }

    private void createLabelPool() {
        if (this.labels.size() > this.values.size()) {
            List<TooltipDrawable> list = this.labels.subList(this.values.size(), this.labels.size());
            for (TooltipDrawable object2 : list) {
                if (!ViewCompat.isAttachedToWindow(this)) continue;
                this.detachLabelFromContentView(object2);
            }
            list.clear();
        }
        while (this.labels.size() < this.values.size()) {
            TooltipDrawable tooltipDrawable = this.labelMaker.createTooltipDrawable();
            this.labels.add(tooltipDrawable);
            if (!ViewCompat.isAttachedToWindow(this)) continue;
            this.attachLabelToContentView(tooltipDrawable);
        }
        int n = this.labels.size();
        boolean bl = true;
        if (n == 1) {
            bl = false;
        }
        Iterator<TooltipDrawable> iterator2 = this.labels.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().setStrokeWidth((float)bl);
        }
    }

    private void detachLabelFromContentView(TooltipDrawable tooltipDrawable) {
        ViewOverlayImpl viewOverlayImpl = ViewUtils.getContentViewOverlay(this);
        if (viewOverlayImpl == null) return;
        viewOverlayImpl.remove(tooltipDrawable);
        tooltipDrawable.detachView((View)ViewUtils.getContentView(this));
    }

    private void dispatchOnChangedFromUser(int n) {
        AccessibilityManager accessibilityManager = this.changeListeners.iterator();
        do {
            if (!accessibilityManager.hasNext()) {
                accessibilityManager = this.accessibilityManager;
                if (accessibilityManager == null) return;
                if (!accessibilityManager.isEnabled()) return;
                this.scheduleAccessibilityEventSender(n);
                return;
            }
            ((BaseOnChangeListener)accessibilityManager.next()).onValueChange(this, this.values.get(n).floatValue(), true);
        } while (true);
    }

    private void dispatchOnChangedProgramatically() {
        Iterator<L> iterator2 = this.changeListeners.iterator();
        block0 : while (iterator2.hasNext()) {
            BaseOnChangeListener baseOnChangeListener = (BaseOnChangeListener)iterator2.next();
            Iterator<Float> iterator3 = this.values.iterator();
            do {
                if (!iterator3.hasNext()) continue block0;
                baseOnChangeListener.onValueChange(this, iterator3.next().floatValue(), false);
            } while (true);
            break;
        }
        return;
    }

    private void drawActiveTrack(Canvas canvas, int n, int n2) {
        float[] arrf = this.getActiveRange();
        int n3 = this.trackSidePadding;
        float f = n3;
        float f2 = arrf[1];
        float f3 = n;
        float f4 = n3;
        float f5 = arrf[0];
        float f6 = n2;
        canvas.drawLine(f4 + f5 * f3, f6, f + f2 * f3, f6, this.activeTrackPaint);
    }

    private void drawInactiveTrack(Canvas canvas, int n, int n2) {
        float[] arrf = this.getActiveRange();
        int n3 = this.trackSidePadding;
        float f = n3;
        float f2 = arrf[1];
        float f3 = n;
        if ((f2 = f + f2 * f3) < (float)(n3 + n)) {
            f = n2;
            canvas.drawLine(f2, f, (float)(n3 + n), f, this.inactiveTrackPaint);
        }
        if (!((f2 = (float)(n = this.trackSidePadding) + arrf[0] * f3) > (float)n)) return;
        f = n;
        f3 = n2;
        canvas.drawLine(f, f3, f2, f3, this.inactiveTrackPaint);
    }

    private void drawThumbs(Canvas canvas, int n, int n2) {
        if (!this.isEnabled()) {
            for (Float f : this.values) {
                canvas.drawCircle((float)this.trackSidePadding + this.normalizeValue(f.floatValue()) * (float)n, (float)n2, (float)this.thumbRadius, this.thumbPaint);
            }
        }
        Iterator<Float> iterator2 = this.values.iterator();
        while (iterator2.hasNext()) {
            Float f;
            f = iterator2.next();
            canvas.save();
            int n3 = this.trackSidePadding;
            int n4 = (int)(this.normalizeValue(f.floatValue()) * (float)n);
            int n5 = this.thumbRadius;
            canvas.translate((float)(n3 + n4 - n5), (float)(n2 - n5));
            this.thumbDrawable.draw(canvas);
            canvas.restore();
        }
    }

    private void drawTicks(Canvas canvas) {
        float[] arrf = this.getActiveRange();
        int n = BaseSlider.pivotIndex(this.ticksCoordinates, arrf[0]);
        int n2 = BaseSlider.pivotIndex(this.ticksCoordinates, arrf[1]);
        arrf = this.ticksCoordinates;
        canvas.drawPoints(arrf, 0, n *= 2, this.inactiveTicksPaint);
        arrf = this.ticksCoordinates;
        canvas.drawPoints(arrf, n, (n2 *= 2) - n, this.activeTicksPaint);
        arrf = this.ticksCoordinates;
        canvas.drawPoints(arrf, n2, arrf.length - n2, this.inactiveTicksPaint);
    }

    private void ensureLabels() {
        if (this.labelBehavior == 2) {
            return;
        }
        Iterator<TooltipDrawable> iterator2 = this.labels.iterator();
        for (int i = 0; i < this.values.size() && iterator2.hasNext(); ++i) {
            if (i == this.focusedThumbIdx) continue;
            this.setValueForLabel(iterator2.next(), this.values.get(i).floatValue());
        }
        if (!iterator2.hasNext()) throw new IllegalStateException(String.format("Not enough labels(%d) to display all the values(%d)", this.labels.size(), this.values.size()));
        this.setValueForLabel(iterator2.next(), this.values.get(this.focusedThumbIdx).floatValue());
    }

    private void focusThumbOnFocusGained(int n) {
        if (n == 1) {
            this.moveFocus(Integer.MAX_VALUE);
            return;
        }
        if (n == 2) {
            this.moveFocus(Integer.MIN_VALUE);
            return;
        }
        if (n == 17) {
            this.moveFocusInAbsoluteDirection(Integer.MAX_VALUE);
            return;
        }
        if (n != 66) {
            return;
        }
        this.moveFocusInAbsoluteDirection(Integer.MIN_VALUE);
    }

    private String formatValue(float f) {
        if (this.hasLabelFormatter()) {
            return this.formatter.getFormattedValue(f);
        }
        String string2 = (float)((int)f) == f ? "%.0f" : "%.2f";
        return String.format(string2, Float.valueOf(f));
    }

    private float[] getActiveRange() {
        float[] arrf;
        float f = Collections.max(this.getValues()).floatValue();
        float f2 = Collections.min(this.getValues()).floatValue();
        if (this.values.size() == 1) {
            f2 = this.valueFrom;
        }
        f2 = this.normalizeValue(f2);
        f = this.normalizeValue(f);
        if (this.isRtl()) {
            arrf = new float[]{f, f2};
            return arrf;
        }
        arrf = new float[]{f2, f};
        return arrf;
    }

    private float getClampedValue(int n, float f) {
        float f2;
        int n2 = n + 1;
        float f3 = n2 >= this.values.size() ? this.valueTo : this.values.get(n2).floatValue();
        if (--n < 0) {
            f2 = this.valueFrom;
            return MathUtils.clamp(f, f2, f3);
        }
        f2 = this.values.get(n).floatValue();
        return MathUtils.clamp(f, f2, f3);
    }

    private int getColorForState(ColorStateList colorStateList) {
        return colorStateList.getColorForState(this.getDrawableState(), colorStateList.getDefaultColor());
    }

    private float getValueOfTouchPosition() {
        double d;
        double d2 = d = this.snapPosition(this.touchPosition);
        if (this.isRtl()) {
            d2 = 1.0 - d;
        }
        float f = this.valueTo;
        float f2 = this.valueFrom;
        return (float)(d2 * (double)(f - f2) + (double)f2);
    }

    private float getValueOfTouchPositionAbsolute() {
        float f;
        float f2 = f = this.touchPosition;
        if (this.isRtl()) {
            f2 = 1.0f - f;
        }
        f = this.valueTo;
        float f3 = this.valueFrom;
        return f2 * (f - f3) + f3;
    }

    private void invalidateTrack() {
        this.inactiveTrackPaint.setStrokeWidth((float)this.trackHeight);
        this.activeTrackPaint.setStrokeWidth((float)this.trackHeight);
        this.inactiveTicksPaint.setStrokeWidth((float)this.trackHeight / 2.0f);
        this.activeTicksPaint.setStrokeWidth((float)this.trackHeight / 2.0f);
    }

    private boolean isInScrollingContainer() {
        ViewParent viewParent = this.getParent();
        while (viewParent instanceof ViewGroup) {
            if (((ViewGroup)viewParent).shouldDelayChildPressedState()) {
                return true;
            }
            viewParent = viewParent.getParent();
        }
        return false;
    }

    private void loadResources(Resources resources) {
        this.widgetHeight = resources.getDimensionPixelSize(R.dimen.mtrl_slider_widget_height);
        this.trackSidePadding = resources.getDimensionPixelOffset(R.dimen.mtrl_slider_track_side_padding);
        this.trackTop = resources.getDimensionPixelOffset(R.dimen.mtrl_slider_track_top);
        this.labelPadding = resources.getDimensionPixelSize(R.dimen.mtrl_slider_label_padding);
    }

    private void maybeDrawHalo(Canvas canvas, int n, int n2) {
        if (!this.shouldDrawCompatHalo()) return;
        int n3 = (int)((float)this.trackSidePadding + this.normalizeValue(this.values.get(this.focusedThumbIdx).floatValue()) * (float)n);
        if (Build.VERSION.SDK_INT < 28) {
            n = this.haloRadius;
            canvas.clipRect((float)(n3 - n), (float)(n2 - n), (float)(n3 + n), (float)(n + n2), Region.Op.UNION);
        }
        canvas.drawCircle((float)n3, (float)n2, (float)this.haloRadius, this.haloPaint);
    }

    private boolean moveFocus(int n) {
        int n2 = this.focusedThumbIdx;
        this.focusedThumbIdx = n = (int)MathUtils.clamp((long)n2 + (long)n, 0L, (long)(this.values.size() - 1));
        if (n == n2) {
            return false;
        }
        if (this.activeThumbIdx != -1) {
            this.activeThumbIdx = n;
        }
        this.updateHaloHotspot();
        this.postInvalidate();
        return true;
    }

    private boolean moveFocusInAbsoluteDirection(int n) {
        int n2 = n;
        if (!this.isRtl()) return this.moveFocus(n2);
        if (n == Integer.MIN_VALUE) {
            n2 = Integer.MAX_VALUE;
            return this.moveFocus(n2);
        }
        n2 = -n;
        return this.moveFocus(n2);
    }

    private float normalizeValue(float f) {
        float f2 = this.valueFrom;
        f = (f - f2) / (this.valueTo - f2);
        if (!this.isRtl()) return f;
        return 1.0f - f;
    }

    /*
     * Exception decompiling
     */
    private Boolean onKeyDownNoActiveThumb(int var1_1, KeyEvent var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    private void onStartTrackingTouch() {
        Iterator<T> iterator2 = this.touchListeners.iterator();
        while (iterator2.hasNext()) {
            ((BaseOnSliderTouchListener)iterator2.next()).onStartTrackingTouch(this);
        }
    }

    private void onStopTrackingTouch() {
        Iterator<T> iterator2 = this.touchListeners.iterator();
        while (iterator2.hasNext()) {
            ((BaseOnSliderTouchListener)iterator2.next()).onStopTrackingTouch(this);
        }
    }

    private static TooltipDrawable parseLabelDrawable(Context context, TypedArray typedArray) {
        return TooltipDrawable.createFromAttributes(context, null, 0, typedArray.getResourceId(R.styleable.Slider_labelStyle, R.style.Widget_MaterialComponents_Tooltip));
    }

    private static int pivotIndex(float[] arrf, float f) {
        return Math.round(f * (float)(arrf.length / 2 - 1));
    }

    private void processAttributes(Context object, AttributeSet attributeSet, int n) {
        TypedArray typedArray = ThemeEnforcement.obtainStyledAttributes(object, attributeSet, R.styleable.Slider, n, DEF_STYLE_RES, new int[0]);
        this.valueFrom = typedArray.getFloat(R.styleable.Slider_android_valueFrom, 0.0f);
        this.valueTo = typedArray.getFloat(R.styleable.Slider_android_valueTo, 1.0f);
        this.setValues(Float.valueOf(this.valueFrom));
        this.stepSize = typedArray.getFloat(R.styleable.Slider_android_stepSize, 0.0f);
        boolean bl = typedArray.hasValue(R.styleable.Slider_trackColor);
        n = bl ? R.styleable.Slider_trackColor : R.styleable.Slider_trackColorInactive;
        int n2 = bl ? R.styleable.Slider_trackColor : R.styleable.Slider_trackColorActive;
        attributeSet = MaterialResources.getColorStateList(object, typedArray, n);
        if (attributeSet == null) {
            attributeSet = AppCompatResources.getColorStateList(object, R.color.material_slider_inactive_track_color);
        }
        this.setTrackInactiveTintList((ColorStateList)attributeSet);
        attributeSet = MaterialResources.getColorStateList(object, typedArray, n2);
        if (attributeSet == null) {
            attributeSet = AppCompatResources.getColorStateList(object, R.color.material_slider_active_track_color);
        }
        this.setTrackActiveTintList((ColorStateList)attributeSet);
        attributeSet = MaterialResources.getColorStateList(object, typedArray, R.styleable.Slider_thumbColor);
        this.thumbDrawable.setFillColor((ColorStateList)attributeSet);
        attributeSet = MaterialResources.getColorStateList(object, typedArray, R.styleable.Slider_haloColor);
        if (attributeSet == null) {
            attributeSet = AppCompatResources.getColorStateList(object, R.color.material_slider_halo_color);
        }
        this.setHaloTintList((ColorStateList)attributeSet);
        bl = typedArray.hasValue(R.styleable.Slider_tickColor);
        n = bl ? R.styleable.Slider_tickColor : R.styleable.Slider_tickColorInactive;
        n2 = bl ? R.styleable.Slider_tickColor : R.styleable.Slider_tickColorActive;
        attributeSet = MaterialResources.getColorStateList(object, typedArray, n);
        if (attributeSet == null) {
            attributeSet = AppCompatResources.getColorStateList(object, R.color.material_slider_inactive_tick_marks_color);
        }
        this.setTickInactiveTintList((ColorStateList)attributeSet);
        attributeSet = MaterialResources.getColorStateList(object, typedArray, n2);
        object = attributeSet != null ? attributeSet : AppCompatResources.getColorStateList(object, R.color.material_slider_active_tick_marks_color);
        this.setTickActiveTintList((ColorStateList)object);
        this.setThumbRadius(typedArray.getDimensionPixelSize(R.styleable.Slider_thumbRadius, 0));
        this.setHaloRadius(typedArray.getDimensionPixelSize(R.styleable.Slider_haloRadius, 0));
        this.setThumbElevation(typedArray.getDimension(R.styleable.Slider_thumbElevation, 0.0f));
        this.setTrackHeight(typedArray.getDimensionPixelSize(R.styleable.Slider_trackHeight, 0));
        this.labelBehavior = typedArray.getInt(R.styleable.Slider_labelBehavior, 0);
        if (!typedArray.getBoolean(R.styleable.Slider_android_enabled, true)) {
            this.setEnabled(false);
        }
        typedArray.recycle();
    }

    private void scheduleAccessibilityEventSender(int n) {
        BaseSlider<S, L, T> baseSlider = this.accessibilityEventSender;
        if (baseSlider == null) {
            this.accessibilityEventSender = new AccessibilityEventSender();
        } else {
            this.removeCallbacks(baseSlider);
        }
        ((AccessibilityEventSender)((Object)this.accessibilityEventSender)).setVirtualViewId(n);
        this.postDelayed(this.accessibilityEventSender, 200L);
    }

    private void setValueForLabel(TooltipDrawable tooltipDrawable, float f) {
        tooltipDrawable.setText(this.formatValue(f));
        int n = this.trackSidePadding + (int)(this.normalizeValue(f) * (float)this.trackWidth) - tooltipDrawable.getIntrinsicWidth() / 2;
        int n2 = this.calculateTop() - (this.labelPadding + this.thumbRadius);
        tooltipDrawable.setBounds(n, n2 - tooltipDrawable.getIntrinsicHeight(), tooltipDrawable.getIntrinsicWidth() + n, n2);
        Rect rect = new Rect(tooltipDrawable.getBounds());
        DescendantOffsetUtils.offsetDescendantRect(ViewUtils.getContentView(this), this, rect);
        tooltipDrawable.setBounds(rect);
        ViewUtils.getContentViewOverlay(this).add(tooltipDrawable);
    }

    private void setValuesInternal(ArrayList<Float> arrayList) {
        if (arrayList.isEmpty()) throw new IllegalArgumentException("At least one value must be set");
        Collections.sort(arrayList);
        if (this.values.size() == arrayList.size() && this.values.equals(arrayList)) {
            return;
        }
        this.values = arrayList;
        this.dirtyConfig = true;
        this.focusedThumbIdx = 0;
        this.updateHaloHotspot();
        this.createLabelPool();
        this.dispatchOnChangedProgramatically();
        this.postInvalidate();
    }

    private boolean shouldDrawCompatHalo() {
        if (this.forceDrawCompatHalo) return true;
        if (Build.VERSION.SDK_INT < 21) return true;
        if (!(this.getBackground() instanceof RippleDrawable)) return true;
        return false;
    }

    private boolean snapActiveThumbToValue(float f) {
        return this.snapThumbToValue(this.activeThumbIdx, f);
    }

    private double snapPosition(float f) {
        float f2 = this.stepSize;
        if (!(f2 > 0.0f)) return f;
        int n = (int)((this.valueTo - this.valueFrom) / f2);
        return (double)Math.round(f * (float)n) / (double)n;
    }

    private boolean snapThumbToValue(int n, float f) {
        if ((double)Math.abs(f - this.values.get(n).floatValue()) < 1.0E-4) {
            return false;
        }
        f = this.getClampedValue(n, f);
        this.values.set(n, Float.valueOf(f));
        this.focusedThumbIdx = n;
        this.dispatchOnChangedFromUser(n);
        return true;
    }

    private boolean snapTouchPosition() {
        return this.snapActiveThumbToValue(this.getValueOfTouchPosition());
    }

    private void updateHaloHotspot() {
        if (this.shouldDrawCompatHalo()) return;
        if (this.getMeasuredWidth() <= 0) return;
        Drawable drawable2 = this.getBackground();
        if (!(drawable2 instanceof RippleDrawable)) return;
        int n = (int)(this.normalizeValue(this.values.get(this.focusedThumbIdx).floatValue()) * (float)this.trackWidth + (float)this.trackSidePadding);
        int n2 = this.calculateTop();
        int n3 = this.haloRadius;
        DrawableCompat.setHotspotBounds(drawable2, n - n3, n2 - n3, n + n3, n2 + n3);
    }

    private void validateConfigurationIfDirty() {
        if (!this.dirtyConfig) return;
        this.validateValueFrom();
        this.validateValueTo();
        this.validateStepSize();
        this.validateValues();
        this.warnAboutFloatingPointError();
        this.dirtyConfig = false;
    }

    private void validateStepSize() {
        if (!(this.stepSize > 0.0f)) return;
        if (!this.valueLandsOnTick(this.valueTo)) throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_STEP_SIZE, Float.toString(this.stepSize), Float.toString(this.valueFrom), Float.toString(this.valueTo)));
    }

    private void validateValueFrom() {
        if (this.valueFrom >= this.valueTo) throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_VALUE_FROM, Float.toString(this.valueFrom), Float.toString(this.valueTo)));
    }

    private void validateValueTo() {
        if (this.valueTo <= this.valueFrom) throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_VALUE_TO, Float.toString(this.valueTo), Float.toString(this.valueFrom)));
    }

    private void validateValues() {
        Float f;
        Iterator<Float> iterator2 = this.values.iterator();
        do {
            if (!iterator2.hasNext()) return;
            f = iterator2.next();
            if (f.floatValue() < this.valueFrom || f.floatValue() > this.valueTo) throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_VALUE, Float.toString(f.floatValue()), Float.toString(this.valueFrom), Float.toString(this.valueTo)));
        } while (!(this.stepSize > 0.0f) || this.valueLandsOnTick(f.floatValue()));
        throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_DISCRETE_VALUE, Float.toString(f.floatValue()), Float.toString(this.valueFrom), Float.toString(this.stepSize), Float.toString(this.stepSize)));
    }

    private boolean valueLandsOnTick(float f) {
        double d = new BigDecimal(Float.toString(f)).subtract(new BigDecimal(Float.toString(this.valueFrom))).divide(new BigDecimal(Float.toString(this.stepSize)), MathContext.DECIMAL64).doubleValue();
        if (!(Math.abs((double)Math.round(d) - d) < 1.0E-4)) return false;
        return true;
    }

    private float valueToX(float f) {
        return this.normalizeValue(f) * (float)this.trackWidth + (float)this.trackSidePadding;
    }

    private void warnAboutFloatingPointError() {
        float f = this.stepSize;
        if (f == 0.0f) {
            return;
        }
        if ((float)((int)f) != f) {
            Log.w((String)TAG, (String)String.format(WARNING_FLOATING_POINT_ERRROR, "stepSize", Float.valueOf(f)));
        }
        if ((float)((int)(f = this.valueFrom)) != f) {
            Log.w((String)TAG, (String)String.format(WARNING_FLOATING_POINT_ERRROR, "valueFrom", Float.valueOf(f)));
        }
        if ((float)((int)(f = this.valueTo)) == f) return;
        Log.w((String)TAG, (String)String.format(WARNING_FLOATING_POINT_ERRROR, "valueTo", Float.valueOf(f)));
    }

    public void addOnChangeListener(L l) {
        this.changeListeners.add(l);
    }

    public void addOnSliderTouchListener(T t) {
        this.touchListeners.add(t);
    }

    public void clearOnChangeListeners() {
        this.changeListeners.clear();
    }

    public void clearOnSliderTouchListeners() {
        this.touchListeners.clear();
    }

    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (this.accessibilityHelper.dispatchHoverEvent(motionEvent)) return true;
        if (super.dispatchHoverEvent(motionEvent)) return true;
        return false;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.inactiveTrackPaint.setColor(this.getColorForState(this.trackColorInactive));
        this.activeTrackPaint.setColor(this.getColorForState(this.trackColorActive));
        this.inactiveTicksPaint.setColor(this.getColorForState(this.tickColorInactive));
        this.activeTicksPaint.setColor(this.getColorForState(this.tickColorActive));
        for (TooltipDrawable tooltipDrawable : this.labels) {
            if (!tooltipDrawable.isStateful()) continue;
            tooltipDrawable.setState(this.getDrawableState());
        }
        if (this.thumbDrawable.isStateful()) {
            this.thumbDrawable.setState(this.getDrawableState());
        }
        this.haloPaint.setColor(this.getColorForState(this.haloColor));
        this.haloPaint.setAlpha(63);
    }

    void forceDrawCompatHalo(boolean bl) {
        this.forceDrawCompatHalo = bl;
    }

    public CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

    final int getAccessibilityFocusedVirtualViewId() {
        return this.accessibilityHelper.getAccessibilityFocusedVirtualViewId();
    }

    public int getActiveThumbIndex() {
        return this.activeThumbIdx;
    }

    public int getFocusedThumbIndex() {
        return this.focusedThumbIdx;
    }

    public int getHaloRadius() {
        return this.haloRadius;
    }

    public ColorStateList getHaloTintList() {
        return this.haloColor;
    }

    public int getLabelBehavior() {
        return this.labelBehavior;
    }

    public float getStepSize() {
        return this.stepSize;
    }

    public float getThumbElevation() {
        return this.thumbDrawable.getElevation();
    }

    public int getThumbRadius() {
        return this.thumbRadius;
    }

    public ColorStateList getThumbTintList() {
        return this.thumbDrawable.getFillColor();
    }

    public ColorStateList getTickActiveTintList() {
        return this.tickColorActive;
    }

    public ColorStateList getTickInactiveTintList() {
        return this.tickColorInactive;
    }

    public ColorStateList getTickTintList() {
        if (!this.tickColorInactive.equals((Object)this.tickColorActive)) throw new IllegalStateException("The inactive and active ticks are different colors. Use the getTickColorInactive() and getTickColorActive() methods instead.");
        return this.tickColorActive;
    }

    public ColorStateList getTrackActiveTintList() {
        return this.trackColorActive;
    }

    public int getTrackHeight() {
        return this.trackHeight;
    }

    public ColorStateList getTrackInactiveTintList() {
        return this.trackColorInactive;
    }

    public int getTrackSidePadding() {
        return this.trackSidePadding;
    }

    public ColorStateList getTrackTintList() {
        if (!this.trackColorInactive.equals((Object)this.trackColorActive)) throw new IllegalStateException("The inactive and active parts of the track are different colors. Use the getInactiveTrackColor() and getActiveTrackColor() methods instead.");
        return this.trackColorActive;
    }

    public int getTrackWidth() {
        return this.trackWidth;
    }

    public float getValueFrom() {
        return this.valueFrom;
    }

    public float getValueTo() {
        return this.valueTo;
    }

    List<Float> getValues() {
        return new ArrayList<Float>(this.values);
    }

    public boolean hasLabelFormatter() {
        if (this.formatter == null) return false;
        return true;
    }

    final boolean isRtl() {
        int n = ViewCompat.getLayoutDirection(this);
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Iterator<TooltipDrawable> iterator2 = this.labels.iterator();
        while (iterator2.hasNext()) {
            this.attachLabelToContentView(iterator2.next());
        }
    }

    protected void onDetachedFromWindow() {
        Object object = this.accessibilityEventSender;
        if (object != null) {
            this.removeCallbacks(object);
        }
        object = this.labels.iterator();
        do {
            if (!object.hasNext()) {
                super.onDetachedFromWindow();
                return;
            }
            this.detachLabelFromContentView((TooltipDrawable)object.next());
        } while (true);
    }

    protected void onDraw(Canvas canvas) {
        if (this.dirtyConfig) {
            this.validateConfigurationIfDirty();
            if (this.stepSize > 0.0f) {
                this.calculateTicksCoordinates();
            }
        }
        super.onDraw(canvas);
        int n = this.calculateTop();
        this.drawInactiveTrack(canvas, this.trackWidth, n);
        if (Collections.max(this.getValues()).floatValue() > this.valueFrom) {
            this.drawActiveTrack(canvas, this.trackWidth, n);
        }
        if (this.stepSize > 0.0f) {
            this.drawTicks(canvas);
        }
        if ((this.thumbIsPressed || this.isFocused()) && this.isEnabled()) {
            this.maybeDrawHalo(canvas, this.trackWidth, n);
            if (this.activeThumbIdx != -1) {
                this.ensureLabels();
            }
        }
        this.drawThumbs(canvas, this.trackWidth, n);
    }

    protected void onFocusChanged(boolean bl, int n, Rect object) {
        super.onFocusChanged(bl, n, object);
        if (bl) {
            this.focusThumbOnFocusGained(n);
            this.accessibilityHelper.requestKeyboardFocusForVirtualView(this.focusedThumbIdx);
            return;
        }
        this.activeThumbIdx = -1;
        Iterator<TooltipDrawable> iterator2 = this.labels.iterator();
        do {
            if (!iterator2.hasNext()) {
                this.accessibilityHelper.clearKeyboardFocusForVirtualView(this.focusedThumbIdx);
                return;
            }
            object = iterator2.next();
            ViewUtils.getContentViewOverlay(this).remove((Drawable)object);
        } while (true);
    }

    public boolean onKeyDown(int n, KeyEvent object) {
        if (!this.isEnabled()) {
            return super.onKeyDown(n, (KeyEvent)object);
        }
        if (this.values.size() == 1) {
            this.activeThumbIdx = 0;
        }
        if (this.activeThumbIdx == -1) {
            Boolean bl = this.onKeyDownNoActiveThumb(n, (KeyEvent)object);
            if (bl == null) return super.onKeyDown(n, (KeyEvent)object);
            return bl;
        }
        this.isLongPress |= object.isLongPress();
        Object object2 = this.calculateIncrementForKey(n);
        if (object2 != null) {
            if (!this.snapActiveThumbToValue(this.values.get(this.activeThumbIdx).floatValue() + ((Float)object2).floatValue())) return true;
            this.updateHaloHotspot();
            this.postInvalidate();
            return true;
        }
        if (n != 23) {
            if (n != 61) {
                if (n != 66) {
                    return super.onKeyDown(n, (KeyEvent)object);
                }
            } else {
                if (object.hasNoModifiers()) {
                    return this.moveFocus(1);
                }
                if (!object.isShiftPressed()) return false;
                return this.moveFocus(-1);
            }
        }
        this.activeThumbIdx = -1;
        object = this.labels.iterator();
        do {
            if (!object.hasNext()) {
                this.postInvalidate();
                return true;
            }
            object2 = (TooltipDrawable)object.next();
            ViewUtils.getContentViewOverlay(this).remove((Drawable)object2);
        } while (true);
    }

    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        this.isLongPress = false;
        return super.onKeyUp(n, keyEvent);
    }

    protected void onMeasure(int n, int n2) {
        int n3 = this.widgetHeight;
        int n4 = this.labelBehavior;
        n2 = 0;
        if (n4 == 1) {
            n2 = this.labels.get(0).getIntrinsicHeight();
        }
        super.onMeasure(n, View.MeasureSpec.makeMeasureSpec((int)(n3 + n2), (int)1073741824));
    }

    protected void onRestoreInstanceState(Parcelable object) {
        object = (SliderState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.valueFrom = object.valueFrom;
        this.valueTo = object.valueTo;
        this.setValuesInternal(object.values);
        this.stepSize = object.stepSize;
        if (object.hasFocus) {
            this.requestFocus();
        }
        this.dispatchOnChangedProgramatically();
    }

    protected Parcelable onSaveInstanceState() {
        SliderState sliderState = new SliderState(super.onSaveInstanceState());
        sliderState.valueFrom = this.valueFrom;
        sliderState.valueTo = this.valueTo;
        sliderState.values = new ArrayList<Float>(this.values);
        sliderState.stepSize = this.stepSize;
        sliderState.hasFocus = this.hasFocus();
        return sliderState;
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        this.trackWidth = Math.max(n - this.trackSidePadding * 2, 0);
        if (this.stepSize > 0.0f) {
            this.calculateTicksCoordinates();
        }
        this.updateHaloHotspot();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f;
        if (!this.isEnabled()) {
            return false;
        }
        float f2 = motionEvent.getX();
        this.touchPosition = f = (f2 - (float)this.trackSidePadding) / (float)this.trackWidth;
        this.touchPosition = f = Math.max(0.0f, f);
        this.touchPosition = Math.min(1.0f, f);
        int n = motionEvent.getActionMasked();
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    if (!this.thumbIsPressed) {
                        if (Math.abs(f2 - this.touchDownX) < (float)this.scaledTouchSlop) {
                            return false;
                        }
                        this.getParent().requestDisallowInterceptTouchEvent(true);
                        this.onStartTrackingTouch();
                    }
                    if (this.pickActiveThumb()) {
                        this.thumbIsPressed = true;
                        this.snapTouchPosition();
                        this.updateHaloHotspot();
                        this.invalidate();
                    }
                }
            } else {
                this.thumbIsPressed = false;
                MotionEvent motionEvent2 = this.lastEvent;
                if (motionEvent2 != null && motionEvent2.getActionMasked() == 0 && Math.abs(this.lastEvent.getX() - motionEvent.getX()) <= (float)this.scaledTouchSlop && Math.abs(this.lastEvent.getY() - motionEvent.getY()) <= (float)this.scaledTouchSlop) {
                    this.pickActiveThumb();
                }
                if (this.activeThumbIdx != -1) {
                    this.snapTouchPosition();
                    this.activeThumbIdx = -1;
                }
                for (TooltipDrawable tooltipDrawable : this.labels) {
                    ViewUtils.getContentViewOverlay(this).remove(tooltipDrawable);
                }
                this.onStopTrackingTouch();
                this.invalidate();
            }
        } else {
            this.touchDownX = f2;
            if (!this.isInScrollingContainer()) {
                this.getParent().requestDisallowInterceptTouchEvent(true);
                if (this.pickActiveThumb()) {
                    this.requestFocus();
                    this.thumbIsPressed = true;
                    this.snapTouchPosition();
                    this.updateHaloHotspot();
                    this.invalidate();
                    this.onStartTrackingTouch();
                }
            }
        }
        this.setPressed(this.thumbIsPressed);
        this.lastEvent = MotionEvent.obtain((MotionEvent)motionEvent);
        return true;
    }

    protected boolean pickActiveThumb() {
        int n = this.activeThumbIdx;
        boolean bl = true;
        if (n != -1) {
            return true;
        }
        float f = this.getValueOfTouchPositionAbsolute();
        float f2 = this.valueToX(f);
        this.activeThumbIdx = 0;
        float f3 = Math.abs(this.values.get(0).floatValue() - f);
        for (n = 1; n < this.values.size(); ++n) {
            float f4;
            block8 : {
                float f5;
                block7 : {
                    boolean bl2;
                    float f6;
                    block6 : {
                        f5 = Math.abs(this.values.get(n).floatValue() - f);
                        f6 = this.valueToX(this.values.get(n).floatValue());
                        if (Float.compare(f5, f3) > 1) break;
                        bl2 = this.isRtl() ? f6 - f2 > 0.0f : f6 - f2 < 0.0f;
                        if (Float.compare(f5, f3) >= 0) break block6;
                        this.activeThumbIdx = n;
                        break block7;
                    }
                    f4 = f3;
                    if (Float.compare(f5, f3) != 0) break block8;
                    if (Math.abs(f6 - f2) < (float)this.scaledTouchSlop) {
                        this.activeThumbIdx = -1;
                        return false;
                    }
                    f4 = f3;
                    if (!bl2) break block8;
                    this.activeThumbIdx = n;
                }
                f4 = f5;
            }
            f3 = f4;
        }
        if (this.activeThumbIdx == -1) return false;
        return bl;
    }

    public void removeOnChangeListener(L l) {
        this.changeListeners.remove(l);
    }

    public void removeOnSliderTouchListener(T t) {
        this.touchListeners.remove(t);
    }

    protected void setActiveThumbIndex(int n) {
        this.activeThumbIdx = n;
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        int n = bl ? 0 : 2;
        this.setLayerType(n, null);
    }

    public void setFocusedThumbIndex(int n) {
        if (n < 0) throw new IllegalArgumentException("index out of range");
        if (n >= this.values.size()) throw new IllegalArgumentException("index out of range");
        this.focusedThumbIdx = n;
        this.accessibilityHelper.requestKeyboardFocusForVirtualView(n);
        this.postInvalidate();
    }

    public void setHaloRadius(int n) {
        if (n == this.haloRadius) {
            return;
        }
        this.haloRadius = n;
        Drawable drawable2 = this.getBackground();
        if (!this.shouldDrawCompatHalo() && drawable2 instanceof RippleDrawable) {
            DrawableUtils.setRippleDrawableRadius((RippleDrawable)drawable2, this.haloRadius);
            return;
        }
        this.postInvalidate();
    }

    public void setHaloRadiusResource(int n) {
        this.setHaloRadius(this.getResources().getDimensionPixelSize(n));
    }

    public void setHaloTintList(ColorStateList colorStateList) {
        if (colorStateList.equals((Object)this.haloColor)) {
            return;
        }
        this.haloColor = colorStateList;
        Drawable drawable2 = this.getBackground();
        if (!this.shouldDrawCompatHalo() && drawable2 instanceof RippleDrawable) {
            ((RippleDrawable)drawable2).setColor(colorStateList);
            return;
        }
        this.haloPaint.setColor(this.getColorForState(colorStateList));
        this.haloPaint.setAlpha(63);
        this.invalidate();
    }

    public void setLabelBehavior(int n) {
        if (this.labelBehavior == n) return;
        this.labelBehavior = n;
        this.requestLayout();
    }

    public void setLabelFormatter(LabelFormatter labelFormatter) {
        this.formatter = labelFormatter;
    }

    public void setStepSize(float f) {
        if (f < 0.0f) throw new IllegalArgumentException(String.format(EXCEPTION_ILLEGAL_STEP_SIZE, Float.toString(f), Float.toString(this.valueFrom), Float.toString(this.valueTo)));
        if (this.stepSize == f) return;
        this.stepSize = f;
        this.dirtyConfig = true;
        this.postInvalidate();
    }

    public void setThumbElevation(float f) {
        this.thumbDrawable.setElevation(f);
    }

    public void setThumbElevationResource(int n) {
        this.setThumbElevation(this.getResources().getDimension(n));
    }

    public void setThumbRadius(int n) {
        if (n == this.thumbRadius) {
            return;
        }
        this.thumbRadius = n;
        this.thumbDrawable.setShapeAppearanceModel(ShapeAppearanceModel.builder().setAllCorners(0, this.thumbRadius).build());
        MaterialShapeDrawable materialShapeDrawable = this.thumbDrawable;
        n = this.thumbRadius;
        materialShapeDrawable.setBounds(0, 0, n * 2, n * 2);
        this.postInvalidate();
    }

    public void setThumbRadiusResource(int n) {
        this.setThumbRadius(this.getResources().getDimensionPixelSize(n));
    }

    public void setThumbTintList(ColorStateList colorStateList) {
        this.thumbDrawable.setFillColor(colorStateList);
    }

    public void setTickActiveTintList(ColorStateList colorStateList) {
        if (colorStateList.equals((Object)this.tickColorActive)) {
            return;
        }
        this.tickColorActive = colorStateList;
        this.activeTicksPaint.setColor(this.getColorForState(colorStateList));
        this.invalidate();
    }

    public void setTickInactiveTintList(ColorStateList colorStateList) {
        if (colorStateList.equals((Object)this.tickColorInactive)) {
            return;
        }
        this.tickColorInactive = colorStateList;
        this.inactiveTicksPaint.setColor(this.getColorForState(colorStateList));
        this.invalidate();
    }

    public void setTickTintList(ColorStateList colorStateList) {
        this.setTickInactiveTintList(colorStateList);
        this.setTickActiveTintList(colorStateList);
    }

    public void setTrackActiveTintList(ColorStateList colorStateList) {
        if (colorStateList.equals((Object)this.trackColorActive)) {
            return;
        }
        this.trackColorActive = colorStateList;
        this.activeTrackPaint.setColor(this.getColorForState(colorStateList));
        this.invalidate();
    }

    public void setTrackHeight(int n) {
        if (this.trackHeight == n) return;
        this.trackHeight = n;
        this.invalidateTrack();
        this.postInvalidate();
    }

    public void setTrackInactiveTintList(ColorStateList colorStateList) {
        if (colorStateList.equals((Object)this.trackColorInactive)) {
            return;
        }
        this.trackColorInactive = colorStateList;
        this.inactiveTrackPaint.setColor(this.getColorForState(colorStateList));
        this.invalidate();
    }

    public void setTrackTintList(ColorStateList colorStateList) {
        this.setTrackInactiveTintList(colorStateList);
        this.setTrackActiveTintList(colorStateList);
    }

    public void setValueFrom(float f) {
        this.valueFrom = f;
        this.dirtyConfig = true;
        this.postInvalidate();
    }

    public void setValueTo(float f) {
        this.valueTo = f;
        this.dirtyConfig = true;
        this.postInvalidate();
    }

    void setValues(List<Float> list) {
        this.setValuesInternal(new ArrayList<Float>(list));
    }

    void setValues(Float ... arrfloat) {
        ArrayList<Float> arrayList = new ArrayList<Float>();
        Collections.addAll(arrayList, arrfloat);
        this.setValuesInternal(arrayList);
    }

    void updateBoundsForVirturalViewId(int n, Rect rect) {
        n = this.trackSidePadding + (int)(this.normalizeValue(this.getValues().get(n).floatValue()) * (float)this.trackWidth);
        int n2 = this.calculateTop();
        int n3 = this.thumbRadius;
        rect.set(n - n3, n2 - n3, n + n3, n2 + n3);
    }

    private class AccessibilityEventSender
    implements Runnable {
        int virtualViewId = -1;

        private AccessibilityEventSender() {
        }

        @Override
        public void run() {
            BaseSlider.this.accessibilityHelper.sendEventForVirtualView(this.virtualViewId, 4);
        }

        void setVirtualViewId(int n) {
            this.virtualViewId = n;
        }
    }

    private static class AccessibilityHelper
    extends ExploreByTouchHelper {
        private final BaseSlider<?, ?, ?> slider;
        Rect virtualViewBounds = new Rect();

        AccessibilityHelper(BaseSlider<?, ?, ?> baseSlider) {
            super(baseSlider);
            this.slider = baseSlider;
        }

        private String startOrEndDescription(int n) {
            if (n == this.slider.getValues().size() - 1) {
                return this.slider.getContext().getString(R.string.material_slider_range_end);
            }
            if (n != 0) return "";
            return this.slider.getContext().getString(R.string.material_slider_range_start);
        }

        @Override
        protected int getVirtualViewAt(float f, float f2) {
            int n = 0;
            while (n < this.slider.getValues().size()) {
                this.slider.updateBoundsForVirturalViewId(n, this.virtualViewBounds);
                if (this.virtualViewBounds.contains((int)f, (int)f2)) {
                    return n;
                }
                ++n;
            }
            return -1;
        }

        @Override
        protected void getVisibleVirtualViews(List<Integer> list) {
            int n = 0;
            while (n < this.slider.getValues().size()) {
                list.add(n);
                ++n;
            }
        }

        @Override
        protected boolean onPerformActionForVirtualView(int n, int n2, Bundle bundle) {
            float f;
            if (!this.slider.isEnabled()) {
                return false;
            }
            if (n2 != 4096 && n2 != 8192) {
                if (n2 != 16908349) {
                    return false;
                }
                if (bundle == null) return false;
                if (!bundle.containsKey("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE")) {
                    return false;
                }
                float f2 = bundle.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE");
                if (!this.slider.snapThumbToValue(n, f2)) return false;
                this.slider.updateHaloHotspot();
                this.slider.postInvalidate();
                this.invalidateVirtualView(n);
                return true;
            }
            float f3 = f = this.slider.calculateStepIncrement(20);
            if (n2 == 8192) {
                f3 = -f;
            }
            f = f3;
            if (this.slider.isRtl()) {
                f = -f3;
            }
            if (!this.slider.snapThumbToValue(n, f3 = MathUtils.clamp(this.slider.getValues().get(n).floatValue() + f, this.slider.getValueFrom(), this.slider.getValueTo()))) return false;
            this.slider.updateHaloHotspot();
            this.slider.postInvalidate();
            this.invalidateVirtualView(n);
            return true;
        }

        @Override
        protected void onPopulateNodeForVirtualView(int n, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
            List<Float> list = this.slider.getValues();
            float f = list.get(n).floatValue();
            float f2 = this.slider.getValueFrom();
            float f3 = this.slider.getValueTo();
            if (this.slider.isEnabled()) {
                if (f > f2) {
                    accessibilityNodeInfoCompat.addAction(8192);
                }
                if (f < f3) {
                    accessibilityNodeInfoCompat.addAction(4096);
                }
            }
            accessibilityNodeInfoCompat.setRangeInfo(AccessibilityNodeInfoCompat.RangeInfoCompat.obtain(1, f2, f3, f));
            accessibilityNodeInfoCompat.setClassName(SeekBar.class.getName());
            StringBuilder stringBuilder = new StringBuilder();
            if (this.slider.getContentDescription() != null) {
                stringBuilder.append(this.slider.getContentDescription());
                stringBuilder.append(",");
            }
            if (list.size() > 1) {
                stringBuilder.append(this.startOrEndDescription(n));
                stringBuilder.append(this.slider.formatValue(f));
            }
            accessibilityNodeInfoCompat.setContentDescription(stringBuilder.toString());
            this.slider.updateBoundsForVirturalViewId(n, this.virtualViewBounds);
            accessibilityNodeInfoCompat.setBoundsInParent(this.virtualViewBounds);
        }
    }

    static class SliderState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SliderState> CREATOR = new Parcelable.Creator<SliderState>(){

            public SliderState createFromParcel(Parcel parcel) {
                return new SliderState(parcel);
            }

            public SliderState[] newArray(int n) {
                return new SliderState[n];
            }
        };
        boolean hasFocus;
        float stepSize;
        float valueFrom;
        float valueTo;
        ArrayList<Float> values;

        private SliderState(Parcel parcel) {
            super(parcel);
            this.valueFrom = parcel.readFloat();
            this.valueTo = parcel.readFloat();
            ArrayList arrayList = new ArrayList();
            this.values = arrayList;
            parcel.readList(arrayList, Float.class.getClassLoader());
            this.stepSize = parcel.readFloat();
            this.hasFocus = parcel.createBooleanArray()[0];
        }

        SliderState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeFloat(this.valueFrom);
            parcel.writeFloat(this.valueTo);
            parcel.writeList(this.values);
            parcel.writeFloat(this.stepSize);
            parcel.writeBooleanArray(new boolean[]{this.hasFocus});
        }

    }

    private static interface TooltipDrawableFactory {
        public TooltipDrawable createTooltipDrawable();
    }

}

