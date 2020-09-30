/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.content.Intent
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.View
 *  android.view.Window
 */
package androidx.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import androidx.activity.ImmLeaksCleaner;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ReportFragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;

public class ComponentActivity
extends androidx.core.app.ComponentActivity
implements LifecycleOwner,
ViewModelStoreOwner,
HasDefaultViewModelProviderFactory,
SavedStateRegistryOwner,
OnBackPressedDispatcherOwner {
    private int mContentLayoutId;
    private ViewModelProvider.Factory mDefaultFactory;
    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    private final OnBackPressedDispatcher mOnBackPressedDispatcher = new OnBackPressedDispatcher(new Runnable(){

        @Override
        public void run() {
            ComponentActivity.super.onBackPressed();
        }
    });
    private final SavedStateRegistryController mSavedStateRegistryController = SavedStateRegistryController.create(this);
    private ViewModelStore mViewModelStore;

    public ComponentActivity() {
        if (this.getLifecycle() == null) throw new IllegalStateException("getLifecycle() returned null in ComponentActivity's constructor. Please make sure you are lazily constructing your Lifecycle in the first call to getLifecycle() rather than relying on field initialization.");
        if (Build.VERSION.SDK_INT >= 19) {
            this.getLifecycle().addObserver(new LifecycleEventObserver(){

                @Override
                public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                    if (event != Lifecycle.Event.ON_STOP) return;
                    lifecycleOwner = ComponentActivity.this.getWindow();
                    lifecycleOwner = lifecycleOwner != null ? lifecycleOwner.peekDecorView() : null;
                    if (lifecycleOwner == null) return;
                    lifecycleOwner.cancelPendingInputEvents();
                }
            });
        }
        this.getLifecycle().addObserver(new LifecycleEventObserver(){

            @Override
            public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                if (event != Lifecycle.Event.ON_DESTROY) return;
                if (ComponentActivity.this.isChangingConfigurations()) return;
                ComponentActivity.this.getViewModelStore().clear();
            }
        });
        if (19 > Build.VERSION.SDK_INT) return;
        if (Build.VERSION.SDK_INT > 23) return;
        this.getLifecycle().addObserver(new ImmLeaksCleaner(this));
    }

    public ComponentActivity(int n) {
        this();
        this.mContentLayoutId = n;
    }

    @Override
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        if (this.getApplication() == null) throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
        if (this.mDefaultFactory != null) return this.mDefaultFactory;
        Application application = this.getApplication();
        Bundle bundle = this.getIntent() != null ? this.getIntent().getExtras() : null;
        this.mDefaultFactory = new SavedStateViewModelFactory(application, this, bundle);
        return this.mDefaultFactory;
    }

    @Deprecated
    public Object getLastCustomNonConfigurationInstance() {
        NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances)this.getLastNonConfigurationInstance();
        if (nonConfigurationInstances == null) return null;
        return nonConfigurationInstances.custom;
    }

    @Override
    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    @Override
    public final OnBackPressedDispatcher getOnBackPressedDispatcher() {
        return this.mOnBackPressedDispatcher;
    }

    @Override
    public final SavedStateRegistry getSavedStateRegistry() {
        return this.mSavedStateRegistryController.getSavedStateRegistry();
    }

    @Override
    public ViewModelStore getViewModelStore() {
        if (this.getApplication() == null) throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
        if (this.mViewModelStore != null) return this.mViewModelStore;
        NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances)this.getLastNonConfigurationInstance();
        if (nonConfigurationInstances != null) {
            this.mViewModelStore = nonConfigurationInstances.viewModelStore;
        }
        if (this.mViewModelStore != null) return this.mViewModelStore;
        this.mViewModelStore = new ViewModelStore();
        return this.mViewModelStore;
    }

    public void onBackPressed() {
        this.mOnBackPressedDispatcher.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSavedStateRegistryController.performRestore(bundle);
        ReportFragment.injectIfNeededIn(this);
        int n = this.mContentLayoutId;
        if (n == 0) return;
        this.setContentView(n);
    }

    @Deprecated
    public Object onRetainCustomNonConfigurationInstance() {
        return null;
    }

    public final Object onRetainNonConfigurationInstance() {
        Object object = this.onRetainCustomNonConfigurationInstance();
        Object object2 = this.mViewModelStore;
        ViewModelStore viewModelStore = object2;
        if (object2 == null) {
            NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances)this.getLastNonConfigurationInstance();
            viewModelStore = object2;
            if (nonConfigurationInstances != null) {
                viewModelStore = nonConfigurationInstances.viewModelStore;
            }
        }
        if (viewModelStore == null && object == null) {
            return null;
        }
        object2 = new NonConfigurationInstances();
        ((NonConfigurationInstances)object2).custom = object;
        ((NonConfigurationInstances)object2).viewModelStore = viewModelStore;
        return object2;
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        Lifecycle lifecycle = this.getLifecycle();
        if (lifecycle instanceof LifecycleRegistry) {
            ((LifecycleRegistry)lifecycle).setCurrentState(Lifecycle.State.CREATED);
        }
        super.onSaveInstanceState(bundle);
        this.mSavedStateRegistryController.performSave(bundle);
    }

    static final class NonConfigurationInstances {
        Object custom;
        ViewModelStore viewModelStore;

        NonConfigurationInstances() {
        }
    }

}

