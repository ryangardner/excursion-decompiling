/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.method.KeyListener
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityManager
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.Filterable
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package com.google.android.material.textfield;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.ListPopupWindow;
import com.google.android.material.R;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialAutoCompleteTextView
extends AppCompatAutoCompleteTextView {
    private static final int MAX_ITEMS_MEASURED = 15;
    private final AccessibilityManager accessibilityManager;
    private final ListPopupWindow modalListPopup;
    private final Rect tempRect = new Rect();

    public MaterialAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public MaterialAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.autoCompleteTextViewStyle);
    }

    public MaterialAutoCompleteTextView(Context object, AttributeSet attributeSet, int n) {
        super(MaterialThemeOverlay.wrap((Context)object, attributeSet, n, 0), attributeSet, n);
        object = this.getContext();
        attributeSet = ThemeEnforcement.obtainStyledAttributes((Context)object, attributeSet, R.styleable.MaterialAutoCompleteTextView, n, R.style.Widget_AppCompat_AutoCompleteTextView, new int[0]);
        if (attributeSet.hasValue(R.styleable.MaterialAutoCompleteTextView_android_inputType) && attributeSet.getInt(R.styleable.MaterialAutoCompleteTextView_android_inputType, 0) == 0) {
            this.setKeyListener(null);
        }
        this.accessibilityManager = (AccessibilityManager)object.getSystemService("accessibility");
        object = new ListPopupWindow((Context)object);
        this.modalListPopup = object;
        ((ListPopupWindow)object).setModal(true);
        this.modalListPopup.setAnchorView((View)this);
        this.modalListPopup.setInputMethodMode(2);
        this.modalListPopup.setAdapter(this.getAdapter());
        this.modalListPopup.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> object, View view, int n, long l) {
                block2 : {
                    int n2;
                    block4 : {
                        block3 : {
                            object = n < 0 ? MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItem() : MaterialAutoCompleteTextView.this.getAdapter().getItem(n);
                            MaterialAutoCompleteTextView.this.updateText(object);
                            object = MaterialAutoCompleteTextView.this.getOnItemClickListener();
                            if (object == null) break block2;
                            if (view == null) break block3;
                            n2 = n;
                            if (n >= 0) break block4;
                        }
                        view = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedView();
                        n2 = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItemPosition();
                        l = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItemId();
                    }
                    object.onItemClick((AdapterView)MaterialAutoCompleteTextView.this.modalListPopup.getListView(), view, n2, l);
                }
                MaterialAutoCompleteTextView.this.modalListPopup.dismiss();
            }
        });
        attributeSet.recycle();
    }

    private TextInputLayout findTextInputLayoutAncestor() {
        ViewParent viewParent = this.getParent();
        while (viewParent != null) {
            if (viewParent instanceof TextInputLayout) {
                return (TextInputLayout)viewParent;
            }
            viewParent = viewParent.getParent();
        }
        return null;
    }

    private int measureContentWidth() {
        ListAdapter listAdapter = this.getAdapter();
        TextInputLayout textInputLayout = this.findTextInputLayoutAncestor();
        int n = 0;
        if (listAdapter == null) return 0;
        if (textInputLayout == null) {
            return 0;
        }
        int n2 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)0);
        int n3 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)0);
        int n4 = Math.max(0, this.modalListPopup.getSelectedItemPosition());
        int n5 = Math.min(listAdapter.getCount(), n4 + 15);
        int n6 = Math.max(0, n5 - 15);
        Drawable drawable2 = null;
        n4 = 0;
        do {
            if (n6 >= n5) {
                drawable2 = this.modalListPopup.getBackground();
                n6 = n4;
                if (drawable2 == null) return n6 + textInputLayout.getEndIconView().getMeasuredWidth();
                drawable2.getPadding(this.tempRect);
                n6 = n4 + (this.tempRect.left + this.tempRect.right);
                return n6 + textInputLayout.getEndIconView().getMeasuredWidth();
            }
            int n7 = listAdapter.getItemViewType(n6);
            int n8 = n;
            if (n7 != n) {
                drawable2 = null;
                n8 = n7;
            }
            if ((drawable2 = listAdapter.getView(n6, (View)drawable2, (ViewGroup)textInputLayout)).getLayoutParams() == null) {
                drawable2.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            drawable2.measure(n2, n3);
            n4 = Math.max(n4, drawable2.getMeasuredWidth());
            ++n6;
            n = n8;
        } while (true);
    }

    private <T extends ListAdapter & Filterable> void updateText(Object object) {
        if (Build.VERSION.SDK_INT >= 17) {
            this.setText(this.convertSelectionToString(object), false);
            return;
        }
        ListAdapter listAdapter = this.getAdapter();
        this.setAdapter(null);
        this.setText(this.convertSelectionToString(object));
        this.setAdapter((T)listAdapter);
    }

    public CharSequence getHint() {
        TextInputLayout textInputLayout = this.findTextInputLayoutAncestor();
        if (textInputLayout == null) return super.getHint();
        if (!textInputLayout.isProvidingHint()) return super.getHint();
        return textInputLayout.getHint();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextInputLayout textInputLayout = this.findTextInputLayoutAncestor();
        if (textInputLayout == null) return;
        if (!textInputLayout.isProvidingHint()) return;
        if (super.getHint() != null) return;
        if (!ManufacturerUtils.isMeizuDevice()) return;
        this.setHint((CharSequence)"");
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (View.MeasureSpec.getMode((int)n) != Integer.MIN_VALUE) return;
        this.setMeasuredDimension(Math.min(Math.max(this.getMeasuredWidth(), this.measureContentWidth()), View.MeasureSpec.getSize((int)n)), this.getMeasuredHeight());
    }

    public <T extends ListAdapter & Filterable> void setAdapter(T t) {
        super.setAdapter(t);
        this.modalListPopup.setAdapter(this.getAdapter());
    }

    public void showDropDown() {
        AccessibilityManager accessibilityManager;
        if (this.getInputType() == 0 && (accessibilityManager = this.accessibilityManager) != null && accessibilityManager.isTouchExplorationEnabled()) {
            this.modalListPopup.show();
            return;
        }
        super.showDropDown();
    }

}

