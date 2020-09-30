/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.LayoutTransition
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package androidx.viewpager2.widget;

import android.animation.LayoutTransition;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.Arrays;
import java.util.Comparator;

final class AnimateLayoutChangeDetector {
    private static final ViewGroup.MarginLayoutParams ZERO_MARGIN_LAYOUT_PARAMS;
    private LinearLayoutManager mLayoutManager;

    static {
        ViewGroup.MarginLayoutParams marginLayoutParams;
        ZERO_MARGIN_LAYOUT_PARAMS = marginLayoutParams = new ViewGroup.MarginLayoutParams(-1, -1);
        marginLayoutParams.setMargins(0, 0, 0, 0);
    }

    AnimateLayoutChangeDetector(LinearLayoutManager linearLayoutManager) {
        this.mLayoutManager = linearLayoutManager;
    }

    private boolean arePagesLaidOutContiguously() {
        int n;
        int n2 = this.mLayoutManager.getChildCount();
        if (n2 == 0) {
            return true;
        }
        int n3 = this.mLayoutManager.getOrientation() == 0 ? 1 : 0;
        int[][] arrn = new int[n2][2];
        for (n = 0; n < n2; ++n) {
            int n4;
            int n5;
            View view = this.mLayoutManager.getChildAt(n);
            if (view == null) throw new IllegalStateException("null view contained in the view hierarchy");
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams)layoutParams : ZERO_MARGIN_LAYOUT_PARAMS;
            int[] arrn2 = arrn[n];
            if (n3 != 0) {
                n5 = view.getLeft();
                n4 = layoutParams.leftMargin;
            } else {
                n5 = view.getTop();
                n4 = layoutParams.topMargin;
            }
            arrn2[0] = n5 - n4;
            arrn2 = arrn[n];
            if (n3 != 0) {
                n4 = view.getRight();
                n5 = layoutParams.rightMargin;
            } else {
                n4 = view.getBottom();
                n5 = layoutParams.bottomMargin;
            }
            arrn2[1] = n4 + n5;
        }
        Arrays.sort(arrn, new Comparator<int[]>(){

            @Override
            public int compare(int[] arrn, int[] arrn2) {
                return arrn[0] - arrn2[0];
            }
        });
        n3 = 1;
        do {
            if (n3 >= n2) {
                n = arrn[0][1];
                n3 = arrn[0][0];
                if (arrn[0][0] > 0) return false;
                if (arrn[n2 - 1][1] >= n - n3) return true;
                return false;
            }
            if (arrn[n3 - 1][1] != arrn[n3][0]) {
                return false;
            }
            ++n3;
        } while (true);
    }

    private boolean hasRunningChangingLayoutTransition() {
        int n = this.mLayoutManager.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            if (AnimateLayoutChangeDetector.hasRunningChangingLayoutTransition(this.mLayoutManager.getChildAt(n2))) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private static boolean hasRunningChangingLayoutTransition(View view) {
        if (!(view instanceof ViewGroup)) return false;
        ViewGroup viewGroup = (ViewGroup)view;
        view = viewGroup.getLayoutTransition();
        if (view != null && view.isChangingLayout()) {
            return true;
        }
        int n = viewGroup.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            if (AnimateLayoutChangeDetector.hasRunningChangingLayoutTransition(viewGroup.getChildAt(n2))) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    boolean mayHaveInterferingAnimations() {
        boolean bl = this.arePagesLaidOutContiguously();
        boolean bl2 = true;
        if (bl) {
            if (this.mLayoutManager.getChildCount() > 1) return false;
        }
        if (!this.hasRunningChangingLayoutTransition()) return false;
        return bl2;
    }

}

