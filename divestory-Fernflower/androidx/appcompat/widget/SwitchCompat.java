package androidx.appcompat.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.PorterDuff.Mode;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.Layout.Alignment;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Property;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ActionMode.Callback;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.text.AllCapsTransformationMethod;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;

public class SwitchCompat extends CompoundButton {
   private static final String ACCESSIBILITY_EVENT_CLASS_NAME = "android.widget.Switch";
   private static final int[] CHECKED_STATE_SET = new int[]{16842912};
   private static final int MONOSPACE = 3;
   private static final int SANS = 1;
   private static final int SERIF = 2;
   private static final int THUMB_ANIMATION_DURATION = 250;
   private static final Property<SwitchCompat, Float> THUMB_POS = new Property<SwitchCompat, Float>(Float.class, "thumbPos") {
      public Float get(SwitchCompat var1) {
         return var1.mThumbPosition;
      }

      public void set(SwitchCompat var1, Float var2) {
         var1.setThumbPosition(var2);
      }
   };
   private static final int TOUCH_MODE_DOWN = 1;
   private static final int TOUCH_MODE_DRAGGING = 2;
   private static final int TOUCH_MODE_IDLE = 0;
   private boolean mHasThumbTint;
   private boolean mHasThumbTintMode;
   private boolean mHasTrackTint;
   private boolean mHasTrackTintMode;
   private int mMinFlingVelocity;
   private Layout mOffLayout;
   private Layout mOnLayout;
   ObjectAnimator mPositionAnimator;
   private boolean mShowText;
   private boolean mSplitTrack;
   private int mSwitchBottom;
   private int mSwitchHeight;
   private int mSwitchLeft;
   private int mSwitchMinWidth;
   private int mSwitchPadding;
   private int mSwitchRight;
   private int mSwitchTop;
   private TransformationMethod mSwitchTransformationMethod;
   private int mSwitchWidth;
   private final Rect mTempRect;
   private ColorStateList mTextColors;
   private final AppCompatTextHelper mTextHelper;
   private CharSequence mTextOff;
   private CharSequence mTextOn;
   private final TextPaint mTextPaint;
   private Drawable mThumbDrawable;
   float mThumbPosition;
   private int mThumbTextPadding;
   private ColorStateList mThumbTintList;
   private Mode mThumbTintMode;
   private int mThumbWidth;
   private int mTouchMode;
   private int mTouchSlop;
   private float mTouchX;
   private float mTouchY;
   private Drawable mTrackDrawable;
   private ColorStateList mTrackTintList;
   private Mode mTrackTintMode;
   private VelocityTracker mVelocityTracker;

   public SwitchCompat(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public SwitchCompat(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.switchStyle);
   }

   public SwitchCompat(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.mThumbTintList = null;
      this.mThumbTintMode = null;
      this.mHasThumbTint = false;
      this.mHasThumbTintMode = false;
      this.mTrackTintList = null;
      this.mTrackTintMode = null;
      this.mHasTrackTint = false;
      this.mHasTrackTintMode = false;
      this.mVelocityTracker = VelocityTracker.obtain();
      this.mTempRect = new Rect();
      ThemeUtils.checkAppCompatTheme(this, this.getContext());
      this.mTextPaint = new TextPaint(1);
      Resources var4 = this.getResources();
      this.mTextPaint.density = var4.getDisplayMetrics().density;
      TintTypedArray var8 = TintTypedArray.obtainStyledAttributes(var1, var2, R.styleable.SwitchCompat, var3, 0);
      ViewCompat.saveAttributeDataForStyleable(this, var1, R.styleable.SwitchCompat, var2, var8.getWrappedTypeArray(), var3, 0);
      Drawable var5 = var8.getDrawable(R.styleable.SwitchCompat_android_thumb);
      this.mThumbDrawable = var5;
      if (var5 != null) {
         var5.setCallback(this);
      }

      var5 = var8.getDrawable(R.styleable.SwitchCompat_track);
      this.mTrackDrawable = var5;
      if (var5 != null) {
         var5.setCallback(this);
      }

      this.mTextOn = var8.getText(R.styleable.SwitchCompat_android_textOn);
      this.mTextOff = var8.getText(R.styleable.SwitchCompat_android_textOff);
      this.mShowText = var8.getBoolean(R.styleable.SwitchCompat_showText, true);
      this.mThumbTextPadding = var8.getDimensionPixelSize(R.styleable.SwitchCompat_thumbTextPadding, 0);
      this.mSwitchMinWidth = var8.getDimensionPixelSize(R.styleable.SwitchCompat_switchMinWidth, 0);
      this.mSwitchPadding = var8.getDimensionPixelSize(R.styleable.SwitchCompat_switchPadding, 0);
      this.mSplitTrack = var8.getBoolean(R.styleable.SwitchCompat_splitTrack, false);
      ColorStateList var9 = var8.getColorStateList(R.styleable.SwitchCompat_thumbTint);
      if (var9 != null) {
         this.mThumbTintList = var9;
         this.mHasThumbTint = true;
      }

      Mode var10 = DrawableUtils.parseTintMode(var8.getInt(R.styleable.SwitchCompat_thumbTintMode, -1), (Mode)null);
      if (this.mThumbTintMode != var10) {
         this.mThumbTintMode = var10;
         this.mHasThumbTintMode = true;
      }

      if (this.mHasThumbTint || this.mHasThumbTintMode) {
         this.applyThumbTint();
      }

      var9 = var8.getColorStateList(R.styleable.SwitchCompat_trackTint);
      if (var9 != null) {
         this.mTrackTintList = var9;
         this.mHasTrackTint = true;
      }

      var10 = DrawableUtils.parseTintMode(var8.getInt(R.styleable.SwitchCompat_trackTintMode, -1), (Mode)null);
      if (this.mTrackTintMode != var10) {
         this.mTrackTintMode = var10;
         this.mHasTrackTintMode = true;
      }

      if (this.mHasTrackTint || this.mHasTrackTintMode) {
         this.applyTrackTint();
      }

      int var6 = var8.getResourceId(R.styleable.SwitchCompat_switchTextAppearance, 0);
      if (var6 != 0) {
         this.setSwitchTextAppearance(var1, var6);
      }

      AppCompatTextHelper var11 = new AppCompatTextHelper(this);
      this.mTextHelper = var11;
      var11.loadFromAttributes(var2, var3);
      var8.recycle();
      ViewConfiguration var7 = ViewConfiguration.get(var1);
      this.mTouchSlop = var7.getScaledTouchSlop();
      this.mMinFlingVelocity = var7.getScaledMinimumFlingVelocity();
      this.refreshDrawableState();
      this.setChecked(this.isChecked());
   }

   private void animateThumbToCheckedState(boolean var1) {
      float var2;
      if (var1) {
         var2 = 1.0F;
      } else {
         var2 = 0.0F;
      }

      ObjectAnimator var3 = ObjectAnimator.ofFloat(this, THUMB_POS, new float[]{var2});
      this.mPositionAnimator = var3;
      var3.setDuration(250L);
      if (VERSION.SDK_INT >= 18) {
         this.mPositionAnimator.setAutoCancel(true);
      }

      this.mPositionAnimator.start();
   }

   private void applyThumbTint() {
      if (this.mThumbDrawable != null && (this.mHasThumbTint || this.mHasThumbTintMode)) {
         Drawable var1 = DrawableCompat.wrap(this.mThumbDrawable).mutate();
         this.mThumbDrawable = var1;
         if (this.mHasThumbTint) {
            DrawableCompat.setTintList(var1, this.mThumbTintList);
         }

         if (this.mHasThumbTintMode) {
            DrawableCompat.setTintMode(this.mThumbDrawable, this.mThumbTintMode);
         }

         if (this.mThumbDrawable.isStateful()) {
            this.mThumbDrawable.setState(this.getDrawableState());
         }
      }

   }

   private void applyTrackTint() {
      if (this.mTrackDrawable != null && (this.mHasTrackTint || this.mHasTrackTintMode)) {
         Drawable var1 = DrawableCompat.wrap(this.mTrackDrawable).mutate();
         this.mTrackDrawable = var1;
         if (this.mHasTrackTint) {
            DrawableCompat.setTintList(var1, this.mTrackTintList);
         }

         if (this.mHasTrackTintMode) {
            DrawableCompat.setTintMode(this.mTrackDrawable, this.mTrackTintMode);
         }

         if (this.mTrackDrawable.isStateful()) {
            this.mTrackDrawable.setState(this.getDrawableState());
         }
      }

   }

   private void cancelPositionAnimator() {
      ObjectAnimator var1 = this.mPositionAnimator;
      if (var1 != null) {
         var1.cancel();
      }

   }

   private void cancelSuperTouch(MotionEvent var1) {
      var1 = MotionEvent.obtain(var1);
      var1.setAction(3);
      super.onTouchEvent(var1);
      var1.recycle();
   }

   private static float constrain(float var0, float var1, float var2) {
      if (var0 >= var1) {
         var1 = var0;
         if (var0 > var2) {
            var1 = var2;
         }
      }

      return var1;
   }

   private boolean getTargetCheckedState() {
      boolean var1;
      if (this.mThumbPosition > 0.5F) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private int getThumbOffset() {
      float var1;
      if (ViewUtils.isLayoutRtl(this)) {
         var1 = 1.0F - this.mThumbPosition;
      } else {
         var1 = this.mThumbPosition;
      }

      return (int)(var1 * (float)this.getThumbScrollRange() + 0.5F);
   }

   private int getThumbScrollRange() {
      Drawable var1 = this.mTrackDrawable;
      if (var1 != null) {
         Rect var2 = this.mTempRect;
         var1.getPadding(var2);
         var1 = this.mThumbDrawable;
         Rect var3;
         if (var1 != null) {
            var3 = DrawableUtils.getOpticalBounds(var1);
         } else {
            var3 = DrawableUtils.INSETS_NONE;
         }

         return this.mSwitchWidth - this.mThumbWidth - var2.left - var2.right - var3.left - var3.right;
      } else {
         return 0;
      }
   }

   private boolean hitThumb(float var1, float var2) {
      Drawable var3 = this.mThumbDrawable;
      boolean var4 = false;
      if (var3 == null) {
         return false;
      } else {
         int var5 = this.getThumbOffset();
         this.mThumbDrawable.getPadding(this.mTempRect);
         int var6 = this.mSwitchTop;
         int var7 = this.mTouchSlop;
         var5 = this.mSwitchLeft + var5 - var7;
         int var8 = this.mThumbWidth;
         int var9 = this.mTempRect.left;
         int var10 = this.mTempRect.right;
         int var11 = this.mTouchSlop;
         int var12 = this.mSwitchBottom;
         boolean var13 = var4;
         if (var1 > (float)var5) {
            var13 = var4;
            if (var1 < (float)(var8 + var5 + var9 + var10 + var11)) {
               var13 = var4;
               if (var2 > (float)(var6 - var7)) {
                  var13 = var4;
                  if (var2 < (float)(var12 + var11)) {
                     var13 = true;
                  }
               }
            }
         }

         return var13;
      }
   }

   private Layout makeLayout(CharSequence var1) {
      TransformationMethod var2 = this.mSwitchTransformationMethod;
      CharSequence var3 = var1;
      if (var2 != null) {
         var3 = var2.getTransformation(var1, this);
      }

      TextPaint var5 = this.mTextPaint;
      int var4;
      if (var3 != null) {
         var4 = (int)Math.ceil((double)Layout.getDesiredWidth(var3, var5));
      } else {
         var4 = 0;
      }

      return new StaticLayout(var3, var5, var4, Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
   }

   private void setSwitchTypefaceByIndex(int var1, int var2) {
      Typeface var3;
      if (var1 != 1) {
         if (var1 != 2) {
            if (var1 != 3) {
               var3 = null;
            } else {
               var3 = Typeface.MONOSPACE;
            }
         } else {
            var3 = Typeface.SERIF;
         }
      } else {
         var3 = Typeface.SANS_SERIF;
      }

      this.setSwitchTypeface(var3, var2);
   }

   private void stopDrag(MotionEvent var1) {
      this.mTouchMode = 0;
      int var2 = var1.getAction();
      boolean var3 = true;
      boolean var6;
      if (var2 == 1 && this.isEnabled()) {
         var6 = true;
      } else {
         var6 = false;
      }

      boolean var4 = this.isChecked();
      if (var6) {
         this.mVelocityTracker.computeCurrentVelocity(1000);
         float var5 = this.mVelocityTracker.getXVelocity();
         if (Math.abs(var5) > (float)this.mMinFlingVelocity) {
            label27: {
               if (ViewUtils.isLayoutRtl(this)) {
                  if (var5 < 0.0F) {
                     break label27;
                  }
               } else if (var5 > 0.0F) {
                  break label27;
               }

               var3 = false;
            }
         } else {
            var3 = this.getTargetCheckedState();
         }
      } else {
         var3 = var4;
      }

      if (var3 != var4) {
         this.playSoundEffect(0);
      }

      this.setChecked(var3);
      this.cancelSuperTouch(var1);
   }

   public void draw(Canvas var1) {
      Rect var2 = this.mTempRect;
      int var3 = this.mSwitchLeft;
      int var4 = this.mSwitchTop;
      int var5 = this.mSwitchRight;
      int var6 = this.mSwitchBottom;
      int var7 = this.getThumbOffset() + var3;
      Drawable var8 = this.mThumbDrawable;
      Rect var14;
      if (var8 != null) {
         var14 = DrawableUtils.getOpticalBounds(var8);
      } else {
         var14 = DrawableUtils.INSETS_NONE;
      }

      Drawable var9 = this.mTrackDrawable;
      int var10 = var7;
      if (var9 != null) {
         int var11;
         int var12;
         label38: {
            var9.getPadding(var2);
            var11 = var7 + var2.left;
            int var13;
            if (var14 != null) {
               var7 = var3;
               if (var14.left > var2.left) {
                  var7 = var3 + (var14.left - var2.left);
               }

               if (var14.top > var2.top) {
                  var10 = var14.top - var2.top + var4;
               } else {
                  var10 = var4;
               }

               var12 = var5;
               if (var14.right > var2.right) {
                  var12 = var5 - (var14.right - var2.right);
               }

               var3 = var7;
               var5 = var12;
               var13 = var10;
               if (var14.bottom > var2.bottom) {
                  var5 = var6 - (var14.bottom - var2.bottom);
                  var3 = var7;
                  var7 = var5;
                  break label38;
               }
            } else {
               var13 = var4;
            }

            var7 = var6;
            var10 = var13;
            var12 = var5;
         }

         this.mTrackDrawable.setBounds(var3, var10, var12, var7);
         var10 = var11;
      }

      var8 = this.mThumbDrawable;
      if (var8 != null) {
         var8.getPadding(var2);
         var7 = var10 - var2.left;
         var10 = var10 + this.mThumbWidth + var2.right;
         this.mThumbDrawable.setBounds(var7, var4, var10, var6);
         var8 = this.getBackground();
         if (var8 != null) {
            DrawableCompat.setHotspotBounds(var8, var7, var4, var10, var6);
         }
      }

      super.draw(var1);
   }

   public void drawableHotspotChanged(float var1, float var2) {
      if (VERSION.SDK_INT >= 21) {
         super.drawableHotspotChanged(var1, var2);
      }

      Drawable var3 = this.mThumbDrawable;
      if (var3 != null) {
         DrawableCompat.setHotspot(var3, var1, var2);
      }

      var3 = this.mTrackDrawable;
      if (var3 != null) {
         DrawableCompat.setHotspot(var3, var1, var2);
      }

   }

   protected void drawableStateChanged() {
      super.drawableStateChanged();
      int[] var1 = this.getDrawableState();
      Drawable var2 = this.mThumbDrawable;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2 != null) {
         var4 = var3;
         if (var2.isStateful()) {
            var4 = false | var2.setState(var1);
         }
      }

      var2 = this.mTrackDrawable;
      var3 = var4;
      if (var2 != null) {
         var3 = var4;
         if (var2.isStateful()) {
            var3 = var4 | var2.setState(var1);
         }
      }

      if (var3) {
         this.invalidate();
      }

   }

   public int getCompoundPaddingLeft() {
      if (!ViewUtils.isLayoutRtl(this)) {
         return super.getCompoundPaddingLeft();
      } else {
         int var1 = super.getCompoundPaddingLeft() + this.mSwitchWidth;
         int var2 = var1;
         if (!TextUtils.isEmpty(this.getText())) {
            var2 = var1 + this.mSwitchPadding;
         }

         return var2;
      }
   }

   public int getCompoundPaddingRight() {
      if (ViewUtils.isLayoutRtl(this)) {
         return super.getCompoundPaddingRight();
      } else {
         int var1 = super.getCompoundPaddingRight() + this.mSwitchWidth;
         int var2 = var1;
         if (!TextUtils.isEmpty(this.getText())) {
            var2 = var1 + this.mSwitchPadding;
         }

         return var2;
      }
   }

   public boolean getShowText() {
      return this.mShowText;
   }

   public boolean getSplitTrack() {
      return this.mSplitTrack;
   }

   public int getSwitchMinWidth() {
      return this.mSwitchMinWidth;
   }

   public int getSwitchPadding() {
      return this.mSwitchPadding;
   }

   public CharSequence getTextOff() {
      return this.mTextOff;
   }

   public CharSequence getTextOn() {
      return this.mTextOn;
   }

   public Drawable getThumbDrawable() {
      return this.mThumbDrawable;
   }

   public int getThumbTextPadding() {
      return this.mThumbTextPadding;
   }

   public ColorStateList getThumbTintList() {
      return this.mThumbTintList;
   }

   public Mode getThumbTintMode() {
      return this.mThumbTintMode;
   }

   public Drawable getTrackDrawable() {
      return this.mTrackDrawable;
   }

   public ColorStateList getTrackTintList() {
      return this.mTrackTintList;
   }

   public Mode getTrackTintMode() {
      return this.mTrackTintMode;
   }

   public void jumpDrawablesToCurrentState() {
      super.jumpDrawablesToCurrentState();
      Drawable var1 = this.mThumbDrawable;
      if (var1 != null) {
         var1.jumpToCurrentState();
      }

      var1 = this.mTrackDrawable;
      if (var1 != null) {
         var1.jumpToCurrentState();
      }

      ObjectAnimator var2 = this.mPositionAnimator;
      if (var2 != null && var2.isStarted()) {
         this.mPositionAnimator.end();
         this.mPositionAnimator = null;
      }

   }

   protected int[] onCreateDrawableState(int var1) {
      int[] var2 = super.onCreateDrawableState(var1 + 1);
      if (this.isChecked()) {
         mergeDrawableStates(var2, CHECKED_STATE_SET);
      }

      return var2;
   }

   protected void onDraw(Canvas var1) {
      super.onDraw(var1);
      Rect var2 = this.mTempRect;
      Drawable var3 = this.mTrackDrawable;
      if (var3 != null) {
         var3.getPadding(var2);
      } else {
         var2.setEmpty();
      }

      int var4 = this.mSwitchTop;
      int var5 = this.mSwitchBottom;
      int var6 = var2.top;
      int var7 = var2.bottom;
      Drawable var8 = this.mThumbDrawable;
      int var10;
      if (var3 != null) {
         if (this.mSplitTrack && var8 != null) {
            Rect var9 = DrawableUtils.getOpticalBounds(var8);
            var8.copyBounds(var2);
            var2.left += var9.left;
            var2.right -= var9.right;
            var10 = var1.save();
            var1.clipRect(var2, Op.DIFFERENCE);
            var3.draw(var1);
            var1.restoreToCount(var10);
         } else {
            var3.draw(var1);
         }
      }

      int var11 = var1.save();
      if (var8 != null) {
         var8.draw(var1);
      }

      Layout var13;
      if (this.getTargetCheckedState()) {
         var13 = this.mOnLayout;
      } else {
         var13 = this.mOffLayout;
      }

      if (var13 != null) {
         int[] var14 = this.getDrawableState();
         ColorStateList var16 = this.mTextColors;
         if (var16 != null) {
            this.mTextPaint.setColor(var16.getColorForState(var14, 0));
         }

         this.mTextPaint.drawableState = var14;
         if (var8 != null) {
            Rect var15 = var8.getBounds();
            var10 = var15.left + var15.right;
         } else {
            var10 = this.getWidth();
         }

         int var12 = var10 / 2;
         var10 = var13.getWidth() / 2;
         var7 = (var4 + var6 + (var5 - var7)) / 2;
         var5 = var13.getHeight() / 2;
         var1.translate((float)(var12 - var10), (float)(var7 - var5));
         var13.draw(var1);
      }

      var1.restoreToCount(var11);
   }

   public void onInitializeAccessibilityEvent(AccessibilityEvent var1) {
      super.onInitializeAccessibilityEvent(var1);
      var1.setClassName("android.widget.Switch");
   }

   public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1) {
      super.onInitializeAccessibilityNodeInfo(var1);
      var1.setClassName("android.widget.Switch");
      CharSequence var2;
      if (this.isChecked()) {
         var2 = this.mTextOn;
      } else {
         var2 = this.mTextOff;
      }

      if (!TextUtils.isEmpty(var2)) {
         CharSequence var3 = var1.getText();
         if (TextUtils.isEmpty(var3)) {
            var1.setText(var2);
         } else {
            StringBuilder var4 = new StringBuilder();
            var4.append(var3);
            var4.append(' ');
            var4.append(var2);
            var1.setText(var4);
         }
      }

   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      super.onLayout(var1, var2, var3, var4, var5);
      Drawable var6 = this.mThumbDrawable;
      var2 = 0;
      if (var6 != null) {
         Rect var8 = this.mTempRect;
         Drawable var7 = this.mTrackDrawable;
         if (var7 != null) {
            var7.getPadding(var8);
         } else {
            var8.setEmpty();
         }

         Rect var9 = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
         var3 = Math.max(0, var9.left - var8.left);
         var2 = Math.max(0, var9.right - var8.right);
      } else {
         var3 = 0;
      }

      if (ViewUtils.isLayoutRtl(this)) {
         var4 = this.getPaddingLeft() + var3;
         var2 = this.mSwitchWidth + var4 - var3 - var2;
         var3 = var4;
         var4 = var2;
      } else {
         var4 = this.getWidth() - this.getPaddingRight() - var2;
         var3 = var4 - this.mSwitchWidth + var3 + var2;
      }

      label29: {
         var2 = this.getGravity() & 112;
         if (var2 != 16) {
            if (var2 == 80) {
               var5 = this.getHeight() - this.getPaddingBottom();
               var2 = var5 - this.mSwitchHeight;
               break label29;
            }

            var2 = this.getPaddingTop();
            var5 = this.mSwitchHeight;
         } else {
            var2 = (this.getPaddingTop() + this.getHeight() - this.getPaddingBottom()) / 2;
            var5 = this.mSwitchHeight;
            var2 -= var5 / 2;
         }

         var5 += var2;
      }

      this.mSwitchLeft = var3;
      this.mSwitchTop = var2;
      this.mSwitchBottom = var5;
      this.mSwitchRight = var4;
   }

   public void onMeasure(int var1, int var2) {
      if (this.mShowText) {
         if (this.mOnLayout == null) {
            this.mOnLayout = this.makeLayout(this.mTextOn);
         }

         if (this.mOffLayout == null) {
            this.mOffLayout = this.makeLayout(this.mTextOff);
         }
      }

      Rect var3 = this.mTempRect;
      Drawable var4 = this.mThumbDrawable;
      byte var5 = 0;
      int var6;
      int var7;
      if (var4 != null) {
         var4.getPadding(var3);
         var6 = this.mThumbDrawable.getIntrinsicWidth() - var3.left - var3.right;
         var7 = this.mThumbDrawable.getIntrinsicHeight();
      } else {
         var6 = 0;
         var7 = 0;
      }

      int var8;
      if (this.mShowText) {
         var8 = Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth()) + this.mThumbTextPadding * 2;
      } else {
         var8 = 0;
      }

      this.mThumbWidth = Math.max(var8, var6);
      var4 = this.mTrackDrawable;
      if (var4 != null) {
         var4.getPadding(var3);
         var6 = this.mTrackDrawable.getIntrinsicHeight();
      } else {
         var3.setEmpty();
         var6 = var5;
      }

      int var9 = var3.left;
      int var10 = var3.right;
      Drawable var11 = this.mThumbDrawable;
      int var12 = var10;
      var8 = var9;
      if (var11 != null) {
         var3 = DrawableUtils.getOpticalBounds(var11);
         var8 = Math.max(var9, var3.left);
         var12 = Math.max(var10, var3.right);
      }

      var8 = Math.max(this.mSwitchMinWidth, this.mThumbWidth * 2 + var8 + var12);
      var7 = Math.max(var6, var7);
      this.mSwitchWidth = var8;
      this.mSwitchHeight = var7;
      super.onMeasure(var1, var2);
      if (this.getMeasuredHeight() < var7) {
         this.setMeasuredDimension(this.getMeasuredWidthAndState(), var7);
      }

   }

   public void onPopulateAccessibilityEvent(AccessibilityEvent var1) {
      super.onPopulateAccessibilityEvent(var1);
      CharSequence var2;
      if (this.isChecked()) {
         var2 = this.mTextOn;
      } else {
         var2 = this.mTextOff;
      }

      if (var2 != null) {
         var1.getText().add(var2);
      }

   }

   public boolean onTouchEvent(MotionEvent var1) {
      this.mVelocityTracker.addMovement(var1);
      int var2 = var1.getActionMasked();
      float var4;
      float var5;
      if (var2 != 0) {
         if (var2 != 1) {
            if (var2 == 2) {
               var2 = this.mTouchMode;
               if (var2 != 1) {
                  if (var2 == 2) {
                     float var3 = var1.getX();
                     var2 = this.getThumbScrollRange();
                     var4 = var3 - this.mTouchX;
                     if (var2 != 0) {
                        var4 /= (float)var2;
                     } else if (var4 > 0.0F) {
                        var4 = 1.0F;
                     } else {
                        var4 = -1.0F;
                     }

                     var5 = var4;
                     if (ViewUtils.isLayoutRtl(this)) {
                        var5 = -var4;
                     }

                     var4 = constrain(this.mThumbPosition + var5, 0.0F, 1.0F);
                     if (var4 != this.mThumbPosition) {
                        this.mTouchX = var3;
                        this.setThumbPosition(var4);
                     }

                     return true;
                  }
               } else {
                  var4 = var1.getX();
                  var5 = var1.getY();
                  if (Math.abs(var4 - this.mTouchX) > (float)this.mTouchSlop || Math.abs(var5 - this.mTouchY) > (float)this.mTouchSlop) {
                     this.mTouchMode = 2;
                     this.getParent().requestDisallowInterceptTouchEvent(true);
                     this.mTouchX = var4;
                     this.mTouchY = var5;
                     return true;
                  }
               }

               return super.onTouchEvent(var1);
            }

            if (var2 != 3) {
               return super.onTouchEvent(var1);
            }
         }

         if (this.mTouchMode == 2) {
            this.stopDrag(var1);
            super.onTouchEvent(var1);
            return true;
         }

         this.mTouchMode = 0;
         this.mVelocityTracker.clear();
      } else {
         var5 = var1.getX();
         var4 = var1.getY();
         if (this.isEnabled() && this.hitThumb(var5, var4)) {
            this.mTouchMode = 1;
            this.mTouchX = var5;
            this.mTouchY = var4;
         }
      }

      return super.onTouchEvent(var1);
   }

   public void setChecked(boolean var1) {
      super.setChecked(var1);
      var1 = this.isChecked();
      if (this.getWindowToken() != null && ViewCompat.isLaidOut(this)) {
         this.animateThumbToCheckedState(var1);
      } else {
         this.cancelPositionAnimator();
         float var2;
         if (var1) {
            var2 = 1.0F;
         } else {
            var2 = 0.0F;
         }

         this.setThumbPosition(var2);
      }

   }

   public void setCustomSelectionActionModeCallback(Callback var1) {
      super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, var1));
   }

   public void setShowText(boolean var1) {
      if (this.mShowText != var1) {
         this.mShowText = var1;
         this.requestLayout();
      }

   }

   public void setSplitTrack(boolean var1) {
      this.mSplitTrack = var1;
      this.invalidate();
   }

   public void setSwitchMinWidth(int var1) {
      this.mSwitchMinWidth = var1;
      this.requestLayout();
   }

   public void setSwitchPadding(int var1) {
      this.mSwitchPadding = var1;
      this.requestLayout();
   }

   public void setSwitchTextAppearance(Context var1, int var2) {
      TintTypedArray var5 = TintTypedArray.obtainStyledAttributes(var1, var2, R.styleable.TextAppearance);
      ColorStateList var3 = var5.getColorStateList(R.styleable.TextAppearance_android_textColor);
      if (var3 != null) {
         this.mTextColors = var3;
      } else {
         this.mTextColors = this.getTextColors();
      }

      var2 = var5.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
      if (var2 != 0) {
         float var4 = (float)var2;
         if (var4 != this.mTextPaint.getTextSize()) {
            this.mTextPaint.setTextSize(var4);
            this.requestLayout();
         }
      }

      this.setSwitchTypefaceByIndex(var5.getInt(R.styleable.TextAppearance_android_typeface, -1), var5.getInt(R.styleable.TextAppearance_android_textStyle, -1));
      if (var5.getBoolean(R.styleable.TextAppearance_textAllCaps, false)) {
         this.mSwitchTransformationMethod = new AllCapsTransformationMethod(this.getContext());
      } else {
         this.mSwitchTransformationMethod = null;
      }

      var5.recycle();
   }

   public void setSwitchTypeface(Typeface var1) {
      if (this.mTextPaint.getTypeface() != null && !this.mTextPaint.getTypeface().equals(var1) || this.mTextPaint.getTypeface() == null && var1 != null) {
         this.mTextPaint.setTypeface(var1);
         this.requestLayout();
         this.invalidate();
      }

   }

   public void setSwitchTypeface(Typeface var1, int var2) {
      float var3 = 0.0F;
      boolean var4 = false;
      if (var2 > 0) {
         if (var1 == null) {
            var1 = Typeface.defaultFromStyle(var2);
         } else {
            var1 = Typeface.create(var1, var2);
         }

         this.setSwitchTypeface(var1);
         int var5;
         if (var1 != null) {
            var5 = var1.getStyle();
         } else {
            var5 = 0;
         }

         var2 &= var5;
         TextPaint var6 = this.mTextPaint;
         if ((var2 & 1) != 0) {
            var4 = true;
         }

         var6.setFakeBoldText(var4);
         var6 = this.mTextPaint;
         if ((var2 & 2) != 0) {
            var3 = -0.25F;
         }

         var6.setTextSkewX(var3);
      } else {
         this.mTextPaint.setFakeBoldText(false);
         this.mTextPaint.setTextSkewX(0.0F);
         this.setSwitchTypeface(var1);
      }

   }

   public void setTextOff(CharSequence var1) {
      this.mTextOff = var1;
      this.requestLayout();
   }

   public void setTextOn(CharSequence var1) {
      this.mTextOn = var1;
      this.requestLayout();
   }

   public void setThumbDrawable(Drawable var1) {
      Drawable var2 = this.mThumbDrawable;
      if (var2 != null) {
         var2.setCallback((android.graphics.drawable.Drawable.Callback)null);
      }

      this.mThumbDrawable = var1;
      if (var1 != null) {
         var1.setCallback(this);
      }

      this.requestLayout();
   }

   void setThumbPosition(float var1) {
      this.mThumbPosition = var1;
      this.invalidate();
   }

   public void setThumbResource(int var1) {
      this.setThumbDrawable(AppCompatResources.getDrawable(this.getContext(), var1));
   }

   public void setThumbTextPadding(int var1) {
      this.mThumbTextPadding = var1;
      this.requestLayout();
   }

   public void setThumbTintList(ColorStateList var1) {
      this.mThumbTintList = var1;
      this.mHasThumbTint = true;
      this.applyThumbTint();
   }

   public void setThumbTintMode(Mode var1) {
      this.mThumbTintMode = var1;
      this.mHasThumbTintMode = true;
      this.applyThumbTint();
   }

   public void setTrackDrawable(Drawable var1) {
      Drawable var2 = this.mTrackDrawable;
      if (var2 != null) {
         var2.setCallback((android.graphics.drawable.Drawable.Callback)null);
      }

      this.mTrackDrawable = var1;
      if (var1 != null) {
         var1.setCallback(this);
      }

      this.requestLayout();
   }

   public void setTrackResource(int var1) {
      this.setTrackDrawable(AppCompatResources.getDrawable(this.getContext(), var1));
   }

   public void setTrackTintList(ColorStateList var1) {
      this.mTrackTintList = var1;
      this.mHasTrackTint = true;
      this.applyTrackTint();
   }

   public void setTrackTintMode(Mode var1) {
      this.mTrackTintMode = var1;
      this.mHasTrackTintMode = true;
      this.applyTrackTint();
   }

   public void toggle() {
      this.setChecked(this.isChecked() ^ true);
   }

   protected boolean verifyDrawable(Drawable var1) {
      boolean var2;
      if (!super.verifyDrawable(var1) && var1 != this.mThumbDrawable && var1 != this.mTrackDrawable) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }
}
