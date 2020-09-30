/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.View
 *  android.view.ViewParent
 */
package com.google.android.material.expandable;

import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.expandable.ExpandableWidget;

public final class ExpandableWidgetHelper {
    private boolean expanded = false;
    private int expandedComponentIdHint = 0;
    private final View widget;

    public ExpandableWidgetHelper(ExpandableWidget expandableWidget) {
        this.widget = (View)expandableWidget;
    }

    private void dispatchExpandedStateChanged() {
        ViewParent viewParent = this.widget.getParent();
        if (!(viewParent instanceof CoordinatorLayout)) return;
        ((CoordinatorLayout)viewParent).dispatchDependentViewsChanged(this.widget);
    }

    public int getExpandedComponentIdHint() {
        return this.expandedComponentIdHint;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        this.expanded = bundle.getBoolean("expanded", false);
        this.expandedComponentIdHint = bundle.getInt("expandedComponentIdHint", 0);
        if (!this.expanded) return;
        this.dispatchExpandedStateChanged();
    }

    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("expanded", this.expanded);
        bundle.putInt("expandedComponentIdHint", this.expandedComponentIdHint);
        return bundle;
    }

    public boolean setExpanded(boolean bl) {
        if (this.expanded == bl) return false;
        this.expanded = bl;
        this.dispatchExpandedStateChanged();
        return true;
    }

    public void setExpandedComponentIdHint(int n) {
        this.expandedComponentIdHint = n;
    }
}

