/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.fragment.app;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public abstract class FragmentStatePagerAdapter
extends PagerAdapter {
    public static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1;
    @Deprecated
    public static final int BEHAVIOR_SET_USER_VISIBLE_HINT = 0;
    private static final boolean DEBUG = false;
    private static final String TAG = "FragmentStatePagerAdapt";
    private final int mBehavior;
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private final FragmentManager mFragmentManager;
    private ArrayList<Fragment> mFragments = new ArrayList();
    private ArrayList<Fragment.SavedState> mSavedState = new ArrayList();

    @Deprecated
    public FragmentStatePagerAdapter(FragmentManager fragmentManager) {
        this(fragmentManager, 0);
    }

    public FragmentStatePagerAdapter(FragmentManager fragmentManager, int n) {
        this.mFragmentManager = fragmentManager;
        this.mBehavior = n;
    }

    @Override
    public void destroyItem(ViewGroup object, int n, Object object2) {
        object2 = (Fragment)object2;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        while (this.mSavedState.size() <= n) {
            this.mSavedState.add(null);
        }
        ArrayList<Fragment.SavedState> arrayList = this.mSavedState;
        object = ((Fragment)object2).isAdded() ? this.mFragmentManager.saveFragmentInstanceState((Fragment)object2) : null;
        arrayList.set(n, (Fragment.SavedState)object);
        this.mFragments.set(n, null);
        this.mCurTransaction.remove((Fragment)object2);
        if (!((Fragment)object2).equals(this.mCurrentPrimaryItem)) return;
        this.mCurrentPrimaryItem = null;
    }

    @Override
    public void finishUpdate(ViewGroup object) {
        object = this.mCurTransaction;
        if (object == null) return;
        try {
            ((FragmentTransaction)object).commitNowAllowingStateLoss();
        }
        catch (IllegalStateException illegalStateException) {
            this.mCurTransaction.commitAllowingStateLoss();
        }
        this.mCurTransaction = null;
    }

    public abstract Fragment getItem(int var1);

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int n) {
        Fragment.SavedState savedState;
        Fragment fragment;
        if (this.mFragments.size() > n && (fragment = this.mFragments.get(n)) != null) {
            return fragment;
        }
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        fragment = this.getItem(n);
        if (this.mSavedState.size() > n && (savedState = this.mSavedState.get(n)) != null) {
            fragment.setInitialSavedState(savedState);
        }
        while (this.mFragments.size() <= n) {
            this.mFragments.add(null);
        }
        fragment.setMenuVisibility(false);
        if (this.mBehavior == 0) {
            fragment.setUserVisibleHint(false);
        }
        this.mFragments.set(n, fragment);
        this.mCurTransaction.add(viewGroup.getId(), fragment);
        if (this.mBehavior != 1) return fragment;
        this.mCurTransaction.setMaxLifecycle(fragment, Lifecycle.State.STARTED);
        return fragment;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (((Fragment)object).getView() != view) return false;
        return true;
    }

    @Override
    public void restoreState(Parcelable parcelable, ClassLoader object) {
        int n;
        if (parcelable == null) return;
        parcelable = (Bundle)parcelable;
        parcelable.setClassLoader((ClassLoader)object);
        object = parcelable.getParcelableArray("states");
        this.mSavedState.clear();
        this.mFragments.clear();
        if (object != null) {
            for (n = 0; n < ((Parcelable[])object).length; ++n) {
                this.mSavedState.add((Fragment.SavedState)object[n]);
            }
        }
        object = parcelable.keySet().iterator();
        while (object.hasNext()) {
            String string2 = (String)object.next();
            if (!string2.startsWith("f")) continue;
            n = Integer.parseInt(string2.substring(1));
            Object object2 = this.mFragmentManager.getFragment((Bundle)parcelable, string2);
            if (object2 != null) {
                while (this.mFragments.size() <= n) {
                    this.mFragments.add(null);
                }
                ((Fragment)object2).setMenuVisibility(false);
                this.mFragments.set(n, (Fragment)object2);
                continue;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Bad fragment at key ");
            ((StringBuilder)object2).append(string2);
            Log.w((String)"FragmentStatePagerAdapt", (String)((StringBuilder)object2).toString());
        }
    }

    @Override
    public Parcelable saveState() {
        Object object;
        Bundle bundle;
        if (this.mSavedState.size() > 0) {
            object = new Bundle();
            bundle = new Fragment.SavedState[this.mSavedState.size()];
            this.mSavedState.toArray((T[])bundle);
            object.putParcelableArray("states", (Parcelable[])bundle);
        } else {
            object = null;
        }
        int n = 0;
        while (n < this.mFragments.size()) {
            Fragment fragment = this.mFragments.get(n);
            bundle = object;
            if (fragment != null) {
                bundle = object;
                if (fragment.isAdded()) {
                    bundle = object;
                    if (object == null) {
                        bundle = new Bundle();
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("f");
                    ((StringBuilder)object).append(n);
                    object = ((StringBuilder)object).toString();
                    this.mFragmentManager.putFragment(bundle, (String)object, fragment);
                }
            }
            ++n;
            object = bundle;
        }
        return object;
    }

    @Override
    public void setPrimaryItem(ViewGroup object, int n, Object object2) {
        object = this.mCurrentPrimaryItem;
        if ((object2 = (Fragment)object2) == object) return;
        if (object != null) {
            ((Fragment)object).setMenuVisibility(false);
            if (this.mBehavior == 1) {
                if (this.mCurTransaction == null) {
                    this.mCurTransaction = this.mFragmentManager.beginTransaction();
                }
                this.mCurTransaction.setMaxLifecycle(this.mCurrentPrimaryItem, Lifecycle.State.STARTED);
            } else {
                this.mCurrentPrimaryItem.setUserVisibleHint(false);
            }
        }
        ((Fragment)object2).setMenuVisibility(true);
        if (this.mBehavior == 1) {
            if (this.mCurTransaction == null) {
                this.mCurTransaction = this.mFragmentManager.beginTransaction();
            }
            this.mCurTransaction.setMaxLifecycle((Fragment)object2, Lifecycle.State.RESUMED);
        } else {
            ((Fragment)object2).setUserVisibleHint(true);
        }
        this.mCurrentPrimaryItem = object2;
    }

    @Override
    public void startUpdate(ViewGroup object) {
        if (object.getId() != -1) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("ViewPager with adapter ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" requires a view id");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }
}

