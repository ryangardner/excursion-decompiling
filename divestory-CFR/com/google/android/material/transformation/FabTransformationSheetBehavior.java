/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package com.google.android.material.transformation;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.Positioning;
import com.google.android.material.transformation.FabTransformationBehavior;
import com.google.android.material.transformation.FabTransformationScrimBehavior;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class FabTransformationSheetBehavior
extends FabTransformationBehavior {
    private Map<View, Integer> importantForAccessibilityMap;

    public FabTransformationSheetBehavior() {
    }

    public FabTransformationSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void updateImportantForAccessibility(View view, boolean bl) {
        ViewParent viewParent = view.getParent();
        if (!(viewParent instanceof CoordinatorLayout)) {
            return;
        }
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout)viewParent;
        int n = coordinatorLayout.getChildCount();
        if (Build.VERSION.SDK_INT >= 16 && bl) {
            this.importantForAccessibilityMap = new HashMap<View, Integer>(n);
        }
        int n2 = 0;
        do {
            if (n2 >= n) {
                if (bl) return;
                this.importantForAccessibilityMap = null;
                return;
            }
            viewParent = coordinatorLayout.getChildAt(n2);
            boolean bl2 = viewParent.getLayoutParams() instanceof CoordinatorLayout.LayoutParams && ((CoordinatorLayout.LayoutParams)viewParent.getLayoutParams()).getBehavior() instanceof FabTransformationScrimBehavior;
            if (viewParent != view && !bl2) {
                if (!bl) {
                    Map<View, Integer> map = this.importantForAccessibilityMap;
                    if (map != null && map.containsKey((Object)viewParent)) {
                        ViewCompat.setImportantForAccessibility((View)viewParent, this.importantForAccessibilityMap.get((Object)viewParent));
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= 16) {
                        this.importantForAccessibilityMap.put((View)viewParent, viewParent.getImportantForAccessibility());
                    }
                    ViewCompat.setImportantForAccessibility((View)viewParent, 4);
                }
            }
            ++n2;
        } while (true);
    }

    @Override
    protected FabTransformationBehavior.FabTransformationSpec onCreateMotionSpec(Context context, boolean bl) {
        int n = bl ? R.animator.mtrl_fab_transformation_sheet_expand_spec : R.animator.mtrl_fab_transformation_sheet_collapse_spec;
        FabTransformationBehavior.FabTransformationSpec fabTransformationSpec = new FabTransformationBehavior.FabTransformationSpec();
        fabTransformationSpec.timings = MotionSpec.createFromResource(context, n);
        fabTransformationSpec.positioning = new Positioning(17, 0.0f, 0.0f);
        return fabTransformationSpec;
    }

    @Override
    protected boolean onExpandedStateChange(View view, View view2, boolean bl, boolean bl2) {
        this.updateImportantForAccessibility(view2, bl);
        return super.onExpandedStateChange(view, view2, bl, bl2);
    }
}

