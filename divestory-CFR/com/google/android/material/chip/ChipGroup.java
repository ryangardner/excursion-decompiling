/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewGroup$OnHierarchyChangeListener
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package com.google.android.material.chip;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.internal.FlowLayout;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.ArrayList;
import java.util.List;

public class ChipGroup
extends FlowLayout {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_ChipGroup;
    private int checkedId = -1;
    private final CheckedStateTracker checkedStateTracker = new CheckedStateTracker();
    private int chipSpacingHorizontal;
    private int chipSpacingVertical;
    private OnCheckedChangeListener onCheckedChangeListener;
    private PassThroughHierarchyChangeListener passThroughListener = new PassThroughHierarchyChangeListener();
    private boolean protectFromCheckedChange = false;
    private boolean selectionRequired;
    private boolean singleSelection;

    public ChipGroup(Context context) {
        this(context, null);
    }

    public ChipGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.chipGroupStyle);
    }

    public ChipGroup(Context context, AttributeSet attributeSet, int n) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, n, DEF_STYLE_RES), attributeSet, n);
        context = ThemeEnforcement.obtainStyledAttributes(this.getContext(), attributeSet, R.styleable.ChipGroup, n, DEF_STYLE_RES, new int[0]);
        n = context.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacing, 0);
        this.setChipSpacingHorizontal(context.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacingHorizontal, n));
        this.setChipSpacingVertical(context.getDimensionPixelOffset(R.styleable.ChipGroup_chipSpacingVertical, n));
        this.setSingleLine(context.getBoolean(R.styleable.ChipGroup_singleLine, false));
        this.setSingleSelection(context.getBoolean(R.styleable.ChipGroup_singleSelection, false));
        this.setSelectionRequired(context.getBoolean(R.styleable.ChipGroup_selectionRequired, false));
        n = context.getResourceId(R.styleable.ChipGroup_checkedChip, -1);
        if (n != -1) {
            this.checkedId = n;
        }
        context.recycle();
        super.setOnHierarchyChangeListener((ViewGroup.OnHierarchyChangeListener)this.passThroughListener);
        ViewCompat.setImportantForAccessibility((View)this, 1);
    }

    private int getChipCount() {
        int n = 0;
        int n2 = 0;
        while (n < this.getChildCount()) {
            int n3 = n2;
            if (this.getChildAt(n) instanceof Chip) {
                n3 = n2 + 1;
            }
            ++n;
            n2 = n3;
        }
        return n2;
    }

    private void setCheckedId(int n) {
        this.setCheckedId(n, true);
    }

    private void setCheckedId(int n, boolean bl) {
        this.checkedId = n;
        OnCheckedChangeListener onCheckedChangeListener = this.onCheckedChangeListener;
        if (onCheckedChangeListener == null) return;
        if (!this.singleSelection) return;
        if (!bl) return;
        onCheckedChangeListener.onCheckedChanged(this, n);
    }

    private void setCheckedStateForView(int n, boolean bl) {
        View view = this.findViewById(n);
        if (!(view instanceof Chip)) return;
        this.protectFromCheckedChange = true;
        ((Chip)view).setChecked(bl);
        this.protectFromCheckedChange = false;
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        Chip chip;
        if (view instanceof Chip && (chip = (Chip)view).isChecked()) {
            int n2 = this.checkedId;
            if (n2 != -1 && this.singleSelection) {
                this.setCheckedStateForView(n2, false);
            }
            this.setCheckedId(chip.getId());
        }
        super.addView(view, n, layoutParams);
    }

    public void check(int n) {
        int n2 = this.checkedId;
        if (n == n2) {
            return;
        }
        if (n2 != -1 && this.singleSelection) {
            this.setCheckedStateForView(n2, false);
        }
        if (n != -1) {
            this.setCheckedStateForView(n, true);
        }
        this.setCheckedId(n);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (!super.checkLayoutParams(layoutParams)) return false;
        if (!(layoutParams instanceof LayoutParams)) return false;
        return true;
    }

    public void clearCheck() {
        this.protectFromCheckedChange = true;
        int n = 0;
        do {
            if (n >= this.getChildCount()) {
                this.protectFromCheckedChange = false;
                this.setCheckedId(-1);
                return;
            }
            View view = this.getChildAt(n);
            if (view instanceof Chip) {
                ((Chip)view).setChecked(false);
            }
            ++n;
        } while (true);
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getCheckedChipId() {
        if (!this.singleSelection) return -1;
        return this.checkedId;
    }

    public List<Integer> getCheckedChipIds() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int n = 0;
        while (n < this.getChildCount()) {
            View view = this.getChildAt(n);
            if (view instanceof Chip && ((Chip)view).isChecked()) {
                arrayList.add(view.getId());
                if (this.singleSelection) {
                    return arrayList;
                }
            }
            ++n;
        }
        return arrayList;
    }

    public int getChipSpacingHorizontal() {
        return this.chipSpacingHorizontal;
    }

    public int getChipSpacingVertical() {
        return this.chipSpacingVertical;
    }

    int getIndexOfChip(View view) {
        if (!(view instanceof Chip)) {
            return -1;
        }
        int n = 0;
        int n2 = 0;
        while (n < this.getChildCount()) {
            int n3 = n2;
            if (this.getChildAt(n) instanceof Chip) {
                if ((Chip)this.getChildAt(n) == view) {
                    return n2;
                }
                n3 = n2 + 1;
            }
            ++n;
            n2 = n3;
        }
        return -1;
    }

    public boolean isSelectionRequired() {
        return this.selectionRequired;
    }

    @Override
    public boolean isSingleLine() {
        return super.isSingleLine();
    }

    public boolean isSingleSelection() {
        return this.singleSelection;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        int n = this.checkedId;
        if (n == -1) return;
        this.setCheckedStateForView(n, true);
        this.setCheckedId(this.checkedId);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo object) {
        super.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo)object);
        object = AccessibilityNodeInfoCompat.wrap((AccessibilityNodeInfo)object);
        int n = this.isSingleLine() ? this.getChipCount() : -1;
        int n2 = this.getRowCount();
        int n3 = this.isSingleSelection() ? 1 : 2;
        ((AccessibilityNodeInfoCompat)object).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(n2, n, false, n3));
    }

    public void setChipSpacing(int n) {
        this.setChipSpacingHorizontal(n);
        this.setChipSpacingVertical(n);
    }

    public void setChipSpacingHorizontal(int n) {
        if (this.chipSpacingHorizontal == n) return;
        this.chipSpacingHorizontal = n;
        this.setItemSpacing(n);
        this.requestLayout();
    }

    public void setChipSpacingHorizontalResource(int n) {
        this.setChipSpacingHorizontal(this.getResources().getDimensionPixelOffset(n));
    }

    public void setChipSpacingResource(int n) {
        this.setChipSpacing(this.getResources().getDimensionPixelOffset(n));
    }

    public void setChipSpacingVertical(int n) {
        if (this.chipSpacingVertical == n) return;
        this.chipSpacingVertical = n;
        this.setLineSpacing(n);
        this.requestLayout();
    }

    public void setChipSpacingVerticalResource(int n) {
        this.setChipSpacingVertical(this.getResources().getDimensionPixelOffset(n));
    }

    @Deprecated
    public void setDividerDrawableHorizontal(Drawable drawable2) {
        throw new UnsupportedOperationException("Changing divider drawables have no effect. ChipGroup do not use divider drawables as spacing.");
    }

    @Deprecated
    public void setDividerDrawableVertical(Drawable drawable2) {
        throw new UnsupportedOperationException("Changing divider drawables have no effect. ChipGroup do not use divider drawables as spacing.");
    }

    @Deprecated
    public void setFlexWrap(int n) {
        throw new UnsupportedOperationException("Changing flex wrap not allowed. ChipGroup exposes a singleLine attribute instead.");
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.passThroughListener.onHierarchyChangeListener = onHierarchyChangeListener;
    }

    public void setSelectionRequired(boolean bl) {
        this.selectionRequired = bl;
    }

    @Deprecated
    public void setShowDividerHorizontal(int n) {
        throw new UnsupportedOperationException("Changing divider modes has no effect. ChipGroup do not use divider drawables as spacing.");
    }

    @Deprecated
    public void setShowDividerVertical(int n) {
        throw new UnsupportedOperationException("Changing divider modes has no effect. ChipGroup do not use divider drawables as spacing.");
    }

    public void setSingleLine(int n) {
        this.setSingleLine(this.getResources().getBoolean(n));
    }

    @Override
    public void setSingleLine(boolean bl) {
        super.setSingleLine(bl);
    }

    public void setSingleSelection(int n) {
        this.setSingleSelection(this.getResources().getBoolean(n));
    }

    public void setSingleSelection(boolean bl) {
        if (this.singleSelection == bl) return;
        this.singleSelection = bl;
        this.clearCheck();
    }

    private class CheckedStateTracker
    implements CompoundButton.OnCheckedChangeListener {
        private CheckedStateTracker() {
        }

        public void onCheckedChanged(CompoundButton object, boolean bl) {
            if (ChipGroup.this.protectFromCheckedChange) {
                return;
            }
            if (ChipGroup.this.getCheckedChipIds().isEmpty() && ChipGroup.this.selectionRequired) {
                ChipGroup.this.setCheckedStateForView(object.getId(), true);
                ChipGroup.this.setCheckedId(object.getId(), false);
                return;
            }
            int n = object.getId();
            if (!bl) {
                if (ChipGroup.this.checkedId != n) return;
                ChipGroup.this.setCheckedId(-1);
                return;
            }
            if (ChipGroup.this.checkedId != -1 && ChipGroup.this.checkedId != n && ChipGroup.this.singleSelection) {
                object = ChipGroup.this;
                ((ChipGroup)((Object)object)).setCheckedStateForView(((ChipGroup)((Object)object)).checkedId, false);
            }
            ChipGroup.this.setCheckedId(n);
        }
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    public static interface OnCheckedChangeListener {
        public void onCheckedChanged(ChipGroup var1, int var2);
    }

    private class PassThroughHierarchyChangeListener
    implements ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener;

        private PassThroughHierarchyChangeListener() {
        }

        public void onChildViewAdded(View view, View view2) {
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener;
            if (view == ChipGroup.this && view2 instanceof Chip) {
                if (view2.getId() == -1) {
                    int n = Build.VERSION.SDK_INT >= 17 ? View.generateViewId() : view2.hashCode();
                    view2.setId(n);
                }
                ((Chip)view2).setOnCheckedChangeListenerInternal(ChipGroup.this.checkedStateTracker);
            }
            if ((onHierarchyChangeListener = this.onHierarchyChangeListener) == null) return;
            onHierarchyChangeListener.onChildViewAdded(view, view2);
        }

        public void onChildViewRemoved(View view, View view2) {
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener;
            if (view == ChipGroup.this && view2 instanceof Chip) {
                ((Chip)view2).setOnCheckedChangeListenerInternal(null);
            }
            if ((onHierarchyChangeListener = this.onHierarchyChangeListener) == null) return;
            onHierarchyChangeListener.onChildViewRemoved(view, view2);
        }
    }

}

