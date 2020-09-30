/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.widget.FrameLayout
 */
package com.google.android.material.bottomsheet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class BottomSheetDialog
extends AppCompatDialog {
    private BottomSheetBehavior<FrameLayout> behavior;
    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback(){

        @Override
        public void onSlide(View view, float f) {
        }

        @Override
        public void onStateChanged(View view, int n) {
            if (n != 5) return;
            BottomSheetDialog.this.cancel();
        }
    };
    boolean cancelable = true;
    private boolean canceledOnTouchOutside = true;
    private boolean canceledOnTouchOutsideSet;
    private FrameLayout container;
    boolean dismissWithAnimation;

    public BottomSheetDialog(Context context) {
        this(context, 0);
    }

    public BottomSheetDialog(Context context, int n) {
        super(context, BottomSheetDialog.getThemeResId(context, n));
        this.supportRequestWindowFeature(1);
    }

    protected BottomSheetDialog(Context context, boolean bl, DialogInterface.OnCancelListener onCancelListener) {
        super(context, bl, onCancelListener);
        this.supportRequestWindowFeature(1);
        this.cancelable = bl;
    }

    private FrameLayout ensureContainerAndBehavior() {
        if (this.container != null) return this.container;
        Object object = (FrameLayout)View.inflate((Context)this.getContext(), (int)R.layout.design_bottom_sheet_dialog, null);
        this.container = object;
        this.behavior = object = BottomSheetBehavior.from((FrameLayout)object.findViewById(R.id.design_bottom_sheet));
        ((BottomSheetBehavior)object).addBottomSheetCallback(this.bottomSheetCallback);
        this.behavior.setHideable(this.cancelable);
        return this.container;
    }

    private static int getThemeResId(Context context, int n) {
        int n2 = n;
        if (n != 0) return n2;
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, typedValue, true)) return R.style.Theme_Design_Light_BottomSheetDialog;
        return typedValue.resourceId;
    }

    private View wrapInBottomSheet(int n, View view, ViewGroup.LayoutParams layoutParams) {
        this.ensureContainerAndBehavior();
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout)this.container.findViewById(R.id.coordinator);
        View view2 = view;
        if (n != 0) {
            view2 = view;
            if (view == null) {
                view2 = this.getLayoutInflater().inflate(n, (ViewGroup)coordinatorLayout, false);
            }
        }
        view = (FrameLayout)this.container.findViewById(R.id.design_bottom_sheet);
        view.removeAllViews();
        if (layoutParams == null) {
            view.addView(view2);
        } else {
            view.addView(view2, layoutParams);
        }
        coordinatorLayout.findViewById(R.id.touch_outside).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (!BottomSheetDialog.this.cancelable) return;
                if (!BottomSheetDialog.this.isShowing()) return;
                if (!BottomSheetDialog.this.shouldWindowCloseOnTouchOutside()) return;
                BottomSheetDialog.this.cancel();
            }
        });
        ViewCompat.setAccessibilityDelegate(view, new AccessibilityDelegateCompat(){

            @Override
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                if (BottomSheetDialog.this.cancelable) {
                    accessibilityNodeInfoCompat.addAction(1048576);
                    accessibilityNodeInfoCompat.setDismissable(true);
                    return;
                }
                accessibilityNodeInfoCompat.setDismissable(false);
            }

            @Override
            public boolean performAccessibilityAction(View view, int n, Bundle bundle) {
                if (n != 1048576) return super.performAccessibilityAction(view, n, bundle);
                if (!BottomSheetDialog.this.cancelable) return super.performAccessibilityAction(view, n, bundle);
                BottomSheetDialog.this.cancel();
                return true;
            }
        });
        view.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        return this.container;
    }

    public void cancel() {
        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.getBehavior();
        if (this.dismissWithAnimation && bottomSheetBehavior.getState() != 5) {
            bottomSheetBehavior.setState(5);
            return;
        }
        super.cancel();
    }

    public BottomSheetBehavior<FrameLayout> getBehavior() {
        if (this.behavior != null) return this.behavior;
        this.ensureContainerAndBehavior();
        return this.behavior;
    }

    public boolean getDismissWithAnimation() {
        return this.dismissWithAnimation;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        bundle = this.getWindow();
        if (bundle == null) return;
        if (Build.VERSION.SDK_INT >= 21) {
            bundle.clearFlags(67108864);
            bundle.addFlags(Integer.MIN_VALUE);
        }
        bundle.setLayout(-1, -1);
    }

    protected void onStart() {
        super.onStart();
        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.behavior;
        if (bottomSheetBehavior == null) return;
        if (bottomSheetBehavior.getState() != 5) return;
        this.behavior.setState(4);
    }

    void removeDefaultCallback() {
        this.behavior.removeBottomSheetCallback(this.bottomSheetCallback);
    }

    public void setCancelable(boolean bl) {
        super.setCancelable(bl);
        if (this.cancelable == bl) return;
        this.cancelable = bl;
        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.behavior;
        if (bottomSheetBehavior == null) return;
        bottomSheetBehavior.setHideable(bl);
    }

    public void setCanceledOnTouchOutside(boolean bl) {
        super.setCanceledOnTouchOutside(bl);
        if (bl && !this.cancelable) {
            this.cancelable = true;
        }
        this.canceledOnTouchOutside = bl;
        this.canceledOnTouchOutsideSet = true;
    }

    @Override
    public void setContentView(int n) {
        super.setContentView(this.wrapInBottomSheet(n, null, null));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(this.wrapInBottomSheet(0, view, null));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        super.setContentView(this.wrapInBottomSheet(0, view, layoutParams));
    }

    public void setDismissWithAnimation(boolean bl) {
        this.dismissWithAnimation = bl;
    }

    boolean shouldWindowCloseOnTouchOutside() {
        if (this.canceledOnTouchOutsideSet) return this.canceledOnTouchOutside;
        TypedArray typedArray = this.getContext().obtainStyledAttributes(new int[]{16843611});
        this.canceledOnTouchOutside = typedArray.getBoolean(0, true);
        typedArray.recycle();
        this.canceledOnTouchOutsideSet = true;
        return this.canceledOnTouchOutside;
    }

}

