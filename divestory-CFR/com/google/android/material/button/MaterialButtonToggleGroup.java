/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package com.google.android.material.button;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeMap;

public class MaterialButtonToggleGroup
extends LinearLayout {
    private static final int DEF_STYLE_RES;
    private static final String LOG_TAG;
    private int checkedId;
    private final CheckedStateTracker checkedStateTracker = new CheckedStateTracker();
    private Integer[] childOrder;
    private final Comparator<MaterialButton> childOrderComparator = new Comparator<MaterialButton>(){

        @Override
        public int compare(MaterialButton materialButton, MaterialButton materialButton2) {
            int n = Boolean.valueOf(materialButton.isChecked()).compareTo(materialButton2.isChecked());
            if (n != 0) {
                return n;
            }
            n = Boolean.valueOf(materialButton.isPressed()).compareTo(materialButton2.isPressed());
            if (n == 0) return Integer.valueOf(MaterialButtonToggleGroup.this.indexOfChild((View)materialButton)).compareTo(MaterialButtonToggleGroup.this.indexOfChild((View)materialButton2));
            return n;
        }
    };
    private final LinkedHashSet<OnButtonCheckedListener> onButtonCheckedListeners = new LinkedHashSet();
    private final List<CornerData> originalCornerData = new ArrayList<CornerData>();
    private final PressedStateTracker pressedStateTracker = new PressedStateTracker();
    private boolean selectionRequired;
    private boolean singleSelection;
    private boolean skipCheckedStateTracker = false;

    static {
        LOG_TAG = MaterialButtonToggleGroup.class.getSimpleName();
        DEF_STYLE_RES = R.style.Widget_MaterialComponents_MaterialButtonToggleGroup;
    }

    public MaterialButtonToggleGroup(Context context) {
        this(context, null);
    }

    public MaterialButtonToggleGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialButtonToggleGroupStyle);
    }

    public MaterialButtonToggleGroup(Context context, AttributeSet attributeSet, int n) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, n, DEF_STYLE_RES), attributeSet, n);
        context = ThemeEnforcement.obtainStyledAttributes(this.getContext(), attributeSet, R.styleable.MaterialButtonToggleGroup, n, DEF_STYLE_RES, new int[0]);
        this.setSingleSelection(context.getBoolean(R.styleable.MaterialButtonToggleGroup_singleSelection, false));
        this.checkedId = context.getResourceId(R.styleable.MaterialButtonToggleGroup_checkedButton, -1);
        this.selectionRequired = context.getBoolean(R.styleable.MaterialButtonToggleGroup_selectionRequired, false);
        this.setChildrenDrawingOrderEnabled(true);
        context.recycle();
        ViewCompat.setImportantForAccessibility((View)this, 1);
    }

    private void adjustChildMarginsAndUpdateLayout() {
        int n = this.getFirstVisibleChildIndex();
        if (n == -1) {
            return;
        }
        int n2 = n + 1;
        do {
            if (n2 >= this.getChildCount()) {
                this.resetChildMargins(n);
                return;
            }
            MaterialButton materialButton = this.getChildButton(n2);
            MaterialButton materialButton2 = this.getChildButton(n2 - 1);
            int n3 = Math.min(materialButton.getStrokeWidth(), materialButton2.getStrokeWidth());
            materialButton2 = this.buildLayoutParams((View)materialButton);
            if (this.getOrientation() == 0) {
                MarginLayoutParamsCompat.setMarginEnd((ViewGroup.MarginLayoutParams)materialButton2, 0);
                MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams)materialButton2, -n3);
            } else {
                ((LinearLayout.LayoutParams)materialButton2).bottomMargin = 0;
                ((LinearLayout.LayoutParams)materialButton2).topMargin = -n3;
            }
            materialButton.setLayoutParams((ViewGroup.LayoutParams)materialButton2);
            ++n2;
        } while (true);
    }

    private LinearLayout.LayoutParams buildLayoutParams(View view) {
        if (!((view = view.getLayoutParams()) instanceof LinearLayout.LayoutParams)) return new LinearLayout.LayoutParams(view.width, view.height);
        return (LinearLayout.LayoutParams)view;
    }

    private void checkForced(int n) {
        this.setCheckedStateForView(n, true);
        this.updateCheckedStates(n, true);
        this.setCheckedId(n);
    }

    private void dispatchOnButtonChecked(int n, boolean bl) {
        Iterator iterator2 = this.onButtonCheckedListeners.iterator();
        while (iterator2.hasNext()) {
            ((OnButtonCheckedListener)iterator2.next()).onButtonChecked(this, n, bl);
        }
    }

    private MaterialButton getChildButton(int n) {
        return (MaterialButton)this.getChildAt(n);
    }

    private int getFirstVisibleChildIndex() {
        int n = this.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            if (this.isChildVisible(n2)) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    private int getIndexWithinVisibleButtons(View view) {
        if (!(view instanceof MaterialButton)) {
            return -1;
        }
        int n = 0;
        int n2 = 0;
        while (n < this.getChildCount()) {
            if (this.getChildAt(n) == view) {
                return n2;
            }
            int n3 = n2;
            if (this.getChildAt(n) instanceof MaterialButton) {
                n3 = n2;
                if (this.isChildVisible(n)) {
                    n3 = n2 + 1;
                }
            }
            ++n;
            n2 = n3;
        }
        return -1;
    }

    private int getLastVisibleChildIndex() {
        int n = this.getChildCount() - 1;
        while (n >= 0) {
            if (this.isChildVisible(n)) {
                return n;
            }
            --n;
        }
        return -1;
    }

    private CornerData getNewCornerData(int n, int n2, int n3) {
        CornerData cornerData = this.originalCornerData.get(n);
        if (n2 == n3) {
            return cornerData;
        }
        boolean bl = this.getOrientation() == 0;
        if (n == n2) {
            if (!bl) return CornerData.top(cornerData);
            return CornerData.start(cornerData, (View)this);
        }
        if (n != n3) return null;
        if (!bl) return CornerData.bottom(cornerData);
        return CornerData.end(cornerData, (View)this);
    }

    private int getVisibleButtonCount() {
        int n = 0;
        int n2 = 0;
        while (n < this.getChildCount()) {
            int n3 = n2;
            if (this.getChildAt(n) instanceof MaterialButton) {
                n3 = n2;
                if (this.isChildVisible(n)) {
                    n3 = n2 + 1;
                }
            }
            ++n;
            n2 = n3;
        }
        return n2;
    }

    private boolean isChildVisible(int n) {
        if (this.getChildAt(n).getVisibility() == 8) return false;
        return true;
    }

    private void resetChildMargins(int n) {
        if (this.getChildCount() == 0) return;
        if (n == -1) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)this.getChildButton(n).getLayoutParams();
        if (this.getOrientation() == 1) {
            layoutParams.topMargin = 0;
            layoutParams.bottomMargin = 0;
            return;
        }
        MarginLayoutParamsCompat.setMarginEnd((ViewGroup.MarginLayoutParams)layoutParams, 0);
        MarginLayoutParamsCompat.setMarginStart((ViewGroup.MarginLayoutParams)layoutParams, 0);
        layoutParams.leftMargin = 0;
        layoutParams.rightMargin = 0;
    }

    private void setCheckedId(int n) {
        this.checkedId = n;
        this.dispatchOnButtonChecked(n, true);
    }

    private void setCheckedStateForView(int n, boolean bl) {
        View view = this.findViewById(n);
        if (!(view instanceof MaterialButton)) return;
        this.skipCheckedStateTracker = true;
        ((MaterialButton)view).setChecked(bl);
        this.skipCheckedStateTracker = false;
    }

    private void setGeneratedIdIfNeeded(MaterialButton materialButton) {
        if (materialButton.getId() != -1) return;
        materialButton.setId(ViewCompat.generateViewId());
    }

    private void setupButtonChild(MaterialButton materialButton) {
        materialButton.setMaxLines(1);
        materialButton.setEllipsize(TextUtils.TruncateAt.END);
        materialButton.setCheckable(true);
        materialButton.addOnCheckedChangeListener(this.checkedStateTracker);
        materialButton.setOnPressedChangeListenerInternal(this.pressedStateTracker);
        materialButton.setShouldDrawSurfaceColorStroke(true);
    }

    private static void updateBuilderWithCornerData(ShapeAppearanceModel.Builder builder, CornerData cornerData) {
        if (cornerData == null) {
            builder.setAllCornerSizes(0.0f);
            return;
        }
        builder.setTopLeftCornerSize(cornerData.topLeft).setBottomLeftCornerSize(cornerData.bottomLeft).setTopRightCornerSize(cornerData.topRight).setBottomRightCornerSize(cornerData.bottomRight);
    }

    private boolean updateCheckedStates(int n, boolean bl) {
        Object object = this.getCheckedButtonIds();
        if (this.selectionRequired && object.isEmpty()) {
            this.setCheckedStateForView(n, true);
            this.checkedId = n;
            return false;
        }
        if (!bl) return true;
        if (!this.singleSelection) return true;
        object.remove((Object)n);
        object = object.iterator();
        while (object.hasNext()) {
            n = (Integer)object.next();
            this.setCheckedStateForView(n, false);
            this.dispatchOnButtonChecked(n, false);
        }
        return true;
    }

    private void updateChildOrder() {
        TreeMap<MaterialButton, Integer> treeMap = new TreeMap<MaterialButton, Integer>(this.childOrderComparator);
        int n = this.getChildCount();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.childOrder = treeMap.values().toArray(new Integer[0]);
                return;
            }
            treeMap.put(this.getChildButton(n2), n2);
            ++n2;
        } while (true);
    }

    public void addOnButtonCheckedListener(OnButtonCheckedListener onButtonCheckedListener) {
        this.onButtonCheckedListeners.add(onButtonCheckedListener);
    }

    public void addView(View object, int n, ViewGroup.LayoutParams object2) {
        if (!(object instanceof MaterialButton)) {
            Log.e((String)LOG_TAG, (String)"Child views must be of type MaterialButton.");
            return;
        }
        super.addView((View)object, n, (ViewGroup.LayoutParams)object2);
        object = (MaterialButton)object;
        this.setGeneratedIdIfNeeded((MaterialButton)object);
        this.setupButtonChild((MaterialButton)object);
        if (((MaterialButton)object).isChecked()) {
            this.updateCheckedStates(object.getId(), true);
            this.setCheckedId(object.getId());
        }
        object2 = ((MaterialButton)object).getShapeAppearanceModel();
        this.originalCornerData.add(new CornerData(((ShapeAppearanceModel)object2).getTopLeftCornerSize(), ((ShapeAppearanceModel)object2).getBottomLeftCornerSize(), ((ShapeAppearanceModel)object2).getTopRightCornerSize(), ((ShapeAppearanceModel)object2).getBottomRightCornerSize()));
        ViewCompat.setAccessibilityDelegate((View)object, new AccessibilityDelegateCompat(){

            @Override
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, MaterialButtonToggleGroup.this.getIndexWithinVisibleButtons(view), 1, false, ((MaterialButton)view).isChecked()));
            }
        });
    }

    public void check(int n) {
        if (n == this.checkedId) {
            return;
        }
        this.checkForced(n);
    }

    public void clearChecked() {
        this.skipCheckedStateTracker = true;
        int n = 0;
        do {
            if (n >= this.getChildCount()) {
                this.skipCheckedStateTracker = false;
                this.setCheckedId(-1);
                return;
            }
            MaterialButton materialButton = this.getChildButton(n);
            materialButton.setChecked(false);
            this.dispatchOnButtonChecked(materialButton.getId(), false);
            ++n;
        } while (true);
    }

    public void clearOnButtonCheckedListeners() {
        this.onButtonCheckedListeners.clear();
    }

    protected void dispatchDraw(Canvas canvas) {
        this.updateChildOrder();
        super.dispatchDraw(canvas);
    }

    public CharSequence getAccessibilityClassName() {
        return MaterialButtonToggleGroup.class.getName();
    }

    public int getCheckedButtonId() {
        if (!this.singleSelection) return -1;
        return this.checkedId;
    }

    public List<Integer> getCheckedButtonIds() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int n = 0;
        while (n < this.getChildCount()) {
            MaterialButton materialButton = this.getChildButton(n);
            if (materialButton.isChecked()) {
                arrayList.add(materialButton.getId());
            }
            ++n;
        }
        return arrayList;
    }

    protected int getChildDrawingOrder(int n, int n2) {
        Integer[] arrinteger = this.childOrder;
        if (arrinteger != null) {
            if (n2 < arrinteger.length) return arrinteger[n2];
        }
        Log.w((String)LOG_TAG, (String)"Child order wasn't updated");
        return n2;
    }

    public boolean isSelectionRequired() {
        return this.selectionRequired;
    }

    public boolean isSingleSelection() {
        return this.singleSelection;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        int n = this.checkedId;
        if (n == -1) return;
        this.checkForced(n);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo object) {
        super.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo)object);
        object = AccessibilityNodeInfoCompat.wrap((AccessibilityNodeInfo)object);
        int n = this.getVisibleButtonCount();
        int n2 = this.isSingleSelection() ? 1 : 2;
        ((AccessibilityNodeInfoCompat)object).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, n, false, n2));
    }

    protected void onMeasure(int n, int n2) {
        this.updateChildShapes();
        this.adjustChildMarginsAndUpdateLayout();
        super.onMeasure(n, n2);
    }

    public void onViewRemoved(View view) {
        int n;
        super.onViewRemoved(view);
        if (view instanceof MaterialButton) {
            MaterialButton materialButton = (MaterialButton)view;
            materialButton.removeOnCheckedChangeListener(this.checkedStateTracker);
            materialButton.setOnPressedChangeListenerInternal(null);
        }
        if ((n = this.indexOfChild(view)) >= 0) {
            this.originalCornerData.remove(n);
        }
        this.updateChildShapes();
        this.adjustChildMarginsAndUpdateLayout();
    }

    public void removeOnButtonCheckedListener(OnButtonCheckedListener onButtonCheckedListener) {
        this.onButtonCheckedListeners.remove(onButtonCheckedListener);
    }

    public void setSelectionRequired(boolean bl) {
        this.selectionRequired = bl;
    }

    public void setSingleSelection(int n) {
        this.setSingleSelection(this.getResources().getBoolean(n));
    }

    public void setSingleSelection(boolean bl) {
        if (this.singleSelection == bl) return;
        this.singleSelection = bl;
        this.clearChecked();
    }

    public void uncheck(int n) {
        this.setCheckedStateForView(n, false);
        this.updateCheckedStates(n, false);
        this.checkedId = -1;
        this.dispatchOnButtonChecked(n, false);
    }

    void updateChildShapes() {
        int n = this.getChildCount();
        int n2 = this.getFirstVisibleChildIndex();
        int n3 = this.getLastVisibleChildIndex();
        int n4 = 0;
        while (n4 < n) {
            MaterialButton materialButton = this.getChildButton(n4);
            if (materialButton.getVisibility() != 8) {
                ShapeAppearanceModel.Builder builder = materialButton.getShapeAppearanceModel().toBuilder();
                MaterialButtonToggleGroup.updateBuilderWithCornerData(builder, this.getNewCornerData(n4, n2, n3));
                materialButton.setShapeAppearanceModel(builder.build());
            }
            ++n4;
        }
    }

    private class CheckedStateTracker
    implements MaterialButton.OnCheckedChangeListener {
        private CheckedStateTracker() {
        }

        @Override
        public void onCheckedChanged(MaterialButton materialButton, boolean bl) {
            if (MaterialButtonToggleGroup.this.skipCheckedStateTracker) {
                return;
            }
            if (MaterialButtonToggleGroup.this.singleSelection) {
                MaterialButtonToggleGroup materialButtonToggleGroup = MaterialButtonToggleGroup.this;
                int n = bl ? materialButton.getId() : -1;
                materialButtonToggleGroup.checkedId = n;
            }
            if (MaterialButtonToggleGroup.this.updateCheckedStates(materialButton.getId(), bl)) {
                MaterialButtonToggleGroup.this.dispatchOnButtonChecked(materialButton.getId(), materialButton.isChecked());
            }
            MaterialButtonToggleGroup.this.invalidate();
        }
    }

    private static class CornerData {
        private static final CornerSize noCorner = new AbsoluteCornerSize(0.0f);
        CornerSize bottomLeft;
        CornerSize bottomRight;
        CornerSize topLeft;
        CornerSize topRight;

        CornerData(CornerSize cornerSize, CornerSize cornerSize2, CornerSize cornerSize3, CornerSize cornerSize4) {
            this.topLeft = cornerSize;
            this.topRight = cornerSize3;
            this.bottomRight = cornerSize4;
            this.bottomLeft = cornerSize2;
        }

        public static CornerData bottom(CornerData cornerData) {
            CornerSize cornerSize = noCorner;
            return new CornerData(cornerSize, cornerData.bottomLeft, cornerSize, cornerData.bottomRight);
        }

        public static CornerData end(CornerData cornerData, View view) {
            if (!ViewUtils.isLayoutRtl(view)) return CornerData.right(cornerData);
            return CornerData.left(cornerData);
        }

        public static CornerData left(CornerData object) {
            CornerSize cornerSize = ((CornerData)object).topLeft;
            CornerSize cornerSize2 = ((CornerData)object).bottomLeft;
            object = noCorner;
            return new CornerData(cornerSize, cornerSize2, (CornerSize)object, (CornerSize)object);
        }

        public static CornerData right(CornerData cornerData) {
            CornerSize cornerSize = noCorner;
            return new CornerData(cornerSize, cornerSize, cornerData.topRight, cornerData.bottomRight);
        }

        public static CornerData start(CornerData cornerData, View view) {
            if (!ViewUtils.isLayoutRtl(view)) return CornerData.left(cornerData);
            return CornerData.right(cornerData);
        }

        public static CornerData top(CornerData cornerData) {
            CornerSize cornerSize = cornerData.topLeft;
            CornerSize cornerSize2 = noCorner;
            return new CornerData(cornerSize, cornerSize2, cornerData.topRight, cornerSize2);
        }
    }

    public static interface OnButtonCheckedListener {
        public void onButtonChecked(MaterialButtonToggleGroup var1, int var2, boolean var3);
    }

    private class PressedStateTracker
    implements MaterialButton.OnPressedChangeListener {
        private PressedStateTracker() {
        }

        @Override
        public void onPressedChanged(MaterialButton materialButton, boolean bl) {
            MaterialButtonToggleGroup.this.invalidate();
        }
    }

}

