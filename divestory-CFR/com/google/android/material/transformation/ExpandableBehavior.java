/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package com.google.android.material.transformation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.expandable.ExpandableWidget;
import java.util.List;

@Deprecated
public abstract class ExpandableBehavior
extends CoordinatorLayout.Behavior<View> {
    private static final int STATE_COLLAPSED = 2;
    private static final int STATE_EXPANDED = 1;
    private static final int STATE_UNINITIALIZED = 0;
    private int currentState = 0;

    public ExpandableBehavior() {
    }

    public ExpandableBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private boolean didStateChange(boolean bl) {
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            bl = bl2;
            if (this.currentState != 1) return bl;
            return true;
        }
        int n = this.currentState;
        if (n == 0) return true;
        bl = bl3;
        if (n != 2) return bl;
        return true;
    }

    public static <T extends ExpandableBehavior> T from(View object, Class<T> class_) {
        if (!((object = object.getLayoutParams()) instanceof CoordinatorLayout.LayoutParams)) throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        if (!((object = ((CoordinatorLayout.LayoutParams)((Object)object)).getBehavior()) instanceof ExpandableBehavior)) throw new IllegalArgumentException("The view is not associated with ExpandableBehavior");
        return (T)((ExpandableBehavior)class_.cast(object));
    }

    protected ExpandableWidget findExpandableWidget(CoordinatorLayout coordinatorLayout, View view) {
        List<View> list = coordinatorLayout.getDependencies(view);
        int n = list.size();
        int n2 = 0;
        while (n2 < n) {
            View view2 = list.get(n2);
            if (this.layoutDependsOn(coordinatorLayout, view, view2)) {
                return (ExpandableWidget)view2;
            }
            ++n2;
        }
        return null;
    }

    @Override
    public abstract boolean layoutDependsOn(CoordinatorLayout var1, View var2, View var3);

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout object, View view, View view2) {
        object = (ExpandableWidget)view2;
        if (!this.didStateChange(object.isExpanded())) return false;
        int n = object.isExpanded() ? 1 : 2;
        this.currentState = n;
        return this.onExpandedStateChange((View)object, view, object.isExpanded(), true);
    }

    protected abstract boolean onExpandedStateChange(View var1, View var2, boolean var3, boolean var4);

    @Override
    public boolean onLayoutChild(CoordinatorLayout object, final View view, final int n) {
        if (ViewCompat.isLaidOut(view)) return false;
        if ((object = this.findExpandableWidget((CoordinatorLayout)object, view)) == null) return false;
        if (!this.didStateChange(object.isExpanded())) return false;
        n = object.isExpanded() ? 1 : 2;
        this.currentState = n;
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener((ExpandableWidget)object){
            final /* synthetic */ ExpandableWidget val$dep;
            {
                this.val$dep = expandableWidget;
            }

            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
                if (ExpandableBehavior.this.currentState != n) return false;
                ExpandableBehavior expandableBehavior = ExpandableBehavior.this;
                ExpandableWidget expandableWidget = this.val$dep;
                expandableBehavior.onExpandedStateChange((View)expandableWidget, view, expandableWidget.isExpanded(), false);
                return false;
            }
        });
        return false;
    }

}

