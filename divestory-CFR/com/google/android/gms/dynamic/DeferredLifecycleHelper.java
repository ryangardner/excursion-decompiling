/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.OnDelegateCreatedListener;
import com.google.android.gms.dynamic.zab;
import com.google.android.gms.dynamic.zac;
import com.google.android.gms.dynamic.zad;
import com.google.android.gms.dynamic.zae;
import com.google.android.gms.dynamic.zaf;
import com.google.android.gms.dynamic.zag;
import java.util.LinkedList;

public abstract class DeferredLifecycleHelper<T extends LifecycleDelegate> {
    private T zaa;
    private Bundle zab;
    private LinkedList<zaa> zac;
    private final OnDelegateCreatedListener<T> zad = new zab(this);

    public static void showGooglePlayUnavailableMessage(FrameLayout frameLayout) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        Context context = frameLayout.getContext();
        int n = googleApiAvailability.isGooglePlayServicesAvailable(context);
        String string2 = com.google.android.gms.common.internal.zac.zac(context, n);
        String string3 = com.google.android.gms.common.internal.zac.zae(context, n);
        LinearLayout linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        frameLayout.addView((View)linearLayout);
        frameLayout = new TextView(frameLayout.getContext());
        frameLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        frameLayout.setText((CharSequence)string2);
        linearLayout.addView((View)frameLayout);
        frameLayout = googleApiAvailability.getErrorResolutionIntent(context, n, null);
        if (frameLayout == null) return;
        string2 = new Button(context);
        string2.setId(16908313);
        string2.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        string2.setText((CharSequence)string3);
        linearLayout.addView((View)string2);
        string2.setOnClickListener((View.OnClickListener)new zaf(context, (Intent)frameLayout));
    }

    static /* synthetic */ Bundle zaa(DeferredLifecycleHelper deferredLifecycleHelper, Bundle bundle) {
        deferredLifecycleHelper.zab = null;
        return null;
    }

    static /* synthetic */ LifecycleDelegate zaa(DeferredLifecycleHelper deferredLifecycleHelper, LifecycleDelegate lifecycleDelegate) {
        deferredLifecycleHelper.zaa = lifecycleDelegate;
        return lifecycleDelegate;
    }

    static /* synthetic */ LinkedList zaa(DeferredLifecycleHelper deferredLifecycleHelper) {
        return deferredLifecycleHelper.zac;
    }

    private final void zaa(int n) {
        while (!this.zac.isEmpty()) {
            if (this.zac.getLast().zaa() < n) return;
            this.zac.removeLast();
        }
    }

    private final void zaa(Bundle bundle, zaa zaa2) {
        T t = this.zaa;
        if (t != null) {
            zaa2.zaa((LifecycleDelegate)t);
            return;
        }
        if (this.zac == null) {
            this.zac = new LinkedList();
        }
        this.zac.add(zaa2);
        if (bundle != null) {
            zaa2 = this.zab;
            if (zaa2 == null) {
                this.zab = (Bundle)bundle.clone();
            } else {
                zaa2.putAll(bundle);
            }
        }
        this.createDelegate(this.zad);
    }

    static /* synthetic */ LifecycleDelegate zab(DeferredLifecycleHelper deferredLifecycleHelper) {
        return deferredLifecycleHelper.zaa;
    }

    protected abstract void createDelegate(OnDelegateCreatedListener<T> var1);

    public T getDelegate() {
        return this.zaa;
    }

    protected void handleGooglePlayUnavailable(FrameLayout frameLayout) {
        DeferredLifecycleHelper.showGooglePlayUnavailableMessage(frameLayout);
    }

    public void onCreate(Bundle bundle) {
        this.zaa(bundle, new zad(this, bundle));
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        this.zaa(bundle, new zac(this, frameLayout, layoutInflater, viewGroup, bundle));
        if (this.zaa != null) return frameLayout;
        this.handleGooglePlayUnavailable(frameLayout);
        return frameLayout;
    }

    public void onDestroy() {
        T t = this.zaa;
        if (t != null) {
            t.onDestroy();
            return;
        }
        this.zaa(1);
    }

    public void onDestroyView() {
        T t = this.zaa;
        if (t != null) {
            t.onDestroyView();
            return;
        }
        this.zaa(2);
    }

    public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
        this.zaa(bundle2, new com.google.android.gms.dynamic.zaa(this, activity, bundle, bundle2));
    }

    public void onLowMemory() {
        T t = this.zaa;
        if (t == null) return;
        t.onLowMemory();
    }

    public void onPause() {
        T t = this.zaa;
        if (t != null) {
            t.onPause();
            return;
        }
        this.zaa(5);
    }

    public void onResume() {
        this.zaa(null, new zag(this));
    }

    public void onSaveInstanceState(Bundle bundle) {
        Object object = this.zaa;
        if (object != null) {
            object.onSaveInstanceState(bundle);
            return;
        }
        object = this.zab;
        if (object == null) return;
        bundle.putAll(object);
    }

    public void onStart() {
        this.zaa(null, new zae(this));
    }

    public void onStop() {
        T t = this.zaa;
        if (t != null) {
            t.onStop();
            return;
        }
        this.zaa(4);
    }

    private static interface zaa {
        public int zaa();

        public void zaa(LifecycleDelegate var1);
    }

}

