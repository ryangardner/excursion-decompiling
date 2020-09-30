/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.viewpager2.widget;

import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

final class CompositeOnPageChangeCallback
extends ViewPager2.OnPageChangeCallback {
    private final List<ViewPager2.OnPageChangeCallback> mCallbacks;

    CompositeOnPageChangeCallback(int n) {
        this.mCallbacks = new ArrayList<ViewPager2.OnPageChangeCallback>(n);
    }

    private void throwCallbackListModifiedWhileInUse(ConcurrentModificationException concurrentModificationException) {
        throw new IllegalStateException("Adding and removing callbacks during dispatch to callbacks is not supported", concurrentModificationException);
    }

    void addOnPageChangeCallback(ViewPager2.OnPageChangeCallback onPageChangeCallback) {
        this.mCallbacks.add(onPageChangeCallback);
    }

    @Override
    public void onPageScrollStateChanged(int n) {
        try {
            Iterator<ViewPager2.OnPageChangeCallback> iterator2 = this.mCallbacks.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().onPageScrollStateChanged(n);
            }
            return;
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            this.throwCallbackListModifiedWhileInUse(concurrentModificationException);
        }
    }

    @Override
    public void onPageScrolled(int n, float f, int n2) {
        try {
            Iterator<ViewPager2.OnPageChangeCallback> iterator2 = this.mCallbacks.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().onPageScrolled(n, f, n2);
            }
            return;
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            this.throwCallbackListModifiedWhileInUse(concurrentModificationException);
        }
    }

    @Override
    public void onPageSelected(int n) {
        try {
            Iterator<ViewPager2.OnPageChangeCallback> iterator2 = this.mCallbacks.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().onPageSelected(n);
            }
            return;
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            this.throwCallbackListModifiedWhileInUse(concurrentModificationException);
        }
    }

    void removeOnPageChangeCallback(ViewPager2.OnPageChangeCallback onPageChangeCallback) {
        this.mCallbacks.remove(onPageChangeCallback);
    }
}

