/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Layout
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.Log
 *  android.view.View
 */
package com.google.android.material.internal;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.core.math.MathUtils;
import androidx.core.text.TextDirectionHeuristicCompat;
import androidx.core.text.TextDirectionHeuristicsCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.StaticLayoutBuilderCompat;
import com.google.android.material.resources.CancelableFontCallback;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;

public final class CollapsingTextHelper {
    private static final boolean DEBUG_DRAW = false;
    private static final Paint DEBUG_DRAW_PAINT;
    private static final String ELLIPSIS_NORMAL = "\u2026";
    private static final String TAG = "CollapsingTextHelper";
    private static final boolean USE_SCALING_TEXTURE;
    private boolean boundsChanged;
    private final Rect collapsedBounds;
    private float collapsedDrawX;
    private float collapsedDrawY;
    private CancelableFontCallback collapsedFontCallback;
    private ColorStateList collapsedShadowColor;
    private float collapsedShadowDx;
    private float collapsedShadowDy;
    private float collapsedShadowRadius;
    private float collapsedTextBlend;
    private ColorStateList collapsedTextColor;
    private int collapsedTextGravity = 16;
    private float collapsedTextSize = 15.0f;
    private Typeface collapsedTypeface;
    private final RectF currentBounds;
    private float currentDrawX;
    private float currentDrawY;
    private float currentTextSize;
    private Typeface currentTypeface;
    private boolean drawTitle;
    private final Rect expandedBounds;
    private float expandedDrawX;
    private float expandedDrawY;
    private float expandedFirstLineDrawX;
    private CancelableFontCallback expandedFontCallback;
    private float expandedFraction;
    private ColorStateList expandedShadowColor;
    private float expandedShadowDx;
    private float expandedShadowDy;
    private float expandedShadowRadius;
    private float expandedTextBlend;
    private ColorStateList expandedTextColor;
    private int expandedTextGravity = 16;
    private float expandedTextSize = 15.0f;
    private Bitmap expandedTitleTexture;
    private Typeface expandedTypeface;
    private boolean isRtl;
    private int maxLines = 1;
    private TimeInterpolator positionInterpolator;
    private float scale;
    private int[] state;
    private CharSequence text;
    private StaticLayout textLayout;
    private final TextPaint textPaint;
    private TimeInterpolator textSizeInterpolator;
    private CharSequence textToDraw;
    private CharSequence textToDrawCollapsed;
    private Paint texturePaint;
    private final TextPaint tmpPaint;
    private boolean useTexture;
    private final View view;

    static {
        boolean bl = Build.VERSION.SDK_INT < 18;
        USE_SCALING_TEXTURE = bl;
        DEBUG_DRAW_PAINT = null;
        if (!false) return;
        throw new NullPointerException();
    }

    public CollapsingTextHelper(View view) {
        this.view = view;
        this.textPaint = new TextPaint(129);
        this.tmpPaint = new TextPaint((Paint)this.textPaint);
        this.collapsedBounds = new Rect();
        this.expandedBounds = new Rect();
        this.currentBounds = new RectF();
    }

    private static int blendColors(int n, int n2, float f) {
        float f2 = 1.0f - f;
        float f3 = Color.alpha((int)n);
        float f4 = Color.alpha((int)n2);
        float f5 = Color.red((int)n);
        float f6 = Color.red((int)n2);
        float f7 = Color.green((int)n);
        float f8 = Color.green((int)n2);
        float f9 = Color.blue((int)n);
        float f10 = Color.blue((int)n2);
        return Color.argb((int)((int)(f3 * f2 + f4 * f)), (int)((int)(f5 * f2 + f6 * f)), (int)((int)(f7 * f2 + f8 * f)), (int)((int)(f9 * f2 + f10 * f)));
    }

    private void calculateBaseOffsets() {
        float f;
        Object object;
        float f2 = this.currentTextSize;
        this.calculateUsingTextSize(this.collapsedTextSize);
        CharSequence charSequence = this.textToDraw;
        if (charSequence != null && (object = this.textLayout) != null) {
            this.textToDrawCollapsed = TextUtils.ellipsize((CharSequence)charSequence, (TextPaint)this.textPaint, (float)object.getWidth(), (TextUtils.TruncateAt)TextUtils.TruncateAt.END);
        }
        object = this.textToDrawCollapsed;
        float f3 = 0.0f;
        float f4 = object != null ? this.textPaint.measureText((CharSequence)object, 0, object.length()) : 0.0f;
        int n = GravityCompat.getAbsoluteGravity(this.collapsedTextGravity, (int)this.isRtl);
        int n2 = n & 112;
        if (n2 != 48) {
            if (n2 != 80) {
                f = (this.textPaint.descent() - this.textPaint.ascent()) / 2.0f;
                this.collapsedDrawY = (float)this.collapsedBounds.centerY() - f;
            } else {
                this.collapsedDrawY = (float)this.collapsedBounds.bottom + this.textPaint.ascent();
            }
        } else {
            this.collapsedDrawY = this.collapsedBounds.top;
        }
        this.collapsedDrawX = (n &= 8388615) != 1 ? (n != 5 ? (float)this.collapsedBounds.left : (float)this.collapsedBounds.right - f4) : (float)this.collapsedBounds.centerX() - f4 / 2.0f;
        this.calculateUsingTextSize(this.expandedTextSize);
        object = this.textLayout;
        f = object != null ? (float)object.getHeight() : 0.0f;
        object = this.textToDraw;
        f4 = object != null ? this.textPaint.measureText((CharSequence)object, 0, object.length()) : 0.0f;
        object = this.textLayout;
        float f5 = f4;
        if (object != null) {
            f5 = f4;
            if (this.maxLines > 1) {
                f5 = f4;
                if (!this.isRtl) {
                    f5 = object.getWidth();
                }
            }
        }
        object = this.textLayout;
        f4 = f3;
        if (object != null) {
            f4 = object.getLineLeft(0);
        }
        this.expandedFirstLineDrawX = f4;
        n2 = GravityCompat.getAbsoluteGravity(this.expandedTextGravity, (int)this.isRtl);
        n = n2 & 112;
        if (n != 48) {
            if (n != 80) {
                f4 = f / 2.0f;
                this.expandedDrawY = (float)this.expandedBounds.centerY() - f4;
            } else {
                this.expandedDrawY = (float)this.expandedBounds.bottom - f + this.textPaint.descent();
            }
        } else {
            this.expandedDrawY = this.expandedBounds.top;
        }
        n = n2 & 8388615;
        this.expandedDrawX = n != 1 ? (n != 5 ? (float)this.expandedBounds.left : (float)this.expandedBounds.right - f5) : (float)this.expandedBounds.centerX() - f5 / 2.0f;
        this.clearTexture();
        this.setInterpolatedTextSize(f2);
    }

    private void calculateCurrentOffsets() {
        this.calculateOffsets(this.expandedFraction);
    }

    private boolean calculateIsRtl(CharSequence charSequence) {
        TextDirectionHeuristicCompat textDirectionHeuristicCompat;
        if (this.isDefaultIsRtl()) {
            textDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL;
            return textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        }
        textDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
        return textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
    }

    private void calculateOffsets(float f) {
        this.interpolateBounds(f);
        this.currentDrawX = CollapsingTextHelper.lerp(this.expandedDrawX, this.collapsedDrawX, f, this.positionInterpolator);
        this.currentDrawY = CollapsingTextHelper.lerp(this.expandedDrawY, this.collapsedDrawY, f, this.positionInterpolator);
        this.setInterpolatedTextSize(CollapsingTextHelper.lerp(this.expandedTextSize, this.collapsedTextSize, f, this.textSizeInterpolator));
        this.setCollapsedTextBlend(1.0f - CollapsingTextHelper.lerp(0.0f, 1.0f, 1.0f - f, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        this.setExpandedTextBlend(CollapsingTextHelper.lerp(1.0f, 0.0f, f, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        if (this.collapsedTextColor != this.expandedTextColor) {
            this.textPaint.setColor(CollapsingTextHelper.blendColors(this.getCurrentExpandedTextColor(), this.getCurrentCollapsedTextColor(), f));
        } else {
            this.textPaint.setColor(this.getCurrentCollapsedTextColor());
        }
        this.textPaint.setShadowLayer(CollapsingTextHelper.lerp(this.expandedShadowRadius, this.collapsedShadowRadius, f, null), CollapsingTextHelper.lerp(this.expandedShadowDx, this.collapsedShadowDx, f, null), CollapsingTextHelper.lerp(this.expandedShadowDy, this.collapsedShadowDy, f, null), CollapsingTextHelper.blendColors(this.getCurrentColor(this.expandedShadowColor), this.getCurrentColor(this.collapsedShadowColor), f));
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private void calculateUsingTextSize(float f) {
        int n;
        float f2;
        Typeface typeface;
        if (this.text == null) {
            return;
        }
        float f3 = this.collapsedBounds.width();
        float f4 = this.expandedBounds.width();
        boolean bl = CollapsingTextHelper.isClose(f, this.collapsedTextSize);
        boolean bl2 = false;
        int n2 = 1;
        if (bl) {
            f2 = this.collapsedTextSize;
            this.scale = 1.0f;
            Typeface typeface2 = this.currentTypeface;
            typeface = this.collapsedTypeface;
            if (typeface2 != typeface) {
                this.currentTypeface = typeface;
                n = 1;
                f = f3;
            } else {
                n = 0;
                f = f3;
            }
        } else {
            f2 = this.expandedTextSize;
            typeface = this.currentTypeface;
            Typeface typeface3 = this.expandedTypeface;
            if (typeface != typeface3) {
                this.currentTypeface = typeface3;
                n = 1;
            } else {
                n = 0;
            }
            this.scale = CollapsingTextHelper.isClose(f, this.expandedTextSize) ? 1.0f : f / this.expandedTextSize;
            f = this.collapsedTextSize / this.expandedTextSize;
            f = f4 * f > f3 ? Math.min(f3 / f, f4) : f4;
        }
        int n3 = n;
        if (f > 0.0f) {
            n = this.currentTextSize == f2 && !this.boundsChanged && n == 0 ? 0 : 1;
            this.currentTextSize = f2;
            this.boundsChanged = false;
            n3 = n;
        }
        if (this.textToDraw != null) {
            if (n3 == 0) return;
        }
        this.textPaint.setTextSize(this.currentTextSize);
        this.textPaint.setTypeface(this.currentTypeface);
        typeface = this.textPaint;
        if (this.scale != 1.0f) {
            bl2 = true;
        }
        typeface.setLinearText(bl2);
        this.isRtl = this.calculateIsRtl(this.text);
        n = n2;
        if (this.shouldDrawMultiline()) {
            n = this.maxLines;
        }
        typeface = this.createStaticLayout(n, f, this.isRtl);
        this.textLayout = typeface;
        this.textToDraw = typeface.getText();
    }

    private void clearTexture() {
        Bitmap bitmap = this.expandedTitleTexture;
        if (bitmap == null) return;
        bitmap.recycle();
        this.expandedTitleTexture = null;
    }

    private StaticLayout createStaticLayout(int n, float f, boolean bl) {
        StaticLayout staticLayout;
        try {
            staticLayout = StaticLayoutBuilderCompat.obtain(this.text, this.textPaint, (int)f).setEllipsize(TextUtils.TruncateAt.END).setIsRtl(bl).setAlignment(Layout.Alignment.ALIGN_NORMAL).setIncludePad(false).setMaxLines(n).build();
            return Preconditions.checkNotNull(staticLayout);
        }
        catch (StaticLayoutBuilderCompat.StaticLayoutBuilderCompatException staticLayoutBuilderCompatException) {
            Log.e((String)TAG, (String)staticLayoutBuilderCompatException.getCause().getMessage(), (Throwable)staticLayoutBuilderCompatException);
            staticLayout = null;
        }
        return Preconditions.checkNotNull(staticLayout);
    }

    private void drawMultinlineTransition(Canvas canvas, float f, float f2) {
        int n = this.textPaint.getAlpha();
        canvas.translate(f, f2);
        Object object = this.textPaint;
        f2 = this.expandedTextBlend;
        f = n;
        object.setAlpha((int)(f2 * f));
        this.textLayout.draw(canvas);
        this.textPaint.setAlpha((int)(this.collapsedTextBlend * f));
        int n2 = this.textLayout.getLineBaseline(0);
        object = this.textToDrawCollapsed;
        int n3 = object.length();
        f = n2;
        canvas.drawText((CharSequence)object, 0, n3, 0.0f, f, (Paint)this.textPaint);
        String string2 = this.textToDrawCollapsed.toString().trim();
        object = string2;
        if (string2.endsWith(ELLIPSIS_NORMAL)) {
            object = string2.substring(0, string2.length() - 1);
        }
        this.textPaint.setAlpha(n);
        canvas.drawText((String)object, 0, Math.min(this.textLayout.getLineEnd(0), ((String)object).length()), 0.0f, f, (Paint)this.textPaint);
    }

    private void ensureExpandedTexture() {
        if (this.expandedTitleTexture != null) return;
        if (this.expandedBounds.isEmpty()) return;
        if (TextUtils.isEmpty((CharSequence)this.textToDraw)) {
            return;
        }
        this.calculateOffsets(0.0f);
        int n = this.textLayout.getWidth();
        int n2 = this.textLayout.getHeight();
        if (n <= 0) return;
        if (n2 <= 0) {
            return;
        }
        this.expandedTitleTexture = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.expandedTitleTexture);
        this.textLayout.draw(canvas);
        if (this.texturePaint != null) return;
        this.texturePaint = new Paint(3);
    }

    private float getCollapsedTextLeftBound(int n, int n2) {
        if (n2 == 17) return (float)n / 2.0f - this.calculateCollapsedTextWidth() / 2.0f;
        if ((n2 & 7) == 1) {
            return (float)n / 2.0f - this.calculateCollapsedTextWidth() / 2.0f;
        }
        if ((n2 & 8388613) != 8388613 && (n2 & 5) != 5) {
            if (!this.isRtl) return this.collapsedBounds.left;
            return (float)this.collapsedBounds.right - this.calculateCollapsedTextWidth();
        }
        if (!this.isRtl) return (float)this.collapsedBounds.right - this.calculateCollapsedTextWidth();
        return this.collapsedBounds.left;
    }

    private float getCollapsedTextRightBound(RectF rectF, int n, int n2) {
        if (n2 == 17) return (float)n / 2.0f + this.calculateCollapsedTextWidth() / 2.0f;
        if ((n2 & 7) == 1) {
            return (float)n / 2.0f + this.calculateCollapsedTextWidth() / 2.0f;
        }
        if ((n2 & 8388613) != 8388613 && (n2 & 5) != 5) {
            if (!this.isRtl) return rectF.left + this.calculateCollapsedTextWidth();
            return this.collapsedBounds.right;
        }
        if (!this.isRtl) return this.collapsedBounds.right;
        return rectF.left + this.calculateCollapsedTextWidth();
    }

    private int getCurrentColor(ColorStateList colorStateList) {
        if (colorStateList == null) {
            return 0;
        }
        int[] arrn = this.state;
        if (arrn == null) return colorStateList.getDefaultColor();
        return colorStateList.getColorForState(arrn, 0);
    }

    private int getCurrentExpandedTextColor() {
        return this.getCurrentColor(this.expandedTextColor);
    }

    private void getTextPaintCollapsed(TextPaint textPaint) {
        textPaint.setTextSize(this.collapsedTextSize);
        textPaint.setTypeface(this.collapsedTypeface);
    }

    private void getTextPaintExpanded(TextPaint textPaint) {
        textPaint.setTextSize(this.expandedTextSize);
        textPaint.setTypeface(this.expandedTypeface);
    }

    private void interpolateBounds(float f) {
        this.currentBounds.left = CollapsingTextHelper.lerp(this.expandedBounds.left, this.collapsedBounds.left, f, this.positionInterpolator);
        this.currentBounds.top = CollapsingTextHelper.lerp(this.expandedDrawY, this.collapsedDrawY, f, this.positionInterpolator);
        this.currentBounds.right = CollapsingTextHelper.lerp(this.expandedBounds.right, this.collapsedBounds.right, f, this.positionInterpolator);
        this.currentBounds.bottom = CollapsingTextHelper.lerp(this.expandedBounds.bottom, this.collapsedBounds.bottom, f, this.positionInterpolator);
    }

    private static boolean isClose(float f, float f2) {
        if (!(Math.abs(f - f2) < 0.001f)) return false;
        return true;
    }

    private boolean isDefaultIsRtl() {
        int n = ViewCompat.getLayoutDirection(this.view);
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    private static float lerp(float f, float f2, float f3, TimeInterpolator timeInterpolator) {
        float f4 = f3;
        if (timeInterpolator == null) return AnimationUtils.lerp(f, f2, f4);
        f4 = timeInterpolator.getInterpolation(f3);
        return AnimationUtils.lerp(f, f2, f4);
    }

    private static boolean rectEquals(Rect rect, int n, int n2, int n3, int n4) {
        if (rect.left != n) return false;
        if (rect.top != n2) return false;
        if (rect.right != n3) return false;
        if (rect.bottom != n4) return false;
        return true;
    }

    private void setCollapsedTextBlend(float f) {
        this.collapsedTextBlend = f;
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private boolean setCollapsedTypefaceInternal(Typeface typeface) {
        CancelableFontCallback cancelableFontCallback = this.collapsedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        if (this.collapsedTypeface == typeface) return false;
        this.collapsedTypeface = typeface;
        return true;
    }

    private void setExpandedTextBlend(float f) {
        this.expandedTextBlend = f;
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private boolean setExpandedTypefaceInternal(Typeface typeface) {
        CancelableFontCallback cancelableFontCallback = this.expandedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        if (this.expandedTypeface == typeface) return false;
        this.expandedTypeface = typeface;
        return true;
    }

    private void setInterpolatedTextSize(float f) {
        this.calculateUsingTextSize(f);
        boolean bl = USE_SCALING_TEXTURE && this.scale != 1.0f;
        this.useTexture = bl;
        if (bl) {
            this.ensureExpandedTexture();
        }
        ViewCompat.postInvalidateOnAnimation(this.view);
    }

    private boolean shouldDrawMultiline() {
        int n = this.maxLines;
        boolean bl = true;
        if (n <= 1) return false;
        if (this.isRtl) return false;
        if (this.useTexture) return false;
        return bl;
    }

    public float calculateCollapsedTextWidth() {
        if (this.text == null) {
            return 0.0f;
        }
        this.getTextPaintCollapsed(this.tmpPaint);
        TextPaint textPaint = this.tmpPaint;
        CharSequence charSequence = this.text;
        return textPaint.measureText(charSequence, 0, charSequence.length());
    }

    public void draw(Canvas canvas) {
        float f;
        int n = canvas.save();
        if (this.textToDraw == null) return;
        if (!this.drawTitle) return;
        float f2 = this.currentDrawX;
        StaticLayout staticLayout = this.textLayout;
        boolean bl = false;
        float f3 = staticLayout.getLineLeft(0);
        float f4 = this.expandedFirstLineDrawX;
        this.textPaint.setTextSize(this.currentTextSize);
        float f5 = this.currentDrawX;
        float f6 = this.currentDrawY;
        boolean bl2 = bl;
        if (this.useTexture) {
            bl2 = bl;
            if (this.expandedTitleTexture != null) {
                bl2 = true;
            }
        }
        if ((f = this.scale) != 1.0f) {
            canvas.scale(f, f, f5, f6);
        }
        if (bl2) {
            canvas.drawBitmap(this.expandedTitleTexture, f5, f6, this.texturePaint);
            canvas.restoreToCount(n);
            return;
        }
        if (this.shouldDrawMultiline()) {
            this.drawMultinlineTransition(canvas, f2 + f3 - f4 * 2.0f, f6);
        } else {
            canvas.translate(f5, f6);
            this.textLayout.draw(canvas);
        }
        canvas.restoreToCount(n);
    }

    public void getCollapsedTextActualBounds(RectF rectF, int n, int n2) {
        this.isRtl = this.calculateIsRtl(this.text);
        rectF.left = this.getCollapsedTextLeftBound(n, n2);
        rectF.top = this.collapsedBounds.top;
        rectF.right = this.getCollapsedTextRightBound(rectF, n, n2);
        rectF.bottom = (float)this.collapsedBounds.top + this.getCollapsedTextHeight();
    }

    public ColorStateList getCollapsedTextColor() {
        return this.collapsedTextColor;
    }

    public int getCollapsedTextGravity() {
        return this.collapsedTextGravity;
    }

    public float getCollapsedTextHeight() {
        this.getTextPaintCollapsed(this.tmpPaint);
        return -this.tmpPaint.ascent();
    }

    public float getCollapsedTextSize() {
        return this.collapsedTextSize;
    }

    public Typeface getCollapsedTypeface() {
        Typeface typeface = this.collapsedTypeface;
        if (typeface == null) return Typeface.DEFAULT;
        return typeface;
    }

    public int getCurrentCollapsedTextColor() {
        return this.getCurrentColor(this.collapsedTextColor);
    }

    public ColorStateList getExpandedTextColor() {
        return this.expandedTextColor;
    }

    public int getExpandedTextGravity() {
        return this.expandedTextGravity;
    }

    public float getExpandedTextHeight() {
        this.getTextPaintExpanded(this.tmpPaint);
        return -this.tmpPaint.ascent();
    }

    public float getExpandedTextSize() {
        return this.expandedTextSize;
    }

    public Typeface getExpandedTypeface() {
        Typeface typeface = this.expandedTypeface;
        if (typeface == null) return Typeface.DEFAULT;
        return typeface;
    }

    public float getExpansionFraction() {
        return this.expandedFraction;
    }

    public int getMaxLines() {
        return this.maxLines;
    }

    public CharSequence getText() {
        return this.text;
    }

    public final boolean isStateful() {
        ColorStateList colorStateList = this.collapsedTextColor;
        if (colorStateList != null) {
            if (colorStateList.isStateful()) return true;
        }
        if ((colorStateList = this.expandedTextColor) == null) return false;
        if (!colorStateList.isStateful()) return false;
        return true;
    }

    void onBoundsChanged() {
        boolean bl = this.collapsedBounds.width() > 0 && this.collapsedBounds.height() > 0 && this.expandedBounds.width() > 0 && this.expandedBounds.height() > 0;
        this.drawTitle = bl;
    }

    public void recalculate() {
        if (this.view.getHeight() <= 0) return;
        if (this.view.getWidth() <= 0) return;
        this.calculateBaseOffsets();
        this.calculateCurrentOffsets();
    }

    public void setCollapsedBounds(int n, int n2, int n3, int n4) {
        if (CollapsingTextHelper.rectEquals(this.collapsedBounds, n, n2, n3, n4)) return;
        this.collapsedBounds.set(n, n2, n3, n4);
        this.boundsChanged = true;
        this.onBoundsChanged();
    }

    public void setCollapsedBounds(Rect rect) {
        this.setCollapsedBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setCollapsedTextAppearance(int n) {
        TextAppearance textAppearance = new TextAppearance(this.view.getContext(), n);
        if (textAppearance.textColor != null) {
            this.collapsedTextColor = textAppearance.textColor;
        }
        if (textAppearance.textSize != 0.0f) {
            this.collapsedTextSize = textAppearance.textSize;
        }
        if (textAppearance.shadowColor != null) {
            this.collapsedShadowColor = textAppearance.shadowColor;
        }
        this.collapsedShadowDx = textAppearance.shadowDx;
        this.collapsedShadowDy = textAppearance.shadowDy;
        this.collapsedShadowRadius = textAppearance.shadowRadius;
        CancelableFontCallback cancelableFontCallback = this.collapsedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        this.collapsedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont(){

            @Override
            public void apply(Typeface typeface) {
                CollapsingTextHelper.this.setCollapsedTypeface(typeface);
            }
        }, textAppearance.getFallbackFont());
        textAppearance.getFontAsync(this.view.getContext(), this.collapsedFontCallback);
        this.recalculate();
    }

    public void setCollapsedTextColor(ColorStateList colorStateList) {
        if (this.collapsedTextColor == colorStateList) return;
        this.collapsedTextColor = colorStateList;
        this.recalculate();
    }

    public void setCollapsedTextGravity(int n) {
        if (this.collapsedTextGravity == n) return;
        this.collapsedTextGravity = n;
        this.recalculate();
    }

    public void setCollapsedTextSize(float f) {
        if (this.collapsedTextSize == f) return;
        this.collapsedTextSize = f;
        this.recalculate();
    }

    public void setCollapsedTypeface(Typeface typeface) {
        if (!this.setCollapsedTypefaceInternal(typeface)) return;
        this.recalculate();
    }

    public void setExpandedBounds(int n, int n2, int n3, int n4) {
        if (CollapsingTextHelper.rectEquals(this.expandedBounds, n, n2, n3, n4)) return;
        this.expandedBounds.set(n, n2, n3, n4);
        this.boundsChanged = true;
        this.onBoundsChanged();
    }

    public void setExpandedBounds(Rect rect) {
        this.setExpandedBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setExpandedTextAppearance(int n) {
        TextAppearance textAppearance = new TextAppearance(this.view.getContext(), n);
        if (textAppearance.textColor != null) {
            this.expandedTextColor = textAppearance.textColor;
        }
        if (textAppearance.textSize != 0.0f) {
            this.expandedTextSize = textAppearance.textSize;
        }
        if (textAppearance.shadowColor != null) {
            this.expandedShadowColor = textAppearance.shadowColor;
        }
        this.expandedShadowDx = textAppearance.shadowDx;
        this.expandedShadowDy = textAppearance.shadowDy;
        this.expandedShadowRadius = textAppearance.shadowRadius;
        CancelableFontCallback cancelableFontCallback = this.expandedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        this.expandedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont(){

            @Override
            public void apply(Typeface typeface) {
                CollapsingTextHelper.this.setExpandedTypeface(typeface);
            }
        }, textAppearance.getFallbackFont());
        textAppearance.getFontAsync(this.view.getContext(), this.expandedFontCallback);
        this.recalculate();
    }

    public void setExpandedTextColor(ColorStateList colorStateList) {
        if (this.expandedTextColor == colorStateList) return;
        this.expandedTextColor = colorStateList;
        this.recalculate();
    }

    public void setExpandedTextGravity(int n) {
        if (this.expandedTextGravity == n) return;
        this.expandedTextGravity = n;
        this.recalculate();
    }

    public void setExpandedTextSize(float f) {
        if (this.expandedTextSize == f) return;
        this.expandedTextSize = f;
        this.recalculate();
    }

    public void setExpandedTypeface(Typeface typeface) {
        if (!this.setExpandedTypefaceInternal(typeface)) return;
        this.recalculate();
    }

    public void setExpansionFraction(float f) {
        if ((f = MathUtils.clamp(f, 0.0f, 1.0f)) == this.expandedFraction) return;
        this.expandedFraction = f;
        this.calculateCurrentOffsets();
    }

    public void setMaxLines(int n) {
        if (n == this.maxLines) return;
        this.maxLines = n;
        this.clearTexture();
        this.recalculate();
    }

    public void setPositionInterpolator(TimeInterpolator timeInterpolator) {
        this.positionInterpolator = timeInterpolator;
        this.recalculate();
    }

    public final boolean setState(int[] arrn) {
        this.state = arrn;
        if (!this.isStateful()) return false;
        this.recalculate();
        return true;
    }

    public void setText(CharSequence charSequence) {
        if (charSequence != null) {
            if (TextUtils.equals((CharSequence)this.text, (CharSequence)charSequence)) return;
        }
        this.text = charSequence;
        this.textToDraw = null;
        this.clearTexture();
        this.recalculate();
    }

    public void setTextSizeInterpolator(TimeInterpolator timeInterpolator) {
        this.textSizeInterpolator = timeInterpolator;
        this.recalculate();
    }

    public void setTypefaces(Typeface typeface) {
        boolean bl = this.setCollapsedTypefaceInternal(typeface);
        boolean bl2 = this.setExpandedTypefaceInternal(typeface);
        if (!bl) {
            if (!bl2) return;
        }
        this.recalculate();
    }

}

