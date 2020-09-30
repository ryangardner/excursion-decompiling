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
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextPaint
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package com.google.android.material.badge;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public class BadgeDrawable
extends Drawable
implements TextDrawableHelper.TextDrawableDelegate {
    private static final int BADGE_NUMBER_NONE = -1;
    public static final int BOTTOM_END = 8388693;
    public static final int BOTTOM_START = 8388691;
    static final String DEFAULT_EXCEED_MAX_BADGE_NUMBER_SUFFIX = "+";
    private static final int DEFAULT_MAX_BADGE_CHARACTER_COUNT = 4;
    private static final int DEFAULT_STYLE = R.style.Widget_MaterialComponents_Badge;
    private static final int DEFAULT_THEME_ATTR = R.attr.badgeStyle;
    private static final int MAX_CIRCULAR_BADGE_NUMBER_COUNT = 9;
    public static final int TOP_END = 8388661;
    public static final int TOP_START = 8388659;
    private WeakReference<View> anchorViewRef;
    private final Rect badgeBounds;
    private float badgeCenterX;
    private float badgeCenterY;
    private final float badgeRadius;
    private final float badgeWidePadding;
    private final float badgeWithTextRadius;
    private final WeakReference<Context> contextRef;
    private float cornerRadius;
    private WeakReference<ViewGroup> customBadgeParentRef;
    private float halfBadgeHeight;
    private float halfBadgeWidth;
    private int maxBadgeNumber;
    private final SavedState savedState;
    private final MaterialShapeDrawable shapeDrawable;
    private final TextDrawableHelper textDrawableHelper;

    private BadgeDrawable(Context context) {
        this.contextRef = new WeakReference<Context>(context);
        ThemeEnforcement.checkMaterialTheme(context);
        Object object = context.getResources();
        this.badgeBounds = new Rect();
        this.shapeDrawable = new MaterialShapeDrawable();
        this.badgeRadius = object.getDimensionPixelSize(R.dimen.mtrl_badge_radius);
        this.badgeWidePadding = object.getDimensionPixelSize(R.dimen.mtrl_badge_long_text_horizontal_padding);
        this.badgeWithTextRadius = object.getDimensionPixelSize(R.dimen.mtrl_badge_with_text_radius);
        object = new TextDrawableHelper(this);
        this.textDrawableHelper = object;
        ((TextDrawableHelper)object).getTextPaint().setTextAlign(Paint.Align.CENTER);
        this.savedState = new SavedState(context);
        this.setTextAppearanceResource(R.style.TextAppearance_MaterialComponents_Badge);
    }

    private void calculateCenterAndBounds(Context context, Rect rect, View view) {
        float f;
        int n = this.savedState.badgeGravity;
        this.badgeCenterY = n != 8388691 && n != 8388693 ? (float)(rect.top + this.savedState.verticalOffset) : (float)(rect.bottom - this.savedState.verticalOffset);
        if (this.getNumber() <= 9) {
            f = !this.hasNumber() ? this.badgeRadius : this.badgeWithTextRadius;
            this.cornerRadius = f;
            this.halfBadgeHeight = f;
            this.halfBadgeWidth = f;
        } else {
            this.cornerRadius = f = this.badgeWithTextRadius;
            this.halfBadgeHeight = f;
            String string2 = this.getBadgeText();
            this.halfBadgeWidth = this.textDrawableHelper.getTextWidth(string2) / 2.0f + this.badgeWidePadding;
        }
        context = context.getResources();
        n = this.hasNumber() ? R.dimen.mtrl_badge_text_horizontal_edge_offset : R.dimen.mtrl_badge_horizontal_edge_offset;
        n = context.getDimensionPixelSize(n);
        int n2 = this.savedState.badgeGravity;
        if (n2 != 8388659 && n2 != 8388691) {
            f = ViewCompat.getLayoutDirection(view) == 0 ? (float)rect.right + this.halfBadgeWidth - (float)n - (float)this.savedState.horizontalOffset : (float)rect.left - this.halfBadgeWidth + (float)n + (float)this.savedState.horizontalOffset;
            this.badgeCenterX = f;
            return;
        }
        f = ViewCompat.getLayoutDirection(view) == 0 ? (float)rect.left - this.halfBadgeWidth + (float)n + (float)this.savedState.horizontalOffset : (float)rect.right + this.halfBadgeWidth - (float)n - (float)this.savedState.horizontalOffset;
        this.badgeCenterX = f;
    }

    public static BadgeDrawable create(Context context) {
        return BadgeDrawable.createFromAttributes(context, null, DEFAULT_THEME_ATTR, DEFAULT_STYLE);
    }

    private static BadgeDrawable createFromAttributes(Context context, AttributeSet attributeSet, int n, int n2) {
        BadgeDrawable badgeDrawable = new BadgeDrawable(context);
        badgeDrawable.loadDefaultStateFromAttributes(context, attributeSet, n, n2);
        return badgeDrawable;
    }

    public static BadgeDrawable createFromResource(Context context, int n) {
        int n2;
        AttributeSet attributeSet = DrawableUtils.parseDrawableXml(context, n, "badge");
        n = n2 = attributeSet.getStyleAttribute();
        if (n2 != 0) return BadgeDrawable.createFromAttributes(context, attributeSet, DEFAULT_THEME_ATTR, n);
        n = DEFAULT_STYLE;
        return BadgeDrawable.createFromAttributes(context, attributeSet, DEFAULT_THEME_ATTR, n);
    }

    static BadgeDrawable createFromSavedState(Context object, SavedState savedState) {
        object = new BadgeDrawable((Context)object);
        BadgeDrawable.super.restoreFromSavedState(savedState);
        return object;
    }

    private void drawText(Canvas canvas) {
        Rect rect = new Rect();
        String string2 = this.getBadgeText();
        this.textDrawableHelper.getTextPaint().getTextBounds(string2, 0, string2.length(), rect);
        canvas.drawText(string2, this.badgeCenterX, this.badgeCenterY + (float)(rect.height() / 2), (Paint)this.textDrawableHelper.getTextPaint());
    }

    private String getBadgeText() {
        if (this.getNumber() <= this.maxBadgeNumber) {
            return Integer.toString(this.getNumber());
        }
        Context context = (Context)this.contextRef.get();
        if (context != null) return context.getString(R.string.mtrl_exceed_max_badge_number_suffix, new Object[]{this.maxBadgeNumber, DEFAULT_EXCEED_MAX_BADGE_NUMBER_SUFFIX});
        return "";
    }

    private void loadDefaultStateFromAttributes(Context context, AttributeSet attributeSet, int n, int n2) {
        attributeSet = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.Badge, n, n2, new int[0]);
        this.setMaxCharacterCount(attributeSet.getInt(R.styleable.Badge_maxCharacterCount, 4));
        if (attributeSet.hasValue(R.styleable.Badge_number)) {
            this.setNumber(attributeSet.getInt(R.styleable.Badge_number, 0));
        }
        this.setBackgroundColor(BadgeDrawable.readColorFromAttributes(context, (TypedArray)attributeSet, R.styleable.Badge_backgroundColor));
        if (attributeSet.hasValue(R.styleable.Badge_badgeTextColor)) {
            this.setBadgeTextColor(BadgeDrawable.readColorFromAttributes(context, (TypedArray)attributeSet, R.styleable.Badge_badgeTextColor));
        }
        this.setBadgeGravity(attributeSet.getInt(R.styleable.Badge_badgeGravity, 8388661));
        this.setHorizontalOffset(attributeSet.getDimensionPixelOffset(R.styleable.Badge_horizontalOffset, 0));
        this.setVerticalOffset(attributeSet.getDimensionPixelOffset(R.styleable.Badge_verticalOffset, 0));
        attributeSet.recycle();
    }

    private static int readColorFromAttributes(Context context, TypedArray typedArray, int n) {
        return MaterialResources.getColorStateList(context, typedArray, n).getDefaultColor();
    }

    private void restoreFromSavedState(SavedState savedState) {
        this.setMaxCharacterCount(savedState.maxCharacterCount);
        if (savedState.number != -1) {
            this.setNumber(savedState.number);
        }
        this.setBackgroundColor(savedState.backgroundColor);
        this.setBadgeTextColor(savedState.badgeTextColor);
        this.setBadgeGravity(savedState.badgeGravity);
        this.setHorizontalOffset(savedState.horizontalOffset);
        this.setVerticalOffset(savedState.verticalOffset);
    }

    private void setTextAppearance(TextAppearance textAppearance) {
        if (this.textDrawableHelper.getTextAppearance() == textAppearance) {
            return;
        }
        Context context = (Context)this.contextRef.get();
        if (context == null) {
            return;
        }
        this.textDrawableHelper.setTextAppearance(textAppearance, context);
        this.updateCenterAndBounds();
    }

    private void setTextAppearanceResource(int n) {
        Context context = (Context)this.contextRef.get();
        if (context == null) {
            return;
        }
        this.setTextAppearance(new TextAppearance(context, n));
    }

    private void updateCenterAndBounds() {
        Context context = (Context)this.contextRef.get();
        Object object = this.anchorViewRef;
        ViewGroup viewGroup = null;
        object = object != null ? (View)object.get() : null;
        if (context == null) return;
        if (object == null) {
            return;
        }
        Rect rect = new Rect();
        rect.set(this.badgeBounds);
        Rect rect2 = new Rect();
        object.getDrawingRect(rect2);
        ViewGroup viewGroup2 = this.customBadgeParentRef;
        if (viewGroup2 != null) {
            viewGroup = (ViewGroup)viewGroup2.get();
        }
        if (viewGroup != null || BadgeUtils.USE_COMPAT_PARENT) {
            viewGroup2 = viewGroup;
            if (viewGroup == null) {
                viewGroup2 = (ViewGroup)object.getParent();
            }
            viewGroup2.offsetDescendantRectToMyCoords(object, rect2);
        }
        this.calculateCenterAndBounds(context, rect2, (View)object);
        BadgeUtils.updateBadgeBounds(this.badgeBounds, this.badgeCenterX, this.badgeCenterY, this.halfBadgeWidth, this.halfBadgeHeight);
        this.shapeDrawable.setCornerSize(this.cornerRadius);
        if (rect.equals((Object)this.badgeBounds)) return;
        this.shapeDrawable.setBounds(this.badgeBounds);
    }

    private void updateMaxBadgeNumber() {
        this.maxBadgeNumber = (int)Math.pow(10.0, (double)this.getMaxCharacterCount() - 1.0) - 1;
    }

    public void clearNumber() {
        this.savedState.number = -1;
        this.invalidateSelf();
    }

    public void draw(Canvas canvas) {
        if (this.getBounds().isEmpty()) return;
        if (this.getAlpha() == 0) return;
        if (!this.isVisible()) {
            return;
        }
        this.shapeDrawable.draw(canvas);
        if (!this.hasNumber()) return;
        this.drawText(canvas);
    }

    public int getAlpha() {
        return this.savedState.alpha;
    }

    public int getBackgroundColor() {
        return this.shapeDrawable.getFillColor().getDefaultColor();
    }

    public int getBadgeGravity() {
        return this.savedState.badgeGravity;
    }

    public int getBadgeTextColor() {
        return this.textDrawableHelper.getTextPaint().getColor();
    }

    public CharSequence getContentDescription() {
        if (!this.isVisible()) {
            return null;
        }
        if (!this.hasNumber()) return this.savedState.contentDescriptionNumberless;
        if (this.savedState.contentDescriptionQuantityStrings <= 0) return null;
        Context context = (Context)this.contextRef.get();
        if (context == null) {
            return null;
        }
        if (this.getNumber() > this.maxBadgeNumber) return context.getString(this.savedState.contentDescriptionExceedsMaxBadgeNumberRes, new Object[]{this.maxBadgeNumber});
        return context.getResources().getQuantityString(this.savedState.contentDescriptionQuantityStrings, this.getNumber(), new Object[]{this.getNumber()});
    }

    public int getHorizontalOffset() {
        return this.savedState.horizontalOffset;
    }

    public int getIntrinsicHeight() {
        return this.badgeBounds.height();
    }

    public int getIntrinsicWidth() {
        return this.badgeBounds.width();
    }

    public int getMaxCharacterCount() {
        return this.savedState.maxCharacterCount;
    }

    public int getNumber() {
        if (this.hasNumber()) return this.savedState.number;
        return 0;
    }

    public int getOpacity() {
        return -3;
    }

    public SavedState getSavedState() {
        return this.savedState;
    }

    public int getVerticalOffset() {
        return this.savedState.verticalOffset;
    }

    public boolean hasNumber() {
        if (this.savedState.number == -1) return false;
        return true;
    }

    public boolean isStateful() {
        return false;
    }

    @Override
    public boolean onStateChange(int[] arrn) {
        return super.onStateChange(arrn);
    }

    @Override
    public void onTextSizeChange() {
        this.invalidateSelf();
    }

    public void setAlpha(int n) {
        this.savedState.alpha = n;
        this.textDrawableHelper.getTextPaint().setAlpha(n);
        this.invalidateSelf();
    }

    public void setBackgroundColor(int n) {
        this.savedState.backgroundColor = n;
        ColorStateList colorStateList = ColorStateList.valueOf((int)n);
        if (this.shapeDrawable.getFillColor() == colorStateList) return;
        this.shapeDrawable.setFillColor(colorStateList);
        this.invalidateSelf();
    }

    public void setBadgeGravity(int n) {
        if (this.savedState.badgeGravity == n) return;
        this.savedState.badgeGravity = n;
        Object object = this.anchorViewRef;
        if (object == null) return;
        if (object.get() == null) return;
        View view = (View)this.anchorViewRef.get();
        object = this.customBadgeParentRef;
        object = object != null ? (ViewGroup)object.get() : null;
        this.updateBadgeCoordinates(view, (ViewGroup)object);
    }

    public void setBadgeTextColor(int n) {
        this.savedState.badgeTextColor = n;
        if (this.textDrawableHelper.getTextPaint().getColor() == n) return;
        this.textDrawableHelper.getTextPaint().setColor(n);
        this.invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setContentDescriptionExceedsMaxBadgeNumberStringResource(int n) {
        this.savedState.contentDescriptionExceedsMaxBadgeNumberRes = n;
    }

    public void setContentDescriptionNumberless(CharSequence charSequence) {
        this.savedState.contentDescriptionNumberless = charSequence;
    }

    public void setContentDescriptionQuantityStringsResource(int n) {
        this.savedState.contentDescriptionQuantityStrings = n;
    }

    public void setHorizontalOffset(int n) {
        this.savedState.horizontalOffset = n;
        this.updateCenterAndBounds();
    }

    public void setMaxCharacterCount(int n) {
        if (this.savedState.maxCharacterCount == n) return;
        this.savedState.maxCharacterCount = n;
        this.updateMaxBadgeNumber();
        this.textDrawableHelper.setTextWidthDirty(true);
        this.updateCenterAndBounds();
        this.invalidateSelf();
    }

    public void setNumber(int n) {
        n = Math.max(0, n);
        if (this.savedState.number == n) return;
        this.savedState.number = n;
        this.textDrawableHelper.setTextWidthDirty(true);
        this.updateCenterAndBounds();
        this.invalidateSelf();
    }

    public void setVerticalOffset(int n) {
        this.savedState.verticalOffset = n;
        this.updateCenterAndBounds();
    }

    public void setVisible(boolean bl) {
        this.setVisible(bl, false);
    }

    public void updateBadgeCoordinates(View view, ViewGroup viewGroup) {
        this.anchorViewRef = new WeakReference<View>(view);
        this.customBadgeParentRef = new WeakReference<ViewGroup>(viewGroup);
        this.updateCenterAndBounds();
        this.invalidateSelf();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BadgeGravity {
    }

    public static final class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        private int alpha = 255;
        private int backgroundColor;
        private int badgeGravity;
        private int badgeTextColor;
        private int contentDescriptionExceedsMaxBadgeNumberRes;
        private CharSequence contentDescriptionNumberless;
        private int contentDescriptionQuantityStrings;
        private int horizontalOffset;
        private int maxCharacterCount;
        private int number = -1;
        private int verticalOffset;

        public SavedState(Context context) {
            this.badgeTextColor = new TextAppearance((Context)context, (int)R.style.TextAppearance_MaterialComponents_Badge).textColor.getDefaultColor();
            this.contentDescriptionNumberless = context.getString(R.string.mtrl_badge_numberless_content_description);
            this.contentDescriptionQuantityStrings = R.plurals.mtrl_badge_content_description;
            this.contentDescriptionExceedsMaxBadgeNumberRes = R.string.mtrl_exceed_max_badge_number_content_description;
        }

        protected SavedState(Parcel parcel) {
            this.backgroundColor = parcel.readInt();
            this.badgeTextColor = parcel.readInt();
            this.alpha = parcel.readInt();
            this.number = parcel.readInt();
            this.maxCharacterCount = parcel.readInt();
            this.contentDescriptionNumberless = parcel.readString();
            this.contentDescriptionQuantityStrings = parcel.readInt();
            this.badgeGravity = parcel.readInt();
            this.horizontalOffset = parcel.readInt();
            this.verticalOffset = parcel.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.backgroundColor);
            parcel.writeInt(this.badgeTextColor);
            parcel.writeInt(this.alpha);
            parcel.writeInt(this.number);
            parcel.writeInt(this.maxCharacterCount);
            parcel.writeString(this.contentDescriptionNumberless.toString());
            parcel.writeInt(this.contentDescriptionQuantityStrings);
            parcel.writeInt(this.badgeGravity);
            parcel.writeInt(this.horizontalOffset);
            parcel.writeInt(this.verticalOffset);
        }

    }

}

