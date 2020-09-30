/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package androidx.viewpager2.widget;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Locale;

final class PageTransformerAdapter
extends ViewPager2.OnPageChangeCallback {
    private final LinearLayoutManager mLayoutManager;
    private ViewPager2.PageTransformer mPageTransformer;

    PageTransformerAdapter(LinearLayoutManager linearLayoutManager) {
        this.mLayoutManager = linearLayoutManager;
    }

    ViewPager2.PageTransformer getPageTransformer() {
        return this.mPageTransformer;
    }

    @Override
    public void onPageScrollStateChanged(int n) {
    }

    @Override
    public void onPageScrolled(int n, float f, int n2) {
        if (this.mPageTransformer == null) {
            return;
        }
        float f2 = -f;
        n2 = 0;
        while (n2 < this.mLayoutManager.getChildCount()) {
            View view = this.mLayoutManager.getChildAt(n2);
            if (view == null) {
                throw new IllegalStateException(String.format(Locale.US, "LayoutManager returned a null child at pos %d/%d while transforming pages", n2, this.mLayoutManager.getChildCount()));
            }
            f = this.mLayoutManager.getPosition(view) - n;
            this.mPageTransformer.transformPage(view, f + f2);
            ++n2;
        }
    }

    @Override
    public void onPageSelected(int n) {
    }

    void setPageTransformer(ViewPager2.PageTransformer pageTransformer) {
        this.mPageTransformer = pageTransformer;
    }
}

