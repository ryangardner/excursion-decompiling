/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Typeface
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.LocaleList
 *  android.text.method.PasswordTransformationMethod
 *  android.text.method.TransformationMethod
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.LocaleList;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.TintInfo;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.AutoSizeableTextView;
import androidx.core.widget.TextViewCompat;
import java.lang.ref.WeakReference;
import java.util.Locale;

class AppCompatTextHelper {
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int TEXT_FONT_WEIGHT_UNSPECIFIED = -1;
    private boolean mAsyncFontPending;
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableEndTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableStartTint;
    private TintInfo mDrawableTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;
    private int mFontWeight = -1;
    private int mStyle = 0;
    private final TextView mView;

    AppCompatTextHelper(TextView textView) {
        this.mView = textView;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(this.mView);
    }

    private void applyCompoundDrawableTint(Drawable drawable2, TintInfo tintInfo) {
        if (drawable2 == null) return;
        if (tintInfo == null) return;
        AppCompatDrawableManager.tintDrawable(drawable2, tintInfo, this.mView.getDrawableState());
    }

    private static TintInfo createTintInfo(Context object, AppCompatDrawableManager appCompatDrawableManager, int n) {
        if ((appCompatDrawableManager = appCompatDrawableManager.getTintList((Context)object, n)) == null) return null;
        object = new TintInfo();
        object.mHasTintList = true;
        object.mTintList = appCompatDrawableManager;
        return object;
    }

    private void setCompoundDrawables(Drawable drawable2, Drawable drawable3, Drawable textView, Drawable drawable4, Drawable textView2, Drawable textView3) {
        if (Build.VERSION.SDK_INT >= 17 && (textView2 != null || textView3 != null)) {
            textView = this.mView.getCompoundDrawablesRelative();
            drawable2 = this.mView;
            if (textView2 == null) {
                textView2 = textView[0];
            }
            if (drawable3 == null) {
                drawable3 = textView[1];
            }
            if (textView3 == null) {
                textView3 = textView[2];
            }
            if (drawable4 == null) {
                drawable4 = textView[3];
            }
            drawable2.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable)textView2, drawable3, (Drawable)textView3, drawable4);
            return;
        }
        if (drawable2 == null && drawable3 == null && textView == null) {
            if (drawable4 == null) return;
        }
        if (Build.VERSION.SDK_INT >= 17 && ((textView2 = this.mView.getCompoundDrawablesRelative())[0] != null || textView2[2] != null)) {
            textView = this.mView;
            drawable2 = textView2[0];
            if (drawable3 == null) {
                drawable3 = textView2[1];
            }
            textView3 = textView2[2];
            if (drawable4 == null) {
                drawable4 = textView2[3];
            }
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable2, drawable3, (Drawable)textView3, drawable4);
            return;
        }
        textView3 = this.mView.getCompoundDrawables();
        textView2 = this.mView;
        if (drawable2 == null) {
            drawable2 = textView3[0];
        }
        if (drawable3 == null) {
            drawable3 = textView3[1];
        }
        if (textView == null) {
            textView = textView3[2];
        }
        if (drawable4 == null) {
            drawable4 = textView3[3];
        }
        textView2.setCompoundDrawablesWithIntrinsicBounds(drawable2, drawable3, (Drawable)textView, drawable4);
    }

    private void setCompoundTints() {
        TintInfo tintInfo;
        this.mDrawableLeftTint = tintInfo = this.mDrawableTint;
        this.mDrawableTopTint = tintInfo;
        this.mDrawableRightTint = tintInfo;
        this.mDrawableBottomTint = tintInfo;
        this.mDrawableStartTint = tintInfo;
        this.mDrawableEndTint = tintInfo;
    }

    private void setTextSizeInternal(int n, float f) {
        this.mAutoSizeTextHelper.setTextSizeInternal(n, f);
    }

    private void updateTypefaceAndStyle(Context object, TintTypedArray tintTypedArray) {
        boolean bl;
        this.mStyle = tintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, this.mStyle);
        int n = Build.VERSION.SDK_INT;
        boolean bl2 = false;
        if (n >= 28) {
            this.mFontWeight = n = tintTypedArray.getInt(R.styleable.TextAppearance_android_textFontWeight, -1);
            if (n != -1) {
                this.mStyle = this.mStyle & 2 | 0;
            }
        }
        if (!tintTypedArray.hasValue(R.styleable.TextAppearance_android_fontFamily) && !tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
            if (!tintTypedArray.hasValue(R.styleable.TextAppearance_android_typeface)) return;
            this.mAsyncFontPending = false;
            n = tintTypedArray.getInt(R.styleable.TextAppearance_android_typeface, 1);
            if (n == 1) {
                this.mFontTypeface = Typeface.SANS_SERIF;
                return;
            }
            if (n == 2) {
                this.mFontTypeface = Typeface.SERIF;
                return;
            }
            if (n != 3) {
                return;
            }
            this.mFontTypeface = Typeface.MONOSPACE;
            return;
        }
        this.mFontTypeface = null;
        n = tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily) ? R.styleable.TextAppearance_fontFamily : R.styleable.TextAppearance_android_fontFamily;
        final int n2 = this.mFontWeight;
        final int n3 = this.mStyle;
        if (!object.isRestricted()) {
            object = new ResourcesCompat.FontCallback(new WeakReference<TextView>(this.mView)){
                final /* synthetic */ WeakReference val$textViewWeak;
                {
                    this.val$textViewWeak = weakReference;
                }

                @Override
                public void onFontRetrievalFailed(int n) {
                }

                @Override
                public void onFontRetrieved(Typeface typeface) {
                    Typeface typeface2 = typeface;
                    if (Build.VERSION.SDK_INT >= 28) {
                        int n = n2;
                        typeface2 = typeface;
                        if (n != -1) {
                            boolean bl = (n3 & 2) != 0;
                            typeface2 = Typeface.create((Typeface)typeface, (int)n, (boolean)bl);
                        }
                    }
                    AppCompatTextHelper.this.onAsyncTypefaceReceived(this.val$textViewWeak, typeface2);
                }
            };
            try {
                object = tintTypedArray.getFont(n, this.mStyle, (ResourcesCompat.FontCallback)object);
                if (object != null) {
                    if (Build.VERSION.SDK_INT >= 28 && this.mFontWeight != -1) {
                        object = Typeface.create((Typeface)object, (int)0);
                        n2 = this.mFontWeight;
                        bl = (this.mStyle & 2) != 0;
                        this.mFontTypeface = Typeface.create((Typeface)object, (int)n2, (boolean)bl);
                    } else {
                        this.mFontTypeface = object;
                    }
                }
                bl = this.mFontTypeface == null;
                this.mAsyncFontPending = bl;
            }
            catch (Resources.NotFoundException | UnsupportedOperationException throwable) {
                // empty catch block
            }
        }
        if (this.mFontTypeface != null) return;
        object = tintTypedArray.getString(n);
        if (object == null) return;
        if (Build.VERSION.SDK_INT >= 28 && this.mFontWeight != -1) {
            object = Typeface.create((String)object, (int)0);
            n = this.mFontWeight;
            bl = bl2;
            if ((this.mStyle & 2) != 0) {
                bl = true;
            }
            this.mFontTypeface = Typeface.create((Typeface)object, (int)n, (boolean)bl);
            return;
        }
        this.mFontTypeface = Typeface.create((String)object, (int)this.mStyle);
    }

    void applyCompoundDrawablesTints() {
        Drawable[] arrdrawable;
        if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            arrdrawable = this.mView.getCompoundDrawables();
            this.applyCompoundDrawableTint(arrdrawable[0], this.mDrawableLeftTint);
            this.applyCompoundDrawableTint(arrdrawable[1], this.mDrawableTopTint);
            this.applyCompoundDrawableTint(arrdrawable[2], this.mDrawableRightTint);
            this.applyCompoundDrawableTint(arrdrawable[3], this.mDrawableBottomTint);
        }
        if (Build.VERSION.SDK_INT < 17) return;
        if (this.mDrawableStartTint == null) {
            if (this.mDrawableEndTint == null) return;
        }
        arrdrawable = this.mView.getCompoundDrawablesRelative();
        this.applyCompoundDrawableTint(arrdrawable[0], this.mDrawableStartTint);
        this.applyCompoundDrawableTint(arrdrawable[2], this.mDrawableEndTint);
    }

    void autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText();
    }

    int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
    }

    int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
    }

    int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
    }

    int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
    }

    int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType();
    }

    ColorStateList getCompoundDrawableTintList() {
        TintInfo tintInfo = this.mDrawableTint;
        if (tintInfo == null) return null;
        return tintInfo.mTintList;
    }

    PorterDuff.Mode getCompoundDrawableTintMode() {
        TintInfo tintInfo = this.mDrawableTint;
        if (tintInfo == null) return null;
        return tintInfo.mTintMode;
    }

    boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled();
    }

    void loadFromAttributes(AttributeSet object, int n) {
        String string2;
        Object object2;
        TintTypedArray tintTypedArray;
        String string3;
        boolean bl;
        Context context = this.mView.getContext();
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        TintTypedArray tintTypedArray2 = TintTypedArray.obtainStyledAttributes(context, object, R.styleable.AppCompatTextHelper, n, 0);
        Object object3 = this.mView;
        ViewCompat.saveAttributeDataForStyleable((View)object3, object3.getContext(), R.styleable.AppCompatTextHelper, object, tintTypedArray2.getWrappedTypeArray(), n, 0);
        int n2 = tintTypedArray2.getResourceId(R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (tintTypedArray2.hasValue(R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.mDrawableLeftTint = AppCompatTextHelper.createTintInfo(context, appCompatDrawableManager, tintTypedArray2.getResourceId(R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (tintTypedArray2.hasValue(R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.mDrawableTopTint = AppCompatTextHelper.createTintInfo(context, appCompatDrawableManager, tintTypedArray2.getResourceId(R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (tintTypedArray2.hasValue(R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.mDrawableRightTint = AppCompatTextHelper.createTintInfo(context, appCompatDrawableManager, tintTypedArray2.getResourceId(R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (tintTypedArray2.hasValue(R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.mDrawableBottomTint = AppCompatTextHelper.createTintInfo(context, appCompatDrawableManager, tintTypedArray2.getResourceId(R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        if (Build.VERSION.SDK_INT >= 17) {
            if (tintTypedArray2.hasValue(R.styleable.AppCompatTextHelper_android_drawableStart)) {
                this.mDrawableStartTint = AppCompatTextHelper.createTintInfo(context, appCompatDrawableManager, tintTypedArray2.getResourceId(R.styleable.AppCompatTextHelper_android_drawableStart, 0));
            }
            if (tintTypedArray2.hasValue(R.styleable.AppCompatTextHelper_android_drawableEnd)) {
                this.mDrawableEndTint = AppCompatTextHelper.createTintInfo(context, appCompatDrawableManager, tintTypedArray2.getResourceId(R.styleable.AppCompatTextHelper_android_drawableEnd, 0));
            }
        }
        tintTypedArray2.recycle();
        boolean bl2 = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
        if (n2 != -1) {
            tintTypedArray = TintTypedArray.obtainStyledAttributes(context, n2, R.styleable.TextAppearance);
            if (!bl2 && tintTypedArray.hasValue(R.styleable.TextAppearance_textAllCaps)) {
                bl = tintTypedArray.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
                n2 = 1;
            } else {
                bl = false;
                n2 = 0;
            }
            this.updateTypefaceAndStyle(context, tintTypedArray);
            if (Build.VERSION.SDK_INT < 23) {
                object2 = tintTypedArray.hasValue(R.styleable.TextAppearance_android_textColor) ? tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor) : null;
                object3 = tintTypedArray.hasValue(R.styleable.TextAppearance_android_textColorHint) ? tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColorHint) : null;
                tintTypedArray2 = tintTypedArray.hasValue(R.styleable.TextAppearance_android_textColorLink) ? tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColorLink) : null;
            } else {
                object3 = null;
                tintTypedArray2 = null;
                object2 = null;
            }
            string2 = tintTypedArray.hasValue(R.styleable.TextAppearance_textLocale) ? tintTypedArray.getString(R.styleable.TextAppearance_textLocale) : null;
            string3 = Build.VERSION.SDK_INT >= 26 && tintTypedArray.hasValue(R.styleable.TextAppearance_fontVariationSettings) ? tintTypedArray.getString(R.styleable.TextAppearance_fontVariationSettings) : null;
            tintTypedArray.recycle();
            tintTypedArray = tintTypedArray2;
            tintTypedArray2 = object2;
        } else {
            object3 = null;
            string3 = null;
            bl = false;
            n2 = 0;
            tintTypedArray = null;
            string2 = null;
            tintTypedArray2 = null;
        }
        TintTypedArray tintTypedArray3 = TintTypedArray.obtainStyledAttributes(context, object, R.styleable.TextAppearance, n, 0);
        if (!bl2 && tintTypedArray3.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            bl = tintTypedArray3.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
            n2 = 1;
        }
        TextView textView = object3;
        TintTypedArray tintTypedArray4 = tintTypedArray;
        object2 = tintTypedArray2;
        if (Build.VERSION.SDK_INT < 23) {
            if (tintTypedArray3.hasValue(R.styleable.TextAppearance_android_textColor)) {
                tintTypedArray2 = tintTypedArray3.getColorStateList(R.styleable.TextAppearance_android_textColor);
            }
            if (tintTypedArray3.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
                object3 = tintTypedArray3.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
            }
            textView = object3;
            tintTypedArray4 = tintTypedArray;
            object2 = tintTypedArray2;
            if (tintTypedArray3.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                tintTypedArray4 = tintTypedArray3.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
                object2 = tintTypedArray2;
                textView = object3;
            }
        }
        if (tintTypedArray3.hasValue(R.styleable.TextAppearance_textLocale)) {
            string2 = tintTypedArray3.getString(R.styleable.TextAppearance_textLocale);
        }
        object3 = string3;
        if (Build.VERSION.SDK_INT >= 26) {
            object3 = string3;
            if (tintTypedArray3.hasValue(R.styleable.TextAppearance_fontVariationSettings)) {
                object3 = tintTypedArray3.getString(R.styleable.TextAppearance_fontVariationSettings);
            }
        }
        if (Build.VERSION.SDK_INT >= 28 && tintTypedArray3.hasValue(R.styleable.TextAppearance_android_textSize) && tintTypedArray3.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        this.updateTypefaceAndStyle(context, tintTypedArray3);
        tintTypedArray3.recycle();
        if (object2 != null) {
            this.mView.setTextColor((ColorStateList)object2);
        }
        if (textView != null) {
            this.mView.setHintTextColor((ColorStateList)textView);
        }
        if (tintTypedArray4 != null) {
            this.mView.setLinkTextColor((ColorStateList)tintTypedArray4);
        }
        if (!bl2 && n2 != 0) {
            this.setAllCaps(bl);
        }
        if ((tintTypedArray2 = this.mFontTypeface) != null) {
            if (this.mFontWeight == -1) {
                this.mView.setTypeface((Typeface)tintTypedArray2, this.mStyle);
            } else {
                this.mView.setTypeface((Typeface)tintTypedArray2);
            }
        }
        if (object3 != null) {
            this.mView.setFontVariationSettings((String)object3);
        }
        if (string2 != null) {
            if (Build.VERSION.SDK_INT >= 24) {
                this.mView.setTextLocales(LocaleList.forLanguageTags((String)string2));
            } else if (Build.VERSION.SDK_INT >= 21) {
                object3 = string2.substring(0, string2.indexOf(44));
                this.mView.setTextLocale(Locale.forLanguageTag((String)object3));
            }
        }
        this.mAutoSizeTextHelper.loadFromAttributes((AttributeSet)object, n);
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && this.mAutoSizeTextHelper.getAutoSizeTextType() != 0 && ((int[])(object3 = this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes())).length > 0) {
            if ((float)this.mView.getAutoSizeStepGranularity() != -1.0f) {
                this.mView.setAutoSizeTextTypeUniformWithConfiguration(this.mAutoSizeTextHelper.getAutoSizeMinTextSize(), this.mAutoSizeTextHelper.getAutoSizeMaxTextSize(), this.mAutoSizeTextHelper.getAutoSizeStepGranularity(), 0);
            } else {
                this.mView.setAutoSizeTextTypeUniformWithPresetSizes((int[])object3, 0);
            }
        }
        object = (n = ((TintTypedArray)(object2 = TintTypedArray.obtainStyledAttributes(context, object, R.styleable.AppCompatTextView))).getResourceId(R.styleable.AppCompatTextView_drawableLeftCompat, -1)) != -1 ? appCompatDrawableManager.getDrawable(context, n) : null;
        n = ((TintTypedArray)object2).getResourceId(R.styleable.AppCompatTextView_drawableTopCompat, -1);
        object3 = n != -1 ? appCompatDrawableManager.getDrawable(context, n) : null;
        n = ((TintTypedArray)object2).getResourceId(R.styleable.AppCompatTextView_drawableRightCompat, -1);
        tintTypedArray2 = n != -1 ? appCompatDrawableManager.getDrawable(context, n) : null;
        n = ((TintTypedArray)object2).getResourceId(R.styleable.AppCompatTextView_drawableBottomCompat, -1);
        string3 = n != -1 ? appCompatDrawableManager.getDrawable(context, n) : null;
        n = ((TintTypedArray)object2).getResourceId(R.styleable.AppCompatTextView_drawableStartCompat, -1);
        string2 = n != -1 ? appCompatDrawableManager.getDrawable(context, n) : null;
        n = ((TintTypedArray)object2).getResourceId(R.styleable.AppCompatTextView_drawableEndCompat, -1);
        tintTypedArray = n != -1 ? appCompatDrawableManager.getDrawable(context, n) : null;
        this.setCompoundDrawables((Drawable)object, (Drawable)object3, (Drawable)tintTypedArray2, (Drawable)string3, (Drawable)string2, (Drawable)tintTypedArray);
        if (((TintTypedArray)object2).hasValue(R.styleable.AppCompatTextView_drawableTint)) {
            object = ((TintTypedArray)object2).getColorStateList(R.styleable.AppCompatTextView_drawableTint);
            TextViewCompat.setCompoundDrawableTintList(this.mView, (ColorStateList)object);
        }
        if (((TintTypedArray)object2).hasValue(R.styleable.AppCompatTextView_drawableTintMode)) {
            object = DrawableUtils.parseTintMode(((TintTypedArray)object2).getInt(R.styleable.AppCompatTextView_drawableTintMode, -1), null);
            TextViewCompat.setCompoundDrawableTintMode(this.mView, (PorterDuff.Mode)object);
        }
        n = ((TintTypedArray)object2).getDimensionPixelSize(R.styleable.AppCompatTextView_firstBaselineToTopHeight, -1);
        int n3 = ((TintTypedArray)object2).getDimensionPixelSize(R.styleable.AppCompatTextView_lastBaselineToBottomHeight, -1);
        n2 = ((TintTypedArray)object2).getDimensionPixelSize(R.styleable.AppCompatTextView_lineHeight, -1);
        ((TintTypedArray)object2).recycle();
        if (n != -1) {
            TextViewCompat.setFirstBaselineToTopHeight(this.mView, n);
        }
        if (n3 != -1) {
            TextViewCompat.setLastBaselineToBottomHeight(this.mView, n3);
        }
        if (n2 == -1) return;
        TextViewCompat.setLineHeight(this.mView, n2);
    }

    void onAsyncTypefaceReceived(WeakReference<TextView> textView, Typeface typeface) {
        if (!this.mAsyncFontPending) return;
        this.mFontTypeface = typeface;
        if ((textView = (TextView)textView.get()) == null) return;
        textView.setTypeface(typeface, this.mStyle);
    }

    void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) return;
        this.autoSizeText();
    }

    void onSetCompoundDrawables() {
        this.applyCompoundDrawablesTints();
    }

    void onSetTextAppearance(Context object, int n) {
        ColorStateList colorStateList;
        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(object, n, R.styleable.TextAppearance);
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            this.setAllCaps(tintTypedArray.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
        }
        if (Build.VERSION.SDK_INT < 23 && tintTypedArray.hasValue(R.styleable.TextAppearance_android_textColor) && (colorStateList = tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor)) != null) {
            this.mView.setTextColor(colorStateList);
        }
        if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_textSize) && tintTypedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        this.updateTypefaceAndStyle((Context)object, tintTypedArray);
        if (Build.VERSION.SDK_INT >= 26 && tintTypedArray.hasValue(R.styleable.TextAppearance_fontVariationSettings) && (object = tintTypedArray.getString(R.styleable.TextAppearance_fontVariationSettings)) != null) {
            this.mView.setFontVariationSettings((String)object);
        }
        tintTypedArray.recycle();
        object = this.mFontTypeface;
        if (object == null) return;
        this.mView.setTypeface((Typeface)object, this.mStyle);
    }

    void setAllCaps(boolean bl) {
        this.mView.setAllCaps(bl);
    }

    void setAutoSizeTextTypeUniformWithConfiguration(int n, int n2, int n3, int n4) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(n, n2, n3, n4);
    }

    void setAutoSizeTextTypeUniformWithPresetSizes(int[] arrn, int n) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(arrn, n);
    }

    void setAutoSizeTextTypeWithDefaults(int n) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(n);
    }

    void setCompoundDrawableTintList(ColorStateList colorStateList) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        this.mDrawableTint.mTintList = colorStateList;
        TintInfo tintInfo = this.mDrawableTint;
        boolean bl = colorStateList != null;
        tintInfo.mHasTintList = bl;
        this.setCompoundTints();
    }

    void setCompoundDrawableTintMode(PorterDuff.Mode mode) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        this.mDrawableTint.mTintMode = mode;
        TintInfo tintInfo = this.mDrawableTint;
        boolean bl = mode != null;
        tintInfo.mHasTintMode = bl;
        this.setCompoundTints();
    }

    void setTextSize(int n, float f) {
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) return;
        if (this.isAutoSizeEnabled()) return;
        this.setTextSizeInternal(n, f);
    }

}

