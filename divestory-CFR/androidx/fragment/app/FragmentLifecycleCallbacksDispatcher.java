/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 */
package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

class FragmentLifecycleCallbacksDispatcher {
    private final FragmentManager mFragmentManager;
    private final CopyOnWriteArrayList<FragmentLifecycleCallbacksHolder> mLifecycleCallbacks = new CopyOnWriteArrayList();

    FragmentLifecycleCallbacksDispatcher(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    void dispatchOnFragmentActivityCreated(Fragment fragment, Bundle bundle, boolean bl) {
        Object object = this.mFragmentManager.getParent();
        if (object != null) {
            ((Fragment)object).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentActivityCreated(fragment, bundle, true);
        }
        object = this.mLifecycleCallbacks.iterator();
        while (object.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)object.next();
            if (bl && !fragmentLifecycleCallbacksHolder.mRecursive) continue;
            fragmentLifecycleCallbacksHolder.mCallback.onFragmentActivityCreated(this.mFragmentManager, fragment, bundle);
        }
    }

    void dispatchOnFragmentAttached(Fragment fragment, Context context, boolean bl) {
        Object object = this.mFragmentManager.getParent();
        if (object != null) {
            ((Fragment)object).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentAttached(fragment, context, true);
        }
        object = this.mLifecycleCallbacks.iterator();
        while (object.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)object.next();
            if (bl && !fragmentLifecycleCallbacksHolder.mRecursive) continue;
            fragmentLifecycleCallbacksHolder.mCallback.onFragmentAttached(this.mFragmentManager, fragment, context);
        }
    }

    void dispatchOnFragmentCreated(Fragment fragment, Bundle bundle, boolean bl) {
        Object object = this.mFragmentManager.getParent();
        if (object != null) {
            ((Fragment)object).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentCreated(fragment, bundle, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> iterator2 = this.mLifecycleCallbacks.iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            if (bl && !((FragmentLifecycleCallbacksHolder)object).mRecursive) continue;
            ((FragmentLifecycleCallbacksHolder)object).mCallback.onFragmentCreated(this.mFragmentManager, fragment, bundle);
        }
    }

    void dispatchOnFragmentDestroyed(Fragment fragment, boolean bl) {
        Object object = this.mFragmentManager.getParent();
        if (object != null) {
            ((Fragment)object).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentDestroyed(fragment, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> iterator2 = this.mLifecycleCallbacks.iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            if (bl && !((FragmentLifecycleCallbacksHolder)object).mRecursive) continue;
            ((FragmentLifecycleCallbacksHolder)object).mCallback.onFragmentDestroyed(this.mFragmentManager, fragment);
        }
    }

    void dispatchOnFragmentDetached(Fragment fragment, boolean bl) {
        Object object = this.mFragmentManager.getParent();
        if (object != null) {
            ((Fragment)object).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentDetached(fragment, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> iterator2 = this.mLifecycleCallbacks.iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            if (bl && !((FragmentLifecycleCallbacksHolder)object).mRecursive) continue;
            ((FragmentLifecycleCallbacksHolder)object).mCallback.onFragmentDetached(this.mFragmentManager, fragment);
        }
    }

    void dispatchOnFragmentPaused(Fragment fragment, boolean bl) {
        Object object = this.mFragmentManager.getParent();
        if (object != null) {
            ((Fragment)object).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentPaused(fragment, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> iterator2 = this.mLifecycleCallbacks.iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            if (bl && !((FragmentLifecycleCallbacksHolder)object).mRecursive) continue;
            ((FragmentLifecycleCallbacksHolder)object).mCallback.onFragmentPaused(this.mFragmentManager, fragment);
        }
    }

    void dispatchOnFragmentPreAttached(Fragment fragment, Context context, boolean bl) {
        Object object = this.mFragmentManager.getParent();
        if (object != null) {
            ((Fragment)object).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentPreAttached(fragment, context, true);
        }
        object = this.mLifecycleCallbacks.iterator();
        while (object.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)object.next();
            if (bl && !fragmentLifecycleCallbacksHolder.mRecursive) continue;
            fragmentLifecycleCallbacksHolder.mCallback.onFragmentPreAttached(this.mFragmentManager, fragment, context);
        }
    }

    void dispatchOnFragmentPreCreated(Fragment fragment, Bundle bundle, boolean bl) {
        Object object = this.mFragmentManager.getParent();
        if (object != null) {
            ((Fragment)object).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentPreCreated(fragment, bundle, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> iterator2 = this.mLifecycleCallbacks.iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            if (bl && !((FragmentLifecycleCallbacksHolder)object).mRecursive) continue;
            ((FragmentLifecycleCallbacksHolder)object).mCallback.onFragmentPreCreated(this.mFragmentManager, fragment, bundle);
        }
    }

    void dispatchOnFragmentResumed(Fragment fragment, boolean bl) {
        Object object = this.mFragmentManager.getParent();
        if (object != null) {
            ((Fragment)object).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentResumed(fragment, true);
        }
        Iterator<FragmentLifecycleCallbacksHolder> iterator2 = this.mLifecycleCallbacks.iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            if (bl && !((FragmentLifecycleCallbacksHolder)object).mRecursive) continue;
            ((FragmentLifecycleCallbacksHolder)object).mCallback.onFragmentResumed(this.mFragmentManager, fragment);
        }
    }

    void dispatchOnFragmentSaveInstanceState(Fragment fragment, Bundle bundle, boolean bl) {
        Object object = this.mFragmentManager.getParent();
        if (object != null) {
            ((Fragment)object).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentSaveInstanceState(fragment, bundle, true);
        }
        object = this.mLifecycleCallbacks.iterator();
        while (object.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)object.next();
            if (bl && !fragmentLifecycleCallbacksHolder.mRecursive) continue;
            fragmentLifecycleCallbacksHolder.mCallback.onFragmentSaveInstanceState(this.mFragmentManager, fragment, bundle);
        }
    }

    void dispatchOnFragmentStarted(Fragment fragment, boolean bl) {
        Object object = this.mFragmentManager.getParent();
        if (object != null) {
            ((Fragment)object).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentStarted(fragment, true);
        }
        object = this.mLifecycleCallbacks.iterator();
        while (object.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)object.next();
            if (bl && !fragmentLifecycleCallbacksHolder.mRecursive) continue;
            fragmentLifecycleCallbacksHolder.mCallback.onFragmentStarted(this.mFragmentManager, fragment);
        }
    }

    void dispatchOnFragmentStopped(Fragment fragment, boolean bl) {
        Object object = this.mFragmentManager.getParent();
        if (object != null) {
            ((Fragment)object).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentStopped(fragment, true);
        }
        object = this.mLifecycleCallbacks.iterator();
        while (object.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)object.next();
            if (bl && !fragmentLifecycleCallbacksHolder.mRecursive) continue;
            fragmentLifecycleCallbacksHolder.mCallback.onFragmentStopped(this.mFragmentManager, fragment);
        }
    }

    void dispatchOnFragmentViewCreated(Fragment fragment, View view, Bundle bundle, boolean bl) {
        Object object = this.mFragmentManager.getParent();
        if (object != null) {
            ((Fragment)object).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentViewCreated(fragment, view, bundle, true);
        }
        object = this.mLifecycleCallbacks.iterator();
        while (object.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)object.next();
            if (bl && !fragmentLifecycleCallbacksHolder.mRecursive) continue;
            fragmentLifecycleCallbacksHolder.mCallback.onFragmentViewCreated(this.mFragmentManager, fragment, view, bundle);
        }
    }

    void dispatchOnFragmentViewDestroyed(Fragment fragment, boolean bl) {
        Object object = this.mFragmentManager.getParent();
        if (object != null) {
            ((Fragment)object).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentViewDestroyed(fragment, true);
        }
        object = this.mLifecycleCallbacks.iterator();
        while (object.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)object.next();
            if (bl && !fragmentLifecycleCallbacksHolder.mRecursive) continue;
            fragmentLifecycleCallbacksHolder.mCallback.onFragmentViewDestroyed(this.mFragmentManager, fragment);
        }
    }

    public void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean bl) {
        this.mLifecycleCallbacks.add(new FragmentLifecycleCallbacksHolder(fragmentLifecycleCallbacks, bl));
    }

    public void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        CopyOnWriteArrayList<FragmentLifecycleCallbacksHolder> copyOnWriteArrayList = this.mLifecycleCallbacks;
        synchronized (copyOnWriteArrayList) {
            int n;
            block6 : {
                n = 0;
                int n2 = this.mLifecycleCallbacks.size();
                while (n < n2) {
                    if (this.mLifecycleCallbacks.get((int)n).mCallback != fragmentLifecycleCallbacks) {
                        ++n;
                        continue;
                    }
                    break block6;
                }
                return;
            }
            this.mLifecycleCallbacks.remove(n);
            return;
        }
    }

    private static final class FragmentLifecycleCallbacksHolder {
        final FragmentManager.FragmentLifecycleCallbacks mCallback;
        final boolean mRecursive;

        FragmentLifecycleCallbacksHolder(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean bl) {
            this.mCallback = fragmentLifecycleCallbacks;
            this.mRecursive = bl;
        }
    }

}

