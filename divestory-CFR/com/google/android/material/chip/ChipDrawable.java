/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Outline
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$FontMetrics
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PointF
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.RippleDrawable
 *  android.graphics.drawable.ShapeDrawable
 *  android.graphics.drawable.shapes.OvalShape
 *  android.graphics.drawable.shapes.Shape
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 */
package com.google.android.material.chip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.core.text.BidiFormatter;
import com.google.android.material.R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.canvas.CanvasCompat;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.ref.WeakReference;
import java.util.Arrays;

public class ChipDrawable
extends MaterialShapeDrawable
implements TintAwareDrawable,
Drawable.Callback,
TextDrawableHelper.TextDrawableDelegate {
    private static final boolean DEBUG = false;
    private static final int[] DEFAULT_STATE = new int[]{16842910};
    private static final String NAMESPACE_APP = "http://schemas.android.com/apk/res-auto";
    private static final ShapeDrawable closeIconRippleMask = new ShapeDrawable((Shape)new OvalShape());
    private int alpha = 255;
    private boolean checkable;
    private Drawable checkedIcon;
    private ColorStateList checkedIconTint;
    private boolean checkedIconVisible;
    private ColorStateList chipBackgroundColor;
    private float chipCornerRadius = -1.0f;
    private float chipEndPadding;
    private Drawable chipIcon;
    private float chipIconSize;
    private ColorStateList chipIconTint;
    private boolean chipIconVisible;
    private float chipMinHeight;
    private final Paint chipPaint = new Paint(1);
    private float chipStartPadding;
    private ColorStateList chipStrokeColor;
    private float chipStrokeWidth;
    private ColorStateList chipSurfaceColor;
    private Drawable closeIcon;
    private CharSequence closeIconContentDescription;
    private float closeIconEndPadding;
    private Drawable closeIconRipple;
    private float closeIconSize;
    private float closeIconStartPadding;
    private int[] closeIconStateSet;
    private ColorStateList closeIconTint;
    private boolean closeIconVisible;
    private ColorFilter colorFilter;
    private ColorStateList compatRippleColor;
    private final Context context;
    private boolean currentChecked;
    private int currentChipBackgroundColor;
    private int currentChipStrokeColor;
    private int currentChipSurfaceColor;
    private int currentCompatRippleColor;
    private int currentCompositeSurfaceBackgroundColor;
    private int currentTextColor;
    private int currentTint;
    private final Paint debugPaint;
    private WeakReference<Delegate> delegate = new WeakReference<Object>(null);
    private final Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    private boolean hasChipIconTint;
    private MotionSpec hideMotionSpec;
    private float iconEndPadding;
    private float iconStartPadding;
    private boolean isShapeThemingEnabled;
    private int maxWidth;
    private final PointF pointF = new PointF();
    private final RectF rectF = new RectF();
    private ColorStateList rippleColor;
    private final Path shapePath = new Path();
    private boolean shouldDrawText;
    private MotionSpec showMotionSpec;
    private CharSequence text;
    private final TextDrawableHelper textDrawableHelper;
    private float textEndPadding;
    private float textStartPadding;
    private ColorStateList tint;
    private PorterDuffColorFilter tintFilter;
    private PorterDuff.Mode tintMode = PorterDuff.Mode.SRC_IN;
    private TextUtils.TruncateAt truncateAt;
    private boolean useCompatRipple;

    private ChipDrawable(Context context, AttributeSet object, int n, int n2) {
        super(context, (AttributeSet)object, n, n2);
        this.initializeElevationOverlay(context);
        this.context = context;
        object = new TextDrawableHelper(this);
        this.textDrawableHelper = object;
        this.text = "";
        object.getTextPaint().density = context.getResources().getDisplayMetrics().density;
        this.debugPaint = null;
        if (false) {
            context = Paint.Style.STROKE;
            throw new NullPointerException();
        }
        this.setState(DEFAULT_STATE);
        this.setCloseIconState(DEFAULT_STATE);
        this.shouldDrawText = true;
        if (!RippleUtils.USE_FRAMEWORK_RIPPLE) return;
        closeIconRippleMask.setTint(-1);
    }

    private void applyChildDrawable(Drawable drawable2) {
        Drawable drawable3;
        if (drawable2 == null) {
            return;
        }
        drawable2.setCallback((Drawable.Callback)this);
        DrawableCompat.setLayoutDirection(drawable2, DrawableCompat.getLayoutDirection(this));
        drawable2.setLevel(this.getLevel());
        drawable2.setVisible(this.isVisible(), false);
        if (drawable2 == this.closeIcon) {
            if (drawable2.isStateful()) {
                drawable2.setState(this.getCloseIconState());
            }
            DrawableCompat.setTintList(drawable2, this.closeIconTint);
            return;
        }
        if (drawable2.isStateful()) {
            drawable2.setState(this.getState());
        }
        if (drawable2 != (drawable3 = this.chipIcon)) return;
        if (!this.hasChipIconTint) return;
        DrawableCompat.setTintList(drawable3, this.chipIconTint);
    }

    private void calculateChipIconBounds(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (!this.showsChipIcon()) {
            if (!this.showsCheckedIcon()) return;
        }
        float f = this.chipStartPadding + this.iconStartPadding;
        if (DrawableCompat.getLayoutDirection(this) == 0) {
            rectF.left = (float)rect.left + f;
            rectF.right = rectF.left + this.chipIconSize;
        } else {
            rectF.right = (float)rect.right - f;
            rectF.left = rectF.right - this.chipIconSize;
        }
        rectF.top = rect.exactCenterY() - this.chipIconSize / 2.0f;
        rectF.bottom = rectF.top + this.chipIconSize;
    }

    private void calculateChipTouchBounds(Rect rect, RectF rectF) {
        rectF.set(rect);
        if (!this.showsCloseIcon()) return;
        float f = this.chipEndPadding + this.closeIconEndPadding + this.closeIconSize + this.closeIconStartPadding + this.textEndPadding;
        if (DrawableCompat.getLayoutDirection(this) == 0) {
            rectF.right = (float)rect.right - f;
            return;
        }
        rectF.left = (float)rect.left + f;
    }

    private void calculateCloseIconBounds(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (!this.showsCloseIcon()) return;
        float f = this.chipEndPadding + this.closeIconEndPadding;
        if (DrawableCompat.getLayoutDirection(this) == 0) {
            rectF.right = (float)rect.right - f;
            rectF.left = rectF.right - this.closeIconSize;
        } else {
            rectF.left = (float)rect.left + f;
            rectF.right = rectF.left + this.closeIconSize;
        }
        rectF.top = rect.exactCenterY() - this.closeIconSize / 2.0f;
        rectF.bottom = rectF.top + this.closeIconSize;
    }

    private void calculateCloseIconTouchBounds(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (!this.showsCloseIcon()) return;
        float f = this.chipEndPadding + this.closeIconEndPadding + this.closeIconSize + this.closeIconStartPadding + this.textEndPadding;
        if (DrawableCompat.getLayoutDirection(this) == 0) {
            rectF.right = rect.right;
            rectF.left = rectF.right - f;
        } else {
            rectF.left = rect.left;
            rectF.right = (float)rect.left + f;
        }
        rectF.top = rect.top;
        rectF.bottom = rect.bottom;
    }

    private void calculateTextBounds(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (this.text == null) return;
        float f = this.chipStartPadding + this.calculateChipIconWidth() + this.textStartPadding;
        float f2 = this.chipEndPadding + this.calculateCloseIconWidth() + this.textEndPadding;
        if (DrawableCompat.getLayoutDirection(this) == 0) {
            rectF.left = (float)rect.left + f;
            rectF.right = (float)rect.right - f2;
        } else {
            rectF.left = (float)rect.left + f2;
            rectF.right = (float)rect.right - f;
        }
        rectF.top = rect.top;
        rectF.bottom = rect.bottom;
    }

    private float calculateTextCenterFromBaseline() {
        this.textDrawableHelper.getTextPaint().getFontMetrics(this.fontMetrics);
        return (this.fontMetrics.descent + this.fontMetrics.ascent) / 2.0f;
    }

    private boolean canShowCheckedIcon() {
        if (!this.checkedIconVisible) return false;
        if (this.checkedIcon == null) return false;
        if (!this.checkable) return false;
        return true;
    }

    public static ChipDrawable createFromAttributes(Context object, AttributeSet attributeSet, int n, int n2) {
        object = new ChipDrawable((Context)object, attributeSet, n, n2);
        ChipDrawable.super.loadFromAttributes(attributeSet, n, n2);
        return object;
    }

    public static ChipDrawable createFromResource(Context context, int n) {
        int n2;
        AttributeSet attributeSet = DrawableUtils.parseDrawableXml(context, n, "chip");
        n = n2 = attributeSet.getStyleAttribute();
        if (n2 != 0) return ChipDrawable.createFromAttributes(context, attributeSet, R.attr.chipStandaloneStyle, n);
        n = R.style.Widget_MaterialComponents_Chip_Entry;
        return ChipDrawable.createFromAttributes(context, attributeSet, R.attr.chipStandaloneStyle, n);
    }

    private void drawCheckedIcon(Canvas canvas, Rect rect) {
        if (!this.showsCheckedIcon()) return;
        this.calculateChipIconBounds(rect, this.rectF);
        float f = this.rectF.left;
        float f2 = this.rectF.top;
        canvas.translate(f, f2);
        this.checkedIcon.setBounds(0, 0, (int)this.rectF.width(), (int)this.rectF.height());
        this.checkedIcon.draw(canvas);
        canvas.translate(-f, -f2);
    }

    private void drawChipBackground(Canvas canvas, Rect rect) {
        if (this.isShapeThemingEnabled) return;
        this.chipPaint.setColor(this.currentChipBackgroundColor);
        this.chipPaint.setStyle(Paint.Style.FILL);
        this.chipPaint.setColorFilter(this.getTintColorFilter());
        this.rectF.set(rect);
        canvas.drawRoundRect(this.rectF, this.getChipCornerRadius(), this.getChipCornerRadius(), this.chipPaint);
    }

    private void drawChipIcon(Canvas canvas, Rect rect) {
        if (!this.showsChipIcon()) return;
        this.calculateChipIconBounds(rect, this.rectF);
        float f = this.rectF.left;
        float f2 = this.rectF.top;
        canvas.translate(f, f2);
        this.chipIcon.setBounds(0, 0, (int)this.rectF.width(), (int)this.rectF.height());
        this.chipIcon.draw(canvas);
        canvas.translate(-f, -f2);
    }

    private void drawChipStroke(Canvas canvas, Rect rect) {
        if (!(this.chipStrokeWidth > 0.0f)) return;
        if (this.isShapeThemingEnabled) return;
        this.chipPaint.setColor(this.currentChipStrokeColor);
        this.chipPaint.setStyle(Paint.Style.STROKE);
        if (!this.isShapeThemingEnabled) {
            this.chipPaint.setColorFilter(this.getTintColorFilter());
        }
        this.rectF.set((float)rect.left + this.chipStrokeWidth / 2.0f, (float)rect.top + this.chipStrokeWidth / 2.0f, (float)rect.right - this.chipStrokeWidth / 2.0f, (float)rect.bottom - this.chipStrokeWidth / 2.0f);
        float f = this.chipCornerRadius - this.chipStrokeWidth / 2.0f;
        canvas.drawRoundRect(this.rectF, f, f, this.chipPaint);
    }

    private void drawChipSurface(Canvas canvas, Rect rect) {
        if (this.isShapeThemingEnabled) return;
        this.chipPaint.setColor(this.currentChipSurfaceColor);
        this.chipPaint.setStyle(Paint.Style.FILL);
        this.rectF.set(rect);
        canvas.drawRoundRect(this.rectF, this.getChipCornerRadius(), this.getChipCornerRadius(), this.chipPaint);
    }

    private void drawCloseIcon(Canvas canvas, Rect rect) {
        if (!this.showsCloseIcon()) return;
        this.calculateCloseIconBounds(rect, this.rectF);
        float f = this.rectF.left;
        float f2 = this.rectF.top;
        canvas.translate(f, f2);
        this.closeIcon.setBounds(0, 0, (int)this.rectF.width(), (int)this.rectF.height());
        if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
            this.closeIconRipple.setBounds(this.closeIcon.getBounds());
            this.closeIconRipple.jumpToCurrentState();
            this.closeIconRipple.draw(canvas);
        } else {
            this.closeIcon.draw(canvas);
        }
        canvas.translate(-f, -f2);
    }

    private void drawCompatRipple(Canvas canvas, Rect rect) {
        this.chipPaint.setColor(this.currentCompatRippleColor);
        this.chipPaint.setStyle(Paint.Style.FILL);
        this.rectF.set(rect);
        if (!this.isShapeThemingEnabled) {
            canvas.drawRoundRect(this.rectF, this.getChipCornerRadius(), this.getChipCornerRadius(), this.chipPaint);
            return;
        }
        this.calculatePathForSize(new RectF(rect), this.shapePath);
        super.drawShape(canvas, this.chipPaint, this.shapePath, this.getBoundsAsRectF());
    }

    private void drawDebug(Canvas canvas, Rect rect) {
        Paint paint = this.debugPaint;
        if (paint == null) return;
        paint.setColor(ColorUtils.setAlphaComponent(-16777216, 127));
        canvas.drawRect(rect, this.debugPaint);
        if (this.showsChipIcon() || this.showsCheckedIcon()) {
            this.calculateChipIconBounds(rect, this.rectF);
            canvas.drawRect(this.rectF, this.debugPaint);
        }
        if (this.text != null) {
            canvas.drawLine((float)rect.left, rect.exactCenterY(), (float)rect.right, rect.exactCenterY(), this.debugPaint);
        }
        if (this.showsCloseIcon()) {
            this.calculateCloseIconBounds(rect, this.rectF);
            canvas.drawRect(this.rectF, this.debugPaint);
        }
        this.debugPaint.setColor(ColorUtils.setAlphaComponent(-65536, 127));
        this.calculateChipTouchBounds(rect, this.rectF);
        canvas.drawRect(this.rectF, this.debugPaint);
        this.debugPaint.setColor(ColorUtils.setAlphaComponent(-16711936, 127));
        this.calculateCloseIconTouchBounds(rect, this.rectF);
        canvas.drawRect(this.rectF, this.debugPaint);
    }

    private void drawText(Canvas canvas, Rect object) {
        if (this.text == null) return;
        Object object2 = this.calculateTextOriginAndAlignment((Rect)object, this.pointF);
        this.calculateTextBounds((Rect)object, this.rectF);
        if (this.textDrawableHelper.getTextAppearance() != null) {
            this.textDrawableHelper.getTextPaint().drawableState = this.getState();
            this.textDrawableHelper.updateTextPaintDrawState(this.context);
        }
        this.textDrawableHelper.getTextPaint().setTextAlign(object2);
        int n = Math.round(this.textDrawableHelper.getTextWidth(this.getText().toString()));
        int n2 = Math.round(this.rectF.width());
        int n3 = 0;
        n = n > n2 ? 1 : 0;
        if (n != 0) {
            n3 = canvas.save();
            canvas.clipRect(this.rectF);
        }
        object2 = this.text;
        object = object2;
        if (n != 0) {
            object = object2;
            if (this.truncateAt != null) {
                object = TextUtils.ellipsize((CharSequence)object2, (TextPaint)this.textDrawableHelper.getTextPaint(), (float)this.rectF.width(), (TextUtils.TruncateAt)this.truncateAt);
            }
        }
        canvas.drawText((CharSequence)object, 0, object.length(), this.pointF.x, this.pointF.y, (Paint)this.textDrawableHelper.getTextPaint());
        if (n == 0) return;
        canvas.restoreToCount(n3);
    }

    private ColorFilter getTintColorFilter() {
        ColorFilter colorFilter = this.colorFilter;
        if (colorFilter == null) return this.tintFilter;
        return colorFilter;
    }

    private static boolean hasState(int[] arrn, int n) {
        if (arrn == null) {
            return false;
        }
        int n2 = arrn.length;
        int n3 = 0;
        while (n3 < n2) {
            if (arrn[n3] == n) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    private static boolean isStateful(ColorStateList colorStateList) {
        if (colorStateList == null) return false;
        if (!colorStateList.isStateful()) return false;
        return true;
    }

    private static boolean isStateful(Drawable drawable2) {
        if (drawable2 == null) return false;
        if (!drawable2.isStateful()) return false;
        return true;
    }

    private static boolean isStateful(TextAppearance textAppearance) {
        if (textAppearance == null) return false;
        if (textAppearance.textColor == null) return false;
        if (!textAppearance.textColor.isStateful()) return false;
        return true;
    }

    private void loadFromAttributes(AttributeSet attributeSet, int n, int n2) {
        TypedArray typedArray = ThemeEnforcement.obtainStyledAttributes(this.context, attributeSet, R.styleable.Chip, n, n2, new int[0]);
        this.isShapeThemingEnabled = typedArray.hasValue(R.styleable.Chip_shapeAppearance);
        this.setChipSurfaceColor(MaterialResources.getColorStateList(this.context, typedArray, R.styleable.Chip_chipSurfaceColor));
        this.setChipBackgroundColor(MaterialResources.getColorStateList(this.context, typedArray, R.styleable.Chip_chipBackgroundColor));
        this.setChipMinHeight(typedArray.getDimension(R.styleable.Chip_chipMinHeight, 0.0f));
        if (typedArray.hasValue(R.styleable.Chip_chipCornerRadius)) {
            this.setChipCornerRadius(typedArray.getDimension(R.styleable.Chip_chipCornerRadius, 0.0f));
        }
        this.setChipStrokeColor(MaterialResources.getColorStateList(this.context, typedArray, R.styleable.Chip_chipStrokeColor));
        this.setChipStrokeWidth(typedArray.getDimension(R.styleable.Chip_chipStrokeWidth, 0.0f));
        this.setRippleColor(MaterialResources.getColorStateList(this.context, typedArray, R.styleable.Chip_rippleColor));
        this.setText(typedArray.getText(R.styleable.Chip_android_text));
        this.setTextAppearance(MaterialResources.getTextAppearance(this.context, typedArray, R.styleable.Chip_android_textAppearance));
        n = typedArray.getInt(R.styleable.Chip_android_ellipsize, 0);
        if (n != 1) {
            if (n != 2) {
                if (n == 3) {
                    this.setEllipsize(TextUtils.TruncateAt.END);
                }
            } else {
                this.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            }
        } else {
            this.setEllipsize(TextUtils.TruncateAt.START);
        }
        this.setChipIconVisible(typedArray.getBoolean(R.styleable.Chip_chipIconVisible, false));
        if (attributeSet != null && attributeSet.getAttributeValue(NAMESPACE_APP, "chipIconEnabled") != null && attributeSet.getAttributeValue(NAMESPACE_APP, "chipIconVisible") == null) {
            this.setChipIconVisible(typedArray.getBoolean(R.styleable.Chip_chipIconEnabled, false));
        }
        this.setChipIcon(MaterialResources.getDrawable(this.context, typedArray, R.styleable.Chip_chipIcon));
        if (typedArray.hasValue(R.styleable.Chip_chipIconTint)) {
            this.setChipIconTint(MaterialResources.getColorStateList(this.context, typedArray, R.styleable.Chip_chipIconTint));
        }
        this.setChipIconSize(typedArray.getDimension(R.styleable.Chip_chipIconSize, 0.0f));
        this.setCloseIconVisible(typedArray.getBoolean(R.styleable.Chip_closeIconVisible, false));
        if (attributeSet != null && attributeSet.getAttributeValue(NAMESPACE_APP, "closeIconEnabled") != null && attributeSet.getAttributeValue(NAMESPACE_APP, "closeIconVisible") == null) {
            this.setCloseIconVisible(typedArray.getBoolean(R.styleable.Chip_closeIconEnabled, false));
        }
        this.setCloseIcon(MaterialResources.getDrawable(this.context, typedArray, R.styleable.Chip_closeIcon));
        this.setCloseIconTint(MaterialResources.getColorStateList(this.context, typedArray, R.styleable.Chip_closeIconTint));
        this.setCloseIconSize(typedArray.getDimension(R.styleable.Chip_closeIconSize, 0.0f));
        this.setCheckable(typedArray.getBoolean(R.styleable.Chip_android_checkable, false));
        this.setCheckedIconVisible(typedArray.getBoolean(R.styleable.Chip_checkedIconVisible, false));
        if (attributeSet != null && attributeSet.getAttributeValue(NAMESPACE_APP, "checkedIconEnabled") != null && attributeSet.getAttributeValue(NAMESPACE_APP, "checkedIconVisible") == null) {
            this.setCheckedIconVisible(typedArray.getBoolean(R.styleable.Chip_checkedIconEnabled, false));
        }
        this.setCheckedIcon(MaterialResources.getDrawable(this.context, typedArray, R.styleable.Chip_checkedIcon));
        if (typedArray.hasValue(R.styleable.Chip_checkedIconTint)) {
            this.setCheckedIconTint(MaterialResources.getColorStateList(this.context, typedArray, R.styleable.Chip_checkedIconTint));
        }
        this.setShowMotionSpec(MotionSpec.createFromAttribute(this.context, typedArray, R.styleable.Chip_showMotionSpec));
        this.setHideMotionSpec(MotionSpec.createFromAttribute(this.context, typedArray, R.styleable.Chip_hideMotionSpec));
        this.setChipStartPadding(typedArray.getDimension(R.styleable.Chip_chipStartPadding, 0.0f));
        this.setIconStartPadding(typedArray.getDimension(R.styleable.Chip_iconStartPadding, 0.0f));
        this.setIconEndPadding(typedArray.getDimension(R.styleable.Chip_iconEndPadding, 0.0f));
        this.setTextStartPadding(typedArray.getDimension(R.styleable.Chip_textStartPadding, 0.0f));
        this.setTextEndPadding(typedArray.getDimension(R.styleable.Chip_textEndPadding, 0.0f));
        this.setCloseIconStartPadding(typedArray.getDimension(R.styleable.Chip_closeIconStartPadding, 0.0f));
        this.setCloseIconEndPadding(typedArray.getDimension(R.styleable.Chip_closeIconEndPadding, 0.0f));
        this.setChipEndPadding(typedArray.getDimension(R.styleable.Chip_chipEndPadding, 0.0f));
        this.setMaxWidth(typedArray.getDimensionPixelSize(R.styleable.Chip_android_maxWidth, Integer.MAX_VALUE));
        typedArray.recycle();
    }

    /*
     * Unable to fully structure code
     */
    private boolean onStateChange(int[] var1_1, int[] var2_2) {
        var3_3 = super.onStateChange(var1_1);
        var4_4 = this.chipSurfaceColor;
        var5_5 = var4_4 != null ? var4_4.getColorForState(var1_1, this.currentChipSurfaceColor) : 0;
        var6_6 = this.currentChipSurfaceColor;
        var7_7 = true;
        if (var6_6 != var5_5) {
            this.currentChipSurfaceColor = var5_5;
            var3_3 = true;
        }
        if (this.currentChipBackgroundColor != (var6_6 = (var4_4 = this.chipBackgroundColor) != null ? var4_4.getColorForState(var1_1, this.currentChipBackgroundColor) : 0)) {
            this.currentChipBackgroundColor = var6_6;
            var3_3 = true;
        }
        var5_5 = this.currentCompositeSurfaceBackgroundColor != (var8_8 = MaterialColors.layer(var5_5, var6_6)) ? 1 : 0;
        var6_6 = this.getFillColor() == null ? 1 : 0;
        if ((var5_5 | var6_6) != 0) {
            this.currentCompositeSurfaceBackgroundColor = var8_8;
            this.setFillColor(ColorStateList.valueOf((int)var8_8));
            var3_3 = true;
        }
        if (this.currentChipStrokeColor != (var5_5 = (var4_4 = this.chipStrokeColor) != null ? var4_4.getColorForState(var1_1, this.currentChipStrokeColor) : 0)) {
            this.currentChipStrokeColor = var5_5;
            var3_3 = true;
        }
        var5_5 = this.compatRippleColor != null && RippleUtils.shouldDrawRippleCompat(var1_1) != false ? this.compatRippleColor.getColorForState(var1_1, this.currentCompatRippleColor) : 0;
        var9_9 = var3_3;
        if (this.currentCompatRippleColor != var5_5) {
            this.currentCompatRippleColor = var5_5;
            var9_9 = var3_3;
            if (this.useCompatRipple) {
                var9_9 = true;
            }
        }
        var5_5 = this.textDrawableHelper.getTextAppearance() != null && this.textDrawableHelper.getTextAppearance().textColor != null ? this.textDrawableHelper.getTextAppearance().textColor.getColorForState(var1_1, this.currentTextColor) : 0;
        var3_3 = var9_9;
        if (this.currentTextColor != var5_5) {
            this.currentTextColor = var5_5;
            var3_3 = true;
        }
        var10_10 = ChipDrawable.hasState(this.getState(), 16842912) != false && this.checkable != false;
        var9_9 = var3_3;
        if (this.currentChecked == var10_10) ** GOTO lbl45
        var9_9 = var3_3;
        if (this.checkedIcon == null) ** GOTO lbl45
        var11_11 = this.calculateChipIconWidth();
        this.currentChecked = var10_10;
        if (var11_11 != this.calculateChipIconWidth()) {
            var9_9 = true;
            var5_5 = 1;
        } else {
            var9_9 = true;
lbl45: // 3 sources:
            var5_5 = 0;
        }
        var4_4 = this.tint;
        var6_6 = var4_4 != null ? var4_4.getColorForState(var1_1, this.currentTint) : 0;
        if (this.currentTint != var6_6) {
            this.currentTint = var6_6;
            this.tintFilter = DrawableUtils.updateTintFilter(this, this.tint, this.tintMode);
            var9_9 = var7_7;
        }
        var3_3 = var9_9;
        if (ChipDrawable.isStateful(this.chipIcon)) {
            var3_3 = var9_9 | this.chipIcon.setState(var1_1);
        }
        var9_9 = var3_3;
        if (ChipDrawable.isStateful(this.checkedIcon)) {
            var9_9 = var3_3 | this.checkedIcon.setState(var1_1);
        }
        var3_3 = var9_9;
        if (ChipDrawable.isStateful(this.closeIcon)) {
            var4_4 = new int[var1_1.length + var2_2.length];
            System.arraycopy(var1_1, 0, var4_4, 0, var1_1.length);
            System.arraycopy(var2_2, 0, var4_4, var1_1.length, var2_2.length);
            var3_3 = var9_9 | this.closeIcon.setState(var4_4);
        }
        var9_9 = var3_3;
        if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
            var9_9 = var3_3;
            if (ChipDrawable.isStateful(this.closeIconRipple)) {
                var9_9 = var3_3 | this.closeIconRipple.setState(var2_2);
            }
        }
        if (var9_9) {
            this.invalidateSelf();
        }
        if (var5_5 == 0) return var9_9;
        this.onSizeChange();
        return var9_9;
    }

    private void setChipSurfaceColor(ColorStateList colorStateList) {
        if (this.chipSurfaceColor == colorStateList) return;
        this.chipSurfaceColor = colorStateList;
        this.onStateChange(this.getState());
    }

    private boolean showsCheckedIcon() {
        if (!this.checkedIconVisible) return false;
        if (this.checkedIcon == null) return false;
        if (!this.currentChecked) return false;
        return true;
    }

    private boolean showsChipIcon() {
        if (!this.chipIconVisible) return false;
        if (this.chipIcon == null) return false;
        return true;
    }

    private boolean showsCloseIcon() {
        if (!this.closeIconVisible) return false;
        if (this.closeIcon == null) return false;
        return true;
    }

    private void unapplyChildDrawable(Drawable drawable2) {
        if (drawable2 == null) return;
        drawable2.setCallback(null);
    }

    private void updateCompatRippleColor() {
        ColorStateList colorStateList = this.useCompatRipple ? RippleUtils.sanitizeRippleDrawableColor(this.rippleColor) : null;
        this.compatRippleColor = colorStateList;
    }

    private void updateFrameworkCloseIconRipple() {
        this.closeIconRipple = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.getRippleColor()), this.closeIcon, (Drawable)closeIconRippleMask);
    }

    float calculateChipIconWidth() {
        if (this.showsChipIcon()) return this.iconStartPadding + this.chipIconSize + this.iconEndPadding;
        if (!this.showsCheckedIcon()) return 0.0f;
        return this.iconStartPadding + this.chipIconSize + this.iconEndPadding;
    }

    float calculateCloseIconWidth() {
        if (!this.showsCloseIcon()) return 0.0f;
        return this.closeIconStartPadding + this.closeIconSize + this.closeIconEndPadding;
    }

    Paint.Align calculateTextOriginAndAlignment(Rect rect, PointF pointF) {
        pointF.set(0.0f, 0.0f);
        Paint.Align align = Paint.Align.LEFT;
        if (this.text == null) return align;
        float f = this.chipStartPadding + this.calculateChipIconWidth() + this.textStartPadding;
        if (DrawableCompat.getLayoutDirection(this) == 0) {
            pointF.x = (float)rect.left + f;
            align = Paint.Align.LEFT;
        } else {
            pointF.x = (float)rect.right - f;
            align = Paint.Align.RIGHT;
        }
        pointF.y = (float)rect.centerY() - this.calculateTextCenterFromBaseline();
        return align;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = this.getBounds();
        if (rect.isEmpty()) return;
        if (this.getAlpha() == 0) {
            return;
        }
        int n = 0;
        if (this.alpha < 255) {
            n = CanvasCompat.saveLayerAlpha(canvas, rect.left, rect.top, rect.right, rect.bottom, this.alpha);
        }
        this.drawChipSurface(canvas, rect);
        this.drawChipBackground(canvas, rect);
        if (this.isShapeThemingEnabled) {
            super.draw(canvas);
        }
        this.drawChipStroke(canvas, rect);
        this.drawCompatRipple(canvas, rect);
        this.drawChipIcon(canvas, rect);
        this.drawCheckedIcon(canvas, rect);
        if (this.shouldDrawText) {
            this.drawText(canvas, rect);
        }
        this.drawCloseIcon(canvas, rect);
        this.drawDebug(canvas, rect);
        if (this.alpha >= 255) return;
        canvas.restoreToCount(n);
    }

    public int getAlpha() {
        return this.alpha;
    }

    public Drawable getCheckedIcon() {
        return this.checkedIcon;
    }

    public ColorStateList getCheckedIconTint() {
        return this.checkedIconTint;
    }

    public ColorStateList getChipBackgroundColor() {
        return this.chipBackgroundColor;
    }

    public float getChipCornerRadius() {
        if (!this.isShapeThemingEnabled) return this.chipCornerRadius;
        return this.getTopLeftCornerResolvedSize();
    }

    public float getChipEndPadding() {
        return this.chipEndPadding;
    }

    public Drawable getChipIcon() {
        Object object = this.chipIcon;
        if (object == null) return null;
        return DrawableCompat.unwrap(object);
    }

    public float getChipIconSize() {
        return this.chipIconSize;
    }

    public ColorStateList getChipIconTint() {
        return this.chipIconTint;
    }

    public float getChipMinHeight() {
        return this.chipMinHeight;
    }

    public float getChipStartPadding() {
        return this.chipStartPadding;
    }

    public ColorStateList getChipStrokeColor() {
        return this.chipStrokeColor;
    }

    public float getChipStrokeWidth() {
        return this.chipStrokeWidth;
    }

    public void getChipTouchBounds(RectF rectF) {
        this.calculateChipTouchBounds(this.getBounds(), rectF);
    }

    public Drawable getCloseIcon() {
        Object object = this.closeIcon;
        if (object == null) return null;
        return DrawableCompat.unwrap(object);
    }

    public CharSequence getCloseIconContentDescription() {
        return this.closeIconContentDescription;
    }

    public float getCloseIconEndPadding() {
        return this.closeIconEndPadding;
    }

    public float getCloseIconSize() {
        return this.closeIconSize;
    }

    public float getCloseIconStartPadding() {
        return this.closeIconStartPadding;
    }

    public int[] getCloseIconState() {
        return this.closeIconStateSet;
    }

    public ColorStateList getCloseIconTint() {
        return this.closeIconTint;
    }

    public void getCloseIconTouchBounds(RectF rectF) {
        this.calculateCloseIconTouchBounds(this.getBounds(), rectF);
    }

    public ColorFilter getColorFilter() {
        return this.colorFilter;
    }

    public TextUtils.TruncateAt getEllipsize() {
        return this.truncateAt;
    }

    public MotionSpec getHideMotionSpec() {
        return this.hideMotionSpec;
    }

    public float getIconEndPadding() {
        return this.iconEndPadding;
    }

    public float getIconStartPadding() {
        return this.iconStartPadding;
    }

    public int getIntrinsicHeight() {
        return (int)this.chipMinHeight;
    }

    public int getIntrinsicWidth() {
        return Math.min(Math.round(this.chipStartPadding + this.calculateChipIconWidth() + this.textStartPadding + this.textDrawableHelper.getTextWidth(this.getText().toString()) + this.textEndPadding + this.calculateCloseIconWidth() + this.chipEndPadding), this.maxWidth);
    }

    public int getMaxWidth() {
        return this.maxWidth;
    }

    @Override
    public int getOpacity() {
        return -3;
    }

    @Override
    public void getOutline(Outline outline) {
        if (this.isShapeThemingEnabled) {
            super.getOutline(outline);
            return;
        }
        Rect rect = this.getBounds();
        if (!rect.isEmpty()) {
            outline.setRoundRect(rect, this.chipCornerRadius);
        } else {
            outline.setRoundRect(0, 0, this.getIntrinsicWidth(), this.getIntrinsicHeight(), this.chipCornerRadius);
        }
        outline.setAlpha((float)this.getAlpha() / 255.0f);
    }

    public ColorStateList getRippleColor() {
        return this.rippleColor;
    }

    public MotionSpec getShowMotionSpec() {
        return this.showMotionSpec;
    }

    public CharSequence getText() {
        return this.text;
    }

    public TextAppearance getTextAppearance() {
        return this.textDrawableHelper.getTextAppearance();
    }

    public float getTextEndPadding() {
        return this.textEndPadding;
    }

    public float getTextStartPadding() {
        return this.textStartPadding;
    }

    public boolean getUseCompatRipple() {
        return this.useCompatRipple;
    }

    public void invalidateDrawable(Drawable drawable2) {
        drawable2 = this.getCallback();
        if (drawable2 == null) return;
        drawable2.invalidateDrawable((Drawable)this);
    }

    public boolean isCheckable() {
        return this.checkable;
    }

    @Deprecated
    public boolean isCheckedIconEnabled() {
        return this.isCheckedIconVisible();
    }

    public boolean isCheckedIconVisible() {
        return this.checkedIconVisible;
    }

    @Deprecated
    public boolean isChipIconEnabled() {
        return this.isChipIconVisible();
    }

    public boolean isChipIconVisible() {
        return this.chipIconVisible;
    }

    @Deprecated
    public boolean isCloseIconEnabled() {
        return this.isCloseIconVisible();
    }

    public boolean isCloseIconStateful() {
        return ChipDrawable.isStateful(this.closeIcon);
    }

    public boolean isCloseIconVisible() {
        return this.closeIconVisible;
    }

    boolean isShapeThemingEnabled() {
        return this.isShapeThemingEnabled;
    }

    @Override
    public boolean isStateful() {
        if (ChipDrawable.isStateful(this.chipSurfaceColor)) return true;
        if (ChipDrawable.isStateful(this.chipBackgroundColor)) return true;
        if (ChipDrawable.isStateful(this.chipStrokeColor)) return true;
        if (this.useCompatRipple) {
            if (ChipDrawable.isStateful(this.compatRippleColor)) return true;
        }
        if (ChipDrawable.isStateful(this.textDrawableHelper.getTextAppearance())) return true;
        if (this.canShowCheckedIcon()) return true;
        if (ChipDrawable.isStateful(this.chipIcon)) return true;
        if (ChipDrawable.isStateful(this.checkedIcon)) return true;
        if (ChipDrawable.isStateful(this.tint)) return true;
        return false;
    }

    public boolean onLayoutDirectionChanged(int n) {
        boolean bl;
        boolean bl2 = bl = super.onLayoutDirectionChanged(n);
        if (this.showsChipIcon()) {
            bl2 = bl | DrawableCompat.setLayoutDirection(this.chipIcon, n);
        }
        bl = bl2;
        if (this.showsCheckedIcon()) {
            bl = bl2 | DrawableCompat.setLayoutDirection(this.checkedIcon, n);
        }
        bl2 = bl;
        if (this.showsCloseIcon()) {
            bl2 = bl | DrawableCompat.setLayoutDirection(this.closeIcon, n);
        }
        if (!bl2) return true;
        this.invalidateSelf();
        return true;
    }

    protected boolean onLevelChange(int n) {
        boolean bl;
        boolean bl2 = bl = super.onLevelChange(n);
        if (this.showsChipIcon()) {
            bl2 = bl | this.chipIcon.setLevel(n);
        }
        bl = bl2;
        if (this.showsCheckedIcon()) {
            bl = bl2 | this.checkedIcon.setLevel(n);
        }
        bl2 = bl;
        if (this.showsCloseIcon()) {
            bl2 = bl | this.closeIcon.setLevel(n);
        }
        if (!bl2) return bl2;
        this.invalidateSelf();
        return bl2;
    }

    protected void onSizeChange() {
        Delegate delegate = (Delegate)this.delegate.get();
        if (delegate == null) return;
        delegate.onChipDrawableSizeChange();
    }

    @Override
    public boolean onStateChange(int[] arrn) {
        if (!this.isShapeThemingEnabled) return this.onStateChange(arrn, this.getCloseIconState());
        super.onStateChange(arrn);
        return this.onStateChange(arrn, this.getCloseIconState());
    }

    @Override
    public void onTextSizeChange() {
        this.onSizeChange();
        this.invalidateSelf();
    }

    public void scheduleDrawable(Drawable drawable2, Runnable runnable2, long l) {
        drawable2 = this.getCallback();
        if (drawable2 == null) return;
        drawable2.scheduleDrawable((Drawable)this, runnable2, l);
    }

    @Override
    public void setAlpha(int n) {
        if (this.alpha == n) return;
        this.alpha = n;
        this.invalidateSelf();
    }

    public void setCheckable(boolean bl) {
        if (this.checkable == bl) return;
        this.checkable = bl;
        float f = this.calculateChipIconWidth();
        if (!bl && this.currentChecked) {
            this.currentChecked = false;
        }
        float f2 = this.calculateChipIconWidth();
        this.invalidateSelf();
        if (f == f2) return;
        this.onSizeChange();
    }

    public void setCheckableResource(int n) {
        this.setCheckable(this.context.getResources().getBoolean(n));
    }

    public void setCheckedIcon(Drawable drawable2) {
        if (this.checkedIcon == drawable2) return;
        float f = this.calculateChipIconWidth();
        this.checkedIcon = drawable2;
        float f2 = this.calculateChipIconWidth();
        this.unapplyChildDrawable(this.checkedIcon);
        this.applyChildDrawable(this.checkedIcon);
        this.invalidateSelf();
        if (f == f2) return;
        this.onSizeChange();
    }

    @Deprecated
    public void setCheckedIconEnabled(boolean bl) {
        this.setCheckedIconVisible(bl);
    }

    @Deprecated
    public void setCheckedIconEnabledResource(int n) {
        this.setCheckedIconVisible(this.context.getResources().getBoolean(n));
    }

    public void setCheckedIconResource(int n) {
        this.setCheckedIcon(AppCompatResources.getDrawable(this.context, n));
    }

    public void setCheckedIconTint(ColorStateList colorStateList) {
        if (this.checkedIconTint == colorStateList) return;
        this.checkedIconTint = colorStateList;
        if (this.canShowCheckedIcon()) {
            DrawableCompat.setTintList(this.checkedIcon, colorStateList);
        }
        this.onStateChange(this.getState());
    }

    public void setCheckedIconTintResource(int n) {
        this.setCheckedIconTint(AppCompatResources.getColorStateList(this.context, n));
    }

    public void setCheckedIconVisible(int n) {
        this.setCheckedIconVisible(this.context.getResources().getBoolean(n));
    }

    public void setCheckedIconVisible(boolean bl) {
        if (this.checkedIconVisible == bl) return;
        boolean bl2 = this.showsCheckedIcon();
        this.checkedIconVisible = bl;
        bl = this.showsCheckedIcon();
        boolean bl3 = bl2 != bl;
        if (!bl3) return;
        if (bl) {
            this.applyChildDrawable(this.checkedIcon);
        } else {
            this.unapplyChildDrawable(this.checkedIcon);
        }
        this.invalidateSelf();
        this.onSizeChange();
    }

    public void setChipBackgroundColor(ColorStateList colorStateList) {
        if (this.chipBackgroundColor == colorStateList) return;
        this.chipBackgroundColor = colorStateList;
        this.onStateChange(this.getState());
    }

    public void setChipBackgroundColorResource(int n) {
        this.setChipBackgroundColor(AppCompatResources.getColorStateList(this.context, n));
    }

    @Deprecated
    public void setChipCornerRadius(float f) {
        if (this.chipCornerRadius == f) return;
        this.chipCornerRadius = f;
        this.setShapeAppearanceModel(this.getShapeAppearanceModel().withCornerSize(f));
    }

    @Deprecated
    public void setChipCornerRadiusResource(int n) {
        this.setChipCornerRadius(this.context.getResources().getDimension(n));
    }

    public void setChipEndPadding(float f) {
        if (this.chipEndPadding == f) return;
        this.chipEndPadding = f;
        this.invalidateSelf();
        this.onSizeChange();
    }

    public void setChipEndPaddingResource(int n) {
        this.setChipEndPadding(this.context.getResources().getDimension(n));
    }

    public void setChipIcon(Drawable object) {
        Drawable drawable2 = this.getChipIcon();
        if (drawable2 == object) return;
        float f = this.calculateChipIconWidth();
        object = object != null ? DrawableCompat.wrap(object).mutate() : null;
        this.chipIcon = object;
        float f2 = this.calculateChipIconWidth();
        this.unapplyChildDrawable(drawable2);
        if (this.showsChipIcon()) {
            this.applyChildDrawable(this.chipIcon);
        }
        this.invalidateSelf();
        if (f == f2) return;
        this.onSizeChange();
    }

    @Deprecated
    public void setChipIconEnabled(boolean bl) {
        this.setChipIconVisible(bl);
    }

    @Deprecated
    public void setChipIconEnabledResource(int n) {
        this.setChipIconVisible(n);
    }

    public void setChipIconResource(int n) {
        this.setChipIcon(AppCompatResources.getDrawable(this.context, n));
    }

    public void setChipIconSize(float f) {
        if (this.chipIconSize == f) return;
        float f2 = this.calculateChipIconWidth();
        this.chipIconSize = f;
        f = this.calculateChipIconWidth();
        this.invalidateSelf();
        if (f2 == f) return;
        this.onSizeChange();
    }

    public void setChipIconSizeResource(int n) {
        this.setChipIconSize(this.context.getResources().getDimension(n));
    }

    public void setChipIconTint(ColorStateList colorStateList) {
        this.hasChipIconTint = true;
        if (this.chipIconTint == colorStateList) return;
        this.chipIconTint = colorStateList;
        if (this.showsChipIcon()) {
            DrawableCompat.setTintList(this.chipIcon, colorStateList);
        }
        this.onStateChange(this.getState());
    }

    public void setChipIconTintResource(int n) {
        this.setChipIconTint(AppCompatResources.getColorStateList(this.context, n));
    }

    public void setChipIconVisible(int n) {
        this.setChipIconVisible(this.context.getResources().getBoolean(n));
    }

    public void setChipIconVisible(boolean bl) {
        if (this.chipIconVisible == bl) return;
        boolean bl2 = this.showsChipIcon();
        this.chipIconVisible = bl;
        bl = this.showsChipIcon();
        boolean bl3 = bl2 != bl;
        if (!bl3) return;
        if (bl) {
            this.applyChildDrawable(this.chipIcon);
        } else {
            this.unapplyChildDrawable(this.chipIcon);
        }
        this.invalidateSelf();
        this.onSizeChange();
    }

    public void setChipMinHeight(float f) {
        if (this.chipMinHeight == f) return;
        this.chipMinHeight = f;
        this.invalidateSelf();
        this.onSizeChange();
    }

    public void setChipMinHeightResource(int n) {
        this.setChipMinHeight(this.context.getResources().getDimension(n));
    }

    public void setChipStartPadding(float f) {
        if (this.chipStartPadding == f) return;
        this.chipStartPadding = f;
        this.invalidateSelf();
        this.onSizeChange();
    }

    public void setChipStartPaddingResource(int n) {
        this.setChipStartPadding(this.context.getResources().getDimension(n));
    }

    public void setChipStrokeColor(ColorStateList colorStateList) {
        if (this.chipStrokeColor == colorStateList) return;
        this.chipStrokeColor = colorStateList;
        if (this.isShapeThemingEnabled) {
            this.setStrokeColor(colorStateList);
        }
        this.onStateChange(this.getState());
    }

    public void setChipStrokeColorResource(int n) {
        this.setChipStrokeColor(AppCompatResources.getColorStateList(this.context, n));
    }

    public void setChipStrokeWidth(float f) {
        if (this.chipStrokeWidth == f) return;
        this.chipStrokeWidth = f;
        this.chipPaint.setStrokeWidth(f);
        if (this.isShapeThemingEnabled) {
            super.setStrokeWidth(f);
        }
        this.invalidateSelf();
    }

    public void setChipStrokeWidthResource(int n) {
        this.setChipStrokeWidth(this.context.getResources().getDimension(n));
    }

    public void setCloseIcon(Drawable object) {
        Drawable drawable2 = this.getCloseIcon();
        if (drawable2 == object) return;
        float f = this.calculateCloseIconWidth();
        object = object != null ? DrawableCompat.wrap(object).mutate() : null;
        this.closeIcon = object;
        if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
            this.updateFrameworkCloseIconRipple();
        }
        float f2 = this.calculateCloseIconWidth();
        this.unapplyChildDrawable(drawable2);
        if (this.showsCloseIcon()) {
            this.applyChildDrawable(this.closeIcon);
        }
        this.invalidateSelf();
        if (f == f2) return;
        this.onSizeChange();
    }

    public void setCloseIconContentDescription(CharSequence charSequence) {
        if (this.closeIconContentDescription == charSequence) return;
        this.closeIconContentDescription = BidiFormatter.getInstance().unicodeWrap(charSequence);
        this.invalidateSelf();
    }

    @Deprecated
    public void setCloseIconEnabled(boolean bl) {
        this.setCloseIconVisible(bl);
    }

    @Deprecated
    public void setCloseIconEnabledResource(int n) {
        this.setCloseIconVisible(n);
    }

    public void setCloseIconEndPadding(float f) {
        if (this.closeIconEndPadding == f) return;
        this.closeIconEndPadding = f;
        this.invalidateSelf();
        if (!this.showsCloseIcon()) return;
        this.onSizeChange();
    }

    public void setCloseIconEndPaddingResource(int n) {
        this.setCloseIconEndPadding(this.context.getResources().getDimension(n));
    }

    public void setCloseIconResource(int n) {
        this.setCloseIcon(AppCompatResources.getDrawable(this.context, n));
    }

    public void setCloseIconSize(float f) {
        if (this.closeIconSize == f) return;
        this.closeIconSize = f;
        this.invalidateSelf();
        if (!this.showsCloseIcon()) return;
        this.onSizeChange();
    }

    public void setCloseIconSizeResource(int n) {
        this.setCloseIconSize(this.context.getResources().getDimension(n));
    }

    public void setCloseIconStartPadding(float f) {
        if (this.closeIconStartPadding == f) return;
        this.closeIconStartPadding = f;
        this.invalidateSelf();
        if (!this.showsCloseIcon()) return;
        this.onSizeChange();
    }

    public void setCloseIconStartPaddingResource(int n) {
        this.setCloseIconStartPadding(this.context.getResources().getDimension(n));
    }

    public boolean setCloseIconState(int[] arrn) {
        if (Arrays.equals(this.closeIconStateSet, arrn)) return false;
        this.closeIconStateSet = arrn;
        if (!this.showsCloseIcon()) return false;
        return this.onStateChange(this.getState(), arrn);
    }

    public void setCloseIconTint(ColorStateList colorStateList) {
        if (this.closeIconTint == colorStateList) return;
        this.closeIconTint = colorStateList;
        if (this.showsCloseIcon()) {
            DrawableCompat.setTintList(this.closeIcon, colorStateList);
        }
        this.onStateChange(this.getState());
    }

    public void setCloseIconTintResource(int n) {
        this.setCloseIconTint(AppCompatResources.getColorStateList(this.context, n));
    }

    public void setCloseIconVisible(int n) {
        this.setCloseIconVisible(this.context.getResources().getBoolean(n));
    }

    public void setCloseIconVisible(boolean bl) {
        if (this.closeIconVisible == bl) return;
        boolean bl2 = this.showsCloseIcon();
        this.closeIconVisible = bl;
        bl = this.showsCloseIcon();
        boolean bl3 = bl2 != bl;
        if (!bl3) return;
        if (bl) {
            this.applyChildDrawable(this.closeIcon);
        } else {
            this.unapplyChildDrawable(this.closeIcon);
        }
        this.invalidateSelf();
        this.onSizeChange();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        if (this.colorFilter == colorFilter) return;
        this.colorFilter = colorFilter;
        this.invalidateSelf();
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = new WeakReference<Delegate>(delegate);
    }

    public void setEllipsize(TextUtils.TruncateAt truncateAt) {
        this.truncateAt = truncateAt;
    }

    public void setHideMotionSpec(MotionSpec motionSpec) {
        this.hideMotionSpec = motionSpec;
    }

    public void setHideMotionSpecResource(int n) {
        this.setHideMotionSpec(MotionSpec.createFromResource(this.context, n));
    }

    public void setIconEndPadding(float f) {
        if (this.iconEndPadding == f) return;
        float f2 = this.calculateChipIconWidth();
        this.iconEndPadding = f;
        f = this.calculateChipIconWidth();
        this.invalidateSelf();
        if (f2 == f) return;
        this.onSizeChange();
    }

    public void setIconEndPaddingResource(int n) {
        this.setIconEndPadding(this.context.getResources().getDimension(n));
    }

    public void setIconStartPadding(float f) {
        if (this.iconStartPadding == f) return;
        float f2 = this.calculateChipIconWidth();
        this.iconStartPadding = f;
        f = this.calculateChipIconWidth();
        this.invalidateSelf();
        if (f2 == f) return;
        this.onSizeChange();
    }

    public void setIconStartPaddingResource(int n) {
        this.setIconStartPadding(this.context.getResources().getDimension(n));
    }

    public void setMaxWidth(int n) {
        this.maxWidth = n;
    }

    public void setRippleColor(ColorStateList colorStateList) {
        if (this.rippleColor == colorStateList) return;
        this.rippleColor = colorStateList;
        this.updateCompatRippleColor();
        this.onStateChange(this.getState());
    }

    public void setRippleColorResource(int n) {
        this.setRippleColor(AppCompatResources.getColorStateList(this.context, n));
    }

    void setShouldDrawText(boolean bl) {
        this.shouldDrawText = bl;
    }

    public void setShowMotionSpec(MotionSpec motionSpec) {
        this.showMotionSpec = motionSpec;
    }

    public void setShowMotionSpecResource(int n) {
        this.setShowMotionSpec(MotionSpec.createFromResource(this.context, n));
    }

    public void setText(CharSequence charSequence) {
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            charSequence2 = "";
        }
        if (TextUtils.equals((CharSequence)this.text, (CharSequence)charSequence2)) return;
        this.text = charSequence2;
        this.textDrawableHelper.setTextWidthDirty(true);
        this.invalidateSelf();
        this.onSizeChange();
    }

    public void setTextAppearance(TextAppearance textAppearance) {
        this.textDrawableHelper.setTextAppearance(textAppearance, this.context);
    }

    public void setTextAppearanceResource(int n) {
        this.setTextAppearance(new TextAppearance(this.context, n));
    }

    public void setTextEndPadding(float f) {
        if (this.textEndPadding == f) return;
        this.textEndPadding = f;
        this.invalidateSelf();
        this.onSizeChange();
    }

    public void setTextEndPaddingResource(int n) {
        this.setTextEndPadding(this.context.getResources().getDimension(n));
    }

    public void setTextResource(int n) {
        this.setText(this.context.getResources().getString(n));
    }

    public void setTextStartPadding(float f) {
        if (this.textStartPadding == f) return;
        this.textStartPadding = f;
        this.invalidateSelf();
        this.onSizeChange();
    }

    public void setTextStartPaddingResource(int n) {
        this.setTextStartPadding(this.context.getResources().getDimension(n));
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        if (this.tint == colorStateList) return;
        this.tint = colorStateList;
        this.onStateChange(this.getState());
    }

    @Override
    public void setTintMode(PorterDuff.Mode mode) {
        if (this.tintMode == mode) return;
        this.tintMode = mode;
        this.tintFilter = DrawableUtils.updateTintFilter(this, this.tint, mode);
        this.invalidateSelf();
    }

    public void setUseCompatRipple(boolean bl) {
        if (this.useCompatRipple == bl) return;
        this.useCompatRipple = bl;
        this.updateCompatRippleColor();
        this.onStateChange(this.getState());
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        boolean bl3;
        boolean bl4 = bl3 = super.setVisible(bl, bl2);
        if (this.showsChipIcon()) {
            bl4 = bl3 | this.chipIcon.setVisible(bl, bl2);
        }
        bl3 = bl4;
        if (this.showsCheckedIcon()) {
            bl3 = bl4 | this.checkedIcon.setVisible(bl, bl2);
        }
        bl4 = bl3;
        if (this.showsCloseIcon()) {
            bl4 = bl3 | this.closeIcon.setVisible(bl, bl2);
        }
        if (!bl4) return bl4;
        this.invalidateSelf();
        return bl4;
    }

    boolean shouldDrawText() {
        return this.shouldDrawText;
    }

    public void unscheduleDrawable(Drawable drawable2, Runnable runnable2) {
        drawable2 = this.getCallback();
        if (drawable2 == null) return;
        drawable2.unscheduleDrawable((Drawable)this, runnable2);
    }

    public static interface Delegate {
        public void onChipDrawableSizeChange();
    }

}

