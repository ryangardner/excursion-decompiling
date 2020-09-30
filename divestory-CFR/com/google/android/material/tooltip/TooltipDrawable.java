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
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$FontMetrics
 *  android.graphics.Rect
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$OnLayoutChangeListener
 */
package com.google.android.material.tooltip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.MarkerEdgeTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.OffsetEdgeTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;

public class TooltipDrawable
extends MaterialShapeDrawable
implements TextDrawableHelper.TextDrawableDelegate {
    private static final int DEFAULT_STYLE = R.style.Widget_MaterialComponents_Tooltip;
    private static final int DEFAULT_THEME_ATTR = R.attr.tooltipStyle;
    private int arrowSize;
    private final View.OnLayoutChangeListener attachedViewLayoutChangeListener = new View.OnLayoutChangeListener(){

        public void onLayoutChange(View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
            TooltipDrawable.this.updateLocationOnScreen(view);
        }
    };
    private final Context context;
    private final Rect displayFrame = new Rect();
    private final Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    private int layoutMargin;
    private int locationOnScreenX;
    private int minHeight;
    private int minWidth;
    private int padding;
    private CharSequence text;
    private final TextDrawableHelper textDrawableHelper = new TextDrawableHelper(this);

    private TooltipDrawable(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.context = context;
        this.textDrawableHelper.getTextPaint().density = context.getResources().getDisplayMetrics().density;
        this.textDrawableHelper.getTextPaint().setTextAlign(Paint.Align.CENTER);
    }

    private float calculatePointerOffset() {
        int n;
        if (this.displayFrame.right - this.getBounds().right - this.locationOnScreenX - this.layoutMargin < 0) {
            n = this.displayFrame.right - this.getBounds().right - this.locationOnScreenX - this.layoutMargin;
            return n;
        } else {
            if (this.displayFrame.left - this.getBounds().left - this.locationOnScreenX + this.layoutMargin <= 0) {
                return 0.0f;
            }
            n = this.displayFrame.left - this.getBounds().left - this.locationOnScreenX + this.layoutMargin;
        }
        return n;
    }

    private float calculateTextCenterFromBaseline() {
        this.textDrawableHelper.getTextPaint().getFontMetrics(this.fontMetrics);
        return (this.fontMetrics.descent + this.fontMetrics.ascent) / 2.0f;
    }

    private float calculateTextOriginAndAlignment(Rect rect) {
        return (float)rect.centerY() - this.calculateTextCenterFromBaseline();
    }

    public static TooltipDrawable create(Context context) {
        return TooltipDrawable.createFromAttributes(context, null, DEFAULT_THEME_ATTR, DEFAULT_STYLE);
    }

    public static TooltipDrawable createFromAttributes(Context context, AttributeSet attributeSet) {
        return TooltipDrawable.createFromAttributes(context, attributeSet, DEFAULT_THEME_ATTR, DEFAULT_STYLE);
    }

    public static TooltipDrawable createFromAttributes(Context object, AttributeSet attributeSet, int n, int n2) {
        object = new TooltipDrawable((Context)object, attributeSet, n, n2);
        TooltipDrawable.super.loadFromAttributes(attributeSet, n, n2);
        return object;
    }

    private EdgeTreatment createMarkerEdge() {
        float f = -this.calculatePointerOffset();
        float f2 = (float)((double)this.getBounds().width() - (double)this.arrowSize * Math.sqrt(2.0)) / 2.0f;
        f2 = Math.min(Math.max(f, -f2), f2);
        return new OffsetEdgeTreatment(new MarkerEdgeTreatment(this.arrowSize), f2);
    }

    private void drawText(Canvas canvas) {
        if (this.text == null) {
            return;
        }
        Rect rect = this.getBounds();
        int n = (int)this.calculateTextOriginAndAlignment(rect);
        if (this.textDrawableHelper.getTextAppearance() != null) {
            this.textDrawableHelper.getTextPaint().drawableState = this.getState();
            this.textDrawableHelper.updateTextPaintDrawState(this.context);
        }
        CharSequence charSequence = this.text;
        canvas.drawText(charSequence, 0, charSequence.length(), (float)rect.centerX(), (float)n, (Paint)this.textDrawableHelper.getTextPaint());
    }

    private float getTextWidth() {
        CharSequence charSequence = this.text;
        if (charSequence != null) return this.textDrawableHelper.getTextWidth(charSequence.toString());
        return 0.0f;
    }

    private void loadFromAttributes(AttributeSet attributeSet, int n, int n2) {
        attributeSet = ThemeEnforcement.obtainStyledAttributes(this.context, attributeSet, R.styleable.Tooltip, n, n2, new int[0]);
        this.arrowSize = this.context.getResources().getDimensionPixelSize(R.dimen.mtrl_tooltip_arrowSize);
        this.setShapeAppearanceModel(this.getShapeAppearanceModel().toBuilder().setBottomEdge(this.createMarkerEdge()).build());
        this.setText(attributeSet.getText(R.styleable.Tooltip_android_text));
        this.setTextAppearance(MaterialResources.getTextAppearance(this.context, (TypedArray)attributeSet, R.styleable.Tooltip_android_textAppearance));
        n = MaterialColors.getColor(this.context, R.attr.colorOnBackground, TooltipDrawable.class.getCanonicalName());
        n = MaterialColors.layer(ColorUtils.setAlphaComponent(MaterialColors.getColor(this.context, 16842801, TooltipDrawable.class.getCanonicalName()), 229), ColorUtils.setAlphaComponent(n, 153));
        this.setFillColor(ColorStateList.valueOf((int)attributeSet.getColor(R.styleable.Tooltip_backgroundTint, n)));
        this.setStrokeColor(ColorStateList.valueOf((int)MaterialColors.getColor(this.context, R.attr.colorSurface, TooltipDrawable.class.getCanonicalName())));
        this.padding = attributeSet.getDimensionPixelSize(R.styleable.Tooltip_android_padding, 0);
        this.minWidth = attributeSet.getDimensionPixelSize(R.styleable.Tooltip_android_minWidth, 0);
        this.minHeight = attributeSet.getDimensionPixelSize(R.styleable.Tooltip_android_minHeight, 0);
        this.layoutMargin = attributeSet.getDimensionPixelSize(R.styleable.Tooltip_android_layout_margin, 0);
        attributeSet.recycle();
    }

    private void updateLocationOnScreen(View view) {
        int[] arrn = new int[2];
        view.getLocationOnScreen(arrn);
        this.locationOnScreenX = arrn[0];
        view.getWindowVisibleDisplayFrame(this.displayFrame);
    }

    public void detachView(View view) {
        if (view == null) {
            return;
        }
        view.removeOnLayoutChangeListener(this.attachedViewLayoutChangeListener);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(this.calculatePointerOffset(), (float)(-((double)this.arrowSize * Math.sqrt(2.0) - (double)this.arrowSize)));
        super.draw(canvas);
        this.drawText(canvas);
        canvas.restore();
    }

    public int getIntrinsicHeight() {
        return (int)Math.max(this.textDrawableHelper.getTextPaint().getTextSize(), (float)this.minHeight);
    }

    public int getIntrinsicWidth() {
        return (int)Math.max((float)(this.padding * 2) + this.getTextWidth(), (float)this.minWidth);
    }

    public int getLayoutMargin() {
        return this.layoutMargin;
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public int getMinWidth() {
        return this.minWidth;
    }

    public CharSequence getText() {
        return this.text;
    }

    public TextAppearance getTextAppearance() {
        return this.textDrawableHelper.getTextAppearance();
    }

    public int getTextPadding() {
        return this.padding;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.setShapeAppearanceModel(this.getShapeAppearanceModel().toBuilder().setBottomEdge(this.createMarkerEdge()).build());
    }

    @Override
    public boolean onStateChange(int[] arrn) {
        return super.onStateChange(arrn);
    }

    @Override
    public void onTextSizeChange() {
        this.invalidateSelf();
    }

    public void setLayoutMargin(int n) {
        this.layoutMargin = n;
        this.invalidateSelf();
    }

    public void setMinHeight(int n) {
        this.minHeight = n;
        this.invalidateSelf();
    }

    public void setMinWidth(int n) {
        this.minWidth = n;
        this.invalidateSelf();
    }

    public void setRelativeToView(View view) {
        if (view == null) {
            return;
        }
        this.updateLocationOnScreen(view);
        view.addOnLayoutChangeListener(this.attachedViewLayoutChangeListener);
    }

    public void setText(CharSequence charSequence) {
        if (TextUtils.equals((CharSequence)this.text, (CharSequence)charSequence)) return;
        this.text = charSequence;
        this.textDrawableHelper.setTextWidthDirty(true);
        this.invalidateSelf();
    }

    public void setTextAppearance(TextAppearance textAppearance) {
        this.textDrawableHelper.setTextAppearance(textAppearance, this.context);
    }

    public void setTextAppearanceResource(int n) {
        this.setTextAppearance(new TextAppearance(this.context, n));
    }

    public void setTextPadding(int n) {
        this.padding = n;
        this.invalidateSelf();
    }

    public void setTextResource(int n) {
        this.setText(this.context.getResources().getString(n));
    }

}

