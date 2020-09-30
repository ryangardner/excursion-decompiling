/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 */
package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.lifecycle.EmptyActivityLifecycleCallbacks;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ReportFragment;

public class ProcessLifecycleOwner
implements LifecycleOwner {
    static final long TIMEOUT_MS = 700L;
    private static final ProcessLifecycleOwner sInstance = new ProcessLifecycleOwner();
    private Runnable mDelayedPauseRunnable = new Runnable(){

        @Override
        public void run() {
            ProcessLifecycleOwner.this.dispatchPauseIfNeeded();
            ProcessLifecycleOwner.this.dispatchStopIfNeeded();
        }
    };
    private Handler mHandler;
    ReportFragment.ActivityInitializationListener mInitializationListener = new ReportFragment.ActivityInitializationListener(){

        @Override
        public void onCreate() {
        }

        @Override
        public void onResume() {
            ProcessLifecycleOwner.this.activityResumed();
        }

        @Override
        public void onStart() {
            ProcessLifecycleOwner.this.activityStarted();
        }
    };
    private boolean mPauseSent = true;
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    private int mResumedCounter = 0;
    private int mStartedCounter = 0;
    private boolean mStopSent = true;

    private ProcessLifecycleOwner() {
    }

    public static LifecycleOwner get() {
        return sInstance;
    }

    static void init(Context context) {
        sInstance.attach(context);
    }

    void activityPaused() {
        int n;
        this.mResumedCounter = n = this.mResumedCounter - 1;
        if (n != 0) return;
        this.mHandler.postDelayed(this.mDelayedPauseRunnable, 700L);
    }

    void activityResumed() {
        int n;
        this.mResumedCounter = n = this.mResumedCounter + 1;
        if (n != 1) return;
        if (this.mPauseSent) {
            this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
            this.mPauseSent = false;
            return;
        }
        this.mHandler.removeCallbacks(this.mDelayedPauseRunnable);
    }

    void activityStarted() {
        int n;
        this.mStartedCounter = n = this.mStartedCounter + 1;
        if (n != 1) return;
        if (!this.mStopSent) return;
        this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        this.mStopSent = false;
    }

    void activityStopped() {
        --this.mStartedCounter;
        this.dispatchStopIfNeeded();
    }

    void attach(Context context) {
        this.mHandler = new Handler();
        this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        ((Application)context.getApplicationContext()).registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)new EmptyActivityLifecycleCallbacks(){

            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                if (Build.VERSION.SDK_INT >= 29) return;
                ReportFragment.get(activity).setProcessListener(ProcessLifecycleOwner.this.mInitializationListener);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                ProcessLifecycleOwner.this.activityPaused();
            }

            public void onActivityPreCreated(Activity activity, Bundle bundle) {
                activity.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)new EmptyActivityLifecycleCallbacks(){

                    public void onActivityPostResumed(Activity activity) {
                        ProcessLifecycleOwner.this.activityResumed();
                    }

                    public void onActivityPostStarted(Activity activity) {
                        ProcessLifecycleOwner.this.activityStarted();
                    }
                });
            }

            @Override
            public void onActivityStopped(Activity activity) {
                ProcessLifecycleOwner.this.activityStopped();
            }

        });
    }

    void dispatchPauseIfNeeded() {
        if (this.mResumedCounter != 0) return;
        this.mPauseSent = true;
        this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    void dispatchStopIfNeeded() {
        if (this.mStartedCounter != 0) return;
        if (!this.mPauseSent) return;
        this.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        this.mStopSent = true;
    }

    @Override
    public Lifecycle getLifecycle() {
        return this.mRegistry;
    }

}

