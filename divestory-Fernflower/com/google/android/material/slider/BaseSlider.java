package com.google.android.material.slider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.accessibility.AccessibilityManager;
import android.widget.SeekBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
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
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.google.android.material.tooltip.TooltipDrawable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

abstract class BaseSlider<S extends BaseSlider<S, L, T>, L extends BaseOnChangeListener<S>, T extends BaseOnSliderTouchListener<S>> extends View {
   private static final int DEF_STYLE_RES;
   private static final String EXCEPTION_ILLEGAL_DISCRETE_VALUE = "Value(%s) must be equal to valueFrom(%s) plus a multiple of stepSize(%s) when using stepSize(%s)";
   private static final String EXCEPTION_ILLEGAL_STEP_SIZE = "The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range";
   private static final String EXCEPTION_ILLEGAL_VALUE = "Slider value(%s) must be greater or equal to valueFrom(%s), and lower or equal to valueTo(%s)";
   private static final String EXCEPTION_ILLEGAL_VALUE_FROM = "valueFrom(%s) must be smaller than valueTo(%s)";
   private static final String EXCEPTION_ILLEGAL_VALUE_TO = "valueTo(%s) must be greater than valueFrom(%s)";
   private static final int HALO_ALPHA = 63;
   private static final String TAG = BaseSlider.class.getSimpleName();
   private static final double THRESHOLD = 1.0E-4D;
   private static final int TIMEOUT_SEND_ACCESSIBILITY_EVENT = 200;
   private static final String WARNING_FLOATING_POINT_ERRROR = "Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the  value correctly.";
   private BaseSlider<S, L, T>.AccessibilityEventSender accessibilityEventSender;
   private final BaseSlider.AccessibilityHelper accessibilityHelper;
   private final AccessibilityManager accessibilityManager;
   private int activeThumbIdx;
   private final Paint activeTicksPaint;
   private final Paint activeTrackPaint;
   private final List<L> changeListeners;
   private boolean dirtyConfig;
   private int focusedThumbIdx;
   private boolean forceDrawCompatHalo;
   private LabelFormatter formatter;
   private ColorStateList haloColor;
   private final Paint haloPaint;
   private int haloRadius;
   private final Paint inactiveTicksPaint;
   private final Paint inactiveTrackPaint;
   private boolean isLongPress;
   private int labelBehavior;
   private final BaseSlider.TooltipDrawableFactory labelMaker;
   private int labelPadding;
   private final List<TooltipDrawable> labels;
   private MotionEvent lastEvent;
   private final int scaledTouchSlop;
   private float stepSize;
   private final MaterialShapeDrawable thumbDrawable;
   private boolean thumbIsPressed;
   private final Paint thumbPaint;
   private int thumbRadius;
   private ColorStateList tickColorActive;
   private ColorStateList tickColorInactive;
   private float[] ticksCoordinates;
   private float touchDownX;
   private final List<T> touchListeners;
   private float touchPosition;
   private ColorStateList trackColorActive;
   private ColorStateList trackColorInactive;
   private int trackHeight;
   private int trackSidePadding;
   private int trackTop;
   private int trackWidth;
   private float valueFrom;
   private float valueTo;
   private ArrayList<Float> values;
   private int widgetHeight;

   static {
      DEF_STYLE_RES = R.style.Widget_MaterialComponents_Slider;
   }

   public BaseSlider(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public BaseSlider(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.sliderStyle);
   }

   public BaseSlider(Context var1, final AttributeSet var2, final int var3) {
      super(MaterialThemeOverlay.wrap(var1, var2, var3, DEF_STYLE_RES), var2, var3);
      this.labels = new ArrayList();
      this.changeListeners = new ArrayList();
      this.touchListeners = new ArrayList();
      this.thumbIsPressed = false;
      this.values = new ArrayList();
      this.activeThumbIdx = -1;
      this.focusedThumbIdx = -1;
      this.stepSize = 0.0F;
      this.isLongPress = false;
      this.thumbDrawable = new MaterialShapeDrawable();
      var1 = this.getContext();
      Paint var4 = new Paint();
      this.inactiveTrackPaint = var4;
      var4.setStyle(Style.STROKE);
      this.inactiveTrackPaint.setStrokeCap(Cap.ROUND);
      var4 = new Paint();
      this.activeTrackPaint = var4;
      var4.setStyle(Style.STROKE);
      this.activeTrackPaint.setStrokeCap(Cap.ROUND);
      var4 = new Paint(1);
      this.thumbPaint = var4;
      var4.setStyle(Style.FILL);
      this.thumbPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
      var4 = new Paint(1);
      this.haloPaint = var4;
      var4.setStyle(Style.FILL);
      var4 = new Paint();
      this.inactiveTicksPaint = var4;
      var4.setStyle(Style.STROKE);
      this.inactiveTicksPaint.setStrokeCap(Cap.ROUND);
      var4 = new Paint();
      this.activeTicksPaint = var4;
      var4.setStyle(Style.STROKE);
      this.activeTicksPaint.setStrokeCap(Cap.ROUND);
      this.loadResources(var1.getResources());
      this.labelMaker = new BaseSlider.TooltipDrawableFactory() {
         public TooltipDrawable createTooltipDrawable() {
            TypedArray var1 = ThemeEnforcement.obtainStyledAttributes(BaseSlider.this.getContext(), var2, R.styleable.Slider, var3, BaseSlider.DEF_STYLE_RES);
            TooltipDrawable var2x = BaseSlider.parseLabelDrawable(BaseSlider.this.getContext(), var1);
            var1.recycle();
            return var2x;
         }
      };
      this.processAttributes(var1, var2, var3);
      this.setFocusable(true);
      this.setClickable(true);
      this.thumbDrawable.setShadowCompatibilityMode(2);
      this.scaledTouchSlop = ViewConfiguration.get(var1).getScaledTouchSlop();
      BaseSlider.AccessibilityHelper var5 = new BaseSlider.AccessibilityHelper(this);
      this.accessibilityHelper = var5;
      ViewCompat.setAccessibilityDelegate(this, var5);
      this.accessibilityManager = (AccessibilityManager)this.getContext().getSystemService("accessibility");
   }

   private void attachLabelToContentView(TooltipDrawable var1) {
      var1.setRelativeToView(ViewUtils.getContentView(this));
   }

   private Float calculateIncrementForKey(int var1) {
      float var2;
      if (this.isLongPress) {
         var2 = this.calculateStepIncrement(20);
      } else {
         var2 = this.calculateStepIncrement();
      }

      if (var1 != 21) {
         if (var1 != 22) {
            if (var1 != 69) {
               return var1 != 70 && var1 != 81 ? null : var2;
            } else {
               return -var2;
            }
         } else {
            float var3 = var2;
            if (this.isRtl()) {
               var3 = -var2;
            }

            return var3;
         }
      } else {
         if (!this.isRtl()) {
            var2 = -var2;
         }

         return var2;
      }
   }

   private float calculateStepIncrement() {
      float var1 = this.stepSize;
      float var2 = var1;
      if (var1 == 0.0F) {
         var2 = 1.0F;
      }

      return var2;
   }

   private float calculateStepIncrement(int var1) {
      float var2 = this.calculateStepIncrement();
      float var3 = (this.valueTo - this.valueFrom) / var2;
      float var4 = (float)var1;
      return var3 <= var4 ? var2 : (float)Math.round(var3 / var4) * var2;
   }

   private void calculateTicksCoordinates() {
      this.validateConfigurationIfDirty();
      int var1 = Math.min((int)((this.valueTo - this.valueFrom) / this.stepSize + 1.0F), this.trackWidth / (this.trackHeight * 2) + 1);
      float[] var2 = this.ticksCoordinates;
      if (var2 == null || var2.length != var1 * 2) {
         this.ticksCoordinates = new float[var1 * 2];
      }

      float var3 = (float)this.trackWidth / (float)(var1 - 1);

      for(int var4 = 0; var4 < var1 * 2; var4 += 2) {
         var2 = this.ticksCoordinates;
         var2[var4] = (float)this.trackSidePadding + (float)(var4 / 2) * var3;
         var2[var4 + 1] = (float)this.calculateTop();
      }

   }

   private int calculateTop() {
      int var1 = this.trackTop;
      int var2 = this.labelBehavior;
      int var3 = 0;
      if (var2 == 1) {
         var3 = ((TooltipDrawable)this.labels.get(0)).getIntrinsicHeight();
      }

      return var1 + var3;
   }

   private void createLabelPool() {
      TooltipDrawable var3;
      if (this.labels.size() > this.values.size()) {
         List var1 = this.labels.subList(this.values.size(), this.labels.size());
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            var3 = (TooltipDrawable)var2.next();
            if (ViewCompat.isAttachedToWindow(this)) {
               this.detachLabelFromContentView(var3);
            }
         }

         var1.clear();
      }

      while(this.labels.size() < this.values.size()) {
         var3 = this.labelMaker.createTooltipDrawable();
         this.labels.add(var3);
         if (ViewCompat.isAttachedToWindow(this)) {
            this.attachLabelToContentView(var3);
         }
      }

      int var4 = this.labels.size();
      byte var5 = 1;
      if (var4 == 1) {
         var5 = 0;
      }

      Iterator var6 = this.labels.iterator();

      while(var6.hasNext()) {
         ((TooltipDrawable)var6.next()).setStrokeWidth((float)var5);
      }

   }

   private void detachLabelFromContentView(TooltipDrawable var1) {
      ViewOverlayImpl var2 = ViewUtils.getContentViewOverlay(this);
      if (var2 != null) {
         var2.remove(var1);
         var1.detachView(ViewUtils.getContentView(this));
      }

   }

   private void dispatchOnChangedFromUser(int var1) {
      Iterator var2 = this.changeListeners.iterator();

      while(var2.hasNext()) {
         ((BaseOnChangeListener)var2.next()).onValueChange(this, (Float)this.values.get(var1), true);
      }

      AccessibilityManager var3 = this.accessibilityManager;
      if (var3 != null && var3.isEnabled()) {
         this.scheduleAccessibilityEventSender(var1);
      }

   }

   private void dispatchOnChangedProgramatically() {
      Iterator var1 = this.changeListeners.iterator();

      while(var1.hasNext()) {
         BaseOnChangeListener var2 = (BaseOnChangeListener)var1.next();
         Iterator var3 = this.values.iterator();

         while(var3.hasNext()) {
            var2.onValueChange(this, (Float)var3.next(), false);
         }
      }

   }

   private void drawActiveTrack(Canvas var1, int var2, int var3) {
      float[] var4 = this.getActiveRange();
      int var5 = this.trackSidePadding;
      float var6 = (float)var5;
      float var7 = var4[1];
      float var8 = (float)var2;
      float var9 = (float)var5;
      float var10 = var4[0];
      float var11 = (float)var3;
      var1.drawLine(var9 + var10 * var8, var11, var6 + var7 * var8, var11, this.activeTrackPaint);
   }

   private void drawInactiveTrack(Canvas var1, int var2, int var3) {
      float[] var4 = this.getActiveRange();
      int var5 = this.trackSidePadding;
      float var6 = (float)var5;
      float var7 = var4[1];
      float var8 = (float)var2;
      var7 = var6 + var7 * var8;
      if (var7 < (float)(var5 + var2)) {
         var6 = (float)var3;
         var1.drawLine(var7, var6, (float)(var5 + var2), var6, this.inactiveTrackPaint);
      }

      var2 = this.trackSidePadding;
      var7 = (float)var2 + var4[0] * var8;
      if (var7 > (float)var2) {
         var6 = (float)var2;
         var8 = (float)var3;
         var1.drawLine(var6, var8, var7, var8, this.inactiveTrackPaint);
      }

   }

   private void drawThumbs(Canvas var1, int var2, int var3) {
      Iterator var4;
      Float var5;
      if (!this.isEnabled()) {
         var4 = this.values.iterator();

         while(var4.hasNext()) {
            var5 = (Float)var4.next();
            var1.drawCircle((float)this.trackSidePadding + this.normalizeValue(var5) * (float)var2, (float)var3, (float)this.thumbRadius, this.thumbPaint);
         }
      }

      var4 = this.values.iterator();

      while(var4.hasNext()) {
         var5 = (Float)var4.next();
         var1.save();
         int var6 = this.trackSidePadding;
         int var7 = (int)(this.normalizeValue(var5) * (float)var2);
         int var8 = this.thumbRadius;
         var1.translate((float)(var6 + var7 - var8), (float)(var3 - var8));
         this.thumbDrawable.draw(var1);
         var1.restore();
      }

   }

   private void drawTicks(Canvas var1) {
      float[] var2 = this.getActiveRange();
      int var3 = pivotIndex(this.ticksCoordinates, var2[0]);
      int var4 = pivotIndex(this.ticksCoordinates, var2[1]);
      var2 = this.ticksCoordinates;
      var3 *= 2;
      var1.drawPoints(var2, 0, var3, this.inactiveTicksPaint);
      var2 = this.ticksCoordinates;
      var4 *= 2;
      var1.drawPoints(var2, var3, var4 - var3, this.activeTicksPaint);
      var2 = this.ticksCoordinates;
      var1.drawPoints(var2, var4, var2.length - var4, this.inactiveTicksPaint);
   }

   private void ensureLabels() {
      if (this.labelBehavior != 2) {
         Iterator var1 = this.labels.iterator();

         for(int var2 = 0; var2 < this.values.size() && var1.hasNext(); ++var2) {
            if (var2 != this.focusedThumbIdx) {
               this.setValueForLabel((TooltipDrawable)var1.next(), (Float)this.values.get(var2));
            }
         }

         if (var1.hasNext()) {
            this.setValueForLabel((TooltipDrawable)var1.next(), (Float)this.values.get(this.focusedThumbIdx));
         } else {
            throw new IllegalStateException(String.format("Not enough labels(%d) to display all the values(%d)", this.labels.size(), this.values.size()));
         }
      }
   }

   private void focusThumbOnFocusGained(int var1) {
      if (var1 != 1) {
         if (var1 != 2) {
            if (var1 != 17) {
               if (var1 == 66) {
                  this.moveFocusInAbsoluteDirection(Integer.MIN_VALUE);
               }
            } else {
               this.moveFocusInAbsoluteDirection(Integer.MAX_VALUE);
            }
         } else {
            this.moveFocus(Integer.MIN_VALUE);
         }
      } else {
         this.moveFocus(Integer.MAX_VALUE);
      }

   }

   private String formatValue(float var1) {
      if (this.hasLabelFormatter()) {
         return this.formatter.getFormattedValue(var1);
      } else {
         String var2;
         if ((float)((int)var1) == var1) {
            var2 = "%.0f";
         } else {
            var2 = "%.2f";
         }

         return String.format(var2, var1);
      }
   }

   private float[] getActiveRange() {
      float var1 = (Float)Collections.max(this.getValues());
      float var2 = (Float)Collections.min(this.getValues());
      if (this.values.size() == 1) {
         var2 = this.valueFrom;
      }

      var2 = this.normalizeValue(var2);
      var1 = this.normalizeValue(var1);
      float[] var3;
      if (this.isRtl()) {
         var3 = new float[]{var1, var2};
      } else {
         var3 = new float[]{var2, var1};
      }

      return var3;
   }

   private float getClampedValue(int var1, float var2) {
      int var3 = var1 + 1;
      float var4;
      if (var3 >= this.values.size()) {
         var4 = this.valueTo;
      } else {
         var4 = (Float)this.values.get(var3);
      }

      --var1;
      float var5;
      if (var1 < 0) {
         var5 = this.valueFrom;
      } else {
         var5 = (Float)this.values.get(var1);
      }

      return MathUtils.clamp(var2, var5, var4);
   }

   private int getColorForState(ColorStateList var1) {
      return var1.getColorForState(this.getDrawableState(), var1.getDefaultColor());
   }

   private float getValueOfTouchPosition() {
      double var1 = this.snapPosition(this.touchPosition);
      double var3 = var1;
      if (this.isRtl()) {
         var3 = 1.0D - var1;
      }

      float var5 = this.valueTo;
      float var6 = this.valueFrom;
      return (float)(var3 * (double)(var5 - var6) + (double)var6);
   }

   private float getValueOfTouchPositionAbsolute() {
      float var1 = this.touchPosition;
      float var2 = var1;
      if (this.isRtl()) {
         var2 = 1.0F - var1;
      }

      var1 = this.valueTo;
      float var3 = this.valueFrom;
      return var2 * (var1 - var3) + var3;
   }

   private void invalidateTrack() {
      this.inactiveTrackPaint.setStrokeWidth((float)this.trackHeight);
      this.activeTrackPaint.setStrokeWidth((float)this.trackHeight);
      this.inactiveTicksPaint.setStrokeWidth((float)this.trackHeight / 2.0F);
      this.activeTicksPaint.setStrokeWidth((float)this.trackHeight / 2.0F);
   }

   private boolean isInScrollingContainer() {
      for(ViewParent var1 = this.getParent(); var1 instanceof ViewGroup; var1 = var1.getParent()) {
         if (((ViewGroup)var1).shouldDelayChildPressedState()) {
            return true;
         }
      }

      return false;
   }

   private void loadResources(Resources var1) {
      this.widgetHeight = var1.getDimensionPixelSize(R.dimen.mtrl_slider_widget_height);
      this.trackSidePadding = var1.getDimensionPixelOffset(R.dimen.mtrl_slider_track_side_padding);
      this.trackTop = var1.getDimensionPixelOffset(R.dimen.mtrl_slider_track_top);
      this.labelPadding = var1.getDimensionPixelSize(R.dimen.mtrl_slider_label_padding);
   }

   private void maybeDrawHalo(Canvas var1, int var2, int var3) {
      if (this.shouldDrawCompatHalo()) {
         int var4 = (int)((float)this.trackSidePadding + this.normalizeValue((Float)this.values.get(this.focusedThumbIdx)) * (float)var2);
         if (VERSION.SDK_INT < 28) {
            var2 = this.haloRadius;
            var1.clipRect((float)(var4 - var2), (float)(var3 - var2), (float)(var4 + var2), (float)(var2 + var3), Op.UNION);
         }

         var1.drawCircle((float)var4, (float)var3, (float)this.haloRadius, this.haloPaint);
      }

   }

   private boolean moveFocus(int var1) {
      int var2 = this.focusedThumbIdx;
      var1 = (int)MathUtils.clamp((long)var2 + (long)var1, 0L, (long)(this.values.size() - 1));
      this.focusedThumbIdx = var1;
      if (var1 == var2) {
         return false;
      } else {
         if (this.activeThumbIdx != -1) {
            this.activeThumbIdx = var1;
         }

         this.updateHaloHotspot();
         this.postInvalidate();
         return true;
      }
   }

   private boolean moveFocusInAbsoluteDirection(int var1) {
      int var2 = var1;
      if (this.isRtl()) {
         if (var1 == Integer.MIN_VALUE) {
            var2 = Integer.MAX_VALUE;
         } else {
            var2 = -var1;
         }
      }

      return this.moveFocus(var2);
   }

   private float normalizeValue(float var1) {
      float var2 = this.valueFrom;
      var1 = (var1 - var2) / (this.valueTo - var2);
      return this.isRtl() ? 1.0F - var1 : var1;
   }

   private Boolean onKeyDownNoActiveThumb(int var1, KeyEvent var2) {
      if (var1 == 61) {
         if (var2.hasNoModifiers()) {
            return this.moveFocus(1);
         } else {
            return var2.isShiftPressed() ? this.moveFocus(-1) : false;
         }
      } else {
         label38: {
            if (var1 != 66) {
               if (var1 == 81) {
                  break label38;
               }

               if (var1 == 69) {
                  this.moveFocus(-1);
                  return true;
               }

               if (var1 == 70) {
                  break label38;
               }

               switch(var1) {
               case 21:
                  this.moveFocusInAbsoluteDirection(-1);
                  return true;
               case 22:
                  this.moveFocusInAbsoluteDirection(1);
                  return true;
               case 23:
                  break;
               default:
                  return null;
               }
            }

            this.activeThumbIdx = this.focusedThumbIdx;
            this.postInvalidate();
            return true;
         }

         this.moveFocus(1);
         return true;
      }
   }

   private void onStartTrackingTouch() {
      Iterator var1 = this.touchListeners.iterator();

      while(var1.hasNext()) {
         ((BaseOnSliderTouchListener)var1.next()).onStartTrackingTouch(this);
      }

   }

   private void onStopTrackingTouch() {
      Iterator var1 = this.touchListeners.iterator();

      while(var1.hasNext()) {
         ((BaseOnSliderTouchListener)var1.next()).onStopTrackingTouch(this);
      }

   }

   private static TooltipDrawable parseLabelDrawable(Context var0, TypedArray var1) {
      return TooltipDrawable.createFromAttributes(var0, (AttributeSet)null, 0, var1.getResourceId(R.styleable.Slider_labelStyle, R.style.Widget_MaterialComponents_Tooltip));
   }

   private static int pivotIndex(float[] var0, float var1) {
      return Math.round(var1 * (float)(var0.length / 2 - 1));
   }

   private void processAttributes(Context var1, AttributeSet var2, int var3) {
      TypedArray var4 = ThemeEnforcement.obtainStyledAttributes(var1, var2, R.styleable.Slider, var3, DEF_STYLE_RES);
      this.valueFrom = var4.getFloat(R.styleable.Slider_android_valueFrom, 0.0F);
      this.valueTo = var4.getFloat(R.styleable.Slider_android_valueTo, 1.0F);
      this.setValues(this.valueFrom);
      this.stepSize = var4.getFloat(R.styleable.Slider_android_stepSize, 0.0F);
      boolean var5 = var4.hasValue(R.styleable.Slider_trackColor);
      if (var5) {
         var3 = R.styleable.Slider_trackColor;
      } else {
         var3 = R.styleable.Slider_trackColorInactive;
      }

      int var6;
      if (var5) {
         var6 = R.styleable.Slider_trackColor;
      } else {
         var6 = R.styleable.Slider_trackColorActive;
      }

      ColorStateList var8 = MaterialResources.getColorStateList(var1, var4, var3);
      if (var8 == null) {
         var8 = AppCompatResources.getColorStateList(var1, R.color.material_slider_inactive_track_color);
      }

      this.setTrackInactiveTintList(var8);
      var8 = MaterialResources.getColorStateList(var1, var4, var6);
      if (var8 == null) {
         var8 = AppCompatResources.getColorStateList(var1, R.color.material_slider_active_track_color);
      }

      this.setTrackActiveTintList(var8);
      var8 = MaterialResources.getColorStateList(var1, var4, R.styleable.Slider_thumbColor);
      this.thumbDrawable.setFillColor(var8);
      var8 = MaterialResources.getColorStateList(var1, var4, R.styleable.Slider_haloColor);
      if (var8 == null) {
         var8 = AppCompatResources.getColorStateList(var1, R.color.material_slider_halo_color);
      }

      this.setHaloTintList(var8);
      var5 = var4.hasValue(R.styleable.Slider_tickColor);
      if (var5) {
         var3 = R.styleable.Slider_tickColor;
      } else {
         var3 = R.styleable.Slider_tickColorInactive;
      }

      if (var5) {
         var6 = R.styleable.Slider_tickColor;
      } else {
         var6 = R.styleable.Slider_tickColorActive;
      }

      var8 = MaterialResources.getColorStateList(var1, var4, var3);
      if (var8 == null) {
         var8 = AppCompatResources.getColorStateList(var1, R.color.material_slider_inactive_tick_marks_color);
      }

      this.setTickInactiveTintList(var8);
      var8 = MaterialResources.getColorStateList(var1, var4, var6);
      ColorStateList var7;
      if (var8 != null) {
         var7 = var8;
      } else {
         var7 = AppCompatResources.getColorStateList(var1, R.color.material_slider_active_tick_marks_color);
      }

      this.setTickActiveTintList(var7);
      this.setThumbRadius(var4.getDimensionPixelSize(R.styleable.Slider_thumbRadius, 0));
      this.setHaloRadius(var4.getDimensionPixelSize(R.styleable.Slider_haloRadius, 0));
      this.setThumbElevation(var4.getDimension(R.styleable.Slider_thumbElevation, 0.0F));
      this.setTrackHeight(var4.getDimensionPixelSize(R.styleable.Slider_trackHeight, 0));
      this.labelBehavior = var4.getInt(R.styleable.Slider_labelBehavior, 0);
      if (!var4.getBoolean(R.styleable.Slider_android_enabled, true)) {
         this.setEnabled(false);
      }

      var4.recycle();
   }

   private void scheduleAccessibilityEventSender(int var1) {
      BaseSlider.AccessibilityEventSender var2 = this.accessibilityEventSender;
      if (var2 == null) {
         this.accessibilityEventSender = new BaseSlider.AccessibilityEventSender();
      } else {
         this.removeCallbacks(var2);
      }

      this.accessibilityEventSender.setVirtualViewId(var1);
      this.postDelayed(this.accessibilityEventSender, 200L);
   }

   private void setValueForLabel(TooltipDrawable var1, float var2) {
      var1.setText(this.formatValue(var2));
      int var3 = this.trackSidePadding + (int)(this.normalizeValue(var2) * (float)this.trackWidth) - var1.getIntrinsicWidth() / 2;
      int var4 = this.calculateTop() - (this.labelPadding + this.thumbRadius);
      var1.setBounds(var3, var4 - var1.getIntrinsicHeight(), var1.getIntrinsicWidth() + var3, var4);
      Rect var5 = new Rect(var1.getBounds());
      DescendantOffsetUtils.offsetDescendantRect(ViewUtils.getContentView(this), this, var5);
      var1.setBounds(var5);
      ViewUtils.getContentViewOverlay(this).add(var1);
   }

   private void setValuesInternal(ArrayList<Float> var1) {
      if (!var1.isEmpty()) {
         Collections.sort(var1);
         if (this.values.size() != var1.size() || !this.values.equals(var1)) {
            this.values = var1;
            this.dirtyConfig = true;
            this.focusedThumbIdx = 0;
            this.updateHaloHotspot();
            this.createLabelPool();
            this.dispatchOnChangedProgramatically();
            this.postInvalidate();
         }
      } else {
         throw new IllegalArgumentException("At least one value must be set");
      }
   }

   private boolean shouldDrawCompatHalo() {
      boolean var1;
      if (!this.forceDrawCompatHalo && VERSION.SDK_INT >= 21 && this.getBackground() instanceof RippleDrawable) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private boolean snapActiveThumbToValue(float var1) {
      return this.snapThumbToValue(this.activeThumbIdx, var1);
   }

   private double snapPosition(float var1) {
      float var2 = this.stepSize;
      if (var2 > 0.0F) {
         int var3 = (int)((this.valueTo - this.valueFrom) / var2);
         return (double)Math.round(var1 * (float)var3) / (double)var3;
      } else {
         return (double)var1;
      }
   }

   private boolean snapThumbToValue(int var1, float var2) {
      if ((double)Math.abs(var2 - (Float)this.values.get(var1)) < 1.0E-4D) {
         return false;
      } else {
         var2 = this.getClampedValue(var1, var2);
         this.values.set(var1, var2);
         this.focusedThumbIdx = var1;
         this.dispatchOnChangedFromUser(var1);
         return true;
      }
   }

   private boolean snapTouchPosition() {
      return this.snapActiveThumbToValue(this.getValueOfTouchPosition());
   }

   private void updateHaloHotspot() {
      if (!this.shouldDrawCompatHalo() && this.getMeasuredWidth() > 0) {
         Drawable var1 = this.getBackground();
         if (var1 instanceof RippleDrawable) {
            int var2 = (int)(this.normalizeValue((Float)this.values.get(this.focusedThumbIdx)) * (float)this.trackWidth + (float)this.trackSidePadding);
            int var3 = this.calculateTop();
            int var4 = this.haloRadius;
            DrawableCompat.setHotspotBounds(var1, var2 - var4, var3 - var4, var2 + var4, var3 + var4);
         }
      }

   }

   private void validateConfigurationIfDirty() {
      if (this.dirtyConfig) {
         this.validateValueFrom();
         this.validateValueTo();
         this.validateStepSize();
         this.validateValues();
         this.warnAboutFloatingPointError();
         this.dirtyConfig = false;
      }

   }

   private void validateStepSize() {
      if (this.stepSize > 0.0F && !this.valueLandsOnTick(this.valueTo)) {
         throw new IllegalStateException(String.format("The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range", Float.toString(this.stepSize), Float.toString(this.valueFrom), Float.toString(this.valueTo)));
      }
   }

   private void validateValueFrom() {
      if (this.valueFrom >= this.valueTo) {
         throw new IllegalStateException(String.format("valueFrom(%s) must be smaller than valueTo(%s)", Float.toString(this.valueFrom), Float.toString(this.valueTo)));
      }
   }

   private void validateValueTo() {
      if (this.valueTo <= this.valueFrom) {
         throw new IllegalStateException(String.format("valueTo(%s) must be greater than valueFrom(%s)", Float.toString(this.valueTo), Float.toString(this.valueFrom)));
      }
   }

   private void validateValues() {
      Iterator var1 = this.values.iterator();

      Float var2;
      do {
         if (!var1.hasNext()) {
            return;
         }

         var2 = (Float)var1.next();
         if (var2 < this.valueFrom || var2 > this.valueTo) {
            throw new IllegalStateException(String.format("Slider value(%s) must be greater or equal to valueFrom(%s), and lower or equal to valueTo(%s)", Float.toString(var2), Float.toString(this.valueFrom), Float.toString(this.valueTo)));
         }
      } while(this.stepSize <= 0.0F || this.valueLandsOnTick(var2));

      throw new IllegalStateException(String.format("Value(%s) must be equal to valueFrom(%s) plus a multiple of stepSize(%s) when using stepSize(%s)", Float.toString(var2), Float.toString(this.valueFrom), Float.toString(this.stepSize), Float.toString(this.stepSize)));
   }

   private boolean valueLandsOnTick(float var1) {
      double var2 = (new BigDecimal(Float.toString(var1))).subtract(new BigDecimal(Float.toString(this.valueFrom))).divide(new BigDecimal(Float.toString(this.stepSize)), MathContext.DECIMAL64).doubleValue();
      boolean var4;
      if (Math.abs((double)Math.round(var2) - var2) < 1.0E-4D) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   private float valueToX(float var1) {
      return this.normalizeValue(var1) * (float)this.trackWidth + (float)this.trackSidePadding;
   }

   private void warnAboutFloatingPointError() {
      float var1 = this.stepSize;
      if (var1 != 0.0F) {
         if ((float)((int)var1) != var1) {
            Log.w(TAG, String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the  value correctly.", "stepSize", var1));
         }

         var1 = this.valueFrom;
         if ((float)((int)var1) != var1) {
            Log.w(TAG, String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the  value correctly.", "valueFrom", var1));
         }

         var1 = this.valueTo;
         if ((float)((int)var1) != var1) {
            Log.w(TAG, String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the  value correctly.", "valueTo", var1));
         }

      }
   }

   public void addOnChangeListener(L var1) {
      this.changeListeners.add(var1);
   }

   public void addOnSliderTouchListener(T var1) {
      this.touchListeners.add(var1);
   }

   public void clearOnChangeListeners() {
      this.changeListeners.clear();
   }

   public void clearOnSliderTouchListeners() {
      this.touchListeners.clear();
   }

   public boolean dispatchHoverEvent(MotionEvent var1) {
      boolean var2;
      if (!this.accessibilityHelper.dispatchHoverEvent(var1) && !super.dispatchHoverEvent(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public boolean dispatchKeyEvent(KeyEvent var1) {
      return super.dispatchKeyEvent(var1);
   }

   protected void drawableStateChanged() {
      super.drawableStateChanged();
      this.inactiveTrackPaint.setColor(this.getColorForState(this.trackColorInactive));
      this.activeTrackPaint.setColor(this.getColorForState(this.trackColorActive));
      this.inactiveTicksPaint.setColor(this.getColorForState(this.tickColorInactive));
      this.activeTicksPaint.setColor(this.getColorForState(this.tickColorActive));
      Iterator var1 = this.labels.iterator();

      while(var1.hasNext()) {
         TooltipDrawable var2 = (TooltipDrawable)var1.next();
         if (var2.isStateful()) {
            var2.setState(this.getDrawableState());
         }
      }

      if (this.thumbDrawable.isStateful()) {
         this.thumbDrawable.setState(this.getDrawableState());
      }

      this.haloPaint.setColor(this.getColorForState(this.haloColor));
      this.haloPaint.setAlpha(63);
   }

   void forceDrawCompatHalo(boolean var1) {
      this.forceDrawCompatHalo = var1;
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
      if (this.tickColorInactive.equals(this.tickColorActive)) {
         return this.tickColorActive;
      } else {
         throw new IllegalStateException("The inactive and active ticks are different colors. Use the getTickColorInactive() and getTickColorActive() methods instead.");
      }
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
      if (this.trackColorInactive.equals(this.trackColorActive)) {
         return this.trackColorActive;
      } else {
         throw new IllegalStateException("The inactive and active parts of the track are different colors. Use the getInactiveTrackColor() and getActiveTrackColor() methods instead.");
      }
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
      return new ArrayList(this.values);
   }

   public boolean hasLabelFormatter() {
      boolean var1;
      if (this.formatter != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   final boolean isRtl() {
      int var1 = ViewCompat.getLayoutDirection(this);
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      Iterator var1 = this.labels.iterator();

      while(var1.hasNext()) {
         this.attachLabelToContentView((TooltipDrawable)var1.next());
      }

   }

   protected void onDetachedFromWindow() {
      BaseSlider.AccessibilityEventSender var1 = this.accessibilityEventSender;
      if (var1 != null) {
         this.removeCallbacks(var1);
      }

      Iterator var2 = this.labels.iterator();

      while(var2.hasNext()) {
         this.detachLabelFromContentView((TooltipDrawable)var2.next());
      }

      super.onDetachedFromWindow();
   }

   protected void onDraw(Canvas var1) {
      if (this.dirtyConfig) {
         this.validateConfigurationIfDirty();
         if (this.stepSize > 0.0F) {
            this.calculateTicksCoordinates();
         }
      }

      super.onDraw(var1);
      int var2 = this.calculateTop();
      this.drawInactiveTrack(var1, this.trackWidth, var2);
      if ((Float)Collections.max(this.getValues()) > this.valueFrom) {
         this.drawActiveTrack(var1, this.trackWidth, var2);
      }

      if (this.stepSize > 0.0F) {
         this.drawTicks(var1);
      }

      if ((this.thumbIsPressed || this.isFocused()) && this.isEnabled()) {
         this.maybeDrawHalo(var1, this.trackWidth, var2);
         if (this.activeThumbIdx != -1) {
            this.ensureLabels();
         }
      }

      this.drawThumbs(var1, this.trackWidth, var2);
   }

   protected void onFocusChanged(boolean var1, int var2, Rect var3) {
      super.onFocusChanged(var1, var2, var3);
      if (!var1) {
         this.activeThumbIdx = -1;
         Iterator var4 = this.labels.iterator();

         while(var4.hasNext()) {
            TooltipDrawable var5 = (TooltipDrawable)var4.next();
            ViewUtils.getContentViewOverlay(this).remove(var5);
         }

         this.accessibilityHelper.clearKeyboardFocusForVirtualView(this.focusedThumbIdx);
      } else {
         this.focusThumbOnFocusGained(var2);
         this.accessibilityHelper.requestKeyboardFocusForVirtualView(this.focusedThumbIdx);
      }

   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      if (!this.isEnabled()) {
         return super.onKeyDown(var1, var2);
      } else {
         if (this.values.size() == 1) {
            this.activeThumbIdx = 0;
         }

         if (this.activeThumbIdx == -1) {
            Boolean var7 = this.onKeyDownNoActiveThumb(var1, var2);
            boolean var4;
            if (var7 != null) {
               var4 = var7;
            } else {
               var4 = super.onKeyDown(var1, var2);
            }

            return var4;
         } else {
            this.isLongPress |= var2.isLongPress();
            Float var3 = this.calculateIncrementForKey(var1);
            if (var3 != null) {
               if (this.snapActiveThumbToValue((Float)this.values.get(this.activeThumbIdx) + var3)) {
                  this.updateHaloHotspot();
                  this.postInvalidate();
               }

               return true;
            } else {
               if (var1 != 23) {
                  if (var1 == 61) {
                     if (var2.hasNoModifiers()) {
                        return this.moveFocus(1);
                     }

                     if (var2.isShiftPressed()) {
                        return this.moveFocus(-1);
                     }

                     return false;
                  }

                  if (var1 != 66) {
                     return super.onKeyDown(var1, var2);
                  }
               }

               this.activeThumbIdx = -1;
               Iterator var5 = this.labels.iterator();

               while(var5.hasNext()) {
                  TooltipDrawable var6 = (TooltipDrawable)var5.next();
                  ViewUtils.getContentViewOverlay(this).remove(var6);
               }

               this.postInvalidate();
               return true;
            }
         }
      }
   }

   public boolean onKeyUp(int var1, KeyEvent var2) {
      this.isLongPress = false;
      return super.onKeyUp(var1, var2);
   }

   protected void onMeasure(int var1, int var2) {
      int var3 = this.widgetHeight;
      int var4 = this.labelBehavior;
      var2 = 0;
      if (var4 == 1) {
         var2 = ((TooltipDrawable)this.labels.get(0)).getIntrinsicHeight();
      }

      super.onMeasure(var1, MeasureSpec.makeMeasureSpec(var3 + var2, 1073741824));
   }

   protected void onRestoreInstanceState(Parcelable var1) {
      BaseSlider.SliderState var2 = (BaseSlider.SliderState)var1;
      super.onRestoreInstanceState(var2.getSuperState());
      this.valueFrom = var2.valueFrom;
      this.valueTo = var2.valueTo;
      this.setValuesInternal(var2.values);
      this.stepSize = var2.stepSize;
      if (var2.hasFocus) {
         this.requestFocus();
      }

      this.dispatchOnChangedProgramatically();
   }

   protected Parcelable onSaveInstanceState() {
      BaseSlider.SliderState var1 = new BaseSlider.SliderState(super.onSaveInstanceState());
      var1.valueFrom = this.valueFrom;
      var1.valueTo = this.valueTo;
      var1.values = new ArrayList(this.values);
      var1.stepSize = this.stepSize;
      var1.hasFocus = this.hasFocus();
      return var1;
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      this.trackWidth = Math.max(var1 - this.trackSidePadding * 2, 0);
      if (this.stepSize > 0.0F) {
         this.calculateTicksCoordinates();
      }

      this.updateHaloHotspot();
   }

   public boolean onTouchEvent(MotionEvent var1) {
      if (!this.isEnabled()) {
         return false;
      } else {
         float var2 = var1.getX();
         float var3 = (var2 - (float)this.trackSidePadding) / (float)this.trackWidth;
         this.touchPosition = var3;
         var3 = Math.max(0.0F, var3);
         this.touchPosition = var3;
         this.touchPosition = Math.min(1.0F, var3);
         int var4 = var1.getActionMasked();
         if (var4 != 0) {
            if (var4 != 1) {
               if (var4 == 2) {
                  if (!this.thumbIsPressed) {
                     if (Math.abs(var2 - this.touchDownX) < (float)this.scaledTouchSlop) {
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
               MotionEvent var5 = this.lastEvent;
               if (var5 != null && var5.getActionMasked() == 0 && Math.abs(this.lastEvent.getX() - var1.getX()) <= (float)this.scaledTouchSlop && Math.abs(this.lastEvent.getY() - var1.getY()) <= (float)this.scaledTouchSlop) {
                  this.pickActiveThumb();
               }

               if (this.activeThumbIdx != -1) {
                  this.snapTouchPosition();
                  this.activeThumbIdx = -1;
               }

               Iterator var7 = this.labels.iterator();

               while(var7.hasNext()) {
                  TooltipDrawable var6 = (TooltipDrawable)var7.next();
                  ViewUtils.getContentViewOverlay(this).remove(var6);
               }

               this.onStopTrackingTouch();
               this.invalidate();
            }
         } else {
            this.touchDownX = var2;
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
         this.lastEvent = MotionEvent.obtain(var1);
         return true;
      }
   }

   protected boolean pickActiveThumb() {
      int var1 = this.activeThumbIdx;
      boolean var2 = true;
      if (var1 != -1) {
         return true;
      } else {
         float var3 = this.getValueOfTouchPositionAbsolute();
         float var4 = this.valueToX(var3);
         this.activeThumbIdx = 0;
         float var5 = Math.abs((Float)this.values.get(0) - var3);

         float var9;
         for(var1 = 1; var1 < this.values.size(); var5 = var9) {
            float var6 = Math.abs((Float)this.values.get(var1) - var3);
            float var7 = this.valueToX((Float)this.values.get(var1));
            if (Float.compare(var6, var5) > 1) {
               break;
            }

            boolean var8;
            label53: {
               label52: {
                  if (this.isRtl()) {
                     if (var7 - var4 > 0.0F) {
                        break label52;
                     }
                  } else if (var7 - var4 < 0.0F) {
                     break label52;
                  }

                  var8 = false;
                  break label53;
               }

               var8 = true;
            }

            label45: {
               if (Float.compare(var6, var5) < 0) {
                  this.activeThumbIdx = var1;
               } else {
                  var9 = var5;
                  if (Float.compare(var6, var5) != 0) {
                     break label45;
                  }

                  if (Math.abs(var7 - var4) < (float)this.scaledTouchSlop) {
                     this.activeThumbIdx = -1;
                     return false;
                  }

                  var9 = var5;
                  if (!var8) {
                     break label45;
                  }

                  this.activeThumbIdx = var1;
               }

               var9 = var6;
            }

            ++var1;
         }

         if (this.activeThumbIdx == -1) {
            var2 = false;
         }

         return var2;
      }
   }

   public void removeOnChangeListener(L var1) {
      this.changeListeners.remove(var1);
   }

   public void removeOnSliderTouchListener(T var1) {
      this.touchListeners.remove(var1);
   }

   protected void setActiveThumbIndex(int var1) {
      this.activeThumbIdx = var1;
   }

   public void setEnabled(boolean var1) {
      super.setEnabled(var1);
      byte var2;
      if (var1) {
         var2 = 0;
      } else {
         var2 = 2;
      }

      this.setLayerType(var2, (Paint)null);
   }

   public void setFocusedThumbIndex(int var1) {
      if (var1 >= 0 && var1 < this.values.size()) {
         this.focusedThumbIdx = var1;
         this.accessibilityHelper.requestKeyboardFocusForVirtualView(var1);
         this.postInvalidate();
      } else {
         throw new IllegalArgumentException("index out of range");
      }
   }

   public void setHaloRadius(int var1) {
      if (var1 != this.haloRadius) {
         this.haloRadius = var1;
         Drawable var2 = this.getBackground();
         if (!this.shouldDrawCompatHalo() && var2 instanceof RippleDrawable) {
            DrawableUtils.setRippleDrawableRadius((RippleDrawable)var2, this.haloRadius);
         } else {
            this.postInvalidate();
         }
      }
   }

   public void setHaloRadiusResource(int var1) {
      this.setHaloRadius(this.getResources().getDimensionPixelSize(var1));
   }

   public void setHaloTintList(ColorStateList var1) {
      if (!var1.equals(this.haloColor)) {
         this.haloColor = var1;
         Drawable var2 = this.getBackground();
         if (!this.shouldDrawCompatHalo() && var2 instanceof RippleDrawable) {
            ((RippleDrawable)var2).setColor(var1);
         } else {
            this.haloPaint.setColor(this.getColorForState(var1));
            this.haloPaint.setAlpha(63);
            this.invalidate();
         }
      }
   }

   public void setLabelBehavior(int var1) {
      if (this.labelBehavior != var1) {
         this.labelBehavior = var1;
         this.requestLayout();
      }

   }

   public void setLabelFormatter(LabelFormatter var1) {
      this.formatter = var1;
   }

   public void setStepSize(float var1) {
      if (var1 >= 0.0F) {
         if (this.stepSize != var1) {
            this.stepSize = var1;
            this.dirtyConfig = true;
            this.postInvalidate();
         }

      } else {
         throw new IllegalArgumentException(String.format("The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range", Float.toString(var1), Float.toString(this.valueFrom), Float.toString(this.valueTo)));
      }
   }

   public void setThumbElevation(float var1) {
      this.thumbDrawable.setElevation(var1);
   }

   public void setThumbElevationResource(int var1) {
      this.setThumbElevation(this.getResources().getDimension(var1));
   }

   public void setThumbRadius(int var1) {
      if (var1 != this.thumbRadius) {
         this.thumbRadius = var1;
         this.thumbDrawable.setShapeAppearanceModel(ShapeAppearanceModel.builder().setAllCorners(0, (float)this.thumbRadius).build());
         MaterialShapeDrawable var2 = this.thumbDrawable;
         var1 = this.thumbRadius;
         var2.setBounds(0, 0, var1 * 2, var1 * 2);
         this.postInvalidate();
      }
   }

   public void setThumbRadiusResource(int var1) {
      this.setThumbRadius(this.getResources().getDimensionPixelSize(var1));
   }

   public void setThumbTintList(ColorStateList var1) {
      this.thumbDrawable.setFillColor(var1);
   }

   public void setTickActiveTintList(ColorStateList var1) {
      if (!var1.equals(this.tickColorActive)) {
         this.tickColorActive = var1;
         this.activeTicksPaint.setColor(this.getColorForState(var1));
         this.invalidate();
      }
   }

   public void setTickInactiveTintList(ColorStateList var1) {
      if (!var1.equals(this.tickColorInactive)) {
         this.tickColorInactive = var1;
         this.inactiveTicksPaint.setColor(this.getColorForState(var1));
         this.invalidate();
      }
   }

   public void setTickTintList(ColorStateList var1) {
      this.setTickInactiveTintList(var1);
      this.setTickActiveTintList(var1);
   }

   public void setTrackActiveTintList(ColorStateList var1) {
      if (!var1.equals(this.trackColorActive)) {
         this.trackColorActive = var1;
         this.activeTrackPaint.setColor(this.getColorForState(var1));
         this.invalidate();
      }
   }

   public void setTrackHeight(int var1) {
      if (this.trackHeight != var1) {
         this.trackHeight = var1;
         this.invalidateTrack();
         this.postInvalidate();
      }

   }

   public void setTrackInactiveTintList(ColorStateList var1) {
      if (!var1.equals(this.trackColorInactive)) {
         this.trackColorInactive = var1;
         this.inactiveTrackPaint.setColor(this.getColorForState(var1));
         this.invalidate();
      }
   }

   public void setTrackTintList(ColorStateList var1) {
      this.setTrackInactiveTintList(var1);
      this.setTrackActiveTintList(var1);
   }

   public void setValueFrom(float var1) {
      this.valueFrom = var1;
      this.dirtyConfig = true;
      this.postInvalidate();
   }

   public void setValueTo(float var1) {
      this.valueTo = var1;
      this.dirtyConfig = true;
      this.postInvalidate();
   }

   void setValues(List<Float> var1) {
      this.setValuesInternal(new ArrayList(var1));
   }

   void setValues(Float... var1) {
      ArrayList var2 = new ArrayList();
      Collections.addAll(var2, var1);
      this.setValuesInternal(var2);
   }

   void updateBoundsForVirturalViewId(int var1, Rect var2) {
      var1 = this.trackSidePadding + (int)(this.normalizeValue((Float)this.getValues().get(var1)) * (float)this.trackWidth);
      int var3 = this.calculateTop();
      int var4 = this.thumbRadius;
      var2.set(var1 - var4, var3 - var4, var1 + var4, var3 + var4);
   }

   private class AccessibilityEventSender implements Runnable {
      int virtualViewId;

      private AccessibilityEventSender() {
         this.virtualViewId = -1;
      }

      // $FF: synthetic method
      AccessibilityEventSender(Object var2) {
         this();
      }

      public void run() {
         BaseSlider.this.accessibilityHelper.sendEventForVirtualView(this.virtualViewId, 4);
      }

      void setVirtualViewId(int var1) {
         this.virtualViewId = var1;
      }
   }

   private static class AccessibilityHelper extends ExploreByTouchHelper {
      private final BaseSlider<?, ?, ?> slider;
      Rect virtualViewBounds = new Rect();

      AccessibilityHelper(BaseSlider<?, ?, ?> var1) {
         super(var1);
         this.slider = var1;
      }

      private String startOrEndDescription(int var1) {
         if (var1 == this.slider.getValues().size() - 1) {
            return this.slider.getContext().getString(R.string.material_slider_range_end);
         } else {
            return var1 == 0 ? this.slider.getContext().getString(R.string.material_slider_range_start) : "";
         }
      }

      protected int getVirtualViewAt(float var1, float var2) {
         for(int var3 = 0; var3 < this.slider.getValues().size(); ++var3) {
            this.slider.updateBoundsForVirturalViewId(var3, this.virtualViewBounds);
            if (this.virtualViewBounds.contains((int)var1, (int)var2)) {
               return var3;
            }
         }

         return -1;
      }

      protected void getVisibleVirtualViews(List<Integer> var1) {
         for(int var2 = 0; var2 < this.slider.getValues().size(); ++var2) {
            var1.add(var2);
         }

      }

      protected boolean onPerformActionForVirtualView(int var1, int var2, Bundle var3) {
         if (!this.slider.isEnabled()) {
            return false;
         } else {
            float var4;
            if (var2 != 4096 && var2 != 8192) {
               if (var2 != 16908349) {
                  return false;
               } else {
                  if (var3 != null && var3.containsKey("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE")) {
                     var4 = var3.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE");
                     if (this.slider.snapThumbToValue(var1, var4)) {
                        this.slider.updateHaloHotspot();
                        this.slider.postInvalidate();
                        this.invalidateVirtualView(var1);
                        return true;
                     }
                  }

                  return false;
               }
            } else {
               float var5 = this.slider.calculateStepIncrement(20);
               var4 = var5;
               if (var2 == 8192) {
                  var4 = -var5;
               }

               var5 = var4;
               if (this.slider.isRtl()) {
                  var5 = -var4;
               }

               var4 = MathUtils.clamp((Float)this.slider.getValues().get(var1) + var5, this.slider.getValueFrom(), this.slider.getValueTo());
               if (this.slider.snapThumbToValue(var1, var4)) {
                  this.slider.updateHaloHotspot();
                  this.slider.postInvalidate();
                  this.invalidateVirtualView(var1);
                  return true;
               } else {
                  return false;
               }
            }
         }
      }

      protected void onPopulateNodeForVirtualView(int var1, AccessibilityNodeInfoCompat var2) {
         var2.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
         List var3 = this.slider.getValues();
         float var4 = (Float)var3.get(var1);
         float var5 = this.slider.getValueFrom();
         float var6 = this.slider.getValueTo();
         if (this.slider.isEnabled()) {
            if (var4 > var5) {
               var2.addAction(8192);
            }

            if (var4 < var6) {
               var2.addAction(4096);
            }
         }

         var2.setRangeInfo(AccessibilityNodeInfoCompat.RangeInfoCompat.obtain(1, var5, var6, var4));
         var2.setClassName(SeekBar.class.getName());
         StringBuilder var7 = new StringBuilder();
         if (this.slider.getContentDescription() != null) {
            var7.append(this.slider.getContentDescription());
            var7.append(",");
         }

         if (var3.size() > 1) {
            var7.append(this.startOrEndDescription(var1));
            var7.append(this.slider.formatValue(var4));
         }

         var2.setContentDescription(var7.toString());
         this.slider.updateBoundsForVirturalViewId(var1, this.virtualViewBounds);
         var2.setBoundsInParent(this.virtualViewBounds);
      }
   }

   static class SliderState extends BaseSavedState {
      public static final Creator<BaseSlider.SliderState> CREATOR = new Creator<BaseSlider.SliderState>() {
         public BaseSlider.SliderState createFromParcel(Parcel var1) {
            return new BaseSlider.SliderState(var1);
         }

         public BaseSlider.SliderState[] newArray(int var1) {
            return new BaseSlider.SliderState[var1];
         }
      };
      boolean hasFocus;
      float stepSize;
      float valueFrom;
      float valueTo;
      ArrayList<Float> values;

      private SliderState(Parcel var1) {
         super(var1);
         this.valueFrom = var1.readFloat();
         this.valueTo = var1.readFloat();
         ArrayList var2 = new ArrayList();
         this.values = var2;
         var1.readList(var2, Float.class.getClassLoader());
         this.stepSize = var1.readFloat();
         this.hasFocus = var1.createBooleanArray()[0];
      }

      // $FF: synthetic method
      SliderState(Parcel var1, Object var2) {
         this(var1);
      }

      SliderState(Parcelable var1) {
         super(var1);
      }

      public void writeToParcel(Parcel var1, int var2) {
         super.writeToParcel(var1, var2);
         var1.writeFloat(this.valueFrom);
         var1.writeFloat(this.valueTo);
         var1.writeList(this.values);
         var1.writeFloat(this.stepSize);
         var1.writeBooleanArray(new boolean[]{this.hasFocus});
      }
   }

   private interface TooltipDrawableFactory {
      TooltipDrawable createTooltipDrawable();
   }
}
