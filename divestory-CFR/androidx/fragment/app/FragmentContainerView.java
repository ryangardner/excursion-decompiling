/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.LayoutTransition
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.WindowInsets
 *  android.view.animation.Animation
 *  android.widget.FrameLayout
 */
package androidx.fragment.app;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import androidx.fragment.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;

public final class FragmentContainerView
extends FrameLayout {
    private ArrayList<View> mDisappearingFragmentChildren;
    private boolean mDrawDisappearingViewsFirst = true;
    private ArrayList<View> mTransitioningFragmentViews;

    public FragmentContainerView(Context context) {
        super(context);
    }

    public FragmentContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        throw new UnsupportedOperationException("FragmentContainerView must be within a FragmentActivity to be instantiated from XML.");
    }

    public FragmentContainerView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        throw new UnsupportedOperationException("FragmentContainerView must be within a FragmentActivity to be instantiated from XML.");
    }

    FragmentContainerView(Context object, AttributeSet object2, FragmentManager fragmentManager) {
        super((Context)object, (AttributeSet)object2);
        String string2 = object2.getClassAttribute();
        Object object3 = object.obtainStyledAttributes((AttributeSet)object2, R.styleable.FragmentContainerView);
        Object object4 = string2;
        if (string2 == null) {
            object4 = object3.getString(R.styleable.FragmentContainerView_android_name);
        }
        string2 = object3.getString(R.styleable.FragmentContainerView_android_tag);
        object3.recycle();
        int n = this.getId();
        object3 = fragmentManager.findFragmentById(n);
        if (object4 == null) return;
        if (object3 != null) return;
        if (n > 0) {
            object4 = fragmentManager.getFragmentFactory().instantiate(object.getClassLoader(), (String)object4);
            ((Fragment)object4).onInflate((Context)object, (AttributeSet)object2, null);
            fragmentManager.beginTransaction().setReorderingAllowed(true).add((ViewGroup)this, (Fragment)object4, string2).commitNowAllowingStateLoss();
            return;
        }
        if (string2 != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" with tag ");
            ((StringBuilder)object).append(string2);
            object = ((StringBuilder)object).toString();
        } else {
            object = "";
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("FragmentContainerView must have an android:id to add Fragment ");
        ((StringBuilder)object2).append((String)object4);
        ((StringBuilder)object2).append((String)object);
        throw new IllegalStateException(((StringBuilder)object2).toString());
    }

    private void addDisappearingFragmentView(View view) {
        if (view.getAnimation() == null) {
            ArrayList<View> arrayList = this.mTransitioningFragmentViews;
            if (arrayList == null) return;
            if (!arrayList.contains((Object)view)) return;
        }
        if (this.mDisappearingFragmentChildren == null) {
            this.mDisappearingFragmentChildren = new ArrayList();
        }
        this.mDisappearingFragmentChildren.add(view);
    }

    public void addView(View view, int n, ViewGroup.LayoutParams object) {
        if (FragmentManager.getViewFragment(view) != null) {
            super.addView(view, n, (ViewGroup.LayoutParams)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Views added to a FragmentContainerView must be associated with a Fragment. View ");
        ((StringBuilder)object).append((Object)view);
        ((StringBuilder)object).append(" is not associated with a Fragment.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    protected boolean addViewInLayout(View view, int n, ViewGroup.LayoutParams object, boolean bl) {
        if (FragmentManager.getViewFragment(view) != null) {
            return super.addViewInLayout(view, n, (ViewGroup.LayoutParams)object, bl);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Views added to a FragmentContainerView must be associated with a Fragment. View ");
        ((StringBuilder)object).append((Object)view);
        ((StringBuilder)object).append(" is not associated with a Fragment.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    protected void dispatchDraw(Canvas canvas) {
        if (this.mDrawDisappearingViewsFirst && this.mDisappearingFragmentChildren != null) {
            for (int i = 0; i < this.mDisappearingFragmentChildren.size(); ++i) {
                super.drawChild(canvas, this.mDisappearingFragmentChildren.get(i), this.getDrawingTime());
            }
        }
        super.dispatchDraw(canvas);
    }

    protected boolean drawChild(Canvas canvas, View view, long l) {
        if (!this.mDrawDisappearingViewsFirst) return super.drawChild(canvas, view, l);
        ArrayList<View> arrayList = this.mDisappearingFragmentChildren;
        if (arrayList == null) return super.drawChild(canvas, view, l);
        if (arrayList.size() <= 0) return super.drawChild(canvas, view, l);
        if (!this.mDisappearingFragmentChildren.contains((Object)view)) return super.drawChild(canvas, view, l);
        return false;
    }

    public void endViewTransition(View view) {
        ArrayList<View> arrayList = this.mTransitioningFragmentViews;
        if (arrayList != null) {
            arrayList.remove((Object)view);
            arrayList = this.mDisappearingFragmentChildren;
            if (arrayList != null && arrayList.remove((Object)view)) {
                this.mDrawDisappearingViewsFirst = true;
            }
        }
        super.endViewTransition(view);
    }

    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        int n = 0;
        while (n < this.getChildCount()) {
            this.getChildAt(n).dispatchApplyWindowInsets(new WindowInsets(windowInsets));
            ++n;
        }
        return windowInsets;
    }

    public void removeAllViewsInLayout() {
        int n = this.getChildCount() - 1;
        do {
            if (n < 0) {
                super.removeAllViewsInLayout();
                return;
            }
            this.addDisappearingFragmentView(this.getChildAt(n));
            --n;
        } while (true);
    }

    protected void removeDetachedView(View view, boolean bl) {
        if (bl) {
            this.addDisappearingFragmentView(view);
        }
        super.removeDetachedView(view, bl);
    }

    public void removeView(View view) {
        this.addDisappearingFragmentView(view);
        super.removeView(view);
    }

    public void removeViewAt(int n) {
        this.addDisappearingFragmentView(this.getChildAt(n));
        super.removeViewAt(n);
    }

    public void removeViewInLayout(View view) {
        this.addDisappearingFragmentView(view);
        super.removeViewInLayout(view);
    }

    public void removeViews(int n, int n2) {
        int n3 = n;
        do {
            if (n3 >= n + n2) {
                super.removeViews(n, n2);
                return;
            }
            this.addDisappearingFragmentView(this.getChildAt(n3));
            ++n3;
        } while (true);
    }

    public void removeViewsInLayout(int n, int n2) {
        int n3 = n;
        do {
            if (n3 >= n + n2) {
                super.removeViewsInLayout(n, n2);
                return;
            }
            this.addDisappearingFragmentView(this.getChildAt(n3));
            ++n3;
        } while (true);
    }

    void setDrawDisappearingViewsLast(boolean bl) {
        this.mDrawDisappearingViewsFirst = bl;
    }

    public void setLayoutTransition(LayoutTransition layoutTransition) {
        if (Build.VERSION.SDK_INT >= 18) throw new UnsupportedOperationException("FragmentContainerView does not support Layout Transitions or animateLayoutChanges=\"true\".");
        super.setLayoutTransition(layoutTransition);
    }

    public void startViewTransition(View view) {
        if (view.getParent() == this) {
            if (this.mTransitioningFragmentViews == null) {
                this.mTransitioningFragmentViews = new ArrayList();
            }
            this.mTransitioningFragmentViews.add(view);
        }
        super.startViewTransition(view);
    }
}

