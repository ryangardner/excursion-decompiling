/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Point
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 */
package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.appcompat.widget.AppCompatEditText;
import com.google.android.material.R;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class TextInputEditText
extends AppCompatEditText {
    private final Rect parentRect = new Rect();
    private boolean textInputLayoutFocusedRectEnabled;

    public TextInputEditText(Context context) {
        this(context, null);
    }

    public TextInputEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.editTextStyle);
    }

    public TextInputEditText(Context context, AttributeSet attributeSet, int n) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, n, 0), attributeSet, n);
        context = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.TextInputEditText, n, R.style.Widget_Design_TextInputEditText, new int[0]);
        this.setTextInputLayoutFocusedRectEnabled(context.getBoolean(R.styleable.TextInputEditText_textInputLayoutFocusedRectEnabled, false));
        context.recycle();
    }

    private String getAccessibilityNodeInfoText(TextInputLayout object) {
        Editable editable = this.getText();
        CharSequence charSequence = ((TextInputLayout)((Object)object)).getHint();
        CharSequence charSequence2 = ((TextInputLayout)((Object)object)).getHelperText();
        CharSequence charSequence3 = ((TextInputLayout)((Object)object)).getError();
        boolean bl = TextUtils.isEmpty((CharSequence)editable);
        boolean bl2 = TextUtils.isEmpty((CharSequence)charSequence);
        boolean bl3 = TextUtils.isEmpty((CharSequence)charSequence2) ^ true;
        boolean bl4 = TextUtils.isEmpty((CharSequence)charSequence3) ^ true;
        String string2 = "";
        object = bl2 ^ true ? charSequence.toString() : "";
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)object);
        object = (bl4 || bl3) && !TextUtils.isEmpty((CharSequence)object) ? ", " : "";
        ((StringBuilder)charSequence).append((String)object);
        object = ((StringBuilder)charSequence).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)object);
        object = bl4 ? charSequence3 : (bl3 ? charSequence2 : "");
        ((StringBuilder)charSequence).append(object);
        charSequence3 = ((StringBuilder)charSequence).toString();
        if (!(bl ^ true)) {
            if (TextUtils.isEmpty((CharSequence)charSequence3)) return "";
            return charSequence3;
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((Object)editable);
        object = string2;
        if (!TextUtils.isEmpty((CharSequence)charSequence3)) {
            object = new StringBuilder();
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append((String)charSequence3);
            object = ((StringBuilder)object).toString();
        }
        ((StringBuilder)charSequence2).append((String)object);
        return ((StringBuilder)charSequence2).toString();
    }

    private CharSequence getHintFromLayout() {
        Object object = this.getTextInputLayout();
        if (object == null) return null;
        return object.getHint();
    }

    private TextInputLayout getTextInputLayout() {
        ViewParent viewParent = this.getParent();
        while (viewParent instanceof View) {
            if (viewParent instanceof TextInputLayout) {
                return (TextInputLayout)viewParent;
            }
            viewParent = viewParent.getParent();
        }
        return null;
    }

    public void getFocusedRect(Rect rect) {
        super.getFocusedRect(rect);
        TextInputLayout textInputLayout = this.getTextInputLayout();
        if (textInputLayout == null) return;
        if (!this.textInputLayoutFocusedRectEnabled) return;
        if (rect == null) return;
        textInputLayout.getFocusedRect(this.parentRect);
        rect.bottom = this.parentRect.bottom;
    }

    public boolean getGlobalVisibleRect(Rect rect, Point point) {
        boolean bl = super.getGlobalVisibleRect(rect, point);
        TextInputLayout textInputLayout = this.getTextInputLayout();
        if (textInputLayout == null) return bl;
        if (!this.textInputLayoutFocusedRectEnabled) return bl;
        if (rect == null) return bl;
        textInputLayout.getGlobalVisibleRect(this.parentRect, point);
        rect.bottom = this.parentRect.bottom;
        return bl;
    }

    public CharSequence getHint() {
        TextInputLayout textInputLayout = this.getTextInputLayout();
        if (textInputLayout == null) return super.getHint();
        if (!textInputLayout.isProvidingHint()) return super.getHint();
        return textInputLayout.getHint();
    }

    public boolean isTextInputLayoutFocusedRectEnabled() {
        return this.textInputLayoutFocusedRectEnabled;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextInputLayout textInputLayout = this.getTextInputLayout();
        if (textInputLayout == null) return;
        if (!textInputLayout.isProvidingHint()) return;
        if (super.getHint() != null) return;
        if (!ManufacturerUtils.isMeizuDevice()) return;
        this.setHint((CharSequence)"");
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnection = super.onCreateInputConnection(editorInfo);
        if (inputConnection == null) return inputConnection;
        if (editorInfo.hintText != null) return inputConnection;
        editorInfo.hintText = this.getHintFromLayout();
        return inputConnection;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        TextInputLayout textInputLayout = this.getTextInputLayout();
        if (Build.VERSION.SDK_INT >= 23) return;
        if (textInputLayout == null) return;
        accessibilityNodeInfo.setText((CharSequence)this.getAccessibilityNodeInfoText(textInputLayout));
    }

    public boolean requestRectangleOnScreen(Rect object) {
        boolean bl = super.requestRectangleOnScreen(object);
        object = this.getTextInputLayout();
        if (object == null) return bl;
        if (!this.textInputLayoutFocusedRectEnabled) return bl;
        this.parentRect.set(0, object.getHeight() - this.getResources().getDimensionPixelOffset(R.dimen.mtrl_edittext_rectangle_top_offset), object.getWidth(), object.getHeight());
        object.requestRectangleOnScreen(this.parentRect, true);
        return bl;
    }

    public void setTextInputLayoutFocusedRectEnabled(boolean bl) {
        this.textInputLayoutFocusedRectEnabled = bl;
    }
}

