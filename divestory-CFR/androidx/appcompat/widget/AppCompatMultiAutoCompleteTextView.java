/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.widget.MultiAutoCompleteTextView
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatBackgroundHelper;
import androidx.appcompat.widget.AppCompatHintHelper;
import androidx.appcompat.widget.AppCompatTextHelper;
import androidx.appcompat.widget.ThemeUtils;
import androidx.appcompat.widget.TintContextWrapper;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.TintableBackgroundView;

public class AppCompatMultiAutoCompleteTextView
extends MultiAutoCompleteTextView
implements TintableBackgroundView {
    private static final int[] TINT_ATTRS = new int[]{16843126};
    private final AppCompatBackgroundHelper mBackgroundTintHelper;
    private final AppCompatTextHelper mTextHelper;

    public AppCompatMultiAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public AppCompatMultiAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.autoCompleteTextViewStyle);
    }

    public AppCompatMultiAutoCompleteTextView(Context object, AttributeSet attributeSet, int n) {
        super(TintContextWrapper.wrap((Context)object), attributeSet, n);
        ThemeUtils.checkAppCompatTheme((View)this, this.getContext());
        object = TintTypedArray.obtainStyledAttributes(this.getContext(), attributeSet, TINT_ATTRS, n, 0);
        if (((TintTypedArray)object).hasValue(0)) {
            this.setDropDownBackgroundDrawable(((TintTypedArray)object).getDrawable(0));
        }
        ((TintTypedArray)object).recycle();
        this.mBackgroundTintHelper = object = new AppCompatBackgroundHelper((View)this);
        ((AppCompatBackgroundHelper)object).loadFromAttributes(attributeSet, n);
        this.mTextHelper = object = new AppCompatTextHelper((TextView)this);
        ((AppCompatTextHelper)object).loadFromAttributes(attributeSet, n);
        this.mTextHelper.applyCompoundDrawablesTints();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Object object = this.mBackgroundTintHelper;
        if (object != null) {
            ((AppCompatBackgroundHelper)object).applySupportBackgroundTint();
        }
        if ((object = this.mTextHelper) == null) return;
        ((AppCompatTextHelper)object).applyCompoundDrawablesTints();
    }

    @Override
    public ColorStateList getSupportBackgroundTintList() {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper == null) return null;
        return appCompatBackgroundHelper.getSupportBackgroundTintList();
    }

    @Override
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper == null) return null;
        return appCompatBackgroundHelper.getSupportBackgroundTintMode();
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        return AppCompatHintHelper.onCreateInputConnection(super.onCreateInputConnection(editorInfo), editorInfo, (View)this);
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        super.setBackgroundDrawable(drawable2);
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper == null) return;
        appCompatBackgroundHelper.onSetBackgroundDrawable(drawable2);
    }

    public void setBackgroundResource(int n) {
        super.setBackgroundResource(n);
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper == null) return;
        appCompatBackgroundHelper.onSetBackgroundResource(n);
    }

    public void setDropDownBackgroundResource(int n) {
        this.setDropDownBackgroundDrawable(AppCompatResources.getDrawable(this.getContext(), n));
    }

    @Override
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper == null) return;
        appCompatBackgroundHelper.setSupportBackgroundTintList(colorStateList);
    }

    @Override
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper == null) return;
        appCompatBackgroundHelper.setSupportBackgroundTintMode(mode);
    }

    public void setTextAppearance(Context context, int n) {
        super.setTextAppearance(context, n);
        AppCompatTextHelper appCompatTextHelper = this.mTextHelper;
        if (appCompatTextHelper == null) return;
        appCompatTextHelper.onSetTextAppearance(context, n);
    }
}

