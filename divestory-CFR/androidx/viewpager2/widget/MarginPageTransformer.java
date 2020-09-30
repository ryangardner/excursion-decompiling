/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewParent
 */
package androidx.viewpager2.widget;

import android.view.View;
import android.view.ViewParent;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public final class MarginPageTransformer
implements ViewPager2.PageTransformer {
    private final int mMarginPx;

    public MarginPageTransformer(int n) {
        Preconditions.checkArgumentNonnegative(n, "Margin must be non-negative");
        this.mMarginPx = n;
    }

    private ViewPager2 requireViewPager(View view) {
        ViewParent viewParent = view.getParent();
        view = viewParent.getParent();
        if (!(viewParent instanceof RecyclerView)) throw new IllegalStateException("Expected the page view to be managed by a ViewPager2 instance.");
        if (!(view instanceof ViewPager2)) throw new IllegalStateException("Expected the page view to be managed by a ViewPager2 instance.");
        return (ViewPager2)view;
    }

    @Override
    public void transformPage(View view, float f) {
        ViewPager2 viewPager2 = this.requireViewPager(view);
        float f2 = (float)this.mMarginPx * f;
        if (viewPager2.getOrientation() != 0) {
            view.setTranslationY(f2);
            return;
        }
        f = f2;
        if (viewPager2.isRtl()) {
            f = -f2;
        }
        view.setTranslationX(f);
    }
}

