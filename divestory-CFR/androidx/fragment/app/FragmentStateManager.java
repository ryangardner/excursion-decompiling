/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.fragment.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.fragment.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentHostCallback;
import androidx.fragment.app.FragmentLifecycleCallbacksDispatcher;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerViewModel;
import androidx.fragment.app.FragmentState;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelStoreOwner;

class FragmentStateManager {
    private static final String TAG = "FragmentManager";
    private static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    private static final String TARGET_STATE_TAG = "android:target_state";
    private static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    private static final String VIEW_STATE_TAG = "android:view_state";
    private final FragmentLifecycleCallbacksDispatcher mDispatcher;
    private final Fragment mFragment;
    private int mFragmentManagerState = -1;

    FragmentStateManager(FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, Fragment fragment) {
        this.mDispatcher = fragmentLifecycleCallbacksDispatcher;
        this.mFragment = fragment;
    }

    FragmentStateManager(FragmentLifecycleCallbacksDispatcher object, Fragment fragment, FragmentState fragmentState) {
        this.mDispatcher = object;
        this.mFragment = fragment;
        fragment.mSavedViewState = null;
        this.mFragment.mBackStackNesting = 0;
        this.mFragment.mInLayout = false;
        this.mFragment.mAdded = false;
        fragment = this.mFragment;
        object = fragment.mTarget != null ? this.mFragment.mTarget.mWho : null;
        fragment.mTargetWho = object;
        this.mFragment.mTarget = null;
        if (fragmentState.mSavedFragmentState != null) {
            this.mFragment.mSavedFragmentState = fragmentState.mSavedFragmentState;
            return;
        }
        this.mFragment.mSavedFragmentState = new Bundle();
    }

    FragmentStateManager(FragmentLifecycleCallbacksDispatcher object, ClassLoader classLoader, FragmentFactory fragmentFactory, FragmentState fragmentState) {
        this.mDispatcher = object;
        this.mFragment = fragmentFactory.instantiate(classLoader, fragmentState.mClassName);
        if (fragmentState.mArguments != null) {
            fragmentState.mArguments.setClassLoader(classLoader);
        }
        this.mFragment.setArguments(fragmentState.mArguments);
        this.mFragment.mWho = fragmentState.mWho;
        this.mFragment.mFromLayout = fragmentState.mFromLayout;
        this.mFragment.mRestored = true;
        this.mFragment.mFragmentId = fragmentState.mFragmentId;
        this.mFragment.mContainerId = fragmentState.mContainerId;
        this.mFragment.mTag = fragmentState.mTag;
        this.mFragment.mRetainInstance = fragmentState.mRetainInstance;
        this.mFragment.mRemoving = fragmentState.mRemoving;
        this.mFragment.mDetached = fragmentState.mDetached;
        this.mFragment.mHidden = fragmentState.mHidden;
        this.mFragment.mMaxState = Lifecycle.State.values()[fragmentState.mMaxLifecycleState];
        this.mFragment.mSavedFragmentState = fragmentState.mSavedFragmentState != null ? fragmentState.mSavedFragmentState : new Bundle();
        if (!FragmentManager.isLoggingEnabled(2)) return;
        object = new StringBuilder();
        ((StringBuilder)object).append("Instantiated fragment ");
        ((StringBuilder)object).append(this.mFragment);
        Log.v((String)TAG, (String)((StringBuilder)object).toString());
    }

    private Bundle saveBasicState() {
        Bundle bundle = new Bundle();
        this.mFragment.performSaveInstanceState(bundle);
        this.mDispatcher.dispatchOnFragmentSaveInstanceState(this.mFragment, bundle, false);
        Bundle bundle2 = bundle;
        if (bundle.isEmpty()) {
            bundle2 = null;
        }
        if (this.mFragment.mView != null) {
            this.saveViewState();
        }
        bundle = bundle2;
        if (this.mFragment.mSavedViewState != null) {
            bundle = bundle2;
            if (bundle2 == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray(VIEW_STATE_TAG, this.mFragment.mSavedViewState);
        }
        bundle2 = bundle;
        if (this.mFragment.mUserVisibleHint) return bundle2;
        bundle2 = bundle;
        if (bundle == null) {
            bundle2 = new Bundle();
        }
        bundle2.putBoolean(USER_VISIBLE_HINT_TAG, this.mFragment.mUserVisibleHint);
        return bundle2;
    }

    void activityCreated() {
        Object object;
        if (FragmentManager.isLoggingEnabled(3)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("moveto ACTIVITY_CREATED: ");
            ((StringBuilder)object).append(this.mFragment);
            Log.d((String)TAG, (String)((StringBuilder)object).toString());
        }
        object = this.mFragment;
        ((Fragment)object).performActivityCreated(((Fragment)object).mSavedFragmentState);
        object = this.mDispatcher;
        Fragment fragment = this.mFragment;
        ((FragmentLifecycleCallbacksDispatcher)object).dispatchOnFragmentActivityCreated(fragment, fragment.mSavedFragmentState, false);
    }

    void attach(FragmentHostCallback<?> fragmentHostCallback, FragmentManager fragmentManager, Fragment fragment) {
        this.mFragment.mHost = fragmentHostCallback;
        this.mFragment.mParentFragment = fragment;
        this.mFragment.mFragmentManager = fragmentManager;
        this.mDispatcher.dispatchOnFragmentPreAttached(this.mFragment, fragmentHostCallback.getContext(), false);
        this.mFragment.performAttach();
        if (this.mFragment.mParentFragment == null) {
            fragmentHostCallback.onAttachFragment(this.mFragment);
        } else {
            this.mFragment.mParentFragment.onAttachFragment(this.mFragment);
        }
        this.mDispatcher.dispatchOnFragmentAttached(this.mFragment, fragmentHostCallback.getContext(), false);
    }

    int computeMaxState() {
        int n;
        int n2 = n = this.mFragmentManagerState;
        if (this.mFragment.mFromLayout) {
            n2 = this.mFragment.mInLayout ? Math.max(this.mFragmentManagerState, 1) : Math.min(n, 1);
        }
        n = n2;
        if (!this.mFragment.mAdded) {
            n = Math.min(n2, 1);
        }
        n2 = n;
        if (this.mFragment.mRemoving) {
            n2 = this.mFragment.isInBackStack() ? Math.min(n, 1) : Math.min(n, -1);
        }
        n = n2;
        if (this.mFragment.mDeferStart) {
            n = n2;
            if (this.mFragment.mState < 3) {
                n = Math.min(n2, 2);
            }
        }
        int n3 = 1.$SwitchMap$androidx$lifecycle$Lifecycle$State[this.mFragment.mMaxState.ordinal()];
        n2 = n;
        if (n3 == 1) return n2;
        if (n3 == 2) {
            return Math.min(n, 3);
        }
        if (n3 == 3) return Math.min(n, 1);
        return Math.min(n, -1);
    }

    void create() {
        Object object;
        if (FragmentManager.isLoggingEnabled(3)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("moveto CREATED: ");
            ((StringBuilder)object).append(this.mFragment);
            Log.d((String)TAG, (String)((StringBuilder)object).toString());
        }
        if (!this.mFragment.mIsCreated) {
            object = this.mDispatcher;
            Object object2 = this.mFragment;
            ((FragmentLifecycleCallbacksDispatcher)object).dispatchOnFragmentPreCreated((Fragment)object2, ((Fragment)object2).mSavedFragmentState, false);
            object = this.mFragment;
            ((Fragment)object).performCreate(((Fragment)object).mSavedFragmentState);
            object2 = this.mDispatcher;
            object = this.mFragment;
            ((FragmentLifecycleCallbacksDispatcher)object2).dispatchOnFragmentCreated((Fragment)object, ((Fragment)object).mSavedFragmentState, false);
            return;
        }
        object = this.mFragment;
        ((Fragment)object).restoreChildFragmentState(((Fragment)object).mSavedFragmentState);
        this.mFragment.mState = 1;
    }

    void createView(FragmentContainer object) {
        Object object2;
        if (this.mFragment.mFromLayout) {
            return;
        }
        if (FragmentManager.isLoggingEnabled(3)) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("moveto CREATE_VIEW: ");
            ((StringBuilder)object2).append(this.mFragment);
            Log.d((String)TAG, (String)((StringBuilder)object2).toString());
        }
        object2 = null;
        if (this.mFragment.mContainer != null) {
            object2 = this.mFragment.mContainer;
        } else if (this.mFragment.mContainerId != 0) {
            if (this.mFragment.mContainerId == -1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Cannot create fragment ");
                ((StringBuilder)object).append(this.mFragment);
                ((StringBuilder)object).append(" for a container view with no id");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            object2 = object = (ViewGroup)((FragmentContainer)object).onFindViewById(this.mFragment.mContainerId);
            if (object == null) {
                if (this.mFragment.mRestored) {
                    object2 = object;
                } else {
                    try {
                        object = this.mFragment.getResources().getResourceName(this.mFragment.mContainerId);
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        object = "unknown";
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("No view found for id 0x");
                    ((StringBuilder)object2).append(Integer.toHexString(this.mFragment.mContainerId));
                    ((StringBuilder)object2).append(" (");
                    ((StringBuilder)object2).append((String)object);
                    ((StringBuilder)object2).append(") for fragment ");
                    ((StringBuilder)object2).append(this.mFragment);
                    throw new IllegalArgumentException(((StringBuilder)object2).toString());
                }
            }
        }
        this.mFragment.mContainer = object2;
        object = this.mFragment;
        ((Fragment)object).performCreateView(((Fragment)object).performGetLayoutInflater(((Fragment)object).mSavedFragmentState), (ViewGroup)object2, this.mFragment.mSavedFragmentState);
        if (this.mFragment.mView == null) return;
        object = this.mFragment.mView;
        boolean bl = false;
        object.setSaveFromParentEnabled(false);
        this.mFragment.mView.setTag(R.id.fragment_container_view_tag, (Object)this.mFragment);
        if (object2 != null) {
            object2.addView(this.mFragment.mView);
        }
        if (this.mFragment.mHidden) {
            this.mFragment.mView.setVisibility(8);
        }
        ViewCompat.requestApplyInsets(this.mFragment.mView);
        object = this.mFragment;
        ((Fragment)object).onViewCreated(((Fragment)object).mView, this.mFragment.mSavedFragmentState);
        object2 = this.mDispatcher;
        object = this.mFragment;
        ((FragmentLifecycleCallbacksDispatcher)object2).dispatchOnFragmentViewCreated((Fragment)object, ((Fragment)object).mView, this.mFragment.mSavedFragmentState, false);
        object = this.mFragment;
        boolean bl2 = bl;
        if (((Fragment)object).mView.getVisibility() == 0) {
            bl2 = bl;
            if (this.mFragment.mContainer != null) {
                bl2 = true;
            }
        }
        ((Fragment)object).mIsNewlyAdded = bl2;
    }

    void destroy(FragmentHostCallback<?> fragmentHostCallback, FragmentManagerViewModel fragmentManagerViewModel) {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("movefrom CREATED: ");
            stringBuilder.append(this.mFragment);
            Log.d((String)TAG, (String)stringBuilder.toString());
        }
        boolean bl = this.mFragment.mRemoving;
        boolean bl2 = true;
        boolean bl3 = bl && !this.mFragment.isInBackStack();
        boolean bl4 = bl3 || fragmentManagerViewModel.shouldDestroy(this.mFragment);
        if (!bl4) {
            this.mFragment.mState = 0;
            return;
        }
        if (fragmentHostCallback instanceof ViewModelStoreOwner) {
            bl2 = fragmentManagerViewModel.isCleared();
        } else if (fragmentHostCallback.getContext() instanceof Activity) {
            bl2 = true ^ ((Activity)fragmentHostCallback.getContext()).isChangingConfigurations();
        }
        if (bl3 || bl2) {
            fragmentManagerViewModel.clearNonConfigState(this.mFragment);
        }
        this.mFragment.performDestroy();
        this.mDispatcher.dispatchOnFragmentDestroyed(this.mFragment, false);
    }

    void detach(FragmentManagerViewModel object) {
        Object object2;
        if (FragmentManager.isLoggingEnabled(3)) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("movefrom ATTACHED: ");
            ((StringBuilder)object2).append(this.mFragment);
            Log.d((String)TAG, (String)((StringBuilder)object2).toString());
        }
        this.mFragment.performDetach();
        object2 = this.mDispatcher;
        Fragment fragment = this.mFragment;
        boolean bl = false;
        ((FragmentLifecycleCallbacksDispatcher)object2).dispatchOnFragmentDetached(fragment, false);
        this.mFragment.mState = -1;
        this.mFragment.mHost = null;
        this.mFragment.mParentFragment = null;
        this.mFragment.mFragmentManager = null;
        boolean bl2 = bl;
        if (this.mFragment.mRemoving) {
            bl2 = bl;
            if (!this.mFragment.isInBackStack()) {
                bl2 = true;
            }
        }
        if (!bl2) {
            if (!((FragmentManagerViewModel)object).shouldDestroy(this.mFragment)) return;
        }
        if (FragmentManager.isLoggingEnabled(3)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("initState called for fragment: ");
            ((StringBuilder)object).append(this.mFragment);
            Log.d((String)TAG, (String)((StringBuilder)object).toString());
        }
        this.mFragment.initState();
    }

    void ensureInflatedView() {
        Object object;
        if (!this.mFragment.mFromLayout) return;
        if (!this.mFragment.mInLayout) return;
        if (this.mFragment.mPerformedCreateView) return;
        if (FragmentManager.isLoggingEnabled(3)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("moveto CREATE_VIEW: ");
            ((StringBuilder)object).append(this.mFragment);
            Log.d((String)TAG, (String)((StringBuilder)object).toString());
        }
        object = this.mFragment;
        ((Fragment)object).performCreateView(((Fragment)object).performGetLayoutInflater(((Fragment)object).mSavedFragmentState), null, this.mFragment.mSavedFragmentState);
        if (this.mFragment.mView == null) return;
        this.mFragment.mView.setSaveFromParentEnabled(false);
        if (this.mFragment.mHidden) {
            this.mFragment.mView.setVisibility(8);
        }
        object = this.mFragment;
        ((Fragment)object).onViewCreated(((Fragment)object).mView, this.mFragment.mSavedFragmentState);
        FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
        object = this.mFragment;
        fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentViewCreated((Fragment)object, ((Fragment)object).mView, this.mFragment.mSavedFragmentState, false);
    }

    Fragment getFragment() {
        return this.mFragment;
    }

    void pause() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("movefrom RESUMED: ");
            stringBuilder.append(this.mFragment);
            Log.d((String)TAG, (String)stringBuilder.toString());
        }
        this.mFragment.performPause();
        this.mDispatcher.dispatchOnFragmentPaused(this.mFragment, false);
    }

    void restoreState(ClassLoader object) {
        if (this.mFragment.mSavedFragmentState == null) {
            return;
        }
        this.mFragment.mSavedFragmentState.setClassLoader((ClassLoader)object);
        object = this.mFragment;
        ((Fragment)object).mSavedViewState = ((Fragment)object).mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
        object = this.mFragment;
        ((Fragment)object).mTargetWho = ((Fragment)object).mSavedFragmentState.getString(TARGET_STATE_TAG);
        if (this.mFragment.mTargetWho != null) {
            object = this.mFragment;
            ((Fragment)object).mTargetRequestCode = ((Fragment)object).mSavedFragmentState.getInt(TARGET_REQUEST_CODE_STATE_TAG, 0);
        }
        if (this.mFragment.mSavedUserVisibleHint != null) {
            object = this.mFragment;
            ((Fragment)object).mUserVisibleHint = ((Fragment)object).mSavedUserVisibleHint;
            this.mFragment.mSavedUserVisibleHint = null;
        } else {
            object = this.mFragment;
            ((Fragment)object).mUserVisibleHint = ((Fragment)object).mSavedFragmentState.getBoolean(USER_VISIBLE_HINT_TAG, true);
        }
        if (this.mFragment.mUserVisibleHint) return;
        this.mFragment.mDeferStart = true;
    }

    void restoreViewState() {
        Object object;
        if (FragmentManager.isLoggingEnabled(3)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("moveto RESTORE_VIEW_STATE: ");
            ((StringBuilder)object).append(this.mFragment);
            Log.d((String)TAG, (String)((StringBuilder)object).toString());
        }
        if (this.mFragment.mView != null) {
            object = this.mFragment;
            ((Fragment)object).restoreViewState(((Fragment)object).mSavedFragmentState);
        }
        this.mFragment.mSavedFragmentState = null;
    }

    void resume() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("moveto RESUMED: ");
            stringBuilder.append(this.mFragment);
            Log.d((String)TAG, (String)stringBuilder.toString());
        }
        this.mFragment.performResume();
        this.mDispatcher.dispatchOnFragmentResumed(this.mFragment, false);
        this.mFragment.mSavedFragmentState = null;
        this.mFragment.mSavedViewState = null;
    }

    Fragment.SavedState saveInstanceState() {
        Fragment.SavedState savedState;
        int n = this.mFragment.mState;
        Fragment.SavedState savedState2 = savedState = null;
        if (n <= -1) return savedState2;
        Bundle bundle = this.saveBasicState();
        savedState2 = savedState;
        if (bundle == null) return savedState2;
        return new Fragment.SavedState(bundle);
    }

    FragmentState saveState() {
        FragmentState fragmentState = new FragmentState(this.mFragment);
        if (this.mFragment.mState > -1 && fragmentState.mSavedFragmentState == null) {
            fragmentState.mSavedFragmentState = this.saveBasicState();
            if (this.mFragment.mTargetWho == null) return fragmentState;
            if (fragmentState.mSavedFragmentState == null) {
                fragmentState.mSavedFragmentState = new Bundle();
            }
            fragmentState.mSavedFragmentState.putString(TARGET_STATE_TAG, this.mFragment.mTargetWho);
            if (this.mFragment.mTargetRequestCode == 0) return fragmentState;
            fragmentState.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, this.mFragment.mTargetRequestCode);
            return fragmentState;
        }
        fragmentState.mSavedFragmentState = this.mFragment.mSavedFragmentState;
        return fragmentState;
    }

    void saveViewState() {
        if (this.mFragment.mView == null) {
            return;
        }
        SparseArray sparseArray = new SparseArray();
        this.mFragment.mView.saveHierarchyState(sparseArray);
        if (sparseArray.size() <= 0) return;
        this.mFragment.mSavedViewState = sparseArray;
    }

    void setFragmentManagerState(int n) {
        this.mFragmentManagerState = n;
    }

    void start() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("moveto STARTED: ");
            stringBuilder.append(this.mFragment);
            Log.d((String)TAG, (String)stringBuilder.toString());
        }
        this.mFragment.performStart();
        this.mDispatcher.dispatchOnFragmentStarted(this.mFragment, false);
    }

    void stop() {
        if (FragmentManager.isLoggingEnabled(3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("movefrom STARTED: ");
            stringBuilder.append(this.mFragment);
            Log.d((String)TAG, (String)stringBuilder.toString());
        }
        this.mFragment.performStop();
        this.mDispatcher.dispatchOnFragmentStopped(this.mFragment, false);
    }

}

