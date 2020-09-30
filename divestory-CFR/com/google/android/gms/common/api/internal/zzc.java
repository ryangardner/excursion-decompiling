/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Looper
 */
package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.gms.common.api.internal.zzd;
import com.google.android.gms.internal.common.zzi;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class zzc
extends Fragment
implements LifecycleFragment {
    private static WeakHashMap<FragmentActivity, WeakReference<zzc>> zza = new WeakHashMap();
    private Map<String, LifecycleCallback> zzb = Collections.synchronizedMap(new ArrayMap());
    private int zzc = 0;
    private Bundle zzd;

    static /* synthetic */ int zza(zzc zzc2) {
        return zzc2.zzc;
    }

    public static zzc zza(FragmentActivity fragmentActivity) {
        WeakReference<zzc> weakReference;
        block7 : {
            block6 : {
                zzc zzc2;
                weakReference = zza.get(fragmentActivity);
                if (weakReference != null && (weakReference = (zzc)weakReference.get()) != null) {
                    return weakReference;
                }
                try {
                    zzc2 = (zzc)fragmentActivity.getSupportFragmentManager().findFragmentByTag("SupportLifecycleFragmentImpl");
                    if (zzc2 == null) break block6;
                    weakReference = zzc2;
                }
                catch (ClassCastException classCastException) {
                    throw new IllegalStateException("Fragment with tag SupportLifecycleFragmentImpl is not a SupportLifecycleFragmentImpl", classCastException);
                }
                if (!zzc2.isRemoving()) break block7;
            }
            weakReference = new zzc();
            fragmentActivity.getSupportFragmentManager().beginTransaction().add((Fragment)((Object)weakReference), "SupportLifecycleFragmentImpl").commitAllowingStateLoss();
        }
        zza.put(fragmentActivity, new WeakReference<Object>(weakReference));
        return weakReference;
    }

    static /* synthetic */ Bundle zzb(zzc zzc2) {
        return zzc2.zzd;
    }

    @Override
    public final void addCallback(String string2, LifecycleCallback object) {
        if (!this.zzb.containsKey(string2)) {
            this.zzb.put(string2, (LifecycleCallback)object);
            if (this.zzc <= 0) return;
            new zzi(Looper.getMainLooper()).post((Runnable)new zzd(this, (LifecycleCallback)object, string2));
            return;
        }
        object = new StringBuilder(String.valueOf(string2).length() + 59);
        ((StringBuilder)object).append("LifecycleCallback with tag ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" already added to this fragment.");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Override
    public final void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(string2, fileDescriptor, printWriter, arrstring);
        Iterator<LifecycleCallback> iterator2 = this.zzb.values().iterator();
        while (iterator2.hasNext()) {
            iterator2.next().dump(string2, fileDescriptor, printWriter, arrstring);
        }
    }

    @Override
    public final <T extends LifecycleCallback> T getCallbackOrNull(String string2, Class<T> class_) {
        return (T)((LifecycleCallback)class_.cast(this.zzb.get(string2)));
    }

    @Override
    public final /* synthetic */ Activity getLifecycleActivity() {
        return this.getActivity();
    }

    @Override
    public final boolean isCreated() {
        if (this.zzc <= 0) return false;
        return true;
    }

    @Override
    public final boolean isStarted() {
        if (this.zzc < 2) return false;
        return true;
    }

    @Override
    public final void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        Iterator<LifecycleCallback> iterator2 = this.zzb.values().iterator();
        while (iterator2.hasNext()) {
            iterator2.next().onActivityResult(n, n2, intent);
        }
    }

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzc = 1;
        this.zzd = bundle;
        Iterator<Map.Entry<String, LifecycleCallback>> iterator2 = this.zzb.entrySet().iterator();
        while (iterator2.hasNext()) {
            Object object = iterator2.next();
            LifecycleCallback lifecycleCallback = object.getValue();
            object = bundle != null ? bundle.getBundle(object.getKey()) : null;
            lifecycleCallback.onCreate((Bundle)object);
        }
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        this.zzc = 5;
        Iterator<LifecycleCallback> iterator2 = this.zzb.values().iterator();
        while (iterator2.hasNext()) {
            iterator2.next().onDestroy();
        }
    }

    @Override
    public final void onResume() {
        super.onResume();
        this.zzc = 3;
        Iterator<LifecycleCallback> iterator2 = this.zzb.values().iterator();
        while (iterator2.hasNext()) {
            iterator2.next().onResume();
        }
    }

    @Override
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle == null) {
            return;
        }
        Iterator<Map.Entry<String, LifecycleCallback>> iterator2 = this.zzb.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, LifecycleCallback> entry = iterator2.next();
            Bundle bundle2 = new Bundle();
            entry.getValue().onSaveInstanceState(bundle2);
            bundle.putBundle(entry.getKey(), bundle2);
        }
    }

    @Override
    public final void onStart() {
        super.onStart();
        this.zzc = 2;
        Iterator<LifecycleCallback> iterator2 = this.zzb.values().iterator();
        while (iterator2.hasNext()) {
            iterator2.next().onStart();
        }
    }

    @Override
    public final void onStop() {
        super.onStop();
        this.zzc = 4;
        Iterator<LifecycleCallback> iterator2 = this.zzb.values().iterator();
        while (iterator2.hasNext()) {
            iterator2.next().onStop();
        }
    }
}

