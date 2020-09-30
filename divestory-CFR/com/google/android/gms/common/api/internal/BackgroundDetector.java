/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.ComponentCallbacks
 *  android.content.ComponentCallbacks2
 *  android.content.res.Configuration
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.android.gms.common.util.PlatformVersion;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public final class BackgroundDetector
implements Application.ActivityLifecycleCallbacks,
ComponentCallbacks2 {
    private static final BackgroundDetector zza = new BackgroundDetector();
    private final AtomicBoolean zzb = new AtomicBoolean();
    private final AtomicBoolean zzc = new AtomicBoolean();
    private final ArrayList<BackgroundStateChangeListener> zzd = new ArrayList();
    private boolean zze = false;

    private BackgroundDetector() {
    }

    public static BackgroundDetector getInstance() {
        return zza;
    }

    public static void initialize(Application application) {
        BackgroundDetector backgroundDetector = zza;
        synchronized (backgroundDetector) {
            if (BackgroundDetector.zza.zze) return;
            application.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)zza);
            application.registerComponentCallbacks((ComponentCallbacks)zza);
            BackgroundDetector.zza.zze = true;
            return;
        }
    }

    private final void zza(boolean bl) {
        BackgroundDetector backgroundDetector = zza;
        synchronized (backgroundDetector) {
            ArrayList<BackgroundStateChangeListener> arrayList = this.zzd;
            int n = arrayList.size();
            int n2 = 0;
            while (n2 < n) {
                BackgroundStateChangeListener backgroundStateChangeListener = arrayList.get(n2);
                ++n2;
                backgroundStateChangeListener.onBackgroundStateChanged(bl);
            }
            return;
        }
    }

    public final void addListener(BackgroundStateChangeListener backgroundStateChangeListener) {
        BackgroundDetector backgroundDetector = zza;
        synchronized (backgroundDetector) {
            this.zzd.add(backgroundStateChangeListener);
            return;
        }
    }

    public final boolean isInBackground() {
        return this.zzb.get();
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        boolean bl = this.zzb.compareAndSet(true, false);
        this.zzc.set(true);
        if (!bl) return;
        this.zza(false);
    }

    public final void onActivityDestroyed(Activity activity) {
    }

    public final void onActivityPaused(Activity activity) {
    }

    public final void onActivityResumed(Activity activity) {
        boolean bl = this.zzb.compareAndSet(true, false);
        this.zzc.set(true);
        if (!bl) return;
        this.zza(false);
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void onLowMemory() {
    }

    public final void onTrimMemory(int n) {
        if (n != 20) return;
        if (!this.zzb.compareAndSet(false, true)) return;
        this.zzc.set(true);
        this.zza(true);
    }

    public final boolean readCurrentStateIfPossible(boolean bl) {
        if (this.zzc.get()) return this.isInBackground();
        if (!PlatformVersion.isAtLeastJellyBean()) return bl;
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState((ActivityManager.RunningAppProcessInfo)runningAppProcessInfo);
        if (this.zzc.getAndSet(true)) return this.isInBackground();
        if (runningAppProcessInfo.importance <= 100) return this.isInBackground();
        this.zzb.set(true);
        return this.isInBackground();
    }

    public static interface BackgroundStateChangeListener {
        public void onBackgroundStateChanged(boolean var1);
    }

}

