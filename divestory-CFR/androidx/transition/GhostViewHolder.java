/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.FrameLayout
 */
package androidx.transition;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.transition.GhostViewPort;
import androidx.transition.R;
import androidx.transition.ViewGroupUtils;
import java.util.ArrayList;

class GhostViewHolder
extends FrameLayout {
    private boolean mAttached;
    private ViewGroup mParent;

    GhostViewHolder(ViewGroup viewGroup) {
        super(viewGroup.getContext());
        this.setClipChildren(false);
        this.mParent = viewGroup;
        viewGroup.setTag(R.id.ghost_view_holder, (Object)this);
        ViewGroupUtils.getOverlay(this.mParent).add((View)this);
        this.mAttached = true;
    }

    static GhostViewHolder getHolder(ViewGroup viewGroup) {
        return (GhostViewHolder)((Object)viewGroup.getTag(R.id.ghost_view_holder));
    }

    private int getInsertIndex(ArrayList<View> arrayList) {
        ArrayList<View> arrayList2 = new ArrayList<View>();
        int n = this.getChildCount() - 1;
        int n2 = 0;
        while (n2 <= n) {
            int n3 = (n2 + n) / 2;
            GhostViewHolder.getParents(((GhostViewPort)this.getChildAt((int)n3)).mView, arrayList2);
            if (GhostViewHolder.isOnTop(arrayList, arrayList2)) {
                n2 = n3 + 1;
            } else {
                n = n3 - 1;
            }
            arrayList2.clear();
        }
        return n2;
    }

    private static void getParents(View view, ArrayList<View> arrayList) {
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof ViewGroup) {
            GhostViewHolder.getParents((View)viewParent, arrayList);
        }
        arrayList.add(view);
    }

    private static boolean isOnTop(View view, View view2) {
        ViewGroup viewGroup = (ViewGroup)view.getParent();
        int n = viewGroup.getChildCount();
        int n2 = Build.VERSION.SDK_INT;
        boolean bl = false;
        boolean bl2 = false;
        if (n2 >= 21 && view.getZ() != view2.getZ()) {
            if (!(view.getZ() > view2.getZ())) return bl2;
            return true;
        }
        n2 = 0;
        while (n2 < n) {
            View view3 = viewGroup.getChildAt(ViewGroupUtils.getChildDrawingOrder(viewGroup, n2));
            if (view3 == view) {
                return bl;
            }
            if (view3 == view2) return true;
            ++n2;
        }
        return true;
    }

    private static boolean isOnTop(ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        boolean bl;
        boolean bl2 = arrayList.isEmpty();
        boolean bl3 = bl = true;
        if (bl2) return bl3;
        bl3 = bl;
        if (arrayList2.isEmpty()) return bl3;
        if (arrayList.get(0) != arrayList2.get(0)) {
            return bl;
        }
        int n = Math.min(arrayList.size(), arrayList2.size());
        for (int i = 1; i < n; ++i) {
            View view;
            View view2 = arrayList.get(i);
            if (view2 == (view = arrayList2.get(i))) continue;
            return GhostViewHolder.isOnTop(view2, view);
        }
        if (arrayList2.size() != n) return false;
        return bl;
    }

    void addGhostView(GhostViewPort ghostViewPort) {
        ArrayList<View> arrayList = new ArrayList<View>();
        GhostViewHolder.getParents(ghostViewPort.mView, arrayList);
        int n = this.getInsertIndex(arrayList);
        if (n >= 0 && n < this.getChildCount()) {
            this.addView((View)ghostViewPort, n);
            return;
        }
        this.addView((View)ghostViewPort);
    }

    public void onViewAdded(View view) {
        if (!this.mAttached) throw new IllegalStateException("This GhostViewHolder is detached!");
        super.onViewAdded(view);
    }

    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if (this.getChildCount() != 1 || this.getChildAt(0) != view) {
            if (this.getChildCount() != 0) return;
        }
        this.mParent.setTag(R.id.ghost_view_holder, null);
        ViewGroupUtils.getOverlay(this.mParent).remove((View)this);
        this.mAttached = false;
    }

    void popToOverlayTop() {
        if (!this.mAttached) throw new IllegalStateException("This GhostViewHolder is detached!");
        ViewGroupUtils.getOverlay(this.mParent).remove((View)this);
        ViewGroupUtils.getOverlay(this.mParent).add((View)this);
    }
}

