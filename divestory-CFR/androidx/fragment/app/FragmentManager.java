/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.Configuration
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.LayoutInflater$Factory2
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.animation.Animation
 */
package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.collection.ArraySet;
import androidx.core.os.CancellationSignal;
import androidx.core.util.LogWriter;
import androidx.fragment.R;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.BackStackState;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentHostCallback;
import androidx.fragment.app.FragmentLayoutInflaterFactory;
import androidx.fragment.app.FragmentLifecycleCallbacksDispatcher;
import androidx.fragment.app.FragmentManagerNonConfig;
import androidx.fragment.app.FragmentManagerState;
import androidx.fragment.app.FragmentManagerViewModel;
import androidx.fragment.app.FragmentState;
import androidx.fragment.app.FragmentStateManager;
import androidx.fragment.app.FragmentStore;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentTransition;
import androidx.fragment.app.FragmentViewLifecycleOwner;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class FragmentManager {
    private static boolean DEBUG = false;
    public static final int POP_BACK_STACK_INCLUSIVE = 1;
    static final String TAG = "FragmentManager";
    ArrayList<BackStackRecord> mBackStack;
    private ArrayList<OnBackStackChangedListener> mBackStackChangeListeners;
    private final AtomicInteger mBackStackIndex = new AtomicInteger();
    FragmentContainer mContainer;
    private ArrayList<Fragment> mCreatedMenus;
    int mCurState = -1;
    private boolean mDestroyed;
    private Runnable mExecCommit = new Runnable(){

        @Override
        public void run() {
            FragmentManager.this.execPendingActions(true);
        }
    };
    private boolean mExecutingActions;
    private ConcurrentHashMap<Fragment, HashSet<CancellationSignal>> mExitAnimationCancellationSignals = new ConcurrentHashMap();
    private FragmentFactory mFragmentFactory = null;
    private final FragmentStore mFragmentStore = new FragmentStore();
    private final FragmentTransition.Callback mFragmentTransitionCallback = new FragmentTransition.Callback(){

        @Override
        public void onComplete(Fragment fragment, CancellationSignal cancellationSignal) {
            if (cancellationSignal.isCanceled()) return;
            FragmentManager.this.removeCancellationSignal(fragment, cancellationSignal);
        }

        @Override
        public void onStart(Fragment fragment, CancellationSignal cancellationSignal) {
            FragmentManager.this.addCancellationSignal(fragment, cancellationSignal);
        }
    };
    private boolean mHavePendingDeferredStart;
    FragmentHostCallback<?> mHost;
    private FragmentFactory mHostFragmentFactory = new FragmentFactory(){

        @Override
        public Fragment instantiate(ClassLoader classLoader, String string2) {
            return FragmentManager.this.mHost.instantiate(FragmentManager.this.mHost.getContext(), string2, null);
        }
    };
    private final FragmentLayoutInflaterFactory mLayoutInflaterFactory = new FragmentLayoutInflaterFactory(this);
    private final FragmentLifecycleCallbacksDispatcher mLifecycleCallbacksDispatcher = new FragmentLifecycleCallbacksDispatcher(this);
    private boolean mNeedMenuInvalidate;
    private FragmentManagerViewModel mNonConfig;
    private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false){

        @Override
        public void handleOnBackPressed() {
            FragmentManager.this.handleOnBackPressed();
        }
    };
    private OnBackPressedDispatcher mOnBackPressedDispatcher;
    private Fragment mParent;
    private final ArrayList<OpGenerator> mPendingActions = new ArrayList();
    private ArrayList<StartEnterTransitionListener> mPostponedTransactions;
    Fragment mPrimaryNav;
    private boolean mStateSaved;
    private boolean mStopped;
    private ArrayList<Fragment> mTmpAddedFragments;
    private ArrayList<Boolean> mTmpIsPop;
    private ArrayList<BackStackRecord> mTmpRecords;

    private void addAddedFragments(ArraySet<Fragment> arraySet) {
        int n = this.mCurState;
        if (n < 1) {
            return;
        }
        n = Math.min(n, 3);
        Iterator<Fragment> iterator2 = this.mFragmentStore.getFragments().iterator();
        while (iterator2.hasNext()) {
            Fragment fragment = iterator2.next();
            if (fragment.mState >= n) continue;
            this.moveToState(fragment, n);
            if (fragment.mView == null || fragment.mHidden || !fragment.mIsNewlyAdded) continue;
            arraySet.add(fragment);
        }
    }

    private void cancelExitAnimation(Fragment fragment) {
        HashSet<CancellationSignal> hashSet = this.mExitAnimationCancellationSignals.get(fragment);
        if (hashSet == null) return;
        Iterator<CancellationSignal> iterator2 = hashSet.iterator();
        do {
            if (!iterator2.hasNext()) {
                hashSet.clear();
                this.destroyFragmentView(fragment);
                this.mExitAnimationCancellationSignals.remove(fragment);
                return;
            }
            iterator2.next().cancel();
        } while (true);
    }

    private void checkStateLoss() {
        if (this.isStateSaved()) throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    private void completeShowHideFragment(final Fragment fragment) {
        if (fragment.mView != null) {
            FragmentAnim.AnimationOrAnimator animationOrAnimator = FragmentAnim.loadAnimation(this.mHost.getContext(), this.mContainer, fragment, fragment.mHidden ^ true);
            if (animationOrAnimator != null && animationOrAnimator.animator != null) {
                animationOrAnimator.animator.setTarget((Object)fragment.mView);
                if (fragment.mHidden) {
                    if (fragment.isHideReplaced()) {
                        fragment.setHideReplaced(false);
                    } else {
                        final ViewGroup viewGroup = fragment.mContainer;
                        final View view = fragment.mView;
                        viewGroup.startViewTransition(view);
                        animationOrAnimator.animator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

                            public void onAnimationEnd(Animator animator2) {
                                viewGroup.endViewTransition(view);
                                animator2.removeListener((Animator.AnimatorListener)this);
                                if (fragment.mView == null) return;
                                if (!fragment.mHidden) return;
                                fragment.mView.setVisibility(8);
                            }
                        });
                    }
                } else {
                    fragment.mView.setVisibility(0);
                }
                animationOrAnimator.animator.start();
            } else {
                if (animationOrAnimator != null) {
                    fragment.mView.startAnimation(animationOrAnimator.animation);
                    animationOrAnimator.animation.start();
                }
                int n = fragment.mHidden && !fragment.isHideReplaced() ? 8 : 0;
                fragment.mView.setVisibility(n);
                if (fragment.isHideReplaced()) {
                    fragment.setHideReplaced(false);
                }
            }
        }
        if (fragment.mAdded && this.isMenuAvailable(fragment)) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mHiddenChanged = false;
        fragment.onHiddenChanged(fragment.mHidden);
    }

    private void destroyFragmentView(Fragment fragment) {
        fragment.performDestroyView();
        this.mLifecycleCallbacksDispatcher.dispatchOnFragmentViewDestroyed(fragment, false);
        fragment.mContainer = null;
        fragment.mView = null;
        fragment.mViewLifecycleOwner = null;
        fragment.mViewLifecycleOwnerLiveData.setValue(null);
        fragment.mInLayout = false;
    }

    private void dispatchParentPrimaryNavigationFragmentChanged(Fragment fragment) {
        if (fragment == null) return;
        if (!fragment.equals(this.findActiveFragment(fragment.mWho))) return;
        fragment.performPrimaryNavigationFragmentChanged();
    }

    private void dispatchStateChange(int n) {
        this.mExecutingActions = true;
        this.mFragmentStore.dispatchStateChange(n);
        this.moveToState(n, false);
        this.execPendingActions(true);
        return;
        finally {
            this.mExecutingActions = false;
        }
    }

    private void doPendingDeferredStart() {
        if (!this.mHavePendingDeferredStart) return;
        this.mHavePendingDeferredStart = false;
        this.startPendingDeferredFragments();
    }

    @Deprecated
    public static void enableDebugLogging(boolean bl) {
        DEBUG = bl;
    }

    private void endAnimatingAwayFragments() {
        if (this.mExitAnimationCancellationSignals.isEmpty()) return;
        Iterator iterator2 = this.mExitAnimationCancellationSignals.keySet().iterator();
        while (iterator2.hasNext()) {
            Fragment fragment = (Fragment)iterator2.next();
            this.cancelExitAnimation(fragment);
            this.moveToState(fragment, fragment.getStateAfterAnimating());
        }
    }

    private void ensureExecReady(boolean bl) {
        if (this.mExecutingActions) throw new IllegalStateException("FragmentManager is already executing transactions");
        if (this.mHost == null) {
            if (!this.mDestroyed) throw new IllegalStateException("FragmentManager has not been attached to a host.");
            throw new IllegalStateException("FragmentManager has been destroyed");
        }
        if (Looper.myLooper() != this.mHost.getHandler().getLooper()) throw new IllegalStateException("Must be called from main thread of fragment host");
        if (!bl) {
            this.checkStateLoss();
        }
        if (this.mTmpRecords == null) {
            this.mTmpRecords = new ArrayList();
            this.mTmpIsPop = new ArrayList();
        }
        this.mExecutingActions = true;
        try {
            this.executePostponedTransaction(null, null);
            return;
        }
        finally {
            this.mExecutingActions = false;
        }
    }

    private static void executeOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2) {
        while (n < n2) {
            BackStackRecord backStackRecord = arrayList.get(n);
            boolean bl = arrayList2.get(n);
            boolean bl2 = true;
            if (bl) {
                backStackRecord.bumpBackStackNesting(-1);
                if (n != n2 - 1) {
                    bl2 = false;
                }
                backStackRecord.executePopOps(bl2);
            } else {
                backStackRecord.bumpBackStackNesting(1);
                backStackRecord.executeOps();
            }
            ++n;
        }
    }

    private void executeOpsTogether(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2) {
        int n3;
        int n4 = n;
        boolean bl = arrayList.get((int)n4).mReorderingAllowed;
        ArrayList<Fragment> arrayList3 = this.mTmpAddedFragments;
        if (arrayList3 == null) {
            this.mTmpAddedFragments = new ArrayList();
        } else {
            arrayList3.clear();
        }
        this.mTmpAddedFragments.addAll(this.mFragmentStore.getFragments());
        arrayList3 = this.getPrimaryNavigationFragment();
        boolean bl2 = false;
        for (n3 = n4; n3 < n2; ++n3) {
            BackStackRecord backStackRecord = arrayList.get(n3);
            arrayList3 = arrayList2.get(n3) == false ? backStackRecord.expandOps(this.mTmpAddedFragments, (Fragment)((Object)arrayList3)) : backStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments, (Fragment)((Object)arrayList3));
            if (!bl2 && !backStackRecord.mAddToBackStack) {
                bl2 = false;
                continue;
            }
            bl2 = true;
        }
        this.mTmpAddedFragments.clear();
        if (!bl) {
            FragmentTransition.startTransitions(this, arrayList, arrayList2, n, n2, false, this.mFragmentTransitionCallback);
        }
        FragmentManager.executeOps(arrayList, arrayList2, n, n2);
        if (bl) {
            arrayList3 = new ArraySet();
            this.addAddedFragments((ArraySet<Fragment>)((Object)arrayList3));
            n3 = this.postponePostponableTransactions(arrayList, arrayList2, n, n2, (ArraySet<Fragment>)((Object)arrayList3));
            this.makeRemovedFragmentsInvisible((ArraySet<Fragment>)((Object)arrayList3));
        } else {
            n3 = n2;
        }
        int n5 = n4;
        if (n3 != n4) {
            n5 = n4;
            if (bl) {
                FragmentTransition.startTransitions(this, arrayList, arrayList2, n, n3, true, this.mFragmentTransitionCallback);
                this.moveToState(this.mCurState, true);
                n5 = n4;
            }
        }
        do {
            if (n5 >= n2) {
                if (!bl2) return;
                this.reportBackStackChanged();
                return;
            }
            arrayList3 = arrayList.get(n5);
            if (arrayList2.get(n5).booleanValue() && ((BackStackRecord)arrayList3).mIndex >= 0) {
                ((BackStackRecord)arrayList3).mIndex = -1;
            }
            ((BackStackRecord)((Object)arrayList3)).runOnCommitRunnables();
            ++n5;
        } while (true);
    }

    private void executePostponedTransaction(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        ArrayList<StartEnterTransitionListener> arrayList3 = this.mPostponedTransactions;
        int n = arrayList3 == null ? 0 : arrayList3.size();
        int n2 = 0;
        int n3 = n;
        while (n2 < n3) {
            int n4;
            block7 : {
                block8 : {
                    block6 : {
                        arrayList3 = this.mPostponedTransactions.get(n2);
                        if (arrayList == null || ((StartEnterTransitionListener)arrayList3).mIsBack || (n = arrayList.indexOf(((StartEnterTransitionListener)arrayList3).mRecord)) == -1 || arrayList2 == null || !arrayList2.get(n).booleanValue()) break block6;
                        this.mPostponedTransactions.remove(n2);
                        n4 = n2 - 1;
                        n = n3 - 1;
                        ((StartEnterTransitionListener)((Object)arrayList3)).cancelTransaction();
                        break block7;
                    }
                    if (((StartEnterTransitionListener)((Object)arrayList3)).isReady()) break block8;
                    n = n3;
                    n4 = n2;
                    if (arrayList == null) break block7;
                    n = n3;
                    n4 = n2;
                    if (!((StartEnterTransitionListener)arrayList3).mRecord.interactsWith(arrayList, 0, arrayList.size())) break block7;
                }
                this.mPostponedTransactions.remove(n2);
                n4 = n2 - 1;
                n = n3 - 1;
                if (arrayList != null && !((StartEnterTransitionListener)arrayList3).mIsBack && (n2 = arrayList.indexOf(((StartEnterTransitionListener)arrayList3).mRecord)) != -1 && arrayList2 != null && arrayList2.get(n2).booleanValue()) {
                    ((StartEnterTransitionListener)((Object)arrayList3)).cancelTransaction();
                } else {
                    ((StartEnterTransitionListener)((Object)arrayList3)).completeTransaction();
                }
            }
            n2 = n4 + 1;
            n3 = n;
        }
    }

    public static <F extends Fragment> F findFragment(View view) {
        Object object = FragmentManager.findViewFragment(view);
        if (object != null) {
            return (F)object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("View ");
        ((StringBuilder)object).append((Object)view);
        ((StringBuilder)object).append(" does not have a Fragment set");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    static FragmentManager findFragmentManager(View object) {
        Object object2 = FragmentManager.findViewFragment(object);
        if (object2 != null) {
            return ((Fragment)object2).getChildFragmentManager();
        }
        Context context = object.getContext();
        Object var3_3 = null;
        do {
            object2 = var3_3;
            if (!(context instanceof ContextWrapper)) break;
            if (context instanceof FragmentActivity) {
                object2 = (FragmentActivity)context;
                break;
            }
            context = ((ContextWrapper)context).getBaseContext();
        } while (true);
        if (object2 != null) {
            return ((FragmentActivity)object2).getSupportFragmentManager();
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("View ");
        ((StringBuilder)object2).append(object);
        ((StringBuilder)object2).append(" is not within a subclass of FragmentActivity.");
        throw new IllegalStateException(((StringBuilder)object2).toString());
    }

    private static Fragment findViewFragment(View view) {
        while (view != null) {
            Fragment fragment = FragmentManager.getViewFragment(view);
            if (fragment != null) {
                return fragment;
            }
            if ((view = view.getParent()) instanceof View) continue;
            view = null;
        }
        return null;
    }

    private void forcePostponedTransactions() {
        if (this.mPostponedTransactions == null) return;
        while (!this.mPostponedTransactions.isEmpty()) {
            this.mPostponedTransactions.remove(0).completeTransaction();
        }
    }

    private boolean generateOpsForPendingActions(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        ArrayList<OpGenerator> arrayList3 = this.mPendingActions;
        synchronized (arrayList3) {
            boolean bl = this.mPendingActions.isEmpty();
            if (bl) {
                return false;
            }
            int n = this.mPendingActions.size();
            bl = false;
            for (int i = 0; i < n; bl |= this.mPendingActions.get((int)i).generateOps(arrayList, arrayList2), ++i) {
            }
            this.mPendingActions.clear();
            this.mHost.getHandler().removeCallbacks(this.mExecCommit);
            return bl;
        }
    }

    private FragmentManagerViewModel getChildNonConfig(Fragment fragment) {
        return this.mNonConfig.getChildNonConfig(fragment);
    }

    private ViewGroup getFragmentContainer(Fragment fragment) {
        if (fragment.mContainerId <= 0) {
            return null;
        }
        if (!this.mContainer.onHasView()) return null;
        fragment = this.mContainer.onFindViewById(fragment.mContainerId);
        if (!(fragment instanceof ViewGroup)) return null;
        return (ViewGroup)fragment;
    }

    static Fragment getViewFragment(View object) {
        if (!((object = object.getTag(R.id.fragment_container_view_tag)) instanceof Fragment)) return null;
        return (Fragment)object;
    }

    static boolean isLoggingEnabled(int n) {
        if (DEBUG) return true;
        if (Log.isLoggable((String)TAG, (int)n)) return true;
        return false;
    }

    private boolean isMenuAvailable(Fragment fragment) {
        if (fragment.mHasMenu) {
            if (fragment.mMenuVisible) return true;
        }
        if (fragment.mChildFragmentManager.checkForMenus()) return true;
        return false;
    }

    private void makeInactive(FragmentStateManager fragmentStateManager) {
        Fragment fragment = fragmentStateManager.getFragment();
        if (!this.mFragmentStore.containsActiveFragment(fragment.mWho)) {
            return;
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Removed fragment from active set ");
            stringBuilder.append(fragment);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        this.mFragmentStore.makeInactive(fragmentStateManager);
        this.removeRetainedFragment(fragment);
    }

    private void makeRemovedFragmentsInvisible(ArraySet<Fragment> arraySet) {
        int n = arraySet.size();
        int n2 = 0;
        while (n2 < n) {
            Fragment fragment = arraySet.valueAt(n2);
            if (!fragment.mAdded) {
                View view = fragment.requireView();
                fragment.mPostponedAlpha = view.getAlpha();
                view.setAlpha(0.0f);
            }
            ++n2;
        }
    }

    private boolean popBackStackImmediate(String string2, int n, int n2) {
        this.execPendingActions(false);
        this.ensureExecReady(true);
        Fragment fragment = this.mPrimaryNav;
        if (fragment != null && n < 0 && string2 == null && fragment.getChildFragmentManager().popBackStackImmediate()) {
            return true;
        }
        boolean bl = this.popBackStackState(this.mTmpRecords, this.mTmpIsPop, string2, n, n2);
        if (bl) {
            this.mExecutingActions = true;
            try {
                this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            }
            finally {
                this.cleanupExec();
            }
        }
        this.updateOnBackPressedCallbackEnabled();
        this.doPendingDeferredStart();
        this.mFragmentStore.burpActive();
        return bl;
    }

    private int postponePostponableTransactions(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2, ArraySet<Fragment> arraySet) {
        int n3 = n2 - 1;
        int n4 = n2;
        while (n3 >= n) {
            BackStackRecord backStackRecord = arrayList.get(n3);
            boolean bl = arrayList2.get(n3);
            boolean bl2 = backStackRecord.isPostponed() && !backStackRecord.interactsWith(arrayList, n3 + 1, n2);
            int n5 = n4;
            if (bl2) {
                if (this.mPostponedTransactions == null) {
                    this.mPostponedTransactions = new ArrayList();
                }
                StartEnterTransitionListener startEnterTransitionListener = new StartEnterTransitionListener(backStackRecord, bl);
                this.mPostponedTransactions.add(startEnterTransitionListener);
                backStackRecord.setOnStartPostponedListener(startEnterTransitionListener);
                if (bl) {
                    backStackRecord.executeOps();
                } else {
                    backStackRecord.executePopOps(false);
                }
                n5 = n4 - 1;
                if (n3 != n5) {
                    arrayList.remove(n3);
                    arrayList.add(n5, backStackRecord);
                }
                this.addAddedFragments(arraySet);
            }
            --n3;
            n4 = n5;
        }
        return n4;
    }

    private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        if (arrayList.isEmpty()) {
            return;
        }
        if (arrayList.size() != arrayList2.size()) throw new IllegalStateException("Internal error with the back stack records");
        this.executePostponedTransaction(arrayList, arrayList2);
        int n = arrayList.size();
        int n2 = 0;
        int n3 = 0;
        do {
            if (n2 >= n) {
                if (n3 == n) return;
                this.executeOpsTogether(arrayList, arrayList2, n3, n);
                return;
            }
            int n4 = n2;
            int n5 = n3;
            if (!arrayList.get((int)n2).mReorderingAllowed) {
                if (n3 != n2) {
                    this.executeOpsTogether(arrayList, arrayList2, n3, n2);
                }
                n5 = n3 = n2 + 1;
                if (arrayList2.get(n2).booleanValue()) {
                    do {
                        n5 = n3;
                        if (n3 >= n) break;
                        n5 = n3;
                        if (!arrayList2.get(n3).booleanValue()) break;
                        n5 = n3++;
                    } while (!arrayList.get((int)n3).mReorderingAllowed);
                }
                this.executeOpsTogether(arrayList, arrayList2, n2, n5);
                n4 = n5 - 1;
            }
            n2 = n4 + 1;
            n3 = n5;
        } while (true);
    }

    private void reportBackStackChanged() {
        if (this.mBackStackChangeListeners == null) return;
        int n = 0;
        while (n < this.mBackStackChangeListeners.size()) {
            this.mBackStackChangeListeners.get(n).onBackStackChanged();
            ++n;
        }
    }

    static int reverseTransit(int n) {
        int n2 = 8194;
        if (n == 4097) return n2;
        if (n == 4099) {
            return 4099;
        }
        if (n == 8194) return 4097;
        return 0;
    }

    private void setVisibleRemovingFragment(Fragment fragment) {
        ViewGroup viewGroup = this.getFragmentContainer(fragment);
        if (viewGroup == null) return;
        if (viewGroup.getTag(R.id.visible_removing_fragment_view_tag) == null) {
            viewGroup.setTag(R.id.visible_removing_fragment_view_tag, (Object)fragment);
        }
        ((Fragment)viewGroup.getTag(R.id.visible_removing_fragment_view_tag)).setNextAnim(fragment.getNextAnim());
    }

    private void startPendingDeferredFragments() {
        Iterator<Fragment> iterator2 = this.mFragmentStore.getActiveFragments().iterator();
        while (iterator2.hasNext()) {
            Fragment fragment = iterator2.next();
            if (fragment == null) continue;
            this.performPendingDeferredStart(fragment);
        }
    }

    private void throwException(RuntimeException runtimeException) {
        Log.e((String)TAG, (String)runtimeException.getMessage());
        Log.e((String)TAG, (String)"Activity state:");
        PrintWriter printWriter = new PrintWriter(new LogWriter(TAG));
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            try {
                fragmentHostCallback.onDump("  ", null, printWriter, new String[0]);
                throw runtimeException;
            }
            catch (Exception exception) {
                Log.e((String)TAG, (String)"Failed dumping state", (Throwable)exception);
                throw runtimeException;
            }
        }
        try {
            this.dump("  ", null, printWriter, new String[0]);
            throw runtimeException;
        }
        catch (Exception exception) {
            Log.e((String)TAG, (String)"Failed dumping state", (Throwable)exception);
        }
        throw runtimeException;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    private void updateOnBackPressedCallbackEnabled() {
        ArrayList<OpGenerator> arrayList = this.mPendingActions;
        synchronized (arrayList) {
            boolean bl = this.mPendingActions.isEmpty();
            boolean bl2 = true;
            if (!bl) {
                this.mOnBackPressedCallback.setEnabled(true);
                return;
            }
            OnBackPressedCallback onBackPressedCallback = this.mOnBackPressedCallback;
            if (this.getBackStackEntryCount() <= 0 || !this.isPrimaryNavigation(this.mParent)) {
                bl2 = false;
            }
            onBackPressedCallback.setEnabled(bl2);
            return;
        }
    }

    void addBackStackState(BackStackRecord backStackRecord) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList();
        }
        this.mBackStack.add(backStackRecord);
    }

    void addCancellationSignal(Fragment fragment, CancellationSignal cancellationSignal) {
        if (this.mExitAnimationCancellationSignals.get(fragment) == null) {
            this.mExitAnimationCancellationSignals.put(fragment, new HashSet());
        }
        this.mExitAnimationCancellationSignals.get(fragment).add(cancellationSignal);
    }

    void addFragment(Fragment fragment) {
        if (FragmentManager.isLoggingEnabled(2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("add: ");
            stringBuilder.append(fragment);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        this.makeActive(fragment);
        if (fragment.mDetached) return;
        this.mFragmentStore.addFragment(fragment);
        fragment.mRemoving = false;
        if (fragment.mView == null) {
            fragment.mHiddenChanged = false;
        }
        if (!this.isMenuAvailable(fragment)) return;
        this.mNeedMenuInvalidate = true;
    }

    public void addOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList();
        }
        this.mBackStackChangeListeners.add(onBackStackChangedListener);
    }

    void addRetainedFragment(Fragment fragment) {
        if (this.isStateSaved()) {
            if (!FragmentManager.isLoggingEnabled(2)) return;
            Log.v((String)TAG, (String)"Ignoring addRetainedFragment as the state is already saved");
            return;
        }
        if (!this.mNonConfig.addRetainedFragment(fragment)) return;
        if (!FragmentManager.isLoggingEnabled(2)) return;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Updating retained Fragments: Added ");
        stringBuilder.append(fragment);
        Log.v((String)TAG, (String)stringBuilder.toString());
    }

    int allocBackStackIndex() {
        return this.mBackStackIndex.getAndIncrement();
    }

    void attachController(FragmentHostCallback<?> fragmentHostCallback, FragmentContainer object, Fragment fragment) {
        if (this.mHost != null) throw new IllegalStateException("Already attached");
        this.mHost = fragmentHostCallback;
        this.mContainer = object;
        this.mParent = fragment;
        if (fragment != null) {
            this.updateOnBackPressedCallbackEnabled();
        }
        if (fragmentHostCallback instanceof OnBackPressedDispatcherOwner) {
            object = (OnBackPressedDispatcherOwner)((Object)fragmentHostCallback);
            this.mOnBackPressedDispatcher = object.getOnBackPressedDispatcher();
            if (fragment != null) {
                object = fragment;
            }
            this.mOnBackPressedDispatcher.addCallback((LifecycleOwner)object, this.mOnBackPressedCallback);
        }
        if (fragment != null) {
            this.mNonConfig = fragment.mFragmentManager.getChildNonConfig(fragment);
            return;
        }
        if (fragmentHostCallback instanceof ViewModelStoreOwner) {
            this.mNonConfig = FragmentManagerViewModel.getInstance(((ViewModelStoreOwner)((Object)fragmentHostCallback)).getViewModelStore());
            return;
        }
        this.mNonConfig = new FragmentManagerViewModel(false);
    }

    void attachFragment(Fragment fragment) {
        StringBuilder stringBuilder;
        if (FragmentManager.isLoggingEnabled(2)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("attach: ");
            stringBuilder.append(fragment);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        if (!fragment.mDetached) return;
        fragment.mDetached = false;
        if (fragment.mAdded) return;
        this.mFragmentStore.addFragment(fragment);
        if (FragmentManager.isLoggingEnabled(2)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("add from attach: ");
            stringBuilder.append(fragment);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        if (!this.isMenuAvailable(fragment)) return;
        this.mNeedMenuInvalidate = true;
    }

    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    boolean checkForMenus() {
        boolean bl;
        Iterator<Fragment> iterator2 = this.mFragmentStore.getActiveFragments().iterator();
        boolean bl2 = false;
        do {
            if (!iterator2.hasNext()) return false;
            Fragment fragment = iterator2.next();
            bl = bl2;
            if (fragment != null) {
                bl = this.isMenuAvailable(fragment);
            }
            bl2 = bl;
        } while (!bl);
        return true;
    }

    void completeExecute(BackStackRecord backStackRecord, boolean bl, boolean bl2, boolean bl3) {
        if (bl) {
            backStackRecord.executePopOps(bl3);
        } else {
            backStackRecord.executeOps();
        }
        Object object = new ArrayList<BackStackRecord>(1);
        Object object2 = new ArrayList<Boolean>(1);
        ((ArrayList)object).add(backStackRecord);
        ((ArrayList)object2).add(bl);
        if (bl2) {
            FragmentTransition.startTransitions(this, object, object2, 0, 1, true, this.mFragmentTransitionCallback);
        }
        if (bl3) {
            this.moveToState(this.mCurState, true);
        }
        object2 = this.mFragmentStore.getActiveFragments().iterator();
        while (object2.hasNext()) {
            object = (Fragment)object2.next();
            if (object == null || ((Fragment)object).mView == null || !((Fragment)object).mIsNewlyAdded || !backStackRecord.interactsWith(((Fragment)object).mContainerId)) continue;
            if (((Fragment)object).mPostponedAlpha > 0.0f) {
                ((Fragment)object).mView.setAlpha(((Fragment)object).mPostponedAlpha);
            }
            if (bl3) {
                ((Fragment)object).mPostponedAlpha = 0.0f;
                continue;
            }
            ((Fragment)object).mPostponedAlpha = -1.0f;
            ((Fragment)object).mIsNewlyAdded = false;
        }
    }

    void detachFragment(Fragment fragment) {
        StringBuilder stringBuilder;
        if (FragmentManager.isLoggingEnabled(2)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("detach: ");
            stringBuilder.append(fragment);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        if (fragment.mDetached) return;
        fragment.mDetached = true;
        if (!fragment.mAdded) return;
        if (FragmentManager.isLoggingEnabled(2)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("remove from detach: ");
            stringBuilder.append(fragment);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        this.mFragmentStore.removeFragment(fragment);
        if (this.isMenuAvailable(fragment)) {
            this.mNeedMenuInvalidate = true;
        }
        this.setVisibleRemovingFragment(fragment);
    }

    void dispatchActivityCreated() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.dispatchStateChange(2);
    }

    void dispatchConfigurationChanged(Configuration configuration) {
        Iterator<Fragment> iterator2 = this.mFragmentStore.getFragments().iterator();
        while (iterator2.hasNext()) {
            Fragment fragment = iterator2.next();
            if (fragment == null) continue;
            fragment.performConfigurationChanged(configuration);
        }
    }

    boolean dispatchContextItemSelected(MenuItem menuItem) {
        Fragment fragment;
        if (this.mCurState < 1) {
            return false;
        }
        Iterator<Fragment> iterator2 = this.mFragmentStore.getFragments().iterator();
        do {
            if (!iterator2.hasNext()) return false;
        } while ((fragment = iterator2.next()) == null || !fragment.performContextItemSelected(menuItem));
        return true;
    }

    void dispatchCreate() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.dispatchStateChange(1);
    }

    boolean dispatchCreateOptionsMenu(Menu object, MenuInflater menuInflater) {
        int n = this.mCurState;
        int n2 = 0;
        if (n < 1) {
            return false;
        }
        ArrayList<Fragment> arrayList = null;
        Iterator<Fragment> iterator2 = this.mFragmentStore.getFragments().iterator();
        boolean bl = false;
        while (iterator2.hasNext()) {
            Fragment fragment = iterator2.next();
            if (fragment == null || !fragment.performCreateOptionsMenu((Menu)object, menuInflater)) continue;
            ArrayList<Fragment> arrayList2 = arrayList;
            if (arrayList == null) {
                arrayList2 = new ArrayList<Fragment>();
            }
            arrayList2.add(fragment);
            bl = true;
            arrayList = arrayList2;
        }
        if (this.mCreatedMenus != null) {
            while (n2 < this.mCreatedMenus.size()) {
                object = this.mCreatedMenus.get(n2);
                if (arrayList == null || !arrayList.contains(object)) {
                    ((Fragment)object).onDestroyOptionsMenu();
                }
                ++n2;
            }
        }
        this.mCreatedMenus = arrayList;
        return bl;
    }

    void dispatchDestroy() {
        this.mDestroyed = true;
        this.execPendingActions(true);
        this.endAnimatingAwayFragments();
        this.dispatchStateChange(-1);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
        if (this.mOnBackPressedDispatcher == null) return;
        this.mOnBackPressedCallback.remove();
        this.mOnBackPressedDispatcher = null;
    }

    void dispatchDestroyView() {
        this.dispatchStateChange(1);
    }

    void dispatchLowMemory() {
        Iterator<Fragment> iterator2 = this.mFragmentStore.getFragments().iterator();
        while (iterator2.hasNext()) {
            Fragment fragment = iterator2.next();
            if (fragment == null) continue;
            fragment.performLowMemory();
        }
    }

    void dispatchMultiWindowModeChanged(boolean bl) {
        Iterator<Fragment> iterator2 = this.mFragmentStore.getFragments().iterator();
        while (iterator2.hasNext()) {
            Fragment fragment = iterator2.next();
            if (fragment == null) continue;
            fragment.performMultiWindowModeChanged(bl);
        }
    }

    boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        Fragment fragment;
        if (this.mCurState < 1) {
            return false;
        }
        Iterator<Fragment> iterator2 = this.mFragmentStore.getFragments().iterator();
        do {
            if (!iterator2.hasNext()) return false;
        } while ((fragment = iterator2.next()) == null || !fragment.performOptionsItemSelected(menuItem));
        return true;
    }

    void dispatchOptionsMenuClosed(Menu menu2) {
        if (this.mCurState < 1) {
            return;
        }
        Iterator<Fragment> iterator2 = this.mFragmentStore.getFragments().iterator();
        while (iterator2.hasNext()) {
            Fragment fragment = iterator2.next();
            if (fragment == null) continue;
            fragment.performOptionsMenuClosed(menu2);
        }
    }

    void dispatchPause() {
        this.dispatchStateChange(3);
    }

    void dispatchPictureInPictureModeChanged(boolean bl) {
        Iterator<Fragment> iterator2 = this.mFragmentStore.getFragments().iterator();
        while (iterator2.hasNext()) {
            Fragment fragment = iterator2.next();
            if (fragment == null) continue;
            fragment.performPictureInPictureModeChanged(bl);
        }
    }

    boolean dispatchPrepareOptionsMenu(Menu menu2) {
        int n = this.mCurState;
        boolean bl = false;
        if (n < 1) {
            return false;
        }
        Iterator<Fragment> iterator2 = this.mFragmentStore.getFragments().iterator();
        while (iterator2.hasNext()) {
            Fragment fragment = iterator2.next();
            if (fragment == null || !fragment.performPrepareOptionsMenu(menu2)) continue;
            bl = true;
        }
        return bl;
    }

    void dispatchPrimaryNavigationFragmentChanged() {
        this.updateOnBackPressedCallbackEnabled();
        this.dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
    }

    void dispatchResume() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.dispatchStateChange(4);
    }

    void dispatchStart() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.dispatchStateChange(3);
    }

    void dispatchStop() {
        this.mStopped = true;
        this.dispatchStateChange(2);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public void dump(String string2, FileDescriptor arrayList, PrintWriter printWriter, String[] object) {
        int n;
        int n2;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string2);
        charSequence.append("    ");
        charSequence = charSequence.toString();
        this.mFragmentStore.dump(string2, (FileDescriptor)((Object)arrayList), printWriter, (String[])object);
        arrayList = this.mCreatedMenus;
        int n3 = 0;
        if (arrayList != null && (n = arrayList.size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Fragments Created Menus:");
            for (n2 = 0; n2 < n; ++n2) {
                arrayList = this.mCreatedMenus.get(n2);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.println(((Fragment)((Object)arrayList)).toString());
            }
        }
        if ((arrayList = this.mBackStack) != null && (n = arrayList.size()) > 0) {
            printWriter.print(string2);
            printWriter.println("Back Stack:");
            for (n2 = 0; n2 < n; ++n2) {
                arrayList = this.mBackStack.get(n2);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(n2);
                printWriter.print(": ");
                printWriter.println(((BackStackRecord)((Object)arrayList)).toString());
                ((BackStackRecord)((Object)arrayList)).dump((String)charSequence, printWriter);
            }
        }
        printWriter.print(string2);
        arrayList = new StringBuilder();
        ((StringBuilder)((Object)arrayList)).append("Back Stack Index: ");
        ((StringBuilder)((Object)arrayList)).append(this.mBackStackIndex.get());
        printWriter.println(((StringBuilder)((Object)arrayList)).toString());
        arrayList = this.mPendingActions;
        synchronized (arrayList) {
            n = this.mPendingActions.size();
            if (n > 0) {
                printWriter.print(string2);
                printWriter.println("Pending Actions:");
                for (n2 = n3; n2 < n; ++n2) {
                    object = this.mPendingActions.get(n2);
                    printWriter.print(string2);
                    printWriter.print("  #");
                    printWriter.print(n2);
                    printWriter.print(": ");
                    printWriter.println(object);
                }
            }
        }
        printWriter.print(string2);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(string2);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(string2);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(string2);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(string2);
        printWriter.print("  mCurState=");
        printWriter.print(this.mCurState);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (!this.mNeedMenuInvalidate) return;
        printWriter.print(string2);
        printWriter.print("  mNeedMenuInvalidate=");
        printWriter.println(this.mNeedMenuInvalidate);
    }

    void enqueueAction(OpGenerator object, boolean bl) {
        if (!bl) {
            if (this.mHost == null) {
                if (!this.mDestroyed) throw new IllegalStateException("FragmentManager has not been attached to a host.");
                throw new IllegalStateException("FragmentManager has been destroyed");
            }
            this.checkStateLoss();
        }
        ArrayList<OpGenerator> arrayList = this.mPendingActions;
        synchronized (arrayList) {
            if (this.mHost != null) {
                this.mPendingActions.add((OpGenerator)object);
                this.scheduleCommit();
                return;
            }
            if (bl) {
                return;
            }
            object = new IllegalStateException("Activity has been destroyed");
            throw object;
        }
    }

    boolean execPendingActions(boolean bl) {
        this.ensureExecReady(bl);
        bl = false;
        do {
            if (!this.generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
                this.updateOnBackPressedCallbackEnabled();
                this.doPendingDeferredStart();
                this.mFragmentStore.burpActive();
                return bl;
            }
            this.mExecutingActions = true;
            this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            bl = true;
            continue;
            break;
        } while (true);
        finally {
            this.cleanupExec();
        }
    }

    void execSingleAction(OpGenerator opGenerator, boolean bl) {
        if (bl) {
            if (this.mHost == null) return;
            if (this.mDestroyed) {
                return;
            }
        }
        this.ensureExecReady(bl);
        if (opGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                this.removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            }
            finally {
                this.cleanupExec();
            }
        }
        this.updateOnBackPressedCallbackEnabled();
        this.doPendingDeferredStart();
        this.mFragmentStore.burpActive();
    }

    public boolean executePendingTransactions() {
        boolean bl = this.execPendingActions(true);
        this.forcePostponedTransactions();
        return bl;
    }

    Fragment findActiveFragment(String string2) {
        return this.mFragmentStore.findActiveFragment(string2);
    }

    public Fragment findFragmentById(int n) {
        return this.mFragmentStore.findFragmentById(n);
    }

    public Fragment findFragmentByTag(String string2) {
        return this.mFragmentStore.findFragmentByTag(string2);
    }

    Fragment findFragmentByWho(String string2) {
        return this.mFragmentStore.findFragmentByWho(string2);
    }

    int getActiveFragmentCount() {
        return this.mFragmentStore.getActiveFragmentCount();
    }

    List<Fragment> getActiveFragments() {
        return this.mFragmentStore.getActiveFragments();
    }

    public BackStackEntry getBackStackEntryAt(int n) {
        return this.mBackStack.get(n);
    }

    public int getBackStackEntryCount() {
        ArrayList<BackStackRecord> arrayList = this.mBackStack;
        if (arrayList == null) return 0;
        return arrayList.size();
    }

    public Fragment getFragment(Bundle object, String string2) {
        String string3 = object.getString(string2);
        if (string3 == null) {
            return null;
        }
        Fragment fragment = this.findActiveFragment(string3);
        if (fragment != null) return fragment;
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment no longer exists for key ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(": unique id ");
        ((StringBuilder)object).append(string3);
        this.throwException(new IllegalStateException(((StringBuilder)object).toString()));
        return fragment;
    }

    public FragmentFactory getFragmentFactory() {
        Object object = this.mFragmentFactory;
        if (object != null) {
            return object;
        }
        object = this.mParent;
        if (object == null) return this.mHostFragmentFactory;
        return ((Fragment)object).mFragmentManager.getFragmentFactory();
    }

    public List<Fragment> getFragments() {
        return this.mFragmentStore.getFragments();
    }

    LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this.mLayoutInflaterFactory;
    }

    FragmentLifecycleCallbacksDispatcher getLifecycleCallbacksDispatcher() {
        return this.mLifecycleCallbacksDispatcher;
    }

    Fragment getParent() {
        return this.mParent;
    }

    public Fragment getPrimaryNavigationFragment() {
        return this.mPrimaryNav;
    }

    ViewModelStore getViewModelStore(Fragment fragment) {
        return this.mNonConfig.getViewModelStore(fragment);
    }

    void handleOnBackPressed() {
        this.execPendingActions(true);
        if (this.mOnBackPressedCallback.isEnabled()) {
            this.popBackStackImmediate();
            return;
        }
        this.mOnBackPressedDispatcher.onBackPressed();
    }

    void hideFragment(Fragment fragment) {
        if (FragmentManager.isLoggingEnabled(2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("hide: ");
            stringBuilder.append(fragment);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        if (fragment.mHidden) return;
        fragment.mHidden = true;
        fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
        this.setVisibleRemovingFragment(fragment);
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    boolean isPrimaryNavigation(Fragment fragment) {
        boolean bl = true;
        if (fragment == null) {
            return true;
        }
        FragmentManager fragmentManager = fragment.mFragmentManager;
        if (!fragment.equals(fragmentManager.getPrimaryNavigationFragment())) return false;
        if (!this.isPrimaryNavigation(fragmentManager.mParent)) return false;
        return bl;
    }

    boolean isStateAtLeast(int n) {
        if (this.mCurState < n) return false;
        return true;
    }

    public boolean isStateSaved() {
        if (this.mStateSaved) return true;
        if (this.mStopped) return true;
        return false;
    }

    void makeActive(Fragment fragment) {
        if (this.mFragmentStore.containsActiveFragment(fragment.mWho)) {
            return;
        }
        Object object = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, fragment);
        ((FragmentStateManager)object).restoreState(this.mHost.getContext().getClassLoader());
        this.mFragmentStore.makeActive((FragmentStateManager)object);
        if (fragment.mRetainInstanceChangedWhileDetached) {
            if (fragment.mRetainInstance) {
                this.addRetainedFragment(fragment);
            } else {
                this.removeRetainedFragment(fragment);
            }
            fragment.mRetainInstanceChangedWhileDetached = false;
        }
        ((FragmentStateManager)object).setFragmentManagerState(this.mCurState);
        if (!FragmentManager.isLoggingEnabled(2)) return;
        object = new StringBuilder();
        ((StringBuilder)object).append("Added fragment to active set ");
        ((StringBuilder)object).append(fragment);
        Log.v((String)TAG, (String)((StringBuilder)object).toString());
    }

    void moveFragmentToExpectedState(Fragment fragment) {
        if (!this.mFragmentStore.containsActiveFragment(fragment.mWho)) {
            if (!FragmentManager.isLoggingEnabled(3)) return;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ignoring moving ");
            stringBuilder.append(fragment);
            stringBuilder.append(" to state ");
            stringBuilder.append(this.mCurState);
            stringBuilder.append("since it is not added to ");
            stringBuilder.append(this);
            Log.d((String)TAG, (String)stringBuilder.toString());
            return;
        }
        this.moveToState(fragment);
        if (fragment.mView != null) {
            Object object = this.mFragmentStore.findFragmentUnder(fragment);
            if (object != null) {
                View view = ((Fragment)object).mView;
                object = fragment.mContainer;
                int n = object.indexOfChild(view);
                int n2 = object.indexOfChild(fragment.mView);
                if (n2 < n) {
                    object.removeViewAt(n2);
                    object.addView(fragment.mView, n);
                }
            }
            if (fragment.mIsNewlyAdded && fragment.mContainer != null) {
                if (fragment.mPostponedAlpha > 0.0f) {
                    fragment.mView.setAlpha(fragment.mPostponedAlpha);
                }
                fragment.mPostponedAlpha = 0.0f;
                fragment.mIsNewlyAdded = false;
                object = FragmentAnim.loadAnimation(this.mHost.getContext(), this.mContainer, fragment, true);
                if (object != null) {
                    if (((FragmentAnim.AnimationOrAnimator)object).animation != null) {
                        fragment.mView.startAnimation(((FragmentAnim.AnimationOrAnimator)object).animation);
                    } else {
                        ((FragmentAnim.AnimationOrAnimator)object).animator.setTarget((Object)fragment.mView);
                        ((FragmentAnim.AnimationOrAnimator)object).animator.start();
                    }
                }
            }
        }
        if (!fragment.mHiddenChanged) return;
        this.completeShowHideFragment(fragment);
    }

    void moveToState(int n, boolean bl) {
        if (this.mHost == null) {
            if (n != -1) throw new IllegalStateException("No activity");
        }
        if (!bl && n == this.mCurState) {
            return;
        }
        this.mCurState = n;
        Object object = this.mFragmentStore.getFragments().iterator();
        while (object.hasNext()) {
            this.moveFragmentToExpectedState(object.next());
        }
        Iterator<Fragment> iterator2 = this.mFragmentStore.getActiveFragments().iterator();
        do {
            if (!iterator2.hasNext()) {
                this.startPendingDeferredFragments();
                if (!this.mNeedMenuInvalidate) return;
                object = this.mHost;
                if (object == null) return;
                if (this.mCurState != 4) return;
                ((FragmentHostCallback)object).onSupportInvalidateOptionsMenu();
                this.mNeedMenuInvalidate = false;
                return;
            }
            object = iterator2.next();
            if (object == null || ((Fragment)object).mIsNewlyAdded) continue;
            this.moveFragmentToExpectedState((Fragment)object);
        } while (true);
    }

    void moveToState(Fragment fragment) {
        this.moveToState(fragment, this.mCurState);
    }

    /*
     * Unable to fully structure code
     */
    void moveToState(Fragment var1_1, int var2_2) {
        block48 : {
            block42 : {
                block47 : {
                    block46 : {
                        block45 : {
                            block44 : {
                                block43 : {
                                    var3_3 = this.mFragmentStore.getFragmentStateManager(var1_1.mWho);
                                    var4_4 = 1;
                                    var5_5 = var3_3;
                                    if (var3_3 == null) {
                                        var5_5 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, var1_1);
                                        var5_5.setFragmentManagerState(1);
                                    }
                                    var2_2 = Math.min(var2_2, var5_5.computeMaxState());
                                    var6_6 = var1_1.mState;
                                    var7_7 = null;
                                    if (var6_6 > var2_2) break block42;
                                    if (var1_1.mState < var2_2 && !this.mExitAnimationCancellationSignals.isEmpty()) {
                                        this.cancelExitAnimation(var1_1);
                                    }
                                    if ((var6_6 = var1_1.mState) == -1) break block43;
                                    if (var6_6 == 0) break block44;
                                    if (var6_6 == 1) break block45;
                                    if (var6_6 == 2) break block46;
                                    if (var6_6 == 3) break block47;
                                    var6_6 = var2_2;
                                    break block48;
                                }
                                if (var2_2 > -1) {
                                    if (FragmentManager.isLoggingEnabled(3)) {
                                        var3_3 = new StringBuilder();
                                        var3_3.append("moveto ATTACHED: ");
                                        var3_3.append(var1_1);
                                        Log.d((String)"FragmentManager", (String)var3_3.toString());
                                    }
                                    if (var1_1.mTarget != null) {
                                        if (!var1_1.mTarget.equals(this.findActiveFragment(var1_1.mTarget.mWho))) {
                                            var5_5 = new StringBuilder();
                                            var5_5.append("Fragment ");
                                            var5_5.append(var1_1);
                                            var5_5.append(" declared target fragment ");
                                            var5_5.append(var1_1.mTarget);
                                            var5_5.append(" that does not belong to this FragmentManager!");
                                            throw new IllegalStateException(var5_5.toString());
                                        }
                                        if (var1_1.mTarget.mState < 1) {
                                            this.moveToState(var1_1.mTarget, 1);
                                        }
                                        var1_1.mTargetWho = var1_1.mTarget.mWho;
                                        var1_1.mTarget = null;
                                    }
                                    if (var1_1.mTargetWho != null) {
                                        var3_3 = this.findActiveFragment(var1_1.mTargetWho);
                                        if (var3_3 == null) {
                                            var5_5 = new StringBuilder();
                                            var5_5.append("Fragment ");
                                            var5_5.append(var1_1);
                                            var5_5.append(" declared target fragment ");
                                            var5_5.append(var1_1.mTargetWho);
                                            var5_5.append(" that does not belong to this FragmentManager!");
                                            throw new IllegalStateException(var5_5.toString());
                                        }
                                        if (var3_3.mState < 1) {
                                            this.moveToState((Fragment)var3_3, 1);
                                        }
                                    }
                                    var5_5.attach(this.mHost, this, this.mParent);
                                }
                            }
                            if (var2_2 > 0) {
                                var5_5.create();
                            }
                        }
                        if (var2_2 > -1) {
                            var5_5.ensureInflatedView();
                        }
                        if (var2_2 > 1) {
                            var5_5.createView(this.mContainer);
                            var5_5.activityCreated();
                            var5_5.restoreViewState();
                        }
                    }
                    if (var2_2 > 2) {
                        var5_5.start();
                    }
                }
                var6_6 = var2_2;
                if (var2_2 > 3) {
                    var5_5.resume();
                    var6_6 = var2_2;
                }
                break block48;
            }
            var6_6 = var2_2;
            if (var1_1.mState <= var2_2) break block48;
            var6_6 = var1_1.mState;
            if (var6_6 == 0) ** GOTO lbl154
            var8_8 = 0;
            if (var6_6 == 1) ** GOTO lbl138
            if (var6_6 == 2) ** GOTO lbl101
            if (var6_6 == 3) ** GOTO lbl99
            if (var6_6 != 4) {
                var6_6 = var2_2;
            } else {
                if (var2_2 < 4) {
                    var5_5.pause();
                }
lbl99: // 4 sources:
                if (var2_2 < 3) {
                    var5_5.stop();
                }
lbl101: // 4 sources:
                if (var2_2 < 2) {
                    if (FragmentManager.isLoggingEnabled(3)) {
                        var3_3 = new StringBuilder();
                        var3_3.append("movefrom ACTIVITY_CREATED: ");
                        var3_3.append(var1_1);
                        Log.d((String)"FragmentManager", (String)var3_3.toString());
                    }
                    if (var1_1.mView != null && this.mHost.onShouldSaveFragmentState(var1_1) && var1_1.mSavedViewState == null) {
                        var5_5.saveViewState();
                    }
                    if (var1_1.mView != null && var1_1.mContainer != null) {
                        var1_1.mContainer.endViewTransition(var1_1.mView);
                        var1_1.mView.clearAnimation();
                        if (!var1_1.isRemovingParent()) {
                            var3_3 = var7_7;
                            if (this.mCurState > -1) {
                                var3_3 = var7_7;
                                if (!this.mDestroyed) {
                                    var3_3 = var7_7;
                                    if (var1_1.mView.getVisibility() == 0) {
                                        var3_3 = var7_7;
                                        if (var1_1.mPostponedAlpha >= 0.0f) {
                                            var3_3 = FragmentAnim.loadAnimation(this.mHost.getContext(), this.mContainer, var1_1, false);
                                        }
                                    }
                                }
                            }
                            var1_1.mPostponedAlpha = 0.0f;
                            var7_7 = var1_1.mContainer;
                            var9_9 = var1_1.mView;
                            if (var3_3 != null) {
                                var1_1.setStateAfterAnimating(var2_2);
                                FragmentAnim.animateRemoveFragment(var1_1, (FragmentAnim.AnimationOrAnimator)var3_3, this.mFragmentTransitionCallback);
                            }
                            var7_7.removeView(var9_9);
                            if (var7_7 != var1_1.mContainer) {
                                return;
                            }
                        }
                    }
                    if (this.mExitAnimationCancellationSignals.get(var1_1) == null) {
                        this.destroyFragmentView(var1_1);
                    } else {
                        var1_1.setStateAfterAnimating(var2_2);
                    }
                }
lbl138: // 5 sources:
                if (var2_2 < 1) {
                    var6_6 = var8_8;
                    if (var1_1.mRemoving) {
                        var6_6 = var8_8;
                        if (!var1_1.isInBackStack()) {
                            var6_6 = 1;
                        }
                    }
                    if (var6_6 == 0 && !this.mNonConfig.shouldDestroy(var1_1)) {
                        if (var1_1.mTargetWho != null && (var3_3 = this.findActiveFragment(var1_1.mTargetWho)) != null && var3_3.getRetainInstance()) {
                            var1_1.mTarget = var3_3;
                        }
                    } else {
                        this.makeInactive((FragmentStateManager)var5_5);
                    }
                    if (this.mExitAnimationCancellationSignals.get(var1_1) != null) {
                        var1_1.setStateAfterAnimating(var2_2);
                        var2_2 = var4_4;
                    } else {
                        var5_5.destroy(this.mHost, this.mNonConfig);
                    }
                }
lbl154: // 5 sources:
                if (var2_2 < 0) {
                    var5_5.detach(this.mNonConfig);
                }
                var6_6 = var2_2;
            }
        }
        if (var1_1.mState == var6_6) return;
        if (FragmentManager.isLoggingEnabled(3)) {
            var5_5 = new StringBuilder();
            var5_5.append("moveToState: Fragment state for ");
            var5_5.append(var1_1);
            var5_5.append(" not updated inline; expected state ");
            var5_5.append(var6_6);
            var5_5.append(" found ");
            var5_5.append(var1_1.mState);
            Log.d((String)"FragmentManager", (String)var5_5.toString());
        }
        var1_1.mState = var6_6;
    }

    void noteStateNotSaved() {
        this.mStateSaved = false;
        this.mStopped = false;
        Iterator<Fragment> iterator2 = this.mFragmentStore.getFragments().iterator();
        while (iterator2.hasNext()) {
            Fragment fragment = iterator2.next();
            if (fragment == null) continue;
            fragment.noteStateNotSaved();
        }
    }

    @Deprecated
    public FragmentTransaction openTransaction() {
        return this.beginTransaction();
    }

    void performPendingDeferredStart(Fragment fragment) {
        if (!fragment.mDeferStart) return;
        if (this.mExecutingActions) {
            this.mHavePendingDeferredStart = true;
            return;
        }
        fragment.mDeferStart = false;
        this.moveToState(fragment, this.mCurState);
    }

    public void popBackStack() {
        this.enqueueAction(new PopBackStackState(null, -1, 0), false);
    }

    public void popBackStack(int n, int n2) {
        if (n >= 0) {
            this.enqueueAction(new PopBackStackState(null, n, n2), false);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad id: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void popBackStack(String string2, int n) {
        this.enqueueAction(new PopBackStackState(string2, -1, n), false);
    }

    public boolean popBackStackImmediate() {
        return this.popBackStackImmediate(null, -1, 0);
    }

    public boolean popBackStackImmediate(int n, int n2) {
        if (n >= 0) {
            return this.popBackStackImmediate(null, n, n2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad id: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean popBackStackImmediate(String string2, int n) {
        return this.popBackStackImmediate(string2, -1, n);
    }

    boolean popBackStackState(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, String string2, int n, int n2) {
        ArrayList<BackStackRecord> arrayList3 = this.mBackStack;
        if (arrayList3 == null) {
            return false;
        }
        if (string2 == null && n < 0 && (n2 & 1) == 0) {
            n = arrayList3.size() - 1;
            if (n < 0) {
                return false;
            }
            arrayList.add(this.mBackStack.remove(n));
            arrayList2.add(true);
            return true;
        }
        if (string2 == null && n < 0) {
            n = -1;
        } else {
            int n3;
            for (n3 = this.mBackStack.size() - 1; n3 >= 0; --n3) {
                arrayList3 = this.mBackStack.get(n3);
                if (string2 != null && string2.equals(((BackStackRecord)((Object)arrayList3)).getName()) || n >= 0 && n == ((BackStackRecord)arrayList3).mIndex) break;
            }
            if (n3 < 0) {
                return false;
            }
            int n4 = n3;
            if ((n2 & 1) != 0) {
                do {
                    n4 = n2 = n3 - 1;
                    if (n2 < 0) break;
                    arrayList3 = this.mBackStack.get(n2);
                    if (string2 != null) {
                        n3 = n2;
                        if (string2.equals(((BackStackRecord)((Object)arrayList3)).getName())) continue;
                    }
                    n4 = n2;
                    if (n < 0) break;
                    n4 = n2;
                    if (n != ((BackStackRecord)arrayList3).mIndex) break;
                    n3 = n2;
                } while (true);
            }
            n = n4;
        }
        if (n == this.mBackStack.size() - 1) {
            return false;
        }
        n2 = this.mBackStack.size() - 1;
        while (n2 > n) {
            arrayList.add(this.mBackStack.remove(n2));
            arrayList2.add(true);
            --n2;
        }
        return true;
    }

    public void putFragment(Bundle bundle, String string2, Fragment fragment) {
        if (fragment.mFragmentManager != this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(fragment);
            stringBuilder.append(" is not currently in the FragmentManager");
            this.throwException(new IllegalStateException(stringBuilder.toString()));
        }
        bundle.putString(string2, fragment.mWho);
    }

    public void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean bl) {
        this.mLifecycleCallbacksDispatcher.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, bl);
    }

    void removeCancellationSignal(Fragment fragment, CancellationSignal cancellationSignal) {
        HashSet<CancellationSignal> hashSet = this.mExitAnimationCancellationSignals.get(fragment);
        if (hashSet == null) return;
        if (!hashSet.remove(cancellationSignal)) return;
        if (!hashSet.isEmpty()) return;
        this.mExitAnimationCancellationSignals.remove(fragment);
        if (fragment.mState >= 3) return;
        this.destroyFragmentView(fragment);
        this.moveToState(fragment, fragment.getStateAfterAnimating());
    }

    void removeFragment(Fragment fragment) {
        if (FragmentManager.isLoggingEnabled(2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("remove: ");
            stringBuilder.append(fragment);
            stringBuilder.append(" nesting=");
            stringBuilder.append(fragment.mBackStackNesting);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        boolean bl = fragment.isInBackStack();
        if (fragment.mDetached) {
            if (!(bl ^ true)) return;
        }
        this.mFragmentStore.removeFragment(fragment);
        if (this.isMenuAvailable(fragment)) {
            this.mNeedMenuInvalidate = true;
        }
        fragment.mRemoving = true;
        this.setVisibleRemovingFragment(fragment);
    }

    public void removeOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener) {
        ArrayList<OnBackStackChangedListener> arrayList = this.mBackStackChangeListeners;
        if (arrayList == null) return;
        arrayList.remove(onBackStackChangedListener);
    }

    void removeRetainedFragment(Fragment fragment) {
        if (this.isStateSaved()) {
            if (!FragmentManager.isLoggingEnabled(2)) return;
            Log.v((String)TAG, (String)"Ignoring removeRetainedFragment as the state is already saved");
            return;
        }
        if (!this.mNonConfig.removeRetainedFragment(fragment)) return;
        if (!FragmentManager.isLoggingEnabled(2)) return;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Updating retained Fragments: Removed ");
        stringBuilder.append(fragment);
        Log.v((String)TAG, (String)stringBuilder.toString());
    }

    void restoreAllState(Parcelable parcelable, FragmentManagerNonConfig fragmentManagerNonConfig) {
        if (this.mHost instanceof ViewModelStoreOwner) {
            this.throwException(new IllegalStateException("You must use restoreSaveState when your FragmentHostCallback implements ViewModelStoreOwner"));
        }
        this.mNonConfig.restoreFromSnapshot(fragmentManagerNonConfig);
        this.restoreSaveState(parcelable);
    }

    void restoreSaveState(Parcelable object) {
        if (object == null) {
            return;
        }
        FragmentManagerState fragmentManagerState = (FragmentManagerState)object;
        if (fragmentManagerState.mActive == null) {
            return;
        }
        this.mFragmentStore.resetActiveFragments();
        for (FragmentState fragmentState : fragmentManagerState.mActive) {
            if (fragmentState == null) continue;
            Object object2 = this.mNonConfig.findRetainedFragmentByWho(fragmentState.mWho);
            if (object2 != null) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("restoreSaveState: re-attaching retained ");
                    ((StringBuilder)object).append(object2);
                    Log.v((String)TAG, (String)((StringBuilder)object).toString());
                }
                object = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, (Fragment)object2, fragmentState);
            } else {
                object = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mHost.getContext().getClassLoader(), this.getFragmentFactory(), fragmentState);
            }
            Fragment fragment = ((FragmentStateManager)object).getFragment();
            fragment.mFragmentManager = this;
            if (FragmentManager.isLoggingEnabled(2)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("restoreSaveState: active (");
                ((StringBuilder)object2).append(fragment.mWho);
                ((StringBuilder)object2).append("): ");
                ((StringBuilder)object2).append(fragment);
                Log.v((String)TAG, (String)((StringBuilder)object2).toString());
            }
            ((FragmentStateManager)object).restoreState(this.mHost.getContext().getClassLoader());
            this.mFragmentStore.makeActive((FragmentStateManager)object);
            ((FragmentStateManager)object).setFragmentManagerState(this.mCurState);
        }
        for (Fragment fragment : this.mNonConfig.getRetainedFragments()) {
            if (this.mFragmentStore.containsActiveFragment(fragment.mWho)) continue;
            if (FragmentManager.isLoggingEnabled(2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Discarding retained Fragment ");
                ((StringBuilder)object).append(fragment);
                ((StringBuilder)object).append(" that was not found in the set of active Fragments ");
                ((StringBuilder)object).append(fragmentManagerState.mActive);
                Log.v((String)TAG, (String)((StringBuilder)object).toString());
            }
            this.moveToState(fragment, 1);
            fragment.mRemoving = true;
            this.moveToState(fragment, -1);
        }
        this.mFragmentStore.restoreAddedFragments(fragmentManagerState.mAdded);
        if (fragmentManagerState.mBackStack != null) {
            this.mBackStack = new ArrayList(fragmentManagerState.mBackStack.length);
            for (int i = 0; i < fragmentManagerState.mBackStack.length; ++i) {
                object = fragmentManagerState.mBackStack[i].instantiate(this);
                if (FragmentManager.isLoggingEnabled(2)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("restoreAllState: back stack #");
                    stringBuilder.append(i);
                    stringBuilder.append(" (index ");
                    stringBuilder.append(((BackStackRecord)object).mIndex);
                    stringBuilder.append("): ");
                    stringBuilder.append(object);
                    Log.v((String)TAG, (String)stringBuilder.toString());
                    PrintWriter printWriter = new PrintWriter(new LogWriter(TAG));
                    ((BackStackRecord)object).dump("  ", printWriter, false);
                    printWriter.close();
                }
                this.mBackStack.add((BackStackRecord)object);
            }
        } else {
            this.mBackStack = null;
        }
        this.mBackStackIndex.set(fragmentManagerState.mBackStackIndex);
        if (fragmentManagerState.mPrimaryNavActiveWho == null) return;
        this.mPrimaryNav = object = this.findActiveFragment(fragmentManagerState.mPrimaryNavActiveWho);
        this.dispatchParentPrimaryNavigationFragmentChanged((Fragment)object);
    }

    @Deprecated
    FragmentManagerNonConfig retainNonConfig() {
        if (!(this.mHost instanceof ViewModelStoreOwner)) return this.mNonConfig.getSnapshot();
        this.throwException(new IllegalStateException("You cannot use retainNonConfig when your FragmentHostCallback implements ViewModelStoreOwner."));
        return this.mNonConfig.getSnapshot();
    }

    Parcelable saveAllState() {
        this.forcePostponedTransactions();
        this.endAnimatingAwayFragments();
        this.execPendingActions(true);
        this.mStateSaved = true;
        ArrayList<FragmentState> arrayList = this.mFragmentStore.saveActiveFragments();
        boolean bl = arrayList.isEmpty();
        BackStackState[] arrbackStackState = null;
        if (bl) {
            if (!FragmentManager.isLoggingEnabled(2)) return null;
            Log.v((String)TAG, (String)"saveAllState: no fragments!");
            return null;
        }
        ArrayList<String> arrayList2 = this.mFragmentStore.saveAddedFragments();
        ArrayList<BackStackRecord> arrayList3 = this.mBackStack;
        Object object = arrbackStackState;
        if (arrayList3 != null) {
            int n = arrayList3.size();
            object = arrbackStackState;
            if (n > 0) {
                arrbackStackState = new BackStackState[n];
                int n2 = 0;
                do {
                    object = arrbackStackState;
                    if (n2 >= n) break;
                    arrbackStackState[n2] = new BackStackState(this.mBackStack.get(n2));
                    if (FragmentManager.isLoggingEnabled(2)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("saveAllState: adding back stack #");
                        ((StringBuilder)object).append(n2);
                        ((StringBuilder)object).append(": ");
                        ((StringBuilder)object).append(this.mBackStack.get(n2));
                        Log.v((String)TAG, (String)((StringBuilder)object).toString());
                    }
                    ++n2;
                } while (true);
            }
        }
        arrbackStackState = new FragmentManagerState();
        arrbackStackState.mActive = arrayList;
        arrbackStackState.mAdded = arrayList2;
        arrbackStackState.mBackStack = object;
        arrbackStackState.mBackStackIndex = this.mBackStackIndex.get();
        object = this.mPrimaryNav;
        if (object == null) return arrbackStackState;
        arrbackStackState.mPrimaryNavActiveWho = ((Fragment)object).mWho;
        return arrbackStackState;
    }

    public Fragment.SavedState saveFragmentInstanceState(Fragment fragment) {
        FragmentStateManager fragmentStateManager = this.mFragmentStore.getFragmentStateManager(fragment.mWho);
        if (fragmentStateManager != null) {
            if (fragmentStateManager.getFragment().equals(fragment)) return fragmentStateManager.saveInstanceState();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(fragment);
        stringBuilder.append(" is not currently in the FragmentManager");
        this.throwException(new IllegalStateException(stringBuilder.toString()));
        return fragmentStateManager.saveInstanceState();
    }

    void scheduleCommit() {
        ArrayList<OpGenerator> arrayList = this.mPendingActions;
        synchronized (arrayList) {
            ArrayList<StartEnterTransitionListener> arrayList2 = this.mPostponedTransactions;
            boolean bl = false;
            boolean bl2 = arrayList2 != null && !this.mPostponedTransactions.isEmpty();
            if (this.mPendingActions.size() == 1) {
                bl = true;
            }
            if (!bl2) {
                if (!bl) return;
            }
            this.mHost.getHandler().removeCallbacks(this.mExecCommit);
            this.mHost.getHandler().post(this.mExecCommit);
            this.updateOnBackPressedCallbackEnabled();
            return;
        }
    }

    void setExitAnimationOrder(Fragment fragment, boolean bl) {
        if ((fragment = this.getFragmentContainer(fragment)) == null) return;
        if (!(fragment instanceof FragmentContainerView)) return;
        ((FragmentContainerView)((Object)fragment)).setDrawDisappearingViewsLast(bl ^ true);
    }

    public void setFragmentFactory(FragmentFactory fragmentFactory) {
        this.mFragmentFactory = fragmentFactory;
    }

    void setMaxLifecycle(Fragment fragment, Lifecycle.State object) {
        if (fragment.equals(this.findActiveFragment(fragment.mWho)) && (fragment.mHost == null || fragment.mFragmentManager == this)) {
            fragment.mMaxState = object;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(fragment);
        ((StringBuilder)object).append(" is not an active fragment of FragmentManager ");
        ((StringBuilder)object).append(this);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    void setPrimaryNavigationFragment(Fragment fragment) {
        if (fragment != null && (!fragment.equals(this.findActiveFragment(fragment.mWho)) || fragment.mHost != null && fragment.mFragmentManager != this)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(fragment);
            stringBuilder.append(" is not an active fragment of FragmentManager ");
            stringBuilder.append(this);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        Fragment fragment2 = this.mPrimaryNav;
        this.mPrimaryNav = fragment;
        this.dispatchParentPrimaryNavigationFragmentChanged(fragment2);
        this.dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
    }

    void showFragment(Fragment fragment) {
        if (FragmentManager.isLoggingEnabled(2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("show: ");
            stringBuilder.append(fragment);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        if (!fragment.mHidden) return;
        fragment.mHidden = false;
        fragment.mHiddenChanged ^= true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("FragmentManager{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" in ");
        Fragment fragment = this.mParent;
        if (fragment != null) {
            stringBuilder.append(fragment.getClass().getSimpleName());
            stringBuilder.append("{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this.mParent)));
            stringBuilder.append("}");
        } else {
            stringBuilder.append(this.mHost.getClass().getSimpleName());
            stringBuilder.append("{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this.mHost)));
            stringBuilder.append("}");
        }
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    public void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        this.mLifecycleCallbacksDispatcher.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks);
    }

    public static interface BackStackEntry {
        @Deprecated
        public CharSequence getBreadCrumbShortTitle();

        @Deprecated
        public int getBreadCrumbShortTitleRes();

        @Deprecated
        public CharSequence getBreadCrumbTitle();

        @Deprecated
        public int getBreadCrumbTitleRes();

        public int getId();

        public String getName();
    }

    public static abstract class FragmentLifecycleCallbacks {
        public void onFragmentActivityCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void onFragmentAttached(FragmentManager fragmentManager, Fragment fragment, Context context) {
        }

        public void onFragmentCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void onFragmentDestroyed(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentDetached(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentPaused(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentPreAttached(FragmentManager fragmentManager, Fragment fragment, Context context) {
        }

        public void onFragmentPreCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void onFragmentResumed(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentSaveInstanceState(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void onFragmentStarted(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentStopped(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void onFragmentViewCreated(FragmentManager fragmentManager, Fragment fragment, View view, Bundle bundle) {
        }

        public void onFragmentViewDestroyed(FragmentManager fragmentManager, Fragment fragment) {
        }
    }

    public static interface OnBackStackChangedListener {
        public void onBackStackChanged();
    }

    static interface OpGenerator {
        public boolean generateOps(ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2);
    }

    private class PopBackStackState
    implements OpGenerator {
        final int mFlags;
        final int mId;
        final String mName;

        PopBackStackState(String string2, int n, int n2) {
            this.mName = string2;
            this.mId = n;
            this.mFlags = n2;
        }

        @Override
        public boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
            if (FragmentManager.this.mPrimaryNav == null) return FragmentManager.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
            if (this.mId >= 0) return FragmentManager.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
            if (this.mName != null) return FragmentManager.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
            if (!FragmentManager.this.mPrimaryNav.getChildFragmentManager().popBackStackImmediate()) return FragmentManager.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
            return false;
        }
    }

    static class StartEnterTransitionListener
    implements Fragment.OnStartEnterTransitionListener {
        final boolean mIsBack;
        private int mNumPostponed;
        final BackStackRecord mRecord;

        StartEnterTransitionListener(BackStackRecord backStackRecord, boolean bl) {
            this.mIsBack = bl;
            this.mRecord = backStackRecord;
        }

        void cancelTransaction() {
            this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
        }

        void completeTransaction() {
            boolean bl = this.mNumPostponed > 0;
            Iterator<Fragment> iterator2 = this.mRecord.mManager.getFragments().iterator();
            do {
                if (!iterator2.hasNext()) {
                    this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, bl ^ true, true);
                    return;
                }
                Fragment fragment = iterator2.next();
                fragment.setOnStartEnterTransitionListener(null);
                if (!bl || !fragment.isPostponed()) continue;
                fragment.startPostponedEnterTransition();
            } while (true);
        }

        public boolean isReady() {
            if (this.mNumPostponed != 0) return false;
            return true;
        }

        @Override
        public void onStartEnterTransition() {
            int n;
            this.mNumPostponed = n = this.mNumPostponed - 1;
            if (n != 0) {
                return;
            }
            this.mRecord.mManager.scheduleCommit();
        }

        @Override
        public void startListening() {
            ++this.mNumPostponed;
        }
    }

}

