/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.app.Activity
 *  android.app.Application
 *  android.content.ComponentCallbacks
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.SparseArray
 *  android.view.ContextMenu
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.LayoutInflater
 *  android.view.LayoutInflater$Factory2
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnCreateContextMenuListener
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 */
package androidx.fragment.app;

import android.animation.Animator;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.core.app.SharedElementCallback;
import androidx.core.view.LayoutInflaterCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentHostCallback;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.fragment.app.FragmentViewLifecycleOwner;
import androidx.fragment.app.SuperNotCalledException;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManager;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Fragment
implements ComponentCallbacks,
View.OnCreateContextMenuListener,
LifecycleOwner,
ViewModelStoreOwner,
HasDefaultViewModelProviderFactory,
SavedStateRegistryOwner {
    static final int ACTIVITY_CREATED = 2;
    static final int ATTACHED = 0;
    static final int CREATED = 1;
    static final int INITIALIZING = -1;
    static final int RESUMED = 4;
    static final int STARTED = 3;
    static final Object USE_DEFAULT_TRANSITION = new Object();
    boolean mAdded;
    AnimationInfo mAnimationInfo;
    Bundle mArguments;
    int mBackStackNesting;
    private boolean mCalled;
    FragmentManager mChildFragmentManager = new FragmentManagerImpl();
    ViewGroup mContainer;
    int mContainerId;
    private int mContentLayoutId;
    private ViewModelProvider.Factory mDefaultFactory;
    boolean mDeferStart;
    boolean mDetached;
    int mFragmentId;
    FragmentManager mFragmentManager;
    boolean mFromLayout;
    boolean mHasMenu;
    boolean mHidden;
    boolean mHiddenChanged;
    FragmentHostCallback<?> mHost;
    boolean mInLayout;
    boolean mIsCreated;
    boolean mIsNewlyAdded;
    private Boolean mIsPrimaryNavigationFragment = null;
    LayoutInflater mLayoutInflater;
    LifecycleRegistry mLifecycleRegistry;
    Lifecycle.State mMaxState = Lifecycle.State.RESUMED;
    boolean mMenuVisible = true;
    Fragment mParentFragment;
    boolean mPerformedCreateView;
    float mPostponedAlpha;
    Runnable mPostponedDurationRunnable = new Runnable(){

        @Override
        public void run() {
            Fragment.this.startPostponedEnterTransition();
        }
    };
    boolean mRemoving;
    boolean mRestored;
    boolean mRetainInstance;
    boolean mRetainInstanceChangedWhileDetached;
    Bundle mSavedFragmentState;
    SavedStateRegistryController mSavedStateRegistryController;
    Boolean mSavedUserVisibleHint;
    SparseArray<Parcelable> mSavedViewState;
    int mState = -1;
    String mTag;
    Fragment mTarget;
    int mTargetRequestCode;
    String mTargetWho = null;
    boolean mUserVisibleHint = true;
    View mView;
    FragmentViewLifecycleOwner mViewLifecycleOwner;
    MutableLiveData<LifecycleOwner> mViewLifecycleOwnerLiveData = new MutableLiveData();
    String mWho = UUID.randomUUID().toString();

    public Fragment() {
        this.initLifecycle();
    }

    public Fragment(int n) {
        this();
        this.mContentLayoutId = n;
    }

    private AnimationInfo ensureAnimationInfo() {
        if (this.mAnimationInfo != null) return this.mAnimationInfo;
        this.mAnimationInfo = new AnimationInfo();
        return this.mAnimationInfo;
    }

    private void initLifecycle() {
        this.mLifecycleRegistry = new LifecycleRegistry(this);
        this.mSavedStateRegistryController = SavedStateRegistryController.create(this);
        if (Build.VERSION.SDK_INT < 19) return;
        this.mLifecycleRegistry.addObserver(new LifecycleEventObserver(){

            @Override
            public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                if (event != Lifecycle.Event.ON_STOP) return;
                if (Fragment.this.mView == null) return;
                Fragment.this.mView.cancelPendingInputEvents();
            }
        });
    }

    @Deprecated
    public static Fragment instantiate(Context context, String string2) {
        return Fragment.instantiate(context, string2, null);
    }

    @Deprecated
    public static Fragment instantiate(Context object, String string2, Bundle object2) {
        try {
            object = FragmentFactory.loadFragmentClass(object.getClassLoader(), string2).getConstructor(new Class[0]).newInstance(new Object[0]);
            if (object2 == null) return object;
            object2.setClassLoader(object.getClass().getClassLoader());
            ((Fragment)object).setArguments((Bundle)object2);
            return object;
        }
        catch (InvocationTargetException invocationTargetException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unable to instantiate fragment ");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(": calling Fragment constructor caused an exception");
            throw new InstantiationException(((StringBuilder)object2).toString(), invocationTargetException);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unable to instantiate fragment ");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(": could not find Fragment constructor");
            throw new InstantiationException(((StringBuilder)object2).toString(), noSuchMethodException);
        }
        catch (IllegalAccessException illegalAccessException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to instantiate fragment ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(": make sure class name exists, is public, and has an empty constructor that is public");
            throw new InstantiationException(((StringBuilder)object).toString(), illegalAccessException);
        }
        catch (java.lang.InstantiationException instantiationException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to instantiate fragment ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(": make sure class name exists, is public, and has an empty constructor that is public");
            throw new InstantiationException(((StringBuilder)object).toString(), instantiationException);
        }
    }

    void callStartTransitionListener() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        OnStartEnterTransitionListener onStartEnterTransitionListener = null;
        if (animationInfo != null) {
            animationInfo.mEnterTransitionPostponed = false;
            onStartEnterTransitionListener = this.mAnimationInfo.mStartEnterTransitionListener;
            this.mAnimationInfo.mStartEnterTransitionListener = null;
        }
        if (onStartEnterTransitionListener == null) return;
        onStartEnterTransitionListener.onStartEnterTransition();
    }

    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        Object object;
        printWriter.print(string2);
        printWriter.print("mFragmentId=#");
        printWriter.print(Integer.toHexString(this.mFragmentId));
        printWriter.print(" mContainerId=#");
        printWriter.print(Integer.toHexString(this.mContainerId));
        printWriter.print(" mTag=");
        printWriter.println(this.mTag);
        printWriter.print(string2);
        printWriter.print("mState=");
        printWriter.print(this.mState);
        printWriter.print(" mWho=");
        printWriter.print(this.mWho);
        printWriter.print(" mBackStackNesting=");
        printWriter.println(this.mBackStackNesting);
        printWriter.print(string2);
        printWriter.print("mAdded=");
        printWriter.print(this.mAdded);
        printWriter.print(" mRemoving=");
        printWriter.print(this.mRemoving);
        printWriter.print(" mFromLayout=");
        printWriter.print(this.mFromLayout);
        printWriter.print(" mInLayout=");
        printWriter.println(this.mInLayout);
        printWriter.print(string2);
        printWriter.print("mHidden=");
        printWriter.print(this.mHidden);
        printWriter.print(" mDetached=");
        printWriter.print(this.mDetached);
        printWriter.print(" mMenuVisible=");
        printWriter.print(this.mMenuVisible);
        printWriter.print(" mHasMenu=");
        printWriter.println(this.mHasMenu);
        printWriter.print(string2);
        printWriter.print("mRetainInstance=");
        printWriter.print(this.mRetainInstance);
        printWriter.print(" mUserVisibleHint=");
        printWriter.println(this.mUserVisibleHint);
        if (this.mFragmentManager != null) {
            printWriter.print(string2);
            printWriter.print("mFragmentManager=");
            printWriter.println(this.mFragmentManager);
        }
        if (this.mHost != null) {
            printWriter.print(string2);
            printWriter.print("mHost=");
            printWriter.println(this.mHost);
        }
        if (this.mParentFragment != null) {
            printWriter.print(string2);
            printWriter.print("mParentFragment=");
            printWriter.println(this.mParentFragment);
        }
        if (this.mArguments != null) {
            printWriter.print(string2);
            printWriter.print("mArguments=");
            printWriter.println((Object)this.mArguments);
        }
        if (this.mSavedFragmentState != null) {
            printWriter.print(string2);
            printWriter.print("mSavedFragmentState=");
            printWriter.println((Object)this.mSavedFragmentState);
        }
        if (this.mSavedViewState != null) {
            printWriter.print(string2);
            printWriter.print("mSavedViewState=");
            printWriter.println(this.mSavedViewState);
        }
        if ((object = this.getTargetFragment()) != null) {
            printWriter.print(string2);
            printWriter.print("mTarget=");
            printWriter.print(object);
            printWriter.print(" mTargetRequestCode=");
            printWriter.println(this.mTargetRequestCode);
        }
        if (this.getNextAnim() != 0) {
            printWriter.print(string2);
            printWriter.print("mNextAnim=");
            printWriter.println(this.getNextAnim());
        }
        if (this.mContainer != null) {
            printWriter.print(string2);
            printWriter.print("mContainer=");
            printWriter.println((Object)this.mContainer);
        }
        if (this.mView != null) {
            printWriter.print(string2);
            printWriter.print("mView=");
            printWriter.println((Object)this.mView);
        }
        if (this.getAnimatingAway() != null) {
            printWriter.print(string2);
            printWriter.print("mAnimatingAway=");
            printWriter.println((Object)this.getAnimatingAway());
            printWriter.print(string2);
            printWriter.print("mStateAfterAnimating=");
            printWriter.println(this.getStateAfterAnimating());
        }
        if (this.getContext() != null) {
            LoaderManager.getInstance(this).dump(string2, fileDescriptor, printWriter, arrstring);
        }
        printWriter.print(string2);
        object = new StringBuilder();
        ((StringBuilder)object).append("Child ");
        ((StringBuilder)object).append(this.mChildFragmentManager);
        ((StringBuilder)object).append(":");
        printWriter.println(((StringBuilder)object).toString());
        object = this.mChildFragmentManager;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("  ");
        ((FragmentManager)object).dump(stringBuilder.toString(), fileDescriptor, printWriter, arrstring);
    }

    public final boolean equals(Object object) {
        return super.equals(object);
    }

    Fragment findFragmentByWho(String string2) {
        if (!string2.equals(this.mWho)) return this.mChildFragmentManager.findFragmentByWho(string2);
        return this;
    }

    public final FragmentActivity getActivity() {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) return (FragmentActivity)fragmentHostCallback.getActivity();
        return null;
    }

    public boolean getAllowEnterTransitionOverlap() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) return true;
        if (animationInfo.mAllowEnterTransitionOverlap == null) return true;
        return this.mAnimationInfo.mAllowEnterTransitionOverlap;
    }

    public boolean getAllowReturnTransitionOverlap() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) return true;
        if (animationInfo.mAllowReturnTransitionOverlap == null) return true;
        return this.mAnimationInfo.mAllowReturnTransitionOverlap;
    }

    View getAnimatingAway() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo != null) return animationInfo.mAnimatingAway;
        return null;
    }

    Animator getAnimator() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo != null) return animationInfo.mAnimator;
        return null;
    }

    public final Bundle getArguments() {
        return this.mArguments;
    }

    public final FragmentManager getChildFragmentManager() {
        if (this.mHost != null) {
            return this.mChildFragmentManager;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" has not been attached yet.");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public Context getContext() {
        Context context = this.mHost;
        if (context != null) return context.getContext();
        return null;
    }

    @Override
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        if (this.mFragmentManager == null) throw new IllegalStateException("Can't access ViewModels from detached fragment");
        if (this.mDefaultFactory != null) return this.mDefaultFactory;
        this.mDefaultFactory = new SavedStateViewModelFactory(this.requireActivity().getApplication(), this, this.getArguments());
        return this.mDefaultFactory;
    }

    public Object getEnterTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo != null) return animationInfo.mEnterTransition;
        return null;
    }

    SharedElementCallback getEnterTransitionCallback() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo != null) return animationInfo.mEnterTransitionCallback;
        return null;
    }

    public Object getExitTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo != null) return animationInfo.mExitTransition;
        return null;
    }

    SharedElementCallback getExitTransitionCallback() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo != null) return animationInfo.mExitTransitionCallback;
        return null;
    }

    @Deprecated
    public final FragmentManager getFragmentManager() {
        return this.mFragmentManager;
    }

    public final Object getHost() {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) return fragmentHostCallback.onGetHost();
        return null;
    }

    public final int getId() {
        return this.mFragmentId;
    }

    public final LayoutInflater getLayoutInflater() {
        LayoutInflater layoutInflater;
        LayoutInflater layoutInflater2 = layoutInflater = this.mLayoutInflater;
        if (layoutInflater != null) return layoutInflater2;
        return this.performGetLayoutInflater(null);
    }

    @Deprecated
    public LayoutInflater getLayoutInflater(Bundle object) {
        object = this.mHost;
        if (object == null) throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
        object = ((FragmentHostCallback)object).onGetLayoutInflater();
        LayoutInflaterCompat.setFactory2((LayoutInflater)object, this.mChildFragmentManager.getLayoutInflaterFactory());
        return object;
    }

    @Override
    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    @Deprecated
    public LoaderManager getLoaderManager() {
        return LoaderManager.getInstance(this);
    }

    int getNextAnim() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo != null) return animationInfo.mNextAnim;
        return 0;
    }

    int getNextTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo != null) return animationInfo.mNextTransition;
        return 0;
    }

    public final Fragment getParentFragment() {
        return this.mParentFragment;
    }

    public final FragmentManager getParentFragmentManager() {
        Object object = this.mFragmentManager;
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not associated with a fragment manager.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public Object getReenterTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return null;
        }
        if (animationInfo.mReenterTransition != USE_DEFAULT_TRANSITION) return this.mAnimationInfo.mReenterTransition;
        return this.getExitTransition();
    }

    public final Resources getResources() {
        return this.requireContext().getResources();
    }

    public final boolean getRetainInstance() {
        return this.mRetainInstance;
    }

    public Object getReturnTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return null;
        }
        if (animationInfo.mReturnTransition != USE_DEFAULT_TRANSITION) return this.mAnimationInfo.mReturnTransition;
        return this.getEnterTransition();
    }

    @Override
    public final SavedStateRegistry getSavedStateRegistry() {
        return this.mSavedStateRegistryController.getSavedStateRegistry();
    }

    public Object getSharedElementEnterTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo != null) return animationInfo.mSharedElementEnterTransition;
        return null;
    }

    public Object getSharedElementReturnTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return null;
        }
        if (animationInfo.mSharedElementReturnTransition != USE_DEFAULT_TRANSITION) return this.mAnimationInfo.mSharedElementReturnTransition;
        return this.getSharedElementEnterTransition();
    }

    int getStateAfterAnimating() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo != null) return animationInfo.mStateAfterAnimating;
        return 0;
    }

    public final String getString(int n) {
        return this.getResources().getString(n);
    }

    public final String getString(int n, Object ... arrobject) {
        return this.getResources().getString(n, arrobject);
    }

    public final String getTag() {
        return this.mTag;
    }

    public final Fragment getTargetFragment() {
        Object object = this.mTarget;
        if (object != null) {
            return object;
        }
        object = this.mFragmentManager;
        if (object == null) return null;
        String string2 = this.mTargetWho;
        if (string2 == null) return null;
        return ((FragmentManager)object).findActiveFragment(string2);
    }

    public final int getTargetRequestCode() {
        return this.mTargetRequestCode;
    }

    public final CharSequence getText(int n) {
        return this.getResources().getText(n);
    }

    @Deprecated
    public boolean getUserVisibleHint() {
        return this.mUserVisibleHint;
    }

    public View getView() {
        return this.mView;
    }

    public LifecycleOwner getViewLifecycleOwner() {
        FragmentViewLifecycleOwner fragmentViewLifecycleOwner = this.mViewLifecycleOwner;
        if (fragmentViewLifecycleOwner == null) throw new IllegalStateException("Can't access the Fragment View's LifecycleOwner when getView() is null i.e., before onCreateView() or after onDestroyView()");
        return fragmentViewLifecycleOwner;
    }

    public LiveData<LifecycleOwner> getViewLifecycleOwnerLiveData() {
        return this.mViewLifecycleOwnerLiveData;
    }

    @Override
    public ViewModelStore getViewModelStore() {
        FragmentManager fragmentManager = this.mFragmentManager;
        if (fragmentManager == null) throw new IllegalStateException("Can't access ViewModels from detached fragment");
        return fragmentManager.getViewModelStore(this);
    }

    public final boolean hasOptionsMenu() {
        return this.mHasMenu;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    void initState() {
        this.initLifecycle();
        this.mWho = UUID.randomUUID().toString();
        this.mAdded = false;
        this.mRemoving = false;
        this.mFromLayout = false;
        this.mInLayout = false;
        this.mRestored = false;
        this.mBackStackNesting = 0;
        this.mFragmentManager = null;
        this.mChildFragmentManager = new FragmentManagerImpl();
        this.mHost = null;
        this.mFragmentId = 0;
        this.mContainerId = 0;
        this.mTag = null;
        this.mHidden = false;
        this.mDetached = false;
    }

    public final boolean isAdded() {
        if (this.mHost == null) return false;
        if (!this.mAdded) return false;
        return true;
    }

    public final boolean isDetached() {
        return this.mDetached;
    }

    public final boolean isHidden() {
        return this.mHidden;
    }

    boolean isHideReplaced() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo != null) return animationInfo.mIsHideReplaced;
        return false;
    }

    final boolean isInBackStack() {
        if (this.mBackStackNesting <= 0) return false;
        return true;
    }

    public final boolean isInLayout() {
        return this.mInLayout;
    }

    public final boolean isMenuVisible() {
        return this.mMenuVisible;
    }

    boolean isPostponed() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo != null) return animationInfo.mEnterTransitionPostponed;
        return false;
    }

    public final boolean isRemoving() {
        return this.mRemoving;
    }

    final boolean isRemovingParent() {
        Fragment fragment = this.getParentFragment();
        if (fragment == null) return false;
        if (fragment.isRemoving()) return true;
        if (!fragment.isRemovingParent()) return false;
        return true;
    }

    public final boolean isResumed() {
        if (this.mState < 4) return false;
        return true;
    }

    public final boolean isStateSaved() {
        FragmentManager fragmentManager = this.mFragmentManager;
        if (fragmentManager != null) return fragmentManager.isStateSaved();
        return false;
    }

    public final boolean isVisible() {
        if (!this.isAdded()) return false;
        if (this.isHidden()) return false;
        View view = this.mView;
        if (view == null) return false;
        if (view.getWindowToken() == null) return false;
        if (this.mView.getVisibility() != 0) return false;
        return true;
    }

    void noteStateNotSaved() {
        this.mChildFragmentManager.noteStateNotSaved();
    }

    public void onActivityCreated(Bundle bundle) {
        this.mCalled = true;
    }

    public void onActivityResult(int n, int n2, Intent intent) {
    }

    @Deprecated
    public void onAttach(Activity activity) {
        this.mCalled = true;
    }

    public void onAttach(Context object) {
        this.mCalled = true;
        object = this.mHost;
        object = object == null ? null : ((FragmentHostCallback)object).getActivity();
        if (object == null) return;
        this.mCalled = false;
        this.onAttach((Activity)object);
    }

    public void onAttachFragment(Fragment fragment) {
    }

    public void onConfigurationChanged(Configuration configuration) {
        this.mCalled = true;
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onCreate(Bundle bundle) {
        this.mCalled = true;
        this.restoreChildFragmentState(bundle);
        if (this.mChildFragmentManager.isStateAtLeast(1)) return;
        this.mChildFragmentManager.dispatchCreate();
    }

    public Animation onCreateAnimation(int n, boolean bl, int n2) {
        return null;
    }

    public Animator onCreateAnimator(int n, boolean bl, int n2) {
        return null;
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.requireActivity().onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int n = this.mContentLayoutId;
        if (n == 0) return null;
        return layoutInflater.inflate(n, viewGroup, false);
    }

    public void onDestroy() {
        this.mCalled = true;
    }

    public void onDestroyOptionsMenu() {
    }

    public void onDestroyView() {
        this.mCalled = true;
    }

    public void onDetach() {
        this.mCalled = true;
    }

    public LayoutInflater onGetLayoutInflater(Bundle bundle) {
        return this.getLayoutInflater(bundle);
    }

    public void onHiddenChanged(boolean bl) {
    }

    @Deprecated
    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true;
    }

    public void onInflate(Context object, AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true;
        object = this.mHost;
        object = object == null ? null : ((FragmentHostCallback)object).getActivity();
        if (object == null) return;
        this.mCalled = false;
        this.onInflate((Activity)object, attributeSet, bundle);
    }

    public void onLowMemory() {
        this.mCalled = true;
    }

    public void onMultiWindowModeChanged(boolean bl) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onOptionsMenuClosed(Menu menu2) {
    }

    public void onPause() {
        this.mCalled = true;
    }

    public void onPictureInPictureModeChanged(boolean bl) {
    }

    public void onPrepareOptionsMenu(Menu menu2) {
    }

    public void onPrimaryNavigationFragmentChanged(boolean bl) {
    }

    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
    }

    public void onResume() {
        this.mCalled = true;
    }

    public void onSaveInstanceState(Bundle bundle) {
    }

    public void onStart() {
        this.mCalled = true;
    }

    public void onStop() {
        this.mCalled = true;
    }

    public void onViewCreated(View view, Bundle bundle) {
    }

    public void onViewStateRestored(Bundle bundle) {
        this.mCalled = true;
    }

    void performActivityCreated(Bundle object) {
        this.mChildFragmentManager.noteStateNotSaved();
        this.mState = 2;
        this.mCalled = false;
        this.onActivityCreated((Bundle)object);
        if (this.mCalled) {
            this.mChildFragmentManager.dispatchActivityCreated();
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onActivityCreated()");
        throw new SuperNotCalledException(((StringBuilder)object).toString());
    }

    void performAttach() {
        this.mChildFragmentManager.attachController(this.mHost, new FragmentContainer(){

            @Override
            public View onFindViewById(int n) {
                if (Fragment.this.mView != null) {
                    return Fragment.this.mView.findViewById(n);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Fragment ");
                stringBuilder.append(this);
                stringBuilder.append(" does not have a view");
                throw new IllegalStateException(stringBuilder.toString());
            }

            @Override
            public boolean onHasView() {
                if (Fragment.this.mView == null) return false;
                return true;
            }
        }, this);
        this.mState = 0;
        this.mCalled = false;
        this.onAttach(this.mHost.getContext());
        if (this.mCalled) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" did not call through to super.onAttach()");
        throw new SuperNotCalledException(stringBuilder.toString());
    }

    void performConfigurationChanged(Configuration configuration) {
        this.onConfigurationChanged(configuration);
        this.mChildFragmentManager.dispatchConfigurationChanged(configuration);
    }

    boolean performContextItemSelected(MenuItem menuItem) {
        if (this.mHidden) return false;
        if (this.onContextItemSelected(menuItem)) {
            return true;
        }
        if (!this.mChildFragmentManager.dispatchContextItemSelected(menuItem)) return false;
        return true;
    }

    void performCreate(Bundle object) {
        this.mChildFragmentManager.noteStateNotSaved();
        this.mState = 1;
        this.mCalled = false;
        this.mSavedStateRegistryController.performRestore((Bundle)object);
        this.onCreate((Bundle)object);
        this.mIsCreated = true;
        if (this.mCalled) {
            this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onCreate()");
        throw new SuperNotCalledException(((StringBuilder)object).toString());
    }

    boolean performCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        boolean bl = this.mHidden;
        boolean bl2 = false;
        boolean bl3 = false;
        if (bl) return bl2;
        boolean bl4 = bl3;
        if (!this.mHasMenu) return bl4 | this.mChildFragmentManager.dispatchCreateOptionsMenu(menu2, menuInflater);
        bl4 = bl3;
        if (!this.mMenuVisible) return bl4 | this.mChildFragmentManager.dispatchCreateOptionsMenu(menu2, menuInflater);
        bl4 = true;
        this.onCreateOptionsMenu(menu2, menuInflater);
        return bl4 | this.mChildFragmentManager.dispatchCreateOptionsMenu(menu2, menuInflater);
    }

    void performCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mChildFragmentManager.noteStateNotSaved();
        this.mPerformedCreateView = true;
        this.mViewLifecycleOwner = new FragmentViewLifecycleOwner();
        layoutInflater = this.onCreateView(layoutInflater, viewGroup, bundle);
        this.mView = layoutInflater;
        if (layoutInflater != null) {
            this.mViewLifecycleOwner.initialize();
            this.mViewLifecycleOwnerLiveData.setValue(this.mViewLifecycleOwner);
            return;
        }
        if (this.mViewLifecycleOwner.isInitialized()) throw new IllegalStateException("Called getViewLifecycleOwner() but onCreateView() returned null");
        this.mViewLifecycleOwner = null;
    }

    void performDestroy() {
        this.mChildFragmentManager.dispatchDestroy();
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        this.mState = 0;
        this.mCalled = false;
        this.mIsCreated = false;
        this.onDestroy();
        if (this.mCalled) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" did not call through to super.onDestroy()");
        throw new SuperNotCalledException(stringBuilder.toString());
    }

    void performDestroyView() {
        this.mChildFragmentManager.dispatchDestroyView();
        if (this.mView != null) {
            this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        }
        this.mState = 1;
        this.mCalled = false;
        this.onDestroyView();
        if (this.mCalled) {
            LoaderManager.getInstance(this).markForRedelivery();
            this.mPerformedCreateView = false;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" did not call through to super.onDestroyView()");
        throw new SuperNotCalledException(stringBuilder.toString());
    }

    void performDetach() {
        this.mState = -1;
        this.mCalled = false;
        this.onDetach();
        this.mLayoutInflater = null;
        if (this.mCalled) {
            if (this.mChildFragmentManager.isDestroyed()) return;
            this.mChildFragmentManager.dispatchDestroy();
            this.mChildFragmentManager = new FragmentManagerImpl();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" did not call through to super.onDetach()");
        throw new SuperNotCalledException(stringBuilder.toString());
    }

    LayoutInflater performGetLayoutInflater(Bundle bundle) {
        bundle = this.onGetLayoutInflater(bundle);
        this.mLayoutInflater = bundle;
        return bundle;
    }

    void performLowMemory() {
        this.onLowMemory();
        this.mChildFragmentManager.dispatchLowMemory();
    }

    void performMultiWindowModeChanged(boolean bl) {
        this.onMultiWindowModeChanged(bl);
        this.mChildFragmentManager.dispatchMultiWindowModeChanged(bl);
    }

    boolean performOptionsItemSelected(MenuItem menuItem) {
        if (this.mHidden) return false;
        if (this.mHasMenu && this.mMenuVisible && this.onOptionsItemSelected(menuItem)) {
            return true;
        }
        if (!this.mChildFragmentManager.dispatchOptionsItemSelected(menuItem)) return false;
        return true;
    }

    void performOptionsMenuClosed(Menu menu2) {
        if (this.mHidden) return;
        if (this.mHasMenu && this.mMenuVisible) {
            this.onOptionsMenuClosed(menu2);
        }
        this.mChildFragmentManager.dispatchOptionsMenuClosed(menu2);
    }

    void performPause() {
        this.mChildFragmentManager.dispatchPause();
        if (this.mView != null) {
            this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        }
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        this.mState = 3;
        this.mCalled = false;
        this.onPause();
        if (this.mCalled) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" did not call through to super.onPause()");
        throw new SuperNotCalledException(stringBuilder.toString());
    }

    void performPictureInPictureModeChanged(boolean bl) {
        this.onPictureInPictureModeChanged(bl);
        this.mChildFragmentManager.dispatchPictureInPictureModeChanged(bl);
    }

    boolean performPrepareOptionsMenu(Menu menu2) {
        boolean bl = this.mHidden;
        boolean bl2 = false;
        boolean bl3 = false;
        if (bl) return bl2;
        boolean bl4 = bl3;
        if (!this.mHasMenu) return bl4 | this.mChildFragmentManager.dispatchPrepareOptionsMenu(menu2);
        bl4 = bl3;
        if (!this.mMenuVisible) return bl4 | this.mChildFragmentManager.dispatchPrepareOptionsMenu(menu2);
        bl4 = true;
        this.onPrepareOptionsMenu(menu2);
        return bl4 | this.mChildFragmentManager.dispatchPrepareOptionsMenu(menu2);
    }

    void performPrimaryNavigationFragmentChanged() {
        boolean bl = this.mFragmentManager.isPrimaryNavigation(this);
        Boolean bl2 = this.mIsPrimaryNavigationFragment;
        if (bl2 != null) {
            if (bl2 == bl) return;
        }
        this.mIsPrimaryNavigationFragment = bl;
        this.onPrimaryNavigationFragmentChanged(bl);
        this.mChildFragmentManager.dispatchPrimaryNavigationFragmentChanged();
    }

    void performResume() {
        this.mChildFragmentManager.noteStateNotSaved();
        this.mChildFragmentManager.execPendingActions(true);
        this.mState = 4;
        this.mCalled = false;
        this.onResume();
        if (!this.mCalled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(this);
            stringBuilder.append(" did not call through to super.onResume()");
            throw new SuperNotCalledException(stringBuilder.toString());
        }
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        if (this.mView != null) {
            this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        }
        this.mChildFragmentManager.dispatchResume();
    }

    void performSaveInstanceState(Bundle bundle) {
        this.onSaveInstanceState(bundle);
        this.mSavedStateRegistryController.performSave(bundle);
        Parcelable parcelable = this.mChildFragmentManager.saveAllState();
        if (parcelable == null) return;
        bundle.putParcelable("android:support:fragments", parcelable);
    }

    void performStart() {
        this.mChildFragmentManager.noteStateNotSaved();
        this.mChildFragmentManager.execPendingActions(true);
        this.mState = 3;
        this.mCalled = false;
        this.onStart();
        if (!this.mCalled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(this);
            stringBuilder.append(" did not call through to super.onStart()");
            throw new SuperNotCalledException(stringBuilder.toString());
        }
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        if (this.mView != null) {
            this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START);
        }
        this.mChildFragmentManager.dispatchStart();
    }

    void performStop() {
        this.mChildFragmentManager.dispatchStop();
        if (this.mView != null) {
            this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        }
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        this.mState = 2;
        this.mCalled = false;
        this.onStop();
        if (this.mCalled) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" did not call through to super.onStop()");
        throw new SuperNotCalledException(stringBuilder.toString());
    }

    public void postponeEnterTransition() {
        this.ensureAnimationInfo().mEnterTransitionPostponed = true;
    }

    public final void postponeEnterTransition(long l, TimeUnit timeUnit) {
        this.ensureAnimationInfo().mEnterTransitionPostponed = true;
        FragmentManager fragmentManager = this.mFragmentManager;
        fragmentManager = fragmentManager != null ? fragmentManager.mHost.getHandler() : new Handler(Looper.getMainLooper());
        fragmentManager.removeCallbacks(this.mPostponedDurationRunnable);
        fragmentManager.postDelayed(this.mPostponedDurationRunnable, timeUnit.toMillis(l));
    }

    public void registerForContextMenu(View view) {
        view.setOnCreateContextMenuListener((View.OnCreateContextMenuListener)this);
    }

    public final void requestPermissions(String[] object, int n) {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            fragmentHostCallback.onRequestPermissionsFromFragment(this, (String[])object, n);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to Activity");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public final FragmentActivity requireActivity() {
        Object object = this.getActivity();
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to an activity.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public final Bundle requireArguments() {
        Object object = this.getArguments();
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" does not have any arguments.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public final Context requireContext() {
        Object object = this.getContext();
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to a context.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    @Deprecated
    public final FragmentManager requireFragmentManager() {
        return this.getParentFragmentManager();
    }

    public final Object requireHost() {
        Object object = this.getHost();
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to a host.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public final Fragment requireParentFragment() {
        Object object = this.getParentFragment();
        if (object != null) return object;
        if (this.getContext() == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Fragment ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" is not attached to any Fragment or host");
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" is not a child Fragment, it is directly attached to ");
        ((StringBuilder)object).append((Object)this.getContext());
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public final View requireView() {
        Object object = this.getView();
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not return a View from onCreateView() or this was called before onCreateView().");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    void restoreChildFragmentState(Bundle bundle) {
        if (bundle == null) return;
        if ((bundle = bundle.getParcelable("android:support:fragments")) == null) return;
        this.mChildFragmentManager.restoreSaveState((Parcelable)bundle);
        this.mChildFragmentManager.dispatchCreate();
    }

    final void restoreViewState(Bundle object) {
        SparseArray<Parcelable> sparseArray = this.mSavedViewState;
        if (sparseArray != null) {
            this.mView.restoreHierarchyState(sparseArray);
            this.mSavedViewState = null;
        }
        this.mCalled = false;
        this.onViewStateRestored((Bundle)object);
        if (this.mCalled) {
            if (this.mView == null) return;
            this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onViewStateRestored()");
        throw new SuperNotCalledException(((StringBuilder)object).toString());
    }

    public void setAllowEnterTransitionOverlap(boolean bl) {
        this.ensureAnimationInfo().mAllowEnterTransitionOverlap = bl;
    }

    public void setAllowReturnTransitionOverlap(boolean bl) {
        this.ensureAnimationInfo().mAllowReturnTransitionOverlap = bl;
    }

    void setAnimatingAway(View view) {
        this.ensureAnimationInfo().mAnimatingAway = view;
    }

    void setAnimator(Animator animator2) {
        this.ensureAnimationInfo().mAnimator = animator2;
    }

    public void setArguments(Bundle bundle) {
        if (this.mFragmentManager != null) {
            if (this.isStateSaved()) throw new IllegalStateException("Fragment already added and state has been saved");
        }
        this.mArguments = bundle;
    }

    public void setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        this.ensureAnimationInfo().mEnterTransitionCallback = sharedElementCallback;
    }

    public void setEnterTransition(Object object) {
        this.ensureAnimationInfo().mEnterTransition = object;
    }

    public void setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        this.ensureAnimationInfo().mExitTransitionCallback = sharedElementCallback;
    }

    public void setExitTransition(Object object) {
        this.ensureAnimationInfo().mExitTransition = object;
    }

    public void setHasOptionsMenu(boolean bl) {
        if (this.mHasMenu == bl) return;
        this.mHasMenu = bl;
        if (!this.isAdded()) return;
        if (this.isHidden()) return;
        this.mHost.onSupportInvalidateOptionsMenu();
    }

    void setHideReplaced(boolean bl) {
        this.ensureAnimationInfo().mIsHideReplaced = bl;
    }

    public void setInitialSavedState(SavedState savedState) {
        if (this.mFragmentManager != null) throw new IllegalStateException("Fragment already added");
        savedState = savedState != null && savedState.mState != null ? savedState.mState : null;
        this.mSavedFragmentState = savedState;
    }

    public void setMenuVisibility(boolean bl) {
        if (this.mMenuVisible == bl) return;
        this.mMenuVisible = bl;
        if (!this.mHasMenu) return;
        if (!this.isAdded()) return;
        if (this.isHidden()) return;
        this.mHost.onSupportInvalidateOptionsMenu();
    }

    void setNextAnim(int n) {
        if (this.mAnimationInfo == null && n == 0) {
            return;
        }
        this.ensureAnimationInfo().mNextAnim = n;
    }

    void setNextTransition(int n) {
        if (this.mAnimationInfo == null && n == 0) {
            return;
        }
        this.ensureAnimationInfo();
        this.mAnimationInfo.mNextTransition = n;
    }

    void setOnStartEnterTransitionListener(OnStartEnterTransitionListener object) {
        this.ensureAnimationInfo();
        if (object == this.mAnimationInfo.mStartEnterTransitionListener) {
            return;
        }
        if (object != null && this.mAnimationInfo.mStartEnterTransitionListener != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Trying to set a replacement startPostponedEnterTransition on ");
            ((StringBuilder)object).append(this);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        if (this.mAnimationInfo.mEnterTransitionPostponed) {
            this.mAnimationInfo.mStartEnterTransitionListener = object;
        }
        if (object == null) return;
        object.startListening();
    }

    public void setReenterTransition(Object object) {
        this.ensureAnimationInfo().mReenterTransition = object;
    }

    public void setRetainInstance(boolean bl) {
        this.mRetainInstance = bl;
        FragmentManager fragmentManager = this.mFragmentManager;
        if (fragmentManager == null) {
            this.mRetainInstanceChangedWhileDetached = true;
            return;
        }
        if (bl) {
            fragmentManager.addRetainedFragment(this);
            return;
        }
        fragmentManager.removeRetainedFragment(this);
    }

    public void setReturnTransition(Object object) {
        this.ensureAnimationInfo().mReturnTransition = object;
    }

    public void setSharedElementEnterTransition(Object object) {
        this.ensureAnimationInfo().mSharedElementEnterTransition = object;
    }

    public void setSharedElementReturnTransition(Object object) {
        this.ensureAnimationInfo().mSharedElementReturnTransition = object;
    }

    void setStateAfterAnimating(int n) {
        this.ensureAnimationInfo().mStateAfterAnimating = n;
    }

    public void setTargetFragment(Fragment fragment, int n) {
        FragmentManager fragmentManager = this.mFragmentManager;
        Object object = fragment != null ? fragment.mFragmentManager : null;
        if (fragmentManager != null && object != null && fragmentManager != object) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Fragment ");
            ((StringBuilder)object).append(fragment);
            ((StringBuilder)object).append(" must share the same FragmentManager to be set as a target fragment");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        for (object = fragment; object != null; object = object.getTargetFragment()) {
            if (object != this) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("Setting ");
            ((StringBuilder)object).append(fragment);
            ((StringBuilder)object).append(" as the target of ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" would create a target cycle");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        if (fragment == null) {
            this.mTargetWho = null;
            this.mTarget = null;
        } else if (this.mFragmentManager != null && fragment.mFragmentManager != null) {
            this.mTargetWho = fragment.mWho;
            this.mTarget = null;
        } else {
            this.mTargetWho = null;
            this.mTarget = fragment;
        }
        this.mTargetRequestCode = n;
    }

    @Deprecated
    public void setUserVisibleHint(boolean bl) {
        if (!this.mUserVisibleHint && bl && this.mState < 3 && this.mFragmentManager != null && this.isAdded() && this.mIsCreated) {
            this.mFragmentManager.performPendingDeferredStart(this);
        }
        this.mUserVisibleHint = bl;
        boolean bl2 = this.mState < 3 && !bl;
        this.mDeferStart = bl2;
        if (this.mSavedFragmentState == null) return;
        this.mSavedUserVisibleHint = bl;
    }

    public boolean shouldShowRequestPermissionRationale(String string2) {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) return false;
        return fragmentHostCallback.onShouldShowRequestPermissionRationale(string2);
    }

    public void startActivity(Intent intent) {
        this.startActivity(intent, null);
    }

    public void startActivity(Intent object, Bundle bundle) {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            fragmentHostCallback.onStartActivityFromFragment(this, (Intent)object, -1, bundle);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to Activity");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void startActivityForResult(Intent intent, int n) {
        this.startActivityForResult(intent, n, null);
    }

    public void startActivityForResult(Intent object, int n, Bundle bundle) {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            fragmentHostCallback.onStartActivityFromFragment(this, (Intent)object, n, bundle);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to Activity");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void startIntentSenderForResult(IntentSender object, int n, Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            fragmentHostCallback.onStartIntentSenderFromFragment(this, (IntentSender)object, n, intent, n2, n3, n4, bundle);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to Activity");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void startPostponedEnterTransition() {
        FragmentManager fragmentManager = this.mFragmentManager;
        if (fragmentManager != null && fragmentManager.mHost != null) {
            if (Looper.myLooper() != this.mFragmentManager.mHost.getHandler().getLooper()) {
                this.mFragmentManager.mHost.getHandler().postAtFrontOfQueue(new Runnable(){

                    @Override
                    public void run() {
                        Fragment.this.callStartTransitionListener();
                    }
                });
                return;
            }
            this.callStartTransitionListener();
            return;
        }
        this.ensureAnimationInfo().mEnterTransitionPostponed = false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append("}");
        stringBuilder.append(" (");
        stringBuilder.append(this.mWho);
        stringBuilder.append(")");
        if (this.mFragmentId != 0) {
            stringBuilder.append(" id=0x");
            stringBuilder.append(Integer.toHexString(this.mFragmentId));
        }
        if (this.mTag != null) {
            stringBuilder.append(" ");
            stringBuilder.append(this.mTag);
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public void unregisterForContextMenu(View view) {
        view.setOnCreateContextMenuListener(null);
    }

    static class AnimationInfo {
        Boolean mAllowEnterTransitionOverlap;
        Boolean mAllowReturnTransitionOverlap;
        View mAnimatingAway;
        Animator mAnimator;
        Object mEnterTransition = null;
        SharedElementCallback mEnterTransitionCallback = null;
        boolean mEnterTransitionPostponed;
        Object mExitTransition = null;
        SharedElementCallback mExitTransitionCallback = null;
        boolean mIsHideReplaced;
        int mNextAnim;
        int mNextTransition;
        Object mReenterTransition = USE_DEFAULT_TRANSITION;
        Object mReturnTransition = USE_DEFAULT_TRANSITION;
        Object mSharedElementEnterTransition = null;
        Object mSharedElementReturnTransition = USE_DEFAULT_TRANSITION;
        OnStartEnterTransitionListener mStartEnterTransitionListener;
        int mStateAfterAnimating;

        AnimationInfo() {
        }
    }

    public static class InstantiationException
    extends RuntimeException {
        public InstantiationException(String string2, Exception exception) {
            super(string2, exception);
        }
    }

    static interface OnStartEnterTransitionListener {
        public void onStartEnterTransition();

        public void startListening();
    }

    public static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        final Bundle mState;

        SavedState(Bundle bundle) {
            this.mState = bundle;
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            parcel = parcel.readBundle();
            this.mState = parcel;
            if (classLoader == null) return;
            if (parcel == null) return;
            parcel.setClassLoader(classLoader);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeBundle(this.mState);
        }

    }

}

